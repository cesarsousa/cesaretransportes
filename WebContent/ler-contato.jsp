<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page import="br.com.cesaretransportes.modelo.Contato"%>
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
</style>
<title>Cesare Transportes - Email</title>
</head>

<body onunload="fecharJanela();">

<jsp:include page="includeCabecalhoLerEmail.jspf"></jsp:include>

<br />
<table width="1000" align="center">
	<tr>
	<c:choose>
		<c:when test="${not empty buscar}">
			<td width="150"><a href="javascript:history.go(-1)">
				<img src="imagens/voltar_20_20.jpg" alt="voltar" title="voltar" border="0" /></a></td>
			<td width="150"><a href="processarAcao?codigo=${contato.codigo }&acao=excluirEmail&buscar=true&paramBusca=${paramBusca}&opcao=${opcao}&filtro=${filtro}" onclick="javascript:return confirm('Deletar este Email ?')">
				<img src="imagens/excluir_20_px.jpg" alt="excluir email" title="excluir email" border="0" /></a></td>
		</c:when>
		<c:otherwise>
			<c:choose>
			<c:when test="${contato.emailSalvo}">
				<td width="150"><a href="mostrarListaDosContatos?opcao=codigo&emailsSalvos=sim">
					<img src="imagens/voltar_20_20.jpg" alt="caixa de entrada" title="caixa de entrada" border="0" /></a></td>
				<td width="150"><a href="processarAcao?codigo=${contato.codigo }&acao=excluirEmail" onclick="javascript:return confirm('Deletar este Email ?')">
					<img src="imagens/excluir_20_px.jpg" alt="excluir email" title="excluir email" border="0" /></a></td>
			</c:when>
			<c:otherwise>
				<td width="150"><a href="mostrarListaDosContatos?opcao=codigo&emailsSalvos=nao">
					<img src="imagens/voltar_20_20.jpg" alt="caixa de entrada" title="caixa de entrada" border="0" /></a></td>
				<td width="150"><a href="processarAcao?codigo=${contato.codigo }&acao=excluirEmail" onclick="javascript:return confirm('Deletar este Email ?')">
					<img src="imagens/excluir_20_px.jpg" alt="excluir email" title="excluir email" border="0" /></a></td>
				</c:otherwise>
			</c:choose>
		</c:otherwise>	
	</c:choose>			
		
				
		
		<td width="100%" align="right">enviado em <b>${contato.data }</b></td> 
	</tr>
</table>

<table class="laranjado" width="1000" cellspacing="10" bgcolor="#CCCCCC" align="center">
	<tr>
		<td>
		<h1>Contato de email  n&ordm; ${contato.codigo}.</h1>		
		De <font class="email">${contato.nome } (${contato.email })</font><br />
		Para <font class="origem">Cesare Transportes</font>
		</td>
	</tr>
</table>
<br />	
		<c:if test="${not empty emailRespondido}">
		<div align="center"><h3>${emailRespondido}</h3></div>
		</c:if>
		<table class="laranjado" width="1000" cellpadding="10" bgcolor="#FFFFFF" align="center"><tr><td>
		<pre>${ contato.mensagem }</pre>
		</td></tr></table>
		
		<c:set var="mensagem" value="${contato.mensagem}"></c:set>
		<c:set var="res" value="Res:"></c:set>
		<c:choose>
			<c:when test="${fn:startsWith(mensagem, res)}">
				<h3>Email ja foi respondido</h3>
			</c:when>
			<c:otherwise>
				<form action="enviarContato" method="post">		
				<input type="hidden" name="acao" value="responderEmail">		
				<input type="hidden" name="codigo" value="${contato.codigo}">
				<input type="hidden" name="nome" value="${contato.nome}">
				<input type="hidden" name="email" value="${contato.email}">
				<input type="hidden" name="data" value="${contato.data}">
				<input type="hidden" name="mensagem" value="${contato.mensagem}">
				<table width="1000" align="center"><tr><td>
				<div align="left">
					<input type="submit" value="Enviar Resposta" onclick="abrirJanela();">
					<i><textarea class="laranjado" name="resposta" rows="10">Digite a resposta</textarea></i>	
				</div>
				</td></tr></table>			
				</form>
			</c:otherwise>
		</c:choose>		
<br />
<jsp:include page="includeRodapeSI.jspf"></jsp:include>
</body>
</html>
