<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="estilo.css" />
<title>Cesare Transportes - Email</title>
</head>
<body>
<% 
String nome = request.getParameter("nome");
String email = request.getParameter("email");
%>
<div align="center">
<table width="680" bgcolor="#CCCCCC" cellpadding="10">
	<tr>
		<td bgcolor="#FF6600" align="center">
			<img src="imagens/header_ tela_alerta.jpg">
		</td>
	</tr>
	<tr>
		<td>
			<br />
			<% out.println(nome); %>		
			<br />
			<% out.println(email); %>
			<br />			
		</td>
	</tr>
</table>
</div>
</body>
</html>