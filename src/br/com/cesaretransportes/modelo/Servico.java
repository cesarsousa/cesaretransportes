package br.com.cesaretransportes.modelo;

import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;

import br.com.cesaretransportes.util.CesareUtil;

public class Servico {
	
	private int idServico;
	private Orcamento orcamento;
	private Veiculo veiculo;
	private BigDecimal valor;
	private Calendar dataPrevEntrega;
	private Calendar dataEntrega;
	private Calendar dataExclusao;
	
	public Servico(int idServico, Orcamento orcamento, Veiculo veiculo,
			BigDecimal valor, Calendar dataPrevEntrega, Calendar dataEntrega, Calendar dataExclusao) {
		this.idServico = idServico;
		this.orcamento = orcamento;
		this.veiculo = veiculo;
		this.valor = valor;
		this.dataPrevEntrega = dataPrevEntrega;
		this.dataEntrega = dataEntrega;
		this.dataExclusao = dataExclusao;
	}
	
	public Servico(Orcamento orcamento, Veiculo veiculo,
			BigDecimal valor, Calendar dataPrevEntrega, Calendar dataEntrega) {		
		this.orcamento = orcamento;
		this.veiculo = veiculo;
		this.valor = valor;
		this.dataPrevEntrega = dataPrevEntrega;
		this.dataEntrega = dataEntrega;
	}
	
	public int getIdServico() {
		return idServico;
	}
	
	public void setIdServico(int idServico) {
		this.idServico = idServico;
	}
	
	public Orcamento getOrcamento() {
		return orcamento;
	}
	
	public void setOrcamento(Orcamento orcamento) {
		this.orcamento = orcamento;
	}
	
	public Veiculo getVeiculo() {
		return veiculo;
	}
	
	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}
	
	public BigDecimal getValor() {
		return valor;
	}
	
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
	public Calendar getDataPrevEntrega() {
		return dataPrevEntrega;
	}
	
	public void setDataPrevEntrega(Calendar dataPrevEntrega) {
		this.dataPrevEntrega = dataPrevEntrega;
	}
	
	public Calendar getDataEntrega() {
		return dataEntrega;
	}
	
	public void setDataEntrega(Calendar dataEntrega) {
		this.dataEntrega = dataEntrega;
	}
		
	public boolean isAtivo() {
		return dataEntrega == null ? true : false;		 
	}
	
	public String getInfoDataPrevEntrega(){
		return dataPrevEntrega == null ? "Nao disponivel" : CesareUtil.formatarData(dataPrevEntrega, "dd/MM/yyyy");
	}
	
	public String getInfoDataEntrega(){
		return dataEntrega == null ? "Nao disponivel" : CesareUtil.formatarData(dataEntrega, "dd/MM/yyyy");
	}
	public boolean isDeletado(){
		return dataExclusao != null ? true : false;
	}

	public void serialize(DataOutputStream dataOut) throws IOException {
		dataOut.writeInt(idServico);
		
		dataOut.writeInt(orcamento.getIdOrcamento());
		dataOut.writeInt(orcamento.getCliente().getIdCliente());
		dataOut.writeUTF(orcamento.getPeso());
		dataOut.writeUTF(orcamento.getDimensao());
		dataOut.writeUTF(orcamento.getMensagem());
		dataOut.writeBoolean(orcamento.isOrcamentoLido());
		dataOut.writeBoolean(orcamento.isOrcamentoRespondido());
		
		dataOut.writeLong(orcamento.getDataCadastro().getTimeInMillis());
		
		dataOut.writeInt(veiculo.getIdVeiculo());
		dataOut.writeUTF(veiculo.getMarca());
		dataOut.writeUTF(veiculo.getCor());
		dataOut.writeUTF(veiculo.getPlaca());
		dataOut.writeUTF(veiculo.getLocalizacao());
		
		dataOut.writeUTF(String.valueOf(valor));
		
		long dtPrevEntrega;
		if(dataPrevEntrega == null){
			dtPrevEntrega = 0;
		}else{
			dtPrevEntrega = dataPrevEntrega.getTimeInMillis();
		}
		dataOut.writeLong(dtPrevEntrega);
		
		long dtEntrega;
		if(dataEntrega == null){
			dtEntrega = 0;
		}else{
			dtEntrega = dataEntrega.getTimeInMillis();
		}
		dataOut.writeLong(dtEntrega);		
	}
}