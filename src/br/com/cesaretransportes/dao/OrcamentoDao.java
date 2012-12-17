package br.com.cesaretransportes.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.cesaretransportes.modelo.Orcamento;

public class OrcamentoDao {
	
	private Connection conexao;
	private ClienteDao clienteDao;	
	
	public OrcamentoDao(Connection conexao) {
		this.conexao = conexao;
		clienteDao = new ClienteDao(conexao);		
	}

	public int cadastrarOrcamento(Orcamento orcamento) throws SQLException {
		String sql = "insert into orcamento (idCliente, peso, dimensao, mensagem, orcamentoLido, orcamentoRespondido, " +
						"dataCadastro, dataExclusao)"
				+ " values (?,?,?,?,?,?,?,?)";
		
			PreparedStatement statement = conexao.prepareStatement(sql);
			statement.setInt(1, orcamento.getCliente().getIdCliente());			
			statement.setString(2, orcamento.getPeso());
			statement.setString(3, orcamento.getDimensao());
			statement.setString(4, orcamento.getMensagem());			
			statement.setString(5, "false");
			statement.setString(6, "false");
			Date dtCad = new Date(orcamento.getDataCadastro().getTimeInMillis());
			statement.setDate(7, dtCad);
			statement.setDate(8, null);
			
			statement.execute();
			
			sql = "select max(idOrcamento) as ultimoId from orcamento;";
			statement = conexao.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			int idOrcamento = 0;
			if(resultSet.first()){
				idOrcamento = resultSet.getInt("ultimoId");	
			}					
			statement.close();
			return idOrcamento;
	}
	
	/**
	 * Metodo utilitario para listar todos os orcamentos de um cliente.
	 * <p>Faz uma chamada ao metodo OrcamentoDao#getListaDeOrcamentos(String, String, int) para
	 * listar todos os orcamentos, e em seguida filtra pelo id do cliente.</p>
	 * 
	 * @param idCliente id do cliente, se id for <code>0</code> lista todos os cliente.
	 * @param filtro filtro para ordenacao (nome da tabela no banco)
	 * @param tipoOrdenacao 0 para ordenacao crescente, 1 para ordenacao descrescente.
	 * @return
	 * @throws SQLException	 
	 */
	public List<Orcamento> getListaDeOrcamentos(int idCliente, String filtro, int tipoOrdenacao) throws SQLException {
		if(idCliente == 0){
			return getListaDeOrcamentos(filtro, tipoOrdenacao);
		}		
		
		List<Orcamento> orcamentos = new ArrayList<Orcamento>();
		for(Orcamento orcamento : getListaDeOrcamentos(filtro, tipoOrdenacao)){
			if(orcamento.getCliente().getIdCliente() == idCliente){
				orcamentos.add(orcamento);
			}
		}
		return orcamentos;
	}

	/**
	 * Metodo utilitario para a listar todos os orcamentos cadastrados.
	 * 
	 * @param filtro
	 *            parametro de ordenacao para exibicao dos orcamentos.
	 * @param tipoOrdenacao 0 para ordenacao crescente, 1 para ordenacao descrescente.
	 * @return lista dos orcamentos que nao foram logicamente excluidos excluidos 
	 * @throws SQLException 
	 */
	public List<Orcamento> getListaDeOrcamentos(String filtro, int tipoOrdenacao) throws SQLException {
		String ordenacao = tipoOrdenacao == 0 ? "asc" : "desc";
		
		String condicaoExclusao = "where dataExclusao is null";
		
//		String sql = "select " +
//				"idOrcamento, idCliente, peso, dimensao, mensagem, orcamentoLido, orcamentoRespondido, dataCadastro, dataExclusao " +
//				"from orcamento  where dataExclusao is null order by " + filtro + " " + ordenacao;
		
		String sql = "select " +
				"idOrcamento, idCliente, peso, dimensao, mensagem, orcamentoLido, orcamentoRespondido, dataCadastro, dataExclusao " +
				"from orcamento order by " + filtro + " " + ordenacao;

		List<Orcamento> listaDeOrcamentos = new ArrayList<Orcamento>();
		PreparedStatement statement = this.conexao.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {
			Orcamento orcamento = new Orcamento();
			orcamento.setIdOrcamento(resultSet.getInt("idOrcamento"));
			orcamento.setCliente(clienteDao.getCliente(resultSet.getInt("idCliente")));
			orcamento.setPeso(resultSet.getString("peso"));
			orcamento.setDimensao(resultSet.getString("dimensao"));
			orcamento.setMensagem(resultSet.getString("mensagem"));
			orcamento.setOrcamentoLido(Boolean.valueOf(resultSet.getString("orcamentoLido")));
			orcamento.setOrcamentoRespondido(Boolean.valueOf(resultSet.getString("orcamentoRespondido")));

			Calendar dataCadastro = Calendar.getInstance();
			dataCadastro.setTime(resultSet.getDate("dataCadastro"));
			orcamento.setDataCadastro(dataCadastro);

			if (resultSet.getDate("dataExclusao") == null) {
				orcamento.setDataExclusao(null);
			} else {
				Calendar dataExclusao = Calendar.getInstance();
				dataExclusao.setTime(resultSet.getDate("dataCadastro"));
				orcamento.setDataExclusao(dataExclusao);
			}

			if (orcamento.getDataExclusao() == null) {
				listaDeOrcamentos.add(orcamento);
			}		
		}
		resultSet.close();
		statement.close();
		return listaDeOrcamentos;
	}

	/**
	 * 
	 * @return todos os orcamentos cadastrados no banco.
	 * @throws SQLException
	 */
	public List<Orcamento> getListaDeOrcamentos() throws SQLException {
		List<Orcamento> listaDeOrcamentos = new ArrayList<Orcamento>();
		
		String sql = "select * from orcamento order by idOrcamento";

		PreparedStatement statement = this.conexao.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {
			Orcamento orcamento = new Orcamento();
			orcamento.setIdOrcamento(resultSet.getInt("idOrcamento"));
			orcamento.setCliente(clienteDao.getCliente(resultSet.getInt("idCliente")));			
			orcamento.setPeso(resultSet.getString("peso"));
			orcamento.setDimensao(resultSet.getString("dimensao"));
			orcamento.setMensagem(resultSet.getString("mensagem"));
			orcamento.setOrcamentoLido(Boolean.valueOf(resultSet.getString("orcamentoLido")));
			orcamento.setOrcamentoRespondido(Boolean.valueOf(resultSet.getString("orcamentoRespondido")));
			
			Calendar dataCadastro = Calendar.getInstance();
			dataCadastro.setTime(resultSet.getDate("dataCadastro"));
			orcamento.setDataCadastro(dataCadastro);

			if (resultSet.getDate("dataExclusao") == null) {
				orcamento.setDataExclusao(null);
			} else {
				Calendar dataExclusao = Calendar.getInstance();
				dataExclusao.setTime(resultSet.getDate("dataExclusao"));
				orcamento.setDataExclusao(dataExclusao);
			}		
			
			listaDeOrcamentos.add(orcamento);
		}
		return listaDeOrcamentos;
			
	}

	/**
	 * exclusao logica de um orcamento da base de dados. Atualiza o campo <code>dataExclusao</code>
	 * com a data atual.
	 * 
	 * @param id
	 * @throws SQLException
	 */
	public void excluirOrcamento(int idOrcamento) throws SQLException {
		Calendar dataHoje = Calendar.getInstance();
		Date dataExclusao = new Date(dataHoje.getTimeInMillis());
		
		String sql = "update orcamento set dataExclusao=? where idOrcamento=?";
		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setDate(1, dataExclusao);
		statement.setInt(2, idOrcamento);
		statement.execute();
		statement.close();		
	}

	public void marcaStatusDeOrcamentoLido(int idOrcamento, boolean status) throws SQLException {
		String strStatus = String.valueOf(status);
		String sql = "update orcamento set orcamentoLido='" + strStatus	+ "' where idOrcamento='" + idOrcamento + "'";
		PreparedStatement statement = this.conexao.prepareStatement(sql);
		statement.execute();
		statement.close();
	}
	
	public void marcaStatusDeOrcamentoRespondido(int idOrcamento) throws SQLException {		
		String sql = "update orcamento set orcamentoRespondido='true' where idOrcamento='" + idOrcamento + "'";
		PreparedStatement statement = this.conexao.prepareStatement(sql);
		statement.execute();
		statement.close();
		
	}

	public int getCodigoDoOrcamento(int idCliente, Calendar dataCadastro) throws SQLException {
		String sql = "select idOrcamento from orcamento where idCliente=? and dataCadastro=?";
		
		PreparedStatement statement = this.conexao.prepareStatement(sql);
		statement.setInt(1, idCliente);
		statement.setDate(2, new Date(dataCadastro.getTimeInMillis()));		
		ResultSet resultSet = statement.executeQuery();

		int idOrcamento = 0;
		if (resultSet.next()) {
			idOrcamento = Integer.valueOf(resultSet.getString("idOrcamento"));
		}
		statement.close();
		return idOrcamento;	
	}

	
	public void updateNovaMensagem(String novaMensagem, int idOrcamento) throws SQLException {
		String sql = "update orcamento set mensagem='" + novaMensagem
				+ "' where idOrcamento='" + idOrcamento + "'";

		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.execute();
		statement.close();
	}

	public Orcamento getOrcamento(int id) throws SQLException {
		String sql = "select " +
				"idOrcamento, idCliente, peso, dimensao, mensagem, orcamentoLido, orcamentoRespondido, dataCadastro, dataExclusao " +
				"from orcamento where idOrcamento=?"
						.replace("?", String.valueOf(id));
		
		PreparedStatement statement = conexao.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		Orcamento orcamento = null;
		if(resultSet.first()){
			orcamento = new Orcamento();			
			orcamento.setIdOrcamento(resultSet.getInt("idOrcamento"));
			orcamento.setCliente(clienteDao.getCliente(resultSet.getInt("idCliente")));
			orcamento.setPeso(resultSet.getString("peso"));
			orcamento.setDimensao(resultSet.getString("dimensao"));
			orcamento.setMensagem(resultSet.getString("mensagem"));
			orcamento.setOrcamentoLido(Boolean.valueOf(resultSet.getString("orcamentoLido")));
			orcamento.setOrcamentoRespondido(Boolean.valueOf(resultSet.getString("orcamentoRespondido")));

			Calendar dataCadastro = Calendar.getInstance();
			dataCadastro.setTime(resultSet.getDate("dataCadastro"));
			orcamento.setDataCadastro(dataCadastro);

			if (resultSet.getDate("dataExclusao") == null) {
				orcamento.setDataExclusao(null);
			} else {
				Calendar dataExclusao = Calendar.getInstance();
				dataExclusao.setTime(resultSet.getDate("dataCadastro"));
				orcamento.setDataExclusao(dataExclusao);
			}						
		}
		return orcamento;
	}	
}