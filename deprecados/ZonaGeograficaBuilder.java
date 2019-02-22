package models.entities.zonaGeografica;

public abstract class ZonaGeograficaBuilder extends Polygon2D {
	
private static final long serialVersionUID = 1L;
	
	protected ZonaGeografica zona = new ZonaGeografica(xpoints,ypoints, npoints);
    
	   public ZonaGeografica getZonaGeografica() {
    	  return zona;
        }
       
	   public void crearZonaGeografica() {
		   zona = new ZonaGeografica(xpoints, ypoints, npoints);
	   }
	   
	   public abstract void buildCoordenadas();
	   public abstract void buildCantidadPuntos();
	   public abstract void buildTransformadores();
	   
}
