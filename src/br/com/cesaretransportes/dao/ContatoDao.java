package br.com.cesaretransportes.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.cesaretransportes.modelo.Contato;

public class ContatoDao {

	private Connection conexao;

	public ContatoDao(Connection conexao) {
		this.conexao = conexao;
	}

	public void cadastrarContato(Contato contato) throws SQLException {
		String sql;
		if(contato.getIdCliente() > 0){
			sql = "insert into contato (nome, email, mensagem, data, emailLido, emailSalvo, idCliente)"
				+ " values (?,?,?,?,?,?,?)";
		}else{
			sql = "insert into contato (nome, email, mensagem, data, emailLido, emailSalvo)"
				+ " values (?,?,?,?,?,?)";
		}

		PreparedStatement statement = conexao.prepareStatement(sql);

		statement.setString(1, contato.getNome());
		statement.setString(2, contato.getEmail());
		statement.setString(3, contato.getMensagem());
		statement.setString(4, contato.getData());
		statement.setString(5, "nao");
		statement.setString(6, "nao");
		if(contato.getIdCliente() > 0){
			statement.setInt(7, contato.getIdCliente());
		}
		
		statement.execute();
		statement.close();
	}

	/**
	 * Metodo utilitario para a classe <i>MostrarListaDosEmailsServlet</i>
	 *  
	 * @param filtro
	 *  parametro de ordenacao para exibicao da lista dos email
	 * @param emailSalvo
	 * "nao" para exibir emails nao salvos e "sim" para exibir os email salvos
	 * @return lista dos emails
	 */
	public List<Contato> getListaDeContatos(String filtro, String emailSalvo) {
		try {
			String codicao = " where dataExclusao is null and emailSalvo='" + emailSalvo + "' ";
			String sql = "select * from contato " + codicao + " order by " + filtro + " desc";		

			List<Contato> listaDeContatos = new ArrayList<Contato>();
			PreparedStatement statement = conexao.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {

				Contato contato = new Contato();
				contato.setCodigo(resultSet.getString("codigo"));
				contato.setNome(resultSet.getString("nome"));
				contato.setEmail(resultSet.getString("email"));
				contato.setMensagem(resultSet.getString("mensagem"));
				contato.setData(resultSet.getString("data"));

				String status = resultSet.getString("emailLido");
				if ("sim".equals(status)) {
					contato.setEmailLido(true);
				} else {
					contato.setEmailLido(false);
				}

				listaDeContatos.add(contato);
			}
			resultSet.close();
			statement.close();			
			
			return listaDeContatos;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Metodo utilitario para a classe <i>BuscaServlet</i>
	 * 
	 * @param paramBusca
	 * @param opcao
	 * @param filtro
	 * @return
	 * @throws SQLException 
	 */
	public List<Contato> getListaDeContatos(String paramBusca, String opcao, String filtro) throws SQLException {
		List<Contato> listaDeContatos = new ArrayList<Contato>();
		List<Contato> listaAuxiliar = new ArrayList<Contato>();
		String sql = "select * from contato where dataExclusao is null order by codigo";
 
		PreparedStatement statement = this.conexao.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {
			Contato contato = new Contato();
			contato.setCodigo(resultSet.getString("codigo"));
			contato.setNome(resultSet.getString("nome"));
			contato.setEmail(resultSet.getString("email"));
			contato.setData(resultSet.getString("data"));
			contato.setMensagem(resultSet.getString("mensagem"));

			listaDeContatos.add(contato);
		}

		if ("codigo".equals(filtro)) {
			try {
				int cod = Integer.parseInt(paramBusca);
				for (Contato contato : listaDeContatos) {
					if (cod == contato.getCodigo()) {
						listaAuxiliar.add(contato);
					}
				}
				// caso a lista nao tenha o cod solicitado retorna uma a lista
				// vazia
				return listaAuxiliar;
			} catch (NumberFormatException e) {
				e.printStackTrace();
				// caso uma palavra seja passada como parametro para consultar
				// codigo
				// retorna uma lista vazia
				return new ArrayList<Contato>();
			}
		} else {
			// filtrar de acordo com a opcao select de index-sistema-interno.jsp
			if ("todos".equals(filtro)) {
				for (Contato contato : listaDeContatos) {
					if (contato.getNome().contains(paramBusca)
							|| contato.getEmail().contains(paramBusca)) {
						listaAuxiliar.add(contato);
					}
				}
				return listaAuxiliar;
			} else if ("nome".equals(filtro)) {
				for (Contato contato : listaDeContatos) {
					if (contato.getNome().contains(paramBusca)) {
						listaAuxiliar.add(contato);
					}
				}
				return listaAuxiliar;
			} else {
				for (Contato contato : listaDeContatos) {
					if (contato.getEmail().contains(paramBusca)) {
						listaAuxiliar.add(contato);
					}
				}
				return listaAuxiliar;
			}
		}
	}

	/**
	 * exclusão lógica de um contato da base de dados. Atualiza o campo <code>dataExclusao</code>
	 * com a data atual.
	 * 
	 * @param id
	 * @throws SQLException
	 */
	public void excluirContato(int codigo) throws SQLException {
		Calendar dataHoje = Calendar.getInstance();
		Date dataExclusao = new Date(dataHoje.getTimeInMillis());
		
		String sql = "update contato set dataExclusao=? where codigo=?";
		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setDate(1, dataExclusao);
		statement.setInt(2, codigo);
		statement.execute();
		statement.close();
	}

	/**
	 * Altera o valor booleano de uma variavel email de um obj contato.
	 * 
	 * @param codigo
	 *            codigo do email a ser alterado.
	 * @param status
	 *            true para marcar email como lido ou false para marcar como nao
	 *            lido.
	 * @throws SQLException 
	 */
	public void marcaStatusDeEmailLido(int codigo, boolean status) throws SQLException {
		String sql;
		if (status) {
			sql = "update contato set emailLido='sim' where codigo='" + codigo + "'";
		} else {
			sql = "update contato set emailLido='nao' where codigo='" + codigo + "'";
		}

		PreparedStatement statement = this.conexao.prepareStatement(sql);
		statement.execute();
		statement.close();
	}

	public int getCodigoDoContato(String nome, String email, String data) throws SQLException {
		String sql = "select codigo from contato where nome='" + nome
				+ "' and email='" + email + "' and data='" + data + "'";

		PreparedStatement statement = this.conexao.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		Contato contato = new Contato();

		if (resultSet.next()) {
			contato.setCodigo(resultSet.getString("codigo"));
		}

		resultSet.close();
		statement.close();
		return contato.getCodigo();
	}

	public void salvarEmail(int codigo) throws SQLException {
		String sql = "update contato set emailSalvo='sim' " + "where codigo='" + codigo + "'";
		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.execute();
		statement.close();
	}
	
	/**
	 * Anexar uma resposta a mensagem original
	 * @param novaMensagem
	 * @param codigo
	 * @throws SQLException 
	 */
	public void updateNovaMensagem(String novaMensagem, int codigo) throws SQLException {
		String sql = "update contato set mensagem='" + novaMensagem + "' where codigo='" + codigo + "'";
		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.execute();
		statement.close();		
	}

	/**
	 * 
	 * 
	 * @param codigo
	 * @return o valor booleano da variável emailSalvo de um obj contato
	 * @throws SQLException 
	 */
	public boolean getValorEmailSalvo(int codigo) throws SQLException {
		String sql = "select emailSalvo from contato where codigo='" + codigo
				+ "'";
		String emailSalvo = "nao";

		Statement statement = conexao.createStatement();
		ResultSet result = statement.executeQuery(sql);
		if (result.next()) {
			emailSalvo = result.getString("emailSalvo");
		}

		statement.close();
		return "nao".equals(emailSalvo) ? false : true;
	}
}