package br.com.cesaretransportes.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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
import br.com.cesaretransportes.dao.TelefoneDao;
import br.com.cesaretransportes.modelo.Cliente;
import br.com.cesaretransportes.modelo.Empresa;
import br.com.cesaretransportes.modelo.Orcamento;
import br.com.cesaretransportes.util.AcaoCliente;
import br.com.cesaretransportes.util.Email;
import br.com.cesaretransportes.util.HtmlMensagem;

/**
 * Classe servlet responsavel pelo tratamento das requisicoes da view <code>mostrar-cliente.jsp</code>
 */
public class ClienteAcaoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;       
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		this.doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		
		String pagina = "/index.jsp";
		int acao = Integer.valueOf(request.getParameter("acao"));
		List<Cliente> clientes = new ArrayList<Cliente>();
		
		Connection conexao = null;
		
		try {
			conexao = AbstractConnectionFactory.getConexao();
			Empresa empresa = new EmpresaDao(conexao).get();
			int id = 0;
			OrcamentoDao orcamentoDao = new OrcamentoDao(conexao);
			TelefoneDao telefoneDao = new TelefoneDao(conexao);
			EnderecoDao enderecoDao = new EnderecoDao(conexao);
			ClienteDao clienteDao = new ClienteDao(conexao);
			
						
			switch (acao) {
			case AcaoCliente.LISTAR_TODOS:
				clientes = clienteDao.getAllBy(true, "dataCadastro", "statusCliente");
				if(clientes.size()>0){
					for(Cliente cliente : clientes){
						atualizarCliente(orcamentoDao, telefoneDao, enderecoDao, cliente);
					}
				}
				request.setAttribute("clientes", clientes);
				pagina = "/mostrar_cliente.jsp";				
				break;
			case AcaoCliente.DELETAR:
				id = Integer.parseInt(request.getParameter("id"));
				if(id == 1){					
					request.setAttribute("deletarAdm", true);					
				}else{
					clienteDao.deletar(id);
					request.setAttribute("infoCliente", "Cliente deletado com sucesso !");					
				}
				clientes = clienteDao.getAllBy(true, "dataCadastro", "statusCliente");
				if(clientes.size()>0){
					for(Cliente c : clientes){
						atualizarCliente(orcamentoDao, telefoneDao, enderecoDao, c);
					}
				}
				pagina = "/mostrar_cliente.jsp";
				request.setAttribute("clientes", clientes);
				break;
			case AcaoCliente.CONFIRMAR:
				id = Integer.parseInt(request.getParameter("id"));
				clienteDao.confirmar(id);
				Cliente cliente = clienteDao.getCliente(false, id);
				clientes = clienteDao.getAllBy(true, "dataCadastro", "statusCliente");
				if(clientes.size()>0){
					for(Cliente c : clientes){
						atualizarCliente(orcamentoDao, telefoneDao, enderecoDao, c);
					}
				}
				pagina = "/mostrar_cliente.jsp";
				request.setAttribute("clientes", clientes);
				request.setAttribute("infoCliente", "Cliente confirmado com sucesso !");
				
				/*Email.enviarEmail(empresa.getEmail(), empresa.getSenha(), cliente.getEmail(), 
						"Cesare Transportes - Confirmacao de Cadastro", 
						HtmlMensagem.getMensagemConfirmacaoCadastro(cliente));*/
							
				break;
			case AcaoCliente.BUSCAR:				
				String parametro = request.getParameter("parametro");
				String filtro = request.getParameter("filtro");				
				if(parametro.isEmpty()){
					request.setAttribute("erroBusca", "digite um par&acirc;metro para busca.");
				}else if(filtro.equals("id")){
					try{
						int cod = Integer.parseInt(parametro);
						Cliente c = clienteDao.getCliente(true, cod);						
						if(c == null){
							clientes = new ArrayList<Cliente>();
						}else{							
							clientes = Arrays.asList(c);
						}
					}catch (NumberFormatException e) {
						request.setAttribute("erroBusca", "digite um n&uacute;mero para busca por id.");
					}					
				}else if(filtro.equals("documento")){
					try {
						Integer.parseInt(parametro);
						clientes = clienteDao.getAllByBusca(true, parametro, filtro);
						
					} catch (NumberFormatException e) {
						request.setAttribute("erroBusca", "digite um n&uacute;mero para busca por documento.");
					}					
				}else{					
					clientes = clienteDao.getAllByBusca(true, parametro, filtro);
				}			
				
				pagina = "/mostrar_cliente.jsp";
				if(clientes.size() > 0){
					for(Cliente c : clientes){
						atualizarCliente(orcamentoDao, telefoneDao, enderecoDao, c);
					}
				}
				request.setAttribute("clientes", clientes);				
				request.setAttribute("numeroOcorrencias", clientes.size());
				request.setAttribute("busca", parametro);
				break;
			case AcaoCliente.LISTAR_ORCAMENTOS:
				pagina = "/mostrar_cliente.jsp";
				int idCliente = Integer.parseInt(request.getParameter("idCliente"));
				cliente = clienteDao.getCliente(false, idCliente);
				atualizarCliente(orcamentoDao, telefoneDao, enderecoDao, cliente);				
				request.setAttribute("clientes", Arrays.asList(cliente));
				request.setAttribute("clienteOrcamentos", cliente.getOrcamentos());
				request.setAttribute("mostrarOrcamentos", true);
				break;				
			case AcaoCliente.ATIVO:
				clientes = clienteDao.getAllByStatus(AcaoCliente.ATIVO);
				if(clientes.size()>0){
					for(Cliente c : clientes){
						atualizarCliente(orcamentoDao, telefoneDao, enderecoDao, c);
					}
				}
				request.setAttribute("clientes", clientes);
				pagina = "/mostrar_cliente.jsp";				
				break;
			case AcaoCliente.INATIVO:
				clientes = clienteDao.getAllByStatus(AcaoCliente.INATIVO);
				if(clientes.size()>0){
					for(Cliente c : clientes){
						atualizarCliente(orcamentoDao, telefoneDao, enderecoDao, c);
					}
				}
				request.setAttribute("clientes", clientes);
				pagina = "/mostrar_cliente.jsp";
				break;
			default:
				new CetransServletException("ClienteAcaoServlet", getClass().getSimpleName(), "No switch for '" + acao + "' option.");
			}
			
			RequestDispatcher dispatcher = request.getRequestDispatcher(pagina);
			dispatcher.forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
			new CetransServletException("SQLE ", getClass().getSimpleName(), e.getMessage()).doPost(request, response);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			new CetransServletException("CNFE ", getClass().getSimpleName(), e.getMessage()).doGet(request, response);
		}  finally{
			try {
				if (conexao != null) conexao.close();
			} catch (SQLException e) {
				e.printStackTrace();
				new CetransServletException("SQLE2 " + e.getMessage(), getClass().getSimpleName(), e.getMessage()).doPost(request, response);
			}
		}		
	}

	/**
	 * Adiciona ao cliente o seu endereco, telefone e caso existam os orcamentos. Caso exista um ou mais orcamentos
	 * relacionados ao cliente, adiciona os respectivos enderecos a cada orcamento.
	 * @param orcamentoDao
	 * @param telefoneDao
	 * @param enderecoDao
	 * @param cliente
	 * @throws SQLException
	 */
	protected void atualizarCliente(OrcamentoDao orcamentoDao, TelefoneDao telefoneDao, EnderecoDao enderecoDao, Cliente cliente) throws SQLException {
		/*cliente.setEndereco(enderecoDao.get(cliente.getIdCliente()));*/
		cliente.setEndereco(null);		
		cliente.setTelefone(telefoneDao.get(cliente.getIdCliente()));
		cliente.setOrcamentos(orcamentoDao.getListaDeOrcamentos(cliente.getIdCliente(), "idOrcamento", 1));
		if(cliente.getOrcamentos().size() > 0){
			for(Orcamento orcamento : cliente.getOrcamentos()){
				orcamento.setEnderecos(enderecoDao.getEnderecosPorOrcamentos(orcamento.getIdOrcamento()));
			}
		}
	}
}