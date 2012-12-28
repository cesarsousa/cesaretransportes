
$(document).ready(function(){
	
	if($('#nomeCadOrcamento').val() == "") $('#nomeCadOrcamento').puts('NOME ou EMPRESA');
	if($('#emailCadOrcamento').val() == "")	$('#emailCadOrcamento').puts('EMAIL');
	if($('#dddCadOrcamento').val() == "") $('#dddCadOrcamento').puts('DDD');
	if($('#telefoneCadOrcamento').val() == "") $('#telefoneCadOrcamento').puts('TELEFONE');
	if($('#origemCadOrcamento').val() == "") $('#origemCadOrcamento').puts('CIDADE DE ORIGEM');
	if($('#destinoCadOrcamento').val() == "") $('#destinoCadOrcamento').puts('CIDADE DE DESTINO');
	if($('#enderecoOrigemCadOrcamento').val() == "")$('#enderecoOrigemCadOrcamento').puts('LOGRADOURO (nome da rua, numero e bairro)');
	if($('#enderecoDestinoCadOrcamento').val() == "")$('#enderecoDestinoCadOrcamento').puts('LOGRADOURO (nome da rua, numero e bairro)');
	if($('#pesoCadOrcamento').val() == "")$('#pesoCadOrcamento').puts('PESO (aproximado)');
	if($('#dimensaoCadOrcamento').val() == "")$('#dimensaoCadOrcamento').puts('DIMENSAO (aproximada)');
	if($('#mensagemCadOrcamento').val() == "")$('#mensagemCadOrcamento').puts('DIGITE SUA MENSAGEM ...');
	
	
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
	
	$('#aguardeEnviarOrcamento').hide();
	$('#btEnviarOrcamento').click(function(){
		$('#btEnviarOrcamento').hide();
		$('#aguardeEnviarOrcamento').slideDown(1000);
		$('#pgOrcamentoPt1, #pgOrcamentoPt2').addClass('opacidade25');
		$("a").click(function() { return false; }); 
	});	
	
});