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
import br.com.cesaretransportes.util.Email;
import br.com.cesaretransportes.util.HtmlMensagem;

/*
 * Esta classe recebe requisiçães das activities.
 * - EnviarEmail
 * - TelaErroCliente
 */
public class AndroidEnviarEmail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		this.doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		String email = request.getParameter("email");
		String assunto = request.getParameter("assunto");
		String mensagem = request.getParameter("mensagem");
		String resultado = "true";
		
		Connection conexao = null;
		try {			
			conexao = AbstractConnectionFactory.getConexao();
			EmpresaDao empresaDao = new EmpresaDao(conexao);
			Empresa empresa = empresaDao.get();
		
			Email.enviarEmail(empresa.getEmail(), empresa.getSenha(), email, assunto,
						HtmlMensagem.getMensagemEnviarEmail(mensagem));
			
			write(response, resultado);
			
		} catch (AddressException e) {			
			e.printStackTrace();
			write(response, "ERRO codigo AE07 " + e.getMessage());
		} catch (SendFailedException e) {			
			e.printStackTrace();
			write(response, "ERRO codigo SFE07" + e.getMessage());
		} catch (MessagingException e) {			
			e.printStackTrace();
			write(response, "ERRO codigo ME07 " + e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
			write(response, "ERRO codigo SQL07 " + e.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			write(response, "ERRO codigo CNFE07 " + e.getMessage());
		} finally{
			try {
				conexao.close();
			} catch (SQLException e) {
				e.printStackTrace();
				write(response, "ERRO codigo SQL207 " + e.getMessage());
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
