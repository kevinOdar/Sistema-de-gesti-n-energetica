var mapa
var transformadores = [];
var zonas = [];
var popsTrafos = [];


$(document).ready(function() {
	mapa = L.map('mapa', {
	center: [-34.606612, -58.435545],
	zoom: 11,  
	minZoom: 11,
	maxZoom: 15,
	zoomControl:true 
	});
	
	L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
	
	attribution: ''}).addTo(mapa);
	
	//poligono cdad baires
	generarPoligonoBaires();
		
	//traer transformadores
	agregarTransformadores();
	//traer zonasGeograficas
	agregarZonas() ;

	//setear refresco de trafos
	window.setInterval(refrescarTransformadores,8000);
	window.setInterval(limpiarPopUpTrafos, 6000);
}); 

function generarPoligonoBaires(){
	
	var bsas = L.polygon([
		[-34.704622, -58.461852],
	    [-34.654155, -58.528811],
	    [-34.615481, -58.531295],
	    [-34.549904, -58.500557],
	    [-34.549904, -58.500557],
	    [-34.534545, -58.464943],
	    [-34.526254, -58.457470],
	    [-34.566400, -58.390951],
	    [-34.566770, -58.380031],
	    [-34.584014, -58.354110],
	    [-34.628732, -58.333575],
	    [-34.640217, -58.358584],
	    [-34.644869, -58.356417],
	    [-34.651806, -58.369463],
	    [-34.653712, -58.369409],
	    [-34.657375, -58.374462],
	    [-34.662809, -58.395496],
	    [-34.659085, -58.413917],
	    [-34.663021, -58.424946],
	    [-34.663021, -58.424946]
	    
	],{
		 color: 'green',
		 fillColor: 'green',
	    fillOpacity: 0.1
	}).addTo(mapa);
}

function agregarZonas(){
	$.ajax({
		type 	: "GET",
		url		: "/mapa_consumo/zonas",
		dataType: "json",
		success: function(result){
			for (i in result.zonas){
				var zona = result.zonas[i];
				console.log(zona.id);
				console.log(zona.coordenadas);
				console.log("llego esta opacidad: " + zona.opacidad);
				agregarUnaZona(zona.coordenadas, zona.opacidad);
			}
		}
	});
}

function agregarUnaZona(coordenadas, opacidad){

	//[[-34.609073, -58.408263], [-34.592152, -58.420075], [-34.598961, -58.444269], [-34.619394, -58.448046]]
	var coordsPoligono = armarCoordenadas(coordenadas);
	if(coordenadas != null){
	var poligono = L.polygon(
			coordsPoligono,{
			color: 'red',
			fillColor: 'red',
			fillOpacity: 0.3
		});
	poligono.addTo(mapa);
	zonas.push(poligono);
	}
}

function armarCoordenadas(unasCoordenadas){
	var coords = [];
	
	for (i in unasCoordenadas){
		var unaCoord = [];
		unaCoord.push(unasCoordenadas[i].lat);
		unaCoord.push(unasCoordenadas[i].long);
		coords.push(unaCoord);
	}
	
	console.log("probando");
	console.log(coords);
	
	return coords;
}

function agregarTransformadores(){
	
	$.ajax({
		type	: "GET",
		url 	: "/mapa_consumo/transformadores",
		dataType: "json",
 		success : function(result){	
 			for(i in result.transformadores){
	 			var objeto = result.transformadores[i];
	 			console.log(objeto.nombre) ;
	 			agregarUnTransformador(objeto.nombre, objeto.lat, objeto.long);
 			}
 		}
	});
	
	

}

function agregarUnTransformador(nombre, lat, long){
	var marker = new L.marker([lat, long], {title: nombre, name: nombre});
	marker.bindPopup("Calculando consumo...");	
	marker.on('click', pedirConsumo);
	marker.addTo(mapa);
	
	transformadores.push(marker);
}

function pedirConsumo(e){
	var popup = e.target.getPopup();
	$.ajax({
		type	: "GET",
		url 	: "/mapa_consumo/transformadores/"+ this.options.name,
		dataType: "json",
		async: false,
		success : function(result){ 		
			
 			popup.setContent("El consumo fue de " + result.consumo + " Kw/H");
 	        
 			popup.update();
 			popsTrafos.push(popup);
 		}
	});

}

function limpiarPopUpTrafos(){
	
	popsTrafos.forEach(limpiarPopTrafos);
	
}

function limpiarPopTrafos(unPopUp){
	unPopUp.setContent("Calculando consumo...");
	unPopUp.update();
	popsTrafos.pop(unPopUp);
}

function refrescarTransformadores(){
	for (i in transformadores){
		mapa.removeControl(transformadores[i]);
		transformadores.pop(transformadores[i]);
	}
	agregarTransformadores();
}

function refrescarPagina(){
	location.reload();
}