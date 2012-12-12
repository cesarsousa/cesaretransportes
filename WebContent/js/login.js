function validaForm(){
	return true;
	d = document.acesso;
	if (d.usuario.value == "USUARIO"){
		alert("< O campo " + d.usuario.name + " deve ser preenchido! >");
		d.usuario.focus();
		return false;
	}
	if (d.senha.value == "SENHA"){
		alert("< O campo " + d.senha.name + " deve ser preenchido! >");
		d.senha.focus();
		return false;
	}
}

function getFocus(){
	document.getElementById("inputUsuario").focus();
}

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
