package br.com.cesaretransportes.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.cesaretransportes.dao.AbstractConnectionFactory;
import br.com.cesaretransportes.dao.EmpresaDao;
import br.com.cesaretransportes.dao.EnderecoDao;
import br.com.cesaretransportes.dao.TelefoneDao;
import br.com.cesaretransportes.modelo.Empresa;
import br.com.cesaretransportes.util.MSG;

public class LoginServlet extends HttpServlet {	
	private static final long serialVersionUID = 2494418281704963586L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
					
		Connection conexao = null;
		try {
						
			conexao = AbstractConnectionFactory.getConexao();
			EmpresaDao empresaDao = new EmpresaDao(conexao);
			TelefoneDao telefoneDao = new TelefoneDao(conexao);
			EnderecoDao enderecoDao = new EnderecoDao(conexao);
			
			String pagina = "/resposta-de-solicitacao.jsp";
			String usuario = request.getParameter("usuario");
			String senha = request.getParameter("senha");
			
			Empresa empresa = empresaDao.get(usuario, senha);			
			
			if (empresa == null) {
				request.setAttribute("mensagem", MSG.LOGIN.toString());
				pagina = "/login.jsp";
			} else {			
				empresa.setEndereco(enderecoDao.getEnderecoEmpresa(empresa.getIdEmpresa()));
				empresa.setTelefones(telefoneDao.getTelefonesEmpresa(empresa.getIdEmpresa()));
				
				HttpSession session = request.getSession();
				session.setAttribute("empresa", empresa);		
				
				pagina = "/index-sistema-interno.jsp";				
			}
			
			
			/*
			 
			Cliente cliente = clienteDao.getCliente(usuario, senha);  
			
		 	if (cliente == null) {
				request.setAttribute("mensagem", MSG.LOGIN.toString());
				pagina = "/login.jsp";
			} else {				
				if(cliente.getTipoCliente().equals("A")){				
					
					
					Empresa empresa = empresaDao.get();
					empresa.setEndereco(enderecoDao.getEnderecoEmpresa(empresa.getIdEmpresa()));
					empresa.setTelefones(telefoneDao.getTelefonesEmpresa(empresa.getIdEmpresa()));
					
					HttpSession session = request.getSession();
					session.setAttribute("empresa", empresa);
					
					
					pagina = "/index-sistema-interno.jsp";
				}else{
					if(cliente.getDataExclusao() != null){
						request.setAttribute("cliente", cliente);
						pagina = "/recadastramentoCliente.jsp";
					}else{
						cliente.setTelefone(telefoneDao.get(cliente.getIdCliente()));
						cliente.setEndereco(enderecoDao.get(cliente.getIdCliente()));
						cliente.setOrcamentos(orcamentoDao.getListaDeOrcamentos(cliente.getIdCliente(), "idOrcamento", 1));
						
						HttpSession session = request.getSession();
						session.setAttribute("cliente", cliente);
						pagina = "/index.jsp";						
					}
				}				
			}*/			
			
			RequestDispatcher dispatcher = request.getRequestDispatcher(pagina);
			dispatcher.forward(request, response);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			new CetransServletException("CNFE", getClass().getSimpleName(), e.getMessage()).doPost(request, response);			
		} catch (SQLException e) {
			e.printStackTrace();
			new CetransServletException("SQLE", getClass().getSimpleName(), e.getMessage()).doPost(request, response);
		} finally {
			try {
				conexao.close();
			} catch (Exception e) {
				e.printStackTrace();
				new CetransServletException("SQLE2", getClass().getSimpleName(), e.getMessage()).doPost(request, response);
			}
		}			
	}
}