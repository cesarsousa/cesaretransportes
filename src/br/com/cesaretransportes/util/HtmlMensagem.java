package br.com.cesaretransportes.util;

import br.com.cesaretransportes.modelo.Cliente;
import br.com.cesaretransportes.modelo.Orcamento;

/**
 * 
 * Classe geradora de mensagem HTML utilizada nos email enviados pela aplicação.
 *  
 * @author cesar sousa
 *
 */
public class HtmlMensagem {
	
	private static final String PATH = "./html_mensagem/";	
	
	public static String getMensagemRecuperarSenha(Cliente cliente){		
		String mensagem = new CesareUtil().lerArquivo(PATH + "recuperar_senha.html");
		return mensagem
			.replace("[NOMEDOCLIENTE]", cliente.getNome())
			.replace("[USUARIO]", cliente.getEmail())
			.replace("[SENHA]", cliente.getSenha());
	}
	
	public static String getMensagemEnviarEmail(int id, String nome) {
		String mensagem = "Novo cliente cadastrado.<br /><br />" +
			"<b>C&oacute;digo do cliente:</b> " + id + "<br/>" + 	
			"Nome: <b>" + nome + "</b><br/>";
		
		return getMensagemEnviarEmail(mensagem);
	}
	
	public static String getMensagemEnviarEmail(String mensagem) {
		String novaMensagem = new CesareUtil().lerArquivo(PATH + "enviar_email.html");
		return novaMensagem.replace("[MENSAGEM]", mensagem);
	}

	public static String getMensagemConfirmacaoCadastro(Cliente cliente) {
		String mensagem = new CesareUtil().lerArquivo(PATH + "cliente_confirmado.html");
		return mensagem
			.replace("[NOMEDOCLIENTE]", cliente.getNome())
			.replace("[USUARIO]", cliente.getEmail())
			.replace("[SENHA]", cliente.getSenha());
	}

	public static String getMensagemNotificacaoCliente(String nome, String tipo) {
		String mensagem  = new CesareUtil().lerArquivo(PATH + "notificacao_cliente.html");
		return mensagem
			.replace("[NOMEDOCLIENTE]", nome)
			.replace("[TIPO]", tipo);
	}

	public static String getMensagemNotificacaoEmpresa(String nome, String data, int codigo, String email, String mensagem, String tipo) {
		if(tipo.equals("or&ccedil;amento")){
			String msg = new CesareUtil().lerArquivo(PATH + "notificacao_empresa.html");
			return msg
				.replace("[TIPO]", tipo)
				.replace("[NOMEDOCLIENTE]", nome)
				.replace("[DATAENVIO]", data)
				.replace("[CODIGOCONTATO]", String.valueOf(codigo))			
				.replace("[TIPO2]", tipo);
		}else{
			String msg = new CesareUtil().lerArquivo(PATH + "notificacao_contato_empresa.html");
			return msg
			.replace("[TIPO]", tipo)
			.replace("[NOMEDOCLIENTE]", nome)
			.replace("[DATAENVIO]", data)
			.replace("[CODIGOCONTATO]", String.valueOf(codigo))		
			.replace("[EMAIL]", email)
			.replace("[MENSAGEM]", mensagem)
			.replace("[TIPO2]", tipo);
		}
	}

	public static String getMensagemRespostaOrcamentoCliente(Orcamento orcamento, String resposta, String mesagemOriginal) {
		String mensagem  = new CesareUtil().lerArquivo(PATH + "resposta_orcamento_cliente.html");
		return mensagem
			.replace("[NOMECLIENTE]", orcamento.getCliente().getNome())
			.replace("[DATA]", CesareUtil.formatarData(orcamento.getDataCadastro(), "dd/MM/yyyy"))
			.replace("[ORIGEM]", orcamento.getDetalheOrigem())
			.replace("[DESTINO]", orcamento.getDetalheDestino())
			.replace("[PESO]", orcamento.getPeso())
			.replace("[DIMENSAO]", orcamento.getDimensao())
			.replace("[MENSAGEM]", mesagemOriginal)
			.replace("[RESPOSTA]", resposta);
	}

	public static String getMensagemRespostaContatoCliente(String mensagem,	String resposta, String data) {
		String novaMensagem  = new CesareUtil().lerArquivo(PATH + "resposta_contato_cliente.html");
		return novaMensagem
			.replace("[DATA]", data)
			.replace("[MENSAGEM]", mensagem)
			.replace("[RESPOSTA]", resposta);
	}

	public static String getMensagemConfirmacaoServico(int codigoServico, int idOrcamento, String nomeCliente) {
		String mensagem  = new CesareUtil().lerArquivo(PATH + "notificacao_cliente_servico.html");
		return mensagem
			.replace("[NOMEDOCLIENTE]", nomeCliente)
			.replace("[CODIGOORCAMENTO]", String.valueOf(idOrcamento))
			.replace("[CODIGOSERVICO]",  String.valueOf(codigoServico));		
	}
}