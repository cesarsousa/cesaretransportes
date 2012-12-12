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
import br.com.cesaretransportes.dao.EnderecoDao;
import br.com.cesaretransportes.modelo.Endereco;

/*
 * Esta classe recebe requisição da activity OpcaoOrcamento.
 */
public class AndroidEnderecosPorOrcamento extends HttpServlet {
	private static final long serialVersionUID = 1L;
    	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		int idOrcamento = Integer.parseInt(request.getParameter("id"));
		
		Connection conexao = null;
		try {
			conexao = AbstractConnectionFactory.getConexao();
			EnderecoDao enderecoDao = new EnderecoDao(conexao);
			
			List<Endereco> enderecos = enderecoDao.getEnderecosPorOrcamentos(idOrcamento);
			
			ServletOutputStream out = response.getOutputStream();
			DataOutputStream dataOut = new DataOutputStream(out);
			
			dataOut.writeInt(enderecos.size());
			for(Endereco endereco : enderecos){
				endereco.serialize(dataOut);
			}
			
			dataOut.flush();
			dataOut.close();
			out.close();			
			
		} catch (ClassNotFoundException e) {
			// TODO tratar exceção android
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO tratar exceção android
			e.printStackTrace();
		} finally{
			try {
				conexao.close();
			} catch (SQLException e) {
				// TODO tratar exceção android
				e.printStackTrace();
			}
		}
	}

}
