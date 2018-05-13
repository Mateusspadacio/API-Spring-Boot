package com.mateus.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.mateus.cursomc.domain.Cliente;
import com.mateus.cursomc.domain.enums.TipoCliente;
import com.mateus.cursomc.dto.ClienteNewDTO;
import com.mateus.cursomc.repositories.ClienteRepository;
import com.mateus.cursomc.resources.exception.FieldMessage;
import com.mateus.cursomc.services.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();
		Cliente obj = null;
		
		if (objDto.getTipo().equals(TipoCliente.PESSOA_FISICA.getCod()) && 
				!BR.isValidCPF(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
		}
		
		if (objDto.getTipo().equals(TipoCliente.PESSOA_JURIDICA.getCod()) && 
				!BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
		}
		
		obj = clienteRepository.findByCpfOuCnpj(objDto.getCpfOuCnpj());
		
		if (obj != null) {
			list.add(new FieldMessage("cpfOuCnpj", "Já existe um cliente com este registro"));
		}
		
		obj = null;
		obj = clienteRepository.findByEmail(objDto.getEmail());
		
		if (obj != null) {
			list.add(new FieldMessage("email", "Já existe um cliente com este email"));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
