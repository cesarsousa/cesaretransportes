<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="estilo.css" />
<script language="javascript">
function enviarEmail(nome, email){	
	var esquerda = (screen.width - 700)/2;
	var topo = (screen.height - 500)/2;
	alertWindow = window.open("enviarEmail.jsp?nome=" + nome + "&email=" + email,"bookpixWin","width=700, height=500, top="+topo+", left="+esquerda);
}

</script>

<style type="text/css">
p {
	color: #333;
	font-weight: bold;
}

p.estilo {
	color: #FF6600
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
</style>
<title>Cesare Transportes - Consultas</title>
</head>

<body bgcolor="#CCCCCC">
<fieldset>

	<!-- Header Image e menu de opções -->
	<table width="100%" bgcolor="#FF6600" cellpadding="10">
	<tr>
		<td>
		<table width="100%" bgcolor="#333333">
			<tr>
				<td>
				<table width="100%">
					<tr>
						<td bgcolor="#333333">
						<div align="left"><img src="imagens/logo_sisint_1_1_200_100.jpg" /></div>
						</td>
						<td bgcolor="#333333">
						<div align="center"><img src="imagens/logo_sisint_2_1_100_100.jpg" /></div>
						</td>
						<td bgcolor="#333333">
						<div align="right"><img src="imagens/logo_sisint_3_1_200_100.jpg" /></div>
						</td>
					</tr>					
				</table>				
				</td>
			</tr>
		</table>
		<table width="800" align="center">
			<tr>
				<td align="center">					
						<ul id="menu">
							<li><a href="index.jsp">Empresa</a></li>
							<li><a href="cadastrar-orcamento.jsp">Or&ccedil;amento</a></li>
							<li><a href="cadastrar-contato.jsp">Contato</a></li>
							<li><a href="login.jsp">Login</a></li>
							<c:if test="${true}">
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
		</td>
	</tr>
</table>
<p><a href="javascript:history.go(-1)"><img alt="VOLTAR" src="imagens/voltar_20px.png"></a></p>	

<label class="label1">Legenda de tipos de or&ccedil;amentos</label>
<ul>
	<li><img alt="orcamento nao confirmado" src="imagens/van_orcamento_30_30.png">
		<label class="label2">Representa um or&ccedil;amento comuns, ou seja, n&atilde;o foi confimado com um servi&ccedil;o ativo.</label>
	<li><img alt="orcamento confirmado como servico" src="imagens/van_servico_30_30.png">
		<label class="label2">Representa um servi&ccedil;o ativo que n&atilde;o foi entregue.</label>
	<li><img alt="serviço finalizado" src="imagens/van_servico_entregue_30_30.png">
		<label class="label2">Representa um servi&ccedil;o inativo, ou seja, um servi&ccedil;o ativo j&aacute; entregue.</label></li>
</ul>

<table width="100%">
	<tr>
		<td align="left"><p class="estilo">${cliente.nome}</p></td>
		<td align="right"><jsp:useBean id="data" class="java.util.Date" />
		<p class="estilo"><fmt:formatDate value="${data}" dateStyle="full" />.</p>
		</td>		
	</tr>
</table>

<table class="laranjado" bgcolor="#FFFFFF" width="100%">
	<tr>
		<td><h3>${titulo}</h3></td>
	</tr>
</table>

<!-- Orçamentos -->
<c:if test="${not empty orcamentos}">
	<table class="laranjado" width="100%" bgcolor="#ffffff"	cellpadding="10">
		<tr>
			<td><c:forEach var="orcamento" items="${orcamentos}">
				<table border="0" width="100%">
					<tr>
						<td width="30">
						<img src="imagens/van_orcamento_30_30.png" alt="orçamento" title="orçamento" border="0" /></td>
						
						<td width="80">						
						 <a href="processarAcaoOrcamento?acao=lerOrcamento&codigo=${orcamento.idOrcamento}" title="ler mensagem">
						 Cod. ${orcamento.idOrcamento}</a></td>						
										
						<td width="200" title="origem: ${orcamento.detalheOrigem}">
						<c:set var="origem"	value="${orcamento.detalheOrigem}"/> 
						<c:out value="${fn:substring(origem,0,20)}"/>...</td>
						
						<td width="200" title="destino: ${orcamento.detalheDestino}">
						<c:set var="destino" value="${orcamento.detalheDestino}"/> 
						<c:out value="${fn:substring(destino,0,20)}"/>...</td>
						
						<td width="100" title="peso: ${orcamento.peso}">
						<c:set var="peso" value="${orcamento.peso}" /> 
						<c:out value="${fn:substring(peso,0,10)}"/>...</td>
						
						<td width="100" title="dimensao: ${orcamento.dimensao}">
						<c:set var="dimensao" value="${orcamento.dimensao}" /> 
						<c:out value="${fn:substring(dimensao,0,10)}"/>...</td>
																			
						<td width="30">
							<form action="" name="dadosCliente">
								<input type="hidden" name="nome" id="nome" value="${cliente.nome}">
								<input type="hidden" name="email" id="email" value="${cliente.email}">
							</form>														
							<a href="javascript:enviarEmail('${cliente.nome}', '${cliente.email}');">													
							<img src="imagens/escrever_20.jpg" title="Enviar Email" alt="Enviar Email" border="0" />
							</a>
						</td>							

						<td><i><c:set var="msg" value="${orcamento.mensagem}" />
						<c:out value="${fn:substring(msg,0,100)}..." /></i></td>

						<td width="150" align="right">
						<c:set var="dt"	value="${orcamento.infoDataCadastro}" /> 
						<c:out value="${fn:substring(dt,0,10)}"></c:out></td>
					</tr>
				</table>
				<hr>
			</c:forEach></td>
		</tr>
	</table>
</c:if>
	
<p>
<div align="center"><img src="imagens/cesare rodape.jpg" width="760" height="30" /></div>
<div align="center" class="designer">Designed by Cesar De Sousa Junior</div>
</p>

</fieldset>
</body>
</html>