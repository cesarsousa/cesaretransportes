<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="icon" type="image/gif" href="imagens/animated_favicon1.gif" />
<link rel="stylesheet" type="text/css" href="estilo.css"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script language="javascript">
function validarFormulario(){
	d = document.formulario;
	if (d.senha1.value == ""){
		alert("< O campo 'Digite sua senha' deve ser preenchido! >");
		d.senha1.focus();
		return false;
	}
	if (d.senha2.value == ""){
		alert("< O campo 'Redigite sua senha' deve ser preenchido! >");
		d.senha2.focus();
		return false;
	}
	
	abrirJanela();
}

function abrirJanela(){
	var esquerda = (screen.width - 550)/2;
	var topo = (screen.height - 250)/2;
	alertWindow = window.open("telaAguarde.html","bookpixWin","width=550, height=250, top="+topo+", left="+esquerda);	
}

function fecharJanela(){
	alertWindow.close();
}

function getFocus(){
	document.getElementById("senha1").focus();
}
</script>
<style type="text/css">
.mensagem {
	color: #FF9900;
	font-weight: bold;
	font-style: italic;
	font-size:medium;
}

.label1 {
	font-size: 14px;
	font-weight: bold;
	font-family: Verdana, Geneva, sans-serif;
	color: #333;
	padding: 10px;
}

.label2 {
	font-size: 12px;
	font-style: italic;
	font-family: Arial, Helvetica, sans-serif;
	color: #333;
	padding: 10px;
}

.erro {
	font-size: 15px;
	font-style: italic;
	font-weight: bold;
	font-family: Arial, Helvetica, sans-serif;
	color: red;
	padding: 10px;
}
</style>
<title>Cesare Transporte</title>
</head>

<body bgcolor="#ffffff" onunload="fecharJanela();" onload="getFocus();">
<table class="laranjado" width="760" border="0" align="center"  bgcolor="#FF9900">
	<tr>
		<td valign="top"><img
			src="imagens/Cesari transportes cabecalho.jpg" width="760"
			height="200" /></td>
	</tr>
	
	<tr>
		<td background="imagens/fundo_index.jpg">
		
		<fieldset><legend class="legenda">Reativa&ccedil;&atilde;o de Conta</legend>
		
		<h3>Ol&aacute; ${cliente.nome}</h3>
		<label class="label2">Consta em nosso sistema que sua conta esta desativada.</label><br/><br/>
		<label class="label1">Seus Dados Cadastrais s&atilde;o:</label>
		<ul>
			<li><label class="label2">Nome ou empresa: ${cliente.nome}</label></li>
			<li><label class="label2">Email: ${cliente.email}</label></li>
			<li><label class="label2">Documento: ${cliente.tipoDoDocumento} - ${cliente.numeroDoDocumentoFormatado}</label></li>
			<li><label class="label2">Telefone: (${cliente.telefone.ddd}) - ${cliente.telefone.numero}</label></li>
			<li><label class="label2">Endere&ccedil;o: ${cliente.endereco.localizacao}, ${cliente.endereco.cidade} - ${cliente.endereco.estado}</label></li>
		</ul>
		<label class="label1">Gostaria de reativar sua conta?</label><br/><br/>
		
		<form action="RecadastrarClienteServlet" method="post" name="formulario" onsubmit="return validarFormulario();">
		<table bgcolor="#FF9900" width="100%" cellpadding="5">
			<tr>
				<td colspan="2" align="center"><label class="label1">Sim. Gostaria de reativar minha conta!</label></td>
			</tr>
			<c:if test="${erroSenha}">
				<div align="center"><label class="erro">Senhas n&atilde;o conferem</label><br/><br/></div>
			</c:if>
			<tr>
				<td align="right"><label class="label2">Digite sua nova senha:</label></td>
				<td align="left"><input type="password" name="senha1" id="senha1"/></td>
			</tr>
			<tr>
				<td align="right"><label class="label2">Redigite sua nova senha:</label></td>
				<td align="left"><input type="password" name="senha2" id="senha2"/></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
				<input type="hidden" name="nome" value="${cliente.nome}"/>
				<input type="hidden" name="email" value="${cliente.email}"/>
				<input type="submit" value="Reativar Conta"/></td>
			</tr>			
		</table>
		</form>
		<br />
		<form action="index.jsp">
		<table bgcolor="#CFCFCF" width="100%" cellpadding="5">
			<tr>
				<td align="center">
				<label class="label1">Não quero reativar minha conta!</label>
				<input type="submit" value="Cancelar"/></td>
			</tr>			
		</table>
		</form>
		
		</fieldset>
		
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
