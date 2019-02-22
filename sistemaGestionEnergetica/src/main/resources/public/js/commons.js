function valorDe(unaVariableDelDocumento){
	return $("#"+unaVariableDelDocumento+"").val();
}

function modal_show(unModal){
	$("#"+unModal+"").modal('show');
}

function agregarA(unaVariableDelDocumento, unValor){
	$("#"+unaVariableDelDocumento+"").append(unValor);
}

function vaciar(unaVariableDelDocumento){
	$("#"+unaVariableDelDocumento+"").empty();
}

function showInModal(unModal, unContenido){
	vaciar(unModal);
	agregarA(unModal,unContenido);
	modal_show(unModal);
}

function enviarAUnModal(metodo, ruta, datos = {}){
	$.ajax({
		type	: metodo,
		url 	: ruta,
		dataType: "html",
		data 	: datos,
 		success : function(result){
    		showInModal("modal",result);
		}
	});
}

function sleep (time) {
 return new Promise((resolve) => setTimeout(resolve, time));
}

function cerrarModal(){
	
	$('#modal').modal('hide');
}

function refrescar(){
	sleep(4000).then(() => {location.reload();
	});
}