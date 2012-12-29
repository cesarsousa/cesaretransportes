package br.com.cesaretransportes.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.cesaretransportes.modelo.Empresa;
import br.com.cesaretransportes.modelo.Endereco;
import br.com.cesaretransportes.modelo.Telefone;

public class EmpresaDao {
	
	private Connection conexao;
	
	public EmpresaDao(Connection conexao) {
		this.conexao = conexao;
	}

	public Empresa get() throws SQLException {
		return get(2);
	}
	
	public Empresa get(int idEmpresa) throws SQLException {
		String sql = "select idEmpresa, nome, cnpj, msn, email, senha, mostrarMapa, localizacao from empresa where idEmpresa=" + idEmpresa;
		PreparedStatement statement = conexao.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		if(resultSet.first()){
			Endereco endereco = new Endereco();		
			List<Telefone> telefones = new ArrayList<Telefone>();
			
			return new Empresa(
					resultSet.getInt("idEmpresa"), 
					resultSet.getString("nome"),
					endereco, 
					resultSet.getString("cnpj"),
					resultSet.getString("msn"),
					resultSet.getString("email"), 
					resultSet.getString("senha"),
					resultSet.getBoolean("mostrarMapa"),
					resultSet.getString("localizacao"),
					telefones);
		}
		return null;
	}
	
	public void atualizar(Empresa empresa, EnderecoDao enderecoDao, TelefoneDao telefoneDao) throws SQLException {
		
		String sqlTelefone = "UPDATE telefone SET ddd='?1', numero='?2', complemento='?3' WHERE idTelefone='?4'";
		String sqlEndereco = "UPDATE endereco SET cidade='?1', estado='?2', localizacao='?3' WHERE idEndereco='?4'";
		String sqlEmpresa = "UPDATE empresa SET nome='?1', cnpj='?2', email='?3', senha='?4', msn='?5', mostrarMapa='?6', localizacao='?7' WHERE idEmpresa='?8'";
		
		
	}

}
