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
import br.com.cesaretransportes.dao.ContatoDao;
import br.com.cesaretransportes.modelo.Contato;

public class ProcessarAcaoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		int codigo = Integer.parseInt(request.getParameter("codigo"));
		String acao = request.getParameter("acao");

		Contato contato = new Contato();
		contato.setCodigo(request.getParameter("codigo"));
		contato.setNome(request.getParameter("nome"));
		contato.setEmail(request.getParameter("email"));
		contato.setData(request.getParameter("data"));
		contato.setMensagem(request.getParameter("mensagem"));

		Connection conexao = null;

		try {
			conexao = AbstractConnectionFactory.getConexao();
			ContatoDao dao = new ContatoDao(conexao);
			boolean status = dao.getValorEmailSalvo(codigo);

			List<Contato> listaDeContatos = new ArrayList<Contato>();

			if ("excluirEmail".equals(acao)) {
				dao.excluirContato(codigo);
				
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
					atualizarLerContatos(request, response, dao, listaDeContatos, status);
				}				
			} else if ("salvarEmail".equals(acao)) {
				dao.salvarEmail(codigo);
				atualizarLerContatos(request, response, dao, listaDeContatos,
						status);
			} else if ("responderEmail".equals(acao)) {
				request.setAttribute("reponderEmail", "responderEmail");
				request.setAttribute("contato", contato);				
				RequestDispatcher dispatcher = request.getRequestDispatcher("/ler-contato.jsp");
				dispatcher.forward(request, response);
			} else if ("marcarEmailComoNaoLido".equals(acao)) {
				dao.marcaStatusDeEmailLido(codigo, false);
				atualizarLerContatos(request, response, dao, listaDeContatos, status);
			} else if ("marcarEmailComoLido".equals(acao)) {
				dao.marcaStatusDeEmailLido(codigo, true);
				atualizarLerContatos(request, response, dao, listaDeContatos, status);
			} else if ("refreshEmail".equals(acao)) {
				atualizarLerContatos(request, response, dao, listaDeContatos, status);
			} else { 
				// lerEmail
				
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
				dao.marcaStatusDeEmailLido(codigo, true);
				contato.setEmailLido(true);
				contato.setEmailSalvo(dao.getValorEmailSalvo(codigo));
				request.setAttribute("contato", contato);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/ler-contato.jsp");
				dispatcher.forward(request, response);
			}

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

	/**
	 * Atualiza os orçamentos e redireciona para a pagina mostrar-contatos.jsp.
	 */
	private void atualizarLerContatos(HttpServletRequest request,
			HttpServletResponse response, ContatoDao dao,
			List<Contato> listaDeContatos, boolean status)
			throws ServletException, IOException {

		String todos;
		if (status) {
			todos = "sim";
			request.setAttribute("mensagem", "Caixa de Entrada - Emails Salvos");
		} else {
			todos = "nao";
			request.setAttribute("mensagem", "Caixa de Entrada");
			// sinal para mostrarContato.jsp renderizar o icone de salvar email na tela
			request.setAttribute("cxEntrada", "cxEnt");
		}

		listaDeContatos = dao.getListaDeContatos("codigo", todos);
		request.setAttribute("listaDeContatos", listaDeContatos);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/mostrar-contatos.jsp");
		dispatcher.forward(request, response);
	}
}
