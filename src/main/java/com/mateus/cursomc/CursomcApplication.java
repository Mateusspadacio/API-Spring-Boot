package com.mateus.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mateus.cursomc.domain.Categoria;
import com.mateus.cursomc.domain.Cidade;
import com.mateus.cursomc.domain.Estado;
import com.mateus.cursomc.domain.Produto;
import com.mateus.cursomc.repositories.CategoriaRepository;
import com.mateus.cursomc.repositories.CidadeRepository;
import com.mateus.cursomc.repositories.EstadoRepository;
import com.mateus.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null, "Computador", 2000d);
		Produto p2 = new Produto(null, "Impressora", 800d);
		Produto p3 = new Produto(null, "Mouse", 80d);
		
		Estado est1 = new Estado("São Paulo");
		Estado est2 = new Estado("Minas Gerais");
		
		Cidade c1 = new Cidade("Uberlandia", est2);
		Cidade c2 = new Cidade("São Paulo", est1);
		Cidade c3 = new Cidade("Campinas", est1);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		est1.getCidades().addAll(Arrays.asList(c1));
		
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
	}
}
