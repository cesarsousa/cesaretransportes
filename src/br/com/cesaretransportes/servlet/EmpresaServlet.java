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
import br.com.cesaretransportes.dao.EmpresaDao;
import br.com.cesaretransportes.dao.EnderecoDao;
import br.com.cesaretransportes.dao.TelefoneDao;
import br.com.cesaretransportes.modelo.Empresa;

/*
 * Esta servlet recebe requisa��o da pagina cadastrar-contato.jsp
 */
public class EmpresaServlet extends HttpServlet {
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
			EmpresaDao empresaDao = new EmpresaDao(conexao);
			EnderecoDao enderecoDao = new EnderecoDao(conexao);
			TelefoneDao telefoneDao = new TelefoneDao(conexao);
			
			
			Empresa empresa = empresaDao.get();
			empresa.setEndereco(enderecoDao.getEnderecoEmpresa(empresa.getIdEmpresa()));
			empresa.setTelefones(telefoneDao.getTelefonesEmpresa(empresa.getIdEmpresa()));
			
			request.setAttribute("empresa", empresa);		
			request.setAttribute("primeiraExecucao", true);		

			RequestDispatcher dispatcher = request.getRequestDispatcher("/cadastrar-contato.jsp");
			dispatcher.forward(request, response);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();			
			new CetransServletException("CNFE", getClass().getSimpleName(), e.getMessage()).doPost(request, response);
		} catch (SQLException e) {
			e.printStackTrace();			
			new CetransServletException("SQLE", getClass().getSimpleName(), e.getMessage()).doPost(request, response);
		} finally{
			try {
				if (conexao != null) conexao.close();
			} catch (SQLException e) {
				e.printStackTrace();			
				new CetransServletException("SLE2", getClass().getSimpleName(), e.getMessage()).doPost(request, response);
			}
		}		
	}
}