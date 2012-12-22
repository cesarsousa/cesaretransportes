
$(document).ready(function(){
		
	if($('#inputNomeContato').val() == "") $('#inputNomeContato').puts('NOME ou EMPRESA');
	if($('#inputEmailContato').val() == "")$('#inputEmailContato').puts('EMAIL');
	if($('#mensagemCadContato').val() == "")$('#mensagemCadContato').puts('DIGITE SUA MENSAGEM ...');
	
	addRemoveDestaque('#inputNomeContato, #inputEmailContato, #mensagemCadContato');
	
	
});