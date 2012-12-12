package br.com.cesaretransportes.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.cesaretransportes.dao.AbstractConnectionFactory;
import br.com.cesaretransportes.dao.ClienteDao;
import br.com.cesaretransportes.dao.EmpresaDao;
import br.com.cesaretransportes.dao.EnderecoDao;
import br.com.cesaretransportes.dao.OrcamentoDao;
import br.com.cesaretransportes.modelo.Cliente;
import br.com.cesaretransportes.modelo.Empresa;
import br.com.cesaretransportes.modelo.Endereco;
import br.com.cesaretransportes.modelo.Orcamento;
import br.com.cesaretransportes.modelo.Endereco.StatusEndereco;
import br.com.cesaretransportes.util.CesareUtil;
import br.com.cesaretransportes.util.Email;
import br.com.cesaretransportes.util.HtmlMensagem;

/*
 * Esta classe recebe requisição da activity ClienteOrcamento.
 */
public class AndroidOrcamentoCadastrar extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {	
		
		int idCliente = Integer.valueOf(request.getParameter("idCliente"));
		String cidadeOrigem = request.getParameter("cidadeOrigem");
		String estadoOrigem = request.getParameter("estadoOrigem");
		String enderecoOrigem = request.getParameter("enderecoOrigem");
		String cidadeDestino = request.getParameter("cidadeDestino");
		String estadoDestino = request.getParameter("estadoDestino");
		String enderecoDestino = request.getParameter("enderecoDestino");
		String peso = request.getParameter("peso");
		String dimensao = request.getParameter("dimensao");
		String mensagem = request.getParameter("mensagem");
		
		
		Connection conexao = null;
		try {
			conexao = AbstractConnectionFactory.getConexao();
			
			Orcamento orcamento = new Orcamento();
			ClienteDao clienteDao = new ClienteDao(conexao);
			OrcamentoDao orcamentoDao = new OrcamentoDao(conexao);
			EnderecoDao enderecoDao = new EnderecoDao(conexao);
			EmpresaDao empresaDao = new EmpresaDao(conexao);
			
			orcamento.setCliente(clienteDao.getCliente(idCliente));
			orcamento.setPeso(peso);
			orcamento.setDimensao(dimensao);
			orcamento.setMensagem(mensagem);					
			orcamento.setDataCadastro(Calendar.getInstance());
			
			int idOrcamento = orcamentoDao.cadastrarOrcamento(orcamento);			
			orcamento.setIdOrcamento(idOrcamento);			
			
			Endereco endereco = new Endereco(new Empresa(), new Cliente(), orcamento, cidadeOrigem, estadoOrigem, enderecoOrigem, StatusEndereco.ORIGEM);
			enderecoDao.cadastrarEndereco(endereco);
			endereco = new Endereco(new Empresa(), new Cliente(), orcamento, cidadeDestino, estadoDestino, enderecoDestino, StatusEndereco.DESTINO);
			enderecoDao.cadastrarEndereco(endereco);				
								
			
			Empresa empresa = empresaDao.get();
			/*
			 *  notificacao para o cliente do recebimento do orcamento
			 */
			Email.enviarEmail(
					empresa.getEmail(), empresa.getSenha(),	orcamento.getCliente().getEmail(),
					"Cesare Transportes - confirmacao de Recebimento de Orcamento",
					HtmlMensagem.getMensagemNotificacaoCliente(orcamento.getCliente().getNome(), "or&ccedil;amento"));
			
			/*
			 *  notificacao para a empresa de um novo orcamento recebido
			 */
			Email.enviarEmail(empresa.getEmail(), empresa.getSenha(), empresa.getEmail(), 
					"CeTrans - Novo Orcamento nº: " + orcamento.getIdOrcamento(), 
					HtmlMensagem.getMensagemNotificacaoEmpresa(
							orcamento.getCliente().getNome(), 
							CesareUtil.formatarData(orcamento.getDataCadastro(), "dd/MM/yyyy"), 
							orcamento.getIdOrcamento(), orcamento.getCliente().getEmail(), orcamento.getMensagem(), "or&ccedil;amento"));
			

			write(response, "Orcamento enviado com sucesso");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			write(response, "ERRO codigo CNFE09 " + e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
			write(response, "ERRO codigo SQLE09 " + e.getMessage());
		} catch (AddressException e) {
			e.printStackTrace();
			write(response, "ERRO codigo AE09 " + e.getMessage());
		} catch (SendFailedException e) {
			e.printStackTrace();
			write(response, "ERRO codigo SFE09 " + e.getMessage());
		} catch (MessagingException e) {
			e.printStackTrace();
			write(response, "ERRO codigo ME09 " + e.getMessage());
		} finally{
			try {
				conexao.close();
			} catch (SQLException e) {
				e.printStackTrace();
				write(response, "ERRO codigo SQLE209 " + e.getMessage());
			}
		}		
	}
	
	private void write(HttpServletResponse response, String resultado) throws IOException {		
		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();		
		writer.print(resultado);		
		writer.flush();
		writer.close();		
	}
}