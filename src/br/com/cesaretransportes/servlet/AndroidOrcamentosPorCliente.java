package br.com.cesaretransportes.servlet;

import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
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
import br.com.cesaretransportes.dao.TelefoneDao;
import br.com.cesaretransportes.modelo.Orcamento;
import br.com.cesaretransportes.modelo.Servico;

/*
 * Esta classe recebe requisição da activity OpcaoOrcamento.
 */
public class AndroidOrcamentosPorCliente extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		this.doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		int idCliente = Integer.parseInt(request.getParameter("id"));		
		
		Connection conexao = null;
		try {
			conexao = AbstractConnectionFactory.getConexao();
			OrcamentoDao orcamentoDao = new OrcamentoDao(conexao);
			TelefoneDao telefoneDao = new TelefoneDao(conexao);
			EnderecoDao enderecoDao = new EnderecoDao(conexao);
			ServicoDao servicoDao = new ServicoDao(conexao);
			
			List<Orcamento> orcamentos = new ArrayList<Orcamento>();
			orcamentos = orcamentoDao.getListaDeOrcamentos(idCliente, "idOrcamento", 1);
			// atualizar orçamentos
			for(Orcamento orcamento : orcamentos){					
				orcamento.getCliente().setTelefone(telefoneDao.get(orcamento.getCliente().getIdCliente()));
				orcamento.setEnderecos(enderecoDao.getEnderecosPorOrcamentos(orcamento.getIdOrcamento()));					
			}
			
			List<Servico> servicos = servicoDao.getAll();
			
			List<Orcamento> orcamentosQueNaoSaoServicos = getOrcamentos(orcamentos, servicos);			
			
			ServletOutputStream out = response.getOutputStream();
			DataOutputStream dataOut = new DataOutputStream(out);
			
			dataOut.writeInt(orcamentosQueNaoSaoServicos.size());
			
			for (Orcamento orcamento : orcamentosQueNaoSaoServicos){
				orcamento.serialize(dataOut);
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

	/*
	 * remove os orçamentos relacionados a serviços
	 */
	private List<Orcamento> getOrcamentos(List<Orcamento> listaDeOrcamentos, List<Servico> listaDeServicos) {
		
		for(Servico servico : listaDeServicos){
			removerDaListaOrcamento(servico.getOrcamento().getIdOrcamento(), listaDeOrcamentos);			
		}			
		
		return listaDeOrcamentos;
	}

	private void removerDaListaOrcamento(int idOrcamentoDoServico, List<Orcamento> listaDeOrcamentos) {
		Integer indice = null;
		
		/*
		 * se o lista todos os orçamentos, ao encontrar o id referente a um serviço
		 * guarda sua posição da lista em indice. A seguir interrompe o loop e remove
		 * o indice da lista de orçamentos.
		 */
		for(int i =0;i<listaDeOrcamentos.size();i++){
			if(listaDeOrcamentos.get(i).getIdOrcamento() == idOrcamentoDoServico){
				indice = i;
				break;
			}
		}
		if(indice != null){
			int i = indice;
			listaDeOrcamentos.remove(i);
		}		
	}
}