<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="icon" type="image/gif" href="imagens/animated_favicon1.gif" />
<link rel="stylesheet" type="text/css" href="estilo.css"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<style type="text/css">
.mensagem {
	color: #FF9900;
	font-weight: bold;
	font-style: italic;
	font-size:medium;
}
</style>
<title>Cesare Transporte</title>
</head>

<body bgcolor="#ffffff" onload="fecharJanela();">
<table class="laranjado" width="760" border="0" align="center"  bgcolor="#FF9900">
	<tr>
		<td valign="top">
			<img src="imagens/Cesari transportes cabecalho.jpg" width="760" height="200" /></td>
	</tr>
	<tr>
		<td align="center" bgcolor="#fff">
		<ul id="menu">
							<li><a href="index.jsp">Empresa</a></li>
							<li><a href="cadastrar-orcamento.jsp">Or&ccedil;amento</a></li>
							<li><a href="cadastrar-contato.jsp">Contato</a></li>
							<li><a href="login.jsp">Login</a></li>
							<c:if test="${not empty cliente}">
								<li><a href="javascript:document.formPerfil.submit();">Perfil</a></li>
								<form action="PerfilServlet" name="formPerfil" id="formPerfil">
									<input type="hidden" name="idCliente" value="${cliente.idCliente}"/>
								</form>
								<li><a href="javascript:document.formOrcamentos.submit();">Meus Or&ccedil;amentos</a></li>
								<form action="ClienteOpcaoConsultaServlet" name="formOrcamentos" id="formOrcamentos">
									<input type="hidden" name="idCliente" value="${cliente.idCliente}"/>
									<input type="hidden" name="opcao" value="5"/>
								</form>
								<li><a href="javascript:document.formServicos.submit();">Meus Servi&ccedil;os</a></li>
								<form action="ClienteOpcaoConsultaServlet" name="formServicos" id="formServicos">
									<input type="hidden" name="idCliente" value="${cliente.idCliente}"/>
									<input type="hidden" name="opcao" value="6"/>
								</form>
								<li><a href="LogoutServlet">sair</a></li>				
							</c:if>					
						</ul>
		</td>
	</tr>
	<tr>
		<td background="imagens/fundo_index.jpg">
		<p>&nbsp;</p>
		<p>&nbsp;</p>
		<p>&nbsp;</p>		
		<p>&nbsp;</p>
		<div align="center">
		<pre><span class="mensagem">${mensagem}</span></pre>
		</div>
		<p>&nbsp;</p>
		<p>&nbsp;</p>
		<p>&nbsp;</p>
		<p>&nbsp;</p>
		<p>&nbsp;</p>
		</td>
	</tr>
	<tr>
		<td>
		<div><img src="imagens/cesare rodape.jpg" width="760" height="30" /></div>
		</td>
	</tr>
</table>
<div align="center" class="designer">Designed by Cesar De Sousa Junior</div>
</body>
</html>
