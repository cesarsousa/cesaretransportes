package br.com.cesaretransportes.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.cesaretransportes.dao.AbstractConnectionFactory;
import br.com.cesaretransportes.dao.EnderecoDao;
import br.com.cesaretransportes.dao.OrcamentoDao;
import br.com.cesaretransportes.dao.ServicoDao;
import br.com.cesaretransportes.dao.TelefoneDao;
import br.com.cesaretransportes.modelo.Orcamento;
import br.com.cesaretransportes.modelo.Servico;
import br.com.cesaretransportes.util.AcaoCliente;
import br.com.cesaretransportes.util.MSG;

/**
 * Servlet implementation class ClienteOpcaoConsultaServlet
 */
public class ClienteOpcaoConsultaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		String pagina = "/resposta-de-solicitacao.jsp";
		
		int opcao = Integer.parseInt(request.getParameter("opcao"));
		int idCliente = Integer.parseInt(request.getParameter("idCliente"));
		
		Connection conexao = null;
		try {
			conexao = AbstractConnectionFactory.getConexao();
			OrcamentoDao orcamentoDao = new OrcamentoDao(conexao);
			ServicoDao servicoDao = new ServicoDao(conexao);
			TelefoneDao telefoneDao = new TelefoneDao(conexao);
			EnderecoDao enderecoDao = new EnderecoDao(conexao);
			
			switch (opcao) {
			case AcaoCliente.LISTAR_ORCAMENTOS:
				List<Orcamento> orcamentos = orcamentoDao.getListaDeOrcamentos(idCliente, "dataCadastro", 1);
				for(Orcamento orcamento : orcamentos){					
					orcamento.getCliente().setTelefone(telefoneDao.get(orcamento.getCliente().getIdCliente()));
					orcamento.setEnderecos(enderecoDao.getEnderecosPorOrcamentos(orcamento.getIdOrcamento()));					
				}
				
				atualizar(orcamentos, servicoDao);
				
				pagina = "/cliente-opcao-consulta.jsp";
				request.setAttribute("titulo", orcamentos.size() == 0 ? "Voc&ecirc; n&atilde;o possui or&ccedil;amentos no momento" : "Or&ccedil;amentos");
				request.setAttribute("exibirOrcamentos", true);
				request.setAttribute("orcamentos", orcamentos);				
				break;
				
			case AcaoCliente.LISTAR_SERVICOS:
				List<Servico> servicos = servicoDao.getServicosPorCliente(idCliente);
				pagina = "/cliente-opcao-consulta.jsp";
				request.setAttribute("titulo", servicos.size() == 0 ? "Voc&ecirc; n&atilde;o possui servi&ccedil;os no momento" : "Servi&ccedil;os");
				request.setAttribute("exibirServicos", true);
				request.setAttribute("servicos", servicos);				
				break;

			default:
				request.setAttribute("mensagem", MSG.ERRO_CLIENTE);
				break;
			}
			
			RequestDispatcher dispatcher = request.getRequestDispatcher(pagina);
			dispatcher.forward(request, response);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(conexao != null)
				try {
					conexao.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}		
	}

	/**
	 * percorre os orçamentos excluidos todos que possuam uma ordem de serviço
	 * relacionada.
	 * @param orcamentos
	 * @param servicoDao 
	 * @throws SQLException 
	 */
	private void atualizar(List<Orcamento> orcamentos, ServicoDao servicoDao) throws SQLException {		
		for(Servico servico : servicoDao.getAll()){
			removerDaListaOrcamento(servico.getOrcamento().getIdOrcamento(), orcamentos);			
		}		
	}

	private void removerDaListaOrcamento(int idOrcamentoDoServico, List<Orcamento> orcamentos) {
Integer indice = null;
		
		/*
		 * se o lista todos os orçamentos, ao encontrar o id referente a um serviço
		 * guarda sua posição da lista em indice. A seguir interrompe o loop e remove
		 * o indice da lista de orçamentos.
		 */
		for(int i =0;i<orcamentos.size();i++){
			if(orcamentos.get(i).getIdOrcamento() == idOrcamentoDoServico){
				indice = i;
				break;
			}
		}
		if(indice != null){
			int i = indice;
			orcamentos.remove(i);
		}		
	}	
}