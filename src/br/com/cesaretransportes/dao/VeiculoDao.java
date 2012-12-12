package br.com.cesaretransportes.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.cesaretransportes.modelo.Veiculo;

public class VeiculoDao {

	private Connection conexao;
	
	public VeiculoDao(Connection conexao){
		this.conexao = conexao;
	}
	
	public void cadastrar(Veiculo veiculo) throws ClassNotFoundException, SQLException {		
		String sql = "insert into veiculo (marca, cor, placa, localizacao) values ('?1', '?2', '?3', '?4')";				
			
		String sqlFormat = sql
			.replace("?1", veiculo.getMarca())
			.replace("?2", veiculo.getCor())
			.replace("?3", veiculo.getPlaca())
			.replace("?4", veiculo.getLocalizacao());	
		
		PreparedStatement statement = conexao.prepareStatement(sqlFormat);
		statement.execute();
		statement.close();		
	}
	
	/**
	 * Recadastra um veiculo na base, ou seja, faz atualizações nos campos
	 * parametrizados e atualiza a data de exclusão como nula.
	 * 
	 * @param idVeiculo
	 *            o id do veículo a ser atualizado.
	 * @param marca
	 *            a marca do veículo.
	 * @param cor
	 *            a cor do veículo.
	 * @param localizacao
	 *            a localização atual do veiculo.
	 * @throws SQLException
	 */
	public void recadastrar(int idVeiculo, String marca, String cor, String localizacao) throws SQLException {
		String sql = "update veiculo set dataExclusao=null, marca=?, cor=?, localizacao=? where idVeiculo=?";
		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setString(1, marca);
		statement.setString(2, cor);
		statement.setString(3, localizacao);
		statement.setInt(4, idVeiculo);
		statement.execute();
		statement.close();		
	}

	/**
	 * 
	 * @param orcamentoDao 
	 * @return uma listagem dos veículos cadastrados na base de dados, ordenados pela placa.
	 * @throws SQLException
	 */
	public List<Veiculo> getAll() throws SQLException {
		String sql = "select idVeiculo, marca, cor, placa, localizacao, dataExclusao from veiculo " +
				"where dataExclusao is null order by placa";
		
		List<Veiculo> veiculos = new ArrayList<Veiculo>();
		
		PreparedStatement statement = conexao.prepareStatement(sql);
		ResultSet result = statement.executeQuery();
		while (result.next()){
			Veiculo veiculo = new Veiculo();
			veiculo.setIdVeiculo(result.getInt("idVeiculo"));
			veiculo.setMarca(result.getString("marca"));
			veiculo.setCor(result.getString("cor"));
			veiculo.setPlaca(result.getString("placa"));			
			veiculo.setLocalizacao(result.getString("localizacao"));
			if(result.getDate("dataExclusao") == null){
				veiculo.setDataExclusao(null);
			}else{
				Calendar dataExclusao = Calendar.getInstance();
				dataExclusao.setTime(result.getDate("dataExclusao"));
				veiculo.setDataExclusao(dataExclusao);
			}		
						
			veiculos.add(veiculo);			
		}
		
		return veiculos;
	}

	/**
	 * exclusão lógica de um veículo da base de dados. Atualiza o campo <code>dataExclusao</code>
	 * com a data atual.
	 * 
	 * @param id
	 * @throws SQLException
	 */
	public void deletar(int id) throws SQLException {
		Calendar dataHoje = Calendar.getInstance();
		Date dataExclusao = new Date(dataHoje.getTimeInMillis());
		
		String sql = "update veiculo set dataExclusao=? where idVeiculo=?";
		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setDate(1, dataExclusao);
		statement.setInt(2, id);
		statement.execute();
		statement.close();		
	}

	public Veiculo getVeiculo(int id) throws SQLException {
		String sql = "select idVeiculo, marca, cor, placa, localizacao, dataExclusao from veiculo where idVeiculo=" + id;
		
		Veiculo veiculo = new Veiculo();
		PreparedStatement statement = conexao.prepareStatement(sql);
		ResultSet result = statement.executeQuery();
		if (result.first()){
			veiculo.setIdVeiculo(id);
			veiculo.setMarca(result.getString("marca"));
			veiculo.setCor(result.getString("cor"));
			veiculo.setPlaca(result.getString("placa"));			
			veiculo.setLocalizacao(result.getString("localizacao"));
			
			if(result.getDate("dataExclusao") == null){
				veiculo.setDataExclusao(null);
			}else{
				Calendar dataExclusao = Calendar.getInstance();
				dataExclusao.setTime(result.getDate("dataExclusao"));
				veiculo.setDataExclusao(dataExclusao);
			}
						
			//veiculo.setOrcamentos(orcamentoDao.getListaPorVeiculo(veiculo.getIdVeiculo(), false, true));
			return veiculo;
		}
		return null;
	}

	/**
	 * Esdita a localização atual de um veiculo.
	 * @param veiculo o veiculo a ser editado
	 * @throws SQLException
	 */
	public void editar(Veiculo veiculo) throws SQLException {
		String sql = "update veiculo set localizacao=? where idVeiculo=?";	
		
		PreparedStatement statement = conexao.prepareStatement(sql);
		
		statement.setString(1, veiculo.getLocalizacao());		
		statement.setInt(2, veiculo.getIdVeiculo());
		
		statement.execute();
		statement.close();		
	}

	/**
	 * verifica se um veiculo ja esta cadastrado na base de dados
	 * @param placa a placa do veículo a ser consultado
	 * @param ativo <code>true</code> para referenciar veículo ativo, ou seja, com data de
	 * exclusão nula, <code>false</code> para veículo inativos, ou seja, veículos deletados
	 * cuja data de exclusão é nula.
	 * @return
	 * @throws SQLException
	 */
	public boolean veiculoJaCadastrado(String placa, boolean ativo) throws SQLException {
		String condicao = ativo ? "and dataExclusao is null" : "and dataExclusao is not null";
		String sql = "select idVeiculo from veiculo where placa=? " + condicao;
		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setString(1, placa.toUpperCase());
		ResultSet resultSet = statement.executeQuery();
		
		boolean resultado = resultSet.first();
		resultSet.close();
		statement.close();
		return resultado;
	}

	public Veiculo getVeiculo(String placa, OrcamentoDao orcamentoDao) throws SQLException {
		String sql = "select idVeiculo, marca, cor, placa, localizacao, dataExclusao from veiculo where placa=?";
		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setString(1, placa);
		
		ResultSet resultSet = statement.executeQuery();
		if(resultSet.first()){
			Veiculo veiculo = new Veiculo();
			veiculo.setIdVeiculo(resultSet.getInt("idVeiculo"));
			veiculo.setMarca(resultSet.getString("marca"));
			veiculo.setCor(resultSet.getString("cor"));
			veiculo.setPlaca(resultSet.getString("placa"));
			veiculo.setLocalizacao(resultSet.getString("localizacao"));
			if(resultSet.getDate("dataExclusao") == null){
				veiculo.setDataExclusao(null);
			}else{
				Calendar dataExclusao = Calendar.getInstance();
				dataExclusao.setTime(resultSet.getDate("dataExclusao"));
				veiculo.setDataExclusao(dataExclusao);
			}
			
			return veiculo;
		}
		return null;
	}	
}