package br.com.cesaretransportes.modelo;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import br.com.cesaretransportes.util.CesareUtil;

public class Cliente {
	
	private int idCliente;
	private String tipoCliente;
	private String nome;
	private String email;
	private TipoDoDocumento tipoDoDocumento;
	private String numeroDoDocumento;
	private Telefone telefone;
	private Endereco endereco;
	private String senha;
	private Calendar dataCadastro;
	private Calendar dataExclusao;
	private boolean statusCliente;	
	private List<Orcamento> orcamentos;
	
	public Cliente(){}
	
	public Cliente(String nome, String tipoCliente, String email, TipoDoDocumento tipoDoDocumento, String numeroDoDocumento,
			Telefone telefone, Endereco endereco, String senha, boolean statusCliente, Calendar dataCadastro, Calendar dataExclusao, List<Orcamento> orcamentos) {		
		this.nome = nome;
		this.tipoCliente = tipoCliente;
		this.email = email;
		this.tipoDoDocumento = tipoDoDocumento;
		this.numeroDoDocumento = numeroDoDocumento;
		this.telefone = telefone;
		this.endereco = endereco;
		this.senha = senha;
		this.statusCliente = statusCliente;
		this.dataCadastro = dataCadastro;
		this.dataExclusao = dataExclusao;
		this.orcamentos = orcamentos;
	}
	
	public Cliente(int idCliente, String nome, String tipo, String email, TipoDoDocumento tipoDoDocumento, String numeroDoDocumento,
			Telefone telefone, Endereco  endereco, String senha, boolean statusCliente, Calendar dataCadastro, Calendar dataExclusao, List<Orcamento> orcamentos ) {		
		this(nome, tipo, email, tipoDoDocumento, numeroDoDocumento, telefone, endereco, senha, statusCliente, dataCadastro, dataExclusao, orcamentos);
		this.idCliente = idCliente;		
	}

	public Cliente(String nome, String email) {
		this.nome = nome;
		this.email = email;
		this.tipoDoDocumento = TipoDoDocumento.NA;
		this.dataCadastro = Calendar.getInstance();
		this.tipoCliente = "U";
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getTipoCliente() {
		return tipoCliente;
	}
	
	public void setTipoCliente(String tipoCliente) {
		this.tipoCliente = tipoCliente;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNumeroDoDocumento() {
		return numeroDoDocumento;
	}

	public void setNumeroDoDocumento(String numeroDoDocumento) {
		this.numeroDoDocumento = numeroDoDocumento;
	}
	
	public String getNumeroDoDocumentoFormatado(){
		return CesareUtil.formatarDocumento(tipoDoDocumento, numeroDoDocumento);
	}

	public Telefone getTelefone() {
		return telefone;
	}

	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}
	
	public Endereco getEndereco() {
		return endereco;
	}
	
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public boolean isCliente() {
		return statusCliente;
	}
	
	public String getSituacaoCliente(){
		return statusCliente ? "Confirmado" : "Pendente";
	}

	public void setStatusCliente(boolean cliente) {
		this.statusCliente = cliente;
	}
	
	public TipoDoDocumento getTipoDoDocumento() {
		return tipoDoDocumento;
	}
	
	public void setTipoDoDocumento(TipoDoDocumento tipoDoDocumento) {
		this.tipoDoDocumento = tipoDoDocumento;
	}
	
	public Calendar getDataCadastro() {		
		return dataCadastro;
	}
	
	public String getDataCadastroFomatada(){
		return dataCadastro == null ? "Sem informa&ccedil;&atilde;o" : CesareUtil.formatarData(dataCadastro, "dd/MM/yyyy");
	}
		
	public Calendar getDataExclusao() {
		return dataExclusao;
	}
	
	public void setDataExclusao(Calendar dataExclusao) {
		this.dataExclusao = dataExclusao;
	}
	
	public List<Orcamento> getOrcamentos() {
		return orcamentos;
	}
	
	public void setOrcamentos(List<Orcamento> orcamentos) {
		this.orcamentos = orcamentos;
	}
	
	public boolean isTemOrcamentos(){
		return !orcamentos.isEmpty() ? true : false;
	}
	
	public boolean isDeletado(){
		return dataExclusao == null ? false : true; 
	}
	
	public boolean isAtivo(){
		return dataExclusao == null ? true : false;
	}
	
	public enum TipoDoDocumento {
		CPF("cpf"),
		CNPJ("cnpj"),
		NA("na");
		
		private String codigo;
		
		private TipoDoDocumento(String codigo){
			this.codigo = codigo;
		}
		
		public static TipoDoDocumento criarPorCodigo(String codigo){
			if (codigo == null) {
				throw new IllegalArgumentException("codigo de documento nulo");
			}
			
			for (TipoDoDocumento tipo : TipoDoDocumento.values()){
				if (codigo.equals(tipo.codigo)){
					return tipo;
				}
			}			
			throw new IllegalArgumentException("c�digo de documento inv�lido: " + codigo);			
		}
		
		public String toString() {			
			return codigo;
		}
	}

	public void serialize(DataOutputStream dataOut) throws IOException {		
		dataOut.writeInt(idCliente);
		dataOut.writeUTF(nome);
		dataOut.writeUTF(tipoCliente);
		dataOut.writeUTF(email);
		
		String codigo = tipoDoDocumento.toString();
		dataOut.writeUTF(codigo);	
		
		dataOut.writeUTF(numeroDoDocumento);
		
		dataOut.writeUTF(senha);
		
		long time = dataCadastro.getTimeInMillis();
		dataOut.writeLong(time);
		
		long dtExclusao = dataExclusao == null ? 0 : dataExclusao.getTimeInMillis();
		dataOut.writeLong(dtExclusao);		
		
		String status = String.valueOf(statusCliente);
		dataOut.writeUTF(status);
		
		// serializa��o do telefone		
		dataOut.writeInt(getTelefone().getIdTelefone());		
		dataOut.writeInt(getTelefone().getIdCliente());
		dataOut.writeUTF(getTelefone().getDdd());
		dataOut.writeUTF(getTelefone().getNumero());
		if(getTelefone().getComplemento() == null || getTelefone().getComplemento().isEmpty()){
			dataOut.writeUTF("null");
		}else{
			dataOut.writeUTF(getTelefone().getComplemento());
		}
		
		// serializa��o do endere�o
		dataOut.writeInt(getEndereco().getIdEndereco());		
		dataOut.writeInt(getEndereco().getCliente().getIdCliente());
		dataOut.writeUTF(getEndereco().getCidade());
		dataOut.writeUTF(getEndereco().getEstado());
		dataOut.writeUTF(getEndereco().getLocalizacao());		
	}	
}