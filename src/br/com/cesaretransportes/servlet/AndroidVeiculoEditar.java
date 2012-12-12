package br.com.cesaretransportes.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.cesaretransportes.dao.AbstractConnectionFactory;
import br.com.cesaretransportes.dao.ServicoDao;
import br.com.cesaretransportes.dao.VeiculoDao;
import br.com.cesaretransportes.modelo.Servico;
import br.com.cesaretransportes.modelo.Veiculo;

/*
 * Esta classe recebe requisição da activity DetalheVeiculo.
 */
public class AndroidVeiculoEditar extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Connection conexao = null;
		try {
			conexao = AbstractConnectionFactory.getConexao();
			VeiculoDao veiculoDao = new VeiculoDao(conexao);
			ServicoDao servicoDao = new ServicoDao(conexao);

			if (Boolean.valueOf(request.getParameter("finalizarRota"))) {
				int idVeiculo = Integer.parseInt(request.getParameter("id"));
				Veiculo veiculo = veiculoDao.getVeiculo(idVeiculo);
				List<Servico> servicos = servicoDao.getServicosPorVeiculo(idVeiculo, true);
				servicoDao.confirmarEntregas(servicos);				
				veiculoDao.editar(veiculo);				
				write(response, "true");
				// retorna para a aplicação mobile.
				return;
			}else{
				Veiculo veiculo = new Veiculo();
				veiculo.setIdVeiculo(Integer.parseInt(request.getParameter("id")));
				veiculo.setLocalizacao(request.getParameter("localizacao"));
				
				veiculoDao.editar(veiculo);
	
				write(response, "Veiculo editado com sucesso !");
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			write(response, "ERRO codigo CNFE15 " + e.getMessage()); 
		} catch (SQLException e) {
			e.printStackTrace();
			write(response, "ERRO codigo SQLE15 " + e.getMessage());
		} finally {
			try {
				conexao.close();
			} catch (SQLException e) {
				e.printStackTrace();
				write(response, "ERRO codigo SQLE215 " + e.getMessage());
			}
		}
	}

	private void write(HttpServletResponse response, String resultado)
			throws IOException {
		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
		writer.print(resultado);
		writer.flush();
		writer.close();
	}
}
