package com.mateus.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mateus.cursomc.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>{
	//Faz a transação ficar mais rapida e diminui o locking no banco de dados
	
	@Transactional(readOnly=true)
	Cliente findByEmail(String email);
	
	@Transactional(readOnly=true)
	Cliente findByCpfOuCnpj(String cpfOuCnpj);
}
