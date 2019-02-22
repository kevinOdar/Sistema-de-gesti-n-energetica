function categoriaDetalle(id, permiteEdicion = false){
	var ruta = "/admin/categoria/"+id;
	var metodo = "GET";
	if(permiteEdicion) metodo = "PUT";
	enviarAUnModal(metodo, ruta);
}

function recuperarDatosCategoria(){
	var datos = {
		nombre 		  : valorDe("nombre"),
		consumoMinimo : valorDe("consumoMinimo"),
		consumoMaximo : valorDe("consumoMaximo"),
		cargoFijo     :	valorDe("cargoFijo"),
		cargoVariable :	valorDe("cargoVariable")
	};
	return datos;
}

function categoriaGuardar(id){
	var datos = recuperarDatosCategoria();
	var ruta = "/admin/categoria/"+id;
	var metodo = "POST";
	enviarAUnModal(metodo, ruta, datos);
}

function categoriaNueva(){
	var ruta = "/admin/categoria/nueva";
	var metodo = "GET";
	enviarAUnModal(metodo, ruta);
}

function guardarNueva(){
	var datos = recuperarDatosCategoria();
	var ruta = "/admin/categoria/nueva";
	var metodo = "POST";
	enviarAUnModal(metodo, ruta, datos);

}

function categoriaEliminar(id){
	var ruta = "/admin/categoria/"+id;
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
