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
import br.com.cesaretransportes.dao.VeiculoDao;
import br.com.cesaretransportes.modelo.Orcamento;

public class ProcessarAcaoOrcamentoServlet extends HttpServlet{	
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {		
		
		int idOrcamento = Integer.parseInt(request.getParameter("codigo"));		
		String acao = request.getParameter("acao");		
		
		Connection conexao = null;
		try {
			conexao = AbstractConnectionFactory.getConexao();
			OrcamentoDao orcamentoDao = new OrcamentoDao(conexao);
			VeiculoDao veiculoDao = new VeiculoDao(conexao);
			EnderecoDao enderecoDao = new EnderecoDao(conexao);
			TelefoneDao telefoneDao = new TelefoneDao(conexao);
			
			Orcamento orcamento;
			List<Orcamento> listaDeOrcamentos = new ArrayList<Orcamento>();

			if ("excluirOrcamento".equals(acao)) {
				orcamentoDao.excluirOrcamento(idOrcamento);
				
				/*
				 * Se a exclusao for da pagina de busca configura uma nova busca utilizando
				 * os mesmos parametros da ultima solicitacao.
				 */
				if(Boolean.valueOf(request.getParameter("buscar"))){
					request.setAttribute("buscar", true);
					String paramBusca = request.getParameter("paramBusca");
					String opcao = request.getParameter("opcao");
					String filtro = request.getParameter("filtro");
					request.setAttribute("paramBusca", paramBusca);
					request.setAttribute("opcao", opcao);
					request.setAttribute("filtro", filtro);				
					
					new BuscaServlet().doGet(request, response);
					
				}else{
					atualizarLerOrcamentos(request, response, orcamentoDao,	enderecoDao, listaDeOrcamentos);
				}
			} else if ("responderOrcamento".equals(acao)) {
				orcamento = orcamentoDao.getOrcamento(idOrcamento);
				orcamento.setEnderecos(enderecoDao.getEnderecosPorOrcamentos(idOrcamento));
				
				request.setAttribute("veiculosCadastrado", veiculoDao.getAll());
				request.setAttribute("reponderOrcamento", "reponderOrcamento");
				request.setAttribute("orcamento", orcamento);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/ler-orcamento.jsp");
				dispatcher.forward(request, response);
			} else if ("marcarOrcamentoComoNaoLido".equals(acao)) {
				orcamentoDao.marcaStatusDeOrcamentoLido(idOrcamento, false);
				atualizarLerOrcamentos(request, response, orcamentoDao,	enderecoDao, listaDeOrcamentos);
			} else if ("marcarOrcamentoComoLido".equals(acao)) {
				orcamentoDao.marcaStatusDeOrcamentoLido(idOrcamento, true);
				atualizarLerOrcamentos(request, response, orcamentoDao,	enderecoDao, listaDeOrcamentos);
			} else if ("refreshOrcamento".equals(acao)) {
				atualizarLerOrcamentos(request, response, orcamentoDao,	enderecoDao, listaDeOrcamentos);
			} else if ("confirmarOrcamento".equals(acao)){
				orcamentoDao.marcaStatusDeOrcamentoNaoExcluido(idOrcamento);
				atualizarLerOrcamentos(request, response, orcamentoDao, enderecoDao, listaDeOrcamentos);				
			} else { 
				// ler orcamento				
				// testa se a origem da requisicao foi busca.jsp 
				if(Boolean.valueOf(request.getParameter("buscar"))){
					request.setAttribute("buscar", true);
					String paramBusca = request.getParameter("paramBusca");
					String opcao = request.getParameter("opcao");
					String filtro = request.getParameter("filtro");
					request.setAttribute("paramBusca", paramBusca);
					request.setAttribute("opcao", opcao);
					request.setAttribute("filtro", filtro);
				}
				
				orcamento = orcamentoDao.getOrcamento(idOrcamento);
				orcamento.getCliente().setTelefone(telefoneDao.get(orcamento.getCliente().getIdCliente()));
				orcamento.setEnderecos(enderecoDao.getEnderecosPorOrcamentos(idOrcamento));
				
				orcamentoDao.marcaStatusDeOrcamentoLido(idOrcamento, true);
				orcamento.setOrcamentoLido(true);						
				request.setAttribute("orcamento", orcamento);
				request.setAttribute("veiculosCadastrado", veiculoDao.getAll());
				RequestDispatcher dispatcher = request.getRequestDispatcher("/ler-orcamento.jsp");
				dispatcher.forward(request, response);
			}
		}catch (ClassNotFoundException e) {			
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
	
	/**
	 * Atualiza os orcamentos e redireciona para a pagina mostrar-orcamentos.jsp.
	 */
	private void atualizarLerOrcamentos(HttpServletRequest request, HttpServletResponse response, 
			OrcamentoDao dao, EnderecoDao enderecoDao, List<Orcamento> listaDeOrcamentos) throws ServletException, IOException, SQLException {		
						
		listaDeOrcamentos = dao.getListaDeOrcamentos("", "dataCadastro", 1);
		for(Orcamento orcamento : listaDeOrcamentos){
			orcamento.setEnderecos(enderecoDao.getEnderecosPorOrcamentos(orcamento.getIdOrcamento()));
		}		
		
		request.setAttribute("listaDeOrcamentos", listaDeOrcamentos);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/mostrar-orcamentos.jsp");
		dispatcher.forward(request, response);	
	}	
}
