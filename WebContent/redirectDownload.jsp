<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cesare Transportes - Redirecionando...</title>
</head>
<body>
<!-- ponte entre as paginas index e login, usada para configurar a mensagem ao usuario na pagina de login -->
<c:redirect url="login.jsp">
	<c:param name="msgOrcamento" value="Para efetuar o download é necessário fazer o login."></c:param>
</c:redirect>
</body>
</html>