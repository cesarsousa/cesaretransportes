<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page import="java.util.List"%>
<html>
<head>
<link rel="icon" type="image/gif" href="imagens/animated_favicon1.gif" />
<link rel="stylesheet" type="text/css" href="estilo.css">
<link rel="stylesheet" type="text/css" href="estilo2.css">
<style type="text/css">
p {
	color: #333;
}

ul li {
	text-align: left;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cesare Transportes - Buscar</title>
</head>

<body bgcolor="#cccccc">
<fieldset>

<jsp:include page="includeCabecalhoComLinks.jspf"></jsp:include>
<br />
<table class="laranjado" width="740" align="center" bgcolor="#ffffff">
	<tr>
		<td>
		<table border="0">
			<tr>
				<td width="370">
				<fieldset><legend class="legenda">Servi&ccedil;o</legend>				
				<form action="buscar">
				<input type="hidden" name="opcao" value="servico">
				<div align="center">
				<c:choose>
					<c:when test="${not empty mensagemServico}">
						<p class="vermelho">${mensagemServico}</p>
					</c:when>
					<c:otherwise>
						<p>Digite um par&acirc;metro de busca</p>
					</c:otherwise>
				</c:choose>								
				<select name="filtro" class="select">
					<option value="idServico">Id serv</option>
					<option value="idOrcamento">Id orcm</option>
					<option value="idVeiculo">Id vecl</option>
					<option value="nome">nome</option>
					<option value="origem">Origem</option>
					<option value="destino">Destino</option>									
				</select>
				<br/><br/>				
				<input type="text" name="paramBusca">
				<input type="submit" value="Consultar"></div>
				<br/>
				</form>
				</fieldset>
				</td>

				<td width="370">
				<fieldset><legend class="legenda">Or&ccedil;amento</legend>				
				<form action="buscar">
				<input type="hidden" name="opcao" value="orcamento"> 
				<div align="center">
				<c:choose>
					<c:when test="${not empty mensagemOrcamento}">
						<p class="vermelho">${mensagemOrcamento}</p>
					</c:when>
					<c:otherwise>
						<p>Digite um par&acirc;metro de busca</p>
					</c:otherwise>
				</c:choose>								
				<select name="filtro" class="select">
					<option value="codigo">Id orcm</option>
					<option value="nome">Nome</option>
					<option value="email">Email</option>
					<option value="origem">Origem</option>
					<option value="destino">Destino</option>					
				</select>
				<br/><br/>
				<input type="text" name="paramBusca">
				<input type="submit" value="Consultar"></div>
				<br/>
				</form>
				</fieldset>
				</td>				
			</tr>
		</table>
		</td>		
	</tr>	
</table>
<br />

<c:if test="${not empty resultadoServico}">
	<table width="1000" class="laranjado" align="center" bgcolor="#ffffff">
		<tr>
			<td>
			<c:choose>
				<c:when test="${empty listaDeServicos}">
				<h3>${resultadoServico}</h3>
				<p>N&atilde;o houve resultado para <b>&quot;${opcao}.${filtro}&quot;</b>
				 = <b>&quot;${paramBusca}&quot;</b><br/><br/></p>
			</c:when>
			
			<c:otherwise>
				<h3>${resultadoServico}</h3>
				<p>Resultado(s) para <b>&quot;${paramBusca}&quot;</b>, 
				em <b>&quot;${opcao}.${filtro}&quot;</b><br/></p>
				
				<ul>
				<c:forEach items="${listaDeServicos}" var="servico">
				<li>
					<a href="ProcessarAcaoServico?acao=lerServico
						&idServico=${servico.idServico}
						&paramBusca=${paramBusca}
						&opcao=${opcao}
						&filtro=${filtro}
						&buscar=true" 
					title="ler serviço">
						<img src="imagens/icone_visualizar.png" border="0">
					</a>
					
					<c:choose>
						<c:when test="${servico.deletado }">
							<i><font color="#A52A2A"><i>Cod: serv</i>${servico.idServico} -</font></i> 
						</c:when>
						<c:otherwise>
							<i>Cod: serv</i> ${servico.idServico} -
						</c:otherwise>
					</c:choose>
					
					
					<i>Cod: orcm</i>${servico.orcamento.idOrcamento} -
					<i>Cod: vecl</i>${servico.veiculo.idVeiculo} -
					<c:choose>
						<c:when test="${fn:length(servico.orcamento.cliente.nome) > 30}">
							<c:out value="${fn:substring(servico.orcamento.cliente.nome,0,27)}..."/>
						</c:when>
						<c:otherwise>
							<c:out value="${servico.orcamento.cliente.nome}"/>
						</c:otherwise>
					</c:choose>
					-
					<c:choose>
						<c:when test="${fn:length(servico.orcamento.detalheOrigem) > 30}">
							<c:out value="${fn:substring(servico.orcamento.detalheOrigem,0,27)}..."/>
						</c:when>
						<c:otherwise>
							<c:out value="${servico.orcamento.detalheOrigem}"/>
						</c:otherwise>
					</c:choose>
					-
					<c:choose>
						<c:when test="${fn:length(servico.orcamento.detalheDestino) > 30}">
							<c:out value="${fn:substring(servico.orcamento.detalheDestino,0,27)}..."/>
						</c:when>
						<c:otherwise>
							<c:out value="${servico.orcamento.detalheDestino}"/>
						</c:otherwise>
					</c:choose>
					<br/>
					<font style="color: blue;"><c:out value="dtPE: ${servico.infoDataPrevEntrega}"/></font>
					-
					<font style="color: blue;"><c:out value="dtE: ${servico.infoDataEntrega}"/></font>			
				</li>						
				</c:forEach>
				</ul>			
			</c:otherwise>
			</c:choose>
			</td>
		</tr>
	</table>
</c:if>

<c:if test="${not empty resultadoOrcamento}">
	<table width="740" class="laranjado" align="center" bgcolor="#ffffff">
		<tr>
			<td><c:choose>
			<c:when test="${empty listaDeOrcamento}">
				<h3>${resultadoOrcamento}</h3>
				<p>N&atilde;o houve resultado para <b>&quot;${opcao}.${filtro}&quot;</b>
				 = <b>&quot;${paramBusca}&quot;</b><br/><br/></p>
			</c:when>
			<c:otherwise>
				<h3>${resultadoOrcamento}</h3>
				<p>Resultado(s) para <b>&quot;${paramBusca}&quot;</b>, 
				em <b>&quot;${opcao}.${filtro}&quot;</b><br/></p>
				
				
				<ul>
				<c:forEach items="${listaDeOrcamento}" var="orcamento">			
					<li>
					<a href="processarAcaoOrcamento?acao=lerOrcamento
						&codigo=${orcamento.idOrcamento}						
						&paramBusca=${paramBusca}
						&opcao=${opcao}
						&filtro=${filtro}
						&buscar=true" 
						title="Nome: ${orcamento.cliente.nome}(${orcamento.cliente.email})">
							<img src="imagens/icone_visualizar.png" border="0"></a>
					
					<c:choose>
						<c:when test="${orcamento.deletado }">
							<i><font color="#A52A2A">Cod: ${orcamento.idOrcamento } -</font></i> 
						</c:when>
						<c:otherwise>
							<i>Cod:</i> ${orcamento.idOrcamento } -
						</c:otherwise>
					</c:choose>								
					
					<c:choose>
						<c:when test="${fn:length(orcamento.cliente.nome) > 20}">
							<c:out value="${fn:substring(orcamento.cliente.nome,0,17)}..."/>
						</c:when>
						<c:otherwise>
							<c:out value="${orcamento.cliente.nome}"/>
						</c:otherwise>
					</c:choose>
					-
					<c:choose>
						<c:when test="${fn:length(orcamento.cliente.email) > 20}">
							<c:out value="${fn:substring(orcamento.cliente.email,0,17)}..."/>
						</c:when>
						<c:otherwise>
							<c:out value="${orcamento.cliente.email}"/>
						</c:otherwise>
					</c:choose>
					- 			 
					<i>Origem: </i>
					<c:choose>
						<c:when test="${fn:length(orcamento.detalheOrigem) > 10}">
							<c:out value="${fn:substring(orcamento.detalheOrigem,0,7)}..."/>
						</c:when>
						<c:otherwise>
							<c:out value="${orcamento.detalheOrigem}"/>
						</c:otherwise>
					</c:choose>
					,
					<i>Destino: </i>
					<c:choose>
						<c:when test="${fn:length(orcamento.detalheDestino) > 10}">
							<c:out value="${fn:substring(orcamento.detalheDestino,0,7)}..."/>
						</c:when>
						<c:otherwise>
							<c:out value="${orcamento.detalheDestino}"/>
						</c:otherwise>
					</c:choose>
					<br/>
					<c:choose>
						<c:when test="${fn:length(orcamento.mensagem) > 100}">
							<font style="color: blue;"><c:out value="${fn:substring(orcamento.mensagem,0,97)}..."/></font>
						</c:when>
						<c:otherwise>
							<font style="color: blue;"><c:out value="${orcamento.mensagem}"/></font>
						</c:otherwise>
					</c:choose>
					
					
					
					</li>
				</c:forEach>
				</ul>
			</c:otherwise>
			</c:choose></td>
		</tr>
	</table>
</c:if>
<br/>
<jsp:include page="includeRodapeSI.jspf"></jsp:include>

</fieldset>
</body>
</html>