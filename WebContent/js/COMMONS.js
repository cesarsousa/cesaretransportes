
function limitarCaracteres(areaDeTexto, contador, totalCaracteres){		
	var valorDigitado = $(areaDeTexto).val();
	var totalDigitado = valorDigitado.length;		
		
	if(totalDigitado < totalCaracteres){
		var resto = totalCaracteres - totalDigitado;
		$(contador).html('').html(resto);
	}else{
		$(contador).html('').html('0');
		$(areaDeTexto).val(valorDigitado.substring(0, totalCaracteres-2));
	}	
}

$('#mensagemCadOrcamento').keyup(function() {
	
	var valorDigitado = $('#mensagemCadOrcamento').val();
	var totalDigitado = valorDigitado.length;		
		
	if(totalDigitado < 500){
		var resto = 500 - totalDigitado;
		$('#contadorCaracterOrcamento').html('').html(resto);
	}else{
		$('#contadorCaracterOrcamento').html('').html('0');
		$('#mensagemCadOrcamento').val(valorDigitado.substring(0, 500));
	}	  
	  
});


function addRemoveDestaque(elemento) {
	$(elemento).focus(function() {
		$(this).addClass('destacar');
	});
	$(elemento).blur(function() {
		$(this).removeClass('destacar');
	});
}

$(document).ready(function() {
	
	$('#back-top').hide();
	$(function () {
	       $(window).scroll(function () {
	           if ($(this).scrollTop() > 100) {
	               $('#back-top').fadeIn();
	           } else {
	               $('#back-top').fadeOut();
	           }
	       });
	       
	       $('#back-top a').click(function () {
				$('body,html').animate({
					scrollTop: 0
				}, 800);
				return false;
	       });
	});
	
	 $('#fechar').click(function() {
		 $('#infoMensagem').slideUp(1000);
     });
	
});