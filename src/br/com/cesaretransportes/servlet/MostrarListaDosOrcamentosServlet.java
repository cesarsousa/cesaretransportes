package br.com.cesaretransportes.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.cesaretransportes.dao.AbstractConnectionFactory;
import br.com.cesaretransportes.dao.EnderecoDao;
import br.com.cesaretransportes.dao.OrcamentoDao;
import br.com.cesaretransportes.dao.TelefoneDao;
import br.com.cesaretransportes.modelo.Orcamento;

public class MostrarListaDosOrcamentosServlet extends HttpServlet{

	private static final long serialVersionUID = -6203090914107065305L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Connection conexao = null;
		try{
			conexao = AbstractConnectionFactory.getConexao();
			OrcamentoDao orcamentoDao = new OrcamentoDao(conexao);
			EnderecoDao enderecoDao = new EnderecoDao(conexao);
			TelefoneDao telefoneDao = new TelefoneDao(conexao);
			
			String opcao = request.getParameter("opcao");			
			
			String pagina = "/mostrar-orcamentos.jsp";
			
			List<Orcamento> listaDeOrcamentos = new ArrayList<Orcamento>();
			 		
			if("todos".equals(opcao)){								
				request.setAttribute("tipoOrcamento", "Listagem de todos os or&ccedil;amentos");
			}else if("naolido".equals(opcao)){
				request.setAttribute("tipoOrcamento", "Listagem dos or&ccedil;amentos n&atilde;o lidos");
			}else if("lido".equals(opcao)){
				request.setAttribute("tipoOrcamento", "Listagem dos or&ccedil;amentos lidos");
			}else if("excluido".equals(opcao)){
				request.setAttribute("tipoOrcamento", "Listagem dos or&ccedil;amentos exclu&iacute;dos");
			}else if("naoexcluido".equals(opcao)){
				request.setAttribute("tipoOrcamento", "Listagem dos or&ccedil;amentos n&atilde;o exclu&iacute;dos");
			}
			
			listaDeOrcamentos = orcamentoDao.getListaDeOrcamentos(opcao, "dataCadastro", 1);			
			for(Orcamento orcamento : listaDeOrcamentos){					
				orcamento.getCliente().setTelefone(telefoneDao.get(orcamento.getCliente().getIdCliente()));
				orcamento.setEnderecos(enderecoDao.getEnderecosPorOrcamentos(orcamento.getIdOrcamento()));					
			}
			
			request.setAttribute("mensagem", "Or&ccedil;amentos");
			request.setAttribute("listaDeOrcamentos", listaDeOrcamentos);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher(pagina);
			dispatcher.forward(request, response);
			
		} catch (ClassNotFoundException e) {			
			e.printStackTrace();
			new CetransServletException("CNFE", getClass().getSimpleName(), e.getMessage()).doPost(request, response);
		} catch (RuntimeException e) {			
			e.printStackTrace();
			new CetransServletException("RTE", getClass().getSimpleName(), e.getMessage()).doPost(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
			new CetransServletException("SQLE", getClass().getSimpleName(), e.getMessage()).doPost(request, response);
		} finally{
			try {
				conexao.close();
			} catch (SQLException e) {
				e.printStackTrace();
				new CetransServletException("SQLE2", getClass().getSimpleName(), e.getMessage()).doPost(request, response);
			}
		}
	}
}