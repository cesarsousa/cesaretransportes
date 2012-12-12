package br.com.cesaretransportes.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.cesaretransportes.dao.AbstractConnectionFactory;
import br.com.cesaretransportes.dao.EmpresaDao;
import br.com.cesaretransportes.dao.EnderecoDao;
import br.com.cesaretransportes.dao.OrcamentoDao;
import br.com.cesaretransportes.modelo.Empresa;
import br.com.cesaretransportes.modelo.Orcamento;
import br.com.cesaretransportes.util.CesareUtil;
import br.com.cesaretransportes.util.Email;
import br.com.cesaretransportes.util.HtmlMensagem;

/*
 * Esta classe recebe requisição da activity ResponderOrcamento.
 */
public class AndroidResponderOrcamento extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		this.doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		int idOrcamento = Integer.valueOf(request.getParameter("id"));
		
		Connection conexao = null;
		try {
			conexao = AbstractConnectionFactory.getConexao();
			OrcamentoDao orcamentoDao = new OrcamentoDao(conexao);
			Orcamento orcamento = orcamentoDao.getOrcamento(idOrcamento);
			EmpresaDao empresaDao = new EmpresaDao(conexao);
			EnderecoDao enderecoDao = new EnderecoDao(conexao);
			String mesagemOriginal = orcamento.getMensagem();		
			
			Empresa empresa = empresaDao.get();
			
			String resposta = request.getParameter("resposta");
			String novaMensagem = "Res: " + resposta
					+ ".\n\n Recebida de: " + orcamento.getCliente().getNome() + " ("
					+ orcamento.getCliente().getEmail() + "). Em: "
					+ CesareUtil.formatarData(orcamento.getDataCadastro(), "dd/MM/yyyy") + "\n\n" + "MESAGEM ORIGINAL: "
					+ orcamento.getMensagem();
			
			orcamentoDao.marcaStatusDeOrcamentoLido(idOrcamento, true);
			orcamentoDao.marcaStatusDeOrcamentoRespondido(idOrcamento);
			orcamentoDao.updateNovaMensagem(novaMensagem, idOrcamento);
			orcamento.setEnderecos(enderecoDao.getEnderecosPorOrcamentos(idOrcamento));

			/*
			 *  Resposta do orcamento ao cliente 
			 */
			Email.enviarEmail(empresa.getEmail(), empresa.getSenha(), orcamento.getCliente().getEmail(),
					"Res: Cesare Transportes - Resposta de Orçamento. codigo " + orcamento.getIdOrcamento(), 
					HtmlMensagem.getMensagemRespostaOrcamentoCliente(orcamento,	resposta, mesagemOriginal));		
			
			write(response, novaMensagem);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			write(response, "ERRO codigo CNFE13 " + e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
			write(response, "ERRO codigo SQLE13 " + e.getMessage());
		} catch (AddressException e) {
			e.printStackTrace();
			write(response, "ERRO codigo AE13 " + e.getMessage());
		} catch (SendFailedException e) {
			e.printStackTrace();
			write(response, "ERRO codigo SFE13 " + e.getMessage());
		} catch (MessagingException e) {
			e.printStackTrace();
			write(response, "ERRO codigo ME13 " + e.getMessage());
		} finally {
			try {
				conexao.close();
			} catch (SQLException e) {
				e.printStackTrace();
				write(response, "ERRO codigo SQLE213 " + e.getMessage());
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
