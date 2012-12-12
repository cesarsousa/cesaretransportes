package br.com.cesaretransportes.servlet;

import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.cesaretransportes.dao.AbstractConnectionFactory;
import br.com.cesaretransportes.dao.EmpresaDao;
import br.com.cesaretransportes.dao.EnderecoDao;
import br.com.cesaretransportes.dao.TelefoneDao;
import br.com.cesaretransportes.modelo.Empresa;

/*
 * Esta classe recebe requisição da activity ClienteContato.
 */
public class AndroidEmpresaVisualizar extends HttpServlet {
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
			EmpresaDao empresaDao = new EmpresaDao(conexao);
			EnderecoDao enderecoDao = new EnderecoDao(conexao);
			TelefoneDao telefoneDao = new TelefoneDao(conexao);
			
			
			Empresa empresa = empresaDao.get();
			empresa.setEndereco(enderecoDao.getEnderecoEmpresa(empresa.getIdEmpresa()));
			empresa.setTelefones(telefoneDao.getTelefonesEmpresa(empresa.getIdEmpresa()));		
			
			ServletOutputStream out = response.getOutputStream();
			DataOutputStream dataOut = new DataOutputStream(out);
			
			empresa.serialize(dataOut);
			
			dataOut.flush();
			dataOut.close();
			out.close();
			
		} catch (ClassNotFoundException e) {
			// TODO TODO tratar exceção android
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO TODO tratar exceção android
			e.printStackTrace();
		} finally{
			try {
				conexao.close();
			} catch (SQLException e) {
				// TODO TODO tratar exceção android
				e.printStackTrace();
			}
		}
	}
}
