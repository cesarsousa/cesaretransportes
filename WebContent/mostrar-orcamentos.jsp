<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page import="java.util.List"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="icon" type="image/gif" href="imagens/animated_favicon1.gif" />
<link rel="stylesheet" type="text/css" href="estilo.css" />

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
<title>Cesare Transportes - Or&ccedil;amentos</title>
</head>

<body bgcolor="#cccccc">
<div id="wrap">
<div id="main">

<jsp:useBean id="data" class="java.util.Date" />
<h2><fmt:formatDate value="${data}" dateStyle="full" /> : logado como ${empresa.nome} - ${empresa.email}</h2>

<jsp:include page="includeCabecalhoComLinks.jspf"></jsp:include>

<h3>${mensagem}</h3>

<table width="100%">
	<tr>
		<td align="left" width="50%">
		<p class="estilo"><fmt:formatDate value="${data}" dateStyle="full" />.</p>
		<p>
		<label class="label1">${tipoOrcamento}</label>
		</p>
		</td>
		
		<td align="right" width="50%" class="laranjado" bgcolor="#ffffff">
		<label>Filtar busca de or&ccedil;amentos por:</label>
		<ul class="listaSimples">
		<li>
			<form action="mostrarListaDosOrcamentos" method="get">
			<input type="radio" name="opcao" value="naolido" checked="checked"><label>N&atilde;o Lidos</label>
			<input type="radio" name="opcao" value="lido"><label>Lidos</label>
			<input type="radio" name="opcao" value="excluido"><label>Exclu&iacute;dos</label>
			<input type="radio" name="opcao" value="naoexcluido"><label>N&atilde;o Exclu&iacute;dos</label>
			<input type="submit" value="Pesquisar" class="button" />
			</form>
		</li>
		</ul>
		</td>		
	</tr>
	
</table>

<div class="espacador"></div>

<c:if test="${empty listaDeOrcamentos}">
	<h3>Voce não possui nenhum or&ccedil;amento no momento</h3>
</c:if>

<c:if test="${not empty listaDeOrcamentos}">
	<table class="laranjado fontPequena" width="100%" bgcolor="#ffffff"	cellpadding="10">
		<tr>
			<td><c:forEach var="orcamento" items="${listaDeOrcamentos}">
				<table border="0" width="100%">
					
					<c:if test="${orcamento.excluido}">
						<c:set var="cssOrcamentoExcluido" value="tdOrcamentoExcluido"/>
					</c:if>
					<c:if test="${not orcamento.excluido}">
						<c:set var="cssOrcamentoExcluido" value=""/>
					</c:if>
					
					<tr class="<c:out value="${cssOrcamentoExcluido}"/>">
						<td width="30">
						<c:choose>
							<c:when test="${orcamento.excluido}">
							<img src="imagens/icone_excluir_opaco.png" alt="excluido da visualizacao" title="excluido da visualizacao" border="0" />
							</c:when>
							
							<c:otherwise>
							<a href="processarAcaoOrcamento?codigo=${orcamento.idOrcamento}&acao=excluirOrcamento" onclick="javascript:return confirm('Excluir este Orcamento?')">
							<img src="imagens/excluir_20px.png" alt="excluir orcamento" title="excluir orcamento" border="0" /></a>
							</c:otherwise>
						</c:choose>					
						</td>
						
						<c:choose>
							<c:when test="${not orcamento.excluido}">
								<td width="30">						
									<img src="imagens/icone-confirmar_opaco.png" alt="orcamento visivel" title="orcamento visivel" border="0" /></td>
							</c:when>
							<c:otherwise>
								<td width="30">
									<a href="processarAcaoOrcamento?codigo=${orcamento.idOrcamento}&acao=confirmarOrcamento" onclick="javascript:return confirm('confirmar este orçamento como não excluído?')">
									<img src="imagens/icone-confirmar.png" alt="confirmar orcamento para visualizacao"	title="confirmar orcamento para visualizacao" border="0" /></a></td>
							</c:otherwise>
						</c:choose>
							
						<td width="30">
						<img src="imagens/van_orcamento_30_30.png" alt="orçamento" title="orçamento" border="0" /></td>
						
						<c:choose>
							<c:when test="${orcamento.excluido}">
								<td width="50">Cod. ${orcamento.idOrcamento}</td>
							</c:when>							
							<c:otherwise>
								<td width="50">						
									<a href="processarAcaoOrcamento?acao=lerOrcamento&codigo=${orcamento.idOrcamento}" title="ler mensagem">
							 		Cod. ${orcamento.idOrcamento}</a></td>
						 </c:otherwise>
						</c:choose>
						
												
						
						<td width="100" title="cliente: ${orcamento.cliente.nome} (${orcamento.cliente.email})">
						<c:set var="nome"	value="${orcamento.cliente.nome}" /> 
						<c:out value="${fn:substring(nome,0,20)}"/>...</td>
					
						<td width="100" title="origem: ${orcamento.detalheOrigem}">
						<c:set var="origem"	value="${orcamento.detalheOrigem}"/> 
						<c:out value="${fn:substring(origem,0,20)}"/>...</td>
						
						<td width="100" title="destino: ${orcamento.detalheDestino}">
						<c:set var="destino" value="${orcamento.detalheDestino}"/> 
						<c:out value="${fn:substring(destino,0,20)}"/>...</td>
						
						<td width="100" title="peso: ${orcamento.peso}">
						<c:set var="peso" value="${orcamento.peso}" /> 
						<c:out value="${fn:substring(peso,0,10)}"/>...</td>
						
						<td width="100" title="dimensao: ${orcamento.dimensao}">
						<c:set var="dimensao" value="${orcamento.dimensao}" /> 
						<c:out value="${fn:substring(dimensao,0,10)}"/>...</td>
						
						<c:if test="${not orcamento.excluido}">
						<c:choose>
							<c:when test="${orcamento.orcamentoRespondido}">
								<td width="30">
								<img src="imagens/escrever_20_opaco.jpg" title="respondido" alt="respondido" border="0" />
								</td>
							</c:when>
							<c:otherwise>
								<td width="30">
								<img src="imagens/escrever_20.jpg" title="não respondido" alt="não respondido" border="0" />
								</td>
							</c:otherwise>
						</c:choose>			

						<c:choose>
							<c:when test="${orcamento.orcamentoLido}">
								<td width="30">
								<a href="processarAcaoOrcamento?acao=marcarOrcamentoComoNaoLido&codigo=${orcamento.idOrcamento }">
								<img src="imagens/email_lido_20.gif" title="lido. marcar como nao lido?" alt="orcamento lido" border="0" /></a></td>
							</c:when>
							<c:otherwise>
								<td width="30">
								<a href="processarAcaoOrcamento?acao=marcarOrcamentoComoLido&codigo=${orcamento.idOrcamento}">
								<img src="imagens/email_nao_lido_20.gif" title="nao lido. marcar como lido?" alt="orcamento nao lido" border="0" /></a></td>
							</c:otherwise>
						</c:choose>
						</c:if>
						<c:if test="${orcamento.excluido}">
						<td width="30"></td>
						<td width="30"></td>
						</c:if>

						<td width="100" align="right">
						<c:set var="dt"	value="${orcamento.infoDataCadastro}" /> 
						<c:out value="${fn:substring(dt,0,10)}"></c:out></td>
					</tr>
				</table>
				<hr>
			</c:forEach></td>
		</tr>
	</table>
</c:if>
</div>
</div>

<br/>
<div id="footer">	
	<jsp:include page="layout/footerSI.jspf"></jsp:include>
</div>
</body>
</html>
