package com.mateus.cursomc.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mateus.cursomc.domain.Categoria;
import com.mateus.cursomc.domain.Cidade;
import com.mateus.cursomc.domain.Cliente;
import com.mateus.cursomc.domain.Endereco;
import com.mateus.cursomc.domain.Estado;
import com.mateus.cursomc.domain.ItemPedido;
import com.mateus.cursomc.domain.Pagamento;
import com.mateus.cursomc.domain.PagamentoComBoleto;
import com.mateus.cursomc.domain.PagamentoComCartao;
import com.mateus.cursomc.domain.Pedido;
import com.mateus.cursomc.domain.Produto;
import com.mateus.cursomc.domain.enums.EstadoPagamento;
import com.mateus.cursomc.domain.enums.Perfil;
import com.mateus.cursomc.domain.enums.TipoCliente;
import com.mateus.cursomc.repositories.CategoriaRepository;
import com.mateus.cursomc.repositories.CidadeRepository;
import com.mateus.cursomc.repositories.ClienteRepository;
import com.mateus.cursomc.repositories.EnderecoRepository;
import com.mateus.cursomc.repositories.EstadoRepository;
import com.mateus.cursomc.repositories.ItemPedidoRepository;
import com.mateus.cursomc.repositories.PagamentoRepository;
import com.mateus.cursomc.repositories.PedidoRepository;
import com.mateus.cursomc.repositories.ProdutoRepository;

@Service
public class DBService {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private BCryptPasswordEncoder pe;

	public void instatiateDatabase() throws ParseException {
		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "Cama mesa e banho");
		Categoria cat4 = new Categoria(null, "Eletronicos");
		Categoria cat5 = new Categoria(null, "Jardinagem");
		Categoria cat6 = new Categoria(null, "Decoração");
		Categoria cat7 = new Categoria(null, "Perfumaria");
		
		Produto p1 = new Produto(null, "Computador", 2000d);
		Produto p2 = new Produto(null, "Impressora", 800d);
		Produto p3 = new Produto(null, "Mouse", 80d);
		Produto p4 = new Produto(null, "Mesa de escritório", 300d);
		Produto p5 = new Produto(null, "Toalha", 50d);
		Produto p6 = new Produto(null, "Colcha", 200d);
		Produto p7 = new Produto(null, "TV true color", 1200d);
		Produto p8 = new Produto(null, "Roçadeira", 800d);
		Produto p9 = new Produto(null, "Abajour", 100d);
		Produto p10 = new Produto(null, "Pendente", 180d);
		Produto p11 = new Produto(null, "Shampoo", 90d);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2, p4));
		cat3.getProdutos().addAll(Arrays.asList(p5, p6));
		cat4.getProdutos().addAll(Arrays.asList(p1, p2, p3, p7));
		cat5.getProdutos().addAll(Arrays.asList(p8));
		cat6.getProdutos().addAll(Arrays.asList(p9, p10));
		cat7.getProdutos().addAll(Arrays.asList(p11));
		
		p1.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2, cat4));
		p3.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p4.getCategorias().addAll(Arrays.asList(cat2));
		p5.getCategorias().addAll(Arrays.asList(cat3));
		p6.getCategorias().addAll(Arrays.asList(cat3));
		p7.getCategorias().addAll(Arrays.asList(cat4));
		p8.getCategorias().addAll(Arrays.asList(cat5));
		p9.getCategorias().addAll(Arrays.asList(cat6));
		p10.getCategorias().addAll(Arrays.asList(cat6));
		p11.getCategorias().addAll(Arrays.asList(cat7));
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));
		
		Estado est1 = new Estado("São Paulo");
		Estado est2 = new Estado("Minas Gerais");
		
		Cidade c1 = new Cidade(null, "Uberlandia", est2);
		Cidade c2 = new Cidade(null, "São Paulo", est1);
		Cidade c3 = new Cidade(null, "Campinas", est1);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "teussama22@gmail.com", "36378912377", TipoCliente.PESSOA_FISICA, pe.encode("123"));
		cli1.getTelefones().add("27363323");
		cli1.getTelefones().add("93838393");
		
		Cliente cli2 = new Cliente(null, "Ana Silva", "f4isk@hotmail.com", "16434186029", TipoCliente.PESSOA_FISICA, pe.encode("123"));
		cli2.addPerfil(Perfil.ADMIN);
		cli2.getTelefones().add("345345345");
		cli2.getTelefones().add("343453453");
		
		Endereco e1 = new Endereco("Rua Flores", "300", "Apto 203", "Jardim", "38220634", cli1, c1);
		Endereco e2 = new Endereco("Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);
		Endereco e3 = new Endereco("Avenida Floriano", "2106", null, "Centro", "3645645", cli2, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		cli1.getEnderecos().addAll(Arrays.asList(e3));
		
		clienteRepository.saveAll(Arrays.asList(cli1, cli2));
		enderecoRepository.saveAll(Arrays.asList(e1, e2, e3));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(sdf.parse("10/10/2017 19:35"), cli1, e2);
		Pedido ped3 = new Pedido(sdf.parse("10/12/2017 19:50"), cli2, e3);
		
		Pagamento pagto1 = new PagamentoComCartao(EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		Pagamento pagto2 = new PagamentoComBoleto(EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pagto2);
		Pagamento pagto3 = new PagamentoComBoleto(EstadoPagamento.PENDENTE, ped3, sdf.parse("10/12/2017 00:00"), null);
		ped3.setPagamento(pagto3);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		cli2.getPedidos().addAll(Arrays.asList(ped3));
		
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2, ped3));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0d, 1, 2000d);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0d, 2, 80d);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100d, 1, 800d);
		
		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
	}
	
}
