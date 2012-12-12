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
 * Este classe recebe requisição da activity DetalheCliente.
 */
public class AndroidClienteConfirmar extends HttpServlet {
	private static final long serialVersionUID = 1L;   
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		this.doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		int id  = Integer.valueOf(request.getParameter("id"));
		
		Connection conexao = null;		
		try {
			conexao = AbstractConnectionFactory.getConexao();
			EmpresaDao empresaDao = new EmpresaDao(conexao);
			ClienteDao clienteDao = new ClienteDao(conexao);
			
			Empresa empresa = empresaDao.get();
			clienteDao.confirmar(id);
			Cliente cliente = clienteDao.getCliente(true, id);
			
			Email.enviarEmail(
					empresa.getEmail(),empresa.getSenha(), cliente.getEmail(), 
					"Cesare Transportes - Confirmacao de Cadastro", 
					HtmlMensagem.getMensagemConfirmacaoCadastro(cliente));
									
			write(response, "Cliente confirmado com sucesso!");	
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			write(response, "ERRO codigo CNFE02 " + e.getMessage());			
		} catch (SQLException e) {
			e.printStackTrace();
			write(response, "ERRO codigo SQL02 " + e.getMessage());
		} catch (AddressException e) {
			e.printStackTrace();
			write(response, "ERRO codigo AE02 " + e.getMessage());
		} catch (SendFailedException e) {
			e.printStackTrace();
			write(response, "ERRO codigo SFE02 " + e.getMessage());
		} catch (MessagingException e) {
			e.printStackTrace();
			write(response, "ERRO codigo ME02 " + e.getMessage());
		} finally {
			try {
				conexao.close();
			} catch (SQLException e) {
				e.printStackTrace();
				write(response, "ERRO codigo SQL202 " + e.getMessage());
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
