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
import br.com.cesaretransportes.dao.ServicoDao;
import br.com.cesaretransportes.dao.VeiculoDao;
import br.com.cesaretransportes.modelo.Orcamento;
import br.com.cesaretransportes.modelo.Servico;

/*
 * Esta classe recebe requisição de busca.jsp
 * Classe processa busca de orçamentos e contatos na base de dados.
 */
public class BuscaServlet extends HttpServlet {
	private static final long serialVersionUID = -6146017632388993698L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {

		String paramBusca = request.getParameter("paramBusca");
		String opcao = request.getParameter("opcao");
		String filtro = request.getParameter("filtro");
		
		Connection conexao = null;
		try {
			conexao = AbstractConnectionFactory.getConexao();
			OrcamentoDao orcamentoDao = new OrcamentoDao(conexao);
			ServicoDao servicoDao = new ServicoDao(conexao);
			EnderecoDao enderecoDao = new EnderecoDao(conexao);
			VeiculoDao veiculoDao = new VeiculoDao(conexao);

			if (paramBusca == null || paramBusca.length() == 0) {
				if ("servico".equals(opcao)) {
					request.setAttribute("mensagemServico",	"Digite um par&acirc;metro de busca");
				} else {
					request.setAttribute("mensagemOrcamento", "Digite um par&acirc;metro de busca");
				}
			} else {
				List<Servico> listaDeServicos = servicoDao.getAll();
				
				if ("servico".equals(opcao)) {
					atualizarServicos(listaDeServicos, orcamentoDao, servicoDao, enderecoDao, veiculoDao);

					request.setAttribute("listaDeServicos", filtrarServicos(listaDeServicos, paramBusca, filtro));
					request.setAttribute("resultadoServico", "Resultado da Busca");
					
				} else {
					List<Orcamento> listaDeTodosOsOrcamentos = orcamentoDao.getListaDeOrcamentos();				
					atualizarEnderecos(listaDeTodosOsOrcamentos, enderecoDao);
					
					List<Orcamento> listaDeOrcamentos = getOrcamentosQueNaoSaoServicos(listaDeTodosOsOrcamentos, listaDeServicos);

					request.setAttribute("listaDeOrcamento", filtrarOrcamentos(listaDeOrcamentos, paramBusca, filtro));
					request.setAttribute("resultadoOrcamento", "Resultado da Busca");					
				}
			}
			
			request.setAttribute("paramBusca", paramBusca);
			request.setAttribute("opcao", opcao);
			request.setAttribute("filtro", filtro);			
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/busca.jsp");
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
			} catch (SQLException e) {
				e.printStackTrace();
				new CetransServletException("SQLE2", getClass().getSimpleName(), e.getMessage()).doPost(request, response);
			}
		}
	}

	private void atualizarServicos(List<Servico> listaDeServicos, OrcamentoDao orcamentoDao, ServicoDao servicoDao, EnderecoDao enderecoDao, VeiculoDao veiculoDao) throws SQLException {
		for(Servico servico : listaDeServicos){
			int idOrcamento = servico.getOrcamento().getIdOrcamento();
			int idVeiculo = servico.getVeiculo().getIdVeiculo();
			servico.setOrcamento(orcamentoDao.getOrcamento(idOrcamento));
			servico.getOrcamento().setEnderecos(enderecoDao.getEnderecosPorOrcamentos(idOrcamento));
			servico.setVeiculo(veiculoDao.getVeiculo(idVeiculo));			
		}		
	}

	private void atualizarEnderecos(List<Orcamento> listaDeTodosOsOrcamentos, EnderecoDao enderecoDao) throws SQLException {
		for(Orcamento orcamento: listaDeTodosOsOrcamentos){
			orcamento.setEnderecos(enderecoDao.getEnderecosPorOrcamentos(orcamento.getIdOrcamento()));
		}		
	}

	/*
	 * remove os orçamentos relacionados a serviços
	 */
	private List<Orcamento> getOrcamentosQueNaoSaoServicos(List<Orcamento> listaDeOrcamentos, List<Servico> listaDeServicos) {
		
		for(Servico servico : listaDeServicos){
			removerDaListaOrcamento(servico.getOrcamento().getIdOrcamento(), listaDeOrcamentos);			
		}			
		
		return listaDeOrcamentos;
	}

	private void removerDaListaOrcamento(int idOrcamentoDoServico, List<Orcamento> listaDeOrcamentos) {
		Integer indice = null;
		
		/*
		 * se o lista todos os orçamentos, ao encontrar o id referente a um serviço
		 * guarda sua posição da lista em indice. A seguir interrompe o loop e remove
		 * o indice da lista de orçamentos.
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
	
	private List<Orcamento> filtrarOrcamentos(List<Orcamento> listaDeOrcamentos, String paramBusca, String filtro) {
		List<Orcamento> listaAuxilar = new ArrayList<Orcamento>();
		
		if ("codigo".equals(filtro)) {
			try {
				int cod = Integer.parseInt(paramBusca);
				for (Orcamento orcamento : listaDeOrcamentos) {
					if (cod == orcamento.getIdOrcamento()) {
						listaAuxilar.add(orcamento);
					}
				}
				// caso a lista não tenha o codigo solicitado retorna uma
				// lista vazia
				return listaAuxilar;
			} catch (NumberFormatException e) {
				e.printStackTrace();
				// caso uma palavra seja passada como parametro para
				// consultar codigo
				// retorna uma lista vazia
				return new ArrayList<Orcamento>();
			}
		} else {
			// filtrar de acordo com a opcao select de
			// index-sistema-interno.jsp
			if ("nome".equals(filtro)) {
				for (Orcamento orcamento : listaDeOrcamentos) {
					if (orcamento.getCliente().getNome().contains(paramBusca)) {
						listaAuxilar.add(orcamento);
					}
				}
				return listaAuxilar;
			} else if ("email".equals(filtro)) {
				for (Orcamento orcamento : listaDeOrcamentos) {
					if (orcamento.getCliente().getEmail().contains(paramBusca)) {
						listaAuxilar.add(orcamento);
					}
				}
				return listaAuxilar;
			} else if ("origem".equals(filtro)) {
				for (Orcamento orcamento : listaDeOrcamentos) {
					if (orcamento.getDetalheOrigem().contains(paramBusca)) {
						listaAuxilar.add(orcamento);
					}
				}
				return listaAuxilar;
			} else {
				for (Orcamento orcamento : listaDeOrcamentos) {
					if (orcamento.getDetalheDestino().contains(paramBusca)) {
						listaAuxilar.add(orcamento);
					}
				}
				return listaAuxilar;
			}			
		}
	}
	
	private List<Servico> filtrarServicos(List<Servico> listaDeServicos, String paramBusca, String filtro) {
		List<Servico> listaAuxiliar = new ArrayList<Servico>();
		
		if("idServico".equals(filtro)){
			try {
				int idServico = Integer.parseInt(paramBusca);
				for(Servico servico : listaDeServicos){
					if(servico.getIdServico() == idServico){
						listaAuxiliar.add(servico);
						return listaAuxiliar;
					}
				}				
			} catch (NumberFormatException e) {
				e.printStackTrace();
				// caso uma palavra seja passado como parametro para consulta,
				// retorna uma lista vazia.
				return new ArrayList<Servico>();
			}
		}else if("idOrcamento".equals(filtro)){
			try {
				int idOrcamento = Integer.parseInt(paramBusca);
				for(Servico servico : listaDeServicos){
					if(servico.getOrcamento().getIdOrcamento() == idOrcamento){
						listaAuxiliar.add(servico);
						return listaAuxiliar;
					}
				}				
			} catch (NumberFormatException e) {
				e.printStackTrace();
				// caso uma palavra seja passado como parametro para consulta,
				// retorna uma lista vazia.
				return new ArrayList<Servico>();
			}			
		}else if("idVeiculo".equals(filtro)){
			try {
				int idVeiculo = Integer.parseInt(paramBusca);
				for(Servico servico : listaDeServicos){
					if(servico.getVeiculo().getIdVeiculo() == idVeiculo){
						listaAuxiliar.add(servico);
						return listaAuxiliar;
					}
				}				
			} catch (NumberFormatException e) {
				e.printStackTrace();
				// caso uma palavra seja passado como parametro para consulta,
				// retorna uma lista vazia.
				return new ArrayList<Servico>();
			}
		}else if("nome".equals(filtro)){
			for(Servico servico : listaDeServicos){
				if(servico.getOrcamento().getCliente().getNome().contains(paramBusca)){
					listaAuxiliar.add(servico);					
				}
			}
			return listaAuxiliar;
		}else if("origem".equals(filtro)){
			for(Servico servico : listaDeServicos){
				if(servico.getOrcamento().getDetalheOrigem().contains(paramBusca)){
					listaAuxiliar.add(servico);					
				}
			}
			return listaAuxiliar;
		}else {
			// destino
			for(Servico servico : listaDeServicos){
				if(servico.getOrcamento().getDetalheDestino().contains(paramBusca)){
					listaAuxiliar.add(servico);					
				}
			}			
		}	
		return listaAuxiliar;
	}
}