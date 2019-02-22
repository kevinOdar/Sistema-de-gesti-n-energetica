function recuperarDatosLogueo(){
	var datos = {
			alias		: valorDe("alias"),
			contrasenia : valorDe("contrasenia"),
		};
		return datos;
}

function ingresoUsuario(){
	var ruta = "/login/usuario";
	var metodo = "GET";
	enviarAUnModal(metodo, ruta);
}

function loguearUsuario(){
	var datos = recuperarDatosLogueo();
	var alias = valorDe("alias");
	var ruta = "/login/usuario/"+valorDe("alias");
	var metodo = "GET";
	$.ajax({
		type	: metodo,
		url 	: ruta,
		dataType: "html",
		data 	: datos,
 		success : function(result){
 			showInModal("modal",result);
 			//sleep(4000).then(() => {cerrarModal();});
 			//window.location.href = "/admin/inicio_admin/"+ alias ;
 		},
 		error: function(jqXHR, textStatus, errorThrown){
				showInModal("modal",jqXHR.responseText);
 		}
		
	});
}

function irA(destino){
	sleep(4000).then(() => {
		cerrarModal();
	});

	window.location.href = destino;
}

function loguearAdmin(){
	
	var datos = recuperarDatosLogueo();
	var alias = valorDe("alias");
	var ruta = "/login/admin/"+valorDe("alias");
	var metodo = "GET";
	$.ajax({
		type	: metodo,
		url 	: ruta,
		dataType: "html",
		data 	: datos,
 		success : function(result){
 			
 			showInModal("modal",result);
 			
 			sleep(4000).then(() => {cerrarModal();});
 			
 			window.location.href = "/admin/inicio_admin/"+ alias ;
 		},
 		error: function(jqXHR, textStatus, errorThrown){
			//var mensajeError = "Usuario y/o contraseña inválidos\n"+textStatus+"\n"+errorThrown;
			//alert(mensajeError);
    		showInModal("modal",jqXHR.responseText);

	}
		
	});
}

function loguearCliente(){
	
	var datos = recuperarDatosLogueo();
	var alias = valorDe("alias");
	var ruta = "/login/cliente/"+valorDe("alias");
	var metodo = "GET";
	$.ajax({
		type	: metodo,
		url 	: ruta,
		dataType: "html",
		data 	: datos,
 		success : function(result){
 			
 			showInModal("modal",result);
 			
 			sleep(4000).then(() => {cerrarModal();});
 			
 			window.location.href = "cliente/inicio_cliente/"+ alias ;
 		},
		error:  function(jqXHR, textStatus, errorThrown){
//			var mensajeError = "Error al eliminar la categoria:\n"+textStatus+"\n"+errorThrown;
    		showInModal("modal",jqXHR.responseText);
		}
	});
		
}

//para mostrar la contrasenia
function mostrarContrasenia() {
    var x = document.getElementById("contrasenia");
    if (x.type === "password") {
        x.type = "text";
    } else {
        x.type = "password";
    }
}

//para que te tome el enter
$(document).keypress(function(e){
	if(e.which == 13){
		loguearUsuario();
	}
});
