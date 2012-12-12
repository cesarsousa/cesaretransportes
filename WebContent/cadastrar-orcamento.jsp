<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="br.com.cesaretransportes.modelo.Cliente" %>
<%	Cliente cliente = (Cliente) request.getSession().getAttribute("cliente"); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<jsp:include page="layout/library.jspf" />

<title>Cesare Transporte - Orçamento de serviço</title>
</head>

<body onunload="fecharJanela();" bgcolor="#E8E8E8">

<jsp:include page="layout/header.jspf"/>

<table width="100%" bgcolor="#000000">	
	<tr>
		<td>		
		<ul id="menu">
			<li><a href="index.jsp">Empresa</a></li>
			<li><a href="cadastrar-orcamento.jsp">Or&ccedil;amento</a></li>
			<li><a href="cadastrar-contato.jsp">Contato</a></li>
			<li><a href="download.jsp">Download</a></li>
			<li><a href="login.jsp">Login</a></li>							
		</ul>		
		</td>
	</tr>
</table>
<c:if test="${not empty cliente}">
<table width="100%" bgcolor="#000000">	
	<tr>
		<td>		
		<ul id="menu">			
			<c:if test="${not empty cliente}">
				<li><a href="javascript:document.formPerfil.submit();">Perfil</a></li>
				<form action="PerfilServlet" name="formPerfil" id="formPerfil">
					<input type="hidden" name="idCliente" value="${cliente.idCliente}"/>
				</form>
				<li><a href="javascript:document.formOrcamentos.submit();">Meus Or&ccedil;amentos</a></li>
				<form action="ClienteOpcaoConsultaServlet" name="formOrcamentos" id="formOrcamentos">
					<input type="hidden" name="idCliente" value="${cliente.idCliente}"/>
					<input type="hidden" name="opcao" value="5"/>
				</form>
				<li><a href="javascript:document.formServicos.submit();">Meus Servi&ccedil;os</a></li>
				<form action="ClienteOpcaoConsultaServlet" name="formServicos" id="formServicos">
					<input type="hidden" name="idCliente" value="${cliente.idCliente}"/>
					<input type="hidden" name="opcao" value="6"/>
				</form>
				<li><a href="LogoutServlet">sair</a></li>				
			</c:if>					
		</ul>		
		</td>
	</tr>
</table>
</c:if>	


<%-- <c:if test="${empty cliente}">
	<c:redirect url="login.jsp">
		<c:param name="msgOrcamento" value="Para solicitar um serviço é necessário efetuar login"></c:param>
	</c:redirect>
</c:if> --%>

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
				
				<c:if test="${msgErro}">
					<table width="100%" class="tableErro" cellpadding="5">
						<tr>
							<td>
							<div align="center">
							<span class="erro"><c:out value="ERRO DE VALIDAÇÃO NOS DADOS DO SERVIÇO"/></span><br/><br/>
							</div>
							
							<c:if test="${not empty msgOrigem }"><span class="erro"><c:out value="${msgOrigem}"/></span><br/></c:if>
							<c:if test="${not empty msgEnderecoOrigem }"><span class="erro"><c:out value="${msgEnderecoOrigem}"/></span><br/></c:if>
							<c:if test="${not empty msgDestino }"><span class="erro"><c:out value="${msgDestino}"/></span><br/></c:if>
							<c:if test="${not empty msgEnderecoDestino }"><span class="erro"><c:out value="${msgEnderecoDestino}"/></span><br/></c:if>
							<c:if test="${not empty msgPeso }"><span class="erro"><c:out value="${msgPeso}"/></span><br/></c:if>
							<c:if test="${not empty msgDimensao }"><span class="erro"><c:out value="${msgDimensao}"/></span><br/></c:if>
							</td>
						</tr>				
					</table>				
				</c:if>
				
				<%-- <c:if test="${not empty mensagem}">
					<div id="infoMensagem" align="center">
						<img id="fechar" class="direita" src="imagens/iconeok.png" alt="OK" title="OK" border="0" />					
						<pre><span class="mensagem">${mensagem}</span></pre>
					</div>
				</c:if>	 --%>		
				
				<span class="legenda">Dados do solicitante</span>
				<table width="100%" cellpadding="10">
							
				
				<!-- se existe um cliente na sessão renderiza o formulario pre preenchido -->				
				<c:if test="${not empty cliente}">
								
					<tr>
						<td><div align="right"><span class="p">NOME ou EMPRESA : </span></div></td>
						<td><div align="left"><span class="legenda"><i>${cliente.nome }</i></span></div></td>
					</tr>										
					<tr>
						<td><div align="right"><span class="p">EMAIL : </span></div></td>
						<td><div align="left"><span class="legenda"><i>${cliente.email }</i></span></div></td>
					</tr>					
					<tr>
						<td><div align="right"><span class="p">TELEFONE : </span></div></td>
						<td>
						<div align="left"><span class="legenda"><i>${cliente.telefone.ddd} - ${cliente.telefone.numero}</i></span></div></td>
					</tr>															
				
				</c:if>
				<c:if test="${empty cliente}">
					<tr>
						
						<td><div align="left"><input id="nomeCadOrcamento" type="text" class="input100" name="nome"/></div></td>
					</tr>										
					<tr>
						<td><div align="left"><input id="emailCadOrcamento" type="text" class="input100" name="email"/></div></td>
					</tr>					
					<tr>
						<td>
						<div align="left">
						<input id="dddCadOrcamento" class="input20" type="text" maxlength="3" name="ddd"/>
						<input id="telefoneCadOrcamento" class="input40" type="text" maxlength="9" name="telefone"/>
						</div></td>
					</tr>					
				</c:if>			
				</table>
				
				<br/>		
					
				<span class="legenda">Dados de localização</span>				
				<table width="100%">
					<tr>
						<td>
						<table width="100%" cellpadding="10">
						<tr>
						<td>
						<input id="origemCadOrcamento" type="text" name="origem" value="${origem}" class="input80" maxlength="90"/>
						<select name="estadoOrigem" id="estadoOrigem" class="select input15"></select>
             			
             			<br /><br />
             			 
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
						<input id="destinoCadOrcamento" type="text" name="destino" value="${destino}" class="input80" maxlength="70"/>
						<select name="estadoDestino" id="estadoDestino" class="select input15"></select>
             			
             			<br /><br /> 
             			
             			<input id="enderecoDestinoCadOrcamento" type="text" name="enderecoDestino" value="${enderecoDestino}" class="input100" maxlength="254"/>
             			</td>
             			</tr>
             			</table>
						</td>
					</tr>				
				</table>
				
				<span class="legenda">Informações adicionais sobre a carga</span>				
				<table width="100%" cellpadding="10">
					<tr>
						<td><input id="pesoCadOrcamento" type="text" name="peso" value="${peso}" class="input100" maxlength="100"/></td>
						<td><input id="dimensaoCadOrcamento" type="text" name="dimensao" value="${dimensao}" class="input100" maxlength="100"/></td>					
					</tr>				
				</table>				
								
				<br />
				<p><span class="legenda">Digite sua mensagem</span></p>
				
				<span class="textoInformativo">
				Se desejar, utilize o campo abaixo para informa&ccedil;&otilde;es adicionais referentes ao servi&ccedil;o	
				</span>
				
				<p>			
				<textarea id="mensagemCadOrcamento" name="mensagem" rows="10" class="laranjado width">${mensagem}</textarea>
				</p>
				
				<div align="right">
					<input type="submit" value="Enviar Orçamento" onclick="abrirJanela();" class="button" />					
				</div> 
				
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
<jsp:include page="layout/footer.jspf"/>

</body>
</html>
