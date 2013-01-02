
$(document).ready(function(){
	
	$('#inputUsuario').puts('USUARIO');
	
	addRemoveDestaque('#inputUsuario, #inputSenha');
	
	$('#textoContatoADM').hide();
	$('#textoRecuperarSenha').click(function(){
		$('#textoContatoADM').slideDown(500).slideUp(5000);
	});
	
	$('#passwordChecker').show();
	$('#inputSenha').hide();
	
	$('#passwordChecker').focus(function() {
	    $('#passwordChecker').hide();
	    $('#inputSenha').show();
	    $('#inputSenha').focus();
	});
	$('#inputSenha').blur(function() {
	    if($('#inputSenha').val() == '') {
	            $('#passwordChecker').show();
	            $('#inputSenha').hide();
	        }
	});
});
