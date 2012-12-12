package br.com.cesaretransportes.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.internet.AddressException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.cesaretransportes.dao.AbstractConnectionFactory;
import br.com.cesaretransportes.dao.ClienteDao;
import br.com.cesaretransportes.dao.EmpresaDao;
import br.com.cesaretransportes.dao.EnderecoDao;
import br.com.cesaretransportes.dao.OrcamentoDao;
import br.com.cesaretransportes.dao.ServicoDao;
import br.com.cesaretransportes.dao.TelefoneDao;
import br.com.cesaretransportes.dao.VeiculoDao;
import br.com.cesaretransportes.modelo.Empresa;
import br.com.cesaretransportes.modelo.Orcamento;
import br.com.cesaretransportes.modelo.Servico;
import br.com.cesaretransportes.modelo.Veiculo;
import br.com.cesaretransportes.util.CesareUtil;
import br.com.cesaretransportes.util.Email;
import br.com.cesaretransportes.util.HtmlMensagem;

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
			ServicoDao servicoDao = new ServicoDao(conexao);
			TelefoneDao telefoneDao = new TelefoneDao(conexao);
			ClienteDao clienteDao = new ClienteDao(conexao);
			EmpresaDao empresaDao = new EmpresaDao(conexao);
			
			Empresa empresa = empresaDao.get();
			
			Orcamento orcamento;
			List<Orcamento> listaDeOrcamentos = new ArrayList<Orcamento>();

			if ("excluirOrcamento".equals(acao)) {
				orcamentoDao.excluirOrcamento(idOrcamento);
				
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
					atualizarLerOrcamentos(request, response, orcamentoDao,	enderecoDao, servicoDao, listaDeOrcamentos);
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
				atualizarLerOrcamentos(request, response, orcamentoDao,	enderecoDao, servicoDao, listaDeOrcamentos);
			} else if ("marcarOrcamentoComoLido".equals(acao)) {
				orcamentoDao.marcaStatusDeOrcamentoLido(idOrcamento, true);
				atualizarLerOrcamentos(request, response, orcamentoDao,	enderecoDao, servicoDao, listaDeOrcamentos);
			} else if ("refreshOrcamento".equals(acao)) {
				atualizarLerOrcamentos(request, response, orcamentoDao,	enderecoDao, servicoDao, listaDeOrcamentos);
			} else if ("confirmarServico".equals(acao)){
				String pagina = "/index-sistema-interno.jsp";
				orcamento = orcamentoDao.getOrcamento(idOrcamento);
				orcamento.getCliente().setTelefone(telefoneDao.get(orcamento.getCliente().getIdCliente()));
				orcamento.setEnderecos(enderecoDao.getEnderecosPorOrcamentos(idOrcamento));				
				
				String dataPrevEntrega = request.getParameter("dataPrevEntrega");
				String valor = request.getParameter("valor");
				Veiculo veiculo = veiculoDao.getVeiculo(Integer.parseInt(request.getParameter("idVeiculo")));
								
				request.setAttribute("dataPrevEntrega", dataPrevEntrega);				
				request.setAttribute("valor", valor);			
				
				if(dadosSaoValidos(dataPrevEntrega, valor, request)){					
					try {
						Servico servico = new Servico(orcamento, veiculo, new BigDecimal(valor.replace(",", ".")), CesareUtil.getData(getDataNoPadrao(dataPrevEntrega)), Calendar.getInstance());
						int codigoServico = servicoDao.cadastrar(servico);
						
						String cliente = clienteDao.getClientePorOrcamento(idOrcamento);
						
						if("".equals(cliente)){
							request.setAttribute("mensagem", "Novo servico cod:" + String.valueOf(codigoServico) + ". Nao foi possivel" +
									" notificar cliente do servico.");							
						}else{
							String[] dadosCliente = cliente.split(";");
							/*
							 * email de notificação para o cliente do novo serviço.
							 */
							Email.enviarEmail(empresa.getEmail(), empresa.getSenha(), dadosCliente[0], 
									"Cetrans - Confirmacao de Ordem de servico", 
									HtmlMensagem.getMensagemConfirmacaoServico(codigoServico, idOrcamento, dadosCliente[1]));
							
							request.setAttribute("mensagem", "Novo servico cod:" + String.valueOf(codigoServico));	
						}					
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
						pagina = "/ler-orcamento.jsp";
						request.setAttribute("erroValidadeData", true);
					}				
				}else{
					pagina = "/ler-orcamento.jsp";
				}
				
				request.setAttribute("orcamento", orcamento);
				request.setAttribute("veiculosCadastrado", veiculoDao.getAll());
				RequestDispatcher dispatcher = request.getRequestDispatcher(pagina);
				dispatcher.forward(request, response);				
				
			} else { 
				// ler orçamento				
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
		}catch (AddressException e) {
			e.printStackTrace();			
			new CetransServletException("AE", getClass().getSimpleName(), e.getMessage()).doPost(request, response);
		} catch (SendFailedException e) {
			e.printStackTrace();			
			new CetransServletException("SFE", getClass().getSimpleName(), e.getMessage()).doPost(request, response);
		} catch (MessagingException e) {
			e.printStackTrace();			
			new CetransServletException("ME", getClass().getSimpleName(), e.getMessage()).doPost(request, response);
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

	private String getDataNoPadrao(String dataPrevEntrega) {
		// retorna a data no formato yyyyMMaa
		return dataPrevEntrega.substring(6) + dataPrevEntrega.substring(3, 5) + dataPrevEntrega.substring(0, 2);
	}

	private boolean dadosSaoValidos(String data, String valor, HttpServletRequest request) {
		boolean resultado = true;		
		if(!data.matches("\\d{2}/\\d{2}/\\d{4}")){
			resultado = false;			
			request.setAttribute("erroData", true);
		}
		
		if(!valor.matches("(\\d+,\\d{2})|(\\d+)")){
			resultado = false;
			request.setAttribute("erroValor", true);			
		}
		
		return resultado;
	}

	/**
	 * Atualiza os orçamentos e redireciona para a pagina mostrar-orcamentos.jsp.
	 */
	private void atualizarLerOrcamentos(HttpServletRequest request, HttpServletResponse response, 
			OrcamentoDao dao, EnderecoDao enderecoDao, ServicoDao servicoDao,
			List<Orcamento> listaDeOrcamentos) throws ServletException, IOException, SQLException {		
		
				
		listaDeOrcamentos = dao.getListaDeOrcamentos("idOrcamento", 1);
		for(Orcamento orcamento : listaDeOrcamentos){
			orcamento.setEnderecos(enderecoDao.getEnderecosPorOrcamentos(orcamento.getIdOrcamento()));
		}		
		
		// getOrcamentos() remove serviços da lista de orçamentos.
		request.setAttribute("listaDeOrcamentos", getOrcamentos(listaDeOrcamentos, servicoDao.getAll(false)));		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/mostrar-orcamentos.jsp");
		dispatcher.forward(request, response);	
	}
	
	/*
	 * remove os orçamentos relacionados a serviços
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
}
