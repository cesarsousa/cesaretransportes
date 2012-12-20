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
<title>Cesare Transportes - Or&ccedil;amentos</title>
</head>

<body onunload="fecharJanela();">

<jsp:include page="includeCabecalhoLerEmail.jspf"></jsp:include>

<h3>Detalhe do or&ccedil;amento</h3>


<c:if test="${not empty orcamentoAlterado}">
	<h4>Or&ccedil;amento alterado com sucesso.</h4>
</c:if>

<c:if test="${erroValidadeData}">
	<table width="100%" bgcolor="#B22222" cellpadding="5">
		<tr>
			<td>
			<div align="center">
			<label class="erro">Erro no formato da data no campo 'Data entrega prevista'. Verifique se dia, m&ecirc;s e ano est&atilde;o corretos</label>
			</div>
		</tr>
	</table>
</c:if>

<c:if test="${erroData}">
	<table width="100%" bgcolor="#B22222" cellpadding="5">
		<tr>
			<td>
			<div align="center">
			<label class="erro">Erro no formato da data no campo 'Data entrega prevista'. formato esperado DD/MM/AAAA</label>
			</div>
		</tr>
	</table>
</c:if>

<c:if test="${erroValor}">
<table width="100%" bgcolor="#B22222" cellpadding="5">
		<tr>
			<td>
			<div align="center">
			<label class="erro">Erro no formato do valor no campo 'Valor'. formato esperado 999,99 ou 999</label>
			</div>
		</tr>
</table>
</c:if>

<br />
<table width="1000" align="center" border="0" class="colada" cellpadding="5">
	<tr>
		<td colspan="4" align="right">enviado em <b>${orcamento.infoDataCadastro}</b></td> 
	</tr>
	<tr>
		<td colspan="4" align="right">data prevista para entrega em <b>${orcamento.infoDataPrevEntrega}</b></td> 
	</tr>
	<tr>	
		<td colspan="4" align="right">entregue em <b>${orcamento.infoDataEntrega}</b></td> 
	</tr>
	<tr>
		
		<c:choose>
			<c:when test="${not empty buscar}">
				<td width="25"><a href="javascript:history.go(-1)">
					<img src="imagens/voltar_20px.png" alt="voltar" title="voltar" border="0" /></a></td>
				<td width="25">
					<a href="processarAcaoOrcamento?codigo=${orcamento.idOrcamento}
										&acao=excluirOrcamento
										&buscar=true
										&paramBusca=${paramBusca}
										&opcao=${opcao}
										&filtro=${filtro}" onclick="javascript:return confirm('Deletar este Orcamento ?')">
					<img src="imagens/excluir_20px.png" alt="excluir orcamento" title="excluir orcamento" border="0" /></a></td>
			</c:when>
			<c:otherwise>				
				
					<td width="25"><a href="mostrarListaDosOrcamentos?opcao=idOrcamento&tipo=orcamento">
					<img src="imagens/voltar_20px.png" alt="voltar" title="voltar" border="0" /></a></td>
				
					<td width="25"><a href="processarAcaoOrcamento?codigo=${orcamento.idOrcamento}
											&acao=excluirOrcamento											
											&opcao=${opcao}
											&filtro=${filtro}" onclick="javascript:return confirm('Deletar este Orcamento ?')">
					<img src="imagens/excluir_20px.png" alt="excluir orcamento" title="excluir orcamento" border="0" /></a></td>
										
			</c:otherwise>
		</c:choose>
			
					<!-- <td width="35" align="left" bgcolor="#FFA500">
					<img src="imagens/van_orcamento_30_30.png" title="Orcamento de Servico" alt="Orcamento de Servico" border="0" />
					</td> -->
					<%-- <td width="100%" bgcolor="#FFA500">
					<c:if test="${not orcamento.deletado}">
					<form action="processarAcaoOrcamento" method="get" name="formConfirmaServico" onsubmit="return validarFormulario();">
						<label class="label">Valor: </label><input id="valor" type="text" name="valor" value="${valor}"/>
						<label class="label">Data entrega prevista: </label><input id="dtPrevEntrega" type="text" name="dataPrevEntrega" value="${dataPrevEntrega}"/>
						<label class="label">Id Veiculo: </label>
						<select name="idVeiculo">						
							<c:forEach items="${veiculosCadastrado}" var="veiculo">
								<option class="label" value="${veiculo.idVeiculo}">C&oacute;d: <c:out value="${veiculo.idVeiculo}"/> - <c:out value="${veiculo.detalhePlaca}"/></option>
							</c:forEach>						
						</select>
						<input type="hidden" name="codigo" value="${orcamento.idOrcamento}"/>																		
						<input type="hidden" name="acao" value="confirmarServico"/>
						<input type="submit" value="Confirmar serviço" onclick="abrirJanela();"/>
					</form>
					</c:if>
					</td> --%>			
	</tr>
</table>

<table class="laranjado" width="1000" cellspacing="10" bgcolor="#CCCCCC" align="center">
	<tr>
		<td>
		<h1>Or&ccedil;amento de servi&ccedil;o n&ordm; ${orcamento.idOrcamento}.</h1>		
		<h1>Id do cliente ${orcamento.cliente.idCliente}.</h1>		
		De <font class="email">${orcamento.cliente.nome } (${orcamento.cliente.email })</font><br />
		Para <font class="origem">Cesare Transportes</font>
		</td>
	</tr>
</table>
<br />		
			<table class="laranjado" width="1000" cellpadding="10" bgcolor="#FFFFFF" align="center">
				<tr><td>
					<p><b>Dados do Solicitante.</b></p>
					<p>Nome: ${ orcamento.cliente.nome }</p>
					<p>Email: ${ orcamento.cliente.email }</p>
					<p>Telefone: (${orcamento.cliente.telefone.ddd}) ${orcamento.cliente.telefone.numero}  ${orcamento.cliente.telefone.complemento}</p>
					<br/>
					<p><b>Dados do Or&ccedil;amento.</b></p>
					<p>Origem: ${ orcamento.detalheOrigem }</p>
					<p>Destino: ${ orcamento.detalheDestino }</p>
					<p>Peso: ${ orcamento.peso }</p>
					<p>Dimens&atilde;o: ${ orcamento.dimensao }</p>
					<br/>					
					<p><b>Mensagem.</b></p>
					<p>${orcamento.mensagem }</p>
				</td></tr>
			</table>
			
			<c:set var="mensagem" value="${orcamento.mensagem}" />			
			<c:set var="res" value="Res:" />			
			<c:choose>
				<c:when test="${orcamento.orcamentoRespondido}">
					<h3>Or&ccedil;amento j&aacute; foi respondido</h3>
				</c:when>
				<c:otherwise>
				<c:if test="${not orcamento.deletado }">
				<form action="enviarOrcamento" method="post">	
					<input type="hidden" name="acao" value="responderOrcamento">		
					<input type="hidden" name="codigo" value="${orcamento.idOrcamento}">						
					<div align="center">
						<table width="1000" align="center"><tr><td>
							<input type="submit" value="Enviar Resposta" onclick="abrirJanela();">
							<i><textarea class="laranjado" name="resposta" rows="10">Digite a resposta</textarea></i>
						</td></tr></table>	
					</div>	
				</form>
				</c:if>				
				</c:otherwise>			
			</c:choose>		
<p>.</p>
<p>.</p>
<div id="footerIndex">
	<label class="sizeMedium">&copy; 2011 Cesare Transportes - Todos os Direitos Reservados</label>	
</div>
</body>
</html>
