package com.mateus.cursomc.services;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.mateus.cursomc.domain.Cliente;
import com.mateus.cursomc.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {
	
	@Value("${default.sender}")
	private String sender;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public void sendOrderConfirmationEmail(Pedido obj) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(obj);
		sendEmail(sm);
	}
	
	@Override
	public void sendOrderConfirmationHtmlEmail(Pedido obj) {
		try {
			MimeMessage mm = prepareMimeMessageFromPedido(obj);
			sendHtmlEmail(mm);
		} catch(MessagingException e) {
			sendOrderConfirmationEmail(obj);
		}
		
	}
	
	@Override
	public void sendNewPasswordEmail(Cliente cliente, String newPass) {
		SimpleMailMessage sm = prepareSimpleMailMessageNewPasswordCliente(cliente, newPass);
		sendEmail(sm);
	}
	
	@Override
	public void sendNewPasswordHtmlEmail(Cliente cliente, String newPass) {
		try {
			MimeMessage mm = prepareMimeMessageNewPasswordCliente(cliente, newPass);
			sendHtmlEmail(mm);
		} catch (MessagingException e) {
			sendNewPasswordEmail(cliente, newPass);
		}
		
	}

	protected MimeMessage prepareMimeMessageFromPedido(Pedido obj) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
		mmh.setTo(obj.getCliente().getEmail());
		mmh.setFrom(sender);
		mmh.setSubject("Pedido confirmado! Código: " + obj.getId());
		mmh.setSentDate(new Date(System.currentTimeMillis()));
		mmh.setText(htmlFromTemplatePedido(obj), true);
		return mimeMessage;
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido obj) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(obj.getCliente().getEmail());
		sm.setFrom(sender);
		sm.setSubject("Pedido confirmado! Código: " + obj.getId());
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(obj.toString());
		return sm;
	}
	
	public MimeMessage prepareMimeMessageNewPasswordCliente (Cliente cliente, String newPass) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
		mmh.setTo(cliente.getEmail());
		mmh.setFrom(sender);
		mmh.setSubject("Alteração de senha!");
		mmh.setSentDate(new Date(System.currentTimeMillis()));
		mmh.setText(htmlFromTemplateNewPasswordCliente(cliente, newPass), true);
		return mimeMessage;
	}
	
	public SimpleMailMessage prepareSimpleMailMessageNewPasswordCliente (Cliente cliente, String newPass) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(cliente.getEmail());
		sm.setFrom(sender);
		sm.setSubject("Alteração de senha!");
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(cliente.newPasswordMessage(newPass));
		return sm;
	}
	
	protected String htmlFromTemplatePedido(Pedido obj) {
		Context context = new Context();
		context.setVariable("pedido", obj);
		return templateEngine.process("email/confirmacaoPedido", context);
	}
	
	protected String htmlFromTemplateNewPasswordCliente(Cliente cliente, String newPass) {
		Context context = new Context();
		context.setVariable("cliente", cliente);
		context.setVariable("newPass", newPass);
		return templateEngine.process("email/novaSenhaCliente", context);
	}
	
}
