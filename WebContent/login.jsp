<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="br.com.cesaretransportes.modelo.Cliente" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<jsp:include page="layout/library.jspf" />

<title>Cesare Transporte</title>
</head>

<body bgcolor="#E8E8E8">

<div id="wrap">
 
	<div id="main">
 	<jsp:include page="layout/header.jspf"/>
 	<table width="100%" bgcolor="#000000">	
		<tr>
			<td>		
			<ul id="menu">
				<li><a href="index.jsp">Empresa</a></li>
				<li><a href="cadastrar-orcamento.jsp">Or&ccedil;amento</a></li>
				<li><a href="cadastrar-contato.jsp">Contato</a></li>
				<!-- <li><a href="download.jsp">Download</a></li> -->
				<li><a href="login.jsp">Login</a></li>							
			</ul>		
			</td>
		</tr>
	</table>
	
	<table width="800" align="center">
		<tr>
			<td style="padding-top: 10px"><span class="tituloPagina">Login</span></td>
		</tr>	
	</table>
	
	<jsp:useBean id="data" class="java.util.Date"/>
	<h3><fmt:formatDate value="${data}" dateStyle="full" timeZone="America/Sao_Paulo"/></h3>
	
	<table width="800px" align="center">
		<tr>
			<td>
				<div id="cardIndex">				
				
				<h3> &Aacute;rea Restrita. Somente pessoal autorizado.</h3>
				
				<form id="formLogin" action="login" name="acesso" method="post" onsubmit="return validaForm()">
					<div align="center">
						<p><input id="inputUsuario" type="text" name="usuario" class="input70" size="50" value="${usuario}"/></p>
						<p>					
						<input id="passwordChecker" type="text" value="SENHA" class="input70"/>
						<input id="inputSenha" type="password" name="senha" class="input70" />
						</p>
						<p>
						<input type="submit" value="Login" class="button" />
						</p>
					</div>
				</form>		
				
				<table class="faixaLaranja">
					<tr>
						<td align="right">
						<div align="center">
						<p>
						<span id="textoRecuperarSenha" class="legenda ponteiro">Esqueceu sua senha? Clique aqui.</span>
						</p>				
						</div>						
						</td>
					</tr>						
				</table>
				
				</div>
			</td>
		</tr>	
	</table> 	
	</div> 
</div>
<br/> 
<div id="footer">
<jsp:include page="layout/footer.jspf"/>
</div>

</body>
</html>