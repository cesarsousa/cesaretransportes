package br.com.cesaretransportes.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.cesaretransportes.modelo.Cliente;
import br.com.cesaretransportes.modelo.Empresa;
import br.com.cesaretransportes.modelo.Endereco;
import br.com.cesaretransportes.modelo.Endereco.StatusEndereco;
import br.com.cesaretransportes.modelo.Orcamento;

public class EnderecoDao {
	
	private Connection conexao;

	public EnderecoDao(Connection conexao) {
		this.conexao = conexao;
	}

	public Endereco get(int idCliente) throws SQLException {
		String sql = "select idEndereco, idEmpresa, idCliente, idOrcamento, cidade, estado, localizacao, statusEndereco from endereco " +
				"where idCliente=" + idCliente;
		PreparedStatement statement = conexao.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		Endereco endereco = null;
		if(resultSet.first()){
			
			Empresa empresa = new Empresa();
			empresa.setIdEmpresa(resultSet.getInt("idEmpresa"));
			Cliente cliente = new Cliente();
			cliente.setIdCliente(resultSet.getInt("idCliente"));
			Orcamento orcamento = new Orcamento();
			orcamento.setIdOrcamento(resultSet.getInt("idOrcamento"));
			
			endereco = new Endereco(
					resultSet.getInt("idEndereco"), 
					empresa, 
					cliente, 
					orcamento, 
					resultSet.getString("cidade"), 
					resultSet.getString("estado"), 
					resultSet.getString("localizacao"), 
					StatusEndereco.criarPorCodigo(resultSet.getInt("statusEndereco")));
		}
		resultSet.close();
		statement.close();		
		return endereco;
	}

	public void cadastrarEndereco(Endereco endereco) throws SQLException {
		String sql = "insert into endereco (idEmpresa, idCliente, idOrcamento, cidade, estado, localizacao, statusEndereco) values (?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setInt(1, endereco.getEmpresa().getIdEmpresa());
		statement.setInt(2, endereco.getCliente().getIdCliente());
		statement.setInt(3, endereco.getOrcamento().getIdOrcamento());
		statement.setString(4, endereco.getCidade());
		statement.setString(5, endereco.getEstado());
		statement.setString(6, endereco.getLocalizacao());
		statement.setInt(7, endereco.getStatusEndereco().ordinal());
		statement.execute();
		statement.close();		
	}

	public void alterar(int idCliente, String localizacao, String cidade, String estado) throws SQLException {
		String sql = 
			"update endereco set localizacao=?, cidade=?, estado=? " +
			"where idCliente=?";
		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setString(1, localizacao);
		statement.setString(2, cidade);
		statement.setString(3, estado);
		statement.setInt(4, idCliente);
		statement.execute();
		statement.close();		
	}

	public List<Endereco> getEnderecosPorOrcamentos(int idOrcamento) throws SQLException {		
		List<Endereco> enderecos = new ArrayList<Endereco>();
		String sql = 
			"select " +
			"idEndereco, idEmpresa, idCliente, idOrcamento, cidade, estado, localizacao, statusEndereco " +
			"from endereco " +
			"where idOrcamento=" + idOrcamento;
		
		PreparedStatement statement = conexao.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			
			Empresa empresa = new Empresa();
			empresa.setIdEmpresa(resultSet.getInt("idEmpresa"));
			Cliente cliente = new Cliente();
			cliente.setIdCliente(resultSet.getInt("idCliente"));
			Orcamento orcamento = new Orcamento();
			orcamento.setIdOrcamento(resultSet.getInt("idOrcamento"));
			
			Endereco endereco = new Endereco(
					resultSet.getInt("idEndereco"), 
					empresa, 
					cliente, 
					orcamento, 
					resultSet.getString("cidade"), 
					resultSet.getString("estado"), 
					resultSet.getString("localizacao"), 
					StatusEndereco.criarPorCodigo(resultSet.getInt("statusEndereco")));
			enderecos.add(endereco);		
		}		
		
		return enderecos;
	}

	public Endereco getEnderecoEmpresa(int idEmpresa) throws SQLException {
		String sql = "select " +
				"idEndereco, idEmpresa, idCliente, idOrcamento, cidade, estado, localizacao, statusEndereco " +
				"from endereco " +
				"where idEmpresa=" + idEmpresa;
		PreparedStatement statement = conexao.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		Endereco endereco = null;
		if(resultSet.first()){
			
			Empresa empresa = new Empresa();
			empresa.setIdEmpresa(resultSet.getInt("idEmpresa"));
			Cliente cliente = new Cliente();
			cliente.setIdCliente(resultSet.getInt("idCliente"));
			Orcamento orcamento = new Orcamento();
			orcamento.setIdOrcamento(resultSet.getInt("idOrcamento"));
			
			endereco = new Endereco(
					resultSet.getInt("idEndereco"), 
					empresa, 
					cliente, 
					orcamento, 
					resultSet.getString("cidade"), 
					resultSet.getString("estado"), 
					resultSet.getString("localizacao"), 
					StatusEndereco.criarPorCodigo(resultSet.getInt("statusEndereco")));
		}
		resultSet.close();
		statement.close();		
		return endereco;
	}
}