package br.com.cesaretransportes.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class AbstractConnectionFactory {
	
	public static Connection getConexao() throws ClassNotFoundException {
				
		String url = "jdbc:mysql://localhost/cesaretransportes";
		
		
		String usuario = "cetrans2";
		String senha = "53340276";	
		
		//String usuario = "root";
		//String senha = "root";
		
		
		String driver = "com.mysql.jdbc.Driver";
		
		/*
		 * base de dados hsqldb. banco precisa ser criado na memória
		 */
		/*String url = "jdbc:hsqldb:file:C:/hsqldb_cesaretransportes/BD_cesaretransportes";
		String usuario = "SA";
		String senha = "";
		String driver = "org.hsqldb.jdbcDriver";*/
		
		try {
			Class.forName(driver);
			return DriverManager.getConnection(url, usuario, senha);
		} catch (SQLException e) {			
			throw new RuntimeException(e);
		}
	}
}