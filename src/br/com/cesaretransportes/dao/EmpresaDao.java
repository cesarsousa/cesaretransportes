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
		return get(1);
	}
	
	public Empresa get(int idEmpresa) throws SQLException {
		String sql = "select idEmpresa, nome, cnpj, msn, email, senha, mostrarMapa, localizacao from empresa where idEmpresa=" + idEmpresa;
		return obterEmpresa(sql);
	}
	
	public Empresa get(String email, String senha) throws SQLException {
		String sql = "select idEmpresa, nome, cnpj, msn, email, senha, mostrarMapa, localizacao from empresa where email='?1' and senha='?2' "
				.replace("?1", email).replace("?2", senha);
		return obterEmpresa(sql);
	}	
	
	public void atualizar(Empresa empresa, EnderecoDao enderecoDao, TelefoneDao telefoneDao) throws SQLException {
				
		String sqlEndereco = "UPDATE endereco SET cidade='?1', estado='?2', localizacao='?3' WHERE idEndereco='?4'"
				.replace("?1", empresa.getEndereco().getCidade())
				.replace("?2", empresa.getEndereco().getEstado())
				.replace("?3", empresa.getEndereco().getLocalizacao())
				.replace("?4", String.valueOf(empresa.getEndereco().getIdEndereco()));		
		
		String sqlTelefone1 = "UPDATE telefone SET ddd='?1', numero='?2', complemento='?3' WHERE idTelefone='?4'"
				.replace("?1", empresa.getTel1().getDdd())
				.replace("?2", empresa.getTel1().getNumero())
				.replace("?3", empresa.getTel1().getComplemento())
				.replace("?4", String.valueOf(empresa.getTel1().getIdTelefone()));
		
		
		String sqlTelefone2 = "UPDATE telefone SET ddd='?1', numero='?2', complemento='?3' WHERE idTelefone='?4'"
				.replace("?1", empresa.getTel2().getDdd())
				.replace("?2", empresa.getTel2().getNumero())
				.replace("?3", empresa.getTel2().getComplemento())
				.replace("?4", String.valueOf(empresa.getTel2().getIdTelefone()));
		
		
		String sqlTelefone3 = "UPDATE telefone SET ddd='?1', numero='?2', complemento='?3' WHERE idTelefone='?4'"
				.replace("?1", empresa.getTel3().getDdd())
				.replace("?2", empresa.getTel3().getNumero())
				.replace("?3", empresa.getTel3().getComplemento())
				.replace("?4", String.valueOf(empresa.getTel3().getIdTelefone()));
		
		
		String sqlEmpresa = "UPDATE empresa SET nome='?1', cnpj='?2', email='?3', senha='?4', msn='?5', mostrarMapa='?6', localizacao='?7' WHERE idEmpresa='?8'"
				.replace("?1", empresa.getNome())
				.replace("?2", empresa.getCnpj())
				.replace("?3", empresa.getEmail())
				.replace("?4", empresa.getSenha())
				.replace("?5", empresa.getMsn())
				.replace("?6", String.valueOf(empresa.isMostrarMapa()))
				.replace("?7", empresa.getLocalizacao())
				.replace("?8", String.valueOf(empresa.getIdEmpresa()));
		
		PreparedStatement statement = conexao.prepareStatement(sqlEndereco);
		statement.execute();
		
		statement = conexao.prepareStatement(sqlTelefone1);
		statement.execute();
		
		statement = conexao.prepareStatement(sqlTelefone2);
		statement.execute();
		
		statement = conexao.prepareStatement(sqlTelefone3);
		statement.execute();
		
		statement = conexao.prepareStatement(sqlEmpresa);
		statement.execute();
		
		statement.close();
		conexao.close();		
	}	
	
	private Empresa obterEmpresa(String sql) throws SQLException {
		if(conexao == null) return new Empresa("UNDEFINED");
		
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

}
