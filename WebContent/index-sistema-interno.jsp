<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="java.util.List"%>
<html>
<head>
<link rel="icon" type="image/gif" href="imagens/animated_favicon1.gif" />
<link rel="stylesheet" type="text/css" href="estilo.css">
<link rel="stylesheet" type="text/css" href="estilo2.css">
<style type="text/css">
ul li {
	text-align: left;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cesare Transportes - Gerenciamento</title>
</head>

<body bgcolor="#cccccc">
<jsp:include page="includeCabecalhoComLinks.jspf"></jsp:include>
<br/>

<c:if test="${not empty mensagem}">
	<h3>${mensagem}</h3>
</c:if>
<p>.</p>
<p>.</p>
<div id="footerIndex">
	<label class="sizeMedium">&copy; 2011 Cesare Transportes - Todos os Direitos Reservados</label>	
</div>
</body>
</html>