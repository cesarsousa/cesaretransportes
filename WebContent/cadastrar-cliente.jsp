<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<jsp:include page="layout/library.jspf" />

<title>Cesare Transporte - Cadastrar Cliente</title>
</head>

<body bgcolor="#E8E8E8" onload="getFocus();" onunload="fecharJanela();">

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
<br/>

<jsp:useBean id="data" class="java.util.Date"/>
<h3><fmt:formatDate value="${data}" dateStyle="full" timeZone="America/Sao_Paulo"/></h3>

<div align="center">
<div id="cardIndex">

	<br/>
		
		<form action="cadastrarCliente" method="post">
		<input type="hidden" name="opcao" value="cadastrar"/>
		<table width="100%" border="0" cellpadding="5" align="center">
		
			<tr>
				<td colspan="3" bgcolor="#666666">
				<label class="dadosAcesso">Dados de Acesso</label>
				</td>
			</tr>
			
			<tr>				
				<td width="146" bgcolor="#FF6600" colspan="2">
					<c:choose>
						<c:when test="${editarCliente}">
							<font style="color: #000000; font-style: italic; font-weight: bold;">${usuario}</font>
						</c:when>
						<c:otherwise>
							<input id="usuarioCadCliente" type="text" name="usuario" value="${usuario}" class="input70"/>
						</c:otherwise>					
					</c:choose>					
				</td>
				
				<td width="334" rowspan="3" bgcolor="#CCCCCC">
					<c:choose>
						<c:when test="${editarCliente}">
							<font style="color: #666; text-align: justify"> Para trocar sua senha
							digite a nova senha nos campos espec&iacute;ficos ao lado.</font>
						</c:when>
						<c:otherwise>
							<font style="color: #666; text-align: justify"> Voc&ecirc; precisa
							de um e-mail v&aacute;lido para se cadastrar em nosso site. Este
							ser&aacute; sua identifica&ccedil;&atilde;o como us&uacute;ario do
							site. Para se cadastrar preencha os campos abaixo e n&atilde;o
							utilize acentos ou abrevia&ccedil;&otilde;es.</font>
						</c:otherwise>					
					</c:choose>				
				</td>
			</tr>
			<tr>				
				<td bgcolor="#FF6600" colspan="2">
					<input id="senhaCadClienteChecker" type="text" value="SENNHA DE ACESSO AO SITE" class="input70"/>
					<input id="senhaCadCliente" type="password" name="senha1" value="${senha1}" class="input70"/>
				</td>
			</tr>
			<tr>				
				<td bgcolor="#FF6600">
					<input id="senha2CadClienteChecker" type="text" value="CONFIRME SUA SENHA" class="input70"/>
					<input id="senha2CadCliente" type="password" name="senha2" value="${senha2}" class="input70"/>
				</td>
			</tr>
			<c:if test="${not empty msgUser}">
			<tr>
				<td colspan="3">
					<div align="left"><label class="erro">${msgUser}<br/></label></div>							
				</td>
			</tr>
			</c:if>
			<c:if test="${not empty msgSenha}">
			<tr>
				<td colspan="3">
					<div align="left">						
						<label class="erro">${msgSenha}<br/></label></div>							
				</td>
			</tr>
			</c:if>					
		</table>
						
		<br/>
		
		<table width="100%" background="imagens/fundo_index.jpg" cellpadding="5">
			<tr>
				<td colspan="2" bgcolor="#666666">
				<label class="dadosAcesso">Dados de Cadastro</label>
				</td>
			</tr>
			
			<c:if test="${not empty msgNome}">
				<tr>
				<td width="100%" align="center" colspan="2">
					<label class="erro"><br/>${msgNome}</label>
				</td>				
				</tr>				
			</c:if>	
			
			<tr>				
				<td colspan="2">
					<input id="nomeCadCliente" type="text" name="nome" class="input80 transparente" value="${nome}"/>					
				</td>
			</tr>			
			<tr>
				<td width="100%" colspan="2">
					<!-- <label class="label">Tipo do Documento</label> -->
					<c:if test="${not empty msgCpf}">
						<select name="tipoDocCadCliente" class="select transparente input20">
							<option value="cpf">CPF</option>
							<option value="cnpj">CNPJ</option>
						</select>
					</c:if>
					<c:if test="${empty msgCpf}">
						<select name="tipoDocCadCliente" class="select transparente input20">							
							<option value="cnpj">CNPJ</option>
							<option value="cpf">CPF</option>
						</select>
					</c:if>
					
					<input id="numDocCadCliente" type="text" name="numDoc" class="input60 transparente" value="${numDoc}" maxlength="14"/>
				</td>			
			</tr>
			<c:if test="${not empty msgNunDoc}">
				<td width="100%" align="center" colspan="2">
					<label class="erro">${msgNunDoc}</label>
				</td>						
			</c:if>
						
			<c:if test="${msgTelefone}">
				<tr>
				<td width="100%" align="center" colspan="2">				
					<c:if test="${not empty msgTelDdd}">
						<label class="erro">${msgTelDdd}<br/></label>
					</c:if>
					<c:if test="${not empty msgTelNumero}">
						<label class="erro">${msgTelNumero}</label>
					</c:if>				
				</td>				
			</tr>
			</c:if>
									
			<tr>
				<td colspan="2">
					<br/>
					<input id="dddCadCliente" type="text" name="telDdd" class="input20 transparente" maxlength="2" value="${telDdd}" />
					<input id="telefoneCadCliente" type="text" name="telNumero" class="input60 transparente" maxlength="8" size="8" align="middle" value="${telNumero}" />
				</td>				
			</tr>
			
			<tr>
				<td colspan="2">
				<input id="complementoCadCliente" type="text" name="telComplemento" class="input80 transparente" maxlength="20" align="middle" value="${telComplemento}"></input>
				</td>
			</tr>			
			
			<c:if test="${not empty msgEndereco}">
				<tr>
				<td width="100%" align="center" colspan="2">					
						<label class="erro">${msgEndereco}</label>													
				</td>				
			</tr>
			</c:if>			
			
			<c:choose>
				<c:when test="${editarCliente}">
					<tr>
						<td colspan="2"><div align="center"><h2>Para alterar o endere&ccedil;o, digite a nova localiza&ccedil;&atilde;o
						no campo abaixo do endere&ccedil;o atual. Certifique-se que a novo endere&ccedil;o esteja no formato
						: <i>nome_da_rua n&uacute;mero_da_rua, bairro</i></h2></div></td>
					</tr>
					<tr>
						<td colspan="2" align="center">
							<label class="label">Endere&ccedil;o atual: ${enderecoAtual}</label>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center">				
							<input type="text" name="novoEndereco" class="campoInput" size="80" maxlength="200" value="${novoEndereco}"></input>								
						</td>
					</tr>				
				</c:when>
				<c:otherwise>
					<tr>						
						<td colspan="2">
						<br/>				
							<input id="logradouroCadCliente" type="text" name="rua" class="input60 transparente" maxlength="153" value="${rua}"/>								
							<input id="numeroLogradouroCadCliente" type="text" name="numeroRua" class="input20 transparente" maxlength="10" value="${numeroRua}"/>
						</td>
					</tr>
					
					
					
					<tr>
						<td colspan="2">
							<input id="bairroCadCliente" type="text" name="bairro" class="input40 transparente" maxlength="40" value="${bairro}"></input>
							<input id="cidadeCadCliente" type="text" name="cidade" class="input30 transparente" maxlength="40" value="${cidade}"></input>
							<select id="estadosCadCliente" name="estado" class="select input10">
								<c:if test="${editarCliente}"><option value="${estado}">${estado}</option></c:if>
							</select>							
						</td>
					</tr>				
				</c:otherwise>
			</c:choose>			
			
			<c:choose>
				<c:when test="${editarCliente}">
					<tr>
						<td width="100%" align="center" colspan="2">
							<input type="hidden" value="true" name="editarCliente"/>							
							<input type="hidden" value="${usuario}" name="usuario"/>							
							<input type="hidden" value="${enderecoAtual}" name="enderecoAtual"/>
							<input type="hidden" value="true" name="rua"/>
							<input type="hidden" value="1" name="numeroRua"/>
							<input type="hidden" value="true" name="bairro"/>
							<input type="submit" value="Confirmar" onclick="abrirJanela();" class="button"/>
						</td>								
					</tr>
				</c:when>
				<c:otherwise>
					<c:if test="${not empty msgTermoContrato}">
						<tr>
						<td width="100%" align="center" colspan="2">				
							<label class="erro">${msgTermoContrato}</label>
						</td>				
						</tr>
					</c:if>
				</c:otherwise>
			</c:choose>						
		</table>
		
		<table class="faixaLaranja">
			<tr>
				<td>
					<input type="checkbox" name="termoContrato" value="true"/>				
					<label class="label" style="font-size: small">Li e concordo com os <a href="termos-contrato.jsp" target="_blank" style="color: #000000"> Termos de Contrato </a> !</label>				
				</td>			
			</tr>
			<tr>
				<td>
					<input class="button" type="submit" value="Cadastrar" onclick="abrirJanela();"/>
				</td>			
			</tr>
		</table>
		</form>
		
</div>
</div>

<jsp:include page="layout/footer.jspf" />

</body>
</html>
