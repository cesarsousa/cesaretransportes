package br.com.cesaretransportes.validacao;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class ValidacaoOrcamento {
	
	public static boolean orcamentoEhValido(String nome, String email, String ddd, String telefone, String origem, String enderecoOrigem,
			String destino, String enderecoDestino, String peso, String dimensao) {		
		if(nome.isEmpty() || "NOME ou EMPRESA".equals(nome)) return false;
		if(email.isEmpty() || "EMAIL".equals(email) || !ehEmailValido(email)) return false;
		if(ddd.isEmpty() || "DDD".equals(ddd) || !ehDddValido(ddd)) return false;
		if(telefone.isEmpty() || "TELEFONE".equals(telefone) || !ehNumeroTelefoneValido(telefone)) return false;
		if(origem.isEmpty() || "CIDADE DE ORIGEM".equals(origem)) return false;
		if(enderecoOrigem.isEmpty() || "LOGRADOURO (nome da rua, numero e bairro)".equals(enderecoOrigem)) return false;
		if(destino.isEmpty() || "CIDADE DE DESTINO".equals(destino)) return false;
		if(enderecoDestino.isEmpty() || "LOGRADOURO (nome da rua, numero e bairro)".equals(enderecoDestino)) return false;
		if(peso.isEmpty() || "PESO (aproximado)".equals(peso)) return false;
		if(dimensao.isEmpty() || "DIMENSAO (aproximada)".equals(dimensao)) return false;
		
		return true;
	}
	
	public static void verificarCamposPreenchidos(
			String nome, 
			String email, 
			String ddd, 
			String telefone, 
			String cidadeOrigem, 
			String enderecoOrigem, 
			String cidadeDestino, 
			String enderecoDestino, 
			String peso, 
			String dimensao, 
			String mensagem,
			HttpServletRequest request ) throws ServletException, IOException {
		
		if(nome.isEmpty() || "NOME ou EMPRESA".equals(nome)){
			request.setAttribute("msgErro", true);
			request.setAttribute("msgNome", "O campo 'NOME ou EMPRESA' é obrigatório!");
		}else{
			request.setAttribute("nome", nome);
		}
		
		if(email.isEmpty() || "EMAIL".equals(email)){
			request.setAttribute("msgErro", true);
			request.setAttribute("msgEmail", "O campo 'EMAIL' é obrigatório!");
		}else if(!ehEmailValido(email)){
			request.setAttribute("msgErro", true);
			request.setAttribute("email", email);
			request.setAttribute("msgEmail", "O campo 'EMAIL' está com um formato inválido!");
		}
		else{
			request.setAttribute("email", email);
		}
		
		if(ddd.isEmpty() || "DDD".equals(ddd)){
			request.setAttribute("msgErro", true);
			request.setAttribute("msgDdd", "O campo 'DDD' é obrigatório!");
		}else if(!ehDddValido(ddd)){
			request.setAttribute("msgErro", true);
			request.setAttribute("ddd", ddd);
			request.setAttribute("msgDdd", "O campo 'DDD' é inválido, deve possuir 2 dígitos!");
		}else{
			request.setAttribute("ddd", ddd);
		}
		
		if(telefone.isEmpty() || "TELEFONE".equals(telefone)){
			request.setAttribute("msgErro", true);
			request.setAttribute("msgTelefone", "O campo 'TELEFONE' é obrigatório!");
		}else if(!ehNumeroTelefoneValido(telefone)){
			request.setAttribute("msgErro", true);
			request.setAttribute("telefone", telefone);
			request.setAttribute("msgTelefone", "O campo 'TELEFONE' é inválido, deve possuir 8 ou 9 dígitos!");
		}else{
			request.setAttribute("telefone", telefone);
		}
				
		if (cidadeOrigem.isEmpty() || "CIDADE DE ORIGEM".equals(cidadeOrigem)){
			request.setAttribute("msgErro", true);
			request.setAttribute("msgOrigem", "O campo 'CIDADE DE ORIGEM' é obrigatório!");
		}else {
			request.setAttribute("origem", cidadeOrigem);
		}
		
		if(enderecoOrigem.isEmpty() || "LOGRADOURO (nome da rua, numero e bairro)".equals(enderecoOrigem)){
			request.setAttribute("msgErro", true);
			request.setAttribute("msgEnderecoOrigem", "O campo 'LOGRADOURO (nome da rua, numero e bairro)' é obrigatório!");
		}else{
			request.setAttribute("enderecoOrigem", enderecoOrigem);
		}		
		
		if (cidadeDestino.isEmpty() || "CIDADE DE DESTINO".equals(cidadeDestino)){
			request.setAttribute("msgErro", true);
			request.setAttribute("msgDestino", "O campo 'CIDADE DE DESTINO' é obrigatório!");
		}else {
			request.setAttribute("destino", cidadeDestino);
		}
		
		if(enderecoDestino.isEmpty() || "LOGRADOURO (nome da rua, numero e bairro)".equals(enderecoDestino)){
			request.setAttribute("msgErro", true);
			request.setAttribute("msgEnderecoDestino", "O campo 'LOGRADOURO (nome da rua, numero e bairro)' é obrigatório!");
		}else{
			request.setAttribute("enderecoDestino", enderecoDestino);
		}
		
		if (peso.isEmpty() || "PESO (aproximado)".equals(peso)){
			request.setAttribute("msgErro", true);
			request.setAttribute("msgPeso", "O campo 'PESO (aproximado)' é obrigatório!");
		}else {
			request.setAttribute("peso", peso);
		}
		
		if (dimensao.isEmpty() || "DIMENSAO (aproximada)".equals(dimensao)){
			request.setAttribute("msgErro", true);
			request.setAttribute("msgDimensao", "O campo 'DIMENSAO (aproximada)' é obrigatório!");
		}else {
			request.setAttribute("dimensao", dimensao);
		}
		
		request.setAttribute("mensagem", mensagem);				
	}	
	
	public static boolean ehDddValido(String ddd){
		return ddd.matches("\\d{2}");
	}
	
	public static boolean ehNumeroTelefoneValido(String telefone){
		return telefone.matches("(\\d{8})|(\\d{9})");
	}
	
	public static boolean ehEmailValido(String email) {
		return email.matches("[a-zA-Z0-9._%-]+@[a-zA-Z0-9._-]+\\.[a-z]{2,4}");
	}

}
