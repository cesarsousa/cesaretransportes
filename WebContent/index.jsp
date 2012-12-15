<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="br.com.cesaretransportes.modelo.Cliente" %>
<%
	Cliente cliente = (Cliente) request.getSession().getAttribute("cliente");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="icon" type="image/gif" href="imagens/animated_favicon1.gif" />

<jsp:include page="layout/library.jspf" />

<title>Cesare Transportes </title>
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
			<!-- <li><a href="download.jsp">Download</a></li> -->
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

<table width="800px" align="center">
	<tr>
		<td>
		
		<br/>
		<table width="760" align="center">
			<tr>
				<td><span class="tituloPagina">A Empresa</span></td>
			</tr>
		</table>
		
		
		
		<jsp:useBean id="data" class="java.util.Date"/>
        <h3><fmt:formatDate value="${data}" dateStyle="full" timeZone="America/Sao_Paulo"/></h3>
		
		<!-- Se existe um cliente na seção add o acesso ao download na pg. -->
		<c:if test="${not empty cliente}">
		<fieldset><legend class="legenda">Cliente</legend>
			<div align="center">
			<h3>Ol&aacute; <c:out value="${cliente.nome}"></c:out></h3>
			<c:if test="${not empty mensagem}"><h3>${mensagem}</h3></c:if>
			</div>			
		</fieldset>
		<br />
		
		<c:if test="${cliente.cliente}">
			<fieldset><legend class="legenda">Download</legend>
				<div align="left">
				<a href="aplicativo/cetrans_app.zip"><img src="imagens/banner_download.png" alt="Download App" border="0"/></a>
				</div>			
			</fieldset>		
		</c:if>
		<c:if test="${not cliente.cliente}">
			<fieldset><legend class="legenda">Pendente confirma&ccedil;&atilde;o para realizar Download</legend>
				<div align="center">
					<img src="imagens/banner_download_app_pendente.png" alt="Download App" border="0"/>
				</div>			
			</fieldset>		
		</c:if>
		
		</c:if>
		
		<div id="cardIndex">			
			<div align="center"><h1><i>Cesare Transportes</i></h1></div>			
			<h2>Desde sua cria&ccedil;&atilde;o em 2003, a Cesare Transportes vem praticando, 
				moldando e  consolidando um conjunto de valores que sustenta ate hoje a ess&ecirc;ncia 
				da empresa e alavanca o comprimento de sua miss&atilde;o.<br/>
			<a class="saibamaislink" href="valoresCeTrans.jsp#topo" target="_blank">saiba mais...</a></h2>
			<table border="0">
				<tr>
					<td width="250">
					<div align="center"><h1><i>&Eacute;tica</i></h1></div>
					<h2>A &Eacute;tica &eacute; o  solo sobre o qual a Cesare 
					Transportes vem sendo constru&iacute;da desde sua cria&ccedil;&atilde;o...<br/>
					 <a class="saibamaislink" href="valoresCeTrans.jsp#etica" target="_blank">saiba mais...</a></h2>
					</td>
					
					<td width="250">
					<div align="center"><h1><i>Compromisso</i></h1></div>
					<h2>O desafio de ser uma empresa privada no Brasil exige de 
					todos nos o compromisso profissional e pessoal...<br/>
					<a class="saibamaislink" href="valoresCeTrans.jsp#desenvolvimento" target="_blank">saiba mais...</a></h2>
					</td>
								
					<td width="250">
					<div align="center"><h1><i>Excel&ecirc;ncia</i></h1></div>
					<h2>Perseguimos a excel&ecirc;ncia em tudo o que fazemos, no empenho 
					de cumprir a miss&atilde;o da empresa...<br/>
					<a class="saibamaislink" href="valoresCeTrans.jsp#excelencia" target="_blank">saiba mais...</a></h2>
					</td>
				</tr>
			</table>		
		</div>	
		
		
		
		<%-- <!-- se não existe um cliente na seção (usuário) direciona para login. -->
		<c:if test="${empty cliente}">
		<fieldset><legend class="legenda">Download</legend>
			<div align="left">
				<a href="redirectDownload.jsp"><img src="imagens/banner_download_app.png" alt="Download App" border="0"/></a>
			</div>			
		</fieldset>
		</c:if> --%>
			
		</td>
	</tr>
</table>
<br/>

<jsp:include page="layout/footer.jspf"/>

</body>
</html>
