package br.com.cesaretransportes.servlet;

import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.cesaretransportes.dao.AbstractConnectionFactory;
import br.com.cesaretransportes.dao.ClienteDao;
import br.com.cesaretransportes.dao.EnderecoDao;
import br.com.cesaretransportes.dao.TelefoneDao;
import br.com.cesaretransportes.modelo.Cliente;

/*
 * Esta classe recebe requisicao da activity OpcaoVisualizarClientes
 */
public class AndroidClienteVisualizarTodos extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		Connection conexao = null;
		try {
			conexao = AbstractConnectionFactory.getConexao();
			TelefoneDao telefoneDao = new TelefoneDao(conexao);
			EnderecoDao enderecoDao = new EnderecoDao(conexao);
			ClienteDao clienteDao = new ClienteDao(conexao);
			List<Cliente> clientes = new ArrayList<Cliente>();
			
			if(Boolean.valueOf(request.getParameter("pendentes"))){
				clientes = clienteDao.getClientesPendentes();
				for(Cliente cliente : clientes){
					atualizarCliente(telefoneDao, enderecoDao, cliente);
				}
			}else{
				String filtro = request.getParameter("filtro");
				String parametro = request.getParameter("parametro");				
				clientes = clienteDao.getAllPorFiltro(filtro, parametro);
				for(Cliente cliente : clientes){
					atualizarCliente(telefoneDao, enderecoDao, cliente);
				}				
			}
			
			ServletOutputStream out = response.getOutputStream();
			DataOutputStream dataOut = new DataOutputStream(out);
			
			// escrita da quantidade de clientes
			dataOut.writeInt(clientes.size());			
			
			// serialização dos clientes
			for (Cliente cliente : clientes){
				cliente.serialize(dataOut);
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
		} finally {
			try {
				conexao.close();
			} catch (SQLException e) {
				e.printStackTrace();
				// tratar Exceção android
			}
		}
	}

	protected void atualizarCliente(TelefoneDao telefoneDao, EnderecoDao enderecoDao, Cliente cliente) throws SQLException {
		int idCliente = cliente.getIdCliente();
		cliente.setTelefone(telefoneDao.get(idCliente));
		cliente.setEndereco(enderecoDao.get(idCliente));
	}
	
	/*private void write(HttpServletResponse response, String resultado) throws IOException {		
		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();		
		writer.print(resultado);		
		writer.flush();
		writer.close();		
	}*/
}
