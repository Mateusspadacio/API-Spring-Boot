package com.mateus.cursomc.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mateus.cursomc.domain.Estado;
import com.mateus.cursomc.dto.EstadoDTO;
import com.mateus.cursomc.repositories.EstadoRepository;

@Service
public class EstadoService {
	
	@Autowired
	private EstadoRepository repo;

	public List<Estado> findAll() {
		return repo.findAllByOrderByNome();
	}
	
	public List<EstadoDTO> toDTO(List<Estado> estados) {
		List<EstadoDTO> lista = estados.stream().map(e -> new EstadoDTO(e)).collect(Collectors.toList());
		return lista;
	}
}
