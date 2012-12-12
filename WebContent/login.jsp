<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="br.com.cesaretransportes.modelo.Cliente" %>
<%
	Cliente cliente = (Cliente) request.getSession().getAttribute("cliente");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<jsp:include page="layout/library.jspf" />

<title>Cesare Transporte</title>
</head>

<body bgcolor="#E8E8E8">

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
<c:if test="${not empty cliente}">
<table width="100%" bgcolor="#000000">	
	<tr>
		<td>		
		<ul id="menu">			
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
</table>
</c:if>

<br/>
<table width="760" align="center">
	<tr>
		<td><span class="tituloPagina">Login</span></td>
	</tr>
</table>	

<jsp:useBean id="data" class="java.util.Date"/>
<h3><fmt:formatDate value="${data}" dateStyle="full" timeZone="America/Sao_Paulo"/></h3>


<table width="800px" align="center">
	<tr>
		<td>
			<div id="cardIndex">
			
			<c:choose>
				<c:when test="${not empty cliente}">
					<div align="left"><h3>Voc&ecirc; j&aacute; est&aacute; logado como ${cliente.nome} !</h3></div>
				</c:when>
				<c:when test="${not empty mensagem}">
					<div id="infoMensagem" align="center">
						<img id="fechar" class="direita" src="imagens/iconeok.png" alt="OK" title="OK" border="0" />					
						<pre><span class="mensagem">${mensagem}</span></pre>
					</div>
				</c:when>
				<c:when test="${not empty param.msgOrcamento}">
					<h3>${param.msgOrcamento}</h3>
				</c:when>				
			</c:choose>			
			
			<h3>Lembre-se! seu usuário é o email utilizado em seu cadastro.</h3>
			
			<form id="formLogin" action="login" name="acesso" method="post" onsubmit="return validaForm()">
				<div align="center">
					<p><input id="inputUsuario" type="text" name="usuario" class="input70" size="50" value="${usuario}"/></p>
					<p>					
					<input id="passwordChecker" type="text" value="SENHA" class="input70"/>
					<input id="inputSenha" type="password" name="senha" class="input70" />
					</p>
					<input type="submit" value="Login" class="button" />
				</div>
			</form>			
						
			<div align="center">
			<p><a href="recuperarSenha.jsp"><span class="legenda">Esqueceu sua senha? Clique aqui.</span></a></p>				
			</div>
			
			<table class="faixaLaranja">
				<tr>
					<td align="right">
						<span class="labelinformativo">Não sou um cliente ainda?</span>
					</td>
					<td align="left">
						<form action="cadastrar-cliente.jsp">
							<input type="submit" value="Cadastrar-se!" class="button"/>
						</form>
					</td>
				</tr>						
			</table>
			<br/>
			</div>
		</td>
	</tr>

</table>
<br/>
<jsp:include page="layout/footer.jspf"/>	
	
</body>
</html>