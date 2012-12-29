
$(document).ready(function(){
	
	$('textarea#txAreaLerOrcamento').autoResize();
	$('textarea#txAreaLerOrcamento').puts("DIGITE A RESPOSTA AO CLIENTE");
	
	$('#txAreaLerOrcamento').focus(function(){
		$(this).addClass('destacar');
	});
	$('#txAreaLerOrcamento').blur(function(){
		$(this).removeClass('destacar');
	});
	
	$('#aguardeEnviarResposta').hide();
	$('#btEnviarRepostaOrcamento').click(function(){
		$('#btEnviarRepostaOrcamento').hide();
		$('#aguardeEnviarResposta').slideDown(1000);
		$('#pgLerOrcamentoPt1, #pgLerOrcamentoPt2').addClass('opacidade25');
		$("a").click(function() { return false; }); 
	});
		
});