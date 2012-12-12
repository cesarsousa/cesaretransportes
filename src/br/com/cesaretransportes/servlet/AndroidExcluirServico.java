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
import br.com.cesaretransportes.dao.ServicoDao;

/*
 * Esta servlet recebe requisição da activity DetalheServico.
 */
public class AndroidExcluirServico extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		this.doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		int idServico = Integer.parseInt(request.getParameter("id"));
		
		Connection conexao = null;
		try {
			conexao = AbstractConnectionFactory.getConexao();
			ServicoDao servicoDao = new ServicoDao(conexao);
			
			servicoDao.excluir(idServico);
			
			write(response, "Servico excluido com sucesso");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			write(response, "ERRO codigo CNFE21 " + e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
			write(response, "ERRO codigo SQLE21 " + e.getMessage());
		} finally{
			try {
				conexao.close();
			} catch (SQLException e) {
				e.printStackTrace();
				write(response, "ERRO codigo SQLE221 " + e.getMessage());
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
