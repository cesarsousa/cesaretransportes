<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<jsp:include page="layout/library.jspf" />


<title>Cesare Transporte - Recuperar Senha</title>
</head>

<body bgcolor="#E8E8E8" onload="setFocus();"  onunload="fecharJanela();">

<jsp:include page="layout/header.jspf"/>
<table width="100%" bgcolor="#000000">	
	<tr>
		<td>		
		<ul id="menu">
			<li><a href="index.jsp">Empresa</a></li>
			<li><a href="cadastrar-orcamento.jsp">Or&ccedil;amento</a></li>
			<li><a href="cadastrar-contato.jsp">Contato</a></li>
			<li><a href="download.jsp">Download</a></li>
			<li><a href="login.jsp">Login</a></li>							
		</ul>		
		</td>
	</tr>
</table>
<br/>

<table width="760" align="center">
	<tr>
		<td><span class="tituloPagina"> Recuperação de Senha</span></td>
	</tr>
</table>


<jsp:useBean id="data" class="java.util.Date"/>
<h3><fmt:formatDate value="${data}" dateStyle="full" timeZone="America/Sao_Paulo"/></h3>

<div align="center">
<div id="cardIndex">
<table width="760" align="center">	
	
	<tr>
		<td>		
		
		<form action="recuperarSenha" method="post">
		<div align="center">
		
		<c:if test="${not empty mensagem}">
			<div id="infoMensagem" align="center">
				<img id="fechar" class="direita" src="imagens/iconeok.png" alt="OK" title="OK" border="0" />					
				<pre><span class="mensagem">${mensagem}</span></pre>
			</div>
		</c:if>
				
		<p>		
		<label class="legenda">
		<br/>
		Para efetuar a recupera&ccedil;&atilde;o de seu usu&aacute;rio e senha &eacute; 
		obrigat&oacute;rio o prenchimento do e-mail utilizado em seu cadastro !</label>
		<br/><br/><br/>
		<input id="campoEmailRecSenha" type="text" name="email" class="input100 transparente"/>
		</p>		
		
		<p>
		<input id="btSubmit" class="button" type="submit" value="Recuperar Senha" onclick="abrirJanela();"></input>
		</p>
		</div>
		</form>		
		</td>
	</tr>	
</table>
</div>
</div>
</body>
<jsp:include page="layout/footer.jspf" />
</html>
