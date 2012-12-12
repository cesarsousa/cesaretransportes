package br.com.cesaretransportes.modelo;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public class Veiculo {	
	
	private static final String SEM_INFORMACAO = "N&atilde;o dispon&iacute;vel";
	
	private int idVeiculo;
	private String marca;
	private String cor;
	private String placa;	
	private String localizacao;
	private Calendar dataExclusao;	
	private List<Servico> servicos;	
	
	public int getIdVeiculo() {
		return idVeiculo;
	}
	
	public void setIdVeiculo(int idVeiculo) {
		this.idVeiculo = idVeiculo;
	}
	
	public String getMarca() {
		return marca;
	}
	
	public void setMarca(String marca) {
		this.marca = marca;
	}
	
	public String getCor() {
		return cor;
	}
	
	public void setCor(String cor) {
		this.cor = cor;
	}
	
	public String getPlaca() {
		return placa;
	}
	
	public String getDetalhePlaca(){
		return placa.isEmpty() ? SEM_INFORMACAO : placa.substring(0, 3) + "-" + placa.substring(3);
	}
	
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	
	public String getLocalizacao() {
		return localizacao;
	}
	
	public String getInfoLocalizacao(){
		if(localizacao == null || localizacao.isEmpty()){
			return SEM_INFORMACAO;
		}
		return localizacao;
	}		
	
	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}
	
	public Calendar getDataExclusao() {
		return dataExclusao;
	}
	
	public void setDataExclusao(Calendar dataExclusao) {
		this.dataExclusao = dataExclusao;
	}	
	
	public List<Servico> getServicos() {
		return servicos;
	}
	
	public void setServicos(List<Servico> servicos) {
		this.servicos = servicos;
	}	
	
	public boolean isDeletado(){
		return dataExclusao == null ? false : true;
	}
	
	public boolean isEmRota(){
		return servicos.size() != 0 ? true : false;
	}
	
	public void serialize(DataOutputStream dataOut) throws IOException {
		dataOut.writeInt(idVeiculo);
		dataOut.writeUTF(marca);
		dataOut.writeUTF(cor);
		dataOut.writeUTF(placa);
		
		if(localizacao == null || localizacao.equals("")){
			dataOut.writeUTF("nao disponivel");
		}else{
			dataOut.writeUTF(localizacao);		
		}
			
		long dtExclusao;
		if (dataExclusao == null){
			dtExclusao = 0;
		}else{
			dtExclusao = dataExclusao.getTimeInMillis();
		}
		dataOut.writeLong(dtExclusao);		
		dataOut.writeInt(servicos.size());
	}
}