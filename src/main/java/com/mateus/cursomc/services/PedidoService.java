package com.mateus.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mateus.cursomc.domain.Cliente;
import com.mateus.cursomc.domain.ItemPedido;
import com.mateus.cursomc.domain.PagamentoComBoleto;
import com.mateus.cursomc.domain.Pedido;
import com.mateus.cursomc.domain.Produto;
import com.mateus.cursomc.domain.enums.EstadoPagamento;
import com.mateus.cursomc.repositories.ItemPedidoRepository;
import com.mateus.cursomc.repositories.PagamentoRepository;
import com.mateus.cursomc.repositories.PedidoRepository;
import com.mateus.cursomc.services.exception.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private ClienteService clienteService;

	public Pedido find(Integer id) {
		Optional<Pedido> cliente = repo.findById(id);
		return cliente.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! ID: " + id + ", Tipo: " + Pedido.class.getName()));
	}

	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pcb = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pcb, obj.getInstante());
		}
		
		Cliente cliente = clienteService.find(obj.getCliente().getId());
		obj.setCliente(cliente);
		
		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		
		for (ItemPedido item : obj.getItens()) {
			Produto produto = produtoService.find(item.getProduto().getId());
			item.setDesconto(0d);
			item.setProduto(produto);
			item.setPreco(produto.getPreco());
			item.setPedido(obj);
		}
		
		itemPedidoRepository.saveAll(obj.getItens());
		System.out.println(obj);
		return obj;
	}

}
