<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page import="java.util.List"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="icon" type="image/gif" href="imagens/animated_favicon1.gif" />

<jsp:include page="layout/library.jspf" />

<title>Cesare Transportes</title>
</head>

<body bgcolor="#E8E8E8">

<div id="wrap">
<div id="main">

<jsp:useBean id="data" class="java.util.Date" />
<h2><fmt:formatDate value="${data}" dateStyle="full" /> : logado como ${empresa.nome} - ${empresa.email}</h2>

<jsp:include page="includeCabecalhoComLinks.jspf"></jsp:include>

<c:if test="${not empty erroConfiguracaoConta}">
	<div class="tableErro" style="padding: 30px;">								
	<div align="center">Verifique campos obrigat&oacute;rios com erros de preenchimento</div>		
	</div>				
</c:if>
<br/>

<h3>${mensagemConta}</h3>

<div>
<p class="estilo"><fmt:formatDate value="${data}" dateStyle="full" />.</p>
</div>

<div align="center" style="border: 0">

<div id="cardIndex" style="padding: 10px;">			
	<div align="center"><h3><i>Dados da Empresa</i></h3></div>			
	<h1>Dados de identifi&ccedil;&atilde;o da empresa</h1>
	
	<form action="ConfiguracaoDeContaServlet" method="post">
	
	<input type="hidden" name="idEmpresa" value="${empresa.idEmpresa}">
	<input type="hidden" name="idEndereco" value="${empresa.endereco.idEndereco}">	
	
	<c:if test="${not empty erroNomeConta or not empty erroCnpjConta}">		
		<div class="msgBorder msgErro">
			<c:if test="${not empty erroNomeConta}">${erroNomeConta}<br/></c:if>
			<c:if test="${not empty erroCnpjConta}">${erroCnpjConta}</c:if>
		</div>
		<br/>
	</c:if>
	
	<div class="campoInfo">
		<label class="info esquerda">Nome da Empresa</label>
		<input id="empresaNome" type="text" name="nome" value="${empresa.nome}" class="input50 direita">
	</div>
	
	<div class="campoInfo">
		<label class="info esquerda">CNPJ</label>
		<input id="empresaCnpj" type="text" name="cnpj" value="${empresa.cnpj}" maxlength="14" class="input50 direita">
	</div>
	
	<h1>Dados de localiza&ccedil;&atilde;o da empresa</h1>
	
	<c:if test="${not empty erroEnderecoConta or not empty erroCidadeConta}">		
		<div class="msgBorder msgErro">
			<c:if test="${not empty erroEnderecoConta}">${erroEnderecoConta}<br/></c:if>
			<c:if test="${not empty erroCidadeConta}">${erroCidadeConta}</c:if>
		</div>
		<br/>
	</c:if>
	
	<div class="campoInfo">
		<label class="info esquerda">Logradouro (rua, n&uacute;mero e bairro)</label>
		<input id="empresaEnderecoLocalizacao" type="text" name="enderecoLocalizacao" value="${empresa.endereco.localizacao}" class="input50 direita">
	</div>
	
	<div class="campoInfo">
		<label class="info esquerda">Cidade e estado</label>	
		<select id="empresaEnderecoEstado" name="enderecoEstado" class="select input10 direita">
			<option value="${empresa.endereco.estado}">${empresa.endereco.estado}</option>
		</select>	
		<input id="empresaEnderecoCidade" type="text" name="enderecoCidade" value="${empresa.endereco.cidade}" class="input40 direita">
	</div>
	
	<div class="campoInfo">
		<c:choose>
			<c:when test="${empresa.mostrarMapa}"><input id="empresaHabilitarMapa" type="checkbox" name="mostrarMapa" checked="checked"></c:when>
			<c:otherwise><input id="empresaHabilitarMapa" type="checkbox" name="mostrarMapa" ></c:otherwise>
		</c:choose>
		<label class="info">Habilitar visualiza&ccedil;&atilde;o com Google Maps</label>
	</div>
	
	
	<div class="campoInfo">
		<div class="msgBorder msgAlerta">
		Ao marcar a op&ccedil;&atilde;o de <b>visualiza&ccedil;&atilde;o com Google Maps</b> ser&aacute; habilitado para
		o usu&aacute;rio o mapa contendo o endere&ccedil;o da empresa. Para desabilitar esta funcionalidade mantenha
		a op&ccedil;&atilde;o desmarcada.
		</div>	
	</div>
	
	<c:if test="${not empty erroLocalizacaoConta}">		
		<div class="msgBorder msgErro">
			${erroLocalizacaoConta}
		</div>
		<br/>
	</c:if>
	
	<div id="empresaGeoLocalizacao" class="campoInfo2">
		<label class="info esquerda">Geo Localiza&ccedil;&atilde;o</label>
		<textarea id="empresaLocalizacao" name="localizacao" class="mensagemContato direita" rows="5">${empresa.localizacao}</textarea>
	</div>
	
	<h1>Dados da conta para envio de email em nome da empresa</h1>
	
	<div class="campoInfo3">
		<div class="msgBorder msgAlerta">
		&Eacute; necess&aacute;rio cadastrar um Gmail v&aacute;lido para enviar email aos usu&aacute;rio do site. Obs. a
		senha a ser informada deve ser a mesma senha cadastrada no endere&ccedil;o disponibilizado no campo <code>Gmail</code>.
		Caso n&atilde;o seja cadastrado um Gmail v&aacute;lido, n&atilde;o ser&aacute; poss&iacute;vel receber email de 
		or&ccedil;amento e contato para a empresa.
		</div>	
	</div>
	
	<c:if test="${not empty erroEmailConta or not empty erroSenhaConta}">		
		<div class="msgBorder msgErro">
			<c:if test="${not empty erroEmailConta}">${erroEmailConta}<br/></c:if>
			<c:if test="${not empty erroSenhaConta}">${erroSenhaConta}</c:if>
		</div>
		<br/>
	</c:if>
	
	<div class="campoInfo">
		<label class="info esquerda">Gmail</label>
		<input id="empresaNome" type="text" name="email" value="${empresa.email}" class="input50 direita">
	</div>
	
	<div class="campoInfo">
		<label class="info esquerda">Senha do Gmail</label>
		<input id="empresaCnpj" type="password" name="senha" value="${empresa.senha}" class="input50 direita">
	</div>
	
	<h1>Dados para contato com a empresa</h1>


	<div class="msgBorder msgInfo">
	Campo de preenchimento opcional. <b>Lembre-se que omitir este campo implica em um canal de contato a menos com o cliente.</b>
	</div>
	<br/>
	
	
	<div class="campoInfo">
		<label class="info esquerda">Msn Messenger</label>
		<input id="empresaNome" type="text" name="msn" value="${empresa.msn}" class="input50 direita">
	</div>
	
	<div class="msgBorder msgInfo">
	<b>Forma de preenchimento de telefone.</b><br/>
	Preenchimento opcional, ou no caso de prenchimento os campos <b>ddd</b> e <b>n&uacute;mero</b> s&atilde;o obrigat&oacute;rios. 
	</div>
	<br/>	
	
	<c:if test="${not empty erroTelefoneConta}">		
		<div class="msgBorder msgErro">
			<c:if test="${not empty erroTelefone1}">Erro no preencimento do telefone 1<br/></c:if>
			<c:if test="${not empty erroTelefone2}">Erro no preencimento do telefone 2<br/></c:if>
			<c:if test="${not empty erroTelefone3}">Erro no preencimento do telefone 3</c:if>
		</div>
		<br/>
	</c:if>	
	
	<input type="hidden" name="idTelefone1" value="${empresa.tel1.idTelefone}">
	<input type="hidden" name="idTelefone2" value="${empresa.tel2.idTelefone}">
	<input type="hidden" name="idTelefone3" value="${empresa.tel3.idTelefone}">
	
	<table style="width: 100%">
		<thead>			
			<tr>
			<td width="20%"></td>
			<td width="10%"><h2>DDD</h2></td>
			<td width="30%"><h2>N&uacute;mero</h2></td>
			<td width="40%"><h2>Complemento</h2></td>
			</tr>
		</thead>	
		<tbody>					
			<tr>
			<td width="20%"><h2>Telefone 1</h2></td>
			<td width="10%"><input id="empresaTelefone" type="text" name="ddd1" value="${empresa.tel1.ddd}" class="input30" maxlength="2"></td>
			<td width="30%"><input id="empresaTelefone" type="text" name="numero1" value="${empresa.tel1.numero}" class="input80" maxlength="9"></td>
			<td width="40%"><input id="empresaTelefone" type="text" name="complemento1" value="${empresa.tel1.complemento}" class="input80"></td>
			</tr>
			
			<tr>
			<td width="20%"><h2>Telefone 2</h2></td>
			<td width="10%"><input id="empresaTelefone" type="text" name="ddd2" value="${empresa.tel2.ddd}" class="input30" maxlength="2"></td>
			<td width="30%"><input id="empresaTelefone" type="text" name="numero2" value="${empresa.tel2.numero}" class="input80" maxlength="9"></td>
			<td width="40%"><input id="empresaTelefone" type="text" name="complemento2" value="${empresa.tel2.complemento}" class="input80"></td>
			</tr>
			
			<tr>
			<td width="20%"><h2>Telefone 3</h2></td>
			<td width="10%"><input id="empresaTelefone" type="text" name="ddd3" value="${empresa.tel3.ddd}" class="input30" maxlength="2"></td>
			<td width="30%"><input id="empresaTelefone" type="text" name="numero3" value="${empresa.tel3.numero}" class="input80" maxlength="9"></td>
			<td width="40%"><input id="empresaTelefone" type="text" name="complemento3" value="${empresa.tel3.complemento}" class="input80"></td>
			</tr>		
		</tbody>
	
	</table>
	
	<h4>Antes de atualizar os dados confira se os campos foram corretamente preenchidos.</h4>
	
	<div class="campoInfo">	
	<input type="submit" value="Atualizar" class="button" />
	</div>
	
	</form>
	
</div>
</div>

</div>
</div>

<br/><br/>

<div id="footer">
	<jsp:include page="layout/footerSI.jspf"></jsp:include>
</div>

</body>
</html>
