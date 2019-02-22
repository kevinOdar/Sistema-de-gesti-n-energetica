function refrescar(elemento){
	var ruta = "/admin/reportes/"+elemento;
	var metodo = "GET";
	
	$.ajax({
		type	: metodo,
		url 	: ruta,
		dataType: "html",
 		success : function(result){
 			$("#caja-"+elemento+"").empty();
 			$("#caja-"+elemento+"").append(result);
 			$("#caja-"+elemento+"").show();
 		}
	});
}	


function elegirPeriodo(elemento){
	var ruta = "/admin/reportes/periodo/"+elemento;
	var metodo = "GET";
	enviarAUnModal(metodo, ruta);
}

function setPeriodo(unElemento){
}

//esto es para enviar info atraves de los modales
$(document).on("click", ".open-Modal", function () {
var accion = $(this).data('id');
$(".modal-header #accion").val( accion );
});

//mostrar tablas a pedido -- capaz lo hago
//function mostrarTabla(elemento){
//	   if(elem.value == 1)
//	      document.getElementById('hidden_div').style.display = "block";
//}

function showCliente() {
	document.getElementById('cliente-refrescar').style.display = 'block';
	document.getElementById('caja-Hogares').style.display = 'block';
	
	document.getElementById('dispositivo-refrescar').style.display = 'none';
	document.getElementById('caja-Dispositivos').style.display = 'none';
	
	document.getElementById('transformador-refrescar').style.display = 'none';
	document.getElementById('caja-Transformadores').style.display = 'none';
}

function showDispositivo() {
	document.getElementById('cliente-refrescar').style.display = 'none';
	document.getElementById('caja-Hogares').style.display = 'none';
	
	document.getElementById('dispositivo-refrescar').style.display = 'block';
	document.getElementById('caja-Dispositivos').style.display = 'block';
	
	document.getElementById('transformador-refrescar').style.display = 'none';
	document.getElementById('caja-Transformadores').style.display = 'none';
}

function showTransformador() {
	document.getElementById('cliente-refrescar').style.display = 'none';
	document.getElementById('caja-Hogares').style.display = 'none';
	
	document.getElementById('dispositivo-refrescar').style.display = 'none';
	document.getElementById('caja-Dispositivos').style.display = 'none';
	
	document.getElementById('transformador-refrescar').style.display = 'block';
	document.getElementById('caja-Transformadores').style.display = 'block';
}