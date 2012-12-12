package br.com.cesaretransportes.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.cesaretransportes.modelo.Orcamento;
import br.com.cesaretransportes.modelo.Servico;
import br.com.cesaretransportes.modelo.Veiculo;

public class ServicoDao {
	
	Connection conexao;

	public ServicoDao(Connection conexao) {
		this.conexao = conexao;
	}

	public void confirmarEntregas(List<Servico> servicos) throws SQLException {		
		Calendar dtHoje = Calendar.getInstance();
		Date dataEntrega = new Date(dtHoje.getTimeInMillis());
		
		for(Servico servico : servicos){
			String sql = "update servico set dataEntrega=? where idServico=?";
			PreparedStatement statement = conexao.prepareStatement(sql);
			statement.setDate(1, dataEntrega);
			statement.setInt(2, servico.getIdServico());
			statement.execute();
			statement.close();
		}		
	}
	
	public Servico get(int idServico) throws SQLException {
		String sql = 
			"select " +
			"idServico, idOrcamento, idVeiculo, valor, dataPrevEntrega, dataEntrega, dataExclusao " +
			"from servico where idServico=" + idServico;
		
		PreparedStatement statement = conexao.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		
		Servico servico = null;
		if(resultSet.first()){
			Calendar dataPrevEntrega = null;
			if (resultSet.getDate("dataPrevEntrega") != null){
				dataPrevEntrega = Calendar.getInstance();
				dataPrevEntrega.setTime(resultSet.getDate("dataPrevEntrega"));				
			}			
			Calendar dataEntrega = null;
			if(resultSet.getDate("dataEntrega") != null){
				dataEntrega = Calendar.getInstance();
				dataEntrega.setTime(resultSet.getDate("dataEntrega"));
			}
			
			Calendar dataExclusao = null;
			if(resultSet.getDate("dataExclusao") != null){
				dataExclusao = Calendar.getInstance();
				dataExclusao.setTime(resultSet.getDate("dataExclusao"));
			}
			
			Orcamento orcamento = new Orcamento();
			orcamento.setIdOrcamento(resultSet.getInt("idOrcamento"));			
			Veiculo veiculo = new Veiculo();
			veiculo.setIdVeiculo(resultSet.getInt("idVeiculo"));
									
			servico = new Servico(
					resultSet.getInt("idServico"), 
					orcamento, 
					veiculo,
					new BigDecimal(resultSet.getString("valor")),
					dataPrevEntrega, 
					dataEntrega,
					dataExclusao);
			
		}
		resultSet.close();
		statement.close();
		
		return servico;
	}
	
	/**
	 * Lista todos os serviços que não foram logicamente excluídos do banco.
	 * 
	 * @param somenteAtivos
	 *            se <code>true</code> somente os serviços ativos, ou seja, cuja
	 *            data de entrega é nula, caso contrário, considera os ativos e
	 *            inativos.
	 * @return a listagem dos serviços de acordo com o valor de
	 *         <code>somenteAtivos</code>.
	 * @throws SQLException
	 */
	public List<Servico> getAll(boolean somenteAtivos) throws SQLException {		
		return getServicosPorVeiculo(0, false);		
	}
	
	/**
	 * 
	 * @return todos os serviços
	 * @throws SQLException
	 */
	public List<Servico> getAll() throws SQLException {
		
		String sql = 
			"select " +
			"idServico, idOrcamento, idVeiculo, valor, dataPrevEntrega, dataEntrega, dataExclusao " +
			"from servico order by idServico desc";
		
		PreparedStatement statement = conexao.prepareStatement(sql);		
		ResultSet resultSet = statement.executeQuery();
		
		List<Servico> servicos = new ArrayList<Servico>();
		while(resultSet.next()){			
			Calendar dataPrevEntrega = null;
			if (resultSet.getDate("dataPrevEntrega") != null){
				dataPrevEntrega = Calendar.getInstance();
				dataPrevEntrega.setTime(resultSet.getDate("dataPrevEntrega"));				
			}			
			Calendar dataEntrega = null;
			if(resultSet.getDate("dataEntrega") != null){
				dataEntrega = Calendar.getInstance();
				dataEntrega.setTime(resultSet.getDate("dataEntrega"));
			}
			
			Calendar dataExclusao = null;
			if(resultSet.getDate("dataExclusao") != null){
				dataExclusao = Calendar.getInstance();
				dataExclusao.setTime(resultSet.getDate("dataExclusao"));
			}
			
			Orcamento orcamento = new Orcamento();
			orcamento.setIdOrcamento(resultSet.getInt("idOrcamento"));			
			Veiculo veiculo = new Veiculo();
			veiculo.setIdVeiculo(resultSet.getInt("idVeiculo"));
									
			Servico servico = new Servico(
					resultSet.getInt("idServico"), 
					orcamento, 
					veiculo,
					new BigDecimal(resultSet.getString("valor")),
					dataPrevEntrega, 
					dataEntrega,
					dataExclusao);
			
			servicos.add(servico);
		}		
		return servicos;
	}
	
	/**
	 * lista todos os serviços de um veículo.
	 * @param idVeiculo
	 *            id do veiculo
	 * @param somenteAtivos
	 *            se <code>true</code> somente serviço com data de exlusão nula,
	 *            caso contrário não considera data de exlcusão (ativos e
	 *            inativos).
	 * @return
	 * @throws SQLException
	 */
	public List<Servico> getServicosPorVeiculo(int idVeiculo, boolean somenteAtivos) throws SQLException {
		String condicaoVeiculo = idVeiculo == 0 ? "" : " and idVeiculo=" + idVeiculo;
		String condicaoAtivo = somenteAtivos ? " and dataEntrega is null" : "";
		String sql = 
			"select " +
			"idServico, idOrcamento, idVeiculo, valor, dataPrevEntrega, dataEntrega, dataExclusao " +
			"from servico where dataExclusao is null " + condicaoVeiculo + condicaoAtivo + " order by idServico";
		
		PreparedStatement statement = conexao.prepareStatement(sql);		
		ResultSet resultSet = statement.executeQuery();
		
		List<Servico> servicos = new ArrayList<Servico>();
		while(resultSet.next()){			
			Calendar dataPrevEntrega = null;
			if (resultSet.getDate("dataPrevEntrega") != null){
				dataPrevEntrega = Calendar.getInstance();
				dataPrevEntrega.setTime(resultSet.getDate("dataPrevEntrega"));				
			}			
			Calendar dataEntrega = null;
			if(resultSet.getDate("dataEntrega") != null){
				dataEntrega = Calendar.getInstance();
				dataEntrega.setTime(resultSet.getDate("dataEntrega"));
			}
			
			Calendar dataExclusao = null;
			if(resultSet.getDate("dataExclusao") != null){
				dataExclusao = Calendar.getInstance();
				dataExclusao.setTime(resultSet.getDate("dataExclusao"));
			}
			
			Orcamento orcamento = new Orcamento();
			orcamento.setIdOrcamento(resultSet.getInt("idOrcamento"));			
			Veiculo veiculo = new Veiculo();
			veiculo.setIdVeiculo(resultSet.getInt("idVeiculo"));
									
			Servico servico = new Servico(
					resultSet.getInt("idServico"), 
					orcamento, 
					veiculo,
					new BigDecimal(resultSet.getString("valor")),
					dataPrevEntrega, 
					dataEntrega,
					dataExclusao);
			
			servicos.add(servico);
		}		
		return servicos;
	}
	
	

	public int cadastrar(Servico servico) throws SQLException {
		String sql = "insert into servico (idOrcamento, idVeiculo, valor, dataPrevEntrega, dataEntrega) " +
				"values (?,?,?,?,?)";
		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setInt(1, servico.getOrcamento().getIdOrcamento());
		statement.setInt(2, servico.getVeiculo().getIdVeiculo());
		statement.setString(3, servico.getValor().toString());
		Date dtPrevEntrega = new Date(servico.getDataPrevEntrega().getTimeInMillis());
		statement.setDate(4, dtPrevEntrega);
		statement.setDate(5, null);
		statement.execute();
		
		sql = "select max(idServico) as ultimoId from servico;";
		statement = conexao.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		int idOrcamento = 0;
		if(resultSet.first()){
			idOrcamento = resultSet.getInt("ultimoId");	
		}					
		statement.close();
		return idOrcamento;		
	}

	public void excluir(int idServico) throws SQLException {
		Calendar dataHoje = Calendar.getInstance();
		Date dataExclusao = new Date(dataHoje.getTimeInMillis());
		
		String sql = "update servico set dataExclusao=? where idServico=?";
		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setDate(1, dataExclusao);
		statement.setInt(2, idServico);
		statement.execute();
		statement.close();		
	}

	/**
	 * 
	 * @param idCliente
	 * @return
	 * @throws SQLException 
	 */
	public List<Servico> getServicosPorCliente(int idCliente) throws SQLException {
		String sql = 
			"select " +
			"s.idServico, s.idOrcamento, s.idVeiculo, s.valor, s.dataPrevEntrega, s.dataEntrega " +
			"from servico s, orcamento o " +
			"where s.idOrcamento=o.idOrcamento " +
			"and o.idcliente=? and s.dataExclusao is null and o.dataExclusao is null " +
			"order by s.idServico desc";
		
		PreparedStatement statement = conexao.prepareStatement(sql);
		statement.setInt(1, idCliente);
		ResultSet resultSet = statement.executeQuery();
		
		List<Servico> servicos = new ArrayList<Servico>();
		while(resultSet.next()){			
			Calendar dataPrevEntrega = null;
			if (resultSet.getDate("s.dataPrevEntrega") != null){
				dataPrevEntrega = Calendar.getInstance();
				dataPrevEntrega.setTime(resultSet.getDate("s.dataPrevEntrega"));				
			}			
			Calendar dataEntrega = null;
			if(resultSet.getDate("s.dataEntrega") != null){
				dataEntrega = Calendar.getInstance();
				dataEntrega.setTime(resultSet.getDate("s.dataEntrega"));
			}		
			
			Orcamento orcamento = new Orcamento();
			orcamento.setIdOrcamento(resultSet.getInt("s.idOrcamento"));			
			Veiculo veiculo = new Veiculo();
			veiculo.setIdVeiculo(resultSet.getInt("s.idVeiculo"));
									
			Servico servico = new Servico(
					resultSet.getInt("s.idServico"), 
					orcamento, 
					veiculo,
					new BigDecimal(resultSet.getString("s.valor")),
					dataPrevEntrega, 
					dataEntrega,
					null);
			
			servicos.add(servico);
		}		
		return servicos;
	}	
}
