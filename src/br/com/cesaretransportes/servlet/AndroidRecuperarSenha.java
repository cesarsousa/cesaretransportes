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
import br.com.cesaretransportes.dao.ClienteDao;
import br.com.cesaretransportes.dao.EmpresaDao;
import br.com.cesaretransportes.modelo.Cliente;
import br.com.cesaretransportes.modelo.Empresa;
import br.com.cesaretransportes.util.Email;
import br.com.cesaretransportes.util.HtmlMensagem;

/*
 * Esta classe recebe requisição da activity RecuperarSenha.
 */
public class AndroidRecuperarSenha extends HttpServlet {
	private static final long serialVersionUID = 1L;       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {

		String email = request.getParameter("email");
						
		Connection conexao = null;
		try {
			conexao = AbstractConnectionFactory.getConexao();			
			ClienteDao clienteDao = new ClienteDao(conexao);
			EmpresaDao empresaDao = new EmpresaDao(conexao);
			
			Empresa empresa = empresaDao.get();
			
			if(!clienteDao.clienteExiste(email)){
				write(response, "EMAILINVALIDO O email informado nao esta cadastrado em nosso site.");				
			}else{			
				Cliente cliente = clienteDao.getCliente(email);
				
				Email.enviarEmail(empresa.getEmail(), empresa.getSenha(), email,
						"Cesare Transportes - Recuperação de senha.", 
						HtmlMensagem.getMensagemRecuperarSenha(cliente));
				
				write(response, "enviado com sucesso");				
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			write(response, "ERRO codigo CNFE12 " + e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
			write(response, "ERRO codigo SQLE12 " + e.getMessage());
		} catch (AddressException e) {
			e.printStackTrace();
			write(response, "ERRO codigo AE12 " + e.getMessage());
		} catch (SendFailedException e) {
			e.printStackTrace();
			write(response, "ERRO codigo SFE12 " + e.getMessage());
		} catch (MessagingException e) {
			e.printStackTrace();
			write(response, "ERRO codigo ME12 " + e.getMessage());
		} finally {
			try {
				conexao.close();
			} catch (SQLException e) {
				e.printStackTrace();
				write(response, "ERRO codigo SQL2E12 " + e.getMessage());
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