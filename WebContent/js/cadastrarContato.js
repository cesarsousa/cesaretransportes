
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
	
	addRemoveDestaque('#inputNomeContato, #inputEmailContato, #mensagemCadContato');
	
	
});