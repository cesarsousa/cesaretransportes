package br.com.cesaretransportes.validacao;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import br.com.cesaretransportes.modelo.Empresa;
import br.com.cesaretransportes.modelo.Endereco;
import br.com.cesaretransportes.modelo.Telefone;

public class ValidacaoConta {
	
	public static Empresa criarEmpresa(HttpServletRequest request){
		int idEmpresa = Integer.parseInt(request.getParameter("idEmpresa"));
		int idEndereco = Integer.parseInt(request.getParameter("idEndereco"));
		int idTelefone1 = Integer.parseInt(request.getParameter("idTelefone1"));
		int idTelefone2 = Integer.parseInt(request.getParameter("idTelefone2"));
		int idTelefone3 = Integer.parseInt(request.getParameter("idTelefone3"));		
		
		String nome = request.getParameter("nome");
		String cnpj = request.getParameter("cnpj");		
		boolean mostrarMapa = request.getParameter("mostrarMapa") == null ? false : true;
		String localizacao = mostrarMapa == true ? request.getParameter("localizacao") : "";
		String email = request.getParameter("email");
		String senha = request.getParameter("senha");
		String msn = request.getParameter("msn");
				
		Empresa empresa = new Empresa(idEmpresa, nome, null, cnpj, msn, email, senha, mostrarMapa, localizacao, null);
		
		String enderecoLocalizacao = request.getParameter("enderecoLocalizacao");
		String enderecoEstado = request.getParameter("enderecoEstado");
		String enderecoCidade = request.getParameter("enderecoCidade");
		
		Endereco endereco = new Endereco(idEndereco, empresa, null, null, enderecoCidade, enderecoEstado, enderecoLocalizacao, null);
		empresa.setEndereco(endereco);		
		
		String ddd1 = request.getParameter("ddd1");
		String numero1 = request.getParameter("numero1");
		String complemento1 = request.getParameter("complemento1");
		
		Telefone tel1 = new Telefone(idTelefone1, idEmpresa, 0, ddd1, numero1, complemento1);
		
		String ddd2 = request.getParameter("ddd2");
		String numero2 = request.getParameter("numero2");
		String complemento2 = request.getParameter("complemento2");
		
		Telefone tel2 = new Telefone(idTelefone2, idEmpresa, 0, ddd2, numero2, complemento2);
		
		String ddd3 = request.getParameter("ddd3");
		String numero3 = request.getParameter("numero3");
		String complemento3 = request.getParameter("complemento3");
		
		Telefone tel3 = new Telefone(idTelefone3, idEmpresa, 0, ddd3, numero3, complemento3);
		
		List<Telefone> telefones = Arrays.asList(tel1, tel2, tel3);
		empresa.setTelefones(telefones);
		
		return empresa;
	}

	public static boolean validada(Empresa empresa, HttpServletRequest request) {
		boolean resultado = true;
		
		if(empresa.getNome().isEmpty()){
			request.setAttribute("erroNomeConta", "O campo '<b>Nome da Empresa</b>' deve ser preenchido.");
			resultado = false;
		}
		
		if(empresa.getCnpj().isEmpty()){
			request.setAttribute("erroCnpjConta", "O campo '<b>CNPJ</b>' deve ser preenchido.");
			resultado = false;
		}else if (!ehCnpj(empresa.getCnpj())){
			request.setAttribute("erroCnpjConta", "O campo '<b>CNPJ</b>' deve ser possuir exatos 14 dígitos.");
			resultado = false;
		}
		
		if(empresa.getEndereco().getLocalizacao().isEmpty()){
			request.setAttribute("erroEnderecoConta", "O campo '<b>Logradouro (rua, número e bairro)</b>' deve ser preenchido.");
			resultado = false;
		}
		
		if(empresa.getEndereco().getCidade().isEmpty()){
			request.setAttribute("erroCidadeConta", "O campo '<b>Cidade</b>' deve ser preenchida.");
			resultado = false;
		}		
				
		if(empresa.isMostrarMapa() && empresa.getLocalizacao().isEmpty()){
			request.setAttribute("erroLocalizacaoConta", "Ao '<b>Habilitar visualização com Google Maps</b>', o campo 'Geo Localização' deve ser preenchido.");
			resultado = false;
		}		
		
		if(empresa.getEmail().isEmpty()){
			request.setAttribute("erroEmailConta", "O campo '<b>Gmail</b>' deve ser preenchido.");
			resultado = false;
		}else if(!emailValido(empresa.getEmail())){
			request.setAttribute("erroEmailConta", "O campo '<b>Gmail</b>' deve possuir um formato valido.");
			resultado = false;
		}else if(!ehGmail(empresa.getEmail())){
			request.setAttribute("erroEmailConta", "O campo '<b>Gmail</b>' deve ser preenchido com um <b>email do Google</b>, certifique também de usar letra minuscula durante a digitação.");
			resultado = false;
		}
		
		if(empresa.getSenha().isEmpty()){
			request.setAttribute("erroSenhaConta", "O campo '<b>Senha do Gmail</b>' deve ser preenchido.");
			resultado = false;
		}		
		
		Telefone telefone1 = empresa.getTel1();
		if(!ehValido(telefone1)){
			request.setAttribute("erroTelefone1", true);
			request.setAttribute("erroTelefoneConta", true);			
			resultado = false;
		}
		
		Telefone telefone2 = empresa.getTel2();
		if(!ehValido(telefone2)){
			request.setAttribute("erroTelefone2", true);
			request.setAttribute("erroTelefoneConta", true);
			resultado = false;
		}
		
		Telefone telefone3 = empresa.getTel3();
		if(!ehValido(telefone3)){
			request.setAttribute("erroTelefone3", true);
			request.setAttribute("erroTelefoneConta", true);
			resultado = false;
		}
		
		if(!resultado){
			request.setAttribute("erroConfiguracaoConta", true);
		}
		
		return resultado;
	}

	private static boolean ehValido(Telefone telefone) {
		if(telefone.getDdd().isEmpty() && telefone.getNumero().isEmpty()){
			telefone.setComplemento("");
			return true;
		}		
		
		if(!telefone.getDdd().isEmpty() && ehDdd(telefone.getDdd()) && !telefone.getNumero().isEmpty() && ehNumeroTelefone(telefone.getNumero())){
			return true;
		}
		
		return false;
	}

	private static boolean ehCnpj(String cnpj) {
		return cnpj.matches("\\d{14}");
	}
	
	private static boolean ehNumeroTelefone(String telefone) {
		return telefone.matches("(\\d{8})|(\\d{9})");
	}
	
	private static boolean ehDdd(String ddd) {
		return ddd.matches("(\\d{2})");
	}
	
	private static boolean ehGmail(String email) {
		return email.endsWith("@gmail.com");
	}

	private static boolean emailValido(String email) {
		return email.matches("[a-zA-Z0-9._%-]+@[a-zA-Z0-9._-]+\\.[a-z]{2,4}");
	}
}
