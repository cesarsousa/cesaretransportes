package br.com.cesaretransportes.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.cesaretransportes.dao.AbstractConnectionFactory;
import br.com.cesaretransportes.dao.EmpresaDao;
import br.com.cesaretransportes.modelo.Empresa;
import br.com.cesaretransportes.util.CesareUtil;
import br.com.cesaretransportes.util.Email;
import br.com.cesaretransportes.util.HtmlMensagem;

/*
 * Esta classe recebe requisição da activity ClienteContato
 */
public class AndroidContatoCadastrar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
    	throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		int idCliente = Integer.parseInt(request.getParameter("id"));
		String nome = request.getParameter("nome");
		String email = request.getParameter("email");
		String mensagem = request.getParameter("mensagem");
		String data = CesareUtil.getDataDoSistema();
		
		Connection conexao = null;
		try {			
			conexao = AbstractConnectionFactory.getConexao();
			EmpresaDao empresaDao = new EmpresaDao(conexao);		
			
			Empresa empresa = empresaDao.get();			
			
			/*
			 * notificação ao cliente do recebimento do email.
			 */
			Email.enviarEmail(empresa.getEmail(), empresa.getSenha(), 
					email, "Cesare Transportes - confirmacao de Recebimento de Email",
					HtmlMensagem.getMensagemNotificacaoCliente(nome, "contato"));
			
			/*
			 * notificação a empresa de um novo email
			 */
			Email.enviarEmail(empresa.getEmail(), empresa.getSenha(), 
					empresa.getEmail(), "CeTrans - Novo contato Recebido", 
					HtmlMensagem.getMensagemNotificacaoEmpresa(nome, data, idCliente, email, mensagem, "contato"));
			
			write(response, "Seu email foi enviado com sucesso");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			write(response, "ERRO codigo CNFE06 " + e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
			write(response, "ERRO codigo SQL06 " + e.getMessage());
		} catch (AddressException e) {
			e.printStackTrace();
			write(response, "ERRO codigo AE06 " + e.getMessage());
		} catch (SendFailedException e) {
			e.printStackTrace();
			write(response, "ERRO codigo SFE06 " + e.getMessage());
		} catch (MessagingException e) {
			e.printStackTrace();
			write(response, "ERRO codigo ME06 " + e.getMessage());
		} finally{
			try {
				conexao.close();
			} catch (SQLException e) {
				e.printStackTrace();
				write(response, "ERRO codigo SQLE206 " + e.getMessage());
			}
		}
	}
	
	private void write(HttpServletResponse response, String resultado) throws IOException {		
		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();		
		writer.print(resultado);		
		writer.flush();
		writer.close();		
	}
}