package br.com.cesaretransportes.servlet;

import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.cesaretransportes.dao.AbstractConnectionFactory;
import br.com.cesaretransportes.dao.OrcamentoDao;
import br.com.cesaretransportes.modelo.Orcamento;

/*
 * Esta classe recebe requisição da activity OpcaoVisualizarOrcamentos
 */
public class AndroidOrcamentoVisualizarTodos extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   protected void doGet(HttpServletRequest request, HttpServletResponse response) 
   		throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		Connection conexao = null;
		try {
			conexao = AbstractConnectionFactory.getConexao();
			OrcamentoDao orcamentoDao = new OrcamentoDao(conexao);
			List<Orcamento> orcamentos = orcamentoDao.getListaDeOrcamentos("orcamentoLido", 0);
			
			ServletOutputStream out = response.getOutputStream();
			DataOutputStream dataOut = new DataOutputStream(out);
			
			// escrita da quantidade de orçamentos
			dataOut.writeInt(orcamentos.size());
			
			// serialização dos orçamentos
			for (Orcamento orcamento : orcamentos){
				orcamento.serialize(dataOut);
			}
			
			dataOut.flush();
			dataOut.close();
			out.close();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			// TODO tratar Exceção android
		} catch (SQLException e) {
			e.printStackTrace();
			// tratar Exceção android
		} finally{
			try {
				conexao.close();
			} catch (SQLException e) {
				e.printStackTrace();
				// tratar Exceção android
			}
		}		
	}
	
	/*private void write(HttpServletResponse response, String resultado) throws IOException {		
		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();		
		writer.print(resultado);		
		writer.flush();
		writer.close();		
	}*/
}
