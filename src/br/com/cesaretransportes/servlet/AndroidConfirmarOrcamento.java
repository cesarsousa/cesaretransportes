package br.com.cesaretransportes.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
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
import br.com.cesaretransportes.dao.ServicoDao;
import br.com.cesaretransportes.modelo.Empresa;
import br.com.cesaretransportes.modelo.Orcamento;
import br.com.cesaretransportes.modelo.Servico;
import br.com.cesaretransportes.modelo.Veiculo;
import br.com.cesaretransportes.util.Email;
import br.com.cesaretransportes.util.HtmlMensagem;

/*
 * Esta classe recebe requisição da activity ConfirmarOrcamentoComoServico.
 */
public class AndroidConfirmarOrcamento extends HttpServlet {
	private static final long serialVersionUID = 1L;       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		this.doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {	
		
		String opcao = request.getParameter("opcao");	
		
		Connection conexao = null;
		try {
			conexao = AbstractConnectionFactory.getConexao();
			ServicoDao servicoDao = new ServicoDao(conexao);
			EmpresaDao empresaDao = new EmpresaDao(conexao);
			ClienteDao clienteDao = new ClienteDao(conexao);
			
			if("servico".equals(opcao)){
				/*
				 * confirmar o orçamento como serviço.
				 */
				int idOrcamento = Integer.parseInt(request.getParameter("codigo"));
				String valor = request.getParameter("valor");
				Calendar dataPrevEntrega = Calendar.getInstance();
				dataPrevEntrega.setTimeInMillis(Long.parseLong(request.getParameter("dataPrevEntrega")));
				int idVeiculo = Integer.parseInt(request.getParameter("idVeiculo"));
				
				Empresa empresa = empresaDao.get();
				Orcamento orcamento = new Orcamento();
				orcamento.setIdOrcamento(idOrcamento);
				Veiculo veiculo = new Veiculo();
				veiculo.setIdVeiculo(idVeiculo);
				Servico servico = new Servico(orcamento, veiculo, new BigDecimal(valor.replace(",", ".")), dataPrevEntrega, Calendar.getInstance());
				int codigoServico = servicoDao.cadastrar(servico);
				
				String cliente = clienteDao.getClientePorOrcamento(idOrcamento);
				if("".equals(cliente)){
					write(response, "Novo servico cod:" + String.valueOf(codigoServico) + ". Nao foi possivel" +
							" notificar cliente do servico.");
				}else{
					String[] dadosCliente = cliente.split(";");
					/*
					 * email de notificação para o cliente do novo serviço.
					 */
					Email.enviarEmail(empresa.getEmail(), empresa.getSenha(), dadosCliente[0], 
							"Cetrans - Confirmacao de Ordem de servico", 
							HtmlMensagem.getMensagemConfirmacaoServico(codigoServico, idOrcamento, dadosCliente[1]));
		
					write(response, "Novo servico cod:" + String.valueOf(codigoServico));
				}				
			}else{
				/*
				 * confirmar o serviço como entregue
				 */				
				int idServico = Integer.parseInt(request.getParameter("codigo"));
				Servico servico = servicoDao.get(idServico);
				servicoDao.confirmarEntregas(Arrays.asList(servico));
				
				write(response, "true");				
			}		

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			write(response, "ERRO codigo CNFE20 " + e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
			write(response, "ERRO codigo SQLE20 " + e.getMessage());
		} catch (AddressException e) {
			e.printStackTrace();
			write(response, "ERRO codigo AE20 " + e.getMessage());
		} catch (SendFailedException e) {
			e.printStackTrace();
			write(response, "ERRO codigo SFE20 " + e.getMessage());
		} catch (MessagingException e) {
			e.printStackTrace();
			write(response, "ERRO codigo ME20 " + e.getMessage());
		} finally {
			try {
				conexao.close();
			} catch (SQLException e) {
				e.printStackTrace();
				write(response, "ERRO codigo SQLE220 " + e.getMessage());
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
