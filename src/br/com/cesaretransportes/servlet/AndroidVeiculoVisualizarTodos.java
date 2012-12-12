package br.com.cesaretransportes.servlet;

import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.cesaretransportes.dao.AbstractConnectionFactory;
import br.com.cesaretransportes.dao.EnderecoDao;
import br.com.cesaretransportes.dao.OrcamentoDao;
import br.com.cesaretransportes.dao.ServicoDao;
import br.com.cesaretransportes.dao.VeiculoDao;
import br.com.cesaretransportes.modelo.Servico;
import br.com.cesaretransportes.modelo.Veiculo;

/*
 * Esta classe recebe requisição da activity VisualizarVeiculos.
 */
public class AndroidVeiculoVisualizarTodos extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		this.doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {

		Connection conexao = null;
		try {
			conexao = AbstractConnectionFactory.getConexao();
			VeiculoDao veiculoDao = new VeiculoDao(conexao);
			ServicoDao servicoDao = new ServicoDao(conexao);
			EnderecoDao enderecoDao = new EnderecoDao(conexao);
			OrcamentoDao orcamentoDao = new OrcamentoDao(conexao);
			
			
			List<Veiculo> veiculos = veiculoDao.getAll();
			for (Veiculo veiculo : veiculos){				
				atualizarVeiculo(servicoDao, orcamentoDao, enderecoDao, veiculo);				
			}
			
			ServletOutputStream out = response.getOutputStream();
			DataOutputStream dataOut = new DataOutputStream(out);
			
			// escrita da quantidade de veiculos
			dataOut.writeInt(veiculos.size());
			
			// serialização dos veículos
			for(Veiculo veiculo : veiculos){
				veiculo.serialize(dataOut);
			}
			
			dataOut.flush();
			dataOut.close();
			out.close();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			// TODO tratar Exceção android
		} catch (SQLException e) {
			e.printStackTrace();
			// tratar Exceção android
		} finally {
			try {
				conexao.close();
			} catch (SQLException e) {
				e.printStackTrace();
				// tratar Exceção android
			}
		}
	}
	
	protected void atualizarVeiculo(ServicoDao servicoDao, OrcamentoDao orcamentoDao, EnderecoDao enderecoDao, Veiculo veiculo) throws SQLException {
		List<Servico> servicos = servicoDao.getServicosPorVeiculo(veiculo.getIdVeiculo(), true);				
		for(Servico servico : servicos){
			servico.setVeiculo(veiculo);
			servico.setOrcamento(orcamentoDao.getOrcamento(servico.getOrcamento().getIdOrcamento()));
			servico.getOrcamento().setEnderecos(enderecoDao.getEnderecosPorOrcamentos(servico.getOrcamento().getIdOrcamento()));
		}
		veiculo.setServicos(servicos);
	}
	
	/*private void write(HttpServletResponse response, String resultado) throws IOException {		
		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();		
		writer.print(resultado);		
		writer.flush();
		writer.close();		
	}*/
}
