<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="br.com.cesaretransportes.modelo.Cliente" %>
<%
	Cliente cliente = (Cliente) request.getSession().getAttribute("cliente");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<jsp:include page="layout/library.jspf" />

<title>Cesare Transporte</title>
</head>

<body id="paginaContato" bgcolor="#E8E8E8">
	
	<c:if test="${empty empresa}">
		<c:if test="${empty primeiraExecucao}">
			<c:redirect url="EmpresaServlet"></c:redirect>
		</c:if>
	</c:if>

	<div id="wrap">
 
		<div id="main">
	 	<div id="pgContatoPt1">

		<jsp:include page="layout/header.jspf" />

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
		<table width="800" align="center">
			<tr>
				<td style="padding-top: 10px"><span class="tituloPagina">Contato</span></td>
			</tr>
		</table>
		<jsp:useBean id="data" class="java.util.Date" />
		<h3><fmt:formatDate value="${data}" dateStyle="full" timeZone="America/Sao_Paulo" /></h3>
		
		<br />
	
		<c:if test="${not empty erroContato}">
			<div align="center">
				<div class="tableErroContato">
					<div align="center">
						<span class="erro"><c:out value="Verifique campos obrigatórios não preenchidos" /></span>
					</div>
				</div>
			</div>
			<br />
		</c:if>

		<table width="800px" align="center">
			<tr>
				<td>
					<div id="cardIndex">
						<br />
						<table bgcolor="#ff9900" width="100%" cellpadding="10">
							<tr align="center">
								<td valign="middle"><font
									style="color: white; font-weight: bold; font-family: sans-serif;">${empresa.detalheEndereco}</font>
	
								</td>
								<c:if test="${empresa.mostrarMapa}">
									<td><img id="btMapaEmpresa" src="imagens/iconeMapa.png"
										align="right" width="40px" height="40px" class="ponteiro"
										alt="Ver no mapa" title="Ver no mapa" /></td>
								</c:if>
							</tr>
						</table>
						<br />
	
						<div align="center">
							<div align="center">
								<div id="viewMapaEmpresa">
									<c:if test="${empresa.mostrarMapa}">${empresa.localizacao }
							        	<div align="right" style="padding: 10px;">
											<img id="btFecharMapa" src="imagens/iconeFecharMapa.png" width="40px" height="40px" class="ponteiro" alt="Fechar Mapa" title="Fechar Mapa" />
										</div>
									</c:if>
								</div>
							</div>
						</div>
					</div>
				</td>
			</tr>
		</table>
		<br />
		<table width="800px" align="center">
			<tr>
				<td>
					<div id="cardIndex" align="center">
						<p>
							<span class="legenda">Mande seu email de cr&iacute;tica,
								reclama&ccedil;&atilde;o ou sugest&atilde;o !</span>
						</p>
						<form action="enviarContato" name="contato" method="post">
							<input type="hidden" name="data" value=${ dataDoSistema } />
	
							<p>
								<span class="erro"><c:out value="${msgNome}" /></span><br /> <input
									id="inputNomeContato" class="input70" type="text" name="nome"
									value="${nome}" maxlength="50" />
							</p>
	
							<p>
								<span class="erro"><c:out value="${msgEmail}" /></span><br /> <input
									id="inputEmailContato" class="input70" type="text" name="email"
									value="${email}" maxlength="50" />
							</p>					
	
							<p>
								<span class="erro"><c:out value="${msgMensagem}" /></span><br />
								<textarea id="mensagemCadContato" name="mensagem" rows="10"
									class="mensagemContato">${mensagem}</textarea>
							</p>
	
							<div id="btEnviarContato">
								<p>
									<input id="btContato" name="Submit" type="submit"
										value="Enviar Email" class="button" />
								</p>
							</div>
						</form>
					</div>
				</td>
			</tr>
		</table>	
		</div>
		
		<div id="espacador"></div>
		<br />
		<div id="aguardeEnviarContato" >
			<div align="center">
				<table class="telaAguarde">
					<tr>
						<td>
							<h3>Seu email esta sendo enviado. Por favor aguarde um instante!</h3>
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
		
		<div id="pgContatoPt2">
		<table bgcolor="#ffa54f" width="100%">
			<tr>
				<td>
	
					<div align="center">
						<h3>
							<font style="color: white;">Ou utilize um dos nossos canais
								de comunica&ccedil;&atilde;o !</font>
						</h3>
	
	
						<h1>
							<img alt="Mande-nos um Gmail!" src="imagens/gmail_icone.png"
								align="middle" />
							&nbsp;&nbsp;${empresa.email}&nbsp;&nbsp;&nbsp;&nbsp; <img
								alt="Me Add!" src="imagens/msn_icone.png" align="middle" />
							&nbsp;&nbsp;${empresa.msn}
						</h1>
	
						<h1>
							<font size="2px">
								<img alt="Ligue para nós" src="imagens/operadoras_icone.png" align="middle" />
								<img alt="Este é o nosso grupo!" src="imagens/nextel_icone.png" align="middle" />
								
								&nbsp;&nbsp;${empresa.telefone1}&nbsp;&nbsp;${empresa.telefone2}
								&nbsp;&nbsp;${empresa.telefone3}
							</font>
						</h1>
					</div>
				</td>
			</tr>
		</table>	
		</div>		
		</div> 
	</div>
	
	<div id="pgContatoPt3">
	<div id="footer">
	<jsp:include page="layout/footer.jspf" />
	</div>	
	</div>
	
</body>
</html>
