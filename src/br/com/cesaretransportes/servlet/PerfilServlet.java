package br.com.cesaretransportes.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.cesaretransportes.dao.AbstractConnectionFactory;
import br.com.cesaretransportes.dao.ClienteDao;
import br.com.cesaretransportes.dao.EnderecoDao;
import br.com.cesaretransportes.dao.TelefoneDao;
import br.com.cesaretransportes.modelo.Cliente;
import br.com.cesaretransportes.modelo.Cliente.TipoDoDocumento;

/**
 * Servlet implementation class Perfil
 */
public class PerfilServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
          
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		int idCliente = Integer.parseInt(request.getParameter("idCliente"));
		
		Connection conexao = null;
		try {
			conexao = AbstractConnectionFactory.getConexao();
			ClienteDao clienteDao = new ClienteDao(conexao);
			TelefoneDao telefoneDao = new TelefoneDao(conexao);
			EnderecoDao enderecoDao = new EnderecoDao(conexao);
			
			Cliente cliente = clienteDao.getCliente(idCliente);
			cliente.setTelefone(telefoneDao.get(idCliente));
			cliente.setEndereco(enderecoDao.get(idCliente));
			
			request.setAttribute("usuario", cliente.getEmail());
			request.setAttribute("senha1", cliente.getSenha());
			request.setAttribute("senha2", cliente.getSenha());
			request.setAttribute("nome", cliente.getNome());
			if(cliente.getTipoDoDocumento() == TipoDoDocumento.CPF) request.setAttribute("msgCpf", true);			
			request.setAttribute("numDoc", cliente.getNumeroDoDocumento());
			request.setAttribute("telDdd", cliente.getTelefone().getDdd());
			request.setAttribute("telNumero", cliente.getTelefone().getNumero());
			request.setAttribute("telComplemento", cliente.getTelefone().getComplemento());
			request.setAttribute("enderecoAtual", cliente.getEndereco().getLocalizacao());
			request.setAttribute("cidade", cliente.getEndereco().getCidade());
			request.setAttribute("estado", cliente.getEndereco().getEstado());
			
			// sinaliza para a pagina de cadastro de cliente para carregar opções de editar o perfil.
			request.setAttribute("editarCliente", true);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/cadastrar-cliente.jsp");
			dispatcher.forward(request, response);			
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(conexao != null)
				try {
					conexao.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}		
	}
}