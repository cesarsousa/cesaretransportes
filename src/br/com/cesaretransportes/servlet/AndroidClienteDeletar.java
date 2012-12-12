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

/*
 * Esta classe recebe requisi��o da activity DetalheCliente.
 */
public class AndroidClienteDeletar extends HttpServlet {	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		int id = Integer.valueOf(request.getParameter("id"));

		Connection conexao = null;
		try {
			conexao = AbstractConnectionFactory.getConexao();			
			ClienteDao clienteDao = new ClienteDao(conexao);
			clienteDao.deletar(id);
			write(response, "Cliente excluido com sucesso!");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			write(response, "ERRO codigo CNFE03" + e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
			write(response, "ERRO codigo SQL03" + e.getMessage());
		} finally {
			try {
				conexao.close();
			} catch (SQLException e) {
				e.printStackTrace();
				write(response, "ERRO codigo SQL203" + e.getMessage());
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
