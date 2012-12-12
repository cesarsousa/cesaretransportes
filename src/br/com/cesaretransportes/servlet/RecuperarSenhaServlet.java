package br.com.cesaretransportes.servlet;

import java.io.IOException;
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

import br.com.cesaretransportes.dao.AbstractConnectionFactory;
import br.com.cesaretransportes.dao.ClienteDao;
import br.com.cesaretransportes.dao.EmpresaDao;
import br.com.cesaretransportes.modelo.Cliente;
import br.com.cesaretransportes.modelo.Empresa;
import br.com.cesaretransportes.util.Email;
import br.com.cesaretransportes.util.HtmlMensagem;

public class RecuperarSenhaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String email = request.getParameter("email");
		String pagina = "/recuperarSenha.jsp";

		Connection conexao = null;
		try {

			if (isEmpty(email)) {
				request.setAttribute("mensagem", "Por favor digite um email.");
			} else {

				conexao = AbstractConnectionFactory.getConexao();
				ClienteDao clienteDao = new ClienteDao(conexao);
				Empresa empresa = new EmpresaDao(conexao).get(2);

				if (!clienteDao.clienteExiste(email)) {
					request.setAttribute("mensagem", "O email '" +  email + "' não está cadastrado em nossa base de dados.");
				} else {
					Cliente cliente = clienteDao.getCliente(email);
					Email.enviarEmail(empresa.getEmail(), empresa.getSenha(),
							cliente.getEmail(),
							"Cesare Transportes - Recuperação de senha.",
							HtmlMensagem.getMensagemRecuperarSenha(cliente));

					request.setAttribute(
							"mensagem",
							"Uma mensagem foi enviada ao "
									+ "seu e-mail<br/><br/> Verifique sua caixa de entrada "
									+ "para recuperar seu usuário e senha!<br/><br/>"
									+ "Eventualmente verifique sua caixa de spam<br/><br/>"
									+ "Atenciosamente,<br/>Equipe Cesare Transportes.");

					pagina = "/login.jsp";
				}				
			}			

			RequestDispatcher dispatcher = request.getRequestDispatcher(pagina);
			dispatcher.forward(request, response);

		} catch (SQLException e) {
			e.printStackTrace();
			new CetransServletException("SQLE", getClass().getSimpleName(),
					e.getMessage()).doPost(request, response);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			new CetransServletException("CNFE", getClass().getSimpleName(),
					e.getMessage()).doPost(request, response);
		} catch (AddressException e) {
			e.printStackTrace();
			new CetransServletException("AE", getClass().getSimpleName(),
					e.getMessage()).doPost(request, response);
		} catch (SendFailedException e) {
			e.printStackTrace();
			new CetransServletException("SFE", getClass().getSimpleName(),
					e.getMessage()).doPost(request, response);
		} catch (MessagingException e) {
			e.printStackTrace();
			new CetransServletException("ME", getClass().getSimpleName(),
					e.getMessage()).doPost(request, response);
		} finally {
			try {
				if (conexao != null) conexao.close();
			} catch (SQLException e) {
				e.printStackTrace();
				new CetransServletException("SQLE2",
						getClass().getSimpleName(), e.getMessage()).doPost(
						request, response);
			}
		}
	}

	private boolean isEmpty(String email) {
		if (email.isEmpty())
			return true;
		if ("EMAIL".equals(email))
			return true;
		return false;
	}
}
