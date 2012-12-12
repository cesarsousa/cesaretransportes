package br.com.cesaretransportes.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.cesaretransportes.dao.AbstractConnectionFactory;
import br.com.cesaretransportes.dao.ContatoDao;
import br.com.cesaretransportes.modelo.Contato;

public class MostrarListaDosEmailsServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException {
		
		Connection conexao = null;
		
		try {
			String opcao = request.getParameter("opcao");
			String emailsSalvos = request.getParameter("emailsSalvos");
			conexao = AbstractConnectionFactory.getConexao();
			ContatoDao dao = new ContatoDao(conexao);
			List<Contato> listaDeContatos = dao.getListaDeContatos(opcao, emailsSalvos);
			
			if ("sim".equals(emailsSalvos)){
				request.setAttribute("mensagem", "Caixa de Entrada - Emails Salvos");
			}else{
				request.setAttribute("mensagem", "Caixa de Entrada");
				// sinal para mostrar-Contato.jsp renderizar o icone de salvar email na tela
				request.setAttribute("cxEntrada", "cxEnt");
			}
			request.setAttribute("listaDeContatos", listaDeContatos);			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/mostrar-contatos.jsp");
			dispatcher.forward(request, response);
		} catch (ClassNotFoundException e) {			
			e.printStackTrace();
			new CetransServletException("CNFE", getClass().getSimpleName(), e.getMessage()).doPost(request, response);
		} catch (RuntimeException e) {			
			e.printStackTrace();
			new CetransServletException("RTE", getClass().getSimpleName(), e.getMessage()).doPost(request, response);
		} finally{
			try {
				conexao.close();
			} catch (SQLException e) {
				e.printStackTrace();
				new CetransServletException("SQLE", getClass().getSimpleName(), e.getMessage()).doPost(request, response);
			}
		}
	}
}