
function abrirJanela(){
	var esquerda = (screen.width - 550)/2;
	var topo = (screen.height - 250)/2;
	alertWindow = window.open("telaAguarde.html","bookpixWin","width=550, height=250, top="+topo+", left="+esquerda);	
}

function fecharJanela(){
	alertWindow.close();
}

$(document).ready(function(){
		
	$('#inputNomeContato').puts('NOME ou EMPRESA');
	$('#inputEmailContato').puts('EMAIL');
	$('#mensagemCadContato').puts('DIGITE SUA MENSAGEM ...');
	
	addRemoveDestaque('#inputNomeContato, #inputEmailContato, #mensagemCadContato');
	
	
});