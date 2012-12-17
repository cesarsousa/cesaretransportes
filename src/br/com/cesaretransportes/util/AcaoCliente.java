package br.com.cesaretransportes.util;

import br.com.cesaretransportes.servlet.ClienteAcaoServlet;

/**
 * Classe abstrata que define constantes para a servlet {@link ClienteAcaoServlet} manipular
 * a��es CRUD dos clientes na base de dados.
 *  
 * @author cesar sousa.
 *
 */
public abstract class AcaoCliente {
	
	/**
	 * Listar todos os clientes cadastrados na base de dados. 
	 */
	public static final int LISTAR_TODOS = 1;
	
	/**
	 * Exclusao logicaum cliente da base de dados.
	 */
	public static final int DELETAR = 2;

	/**
	 * Confirmacao do cliente para realizar download.
	 */
	public static final int CONFIRMAR = 3;

	/**
	 * Opcao para realizar uma busca na base de dados.
	 */
	public static final int BUSCAR = 4;
	
	/**
	 * Listar todos os orcamentos referentes a um cliente.
	 */
	public static final int LISTAR_ORCAMENTOS = 5;
	
	/**
	 * Listar todos os servicos referentes a um cliente.
	 */
	public static final int LISTAR_SERVICOS = 6;
	
	/**
	 * Lista todos os clientes com data de exclusao nula.
	 */
	public static final int ATIVO = 7;
	
	/**
	 * Lista todos os clientes com data de exclusao nao nula.
	 */
	public static final int INATIVO = 8;

}
