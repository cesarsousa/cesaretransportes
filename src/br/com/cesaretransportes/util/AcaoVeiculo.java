package br.com.cesaretransportes.util;

import br.com.cesaretransportes.servlet.VeiculoServlet;

/**
 * Classe abstrata que define constantes para a servlet {@link VeiculoServlet}
 * manipular a��es CRUD dos ve�culos na base de dados.
 * 
 * @author Cesar Sousa.
 *
 */
public abstract class AcaoVeiculo {
	
	/**
	 * Sinaliza para a p�gina mostrar-veiculo.jsp renderizar o formul�rio de cadastrar ve�culo.
	 */
	public static final int ACESSAR_CADASTRO = 1;
	
	/**
	 * Valida dados do formul�rio, cadastra e altera informa��es sobre o ve�culo na base de dados.
	 */
	public static final int CADASTRAR = 2;
	
	/**
	 * Sinaliza para a p�gina mostrar-veiculo.jsp renderizar os ve�culos cadastrados.
	 */
	public static final int LISTAR_CADASTRADOS = 3;
	
	/**
	 * Deleta um ve�culos da base de dados.
	 */
	public static final int DELETAR = 4;
	
	/**
	 * Sinaliza para a p�gina mostrar-veiculo.jsp renderizar o formul�rio para editar o ve�culo.
	 */
	public static final int ACESSAR_EDITAR = 5;
	
	/**
	 * Altera os dados do ve�culo na base de dados.
	 */
	public static final int EDITAR = 6;
	
	/**
	 * Busca de ve�culos na bas de dados.
	 */
	public static final int BUSCA = 7;
	
	/**
	 * Visualizar todos os or�amentos relacionados a um determinado ve�culo.
	 */
	public static final int VISUALIZAR_ORCAMENTOS = 8;
	
	/**
	 * Marca como finalizadas todas as entregas relacionadas a um ve�culo.
	 */
	public static final int CONFIRMAR_ENTREGAS = 9;
	
	/**
	 * Recadastra um ve�culo na base de dados.
	 */
	public static final int RECADASTRAR_VEICULO = 10;
	
	
	
}
