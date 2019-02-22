var tipoCriterio;
var actuador;
var rangoMayor;
var rangoMenor;
var igualdad;

function reglaDetalle(id)
{
	var ruta = "/cliente/regla/"+id;
	var metodo = "PUT";
	enviarAUnModal(metodo, ruta);
}

function recuperarDatosRegla2()
{
	var datos = {
		nombre 	: valorDe("nombre")
	};
	return datos;
}

function reglaGuardar(id)
{
	var datos = recuperarDatosRegla2();
	var ruta = "/cliente/regla/"+id;
	var metodo = "POST";
	enviarAUnModal(metodo, ruta, datos);
}

function reglaNueva(alias)
{
	var ruta = "/cliente/regla/nueva/"+alias;
	var metodo = "GET";
	enviarAUnModal(metodo, ruta);
}

function recuperarDatosRegla(){
	var datos = {
		nombre : valorDe("nombre"),
		idSensor : valorDe("selectSensor"),
		idDispositivo : valorDe("selectDispInteligente"),
		tipoCriterio : $("#selectCriterio").val(),
		actuador : $("#selectActuador").val(),
		rangoMayor : valorDe("rangoMayor"),
		rangoMenor : valorDe("rangoMenor"),
		valorComparacion : valorDe("valorComparacion")
	};
	return datos;
}

function guardarNueva()
{
	var datos = recuperarDatosRegla();
	var ruta = "/cliente/regla/nueva/";
	var metodo = "POST";
	enviarAUnModal(metodo, ruta, datos);
}

function reglaEliminar(id){
	var ruta = "/cliente/regla/"+id;
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

function seleccionado(){
    var criterioSeleccionado = $('#selectCriterio').val();
		switch (criterioSeleccionado) {
			case "Igualdad":
			$('#comparacion').show();
			$('#rango').hide();
			break;

			case "RangoExcluyente":
			$('#comparacion').hide();
			$('#rango').show();
			break;

			case "Mayor":
			$('#comparacion').show();
			$('#rango').hide();
			break;

			case "Menor":
			$('#comparacion').show();
			$('#rango').hide();
			break;

			//"Distinto"
			default:
			$('#comparacion').show();
			$('#rango').hide();
			break;
		}
}
