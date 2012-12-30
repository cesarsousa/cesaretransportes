
$(document).ready(function(){	
	
	$('#inputUsuario').puts('USUARIO');
	
	addRemoveDestaque('#inputUsuario, #inputSenha');
	
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
