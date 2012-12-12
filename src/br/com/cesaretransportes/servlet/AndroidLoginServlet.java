package br.com.cesaretransportes.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.cesaretransportes.dao.AbstractConnectionFactory;
import br.com.cesaretransportes.dao.ClienteDao;
import br.com.cesaretransportes.modelo.Cliente;

/*
 * Esta classe recebe requisição activity Login.
 */
public class AndroidLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		this.doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		String usuario = request.getParameter("usuario");
		String senha = request.getParameter("senha");
		
		Connection conexao = null;
		
		try {
			conexao = AbstractConnectionFactory.getConexao();			
			ClienteDao clienteDao = new ClienteDao(conexao);
			Cliente cliente = clienteDao.getCliente(usuario, senha);

			String res = "";

			if (cliente == null) {
				res = String.valueOf(0);;
			} else {
				res = String.valueOf(cliente.getIdCliente());
			}		

			write(response, res);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			write(response, "ERRO codigo CNFE08 " + e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
			write(response, "ERRO codigo SQLE08 " + e.getMessage());
		} finally{
			try {
				conexao.close();
			} catch (Exception e) {
				e.printStackTrace();
				write(response, "ERRO codigo SQL2E08 " + e.getMessage());
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