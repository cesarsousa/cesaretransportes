package br.com.cesaretransportes.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.cesaretransportes.dao.AbstractConnectionFactory;
import br.com.cesaretransportes.dao.ClienteDao;
import br.com.cesaretransportes.dao.EnderecoDao;
import br.com.cesaretransportes.dao.TelefoneDao;

import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

/*
 * Esta classe recebe requisição das activities.
 * - ClienteMudarSenha. (opcao = senha)
 * - ClienteAlterarDados. (opcao = dados)
 */
public class AndroidClienteAlterarDados extends HttpServlet {
	private static final long serialVersionUID = 1L;
          
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
	
		String opcao = request.getParameter("opcao");
		String resultado = "true";
				
		Connection conexao = null;
		try {
			conexao = AbstractConnectionFactory.getConexao();
			TelefoneDao telefoneDao = new TelefoneDao(conexao);
			EnderecoDao enderecoDao = new EnderecoDao(conexao);
			ClienteDao clienteDao = new ClienteDao(conexao);
			
			if ("senha".equals(opcao)){
				int id = Integer.parseInt(request.getParameter("id"));
				String senha = request.getParameter("senha");
				try{
					clienteDao.alterarSenha(id, senha);
				} catch (MySQLSyntaxErrorException e) {
					resultado = "ERRO codigo MSQLSEE01 " + e.getMessage();
				}
			}
			
			if("dados".equals(opcao)){			
				int idCliente = Integer.parseInt(request.getParameter("id"));
				String ddd = request.getParameter("ddd");
				String telefone = request.getParameter("telefone");
				String complemento = request.getParameter("complemento");
				String tipoDocumento = request.getParameter("tipoDocumento");
				String numeroDocumento = request.getParameter("numeroDocumento");				
				String localizacao = request.getParameter("localizacao");
				String cidade = request.getParameter("cidade");
				String estado = request.getParameter("estado");
				
				clienteDao.alterar(idCliente, tipoDocumento, numeroDocumento);
				telefoneDao.alterar(idCliente, ddd, telefone, complemento);
				enderecoDao.alterar(idCliente, localizacao, cidade, estado);
			}
			
			write(response, resultado);
			
		} catch (ClassNotFoundException e) {			
			e.printStackTrace();
			write(response, "ERRO codigo CNFE01 " + e.getMessage());
		} catch (SQLException e) {			
			e.printStackTrace();
			write(response, "ERRO codigo SQLE01 " + e.getMessage());
		} finally{
			try {
				conexao.close();
			} catch (SQLException e) {				
				e.printStackTrace();
				write(response, "ERRO codigo SQLE201 " + e.getMessage());
			}
		}
	}
	
	private void write(HttpServletResponse response, String resultado) throws IOException {		
		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();		
		writer.print(resultado);		
		writer.flush();
		writer.close();		
	}
}