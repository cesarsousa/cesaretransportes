package br.com.cesaretransportes.validacao;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ValidacaoOrcamento {
	
	public static void verificarCamposPreenchidos(String cidadeOrigem, String enderecoOrigem, String cidadeDestino, 
			String enderecoDestino, String peso, String dimensao, String mensagem,
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		if ("".equals(cidadeOrigem)){
			request.setAttribute("msgErro", true);
			request.setAttribute("msgOrigem", "O campo 'origem' é obrigatório!");
		}else {
			request.setAttribute("origem", cidadeOrigem);
		}
		
		if("".equals(enderecoOrigem)){
			request.setAttribute("msgErro", true);
			request.setAttribute("msgEnderecoOrigem", "O campo 'endere�o de origem' é obrigatório!");
		}else{
			request.setAttribute("enderecoOrigem", enderecoOrigem);
		}		
		
		if ("".equals(cidadeDestino)){
			request.setAttribute("msgErro", true);
			request.setAttribute("msgDestino", "O campo 'destino' é obrigatório!");
		}else {
			request.setAttribute("destino", cidadeDestino);
		}
		
		if("".equals(enderecoDestino)){
			request.setAttribute("msgErro", true);
			request.setAttribute("msgEnderecoDestino", "O campo 'endere�o do destino' é obrigatório!");
		}else{
			request.setAttribute("enderecoDestino", enderecoDestino);
		}
		
		if ("".equals(peso)){
			request.setAttribute("msgErro", true);
			request.setAttribute("msgPeso", "O campo 'peso' é obrigatório!");
		}else {
			request.setAttribute("peso", peso);
		}
		
		if ("".equals(dimensao)){
			request.setAttribute("msgErro", true);
			request.setAttribute("msgDimensao", "O campo 'dimensão' é obrigatório!");
		}else {
			request.setAttribute("dimensao", dimensao);
		}
		
		request.setAttribute("mensagem", mensagem);		
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/cadastrar-orcamento.jsp");
		dispatcher.forward(request, response);		
	}
	
	public static boolean orcamentoEhValido(String nome, String email, String ddd, String telefone, String origem, String enderecoOrigem,
			String destino, String enderecoDestino, String peso, String dimensao) {		
		if(nome.isEmpty() || "NOME ou EMPRESA".equals(nome)) return false;
		if(email.isEmpty() || "EMAIL".equals(email)) return false;
		if(ddd.isEmpty() || "DDD".equals(ddd)) return false;
		if(telefone.isEmpty() || "TELEFONE".equals(telefone)) return false;
		if(origem.isEmpty() || "CIDADE DE ORIGEM".equals(origem)) return false;
		if(enderecoOrigem.isEmpty() || "LOGRADOURO (nome da rua, numero e bairro)".equals(enderecoOrigem)) return false;
		if(destino.isEmpty() || "CIDADE DE DESTINO".equals(destino)) return false;
		if(enderecoDestino.isEmpty() || "LOGRADOURO (nome da rua, numero e bairro)".equals(enderecoDestino)) return false;
		if(peso.isEmpty() || "PESO (aproximado)".equals(peso)) return false;
		if(dimensao.isEmpty() || "DIMENSAO (aproximada)".equals(dimensao)) return false;
		
		return true;
	}

}
