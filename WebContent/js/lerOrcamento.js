
$(document).ready(function(){
	
	$('textarea#txAreaLerOrcamento').autoResize();
	$('textarea#txAreaLerOrcamento').puts("DIGITE A RESPOSTA AO CLIENTE");
	
	$('#txAreaLerOrcamento').focus(function(){
		$(this).addClass('destacar');
	});
	$('#txAreaLerOrcamento').blur(function(){
		$(this).removeClass('destacar');
	});
		
});