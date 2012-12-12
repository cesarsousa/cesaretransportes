
function abrirJanela(){
	var esquerda = (screen.width - 550)/2;
	var topo = (screen.height - 250)/2;
	alertWindow = window.open("telaAguarde.html","bookpixWin","width=550, height=250, top="+topo+", left="+esquerda);	
}

function fecharJanela(){
	alertWindow.close();
}

$(document).ready(function(){
	
	$('#nomeCadOrcamento').puts('NOME ou EMPRESA');
	$('#emailCadOrcamento').puts('EMAIL');
	$('#dddCadOrcamento').puts('DDD');
	$('#telefoneCadOrcamento').puts('TELEFONE');
	$('#origemCadOrcamento').puts('CIDADE DE ORIGEM');
	$('#destinoCadOrcamento').puts('CIDADE DE DESTINO');
	$('#enderecoOrigemCadOrcamento').puts('LOGRADOURO (nome da rua, numero e bairro)');
	$('#enderecoDestinoCadOrcamento').puts('LOGRADOURO (nome da rua, numero e bairro)');
	$('#pesoCadOrcamento').puts('PESO (aproximado)');
	$('#dimensaoCadOrcamento').puts('DIMENSAO (aproximada)');
	$('#mensagemCadOrcamento').puts('DIGITE SUA MENSAGEM ...');
	
	
	$('#estadoOrigem, #estadoDestino').append(
			'<option value="AC">AC</option><option value="AL">AL</option><option value="AP">AP</option>' +
			'<option value="AM">AM</option><option value="BA">BA</option><option value="CE">CE</option>' +
			'<option value="DF">DF</option><option value="ES">ES</option><option value="GO">GO</option>' +
			'<option value="MA">MA</option><option value="MT">MT</option><option value="MS">MS</option>' +
			'<option value="MG">MG</option><option value="PA">PA</option><option value="PB">PB</option>' +
			'<option value="PR">PR</option><option value="PE">PE</option><option value="PI">PI</option>' +
			'<option value="RN">RN</option><option value="RS">RS</option><option value="RJ">RJ</option>' +
			'<option value="RO">RO</option><option value="RR">RR</option><option value="SC">SC</option>' +
			'<option value="SP">SP</option><option value="SE">SE</option><option value="TO">TO</option>'
	);
	
	$('#formCadOrcamento input, textarea').focus(function(){
		$(this).addClass('destacar');
	});
	$('#formCadOrcamento input, textarea').blur(function(){
		$(this).removeClass('destacar');
	});
	
});