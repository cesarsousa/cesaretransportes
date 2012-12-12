package br.com.cesaretransportes.servlet;

import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.cesaretransportes.dao.AbstractConnectionFactory;
import br.com.cesaretransportes.dao.ClienteDao;
import br.com.cesaretransportes.dao.EnderecoDao;
import br.com.cesaretransportes.dao.OrcamentoDao;
import br.com.cesaretransportes.dao.TelefoneDao;
import br.com.cesaretransportes.modelo.Cliente;

/*
 * Esta classe recebe reuisição da activity Login e OpcaoVisualizarClientes.
 */
public class AndroidClienteVisualizar extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		Connection conexao = null;
		try {
			Cliente cliente;			
			conexao = AbstractConnectionFactory.getConexao();
			OrcamentoDao orcamentoDao = new OrcamentoDao(conexao);
			TelefoneDao telefoneDao = new TelefoneDao(conexao);
			EnderecoDao enderecoDao = new EnderecoDao(conexao);
			ClienteDao clienteDao = new ClienteDao(conexao);
			
			if(Boolean.valueOf(request.getParameter("login"))){
				String usuario = request.getParameter("usuario");
				String senha = request.getParameter("senha");
				cliente = clienteDao.getCliente(usuario, senha);
			}else{			
				int idCliente = Integer.parseInt(request.getParameter("id"));
				cliente = clienteDao.getCliente(idCliente);
			}		
			
			cliente.setTelefone(telefoneDao.get(cliente.getIdCliente()));
			cliente.setEndereco(enderecoDao.get(cliente.getIdCliente()));
			cliente.setOrcamentos(orcamentoDao.getListaDeOrcamentos(cliente.getIdCliente(), "idOrcamento", 1));

			ServletOutputStream out = response.getOutputStream();
			DataOutputStream dataOut = new DataOutputStream(out);	
			cliente.serialize(dataOut);
			
			dataOut.close();
			out.close();
						
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			// TODO tratar Exceção android
		} catch (SQLException e) {
			e.printStackTrace();
			// TODO tratar Exceção android
		} finally {
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