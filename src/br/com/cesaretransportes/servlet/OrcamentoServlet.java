package br.com.cesaretransportes.servlet;

import java.io.IOException;
import java.net.UnknownHostException;
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
import javax.servlet.http.HttpSession;

import br.com.cesaretransportes.dao.AbstractConnectionFactory;
import br.com.cesaretransportes.dao.ClienteDao;
import br.com.cesaretransportes.dao.EmpresaDao;
import br.com.cesaretransportes.dao.EnderecoDao;
import br.com.cesaretransportes.dao.OrcamentoDao;
import br.com.cesaretransportes.modelo.Cliente;
import br.com.cesaretransportes.modelo.Empresa;
import br.com.cesaretransportes.modelo.Endereco;
import br.com.cesaretransportes.modelo.Endereco.StatusEndereco;
import br.com.cesaretransportes.modelo.Orcamento;
import br.com.cesaretransportes.util.CesareUtil;
import br.com.cesaretransportes.util.Email;
import br.com.cesaretransportes.util.HtmlMensagem;
import br.com.cesaretransportes.validacao.ValidacaoOrcamento;

public class OrcamentoServlet extends HttpServlet {
	private static final long serialVersionUID = 536626979486013956L;
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Connection conexao = null;
		try {
			conexao = AbstractConnectionFactory.getConexao();
			OrcamentoDao orcamentoDao = new OrcamentoDao(conexao);
			EmpresaDao empresaDao = new EmpresaDao(conexao);
			EnderecoDao enderecoDao = new EnderecoDao(conexao);
			ClienteDao clienteDao = new ClienteDao(conexao);
			
			Orcamento orcamento = new Orcamento();
			
			Empresa empresa = empresaDao.get(2);
			
			/*
			 *  requisicao da pagina cadastrar-orcamento.jsp
			 */
			
			/*HttpSession sessao = request.getSession();
			Cliente cliente = (Cliente) sessao.getAttribute("cliente");*/
			
			/*boolean clienteAtivo = false;
			if(cliente != null){
				clienteAtivo = clienteDao.clienteAtivo(cliente.getEmail(), cliente.getSenha());
				if(!clienteAtivo){
					new LogoutServlet().doPost(request, response);
				}				
			}			
			*/
			
			
			
			String nome = request.getParameter("nome");
			String email = request.getParameter("email");
			String ddd = request.getParameter("ddd");
			String telefone = request.getParameter("telefone");			
			String cidadeOrigem = request.getParameter("origem");
			String estadoOrigem = request.getParameter("estadoOrigem");
			String enderecoOrigem = request.getParameter("enderecoOrigem");
			String cidadeDestino = request.getParameter("destino");
			String estadoDestino = request.getParameter("estadoDestino");
			String enderecoDestino = request.getParameter("enderecoDestino");
			String peso = request.getParameter("peso");
			String dimensao = request.getParameter("dimensao");
			String mensagem = request.getParameter("mensagem");
			
			System.out.println(nome);
			System.out.println(email);
			System.out.println(ddd);
			System.out.println(telefone);
			System.out.println(enderecoOrigem);
			System.out.println(cidadeOrigem);
			System.out.println(estadoOrigem);
			System.out.println(enderecoDestino);
			System.out.println(cidadeDestino);
			System.out.println(estadoDestino);
			System.out.println(peso);
			System.out.println(dimensao);
			System.out.println(mensagem);

			String pagina = "/index.jsp";

			if (ValidacaoOrcamento.orcamentoEhValido(nome, email, ddd, telefone, cidadeOrigem, enderecoOrigem, cidadeDestino, enderecoDestino, peso, dimensao) /*&& clienteAtivo*/) {
									
				orcamento.setCliente(new Cliente());
				orcamento.setPeso(peso);
				orcamento.setDimensao(dimensao);
				orcamento.setMensagem(mensagem);					
				orcamento.setDataCadastro(Calendar.getInstance());
				
				int idOrcamento = orcamentoDao.cadastrarOrcamento(orcamento);					
				orcamento.setIdOrcamento(idOrcamento);
				
				Empresa e = new Empresa();
				e.setIdEmpresa(0);
				Cliente c = new Cliente();
				c.setIdCliente(0);
				
				Endereco endereco = new Endereco(e, c, orcamento, cidadeOrigem, estadoOrigem, enderecoOrigem, StatusEndereco.ORIGEM);
				enderecoDao.cadastrarEndereco(endereco);
				endereco = new Endereco(e, c, orcamento, cidadeDestino, estadoDestino, enderecoDestino, StatusEndereco.DESTINO);
				enderecoDao.cadastrarEndereco(endereco);				
									
				/*
				 *  notificacao para o cliente do recebimento do orcamento
				 */
				Email.enviarEmail(
						empresa.getEmail(), empresa.getSenha(),	orcamento.getCliente().getEmail(),
						"Cesare Transportes - confirmacao de Recebimento Orcamento",
						HtmlMensagem.getMensagemNotificacaoCliente(orcamento.getCliente().getNome(), "or&ccedil;amento"));
				
				/*
				 *  notificacao para a empresa de um novo orcamento recebido
				 */
				Email.enviarEmail(empresa.getEmail(), empresa.getSenha(), empresa.getEmail(), 
						"CeTrans - Novo Orcamento numero: " + orcamento.getIdOrcamento(), 
						HtmlMensagem.getMensagemNotificacaoEmpresa(
								orcamento.getCliente().getNome(), 
								CesareUtil.formatarData(orcamento.getDataCadastro(), "dd/MM/yyyy"), 
								orcamento.getIdOrcamento(), orcamento.getCliente().getEmail(), orcamento.getMensagem(), "or&ccedil;amento"));

				request.setAttribute("mensagem", "Obrigado,\n\nSeu or&ccedil;amento foi enviado com sucesso e "
								+ "em breve entraremos em contato!\n\nAtenciosamente,\nCesare Transportes Ltda.");
				
				pagina = "/resposta-de-solicitacao.jsp";
				RequestDispatcher dispacher = request.getRequestDispatcher(pagina);
				dispacher.forward(request, response);
			} else {					
				ValidacaoOrcamento.verificarCamposPreenchidos(nome, email, ddd, telefone, cidadeOrigem, enderecoOrigem, cidadeDestino, enderecoDestino, peso, dimensao, mensagem, request);
				pagina = "/cadastrar-orcamento.jsp";				
				RequestDispatcher dispacher = request.getRequestDispatcher(pagina);
				dispacher.forward(request, response);
			}
			
			
			
			
						
			/*if ("responderOrcamento".equals(acao)) {
				
				 *  Requisicao da pagina ler-orcamento.jsp
				 
				
				int idOrcamento = Integer.parseInt(request.getParameter("codigo"));
				orcamento = orcamentoDao.getOrcamento(idOrcamento);
				String mesagemOriginal = orcamento.getMensagem();
				
				
				String resposta = request.getParameter("resposta");
				String novaMensagem = "Res: " + resposta
						+ ".\n\n Recebida de: " + orcamento.getCliente().getNome() + " ("
						+ orcamento.getCliente().getEmail() + "). Em: "
						+ CesareUtil.formatarData(orcamento.getDataCadastro(), "dd/MM/yyyy") + "\n\n" + "MESAGEM ORIGINAL: "
						+ orcamento.getMensagem();
				
				orcamentoDao.marcaStatusDeOrcamentoLido(idOrcamento, true);
				orcamentoDao.marcaStatusDeOrcamentoRespondido(idOrcamento);
				orcamentoDao.updateNovaMensagem(novaMensagem, idOrcamento);
				orcamento = orcamentoDao.getOrcamento(idOrcamento);
				orcamento.setEnderecos(enderecoDao.getEnderecosPorOrcamentos(idOrcamento));
				

				
				 *  Resposta do orcamento ao cliente 
				 
				Email.enviarEmail(empresa.getEmail(), empresa.getSenha(), orcamento.getCliente().getEmail(),
						"Res: Cesare Transportes - Resposta de Or�amento. codigo " + orcamento.getIdOrcamento(), 
						HtmlMensagem.getMensagemRespostaOrcamentoCliente(orcamento,	resposta, mesagemOriginal));			

				request.setAttribute("orcamento", orcamento);				
				request.setAttribute("veiculosCadastrado", veiculoDao.getAll());// se for ficar aki setar os servi�os 
				RequestDispatcher dispatcher = request.getRequestDispatcher("/ler-orcamento.jsp");
				dispatcher.forward(request, response);
			} else {								
				// antiga logica de cadastrar orcamento
			}*/
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			new CetransServletException("CNFE", getClass().getSimpleName(), e.getMessage()).doPost(request, response);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			new CetransServletException("UHE", getClass().getSimpleName(), e.getMessage()).doPost(request, response);
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
		} finally {
			try {
				conexao.close();
			} catch (SQLException e) {
				e.printStackTrace();
				new CetransServletException("SQLE2", getClass().getSimpleName(), e.getMessage()).doPost(request, response);
			}
		}
	}		
}