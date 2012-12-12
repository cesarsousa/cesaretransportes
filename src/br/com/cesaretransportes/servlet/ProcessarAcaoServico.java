package br.com.cesaretransportes.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
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
import br.com.cesaretransportes.modelo.Servico;

public class ProcessarAcaoServico extends HttpServlet {
	private static final long serialVersionUID = 1L;
        
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		int idServico = Integer.parseInt(request.getParameter("idServico"));
		String acao = request.getParameter("acao");
		String pagina = "/mostrar-servicos.jsp";		
		
		Connection conexao = null;
		try {
			conexao = AbstractConnectionFactory.getConexao();
			ServicoDao servicoDao = new ServicoDao(conexao);
			OrcamentoDao orcamentoDao = new OrcamentoDao(conexao);
			EnderecoDao enderecoDao = new EnderecoDao(conexao);
			VeiculoDao veiculoDao = new VeiculoDao(conexao);
			TelefoneDao telefoneDao = new TelefoneDao(conexao);
			
			if("excluirServico".equals(acao)){
				servicoDao.excluir(idServico);	
				/*
				 * Se a exclusão for da pagina de busca configura uma nova busca utilizando
				 * os mesmos parametros da ultima solicitação.
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
					atualizarServicos(request, response, servicoDao, orcamentoDao, enderecoDao, veiculoDao, telefoneDao);					
				}
			}else if("lerServico".equals(acao)){								
				// testa se a origem da requisição foi busca.jsp 
				if(Boolean.valueOf(request.getParameter("buscar"))){
					request.setAttribute("buscar", true);
					String paramBusca = request.getParameter("paramBusca");
					String opcao = request.getParameter("opcao");
					String filtro = request.getParameter("filtro");
					request.setAttribute("paramBusca", paramBusca);
					request.setAttribute("opcao", opcao);
					request.setAttribute("filtro", filtro);
				}
				
				Servico servico = servicoDao.get(idServico);
				atualizarServico(servico, servicoDao, orcamentoDao, enderecoDao, veiculoDao, telefoneDao);
				request.setAttribute("servico", servico);
				pagina = "/ler-servico.jsp";
				
				RequestDispatcher dispatcher = request.getRequestDispatcher(pagina);
				dispatcher.forward(request, response);
			}else{				
				servicoDao.confirmarEntregas(Arrays.asList(servicoDao.get(idServico)));
				List<Servico> listaDeServicos = servicoDao.getAll(false);
				for(Servico servico : listaDeServicos){
					int idOrcamento = servico.getOrcamento().getIdOrcamento();
					int idVeiculo = servico.getVeiculo().getIdVeiculo();
					servico.setOrcamento(orcamentoDao.getOrcamento(idOrcamento));
					servico.getOrcamento().setEnderecos(enderecoDao.getEnderecosPorOrcamentos(idOrcamento));
					servico.setVeiculo(veiculoDao.getVeiculo(idVeiculo));					
				}
				request.setAttribute("mensagem", "Servi&ccedil;os<br/>Servi&ccedil;o confirmado com sucesso. ");
				request.setAttribute("listaDeServicos", listaDeServicos);
				RequestDispatcher dispatcher = request.getRequestDispatcher(pagina);
				dispatcher.forward(request, response);
			}			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			new CetransServletException("CNFE ", getClass().getSimpleName(), e.getMessage()).doPost(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
			new CetransServletException("SQLE ", getClass().getSimpleName(), e.getMessage()).doPost(request, response);
		} finally{
			try {
				conexao.close();
			} catch (SQLException e) {
				e.printStackTrace();
				new CetransServletException("SQLE2 ", getClass().getSimpleName(), e.getMessage()).doPost(request, response);
			}
		}
	}

	private void atualizarServico(Servico servico, ServicoDao servicoDao,
			OrcamentoDao orcamentoDao, EnderecoDao enderecoDao,	VeiculoDao veiculoDao, TelefoneDao telefoneDao) throws SQLException {
		int idOrcamento = servico.getOrcamento().getIdOrcamento();
		int idVeiculo = servico.getVeiculo().getIdVeiculo();
		servico.setOrcamento(orcamentoDao.getOrcamento(idOrcamento));
		servico.getOrcamento().getCliente().setTelefone(telefoneDao.get(servico.getOrcamento().getCliente().getIdCliente()));
		servico.getOrcamento().setEnderecos(enderecoDao.getEnderecosPorOrcamentos(idOrcamento));
		servico.setVeiculo(veiculoDao.getVeiculo(idVeiculo));		
	}

	protected void atualizarServicos(HttpServletRequest request, HttpServletResponse response,
			ServicoDao servicoDao, OrcamentoDao orcamentoDao,
			EnderecoDao enderecoDao, VeiculoDao veiculoDao, TelefoneDao telefoneDao) throws SQLException, ServletException, IOException {
		
		List<Servico> listaDeServicos = servicoDao.getAll(false);
		for(Servico servico : listaDeServicos){
			atualizarServico(servico, servicoDao, orcamentoDao, enderecoDao, veiculoDao, telefoneDao);					
		}
		request.setAttribute("mensagem", "Servi&ccedil;os");
		request.setAttribute("listaDeServicos", listaDeServicos);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/mostrar-servicos.jsp");
		dispatcher.forward(request, response);
	}
}