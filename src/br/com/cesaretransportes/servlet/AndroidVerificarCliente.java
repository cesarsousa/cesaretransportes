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
 * Esta classe recebe requisição da activity OpcaoVisualizarClientes.
 */
public class AndroidVerificarCliente extends HttpServlet {
	private static final long serialVersionUID = 1L;
          
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		this.doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {

		int idCliente = Integer.parseInt(request.getParameter("id"));
		
		Connection conexao = null;
		try {
			conexao = AbstractConnectionFactory.getConexao();
			ClienteDao clienteDao = new ClienteDao(conexao);
			
			Cliente cliente = clienteDao.getCliente(idCliente);
			if(cliente == null || cliente.getTipoCliente().equals("A") || cliente.getDataExclusao() != null){
				write(response, "0");
			}else{
				write(response, cliente.getEmail() + ";" + cliente.getSenha());
			}			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			write(response, "ERRO codigo CNFE19 " + e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
			write(response, "ERRO codigo SQLE19 " + e.getMessage());
		} finally{
			try {
				conexao.close();
			} catch (SQLException e) {
				e.printStackTrace();
				write(response, "ERRO codigo SQLE219 " + e.getMessage());
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
