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
		String sql = "select idEmpresa, nome, cnpj, msn, email, senha from empresa where idEmpresa=" + idEmpresa;
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
					telefones);
		}
		return null;
	}

}
