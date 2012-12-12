package br.com.cesaretransportes.servlet;

import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.internet.AddressException;
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
import br.com.cesaretransportes.modelo.Cliente;
import br.com.cesaretransportes.modelo.Empresa;
import br.com.cesaretransportes.util.CesareUtil;
import br.com.cesaretransportes.util.Email;
import br.com.cesaretransportes.util.HtmlMensagem;

public class ContatoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession sessao = request.getSession();
		Cliente cliente = (Cliente) sessao.getAttribute("cliente");

		String nome = request.getParameter("nome");
		String email = request.getParameter("email");
		String mensagem = request.getParameter("mensagem");

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
			request.setAttribute("primeiraExecucao", true);
			

			if (!"".equals(nome) && !"".equals(email) && emailValido(email)
					&& !"".equals(mensagem)) {

				int codigoCliente = cliente == null ? 0 : cliente.getIdCliente();

				String data = CesareUtil.getDataDoSistema();

				// notificacao ao cliente do cadastro do contato
				Email.enviarEmail(
						empresa.getEmail(),
						empresa.getSenha(),
						email,
						"Cesare Transportes - confirmação de Recebimento de Email",
						HtmlMensagem.getMensagemNotificacaoCliente(nome, "email"));

				// notificacao para a empresa de um novo email
				Email.enviarEmail(
						empresa.getEmail(),
						empresa.getSenha(), 
						empresa.getEmail(),
						"CeTrans - Novo contato de email", 
						HtmlMensagem.getMensagemNotificacaoEmpresa(nome, data, codigoCliente, email, mensagem, "contato"));

				request.setAttribute("mensagem", "Obrigado,\n\nSeu email foi enviado com sucesso, "
								+ "em breve entraremos em contato!\n\nAtenciosamente,\nCesare Transportes Ltda.");
				RequestDispatcher dispather = request.getRequestDispatcher("/resposta-de-solicitacao.jsp");
				dispather.forward(request, response);

			} else {
				verificarCamposPreenchidos(nome, email, mensagem, request, response);
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
			new CetransServletException("UHE", getClass().getSimpleName(), e.getMessage())
					.doPost(request, response);
		} catch (RuntimeException e) {
			e.printStackTrace();
			new CetransServletException("RTE", getClass().getSimpleName(), e.getMessage())
					.doPost(request, response);
		} catch (AddressException e) {
			e.printStackTrace();
			new CetransServletException("AE", getClass().getSimpleName(), e.getMessage())
					.doPost(request, response);
		} catch (SendFailedException e) {
			e.printStackTrace();
			new CetransServletException("SFE", getClass().getSimpleName(), e.getMessage())
					.doPost(request, response);
		} catch (MessagingException e) {
			e.printStackTrace();
			new CetransServletException("ME", getClass().getSimpleName(), e.getMessage())
					.doPost(request, response);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			new CetransServletException("CNFE", getClass().getSimpleName(), e.getMessage())
					.doPost(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
			new CetransServletException("SQLE", getClass().getSimpleName(), e.getMessage())
					.doPost(request, response);
		} finally {
			try {
				conexao.close();
			} catch (SQLException e) {
				e.printStackTrace();
				new CetransServletException("SQLE2", getClass().getSimpleName(), e.getMessage())
						.doPost(request, response);
			}
		}
	}

	private boolean emailValido(String email) {
		return email.matches("[a-zA-Z0-9._%-]+@[a-zA-Z0-9._-]+\\.[a-z]{2,4}");
	}

	private void verificarCamposPreenchidos(String nome, String email,
			String mensagem, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		if ("".equals(nome)) {
			request.setAttribute("msgNome",
					"O campo nome deve ser preenchido !");
		} else {
			request.setAttribute("nome", nome);
		}

		if ("".equals(email)) {
			request.setAttribute("msgEmail",
					"O campo email deve ser preenchido !");
		} else {
			request.setAttribute("email", email);
		}

		if (!emailValido(email)) {
			request.setAttribute("msgEmail",
					"O campo email possui formato invalido !");
		} else {
			request.setAttribute("email", email);
		}

		if ("".equals(mensagem)) {
			request.setAttribute("msgMensagem",
					"O campo mensagem deve ser preenchido !");
		} else {
			request.setAttribute("mensagem", mensagem);
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher("/cadastrar-contato.jsp");
		dispatcher.forward(request, response);
	}
}