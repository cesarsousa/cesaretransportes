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
import br.com.cesaretransportes.dao.VeiculoDao;
import br.com.cesaretransportes.modelo.Orcamento;
import br.com.cesaretransportes.modelo.Servico;

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
			ServicoDao servicoDao = new ServicoDao(conexao);
			TelefoneDao telefoneDao = new TelefoneDao(conexao);
			VeiculoDao veiculoDao = new VeiculoDao(conexao);
			
			/*
			 * filtro de ordenacao dos orcamentos.
			 */
			String opcao = request.getParameter("opcao");
			
			/*
			 * tipo do orcamento.
			 * tipo = orcamento - carregar apenas dados de orcamento.
			 * tipo = servico - carrega dados de servico.
			 */
			String tipo = request.getParameter("tipo");
			
			String pagina;
			
			if(tipo.equals("orcamento")){
				opcao = " orcamentoLido, dataCadastro";
				List<Orcamento> listaDeOrcamentos = orcamentoDao.getListaDeOrcamentos(opcao, 0);
				for(Orcamento orcamento : listaDeOrcamentos){					
					orcamento.getCliente().setTelefone(telefoneDao.get(orcamento.getCliente().getIdCliente()));
					orcamento.setEnderecos(enderecoDao.getEnderecosPorOrcamentos(orcamento.getIdOrcamento()));					
				}
				List<Servico> listaDeServicos = servicoDao.getAll();
				
				request.setAttribute("mensagem", "Or&ccedil;amentos");
				request.setAttribute("tipoOrcamento", "Listagem de todos os or&ccedil;amentos");
				request.setAttribute("listaDeOrcamentos", listaDeOrcamentos);
				
				// filtrar os servicos
				/*request.setAttribute("listaDeOrcamentos", getOrcamentos(listaDeOrcamentos, listaDeServicos));*/
				
				pagina = "/mostrar-orcamentos.jsp";					
			}else{
				List<Servico> listaDeServicos = servicoDao.getAll(false);
				for(Servico servico : listaDeServicos){
					int idOrcamento = servico.getOrcamento().getIdOrcamento();
					int idVeiculo = servico.getVeiculo().getIdVeiculo();
					servico.setOrcamento(orcamentoDao.getOrcamento(idOrcamento));
					servico.getOrcamento().setEnderecos(enderecoDao.getEnderecosPorOrcamentos(idOrcamento));
					servico.setVeiculo(veiculoDao.getVeiculo(idVeiculo));					
				}
				request.setAttribute("mensagem", "Servi&ccedil;os");
				request.setAttribute("listaDeServicos", listaDeServicos);
				pagina = "/mostrar-servicos.jsp";
			}
			
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

	/*
	 * remove os orcamentos relacionados a servicos
	 */
	private List<Orcamento> getOrcamentos(List<Orcamento> listaDeOrcamentos, List<Servico> listaDeServicos) {
		
		for(Servico servico : listaDeServicos){
			removerDaListaOrcamento(servico.getOrcamento().getIdOrcamento(), listaDeOrcamentos);			
		}			
		
		return listaDeOrcamentos;
	}

	private void removerDaListaOrcamento(int idOrcamentoDoServico, List<Orcamento> listaDeOrcamentos) {
		Integer indice = null;
		
		/*
		 * se ao lista todos os orcamentos, ao encontrar o id referente a um servico
		 * guarda sua posicao da lista em indice. A seguir interrompe o loop e remove
		 * o indice da lista de orcamentos.
		 */
		for(int i =0;i<listaDeOrcamentos.size();i++){
			if(listaDeOrcamentos.get(i).getIdOrcamento() == idOrcamentoDoServico){
				indice = i;
				break;
			}
		}
		if(indice != null){
			int i = indice;
			listaDeOrcamentos.remove(i);
		}		
	}
}