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
import br.com.cesaretransportes.validacao.ValidacaoConta;

public class ConfiguracaoDeContaServlet extends HttpServlet {
	
	private static final long serialVersionUID = -9013908639928525729L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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
			request.setAttribute("mensagemConta", "Alteração dos dados cadastrais da empresa");
			

			RequestDispatcher dispatcher = request.getRequestDispatcher("/configuracoesConta.jsp");
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
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection conexao = null;
		
		try {
			conexao = AbstractConnectionFactory.getConexao();
			EmpresaDao empresaDao = new EmpresaDao(conexao);
			EnderecoDao enderecoDao = new EnderecoDao(conexao);
			TelefoneDao telefoneDao = new TelefoneDao(conexao);	
			
			String pagina = "/index-sistema-interno.jsp";
			Empresa empresa = ValidacaoConta.criarEmpresa(request);
			
			if(ValidacaoConta.validada(empresa, request)){
				empresaDao.atualizar(empresa, enderecoDao, telefoneDao);
				request.setAttribute("mensagem", "Dados da empresa alterados com sucesso!");			
			}else{
				request.setAttribute("empresa", empresa);
				pagina = "/configuracoesConta.jsp";
			}
			
			RequestDispatcher dispatcher = request.getRequestDispatcher(pagina);
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
