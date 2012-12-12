<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn" %>
<%@page import="java.util.List"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="icon" type="image/gif" href="imagens/animated_favicon1.gif" />
<link rel="stylesheet" type="text/css" href="estilo.css" />
<link rel="stylesheet" type="text/css" href="estilo2.css" />

<script language="javascript">
function validarFormulario(){
	d = document.formulario;
	if (d.marca.value == ""){
		alert("< O campo 'Marca' deve ser preenchido! >");
		d.marca.focus();
		return false;
	}
	if (d.cor.value == ""){
		alert("< O campo 'Cor' deve ser preenchido! >");
		d.cor.focus();
		return false;
	}
}
</script>

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
	color: #B22222;	
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
	font-weight: bold;
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

.label3 {
	font-size: 14px;
	font-weight: bold;
	font-family: Verdana, Geneva, sans-serif;
	color: #333;
	padding: 10px;
}

.label4 {
	font-size: 12px;
	font-style: italic;
	font-family: Arial, Helvetica, sans-serif;
	color: #333;
	padding: 10px;
}
</style>
<title>Cesare Transportes - Ve&iacute;culo</title>
</head>

<body bgcolor="#cccccc">
<fieldset>

<jsp:include page="includeCabecalhoComLinks.jspf"></jsp:include>

<h3>${mensagem}</h3>
<h3>Ve&iacute;culos</h3>

	<table width="100%">
		<tr>
			<td align="left"><jsp:useBean id="data" class="java.util.Date" />
				<p class="estilo"><fmt:formatDate value="${data}" dateStyle="full" />.</p></td>
		</tr>
	</table>
	
	<c:if test="${not empty veiculoRecadastrado}">
		<h4>Ve&iacute;culo ${veiculoRecadastrado} recadastrado com sucesso.</h4>
	</c:if>
	
	<c:if test="${not empty veiculoAlterado}">
		<h4>Ve&iacute;culo ${veiculoAlterado} alterado com sucesso.</h4>
	</c:if>
	
	<!-- RENDERIZAR RECADASTRO DE FORMULARIO -->
	<c:if test="${not empty erroVeiculoCadInativo}">
		<div align="center">
		<label class="erro"><br/>${erroVeiculoCadInativo}</label><br /><br />
		<table class="laranjado" width="760" bgcolor="#FFFFFF">
			<tr>
				<td>
				<label class="label3">Ve&iacute;culo Cadastrado</label><br /><br />
				<label class="label4">O ve&iacute;culo placa ${veiculoCadastrado.detalhePlaca} ja est&aacute; cadastrado no sistema</label><br /><br />
				<label class="label3">Dados do Ve&iacute;culo</label>
				<ul>
					<li><label class="label4">ID: ${veiculoCadastrado.idVeiculo}</label></li>
					<li><label class="label4">Placa: ${veiculoCadastrado.detalhePlaca}</label></li>
					<li><label class="label4">Marca: ${veiculoCadastrado.marca}</label></li>
					<li><label class="label4">Cor: ${veiculoCadastrado.cor}</label></li>
					<li><label class="label4">Localiza&ccedil;&atilde;o Atual: ${veiculoCadastrado.localizacao}</label></li>
				</ul>							
				
				<form action="cadastrarVeiculo" method="post" name="formulario" onsubmit="return validarFormulario();">	
				<table bgcolor="#FF9900" width="100%" cellpadding="5">					
						<tr>
							<td colspan="2"><label class="label3">Reativar este ve&iacute;culo com os campos abaixo.</label><br/><br/></td>
						</tr>						
						<tr>
							<td align="right"><label class="label4">Marca:</label></td>
							<td align="left"><input type="text" name="marca" id="marca" value="${veiculoCadastrado.marca}"/></td>
						</tr>
						<tr>
							<td align="right"><label class="label4">Cor:</label></td>
							<td align="left"><input type="text" name="cor" id="cor" value="${veiculoCadastrado.cor}"/></td>
						</tr>
						<tr>							
							<td align="right"><label class="label4">Localiza&ccedil;&atilde;o Atual:</label></td>
							<td align="left"><input type="text" name="localizacao" id="localizacao" value="${veiculoCadastrado.localizacao}"/></td>
						</tr>
						<tr>
							<td colspan="2" align="center">
							<input type="hidden" name="acao" value="10"/>
							<input type="hidden" name="idVeiculo" value="${veiculoCadastrado.idVeiculo}"/>
							<input type="hidden" name="placa" value="${veiculoCadastrado.placa}"/>
							<input type="hidden" name="marca" value="${veiculoCadastrado.marca}"/>
							<input type="hidden" name="cor" value="${veiculoCadastrado.cor}"/>
							<input type="hidden" name="localizacao" value="${veiculoCadastrado.localizacao}"/>
							<input type="submit" value="Reativar Veículo"/></td>
						</tr>			
				</table>
				</form>
					
				<br />
				<form action="javascript:history.go(-2)">
				<table bgcolor="#CFCFCF" width="100%" cellpadding="5">				
						<tr>
							<td align="center">
							<label class="label3">Não quero reativar este ve&iacute;culo!</label>
							<input type="submit" value="Cancelar"/></td>
						</tr>			
				</table>
				</form>					
				
				</td>
			</tr>		
		</table>
		</div>		
	</c:if>	
	
<!-- RENDERIZAR FORMULÁRIO DE CADASTRO DE VEÍCULO / RENDERIZAR LISTA DOS VEICULOS CADASTRADOS E FUNÇÃO DE BUSCA -->
<c:if test="${not empty erroVeiculoCadAtivo}">
	<div align="center"><label class="erro"><br/>${erroVeiculoCadAtivo}</label></div>
</c:if>
<c:choose>
	<c:when test="${not empty cadastrarNovo}">
	<c:if test="${not empty cadastrarNovo}">
	<form action="cadastrarVeiculo" method="post">
	
	<div align="center">	
	<c:choose>
		<c:when test="${not empty flagAlterar}">
			<h3>Alterar dados do ve&iacute;culo.</h3>
		</c:when>
		<c:otherwise>
			<h3>Cadastrar um novo ve&iacute;culo.</h3>
		</c:otherwise>
	</c:choose>	
	
	<c:if test="${not empty erroPrefixoPlaca}">
		<label class="erro"><br/>${erroPrefixoPlaca}</label>
	</c:if>
	<c:if test="${not empty erroSufixoPlaca}">
		<label class="erro"><br/>${erroSufixoPlaca}</label>
	</c:if>
				
	<table width="760" height="50" class="laranjado" cellpadding="10">
		<tr>
			<td background="imagens/cadVeiculoFoto.jpg">						
				<p>.</p>
				<h3 style="color: white; text-align: left">Placa.</h3>
				<c:choose>
				<c:when test="${not empty flagAlterar}">
					<input type="text" size="3" maxlength="3" name="prefixoPlaca" value="${prefixoPlaca}" readonly="readonly">			
					<label><font style="color: white;">-</font></label>
					<input type="text" size="4" maxlength="4" name="sufixoPlaca" value="${sufixoPlaca}" readonly="readonly">
				</c:when>
				<c:otherwise>
					<input type="text" size="3" maxlength="3" name="prefixoPlaca" value="${prefixoPlaca}">			
					<label><font style="color: white;">-</font></label>
					<input type="text" size="4" maxlength="4" name="sufixoPlaca" value="${sufixoPlaca}">
				</c:otherwise>
				</c:choose>			
				<p>.<br/></p>							
			</td>
		</tr>
	</table>
	
	<h3>Dados de cadastro do ve&iacute;culo.</h3>
	
	<c:if test="${not empty erroMarca}">
		<label class="erro"><br/>${erroMarca}</label>
	</c:if>
	<c:if test="${not empty erroCor}">
		<label class="erro"><br/>${erroCor}</label>
	</c:if>
	
	<table width="760" class="laranjado" cellpadding="10" bgcolor="#EE9A49">
		<tr>
			<td width="380" align="right"><label class="label">Marca.</label></td>
			<td width="380" align="left">
			<c:choose>
				<c:when test="${not empty flagAlterar}">
					<input type="text" name="marca" value="${marca}" readonly="readonly"></input>
				</c:when>
				<c:otherwise>
					<input type="text" name="marca" value="${marca}"></input>
				</c:otherwise>
			</c:choose>		
			</td>
		</tr>
		
		<tr>
			<td width="380" align="right"><label class="label">Cor.</label></td>
			<td width="380" align="left">
			<c:choose>
				<c:when test="${not empty flagAlterar}">
					<input type="text" name="cor" value="${cor}" readonly="readonly"></input>
				</c:when>
				<c:otherwise>
					<input type="text" name="cor" value="${cor}"></input>
				</c:otherwise>
			</c:choose>		
			</td>
		</tr>	
	</table>
	
	<c:if test="${not empty erroDataChegada}">
	<tr>
		<td colspan="2" align="center"><label class="erro">${erroDataChegada}</label></td>
	</tr>	
	</c:if>
	
	<h3>Dados de localiza&ccedil;&atilde;o do ve&iacute;culo.</h3>
	<h4>As informa&ccedil;&otilde;es de localiza&ccedil;&atilde;o &eacute; de preenchimento opcional</h4>

	<table width="760" class="laranjado" cellpadding="10" bgcolor="#D3D3D3">
	<tr>
		<td><div align="center"><h5>Para manter a exatid&atilde;o da informa&ccedil;&atilde;o utilize endere&ccedil;o no formato.<br />
		<i>rua (nome da rua) (n&uacute;mero da rua), (bairro), (cidade) (estado)</i></h5></div></td>
	</tr>
	<tr>
		<td width="380" align="center"><label class="label">Localiza&ccedil;&atilde;o atual.</label></td>		
	</tr>	
	<tr>		
		<td width="380" align="center"><input type="text" name="localizacao" value="${localizacao}" size="100"></input></td>
	</tr>
	
	<tr>
		<c:choose>
			<c:when test="${not empty flagAlterar}">
				<td align="center">
				<input type="hidden" value="6" name="acao"/>
				<!-- O id esta implicito na pagina. Setado pela classe VeiculoServlet case: ACESSAR_EDITAR#configurarRequestParaAlteracao -->
				<input type="hidden" value="${id}" name="id"/>
				<input type="hidden" name="placa" value="${prefixoPlaca}${sufixoPlaca}"/>
				<input type="submit" value="ALTERAR"/></td>
			</c:when>
			<c:otherwise>
				<input type="hidden" value="2" name="acao">
				<td align="center"><input type="submit" value="CADASTRAR"/></td>
			</c:otherwise>
		</c:choose>		
	</tr>
	</table>	
		
	</div>
	</form>	
	</c:if>
</c:when>

<c:otherwise>
	<c:if test="${not empty msgSemRota }">
		<h4>Este ve&iacute;culo n&atilde;o possui entregas pendentes.</h4>
	</c:if>
	<c:if test="${not empty msgBusca}">
			<h4><c:out value="${msgBusca}"></c:out></h4>
	</c:if>
		
	<table class="collapse" cellpadding="5" width="100%" border="0">
			<tr>						
				<td width="600" bgcolor="#FF6600" align="left">
					<form action="cadastrarVeiculo" method="post">
						<input type="hidden" value="7" name="acao">
						<input type="text" name="parametro" class="campoInput" size="60"/>					
						<label class="label">Filtro:</label>
						<input type="radio" name="filtro" value="id" checked="checked"/><label class="label">id</label>
	      				<input type="radio" name="filtro" value="placa" /><label class="label">placa</label>
	      				<input type="radio" name="filtro" value="localizacao" /><label class="label">localiza&ccedil;ao</label>
						<input type="submit" value="BUSCAR"/>
					</form>								
				</td>
				
				<td width="300"></td>
				
				<c:if test="${empty cadastrarNovo}">
				<td width="100" align="right" bgcolor="#FF6600">
					<form action="cadastrarVeiculo" method="post">
						<input type="hidden" value="1" name="acao">					
						<div align="center"><input type="submit" value="Cadastrar Novo"></div>
					</form>
				</td>
			</c:if>
		</tr>
	</table>
	
	<c:if test="${not empty orcamentosPorVeiculo}">
		<table width="100%" class="laranjado" bgcolor="#ffffff"	cellpadding="5">
			<tr>
				<td><label class="label2">Id ve&iacute;culo</label></td>
				<td><label class="label2">Placa</label></td>
				<td><label class="label2">Marca</label></td>
				<td><label class="label2">Cor</label></td>
				<td><label class="label2">localiza&ccedil;&atilde;o</label></td>
			</tr>
			<tr>
				<td bgcolor="#E8E8E8"><label class="label">${veiculo.idVeiculo}</label></td>
				<td bgcolor="#E8E8E8"><label class="label">${veiculo.placa}</label></td>
				<td bgcolor="#E8E8E8"><label class="label">${veiculo.marca}</label></td>
				<td bgcolor="#E8E8E8"><label class="label">${veiculo.cor}</label></td>
				<td bgcolor="#E8E8E8"><label class="label">${veiculo.localizacao}</label></td>
				<c:if test="${veiculo.emRota}">
					<td bgcolor="#E8E8E8">
						<form action="cadastrarVeiculo" method="post">
							<input type="hidden" name="acao" value="9"/>
							<input type="hidden" name="idVeiculo" value="${veiculo.idVeiculo}"/>
							<input type="submit" value="Finalizar Entregas"/>
						</form>
					</td>
				</c:if>				
			</tr>
		</table>	
		
		<c:choose>
			<c:when test="${veiculo.emRota}">
				<h3>Lista de entregas</h3>				
				<table width="100%" class="laranjado" bgcolor="#ffffff"	cellpadding="5">
					<tr>						
						<td><label class="label2">C&oacute;d. serv.</label></td>
						<td><label class="label2">Id cliente</label></td>
						<td><label class="label2">Origem</label></td>						
						<td><label class="label2">Destino</label></td>
						<td><label class="label2">Peso</label></td>
						<td><label class="label2">Dimens&atilde;o</label></td>
						<td><label class="label2">Data cadastro</label></td>
						<td><label class="label2">Valor</label></td>
						<td><label class="label2">Data prevista entrega</label></td>																	
					</tr>
					<c:forEach items="${veiculo.servicos}" var="servico">
					<tr>						
						<td bgcolor="#E8E8E8"><label class="label">${servico.idServico}</label></td>
						<td bgcolor="#E8E8E8"><label class="label">${servico.orcamento.cliente.idCliente}</label></td>						
						<td bgcolor="#E8E8E8"><label class="label">${servico.orcamento.detalheOrigem}</label></td>
						<td bgcolor="#E8E8E8"><label class="label">${servico.orcamento.detalheDestino}</label></td>
						<td bgcolor="#E8E8E8"><label class="label">${servico.orcamento.peso}</label></td>
						<td bgcolor="#E8E8E8"><label class="label">${servico.orcamento.dimensao}</label></td>
						<td bgcolor="#E8E8E8"><label class="label">${servico.orcamento.infoDataCadastro}</label></td>
						<td bgcolor="#E8E8E8"><label class="label">R$ ${servico.valor}</label></td>
						<td bgcolor="#E8E8E8"><label class="label">${servico.infoDataPrevEntrega}</label></td>																	
					</tr>
					</c:forEach>
				</table>
				
			</c:when>
			<c:otherwise>
				<h3>Este ve&iacute;culo n&atilde;o possui entregas</h3>
			</c:otherwise>
		</c:choose>
	</c:if>
	
	<c:if test="${not empty veiculos}">
		<table width="100%" class="laranjado" bgcolor="#ffffff"	cellpadding="5">
			<tr>
				<td>			
				<table >
				<tr>
					<td colspan="2" width="60"><label class="label2">A&ccedil;&otilde;es</label></td>
					<td width="50"><label class="label2">Id</label></td>
					<td width="100"><label class="label2">Marca</label></td>
					<td width="100"><label class="label2">Cor</label></td>
					<td width="100"><label class="label2">Placa</label></td>				
					<td width="450"><label class="label2">Localiza&ccedil;&atilde;o</label></td>
					<td width="100"><label class="label2"></label></td>
					<td width="150"><label class="label2"></label></td>
					<td width="100"><label class="label2"></label></td>					
				</tr>
				<c:forEach items="${veiculos}" var="veiculo">					
				<tr>
				
					<c:choose>
						<c:when test="${veiculo.deletado}">
							<td width="22" bgcolor="#000000">
							<img src="imagens/excluir_20px.png" alt="veículo excluido"	title="veículo excluido" border="0" /></td>
					
							<td width="30">
							<img src="imagens/escrever_20.jpg" title="veículo não editável" alt="veículo não editável" border="0" /></td>
						</c:when>
						<c:otherwise>
							<td width="22">
							<a href="cadastrarVeiculo?id=${veiculo.idVeiculo}&acao=4" onclick="javascript:return confirm('Deletar este veículo ?')">
							<img src="imagens/excluir_20px.png" alt="excluir veículo"	title="excluir veículo" border="0" /></a></td>
						
							<td width="30"><a href="cadastrarVeiculo?id=${veiculo.idVeiculo}&acao=5&localizacao=${veiculo.infoLocalizacao}">
							<img src="imagens/escrever_20.jpg" title="editar veículo" alt="editar veículo" border="0" /></a></td>
						</c:otherwise>
					</c:choose>				
					
					<td width="50" bgcolor="#E8E8E8"><label class="label">${veiculo.idVeiculo}</label></td>
					<td width="100" bgcolor="#E8E8E8"><label class="label">${veiculo.marca}</label></td>
					<td width="100" bgcolor="#E8E8E8"><label class="label">${veiculo.cor}</label></td>
					<td width="100" bgcolor="#E8E8E8"><label class="label">${veiculo.detalhePlaca}</label></td>				
					<td width="450" bgcolor="#CFCFCF"><label class="label">${veiculo.infoLocalizacao}</label></td>				
					
					<c:choose>
						<c:when test="${veiculo.emRota}">
							<td width="100" bgcolor="#6B8E23"><label class="label">Em Rota</label></td>
							<td width="100" bgcolor="#CFCFCF">
								<div align="center">
								<form action="cadastrarVeiculo" method="post">
									<input type="hidden" name="idVeiculo" value="${veiculo.idVeiculo}"/>
									<input type="hidden" name="acao" value="8"/>
									<input type="submit" value="Serviços"/>
								</form>
								</div>					
							</td>
							<td width="150" bgcolor="#CFCFCF">
								<div align="center">
								<form action="cadastrarVeiculo" method="post">
									<input type="hidden" name="acao" value="9"/>
									<input type="hidden" name="idVeiculo" value="${veiculo.idVeiculo}"/>
									<input type="submit" value="Finalizar Entregas"/>
								</form>
								</div>
							</td>
						</c:when>
						<c:otherwise>
							<td width="100" bgcolor="#CD5C5C"><label class="label">Sem Rota</label></td>
							<td width="100" bgcolor="#CFCFCF">
								<div align="center">									
									<input type="submit" value="Serviços" disabled="disabled"/>								
								</div>					
							</td>
							<td width="150" bgcolor="#CFCFCF">
								<div align="center">								
									<input type="submit" value="Finalizar Entregas" disabled="disabled"/>								
								</div>
							</td>
						</c:otherwise>
					</c:choose>													
				</tr>			
				</c:forEach>
				</table>
				</td>
			</tr>		
		</table>			
	</c:if>
</c:otherwise>
</c:choose>

<br /><jsp:include page="includeRodapeSI.jspf"></jsp:include>

</fieldset>
</body>
</html>
