package br.com.cesaretransportes.util;

import br.com.cesaretransportes.servlet.VeiculoServlet;

/**
 * Classe abstrata que define constantes para a servlet {@link VeiculoServlet}
 * manipular ações CRUD dos veículos na base de dados.
 * 
 * @author Cesar Sousa.
 *
 */
public abstract class AcaoVeiculo {
	
	/**
	 * Sinaliza para a página mostrar-veiculo.jsp renderizar o formulário de cadastrar veículo.
	 */
	public static final int ACESSAR_CADASTRO = 1;
	
	/**
	 * Valida dados do formulário, cadastra e altera informações sobre o veículo na base de dados.
	 */
	public static final int CADASTRAR = 2;
	
	/**
	 * Sinaliza para a página mostrar-veiculo.jsp renderizar os veículos cadastrados.
	 */
	public static final int LISTAR_CADASTRADOS = 3;
	
	/**
	 * Deleta um veículos da base de dados.
	 */
	public static final int DELETAR = 4;
	
	/**
	 * Sinaliza para a página mostrar-veiculo.jsp renderizar o formulário para editar o veículo.
	 */
	public static final int ACESSAR_EDITAR = 5;
	
	/**
	 * Altera os dados do veículo na base de dados.
	 */
	public static final int EDITAR = 6;
	
	/**
	 * Busca de veículos na bas de dados.
	 */
	public static final int BUSCA = 7;
	
	/**
	 * Visualizar todos os orçamentos relacionados a um determinado veículo.
	 */
	public static final int VISUALIZAR_ORCAMENTOS = 8;
	
	/**
	 * Marca como finalizadas todas as entregas relacionadas a um veículo.
	 */
	public static final int CONFIRMAR_ENTREGAS = 9;
	
	/**
	 * Recadastra um veículo na base de dados.
	 */
	public static final int RECADASTRAR_VEICULO = 10;
	
	
	
}
