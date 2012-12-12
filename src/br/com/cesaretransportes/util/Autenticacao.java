package br.com.cesaretransportes.util;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

import org.apache.log4j.Logger;

public class Autenticacao extends Authenticator {
	
	private final String cetrans;
	private final String senha;
	
	private final Logger logger = Logger.getLogger(Autenticacao.class);

	public Autenticacao(String cetrans, String senha) {
		this.cetrans = cetrans;
		this.senha = senha;
	}

	protected PasswordAuthentication getPasswordAuthentication() {		
		logger.info("........................Autenticando");
		return new PasswordAuthentication(cetrans, senha);
	}	
}
