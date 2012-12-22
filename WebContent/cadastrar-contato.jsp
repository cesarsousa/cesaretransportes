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
		<span class="erro"><c:out value="Verifique campos obrigatórios não preenchidos"/></span>
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
    		<td>
    			<font style="color: white; font-weight: bold; font-family: sans-serif;">${empresa.detalheEndereco}</font>	
    		</td>
    		</tr>
    		</table>
    		<br/>    		
    		
    		<div align="center">
        	
        	<!-- <iframe 
        	width="750" 
        	height="500" 
        	frameborder="0" 
        	scrolling="no" 
        	marginheight="0" 
        	marginwidth="0" 
        	src="http://www.google.com/maps?f=q&amp;source=s_q&amp;hl=pt-BR&amp;geocode=&amp;q=rua+alzira+miranda+koerbel+140,+afonso+pena,+s%C3%A3o+jos%C3%A9+dos+pinhais+-+PR&amp;aq=&amp;sll=37.0625,-95.677068&amp;sspn=38.41771,86.572266&amp;vpsrc=0&amp;ie=UTF8&amp;hq=&amp;hnear=R.+Alzira+Miranda+Koerbel,+140+-+Avia%C3%A7%C3%A3o,+S%C3%A3o+Jos%C3%A9+dos+Pinhais+-+Paran%C3%A1,+83045-390,+Brasil&amp;ll=-25.518141,-49.178678&amp;spn=0.010709,0.021136&amp;t=h&amp;z=14&amp;output=embed">
        	</iframe>
        	<br />
        	<small><b>
        	<a href="http://www.google.com/maps?f=q&amp;source=embed&amp;hl=pt-BR&amp;geocode=&amp;q=rua+alzira+miranda+koerbel+140,+afonso+pena,+s%C3%A3o+jos%C3%A9+dos+pinhais+-+PR&amp;aq=&amp;sll=37.0625,-95.677068&amp;sspn=38.41771,86.572266&amp;vpsrc=0&amp;ie=UTF8&amp;hq=&amp;hnear=R.+Alzira+Miranda+Koerbel,+140+-+Avia%C3%A7%C3%A3o,+S%C3%A3o+Jos%C3%A9+dos+Pinhais+-+Paran%C3%A1,+83045-390,+Brasil&amp;ll=-25.518141,-49.178678&amp;spn=0.010709,0.021136&amp;t=h&amp;z=14" style="color:#0000FF;text-align:left"><font color="#FF6600">Exibir mapa ampliado</font></a>
        	</b></small>
        	-->        	
        	
        	<iframe 
        		width="750" 
        		height="500" 
        		frameborder="0" 
        		scrolling="no" 
        		marginheight="0" 
        		marginwidth="0" 
        		src="https://maps.google.com.br/maps?f=q&amp;source=s_q&amp;hl=pt-BR&amp;geocode=&amp;q=rua+padre+anchieta+142,+centro,+niteroi+rio+de+janeiro&amp;aq=&amp;sll=-22.066441,-42.924029&amp;sspn=2.830258,5.020752&amp;t=h&amp;ie=UTF8&amp;hq=&amp;hnear=R.+Padre+Anchieta,+142+-+Morro+do+Estado,+Niter%C3%B3i+-+Rio+de+Janeiro,+24210-050&amp;z=14&amp;ll=-22.897298,-43.120223&amp;output=embed">
        		</iframe>
        		<br />
        		<small>
        		<a href="https://maps.google.com.br/maps?f=q&amp;source=embed&amp;hl=pt-BR&amp;geocode=&amp;q=rua+padre+anchieta+142,+centro,+niteroi+rio+de+janeiro&amp;aq=&amp;sll=-22.066441,-42.924029&amp;sspn=2.830258,5.020752&amp;t=h&amp;ie=UTF8&amp;hq=&amp;hnear=R.+Padre+Anchieta,+142+-+Morro+do+Estado,+Niter%C3%B3i+-+Rio+de+Janeiro,+24210-050&amp;z=14&amp;ll=-22.897298,-43.120223" style="color:#ff6600;text-align:left">Exibir mapa ampliado</a>
        		</small>    		
    		
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
			<img alt="Ligue para nós" src="imagens/operadoras_icone.png" align="middle" />
			<img alt="Este é o nosso grupo!" src="imagens/nextel_icone.png" align="middle" /> 
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
