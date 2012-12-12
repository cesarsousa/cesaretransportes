package br.com.cesaretransportes.modelo;

public class Contato {
	
	private int codigo;
	private int idCliente;
	private String nome;
	private String email;
	private String mensagem;
	private String data;
	private boolean emailLido;
	private boolean emailSalvo;	

	public Contato(){
		this.emailLido = false;
		this.emailSalvo = false;
	}
	
	public int getCodigo(){
		return codigo;
	}	
	
	public void setCodigo(String codigo) {
		this.codigo = Integer.parseInt(codigo);		
	}	

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
	public boolean isEmailLido() {
		return emailLido;
	}
	
	public void setEmailLido(boolean status){
		this.emailLido = status;
	}
	
	public boolean isEmailSalvo() {
		return emailSalvo;
	}

	public void setEmailSalvo(boolean emailSalvo) {
		this.emailSalvo = emailSalvo;
	}
}
