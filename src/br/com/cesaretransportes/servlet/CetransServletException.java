package br.com.cesaretransportes.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CetransServletException
 * Classe servlet utilitária para tratar exceções.
 */
public class CetransServletException extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String mensagemErro;
	private String codigo;
	private String nomeDaClasse;
	   
	public CetransServletException(String codigo, String nomeDaClasse, String mensagemErro) {
		this.codigo = codigo;
		this.nomeDaClasse = nomeDaClasse;
		this.mensagemErro = mensagemErro;
		
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		this.doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {	
		
		String mensagem = "Ocorreu um erro inesperado ao tentar processar sua requisição,<br/><br/>" +
		"Por favor, ajude-nos a resolver este problema o mais r&aacute;pido poss&iacute;vel.<br/>" +
		"Envie-nos um email com o c&oacute;digo para podermos apurrar as causas deste erro.<br/><br/>" +
		"A equipe do portal Cesare Transportes agrade&ccedil;e sua compreens&atilde;o, <br />" +
		"e pede desculpas pelo transtorno.<br/><br/>" +
		"Código do erro: <code>" + codigo + " - " + nomeDaClasse + "</code>" +
		"<p>" + mensagemErro + "</p>" ;
		
		request.setAttribute("mensagem", mensagem);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/resposta-de-solicitacao.jsp");
		dispatcher.forward(request, response);		
	}

}
