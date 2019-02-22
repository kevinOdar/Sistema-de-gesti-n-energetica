function recuperarID()
{
	var datos = {
		id : valorDe("cliente-id")
	};
	return datos;
}

function buscarCliente()
{
	var ruta = "/inicio_cliente/"+valorDe("cliente-id");
	var metodo = "GET";
	enviarAUnModal(metodo, ruta);
}

function opcionesEstadoHogar(alias)
{
	var ruta = "/cliente/hogar/"+alias;
	var metodo = "GET";
	enviarAUnModal(metodo, ruta);
}

function mostrarConsumoPorPeriodo(alias)
{
  var datos = recuperarDatosDatePicker();
	var ruta = "/cliente/consumoPorPeriodo/"+alias;
	var metodo = "GET";
	enviarAUnModal(metodo, ruta, datos);
}

function ejecutarSimplex(alias)
{
  var ruta = "/cliente/simplex/"+ alias;
	var metodo = "GET";
	enviarAUnModal(metodo, ruta);
}

function mostrarMediciones(alias)
{
	var ruta = "/cliente/mediciones/"+ alias;
	var metodo = "GET";
	enviarAUnModal(metodo, ruta);
}

//esto es para enviar info atraves de los modales
$(document).on("click", ".open-Modal", function () {
var accion = $(this).data('id');
$(".modal-header #accion").val( accion );
});
