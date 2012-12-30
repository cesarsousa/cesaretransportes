package br.com.cesaretransportes.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.cesaretransportes.modelo.Cliente;
import br.com.cesaretransportes.modelo.Cliente.TipoDoDocumento;
import br.com.cesaretransportes.modelo.Endereco;
import br.com.cesaretransportes.modelo.Orcamento;
import br.com.cesaretransportes.modelo.Telefone;
import br.com.cesaretransportes.util.AcaoCliente;

import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

public class ClienteDao {

	private Connection conexao;
	
	public ClienteDao(Connection conexao){
		this.conexao = conexao;		
	}

	public Cliente getCliente(String email) throws SQLException, ClassNotFoundException {
			String sql = "select idCliente, nome, tipoCliente, email, statusCliente, senha, tipoDoc, numDoc, dataCadastro, dataExclusao " +
			"from cliente where email='?1'".replace("?1", email);
			PreparedStatement statement = conexao.prepareStatement(sql);
			ResultSet result = statement.executeQuery();			
			
			Cliente cliente = null;
			if (result.next()){
				Calendar dataCadastro = Calendar.getInstance();
				dataCadastro.setTime(result.getDate("dataCadastro"));
				
				Calendar dataExclusao = null;
				if (result.getDate("dataExclusao") != null){
					dataExclusao = Calendar.getInstance();
					dataExclusao.setTime(result.getDate("dataExclusao"));				
				}	
				
				Telefone telefone = new Telefone();				
				Endereco endereco = new Endereco();				
				List<Orcamento> orcamentos = new ArrayList<Orcamento>();
				
				cliente = new Cliente(
						result.getInt("idCliente"),
						result.getString("nome"),
						result.getString("tipoCliente"),
						result.getString("email"),
						TipoDoDocumento.criarPorCodigo(result.getString("tipoDoc")),
						result.getString("numDoc"),
						telefone,
						endereco,
						result.getString("senha"),
						result.getBoolean("statusCliente"),
						dataCadastro,
						dataExclusao,
						orcamentos);
				
			}
			statement.close();
			return cliente;			
	}

	public int cadastrar(Cliente cliente) throws SQLException,	ClassNotFoundException {
				
		String sql = "insert into cliente (nome,email,statusCliente,senha,tipoDoc,numDoc, dataCadastro,tipoCliente) "
				+ "values (?,?,?,?,?,?,?,?)";		
		Date dtCad = new Date(cliente.getDataCadastro().getTimeInMillis());		
		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setString(1, cliente.getNome());		
		statement.setString(2, cliente.getEmail());
		statement.setString(3, String.valueOf(cliente.isCliente()));
		statement.setString(4, cliente.getSenha());
		statement.setString(5, cliente.getTipoDoDocumento().toString());
		statement.setString(6, cliente.getNumeroDoDocumento());
		statement.setDate(7, dtCad);
		statement.setString(8, cliente.getTipoCliente());			
		
		statement.execute();
		
		sql = "select max(idCliente) as ultimoId from cliente;";
		statement = conexao.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		int idCliente = 0;
		if(resultSet.first()){
			idCliente = resultSet.getInt("ultimoId");	
		}
		
		statement.close();
		
		return idCliente;
	}

	/**
	 * 
	 * @param usuario endere�o de email do cliente usado no cadastro.
	 * @param senha a senha do cliente.
	 * @param orcamentoDao 
	 * @return os dados do cliente caso usu�rio e email informados sejam v�lidos, caso contr�rio retorna <code>null</code>.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Cliente getCliente(String usuario, String senha) throws ClassNotFoundException, SQLException {
		String sql = "select idCliente, nome, tipoCliente, email, statusCliente, senha, tipoDoc, numDoc, dataCadastro, dataExclusao " +
					"from cliente where email='?1' and senha='?2'"
			.replace("?1", usuario)
			.replace("?2", senha);
				
		PreparedStatement statement = conexao.prepareStatement(sql);		
		ResultSet result = statement.executeQuery();
		Cliente cliente = null;
		if(result.first()){			
			Calendar dataCadastro = Calendar.getInstance();
			dataCadastro.setTime(result.getDate("dataCadastro"));
			
			Calendar dataExclusao = null;
			if (result.getDate("dataExclusao") != null){
				dataExclusao = Calendar.getInstance();
				dataExclusao.setTime(result.getDate("dataExclusao"));				
			}			
			
			Telefone telefone = new Telefone();				
			Endereco endereco = new Endereco();				
			List<Orcamento> orcamentos = new ArrayList<Orcamento>();
			cliente = new Cliente(
					result.getInt("idCliente"),
					result.getString("nome"),
					result.getString("tipoCliente"),
					result.getString("email"),
					TipoDoDocumento.criarPorCodigo(result.getString("tipoDoc")),
					result.getString("numDoc"),
					telefone,
					endereco,
					result.getString("senha"),
					result.getBoolean("statusCliente"),
					dataCadastro,
					dataExclusao,
					orcamentos);
		}
		statement.close();
		
		return cliente;		
	}

	public List<Cliente> getAllBy(boolean todos, String filtro1, String filtro2) throws SQLException {
		String condicao = todos ? "" : "and dataExclusao is null";		
		String sql = "select idCliente, nome, tipoCliente, email, statusCliente, senha, tipoDoc, numDoc, dataCadastro, dataExclusao " +
				"from cliente where tipoCliente = 'U' ?1 order by ?2 , ?3 desc".replace("?1", condicao).replace("?2", filtro1).replace("?3", filtro2);
				
		PreparedStatement statement = conexao.prepareStatement(sql);
		ResultSet result = statement.executeQuery();		
		List<Cliente> clientes = setListaDeClientes(result);		
		result.close();
		statement.close();		
		return clientes;
	}
	
	public List<Cliente> getAllByStatus(int status) throws SQLException {
		String condicao = AcaoCliente.INATIVO == status ? "not" : "";		
		String sql = "select idCliente, nome, tipoCliente, email, statusCliente, senha, tipoDoc, numDoc, dataCadastro, dataExclusao " +
				"from cliente where tipoCliente = 'U' and dataExclusao is " + condicao + " null order by dataCadastro";
				
		PreparedStatement statement = conexao.prepareStatement(sql);
		ResultSet result = statement.executeQuery();		
		List<Cliente> clientes = setListaDeClientes(result);		
		result.close();
		statement.close();		
		return clientes;
	}

	private List<Cliente> setListaDeClientes(ResultSet result)
			throws SQLException {
		List<Cliente> clientes = new ArrayList<Cliente>();
		while (result.next()){			
			Calendar dataCadastro = Calendar.getInstance();
			dataCadastro.setTime(result.getDate("dataCadastro"));			
			
			Calendar dataExclusao = null;
			if (result.getDate("dataExclusao") != null){
				dataExclusao = Calendar.getInstance();
				dataExclusao.setTime(result.getDate("dataExclusao"));				
			}			
			
			Telefone telefone = new Telefone();				
			Endereco endereco = new Endereco();				
			List<Orcamento> orcamentos = new ArrayList<Orcamento>();
			clientes.add(new Cliente(
					result.getInt("idCliente"),
					result.getString("nome"),
					result.getString("tipoCliente"),
					result.getString("email"),
					TipoDoDocumento.criarPorCodigo(result.getString("tipoDoc")),
					result.getString("numDoc"),
					telefone,
					endereco,
					result.getString("senha"),
					result.getBoolean("statusCliente"),
					dataCadastro,
					dataExclusao,
					orcamentos));			
		}
		return clientes;
	}

	/**
	 * exclusao logica de um cliente da base de dados. Atualiza o campo <code>dataExclusao</code>
	 * com a data atual.
	 * 
	 * @param id
	 * @throws SQLException
	 */
	public void deletar(int id) throws SQLException {		
		Calendar dataHoje = Calendar.getInstance();
		Date dataExclusao = new Date(dataHoje.getTimeInMillis());
		
		String sql = "update cliente set dataExclusao=? where idCliente=?";
		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setDate(1, dataExclusao);
		statement.setInt(2, id);
		statement.execute();
		statement.close();		
	}
	
	/**
	 * Faz uma atualizacao no registro parametrizado, setando o status do cliente como <code>true</code>.
	 * 
	 * @param id identificador do registros do cliente a ser atualizado
	 * @throws SQLException 
	 */
	public void confirmar(int id) throws SQLException {
		String sql = "update cliente set dataExclusao=null where idCliente=" + id;
		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.execute();
		statement.close();		
	}

	/**
	 * 
	 * @param todos
	 *            se <code>true</code> busca todos os cliente ativo e inativos,
	 *            caso contr�rio somente cliente ativo, ou seja, com data de
	 *            exclus�o nula.
	 * @param id
	 *            o id do cliente	
	 * @return um cliente cadastro na base, caso n�o exista o cliente referente
	 *         retorna <code>null</code>
	 * @throws SQLException
	 */
	public Cliente getCliente(boolean todos, int id) throws SQLException {
		String condicao = todos ? "" : "and dataExclusao is null";
		
		String sql = "select idCliente, nome, tipoCliente, email, statusCliente, senha, tipoDoc, numDoc, dataCadastro, dataExclusao " +
			"from cliente where tipoCliente <> 'A' " + condicao + " and idCliente=" + id;
		
		Cliente cliente = null;
		PreparedStatement statement = conexao.prepareStatement(sql);
		ResultSet result = statement.executeQuery();
		if(result.first()){
			Calendar dataCadastro = Calendar.getInstance();
			dataCadastro.setTime(result.getDate("dataCadastro"));
			
			Calendar dataExclusao = null;
			if (result.getDate("dataExclusao") != null){
				dataExclusao = Calendar.getInstance();
				dataExclusao.setTime(result.getDate("dataExclusao"));				
			}		
			
			Telefone telefone = new Telefone();				
			Endereco endereco = new Endereco();				
			List<Orcamento> orcamentos = new ArrayList<Orcamento>();
			cliente = new Cliente(
					result.getInt("idCliente"),
					result.getString("nome"),
					result.getString("tipoCliente"),
					result.getString("email"),
					TipoDoDocumento.criarPorCodigo(result.getString("tipoDoc")),
					result.getString("numDoc"),
					telefone,
					endereco,
					result.getString("senha"),
					result.getBoolean("statusCliente"),
					dataCadastro,
					dataExclusao,
					orcamentos);
		}
		
		statement.close();		
		return cliente;
	}
	
	/**
	 * Pesquisa um cliente sem nenhum tipo de restri��o quanto a tipo de cliente, status e data de exclus�o.
	 * @param idCliente o id do cliente
	 * @return um cliente se existe, caso contrario retorna null.
	 * @throws SQLException
	 */
	public Cliente getCliente(int idCliente) throws SQLException {
					
		String sql = "select idCliente, nome, tipoCliente, email, statusCliente, senha, tipoDoc, numDoc, dataCadastro, dataExclusao " +
			"from cliente where idCliente=" + idCliente;
		
		Cliente cliente = null;
		PreparedStatement statement = conexao.prepareStatement(sql);
		ResultSet result = statement.executeQuery();
		if(result.first()){
			Calendar dataCadastro = Calendar.getInstance();
			dataCadastro.setTime(result.getDate("dataCadastro"));
			
			Calendar dataExclusao = null;
			if (result.getDate("dataExclusao") != null){
				dataExclusao = Calendar.getInstance();
				dataExclusao.setTime(result.getDate("dataExclusao"));				
			}			
			
			Telefone telefone = new Telefone();				
			Endereco endereco = new Endereco();				
			List<Orcamento> orcamentos = new ArrayList<Orcamento>(); 
			cliente = new Cliente(
					result.getInt("idCliente"),
					result.getString("nome"),
					result.getString("tipoCliente"),
					result.getString("email"),
					TipoDoDocumento.criarPorCodigo(result.getString("tipoDoc")),
					result.getString("numDoc"),
					telefone,
					endereco,
					result.getString("senha"),
					result.getBoolean("statusCliente"),
					dataCadastro,
					dataExclusao,
					orcamentos);
		}
		
		statement.close();
		result.close();
		return cliente;
	}
	
	/**
	 * Busca todos os clientes cujo parametro de busca esteja contido no campo
	 * nome do cliente.
	 * 
	 * @param todos
	 *            se <code>true</code> busca todos os cliente ativo e inativos,
	 *            caso contrario somente cliente ativo, ou seja, com data de
	 *            exclusao nula. 
	 * @param busca
	 *            parametro da busca
	 * @param orcamentoDao 
	 * @return a lista dos clientes ordenadas por statusCliente e dataCadastro,
	 *         caso nao sejam encontrados clientes com o parametro uma lista
	 *         vazia sera retornada.
	 * @throws SQLException 
	 */
	public List<Cliente> getAllByBusca(boolean todos, String busca, String filtro) throws SQLException {
		List<Cliente> clientes = getAllBy(todos, "statusCliente", "dataCadastro");
		List<Cliente> clientesDaBusca = new ArrayList<Cliente>();	
		
		if(filtro.equals("nome")){
			for(Cliente cliente : clientes){
				if(cliente.getNome().contains(busca)){
					clientesDaBusca.add(cliente);
				}
			}			
		}else{
			for(Cliente cliente : clientes){
				if(cliente.getEmail().contains(busca)){
					clientesDaBusca.add(cliente);
				}
			}
		}	
		
		return clientesDaBusca;
	}

	public void alterarSenha(int id, String senha) throws SQLException, MySQLSyntaxErrorException {
		String sql = "update cliente set senha=? where idCliente=?";
				
		PreparedStatement statement = conexao.prepareStatement(sql);		
		statement.setString(1, senha);
		statement.setInt(2, id);
		
		statement.execute();
		statement.close();		
	}

	public void alterar(int idCliente, String tipoDocumento, String numeroDocumento) throws SQLException {
		String sql = "update cliente set tipoDoc=?, numDoc=? where idCliente=?";
		
		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setString(1, tipoDocumento);
		statement.setString(2, numeroDocumento);
		statement.setInt(3, idCliente);
		
		statement.execute();
		statement.close();	
	}
	
	public void alterar(Cliente cliente) throws SQLException {
		String sql = "update cliente set tipoDoc=?, numDoc=?, nome=?, senha=?  where idCliente=?";
		
		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setString(1, cliente.getTipoDoDocumento().toString());
		statement.setString(2, cliente.getNumeroDoDocumento());
		statement.setString(3, cliente.getNome());
		statement.setString(4, cliente.getSenha());
		statement.setInt(5, cliente.getIdCliente());
		
		statement.execute();
		statement.close();		
	}

	public boolean clienteExiste(String email) throws SQLException {
		String sql = "select * from cliente where email='" + email + "' and tipoCliente='C'";
		PreparedStatement statement = conexao.prepareStatement(sql);
		ResultSet result = statement.executeQuery();
		boolean resultado = result.first();
		result.close();
		statement.close();
		
		return resultado;
	}
	
	public String getSenha(String email) throws SQLException{
		String sql = "select senha from cliente where email=?";
		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setString(1, email);
		ResultSet resultSet = statement.executeQuery();
		if(resultSet.first()){
			return resultSet.getString("senha");
		}
		return "";
		
	}

	public boolean clienteAtivo(String email, String senha) throws SQLException {
		String sql = "select * from cliente where dataExclusao is null and email=? and senha=?";
		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setString(1, email);
		statement.setString(2, senha);
		ResultSet resultSet = statement.executeQuery();
		boolean resultado = resultSet.first();
		resultSet.close();
		statement.close();
		return resultado;
	}
	
	public boolean clienteInativo(String email, String senha) throws SQLException {
		String sql = "select * from cliente where dataExclusao is not null and email=? and senha=?";
		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setString(1, email);
		statement.setString(2, senha);
		ResultSet resultSet = statement.executeQuery();
		boolean resultado = resultSet.first();
		resultSet.close();
		statement.close();
		return resultado;
	}
	
	/**
	 * Recadastramento de cliente. Ativa o cliente, ou seja, atualizar a data de
	 * exclusao como nula.
	 * 
	 * @param email o email do cliente que ser� atualizado
	 * @param senha a nova senha do cliente
	 * @param dataCadastro a nova data de cadastro
	 * @throws SQLException 
	 */
	public void recadastrar(String email, String senha, Calendar dataCadastro) throws SQLException {
		String sql = "update cliente set dataCadastro=?, senha=?, statusCliente='false', dataExclusao=null where email=?";
		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setDate(1, new Date(dataCadastro.getTimeInMillis()));
		statement.setString(2, senha);
		statement.setString(3, email);
		statement.execute();
		statement.close();	
	}

	public int getId(String usuario) throws SQLException {
		String sql = "select idCliente from cliente where email=?";
		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setString(1, usuario);
		ResultSet resultSet = statement.executeQuery();
		int idCliente = 0;
		if(resultSet.first()){
			idCliente = resultSet.getInt("idCliente");
		}
		resultSet.close();
		statement.close();
		return idCliente;
	}

	public List<Cliente> getClientesPendentes() throws SQLException {
		String sql = 
			"select " +
			"idCliente, nome, tipoCliente, email, senha, tipoDoc, numDoc, dataCadastro " +
			"from cliente where tipoCliente <> 'A' and dataExclusao is null and statusCliente='false' " +
			"order by dataCadastro";
		
		PreparedStatement statement = conexao.prepareStatement(sql);
		ResultSet result = statement.executeQuery();	
		
		List<Cliente> clientes = new ArrayList<Cliente>();
		while (result.next()){			
			Calendar dataCadastro = Calendar.getInstance();
			dataCadastro.setTime(result.getDate("dataCadastro"));					
			
			Telefone telefone = new Telefone();				
			Endereco endereco = new Endereco();				
			List<Orcamento> orcamentos = new ArrayList<Orcamento>();
			clientes.add(new Cliente(
					result.getInt("idCliente"),
					result.getString("nome"),
					result.getString("tipoCliente"),
					result.getString("email"),
					TipoDoDocumento.criarPorCodigo(result.getString("tipoDoc")),
					result.getString("numDoc"),
					telefone,
					endereco,
					result.getString("senha"),
					false,
					dataCadastro,
					null,
					orcamentos));			
		}		
		result.close();
		statement.close();		
		return clientes;
	}

	public List<Cliente> getAllPorFiltro(String filtro, String parametro) throws SQLException {
		String sql = 
			"select " +
			"idCliente, nome, tipoCliente, statusCliente, email, senha, tipoDoc, numDoc, dataCadastro " +
			"from cliente where tipoCliente <> 'A' and dataExclusao is null and ?1 like '%?2%'"
			.replace("?1", filtro).replace("?2", parametro);
		
		PreparedStatement statement = conexao.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		
		List<Cliente> clientes = new ArrayList<Cliente>();
		while (resultSet.next()){			
			Calendar dataCadastro = Calendar.getInstance();
			dataCadastro.setTime(resultSet.getDate("dataCadastro"));					
			
			Telefone telefone = new Telefone();				
			Endereco endereco = new Endereco();				
			List<Orcamento> orcamentos = new ArrayList<Orcamento>();
			clientes.add(new Cliente(
					resultSet.getInt("idCliente"),
					resultSet.getString("nome"),
					resultSet.getString("tipoCliente"),
					resultSet.getString("email"),
					TipoDoDocumento.criarPorCodigo(resultSet.getString("tipoDoc")),
					resultSet.getString("numDoc"),
					telefone,
					endereco,
					resultSet.getString("senha"),
					Boolean.valueOf(resultSet.getString("statusCliente")),
					dataCadastro,
					null,
					orcamentos));			
		}		
		resultSet.close();
		statement.close();		
		return clientes;		
	}

	public String getClientePorOrcamento(int idOrcamento) throws SQLException {
		String sql = "select c.email, c.nome from cliente c, orcamento o " + 
		"where c.idCliente=o.idCliente and o.idOrcamento=?";		
		
		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setInt(1, idOrcamento);
		ResultSet resultSet = statement.executeQuery();
		String resultado = "";
		if(resultSet.first()){
			resultado = resultSet.getString("c.email") + ";" + resultSet.getString("c.nome");
		}
		resultSet.close();
		statement.close();
		return resultado;
	}
	
	
	public Cliente getPorOrcamento(int idOrcamento) throws SQLException {
		String sql = 
			"select " +
			"c.idCliente, c.nome, c.tipoCliente, c.statusCliente, c.email, c.senha, c.tipoDoc, c.numDoc, c.dataCadastro, c.dataExclusao " +
			"from cliente c, orcamento o where c.idCliente=o.idCliente and o.idOrcamento=?";
		
		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setInt(1, idOrcamento);
		ResultSet resultSet = statement.executeQuery();
		Cliente cliente = null;
		if (resultSet.first()){			
			Calendar dataCadastro = Calendar.getInstance();
			dataCadastro.setTime(resultSet.getDate("dataCadastro"));					
			
			Telefone telefone = new Telefone();				
			Endereco endereco = new Endereco();				
			List<Orcamento> orcamentos = new ArrayList<Orcamento>();
			cliente = new Cliente(
					resultSet.getInt("c.idCliente"),
					resultSet.getString("c.nome"),
					resultSet.getString("c.tipoCliente"),
					resultSet.getString("c.email"),
					TipoDoDocumento.criarPorCodigo(resultSet.getString("c.tipoDoc")),
					resultSet.getString("c.numDoc"),
					telefone,
					endereco,
					resultSet.getString("c.senha"),
					Boolean.valueOf(resultSet.getString("c.statusCliente")),
					dataCadastro,
					null,
					orcamentos);	
		}
		resultSet.close();
		statement.close();		
		return cliente;
	}				
}