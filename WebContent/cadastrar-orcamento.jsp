<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<jsp:include page="layout/library.jspf" />

<title>Cesare Transporte</title>
</head>

<body bgcolor="#E8E8E8">

<div id="wrap">
 
	<div id="main">
 	<div id="pgOrcamentoPt1">
	<jsp:include page="layout/header.jspf"/>
	
	<table width="100%" bgcolor="#000000" align="center">	
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
	
	<c:if test="${not empty msgErro}">
		<div class="tableErro" style="padding: 30px;">								
			<div align="center">Verifique campos obrigat&oacute;rios n&atilde;o preenchidos corretamente</div>		
		</div>					
	</c:if>
	<br/>
	
	<table width="800" align="center">
		<tr>
			<td style="padding-top: 10px"><span class="tituloPagina">Or&ccedil;amento</span></td>
		</tr>
	</table>
	<jsp:useBean id="data" class="java.util.Date"/>
	<h3><fmt:formatDate value="${data}" dateStyle="full" timeZone="America/Sao_Paulo"/></h3>
	
	
	<div align="center">
	<div id="cardIndex">				
	<table width="760" border="0" align="center" bgcolor="#FFFFFF">	
		<tr>
		<td>	
		<img src="imagens/cabecalho_orcamento.jpg" alt="orcamento"/>	
		</td>
		</tr>
		<tr>
			<td>		
			<table width="100%" cellpadding="10">
				<tr>
					<td>							
					
					<form id="formCadOrcamento" action="enviarOrcamento" name="orcamento" method="post">
					<input type="hidden" value="cadastrarOrcamento" name="acao"  />
					
					<p class="legenda">Dados do solicitante do or&ccedil;amento</p>
					
					<c:if test="${not empty msgNome }"><c:set var="erronome" value="bordaErro"></c:set></c:if>
					
					<table width="100%" cellpadding="10">				
						<tr>						
							<td>
							<div align="left">
							<c:if test="${not empty msgNome }"><label class="erro">${msgNome}</label><br/></c:if>
							<input id="nomeCadOrcamento" type="text" class="input100" name="nome" value="${nome}" maxlength="200" />
							</div>
							</td>
						</tr>										
						<tr>
							<td>
							<div align="left">
							<c:if test="${not empty msgEmail }"><label class="erro">${msgEmail}</label><br/></c:if>
							<input id="emailCadOrcamento" type="text" class="input100" name="email" value="${email}" maxlength="200" />
							</div>
							</td>
						</tr>					
						<tr>
							<td>
							<div align="left">
							
							<c:if test="${not empty msgDdd }"><label class="erro">${msgDdd}</label><br/></c:if>
							<c:if test="${not empty msgTelefone }"><label class="erro">${msgTelefone}</label><br/></c:if>							
							
							<input id="dddCadOrcamento" class="input20" type="text" maxlength="2" name="ddd" value="${ddd}"/>
							<input id="telefoneCadOrcamento" class="input40" type="text" maxlength="9" name="telefone" value="${telefone}"/>
							</div>
							</td>
						</tr>					
					</table>
					
					<div class="espacador"></div>
						
					<span class="legenda">Dados de localiza&ccedil;&atilde;o do or&ccedil;amento</span>
	
					<table width="100%">
						<tr>
							<td>
							<table width="100%" cellpadding="10">
							<tr>
							<td>
							<span class="textoInformativo">Dados da origem</span><br/>
							
							<c:if test="${not empty msgOrigem }"><label class="erro">${msgOrigem}</label><br/></c:if>							
							<input id="origemCadOrcamento" type="text" name="origem" value="${origem}" class="input80" maxlength="90"/>
							<select name="estadoOrigem" id="estadoOrigem" class="select input15"></select>
	             			
	             			<br /><br />
	             			 
	             			<c:if test="${not empty msgEnderecoOrigem }"><label class="erro">${msgEnderecoOrigem}</label><br/></c:if> 
	             			<input id="enderecoOrigemCadOrcamento" type="text" name="enderecoOrigem" value="${enderecoOrigem}" class="input100" maxlength="254"/>
	             			</td>
							</tr>
							</table>					
							</td>
							<!-- .................................................................. -->
							<td>
							<table width="100%" cellpadding="10">
							<tr>
							<td>
							<span class="textoInformativo">Dados do destino</span><br/>
							
							<c:if test="${not empty msgDestino }"><label class="erro">${msgDestino}</label><br/></c:if>				
							<input id="destinoCadOrcamento" type="text" name="destino" value="${destino}" class="input80" maxlength="70"/>
							<select name="estadoDestino" id="estadoDestino" class="select input15"></select>
	             			
	             			<br /><br /> 
	             			
	             			<c:if test="${not empty msgEnderecoDestino }"><label class="erro">${msgEnderecoDestino}</label><br/></c:if>
	             			<input id="enderecoDestinoCadOrcamento" type="text" name="enderecoDestino" value="${enderecoDestino}" class="input100" maxlength="254"/>
	             			</td>
	             			</tr>
	             			</table>
							</td>
						</tr>				
					</table>
					
					<div class="espacador"></div>
					
					<span class="legenda">Informa&ccedil;&otilde;es adicionais sobre a carga</span>
	
					<table width="100%" cellpadding="10">
						<tr>
							<td>
							<c:if test="${not empty msgPeso }"><label class="erro">${msgPeso}</label><br/></c:if>				
							<input id="pesoCadOrcamento" type="text" name="peso" value="${peso}" class="input100" maxlength="100"/></td>
							
							<td>
							<c:if test="${not empty msgDimensao }"><label class="erro">${msgDimensao}</label><br/></c:if>
							<input id="dimensaoCadOrcamento" type="text" name="dimensao" value="${dimensao}" class="input100" maxlength="100"/></td>					
						</tr>				
					</table>
					
					<div class="espacador"></div>				
									
					<p><span class="legenda">Informa&ccedil;&otilde;es adicionais gerais</span></p>
					
					<span class="textoInformativo">
					Se desejar, utilize o campo abaixo para fazer um detalhamento da carga, ou solicitar informa&ccedil;&otilde;es adicionais referentes ao servi&ccedil;o. 	
					</span>
					
					<p>			
					<textarea id="mensagemCadOrcamento" name="mensagem" rows="10" class="laranjado width">${mensagem}</textarea>
					</p>
					<h2>
					Voc&ecirc; pode digitar <span id="contadorCaracterOrcamento">1000</span> caracteres em sua mensagem.
					</h2>
					
						<input id="btEnviarOrcamento" type="submit" value="Enviar Or�amento" class="button" />					
					
					</form>
					</td>
				</tr>
			</table>		
			</td>
		</tr>	
	</table>
	</div>
	</div>
	<br/>
	</div>

	<div id="espacador"></div>
	<br />
	<div id="aguardeEnviarOrcamento" >
		<div align="center">
			<table class="telaAguarde">
				<tr>
					<td>
						<h3>Seu or&ccedil;amento esta sendo enviado. Por favor aguarde um instante!</h3>
						<div align="center">
							<img alt="Aguarde" src="imagens/cetrans_gif_aguarde.gif" />
						</div> <br /> <br />
					</td>
				</tr>
			</table>
		</div>
	</div>
	<br/>
	<div id="espacador"></div>

	</div> 
</div>

<div id="pgOrcamentoPt2">
	<div id="footer">
	<jsp:include page="layout/footer.jspf"/>
	</div>
</div>

</body>
</html>
