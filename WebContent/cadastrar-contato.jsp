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

<body bgcolor="#E8E8E8" onunload="fecharJanela();">

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


<c:if test="${empty empresa}">
	<c:if test="${empty primeiraExecucao}">
		<c:redirect url="EmpresaServlet"></c:redirect>
	</c:if>	
</c:if>

<br/>
<table width="760" align="center">
	<tr>
		<td><span class="tituloPagina">Contato</span></td>
	</tr>
</table>

<jsp:useBean id="data" class="java.util.Date"/>
<h3><fmt:formatDate value="${data}" dateStyle="full" timeZone="America/Sao_Paulo"/></h3>

<c:if test="${not empty erroContato}">
	<div align="center">
	<div class="tableErroContato">
		<div align="center">
		<span class="erro"><c:out value="Verifique campos obrigat�rios n�o preenchidos"/></span>
		</div>
	</div>
	</div>
	<br/>
</c:if>

<table width="800px" align="center">
    <tr>
    	<td>
    	<div id="cardIndex">
    		<br/>
    		<table bgcolor="#ff9900" width="100%" cellpadding="10">
    		<tr align="center">
    		<td valign="middle">
    			<font style="color: white; font-weight: bold; font-family: sans-serif;">${empresa.detalheEndereco}</font>
    			
    		</td>
    		<c:if test="${empresa.mostrarMapa}">
   			<td>
   				<img id="btMapaEmpresa" src="imagens/iconeMapa.png" align="right" width="40px" height="40px" class="ponteiro" alt="Ver no mapa" title="Ver no mapa"/>	
			</td>    		
    		</c:if>    		
    		</tr>
    		</table>
    		<br/>    		
    		
    		<div align="center">        	
	        	<div align="center">
		    		<div id="viewMapaEmpresa">        	
			        	<c:if test="${empresa.mostrarMapa}">
			        	${empresa.localizacao }
			        	<div align="right" style="padding: 10px;">
			        	<img id="btFecharMapa" src="imagens/iconeFecharMapa.png" width="40px" height="40px" class="ponteiro" alt="Fechar Mapa" title="Fechar Mapa"/>        	
			        	</div>
			        	</c:if>
		        	</div>        	
	    		</div>        	
    		</div>
    		
    	</div>   		    	   		
    	</td>
    </tr>
</table>
<br/>
<table width="800px" align="center">    
    <tr>
        <td>
        <div id="cardIndex" align="center">
        <p>
        <span class="legenda">Mande seu email de cr&iacute;tica, reclama&ccedil;&atilde;o ou sugest&atilde;o !</span>
        </p>
        <form action="enviarContato" name="contato" method="post">                
                <input  type="hidden" name="data" value=${ dataDoSistema }/>   
                
                	<p><span class="erro"><c:out value="${msgNome}"/></span><br />                
                	<input id="inputNomeContato" class="input70" type="text" name="nome" value="${nome}" maxlength="50"/></p>               
                
                	<p><span class="erro"><c:out value="${msgEmail}"/></span><br />                
                	<input id="inputEmailContato" class="input70" type="text" name="email" value="${email}" maxlength="50"/></p>
                
               <%--  <c:if test="${not empty cliente}">
                	<p><span class="erro"><c:out value="${msgNome}"/></span><br />                
                	<input class="input70" type="text" name="nome" value="${cliente.nome}" readonly="readonly"/></p>               
                
                	<p><span class="erro"><c:out value="${msgEmail}"/></span><br />                
                	<input class="input70" type="text" name="email" value="${cliente.email}" readonly="readonly"/></p>
                </c:if> --%>
                
                <p><span class="erro"><c:out value="${msgMensagem}"/></span><br />               
                <textarea id="mensagemCadContato" name="mensagem" rows="10" class="mensagemContato">${mensagem}</textarea></p>
                           
                <p>
                <input name="Submit" type="submit" value="Enviar Email" class="button" onclick="abrirJanela();"/>
                </p>
                </form>
        </div>
        </td>
	</tr>
</table>       
<br/>
<table bgcolor="#ffa54f" width="100%">
      <tr>
        <td>
              
       	<div align="center">
			<h3><font style="color: white;">Ou utilize um dos nossos canais de comunica&ccedil;&atilde;o !</font></h3>
		          
								
		<h1>
			<img alt="Mande-nos um Gmail!" src="imagens/gmail_icone.png" align="middle" />
			&nbsp;&nbsp;${empresa.email}&nbsp;&nbsp;&nbsp;&nbsp;
			<img alt="Me Add!" src="imagens/msn_icone.png" align="middle" />
			&nbsp;&nbsp;${empresa.msn}
		</h1>

		<h1>
			<font size="2px">
			<img alt="Ligue para n�s" src="imagens/operadoras_icone.png" align="middle" />
			<img alt="Este � o nosso grupo!" src="imagens/nextel_icone.png" align="middle" /> 
			&nbsp;&nbsp;${empresa.telefone1}&nbsp;&nbsp;${empresa.telefone2} &nbsp;&nbsp;${empresa.telefone3}
			</font>
		</h1>
		</div>						
		</td>
	</tr>
</table>

<jsp:include page="layout/footer.jspf" />

</body>
</html>
