package com.mateus.cursomc.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mateus.cursomc.domain.Cidade;
import com.mateus.cursomc.dto.CidadeDTO;
import com.mateus.cursomc.dto.EstadoDTO;
import com.mateus.cursomc.services.CidadeService;
import com.mateus.cursomc.services.EstadoService;

@RestController
@RequestMapping(value="/estados")
public class EstadoResource {
	
	@Autowired
	private EstadoService service;
	
	@Autowired
	private CidadeService cidadeService;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<EstadoDTO>> findAll() {
		List<EstadoDTO> lista = service.toDTO(service.findAll());
		return ResponseEntity.ok().body(lista);
	}
	
	@RequestMapping(value="/{idEstado}/cidades")
	public ResponseEntity<List<CidadeDTO>> findCidades(@PathVariable Integer idEstado) {
		List<Cidade> lista = cidadeService.findCidades(idEstado);
		List<CidadeDTO> listaDTO = cidadeService.toDTO(lista);
		return ResponseEntity.ok().body(listaDTO);
	}

}
