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
import br.com.cesaretransportes.dao.ClienteDao;
import br.com.cesaretransportes.dao.OrcamentoDao;
import br.com.cesaretransportes.dao.ServicoDao;
import br.com.cesaretransportes.dao.VeiculoDao;
import br.com.cesaretransportes.modelo.Cliente;
import br.com.cesaretransportes.modelo.Servico;

/*
 * Esta classe recebe requisição da activity DetalheVeiculo.
 */
public class AndroidOrcamentosPorVeiculo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
    	throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		int idVeiculo = Integer.parseInt(request.getParameter("id"));
				
		Connection conexao = null;
		try {
			conexao = AbstractConnectionFactory.getConexao();
			ServicoDao servicoDao = new ServicoDao(conexao);
			ClienteDao clienteDao = new ClienteDao(conexao);
			OrcamentoDao orcamentoDao = new OrcamentoDao(conexao);
			VeiculoDao veiculoDao = new VeiculoDao(conexao);
			
			List<Servico> servicos = servicoDao.getServicosPorVeiculo(idVeiculo, true);
			for(Servico servico : servicos){
				int idOrcamento = servico.getOrcamento().getIdOrcamento();				
				servico.setOrcamento(orcamentoDao.getOrcamento(idOrcamento));
				servico.setVeiculo(veiculoDao.getVeiculo(idVeiculo));
				Cliente cliente = clienteDao.getPorOrcamento(idOrcamento);
				servico.getOrcamento().setCliente(cliente);				
			}
			
			ServletOutputStream out = response.getOutputStream();
			DataOutputStream dataOut = new DataOutputStream(out);
			
			dataOut.writeInt(servicos.size());
			
			for(Servico servico : servicos){
				servico.serialize(dataOut);
			}
			
			dataOut.flush();
			dataOut.close();
			out.close();
						
		} catch (ClassNotFoundException e) {
			// TODO tratar exceção android
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO tratar exceção android
			e.printStackTrace();
		} finally {
			try {
				conexao.close();
			} catch (SQLException e) {
				// TODO tratar exceção android
				e.printStackTrace();
			}
		}		
	}
}