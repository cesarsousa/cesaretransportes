package br.com.cesaretransportes.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;

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
import br.com.cesaretransportes.util.MSG;


public class RecadastrarClienteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		this.doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		Connection conexao = null;
		
		try {
			conexao = AbstractConnectionFactory.getConexao();			
			ClienteDao clienteDao = new ClienteDao(conexao);
			Empresa empresa = new EmpresaDao(conexao).get();
			
			String senha1 = request.getParameter("senha1");
			String senha2 = request.getParameter("senha2");
			String nome = request.getParameter("nome");
			String email = request.getParameter("email");
			String pagina;
			
			if(senha1.equals(senha2)){
				clienteDao.recadastrar(email, senha1, Calendar.getInstance());
				
				// atualiza o cliente com o id.
				Cliente cliente = clienteDao.getCliente(email);
				
				/*
				 *  notificacao para o cliente do recadastramento
				 */
				Email.enviarEmail(
						empresa.getEmail(), empresa.getSenha(),	cliente.getEmail(),
						"Cesare Transportes - confirmacao de Cadastro",
						HtmlMensagem.getMensagemNotificacaoCliente(cliente.getNome(), "cadastro"));

				/*
				 * Email de notificação a empresa de um novo cliente cadastrado.
				 */
				Email.enviarEmail(empresa.getEmail(), empresa.getSenha(), empresa.getEmail(),
						"Cesare Transportes - Novo cliente cadastrado",
						HtmlMensagem.getMensagemEnviarEmail(cliente.getIdCliente(),	cliente.getNome()));				
				
				request.setAttribute("mensagem", MSG.CLIENTE_CADASTRADO.toString().replace("NOME", nome));
				pagina = "/resposta-de-solicitacao.jsp";
			}else{
				request.setAttribute("erroSenha", true);
				pagina = "/recadastramentoCliente.jsp";
			}
			
			RequestDispatcher dispatcher = request.getRequestDispatcher(pagina);
			dispatcher.forward(request, response);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			new CetransServletException("CNFE", getClass().getSimpleName(), e.getMessage()).doPost(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
			new CetransServletException("SQLE", getClass().getSimpleName(), e.getMessage()).doPost(request, response);
		} catch (AddressException e) {
			e.printStackTrace();
			new CetransServletException("AE", getClass().getSimpleName(), e.getMessage()).doPost(request, response);
		} catch (SendFailedException e) {
			e.printStackTrace();
			new CetransServletException("SFE", getClass().getSimpleName(), e.getMessage()).doPost(request, response);
		} catch (MessagingException e) {
			e.printStackTrace();
			new CetransServletException("ME", getClass().getSimpleName(), e.getMessage()).doPost(request, response);
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