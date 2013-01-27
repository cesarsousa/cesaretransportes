
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