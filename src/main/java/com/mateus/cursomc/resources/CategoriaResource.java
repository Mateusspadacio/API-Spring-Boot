package com.mateus.cursomc.resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mateus.cursomc.domain.Categoria;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {

	@RequestMapping(method=RequestMethod.GET)
	public List<Categoria> listar() {
		List<Categoria> lista;
		
		Categoria c1 = new Categoria(1, "Informatica");
		Categoria c2 = new Categoria(2, "Escritorio");
		
		lista = Arrays.asList(c1, c2);
		
		return lista;
	}
	
}
