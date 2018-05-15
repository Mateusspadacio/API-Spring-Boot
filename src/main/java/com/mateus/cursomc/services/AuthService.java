package com.mateus.cursomc.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mateus.cursomc.domain.Cliente;
import com.mateus.cursomc.repositories.ClienteRepository;
import com.mateus.cursomc.services.exception.ObjectNotFoundException;

@Service
public class AuthService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Autowired
	private EmailService emailService;

	private Random random = new Random();

	public void sendNewPassword(String email) {

		Cliente cliente = clienteRepository.findByEmail(email);

		if (cliente == null) {
			throw new ObjectNotFoundException("Email não encontrado");
		}

		String newPass = newPassword();
		cliente.setSenha(encoder.encode(newPass));

		clienteRepository.save(cliente);
		emailService.sendNewPasswordHtmlEmail(cliente, newPass);
	}

	private String newPassword() {
		char[] vet = new char[10];

		for (int i = 0; i < 10; i++) {
			vet[i] = randomChar();
		}

		return new String(vet);
	}

	private char randomChar() {
		int opt = random.nextInt(3);
		char ascii = 0;

		switch (opt) {
		case 0:
			ascii = (char) (random.nextInt(26) + 65); //letra maiuscula
			break;
		case 1:
			ascii = (char) (random.nextInt(26) + 97); //letra minusculas
			break;
		case 2:
			ascii = (char) (random.nextInt(10) + 48); //letra maiuscula
			break;
		}
		return ascii;
	}
}
