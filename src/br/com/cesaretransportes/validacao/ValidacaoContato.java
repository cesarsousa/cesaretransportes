package br.com.cesaretransportes.validacao;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class ValidacaoContato{
	
	public static boolean contatoEhValido(String nome, String email, String mensagem) {
	
		
		
		
		return !"".equals(nome) && !"".equals(email) && ValidacaoContato.emailValido(email) && !"".equals(mensagem);
	}
	
	public static void verificarCamposPreenchidos(
			String nome, 
			String email,
			String mensagem, 
			HttpServletRequest request) throws ServletException, IOException {

		if ("".equals(nome)) {
			request.setAttribute("msgNome", "O campo nome deve ser preenchido !");
		} else {
			request.setAttribute("nome", nome);
		}

		if ("".equals(email)) {
			request.setAttribute("msgEmail","O campo email deve ser preenchido !");
		} else if (!emailValido(email)) {
			request.setAttribute("email", email);
			request.setAttribute("msgEmail", "O campo email possui formato invalido !");
		} else {
			request.setAttribute("email", email);
		}


		if ("".equals(mensagem)) {
			request.setAttribute("msgMensagem", "O campo mensagem deve ser preenchido !");
		} else {
			request.setAttribute("mensagem", mensagem);
		}	
	}
	
	public static boolean emailValido(String email) {
		return email.matches("[a-zA-Z0-9._%-]+@[a-zA-Z0-9._-]+\\.[a-z]{2,4}");
	}
}
