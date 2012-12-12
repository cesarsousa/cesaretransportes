<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page import="java.util.List"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="icon" type="image/gif" href="imagens/animated_favicon1.gif" />
<link rel="stylesheet" type="text/css" href="estilo.css" />
<link rel="stylesheet" type="text/css" href="estilo2.css" />

<style type="text/css">
p {
	color: #333;
	font-weight: bold;
}

p.estilo {
	color: #FF6600
}

table {
	border-collapse: collapse;
}

.label1 {
	font-size: 14px;
	font-weight: bold;
	font-family: Verdana, Geneva, sans-serif;
	color: #333;
	padding: 10px;
}

.label2 {
	font-size: 12px;
	font-style: italic;
	font-family: Arial, Helvetica, sans-serif;
	color: #333;
	padding: 10px;
}

</style>
<title>Cesare Transportes - Servi&ccedil;os</title>
</head>

<body bgcolor="#ffffff">

<jsp:include page="includeCabecalhoComLinks.jspf"></jsp:include>

<h3>${mensagem}</h3>

<label class="label1">Legenda de tipos de servi&ccedil;os</label>
<ul>
	<li><img alt="orcamento nao confirmado" src="imagens/van_orcamento_30_30.png">
		<label class="label2">Representa um or&ccedil;amento comuns, ou seja, n&atilde;o foi confimado com um servi&ccedil;o ativo.</label>
	<li><img alt="orcamento confirmado como servico" src="imagens/van_servico_30_30.png">
		<label class="label2">Representa um servi&ccedil;o ativo que n&atilde;o foi entregue.</label>
	<li><img alt="serviço finalizado" src="imagens/van_servico_entregue_30_30.png">
		<label class="label2">Representa um servi&ccedil;o inativo, ou seja, um servi&ccedil;o ativo j&aacute; entregue.</label></li>
</ul>

<table width="100%">
	<tr>
		<td align="left"><jsp:useBean id="data" class="java.util.Date" />
		<p class="estilo"><fmt:formatDate value="${data}" dateStyle="full" />.</p>
		</td>		
	</tr>
</table>

<c:if test="${empty listaDeServicos}">
	<h3>Voce não possui nenhum servi&ccedil;o no momento</h3>
</c:if>

<c:if test="${not empty listaDeServicos}">
	<table class="laranjado" width="100%" bgcolor="#ffffff"	cellpadding="10">
		<tr>
			<td><c:forEach var="servico" items="${listaDeServicos}">
				<table width="100%" border="0">
					<tr>
						<td width="30">
						<a href="ProcessarAcaoServico?idServico=${servico.idServico}&acao=excluirServico" onclick="javascript:return confirm('Deletar este Serviço ?')">
						<img src="imagens/excluir_20px.png" alt="excluir serviço"
							title="excluir orcamento" border="0" /></a></td>
							
						<c:choose>
							<c:when test="${servico.ativo}">
								<td width="30"><img src="imagens/van_servico_30_30.png" title="Servico nao entregue" alt="Servico nao entregue" border="0" /></td>
							</c:when>
							<c:otherwise>
								<td width="30"><img src="imagens/van_servico_entregue_30_30.png" title="Servico Entregue" alt="Servico Entregue" border="0" /></td>
							</c:otherwise>
						</c:choose>						
						
						<td width="60">
						<a	href="ProcessarAcaoServico?acao=lerServico&idServico=${servico.idServico}" title="visualizar">
						Cod. ${servico.idServico}</a></td>
						
						<td width="100"><b>Cod. ormt.  ${servico.orcamento.idOrcamento}</b></td>	
						
						<td width="100">Vcl. ${servico.veiculo.placa}</td>					
						
						<td width="200" title="cliente: ${servico.orcamento.cliente.nome}">						 
						<c:out value="${servico.orcamento.cliente.nome}"/></td>
					
						<td width="200" title="origem: ${servico.orcamento.detalheOrigem}">
						<c:set var="origem"	value="${servico.orcamento.detalheOrigem}"/> 
						<c:out value="${fn:substring(origem,0,100)}"/>...</td>
						
						<td width="200" title="destino: ${servico.orcamento.detalheDestino}">
						<c:set var="destino" value="${servico.orcamento.detalheDestino}"/> 
						<c:out value="${fn:substring(destino,0,100)}"/>...</td>
						
						<td width="100" title="valor: ${servico.valor}">						
						<c:out value="R$ ${servico.valor}"/></td>					

						<td width="100" align="right" title="data prevista da entrega">						
						<c:out value="${servico.infoDataPrevEntrega}"></c:out></td>
						
						<td width="100" align="right" title="data entrega">						
						<c:out value="${servico.infoDataEntrega}"></c:out></td>
					</tr>
				</table>
				<hr>
			</c:forEach></td>
		</tr>
	</table>
</c:if>
<br /><jsp:include page="includeRodapeSI.jspf"></jsp:include>
</body>
</html>
