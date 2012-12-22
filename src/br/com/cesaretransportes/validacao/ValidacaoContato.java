package br.com.cesaretransportes.validacao;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class ValidacaoContato{
	
	public static boolean contatoEhValido(String nome, String email, String mensagem) {
	
		if(nome.isEmpty() || "NOME ou EMPRESA".equals(nome)) return false;
		if(email.isEmpty() || "EMAIL".equals(mensagem) || !emailValido(email)) return false;
		if(mensagem.isEmpty() || "DIGITE SUA MENSAGEM ...".equals(mensagem)) return false;
		return true;
	}
	
	public static void verificarCamposPreenchidos(
			String nome, 
			String email,
			String mensagem, 
			HttpServletRequest request) throws ServletException, IOException {

		if (nome.isEmpty() || "NOME ou EMPRESA".equals(nome)) {
			request.setAttribute("erroContato", true);
			request.setAttribute("msgNome", "O campo 'NOME ou EMPRESA' deve ser preenchido !");
		} else {
			request.setAttribute("nome", nome);
		}

		if (email.isEmpty() || "EMAIL".equals(email)) {
			request.setAttribute("erroContato", true);
			request.setAttribute("msgEmail","O campo 'EMAIL' deve ser preenchido !");
		} else if (!emailValido(email)) {
			request.setAttribute("erroContato", true);
			request.setAttribute("email", email);
			request.setAttribute("msgEmail", "O campo 'EMAIL' possui formato inválido !");
		} else {
			request.setAttribute("email", email);
		}


		if (mensagem.isEmpty() || "DIGITE SUA MENSAGEM ...".equals(mensagem)) {
			request.setAttribute("erroContato", true);
			request.setAttribute("msgMensagem", "O campo 'MENSAGEM' deve ser preenchido !");
		} else {
			request.setAttribute("mensagem", mensagem);
		}	
	}
	
	public static boolean emailValido(String email) {
		return email.matches("[a-zA-Z0-9._%-]+@[a-zA-Z0-9._-]+\\.[a-z]{2,4}");
	}
}
