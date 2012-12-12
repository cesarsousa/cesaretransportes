package br.com.cesaretransportes.servlet;

import java.io.IOException;
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
import br.com.cesaretransportes.dao.TelefoneDao;
import br.com.cesaretransportes.modelo.Cliente;
import br.com.cesaretransportes.modelo.Cliente.TipoDoDocumento;
import br.com.cesaretransportes.modelo.Empresa;
import br.com.cesaretransportes.modelo.Endereco;
import br.com.cesaretransportes.modelo.Endereco.StatusEndereco;
import br.com.cesaretransportes.modelo.Orcamento;
import br.com.cesaretransportes.modelo.Telefone;
import br.com.cesaretransportes.util.Email;
import br.com.cesaretransportes.util.HtmlMensagem;
import br.com.cesaretransportes.util.MSG;

public class ClienteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Connection conexao = null;
		try {
			conexao = AbstractConnectionFactory.getConexao();
			TelefoneDao telefoneDao = new TelefoneDao(conexao);
			EnderecoDao enderecoDao = new EnderecoDao(conexao);
			ClienteDao clienteDao = new ClienteDao(conexao);
			EmpresaDao empresaDao = new EmpresaDao(conexao);
			
			boolean editarCliente = Boolean.valueOf(request.getParameter("editarCliente"));
			if (editarCliente) request.setAttribute("editarCliente", true);
			boolean paginaComErro = false;
			boolean recadastrar = false;			
			String pagina = "resposta-de-solicitacao.jsp";
			
			//String opcao = request.getParameter("opcao");			
			
			String usuario = request.getParameter("usuario");
			request.setAttribute("usuario", usuario);
			boolean clienteCadastrado = clienteDao.clienteExiste(usuario);
			
			String senha1 = request.getParameter("senha1");
			request.setAttribute("senha1", senha1);
			
			String senha2 = request.getParameter("senha2");
			request.setAttribute("senha2", senha2);
			
			String nome = request.getParameter("nome");
			request.setAttribute("nome", nome);
			
			String tipoDoc = request.getParameter("tipoDoc");			
			boolean cpf = tipoDoc.equals("cpf");			
			
			String numDoc = request.getParameter("numDoc");
			request.setAttribute("numDoc", numDoc);			
			
			String telDdd = request.getParameter("telDdd");
			request.setAttribute("telDdd", telDdd);
			String telNumero = request.getParameter("telNumero");
			request.setAttribute("telNumero", telNumero);
			
			String telComplemento = request.getParameter("telComplemento");
			request.setAttribute("telComplemento", telComplemento);
			
			String novoEndereco = request.getParameter("novoEndereco");
			request.setAttribute("novoEndereco", novoEndereco);
			
			String enderecoAtual = request.getParameter("enderecoAtual");
			request.setAttribute("enderecoAtual", enderecoAtual);
			
			String rua = request.getParameter("rua");
			request.setAttribute("rua", rua);
			
			String numeroRua = request.getParameter("numeroRua");
			request.setAttribute("numeroRua", numeroRua);
			
			String bairro = request.getParameter("bairro");
			request.setAttribute("bairro", bairro);
			
			String cidade = request.getParameter("cidade");
			request.setAttribute("cidade", cidade);
			
			String estado = request.getParameter("estado");
			request.setAttribute("estado", estado);
			
			boolean termoContrato = Boolean.valueOf(request.getParameter("termoContrato"));
			
			// inicio da validação
			if (usuario.isEmpty()){
				paginaComErro = true;				
				request.setAttribute("msgUser", "Um endere&ccedil;o de email v&aacute;lido dever ser preenchido !");
			}else{				
				if (clienteCadastrado && !editarCliente){					
					paginaComErro = true;
					request.setAttribute("msgUser", "O email <i>" + usuario + "</i> j&aacute; est&aacute; cadastrado no site !");
				}else if (!emailValido(usuario)){
					paginaComErro = true;
					request.setAttribute("msgUser", "O email <i>" + usuario + "</i> n&atilde;o &eacute; um email v&aacute;lido !");
				}
			}
			
			if (senha1.isEmpty() || senha2.isEmpty()){
				paginaComErro = true;
				request.setAttribute("msgSenha", "campo de senha n&atilde;o pode estar em branco !");
			}else if (!senhasConferem(senha1, senha2)){
				paginaComErro = true;
				request.setAttribute("msgSenha", "Senhas n&atilde;o conferem !");
			}			
			
			if (nome.isEmpty()){
				paginaComErro = true;
				request.setAttribute("msgNome", "Por favor digite o seu nome !");
			}
			
			if (numDoc.isEmpty()){
				paginaComErro = true;
				request.setAttribute("msgNunDoc", "O n&uacute;mero do documento deve ser preenchido !");
			}
			
			if (cpf){
				request.setAttribute("msgCpf", true);
				if (!numDoc.matches("\\d+")){
					paginaComErro = true;
					request.setAttribute("msgNunDoc", "Digite apenas os n&uacute;meros do CPF !");
				}else if (numDoc.length() != 11){
					paginaComErro = true;
					request.setAttribute("msgNunDoc", "N&uacute;mero de CPF inv&aacute;lido !");				
				}
			}else{
				if (!numDoc.matches("\\d+")){
					paginaComErro = true;
					request.setAttribute("msgNunDoc", "Digite apenas os n&uacute;meros do CNPJ !");
				}else if (numDoc.length() != 14){
					paginaComErro = true;
					request.setAttribute("msgNunDoc", "N&uacute;mero de CNPJ inv&aacute;lido !");				
				}
			}
			
			if (!telDdd.matches("\\d{2}")){
				paginaComErro = true;
				request.setAttribute("msgTelDdd", " O DDD deve conter apenas 2 d&iacute;gitos !");
				request.setAttribute("msgTelefone", true);
			}
			if(!telNumero.matches("\\d{8}")){
				paginaComErro = true;
				request.setAttribute("msgTelNumero", "O n&uacute;mero do telefone deve conter apenas 8 d&iacute;gitos !");			
				request.setAttribute("msgTelefone", true);
			}
			
			if(rua.isEmpty() || numeroRua.isEmpty() || bairro.isEmpty() || cidade.isEmpty()){				
				paginaComErro = true;
				request.setAttribute("msgEndereco", "Existe(m) campo(s) de endere&ccedil;o em branco<br/>Aten&ccedil;&atilde;o . Verifique o seu estado");
			}
			
			if(!numeroRua.matches("\\d+")){
				paginaComErro = true;
				request.setAttribute("msgEndereco", "N&uacute;mero da rua deve conter apenas d&iacute;gitos");
			}
			
			if (!termoContrato && !editarCliente){				
				paginaComErro = true;
				request.setAttribute("msgTermoContrato", "Para se cadastrar, o Termo de Contrato deve ser aceito !");
			}
			// fim da validação
			
			if (clienteCadastrado){
				if(senhasConferem(senha1, senha2)){
					if(clienteEstaInativo(clienteDao, usuario, senha1)){
						recadastrar = true;
					}					
				}				
			}
			
			if(recadastrar){
				Cliente cliente = clienteDao.getCliente(usuario);
				cliente.setTelefone(telefoneDao.get(cliente.getIdCliente()));
				cliente.setEndereco(enderecoDao.get(cliente.getIdCliente()));				
				request.setAttribute("cliente", cliente);
				pagina = "/recadastramentoCliente.jsp";
			}else if (paginaComErro){
				pagina = "cadastrar-cliente.jsp";
			}else{
				Telefone telefone = new Telefone();
				Endereco endereco = new Endereco();
				List<Orcamento> orcamentos = new ArrayList<Orcamento>();
				Cliente cliente = new Cliente(nome, "C", usuario,
						cpf ? TipoDoDocumento.CPF : TipoDoDocumento.CNPJ,
						numDoc, telefone, endereco, senha1, false, Calendar.getInstance(), null, orcamentos);			

				if(editarCliente){
					int idCliente = clienteDao.getId(usuario);
					cliente.setIdCliente(idCliente);
					clienteDao.alterar(cliente);
				}else{
					clienteDao.cadastrar(cliente);
				}
								
				int idCliente = clienteDao.getId(usuario);
				
				cliente.setIdCliente(idCliente);
				Empresa empresa = new Empresa();
				empresa.setIdEmpresa(0);				
				Orcamento orcamento = new Orcamento();
				orcamento.setIdOrcamento(0);
				
				if(editarCliente){
					telefoneDao.alterar(idCliente, telDdd, telNumero, telComplemento);					
				}else{
					telefone = new Telefone(0, idCliente, telDdd, telNumero, telComplemento);
					telefoneDao.cadastrar(telefone);
				}
				
				String localizacao = "";
				if(editarCliente){
					endereco = enderecoDao.get(idCliente);
					localizacao = novoEndereco.isEmpty() ? endereco.getLocalizacao() : novoEndereco;
					enderecoDao.alterar(idCliente, localizacao, cidade, estado);
				}else{
					localizacao = rua +  " " + numeroRua +  ", " + bairro;
					endereco = new Endereco(empresa, cliente, orcamento, cidade, estado, localizacao, StatusEndereco.NAO_SE_APLICA);
					enderecoDao.cadastrarEndereco(endereco);
				}			

				if(editarCliente){
					// atualiza o cliente com o id.
					cliente = clienteDao.getCliente(usuario);
					cliente.setTelefone(telefone);
					cliente.setEndereco(endereco);
					request.setAttribute("cliente", cliente);
					request.setAttribute("mensagem", "Seus dados foram alterados com sucesso!");
					pagina = "/index.jsp";
				}else{				
					// atualiza o cliente com o id.
					cliente = clienteDao.getCliente(usuario);			
					
					Empresa e = empresaDao.get();
					/*
					 *  notificacao para o cliente do recadastramento
					 */
					Email.enviarEmail(
							e.getEmail(), e.getSenha(),	usuario,
							"Cesare Transportes - confirmacao de Cadastro",
							HtmlMensagem.getMensagemNotificacaoCliente(nome, "cadastro"));
	
					/*
					 * Email de notificação a empresa de um novo cliente cadastrado.
					 */
					Email.enviarEmail(
							e.getEmail(), e.getSenha(), e.getEmail(),
							"Cesare Transportes - Novo cliente cadastrado",
							HtmlMensagem.getMensagemEnviarEmail(cliente.getIdCliente(), cliente.getNome()));
	
					request.setAttribute("mensagem", MSG.CLIENTE_CADASTRADO
							.toString().replace("NOME", cliente.getNome()));
				}

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
		} finally {
			try {
				conexao.close();
			} catch (SQLException e) {
				e.printStackTrace();
				new CetransServletException("SQLE2", getClass().getSimpleName(), e.getMessage()).doPost(request, response);
			}
		}
	}	

	private boolean clienteEstaInativo(ClienteDao clienteDao, String usuario, String senha) throws SQLException {
		return clienteDao.clienteInativo(usuario, senha);
	}

	private boolean emailValido(String usuario) {
		return usuario.matches("[a-zA-Z0-9._%-]+@[a-zA-Z0-9._-]+\\.[a-z]{2,4}");
	}

	private boolean senhasConferem(String senha1, String senha2) {
		return senha1.equals(senha2) ? true : false;
	}
}
