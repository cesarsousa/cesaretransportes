
$(document).ready(function(){
	
	$('textarea#txAreaLerOrcamento').autoResize();
	
	$('#txAreaLerOrcamento').focus(function(){
		$(this).addClass('destacar');
	});
	$('#txAreaLerOrcamento').blur(function(){
		$(this).removeClass('destacar');
	});
	
	
});