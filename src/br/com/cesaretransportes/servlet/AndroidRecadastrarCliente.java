package br.com.cesaretransportes.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;

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
 * Esta classe requisição da activity RecadastrarCliente
 * @author Miguel
 *
 */
public class AndroidRecadastrarCliente extends HttpServlet {
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
			
			Cliente cliente = clienteDao.getCliente(email);
			clienteDao.recadastrar(cliente.getEmail(), cliente.getSenha(), Calendar.getInstance());
			
			/*
			 *  notificacao para o cliente do recadastramento
			 */
			Email.enviarEmail(empresa.getEmail(), empresa.getSenha(), cliente.getEmail(),
					"Cesare Transportes - confirmacao de Cadastro",
					HtmlMensagem.getMensagemNotificacaoCliente(cliente.getNome(), "cadastro"));

			/*
			 * Email de notificação a empresa de um novo cliente cadastrado.
			 */
			Email.enviarEmail(empresa.getEmail(), empresa.getSenha(), empresa.getEmail(),
					"Cesare Transportes - Novo cliente cadastrado",
					HtmlMensagem.getMensagemEnviarEmail(cliente.getIdCliente(), cliente.getNome()));
			
			write(response, "true");			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			write(response, "ERRO codigo CNFE17 " + e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
			write(response, "ERRO codigo CNFE17 " + e.getMessage());
		} catch (AddressException e) {
			e.printStackTrace();
			write(response, "ERRO codigo CNFE17 " + e.getMessage());
		} catch (SendFailedException e) {
			e.printStackTrace();
			write(response, "ERRO codigo CNFE17 " + e.getMessage());
		} catch (MessagingException e) {
			e.printStackTrace();
			write(response, "ERRO codigo CNFE17 " + e.getMessage());
		} finally{
			try {
				conexao.close();
			} catch (SQLException e) {
				e.printStackTrace();
				write(response, "ERRO codigo CNFE17 " + e.getMessage());
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


