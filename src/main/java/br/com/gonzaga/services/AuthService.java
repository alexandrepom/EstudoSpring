package br.com.gonzaga.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.gonzaga.domain.Cliente;
import br.com.gonzaga.repositories.ClienteRepository;
import br.com.gonzaga.services.exception.ObjectNotFoundException;

@Service
public class AuthService {
	
	@Autowired private ClienteRepository clienteRepository;
	@Autowired private BCryptPasswordEncoder pe;
	@Autowired private EmailService emailService;
	private Random rand = new Random();
	
	public void sendNewPassword(String email) {
		
		Cliente cliente = clienteRepository.findByEmail(email);
		
		if(cliente==null) {
			throw new ObjectNotFoundException("Email não localizado");
		}
		
		String newPass = newPassword();
		
		cliente.setSenha(pe.encode(newPass));
		
		clienteRepository.save(cliente);
		
		emailService.sendNewPasswordEmail(cliente, newPass);
		
	}

	private String newPassword() {
		char[] vet = new char[10];
		
		for(int i=0; i<10;i++) {
			vet[i] = randonChar();
		}
		
		return new String(vet);
	}

	private char randonChar() {
		int opt = rand.nextInt(3);
		char c;
		
		switch (opt) {
		case 0://gera um dígito
			 c = (char) (rand.nextInt(10)+48);
			break;
		case 1://gera letra maiúscula
			c = (char) (rand.nextInt(26)+65);
			break;
		default://gera letra minúscula
			c = (char) (rand.nextInt(26)+97);
			break;
		}

		return c;
	}

}
