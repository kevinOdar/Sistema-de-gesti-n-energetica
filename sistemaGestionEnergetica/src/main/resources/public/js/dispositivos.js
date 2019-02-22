function dispositivoDetalle(id)
{
	var ruta = "/cliente/dispositivo/"+id;
	var metodo = "PUT";
	enviarAUnModal(metodo, ruta);
}

function recuperarDatosDispositivoInteligente()
{
	var datos = {
				caracteristicaInt  :	valorDe("caracteristica"),
				modoAutomatico 	:	valorDe("modoAutomatico"),
				intensidad 			:	valorDe("intensidad")
	};
	return datos;
}

function recuperarDatosDispositivoStandard()
{
	var datos = {
			caracteristicaStan  :	valorDe("caracteristica"),
			horasUsoDiario  :	valorDe("horasUsoDiario"),
			consumoPorHora 	:	valorDe("consumoPorHora")
	};
	return datos;
}

function dispositivoInteligenteGuardar(id)
{
	var datos = recuperarDatosDispositivoInteligente();
	var ruta = "/cliente/dispositivo/"+id;
	var metodo = "POST";
	enviarAUnModal(metodo, ruta, datos);
}

function dispositivoStandardGuardar(id)
{
	var datos = recuperarDatosDispositivoStandard();
	var ruta = "/cliente/dispositivo/"+id;
	var metodo = "POST";
	enviarAUnModal(metodo, ruta, datos);
}

function dispositivoNuevo(esInteligente, aliasCliente)
{
	var ruta = "/cliente/dispositivoStandard/nuevo/"+aliasCliente;
	if(esInteligente) ruta = "/cliente/dispositivoInteligente/nuevo/"+aliasCliente;
	var metodo = "GET";
	enviarAUnModal(metodo, ruta);
}

function guardarNuevoStandard(aliasCliente){
	var datos = recuperarDatosDispositivoStandard();
	var ruta = "/cliente/dispositivoStandard/nuevo/"+aliasCliente;
	var metodo = "POST";
	enviarAUnModal(metodo, ruta, datos);
}

function guardarNuevoInteligente(aliasCliente){
	var datos = recuperarDatosDispositivoInteligente();
	var ruta = "/cliente/dispositivoInteligente/nuevo/"+aliasCliente;
	var metodo = "POST";
	enviarAUnModal(metodo, ruta, datos);
}

function dispositivoEliminar(id){
	var ruta = "/cliente/dispositivo/"+id;
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
