<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn" %>
<%@page import="java.util.List"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="icon" type="image/gif" href="imagens/animated_favicon1.gif" />
<link rel="stylesheet" type="text/css" href="estilo.css" />

<style type="text/css">
p {
	color: #333;
	font-weight: bold;
}
p.estilo{
	color: #FF6600
}

.erro{
	font-size: 13px;
	font-weight: bold;
	font-family: Verdana, Geneva, sans-serif;
	color: #FFFFFF;	
}

.campoInput {
	border: 1px solid #F90;
}
.collapse {
	border-collapse: collapse;
}
.label{
	color: #000000;
	font-style: italic;
	font-family: Comic Sans MS, Geneva, sans-serif;
	font-size: small;
}
.label2{
	color: #828282;
	font-style: italic;
	font-weight: bold;
	font-family: Comic Sans MS, Geneva, sans-serif;
	font-size: medium;
}
</style>
<title>Cesare Transportes - Clientes</title>
</head>

<body bgcolor="#cccccc">

<div id="wrap">
<div id="main">
<jsp:useBean id="data" class="java.util.Date" />
<h2><fmt:formatDate value="${data}" dateStyle="full" /> : logado como ${empresa.nome} - ${empresa.email}</h2>

<jsp:include page="includeCabecalhoComLinks.jspf"></jsp:include>

<h3>${mensagem}</h3>
<h3>Clientes usu&aacute;rios do site</h3>

	<table width="100%">
		<tr>
			<td align="left">
				<p class="estilo"><fmt:formatDate value="${data}" dateStyle="full" />.</p>
			</td>
		</tr>
	</table>
	
	<c:if test="${deletarAdm}">
	<table width="100%" bgcolor="#B22222" cellpadding="5">
		<tr>
			<td>
			<div align="center">
			<label class="erro">Deletar o Administrador n&atilde;o &eacute; uma op&ccedil;&atilde;o v&aacute;lida.</label>
			</div>
		</tr>
	</table>
	<br/>
	</c:if>
	
	<c:if test="${not empty infoCliente}">
		<h3>${infoCliente}</h3>
	</c:if>
	
	<c:if test="${not empty erroBusca}">
		<table width="100%" bgcolor="#B22222" cellpadding="5">
		<tr>
			<td>
			<div align="center">
			<label class="erro">${erroBusca}</label>
			</div>
		</tr>		
	</table>
	<br/>
	</c:if>
		
	<c:if test="${not empty busca}">
		<h4><c:out value="${numeroOcorrencias}"/> ocorr&ecirc;ncias para a busca [<c:out value="${busca}"/>]</h4>
	</c:if>
	
	<table width="100%" class="collapse" cellpadding="5" width="1200" align="center" >
		<tr>
			<td>
				<table>
					<tr>
					<td>
						<form action="ClienteAcaoServlet" method="post">
						<input type="hidden" name="acao" value="7"/>
						<input type="submit" value="Clientes Ativos" class="button"/>
						</form>
					</td>
					
					<td>
						<form action="ClienteAcaoServlet" method="post">
						<input type="hidden" name="acao" value="8"/>
						<input type="submit" value="Clientes Inativos" class="button"/>
						</form>
					</td>
					</tr>
				</table>		

			</td>
			<td align="right">
				<table>
					<tr>
					<td align="right">
						<form action="ClienteAcaoServlet" method="post">
							<input type="hidden" name="acao" value="4"/>
							<label class="label">Filtro para busca:</label>
							<input type="radio" name="filtro" value="nome" checked="checked"/><label class="label">nome</label>
							<input type="radio" name="filtro" value="email"/><label class="label">email</label>
							<input type="radio" name="filtro" value="id"/><label class="label">id</label>	      			
			      			<!-- <input type="radio" name="filtro" value="documento" /><label class="label">documento</label> -->
							<input type="text" name="parametro" class="input70" size="50"/>							
							<input type="submit" value="BUSCAR" class="button"/>
						</form>
					</td>
					</tr>
				</table>
			
			
				
			</td>					
		</tr>
	</table>
					
	<div align="center">
	<table width="100%" class="laranjado" cellpadding="5" width="1200" bgcolor="#ffffff">
	<tr>
		<td colspan="3" width="100"><label class="label2">A&ccedil;&otilde;es</label></td>
		<td width="50"><label class="label2">Id</label></td>
		<td width="130"><label class="label2">Situa&ccedil;&atilde;o Atual</label></td>
		<td width="200"><label class="label2">Nome</label></td>
		<td width="130"><label class="label2">Telefone</label></td>
		<td width="200"><label class="label2">Email</label></td>			
		<td width="250"><label class="label2">Documento</label></td>	
		<td width="250"><label class="label2">Endere&ccedil;o</label></td>	
		<td width="100"><label class="label2">Data</label></td>
	</tr>
	
	<c:forEach items="${clientes}" var="cliente">
		<tr>
			
			<c:choose>
				<c:when test="${cliente.deletado}">
					<td width="30">
						<img src="imagens/icone_excluir_opaco.png" alt="cliente excluido"	title="cliente nao visivel" border="0" />
					</td>
				</c:when>
				<c:otherwise>
					<td width="30">
						<a href="ClienteAcaoServlet?id=${cliente.idCliente}&acao=2" onclick="javascript:return confirm('Excluir este cliente desta visualização?')">
						<img src="imagens/excluir_20px.png" alt="excluir cliente desta vicualizacao"	title="excluir cliente desta visualizacao" border="0" /></a>
					</td>
				</c:otherwise>
				
			</c:choose>		
			
			<c:choose>
				<c:when test="${cliente.situacaoAtual}">
					<td width="40">						
						<img src="imagens/icone-confirmar_opaco.png" alt="cliente salvo" title="cliente visivel" border="0" /></td>
				</c:when>
				<c:otherwise>
					<td width="40">
						<a href="ClienteAcaoServlet?id=${cliente.idCliente}&acao=3">
						<img src="imagens/icone-confirmar.png" alt="confirmar cliente"	title="confirmar cliente para visualizacao" border="0" /></a></td>
				</c:otherwise>
			</c:choose>		
			
			<c:choose>
				<c:when test="${cliente.temOrcamentos and cliente.ativo}">
					<td width="30">
						<a href="ClienteAcaoServlet?idCliente=${cliente.idCliente}&acao=5">
						<img src="imagens/icone_visualizar.png" alt="visualizar orçamentos"	title="visualizar orçamentos" border="0" /></a></td>
				</c:when>
				<c:otherwise>
					<td width="30">
						<img src="imagens/icone_visualizar_opaco.png" alt="sem orçamentos"	title="sem orçamentos" border="0" /></td>
				</c:otherwise>
			</c:choose>			
			
			<td width="50" class="label" bgcolor="#E8E8E8">${cliente.idCliente}</td>
			
			<c:choose>
				<c:when test="${cliente.situacaoAtual}">
					<td width="130" class="label" bgcolor="#6B8E23">Us&uacute;ario <%-- ${cliente.situacaoCliente} --%></td>
				</c:when>
				<c:otherwise>
					<td width="130" class="label" bgcolor="#CD5C5C">Us&uacute;ario <%-- ${cliente.situacaoCliente} --%></td>
				</c:otherwise>
			</c:choose>			
			<td width="200" class="label" bgcolor="#E8E8E8">${cliente.nome}</td>
			<td width="130" class="label" bgcolor="#CFCFCF">(${cliente.telefone.ddd}) ${cliente.telefone.numero} / ${cliente.telefone.complemento}</td>
			<td width="200" class="label" bgcolor="#CFCFCF">${cliente.email}</td>
			<td width="250" class="label" bgcolor="#E8E8E8">${cliente.tipoDoDocumento} - ${cliente.numeroDoDocumentoFormatado}</td>
			<%-- <td width="250" class="label" bgcolor="#E8E8E8">${cliente.endereco.localizacao}, ${cliente.endereco.cidade} - ${cliente.endereco.estado}</td> --%>
			<td width="250" class="label" bgcolor="#E8E8E8">NA - na</td>				
			<td width="100" class="label" bgcolor="#E8E8E8">${cliente.dataCadastroFomatada}</td>			
		</tr>
		</c:forEach>
		</table>
		
		<c:if test="${mostrarOrcamentos}">
		<table width="100%" class="laranjado"  width="1200" bgcolor="#ffffff">			
			<tr>
				<td colspan="10">
					<div align="center"><h3>Lista de or&ccedil;amentos</h3></div>				
				</td>
			</tr>
			<tr>
				<td width="50"><label class="label2">C&oacute;digo</label></td>
				<td width="400"><label class="label2">Origem</label></td>
				<td width="400"><label class="label2">Destino</label></td>
				<td width="100"><label class="label2">peso</label></td>
				<td width="100"><label class="label2">dimens&atilde;o</label></td>				
				<td width="150"><label class="label2">data cadastro</label></td>							
			</tr>
			
			<c:set var="numero" value="${1}"/>
			<c:forEach items="${clienteOrcamentos}" var="orcamento">			
			<c:choose>
				<c:when test="${numero%2==0}">
					<c:set var="corCelula" value="#B5B5B5"/>
				</c:when>
				<c:otherwise>
					<c:set var="corCelula" value="#E8E8E8"/>
				</c:otherwise>
			</c:choose>		
				<tr>
					<td bgcolor="${corCelula}" width="50"><label class="label">${orcamento.idOrcamento}</label></td>
					<td bgcolor="${corCelula}" width="400"><label class="label">${orcamento.detalheOrigem}</label></td>
					<td bgcolor="${corCelula}" width="400"><label class="label">${orcamento.detalheDestino}</label></td>
					<td bgcolor="${corCelula}" width="100"><label class="label">${orcamento.peso}</label></td>					
					<td bgcolor="${corCelula}" width="100"><label class="label">${orcamento.dimensao}</label></td>
					<td bgcolor="${corCelula}" width="150"><label class="label">${orcamento.infoDataCadastro}</label></td>
				</tr>
				<tr>
					<td bgcolor="${corCelula}" colspan="10"><label class="label">${orcamento.mensagem}</label></td>
				</tr>
				<c:set var="numero" value="${numero + 1}"/>
			</c:forEach>	
	</table>
	</c:if>
	</div>
</div>
</div>

<br/>
<div id="footer">
	<jsp:include page="layout/footerSI.jspf"></jsp:include>	
</div>

</body>
</html>
