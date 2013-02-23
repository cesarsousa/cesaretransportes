<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="icon" type="image/gif" href="imagens/animated_favicon1.gif" />

<jsp:include page="layout/library.jspf" />

<title>Cesare Transportes </title>
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
				<td style="padding-top: 10px"><span class="tituloPagina">A Empresa</span></td>
			</tr>
		</table>

		<table width="800px" align="center">
			<tr>
				<td>						
				
				<jsp:useBean id="data" class="java.util.Date"/>
		        <h3><fmt:formatDate value="${data}" dateStyle="full" timeZone="America/Sao_Paulo"/></h3>	
				
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
				</td>
			</tr>
		</table>
	</div> 
</div>
<br/><br/>
<div id="footer">
<jsp:include page="layout/footer.jspf" />
</div>

</body>
</html>
