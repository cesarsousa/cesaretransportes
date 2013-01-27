
$(document).ready(function(){	
	
	$('#viewMapaEmpresa').hide();
	$('#btMapaEmpresa').click(function(){
		$('#viewMapaEmpresa').slideDown(1000);
	});
	
	$('#btFecharMapa').click(function(){		
		$('#viewMapaEmpresa').slideUp(1000);
	});	
		
	if($('#inputNomeContato').val() == "") $('#inputNomeContato').puts('NOME ou EMPRESA');
	if($('#inputEmailContato').val() == "")$('#inputEmailContato').puts('EMAIL');	
	if($('#mensagemCadContato').val() == "")$('#mensagemCadContato').puts('DIGITE SUA MENSAGEM ...');
	$('#mensagemCadContato').autoResize();

	
	
	addRemoveDestaque('#inputNomeContato, #inputEmailContato, #mensagemCadContato');
	
	$('#aguardeEnviarContato').hide();
	$('#btContato').click(function(){
		$('#btEnviarContato').hide();
		$('#aguardeEnviarContato').slideDown(1000);
		$('#pgContatoPt1, #pgContatoPt2, #pgContatoPt3').addClass('opacidade25');
		$("a").click(function() { return false; }); 
	});
	
	
});