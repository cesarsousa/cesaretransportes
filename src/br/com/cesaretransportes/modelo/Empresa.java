package br.com.cesaretransportes.modelo;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Empresa implements Serializable {
	private static final long serialVersionUID = 1L;
	
	// PK
	private int idEmpresa;
	
	private Endereco endereco; //ok
	
	private String nome;
	private String cnpj;
	private String msn;
	private String email;
	private String senha;
	private boolean mostrarMapa;
	private String localizacao;	
	
	private List<Telefone> telefones;
	
	public Empresa(){}
	
	public Empresa(Endereco endereco, ArrayList<Telefone> telefones) {
		this.endereco = endereco;
		this.telefones = telefones;
	}
	
	public Empresa(
			int idEmpresa, 
			String nome, 
			Endereco endereco, 
			String cnpj, 
			String msn, 
			String email, 
			String senha, 
			Boolean mostrarMapa, 
			String localizacao, 
			List<Telefone> telefones) {
		this.idEmpresa = idEmpresa;
		this.nome = nome;
		this.endereco = endereco;
		this.cnpj = cnpj;
		this.msn = msn;
		this.email = email;
		this.senha = senha;
		this.mostrarMapa = mostrarMapa;
		this.localizacao = localizacao;
		this.telefones = telefones;
	}

	public int getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(int idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getMsn() {
		return msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public void setMostrarMapa(boolean mostrarMapa) {
		this.mostrarMapa = mostrarMapa;
	}
	
	public boolean isMostrarMapa() {
		return mostrarMapa;
	}
	
	public String getLocalizacao() {
		return localizacao;
	}
	
	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}
	
	public List<Telefone> getTelefones() {
		return telefones;
	}
	
	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}
	
	public Telefone getTel1() {
		if(telefones.size() > 0){
			return telefones.get(0);
		}
		return new Telefone();
	}	
	
	public Telefone getTel2() {
		if(telefones.size() > 1){
			return telefones.get(1);
		}
		return new Telefone();
	}	
	
	public Telefone getTel3() {
		if(telefones.size() > 2){
			return telefones.get(2);
		}
		return new Telefone();
	}	
	
	public String getDetalheEndereco(){
		return endereco.getLocalizacao() + ", " + endereco.getCidade() + " - " + endereco.getEstado();
	}
	
	public String getTelefone1(){
		try {
			return telefones.get(0).toString();
		} catch (ArrayIndexOutOfBoundsException e) {
			return "";
		}	
	}
	
	public String getComplemento1(){
		try {
			return telefones.get(0).toString();
		} catch (ArrayIndexOutOfBoundsException e) {
			return "";
		}	
	}
	
	public String getTelefone2(){
		try {
			return telefones.get(1).toString();
		} catch (ArrayIndexOutOfBoundsException e) {
			return "";
		}
	}
	
	public String getTelefone3(){
		try {
			return telefones.get(2).toString();
		} catch (ArrayIndexOutOfBoundsException e) {
			return "";
		}
	}


	public void serialize(DataOutputStream dataOut) throws IOException{		
		dataOut.writeInt(idEmpresa);
		dataOut.writeUTF(nome);		
		dataOut.writeUTF(cnpj);
		dataOut.writeUTF(msn);
		dataOut.writeUTF(email);
		dataOut.writeUTF(senha);
		dataOut.writeInt(endereco.getIdEndereco());
		dataOut.writeUTF(endereco.getCidade());
		dataOut.writeUTF(endereco.getEstado());
		dataOut.writeUTF(endereco.getLocalizacao());
		
		dataOut.writeInt(telefones.size());
		for(Telefone telefone : telefones){
			dataOut.writeUTF(telefone.getDdd());
			dataOut.writeUTF(telefone.getNumero());
			dataOut.writeUTF(telefone.getComplemento());			
		}		
	}
	
}
