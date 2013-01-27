package br.com.cesaretransportes.util;

/**
 * Mensagens de texto utilizadas na aplicacao.
 * 
 * @author Cesar Sousa.
 *
 */
public enum MSG {
	LOGIN("Login inv&aacute;lido!<br/>Verifique se voc&ecirc; digitou seu email e senha corretamente.<br/>certifique-se " +
			"tamb&eacute;m da ocorr&ecirc;ncia de letras maiusculas e minusculas na sua digita&ccedil;&atilde;o."),
	LOGIN_PENDENTE("Seu cadastro ainda aguarda confirma&ccedil;&atilde;o, Desculpe-nos pelo transtorno estamos " +
			"dispon&iacute;vel a ouv&iacute;-lo(a) em um de nossos canais de contato, ou tente novamente assim que receber " +
			"o email de confirma&ccedil;&atilde;o. Grato pela sua compreens&ccedil;&atilde;o"),
	CLIENTE_CADASTRADO("Ol&aacute; NOME, seu cadastro foi realizado com sucesso!<br /><br />" +
			"Por favor aguarde que em breve voc&ecirc; receber&aacute; um email de confirma&ccedil;&atilde;o.<br /><br />" +
			"atenciosamente<br />Equipe Portal Cesare Transportes."),
	ERRO_PREFIXO_PLACA("O prefixo da placa deve conter exatamente 3 letras."),
	ERRO_SUFIXO_PLACA("O sufixo da placa deve conter exatamente 4 d&iacute;gitos."),
	ERRO_MARCA("A marca do ve&iacute;culo deve ser preenchida."),
	ERRO_COR("A cor do ve&iacute;culo deve ser preenchida."),
	ERRO_DATA("Erro na data, verifique se os campos dia, m&ecirc;s e ano est&atilde;o consistentes."), 
	ERRO_VEICULO_CADASTRADO("A placa digitada j&aacute; esta cadastrada na base."),
	ERRO_CLIENTE("Ocorreu um erro inexperado durante sua solicita&ccedil;&atilde;o.<br /><br />" +
			"Sua se&ccedil;&atilde;o teve que ser encerrada. Desculpe-nos pelo transtorno.<br /><br />" +
			"Atenciosamente...");
	
	private String mensagem;
	
	private MSG(String mensagem){
		this.mensagem = mensagem;
	}
	
	@Override
	public String toString() {
		return mensagem;
	}
}
