function caracteristicaDetalle(id, permiteEdicion = false){
	var ruta = "/admin/caracteristica/"+id;
	var metodo = "GET";
	if(permiteEdicion) metodo = "PUT";
	enviarAUnModal(metodo, ruta);
}

function recuperarDatosCaracteristica(){
	var datos = {
		nombre				:	valorDe("nombre"),
		coeficienteConsumo  :	valorDe("coeficienteConsumo"),
		mayorIgual 			:	valorDe("mayorIgual"),
		menorIgual 			:	valorDe("menorIgual"),
		esDeBajoConsumo     :	valorDe("selectDeBajoConsumo"),
		esInteligente 		:	valorDe("selectInteligente")
	};
	return datos;
}

function caracteristicaGuardar(id){
	var datos = recuperarDatosCaracteristica();
	var ruta = "/admin/caracteristica/"+id;
	var metodo = "POST";
	enviarAUnModal(metodo, ruta, datos);
}

function caracteristicaNueva(){
	var ruta = "/admin/caracteristica/nueva";
	var metodo = "GET";
	enviarAUnModal(metodo, ruta);
}

function guardarNueva(){
	var datos = recuperarDatosCaracteristica();
	var ruta = "/admin/caracteristica/nueva";
	var metodo = "POST";
	enviarAUnModal(metodo, ruta, datos);
    
}

function caracteristicaEliminar(id){
	var ruta = "/admin/caracteristica/"+id;
	var metodo = "DELETE";
	$.ajax({
		type	: metodo,
		url 	: ruta,
		dataType: "html",
 		success : function(result){
    		showInModal("modal",result);
    		sleep(6000).then(() => {location.reload();
    		});
		},
		error : function(jqXHR, textStatus, errorThrown){
    		showInModal("modal",jqXHR.responseText);
		}
	});	
}