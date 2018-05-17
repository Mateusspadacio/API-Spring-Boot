package com.mateus.cursomc.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mateus.cursomc.domain.Cidade;
import com.mateus.cursomc.domain.Estado;
import com.mateus.cursomc.dto.CidadeDTO;
import com.mateus.cursomc.repositories.CidadeRepository;
import com.mateus.cursomc.repositories.EstadoRepository;
import com.mateus.cursomc.services.exception.ObjectNotFoundException;

@Service
public class CidadeService {

	@Autowired
	private CidadeRepository repo;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	public List<Cidade> findCidades(Integer idEstado) {
		Optional<Estado> estado = estadoRepository.findById(idEstado);
		if (!estado.isPresent()) {
			throw new ObjectNotFoundException("Estado não encontrado, id: " + idEstado);
		}
		return repo.findByEstadoOrderByNomeAsc(estado.get());
	}
	
	public List<CidadeDTO> toDTO(List<Cidade> lista) {
		List<CidadeDTO> list = lista.stream().map(c -> new CidadeDTO(c)).collect(Collectors.toList());
		return list;
	}
	
}
