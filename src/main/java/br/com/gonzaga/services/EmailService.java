package br.com.gonzaga.services;

import org.springframework.mail.SimpleMailMessage;

import br.com.gonzaga.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido obj);

	void sendEmail(SimpleMailMessage msg);
}