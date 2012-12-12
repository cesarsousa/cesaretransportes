package br.com.cesaretransportes.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.cesaretransportes.modelo.Telefone;

public class TelefoneDao {
	
	private Connection conexao;

	public TelefoneDao(Connection conexao) {
		this.conexao = conexao;
	}

	public Telefone get(int idCliente) throws SQLException {
		String sql = "select idTelefone, idEmpresa, idCliente, ddd, numero, complemento" +
				" from telefone where idCliente=" + idCliente;
		PreparedStatement statement = conexao.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		Telefone telefone = null;
		if(resultSet.first()){
			telefone = new Telefone(
					resultSet.getInt("idTelefone"),
					resultSet.getInt("idEmpresa"), 
					resultSet.getInt("idCliente"),
					resultSet.getString("ddd"), 
					resultSet.getString("numero"), 
					resultSet.getString("complemento"));			
		}
		resultSet.close();
		statement.close();
		return telefone;
	}

	public void cadastrar(Telefone telefone) throws SQLException {
		String sql = "insert into telefone (idEmpresa, idCliente, ddd, numero, complemento) values (?, ?, ?, ?, ?)";
		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setInt(1, telefone.getIdEmpresa());
		statement.setInt(2, telefone.getIdCliente());
		statement.setString(3, telefone.getDdd());
		statement.setString(4, telefone.getNumero());
		statement.setString(5, telefone.getComplemento() == null || telefone.getComplemento().isEmpty() ? null : telefone.getComplemento());
		statement.execute();
		statement.close();		
	}

	public void alterar(int idCliente, String ddd, String numero, String complemento) throws SQLException {
		String sql = "update telefone set ddd=?, numero=?, complemento=? where idCliente=?";
		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setString(1, ddd);
		statement.setString(2, numero);
		statement.setString(3, complemento.isEmpty() ? null : complemento);
		statement.setInt(4, idCliente);
		statement.execute();
		statement.close();	
	}

	public List<Telefone> getTelefonesEmpresa(int idEmpresa) throws SQLException {
		String sql = "select " +
				"idTelefone, idEmpresa, idCliente, ddd, numero, complemento " +
				"from telefone where idEmpresa=" + idEmpresa;
		PreparedStatement statement = conexao.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		
		List<Telefone> telefones = new ArrayList<Telefone>();
		while(resultSet.next()){
			Telefone telefone = new Telefone(
					resultSet.getInt("idTelefone"),
					resultSet.getInt("idEmpresa"), 
					resultSet.getInt("idCliente"),
					resultSet.getString("ddd"), 
					resultSet.getString("numero"), 
					resultSet.getString("complemento"));
			telefones.add(telefone);
		}
		
		resultSet.close();
		statement.close();
		return telefones;
	}
}
