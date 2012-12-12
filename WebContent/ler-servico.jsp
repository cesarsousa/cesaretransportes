<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="icon" type="image/gif" href="imagens/animated_favicon1.gif" />
<link rel="stylesheet" type="text/css" href="estilo.css">
<link rel="stylesheet" type="text/css" href="estilo2.css">
<script language="javascript">
function abrirJanela(){
	var esquerda = (screen.width - 550)/2;
	var topo = (screen.height - 250)/2;
	alertWindow = window.open("telaAguarde.html","bookpixWin","width=550, height=250, top="+topo+", left="+esquerda);	
}

function fecharJanela(){
	alertWindow.close();
}

function validarFormulario(){
	doc = document.formConfirmaServico;
	if (doc.valor.value == ""){
		alert("< O campo valor deve ser preenchido! >");		
		return false;
	}
	
	if(doc.dataPrevEntrega.value == ""){
		alert("< O campo da data prevista para entrega deve ser preenchido! >");		
		return false;
	}	
}
</script>
<style type="text/css">
font.email {
	color: #996600;
	font-family: Comic Sans MS, Geneva, sans-serif;
	font-size: +3;	
	font-weight: bold;
}

font.origem {
	color: #333333;
	font-family: Comic Sans MS, Geneva, sans-serif;
	font-size: +3;	
	font-weight: bold;
}
textarea {	
    border:1px solid #999999;
    width:100%;
    margin:5px 0;
    padding:3px;
}

.label{
	color: #000000;
	font-style: italic;
	font-weight: bold;
	font-family: Comic Sans MS, Geneva, sans-serif;
	font-size: small;
}

.erro{
	font-size: 13px;
	font-weight: bold;
	font-family: Verdana, Geneva, sans-serif;
	color: #FFFFFF;	
}

.colada{
	border-collapse: collapse;
}
</style>
<title>Cesare Transportes - Servi&ccedil;os</title>
</head>

<body onunload="fecharJanela();">

<jsp:include page="includeCabecalhoLerEmail.jspf"></jsp:include>

<h3>Detalhe do servi&ccedil;o</h3>


<c:if test="${not empty orcamentoAlterado}">
	<h4>Or&ccedil;amento alterado com sucesso.</h4>
</c:if>

<br />
<table width="1000" align="center" border="0" class="colada">
	<tr>
		<td colspan="4" align="right">enviado em <b>${servico.orcamento.infoDataCadastro}</b></td> 
	</tr>
	<tr>
		<td colspan="4" align="right">data prevista para entrega em <b>${servico.infoDataPrevEntrega}</b></td> 
	</tr>
	<tr>	
		<td colspan="4" align="right">entregue em <b>${servico.infoDataEntrega}</b></td> 
	</tr>
	<tr>
		
		<c:choose>
			<c:when test="${not empty buscar}">
				<td width="25" bgcolor="#FFA500"><a href="javascript:history.go(-1)">
					<img src="imagens/voltar_20px.png" alt="voltar" title="voltar" border="0" /></a></td>
				<td width="25" bgcolor="#FFA500">
					<a href="ProcessarAcaoServico?idServico=${servico.idServico}
										&acao=excluirServico
										&buscar=true
										&paramBusca=${paramBusca}
										&opcao=${opcao}
										&filtro=${filtro}" onclick="javascript:return confirm('Deletar este Orcamento ?')">
					<img src="imagens/excluir_20px.png" alt="excluir email" title="excluir serviço" border="0" /></a></td>
			</c:when>
			<c:otherwise>				
					<td width="25" bgcolor="#FFA500"><a href="mostrarListaDosOrcamentos?opcao=idServico&tipo=servico">
					<img src="imagens/voltar_20px.png" alt="caixa de entrada" title="caixa de entrada" border="0" /></a></td>
				
					<td width="25" bgcolor="#FFA500">
					<a href="ProcessarAcaoServico?idServico=${servico.idServico}&acao=excluirServico											
											&opcao=${opcao}
											&filtro=${filtro}" onclick="javascript:return confirm('Deletar este Orcamento ?')">
					<img src="imagens/excluir_20px.png" alt="excluir email" title="excluir email" border="0" /></a></td>
										
			</c:otherwise>
		</c:choose>
		
		<c:choose>
			<c:when test="${servico.ativo}">
				<td width="35" align="left" bgcolor="#FFA500">
					<img src="imagens/van_servico_30_30.png" title="servico ativo" alt="servico ativo" border="0" />
				</td>
				<td width="100%" bgcolor="#FFA500">
					<c:if test="${not servico.deletado}">
					<form action="ProcessarAcaoServico" method="post" onsubmit="javascript:return confirm('Confirmar este serviço com a data de hoje?')">
						<input type="hidden" name="idServico" value="${servico.idServico}"/>																		
						<input type="hidden" name="acao" value="confirmarEntrega"/>
						<input type="submit" value="Confirmar Entrega"/>
					</form>
					</c:if>
				</td>
			</c:when>
			<c:otherwise>
				<td width="35" align="left" bgcolor="#FFA500">
					<img src="imagens/van_servico_entregue_30_30.png" title="servico entregue" alt="servico entregue" border="0" />
				</td>
				<td width="100%" bgcolor="#FFA500"></td>
			</c:otherwise>
		</c:choose>							
	</tr>
</table>

<table class="laranjado" width="1000" cellspacing="10" bgcolor="#CCCCCC" align="center">
	<tr>
		<td>
		<h1>Servi&ccedil;o n&ordm; ${servico.idServico}.</h1>		
		<h1>Referente ao or&ccedil;amento n&ordm; ${servico.orcamento.idOrcamento}.</h1>		
		De <font class="email">${servico.orcamento.cliente.nome } (${servico.orcamento.cliente.email })</font><br />
		Para <font class="origem">Cesare Transportes</font>
		</td>
	</tr>
</table>
<br />		
			<table class="laranjado" width="1000" cellpadding="10" bgcolor="#FFFFFF" align="center">
				<tr><td>
					<p><b>Dados do Solicitante.</b></p>
					<p>Nome: ${ servico.orcamento.cliente.nome }</p>
					<p>Email: ${ servico.orcamento.cliente.email }</p>
					<p>Telefone: (${servico.orcamento.cliente.telefone.ddd}) ${servico.orcamento.cliente.telefone.numero}  ${servico.orcamento.cliente.telefone.complemento}</p>
					<br/>
					<p><b>Dados do Servi&ccedil;o.</b></p>
					<p>N&ordm;: ${ servico.idServico }</p>
					<p>Ve&iacute;culo: C&oacute;d: ${ servico.veiculo.idVeiculo } - ${servico.veiculo.detalhePlaca }</p>
					<p><b>Dados do Or&ccedil;amento.</b></p>
					<p>C&oacute;d: ${ servico.orcamento.idOrcamento }</p>
					<p>Origem: ${ servico.orcamento.detalheOrigem }</p>
					<p>Destino: ${ servico.orcamento.detalheDestino }</p>
					<p>Peso: ${ servico.orcamento.peso }</p>
					<p>Dimens&atilde;o: ${ servico.orcamento.dimensao }</p>
					<p>Valor: ${ servico.valor }</p>
					<br/>					
					<p><b>Mensagem.</b></p>
					<p>${orcamento.mensagem }</p>
				</td></tr>
			</table>			
<br />
<jsp:include page="includeRodapeSI.jspf"></jsp:include>
</body>
</html>
