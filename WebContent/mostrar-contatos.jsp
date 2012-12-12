<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn" %>
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
p.estilo{
	color: #FF6600
}
</style>
<title>Cesare Transportes - Mail Box</title>
</head>

<body bgcolor="#ffffff">

<jsp:include page="includeCabecalhoComLinks.jspf"></jsp:include>

<h3>${mensagem}</h3>

<table width="100%">
<tr>
<td align="left">
<jsp:useBean id="data" class="java.util.Date"/>			 
<p class="estilo"><fmt:formatDate value="${data}" dateStyle="full"/>.</p>
</td>
<td align="right">
<a href="processarAcao?codigo=0&acao=refreshEmail">
<img src="imagens/refresh_40_40.jpg" alt="atualizar" title="atualizar" border="0"/></a><br /><br />

</td>
</tr>
</table>

<c:if test="${empty listaDeContatos}">
<h3>Voce não possui nenhum email no momento</h3>
</c:if>

<c:if test="${not empty listaDeContatos}">
<table class="laranjado" width="100%" bgcolor="#ffffff" cellpadding="10">
	<tr>
		<td>
		<c:forEach var="contato" items="${listaDeContatos}">		
		<table border="0" width="100%">
			<tr>
				<td width="30"><a href="processarAcao?codigo=${contato.codigo}&acao=excluirEmail" onclick="javascript:return confirm('Deletar este Email ?')">
				<img src="imagens/excluir_20px.png" alt="excluir email" title="excluir email" border="0"/></a></td>				
				
				<c:if test="${not empty cxEntrada}">				
				<td width="30"><a href="processarAcao?codigo=${contato.codigo}&acao=salvarEmail">
				<img src="imagens/salvar_20.jpg" title="salvar email" alt="salvar email" border="0"/></a></td>
				</c:if>
				
				<td width="30"><a href="processarAcao
				?acao=responderEmail
				&codigo=${contato.codigo}
				&nome=${contato.nome}
				&email=${contato.email}
				&mensagem=${contato.mensagem}
				&data=${contato.data}">		
				<img src="imagens/escrever_20.jpg" title="responder email"	alt="responder email" border="0" /></a></td>
				
				<td width="100">Cod. ${contato.codigo }</td>
				
				<td width="200"><a href="processarAcao
				?acao=lerEmail
				&codigo=${contato.codigo}
				&nome=${contato.nome}
				&email=${contato.email}
				&mensagem=${contato.mensagem}
				&data=${contato.data}" title="ler mensagem">${contato.email}</a></td>					
				
				<c:choose>
				<c:when test="${contato.emailLido}">				
				<td width="30"><a href="processarAcao?codigo=${contato.codigo }&acao=marcarEmailComoNaoLido">
				<img src="imagens/email_lido_20.gif" title="lido. marcar como nao lido?" alt="email lido" border="0" /></a></td>
				</c:when>
				<c:otherwise>
				<td width="30"><a href="processarAcao?codigo=${contato.codigo }&acao=marcarEmailComoLido">
				<img src="imagens/email_nao_lido_20.gif" title="nao lido. marcar como lido?" alt="email nao lido" border="0" /></a></td>
				</c:otherwise>
				</c:choose>						
				
				<td><i>				
				<c:set var="msg" value="${contato.mensagem}"/>
				<a href="processarAcao
				?acao=lerEmail
				&codigo=${contato.codigo}
				&nome=${contato.nome}
				&email=${contato.email}
				&mensagem=${contato.mensagem}
				&data=${contato.data}" title="ler mensagem"><c:out value="${fn:substring(msg,0,100)}" /></a>												 
            	</i></td>
				
				<td width="150" align="right">
				<c:set var="dt" value="${contato.data}"/>
				<c:out value="${fn:substring(dt,0,10)}"></c:out> 
				</td>
			</tr>
		</table>
		<hr>
		</c:forEach>
		</td>
	</tr>
</table>
</c:if>
<br /><jsp:include page="includeRodapeSI.jspf"></jsp:include>
</body>
</html>
