
function abrirJanela(){
	var esquerda = (screen.width - 550)/2;
	var topo = (screen.height - 250)/2;
	alertWindow = window.open("telaAguarde.html","bookpixWin","width=550, height=250, top="+topo+", left="+esquerda);	
}

function fecharJanela(){
	alertWindow.close();
}

function getFocus(){
	document.getElementById("usuario").focus();
}

$(document).ready(function(){
	
	$('#senhaCadClienteChecker').show();
    $('#senhaCadCliente').hide();
    $('#senhaCadClienteChecker').focus(function() {
            $('#senhaCadClienteChecker').hide();
            $('#senhaCadCliente').show();
            $('#senhaCadCliente').focus();
    });
    $('#senhaCadCliente').blur(function() {
            if($('#senhaCadCliente').val() == '') {
                    $('#senhaCadClienteChecker').show();
                    $('#senhaCadCliente').hide();
            }
    });
	
    $('#senha2CadClienteChecker').show();
    $('#senha2CadCliente').hide();
    $('#senha2CadClienteChecker').focus(function() {
            $('#senha2CadClienteChecker').hide();
            $('#senha2CadCliente').show();
            $('#senha2CadCliente').focus();
    });
    $('#senha2CadCliente').blur(function() {
            if($('#senha2CadCliente').val() == '') {
                    $('#senha2CadClienteChecker').show();
                    $('#senha2CadCliente').hide();
            }
    });
    
    addRemoveDestaque('#usuarioCadCliente, #senhaCadCliente, #senha2CadCliente, #nomeCadCliente');
    addRemoveDestaque('#numDocCadCliente, #dddCadCliente, #telefoneCadCliente, #complementoCadCliente');
    addRemoveDestaque('#numeroLogradouroCadCliente, #logradouroCadCliente, #bairroCadCliente, #cidadeCadCliente');
    
    
	$('#usuarioCadCliente').puts('USUARIO (EMAIL)');
	$('#nomeCadCliente').puts('NOME OU EMPRESA');	
	$('#numDocCadCliente').puts('NUMERO DO DOCUMENTO ( somente numeros )');
	$('#dddCadCliente').puts('DDD');
	$('#telefoneCadCliente').puts('NUMERO DO TELEFONE ( somente numeros )');
	$('#complementoCadCliente').puts('COMPLEMENTO DO TELEFONE');
	$('#logradouroCadCliente').puts('NOME DA RUA');
	$('#numeroLogradouroCadCliente').puts('NUMERO');
	$('#bairroCadCliente').puts('BAIRRO');
	$('#cidadeCadCliente').puts('CIDADE');
	
	$('#estadosCadCliente').append(
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
	
	
});