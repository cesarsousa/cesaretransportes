<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
<link rel="icon" type="image/gif" href="imagens/animated_favicon1.gif" />
<link rel="stylesheet" type="text/css" href="estilo.css">
<style type="text/css">
ul li {
	text-align: left;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cesare Transportes - Gerenciamento</title>
</head>

<body bgcolor="#cccccc">

<div id="wrap">
<div id="main">

<jsp:useBean id="data" class="java.util.Date" />

<h2><fmt:formatDate value="${data}" dateStyle="full" /> : logado como ${empresa.nome} - ${empresa.email}</h2>

<jsp:include page="includeCabecalhoComLinks.jspf"></jsp:include>
<br/>

<c:if test="${not empty mensagem}">
	<h3>${mensagem}</h3>
</c:if>

</div>
</div>

<div id="footer">
	<jsp:include page="layout/footerSI.jspf"></jsp:include>
</div>
</body>
</html>