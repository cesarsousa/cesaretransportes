package br.com.cesaretransportes.modelo;

import br.com.cesaretransportes.util.CesareUtil;

public class Telefone {
	
	// PK
	private int idTelefone;
	 
	// FKs
	private int idEmpresa;
	private int idCliente;
		
	private String ddd;
	private String numero;
	private String complemento;
	
	public Telefone(int idTelefone, int idEmpresa, int idCliente, String ddd, String numero, String complemento) {
		this(idEmpresa, idCliente, ddd, numero, complemento);
		this.idTelefone = idTelefone;		
	}
	
	public Telefone(int idEmpresa, int idCliente, String ddd, String numero,
			String complemento) {		
		this.idEmpresa = idEmpresa;
		this.idCliente = idCliente;
		this.ddd = ddd;
		this.numero = numero;
		this.complemento = complemento;
	}

	public Telefone() {}

	public int getIdTelefone() {
		return idTelefone;
	}
	public void setIdTelefone(int idTelefone) {
		this.idTelefone = idTelefone;
	}
	public int getIdEmpresa() {
		return idEmpresa;
	}
	public void setIdEmpresa(int idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
	public int getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}
	public String getDdd() {
		return ddd;
	}
	public void setDdd(String ddd) {
		this.ddd = ddd;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	
	@Override
	public String toString() {
		String c = complemento.isEmpty() ? "" : " / " + complemento;
		return CesareUtil.formatarTelefone(ddd + numero) + c;
	}
}
