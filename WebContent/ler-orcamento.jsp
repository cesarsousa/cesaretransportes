<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="icon" type="image/gif" href="imagens/animated_favicon1.gif" />

<jsp:include page="layout/library.jspf" />

<style type="text/css">
font.email {
	color: #996600;
	font-family: Comic Sans MS, Geneva, sans-serif;
	font-size: +3;	
	font-weight: bold;
}

font.origem {
	color: #333333;
	font-family: Comic Sans MS, Geneva, sans-serif;
	font-size: +3;	
	font-weight: bold;
}
textarea {	
    border:1px solid #999999;
    width:100%;
    margin:5px 0;
    padding:3px;
}

.label{
	color: #000000;
	font-style: italic;
	font-weight: bold;
	font-family: Comic Sans MS, Geneva, sans-serif;
	font-size: small;
}

.erro{
	font-size: 13px;
	font-weight: bold;
	font-family: Verdana, Geneva, sans-serif;
	color: #FFFFFF;	
}

.colada{
	border-collapse: collapse;
}
</style>
<title>Cesare Transportes</title>
</head>

<body bgcolor="#dddddd">

<div id="wrap">

<div id="main">
<div id="pgLerOrcamentoPt1">
	<jsp:include page="includeCabecalhoLerEmail.jspf"></jsp:include>
	<h3>Detalhe do or&ccedil;amento</h3>
	
	
	
	<c:if test="${not empty orcamentoRespondido}">
		<div class="msgBorder msgSucesso">
			<label>Or&ccedil;amento respondido com sucesso</label>
		</div>
	</c:if>
	
	<c:if test="${not empty orcamentoAlterado}">
		<h4>Or&ccedil;amento alterado com sucesso.</h4>
	</c:if>
	
	<br />
	<table width="1000" align="center" border="0" class="colada" cellpadding="5">
		<tr>
			<td colspan="4">			
			<jsp:useBean id="data" class="java.util.Date"/>
			<h3><span class="esquerda"><fmt:formatDate value="${data}" dateStyle="full" timeZone="America/Sao_Paulo"/></span></h3>
			<span class="direita">Or&ccedil;amento enviado em <b>${orcamento.infoDataCadastro}</b></span>
			</td> 
		</tr>
		<tr>
		<td><hr class="destacar"></td>
		</tr>	
		<tr>		
			<c:choose>
				<c:when test="${not empty buscar}">
					<td width="25" class="esquerda"><a href="javascript:history.go(-1)">
						<img src="imagens/voltar_20px.png" alt="voltar" title="voltar" border="0" /></a></td>
					<td width="25" class="direita">
						<a href="processarAcaoOrcamento?codigo=${orcamento.idOrcamento}
											&acao=excluirOrcamento
											&buscar=true
											&paramBusca=${paramBusca}
											&opcao=${opcao}
											&filtro=${filtro}" onclick="javascript:return confirm('Deletar este Orcamento ?')">
						<img src="imagens/excluir_20px.png" alt="excluir orcamento" title="excluir orcamento" border="0" /></a></td>
				</c:when>
				<c:otherwise>				
					
						<td width="25" class="esquerda"><a href="mostrarListaDosOrcamentos?opcao=idOrcamento&tipo=orcamento">
						<img src="imagens/voltar_20px.png" alt="voltar" title="voltar" border="0" /></a></td>
					
						<td width="25" class="direita"><a href="processarAcaoOrcamento?codigo=${orcamento.idOrcamento}
												&acao=excluirOrcamento											
												&opcao=${opcao}
												&filtro=${filtro}" onclick="javascript:return confirm('Deletar este Orcamento ?')">
						<img src="imagens/excluir_20px.png" alt="excluir orcamento" title="excluir orcamento" border="0" /></a></td>
											
				</c:otherwise>
			</c:choose>
								
		</tr>
	</table>
	
	<table class="laranjado" width="1000" cellspacing="10" bgcolor="#CCCCCC" align="center">
		<tr>
			<td>
			<h1>Or&ccedil;amento de servi&ccedil;o n&ordm; ${orcamento.idOrcamento}.<br/>		
			Id do cliente ${orcamento.cliente.idCliente}.</h1>		
			De <font class="email">${orcamento.cliente.nome } (${orcamento.cliente.email })</font><br />
			Para <font class="origem">Cesare Transportes</font>
			</td>
		</tr>
	</table>
	<br />
	
	<table class="laranjado" width="1000" cellpadding="10" bgcolor="#FFFFFF" align="center">
		<tr><td>
			<p style="color: #f90"><b>Dados do Solicitante.</b></p>
			<p>Nome: ${ orcamento.cliente.nome }</p>
			<p>Email: ${ orcamento.cliente.email }</p>
			<p>Telefone: (${orcamento.cliente.telefone.ddd}) ${orcamento.cliente.telefone.numero}  ${orcamento.cliente.telefone.complemento}</p>
			<br/>
			<p style="color: #f90"><b>Dados do Or&ccedil;amento.</b></p>
			<p>Origem: ${ orcamento.detalheOrigem }</p>
			<p>Destino: ${ orcamento.detalheDestino }</p>
			<p>Peso: ${ orcamento.peso }</p>
			<p>Dimens&atilde;o: ${ orcamento.dimensao }</p>
			<br/>					
			<p style="color: #f90"><b>Mensagem</b></p>
			<p>${orcamento.mensagem }</p>
		</td></tr>
	</table>
	
	<c:set var="mensagem" value="${orcamento.mensagem}" />			
	<c:set var="res" value="Res:" />			
	<c:choose>
		<c:when test="${orcamento.orcamentoRespondido}">
			<h3>Or&ccedil;amento j&aacute; foi respondido</h3>
			<div class="espacador"></div>
		</c:when>
		<c:otherwise>
		<c:if test="${not orcamento.deletado }">
		<form action="enviarOrcamento" method="post">	
			<input type="hidden" name="acao" value="responderOrcamento">		
			<input type="hidden" name="codigo" value="${orcamento.idOrcamento}">						
			<div class="responderOrcamento">
			
				<table width="900" align="center"><tr><td>
					<h1>Enviar uma resposta do or&ccedil;amento para ${ orcamento.cliente.nome }</h1>
					
					<input type="text" readonly="readonly" disabled="disabled" value="Email : ${orcamento.cliente.email }" class="laranjado botaoInput">
					<i><textarea id="txAreaLerOrcamento" class="laranjado" name="resposta" rows="5"></textarea></i>
					<br/>
					<input id="btEnviarRepostaOrcamento" type="submit" value="Enviar Resposta" class="button">
				</td></tr></table>	
			</div>	
		</form>
		</c:if>				
		</c:otherwise>			
	</c:choose>
</div>
</div>
</div>				

<div id="aguardeEnviarResposta" >
	<div align="center">
		<table class="telaAguarde">
			<tr>
				<td>
					<h3>Sua resposta ao or&ccedil;amento esta sendo enviada. Por favor aguarde um instante!</h3>
					<div align="center">
						<img alt="Aguarde" src="imagens/cetrans_gif_aguarde.gif" />
					</div> <br /> <br />
				</td>
			</tr>
		</table>
	</div>
	<br/>
	<br/>
	<br/>
	<br/>
</div>

<div id="pgLerOrcamentoPt2" >
<div id="footer">
	<jsp:include page="layout/footerSI.jspf"></jsp:include>
</div>
</div>
</body>
</html>
