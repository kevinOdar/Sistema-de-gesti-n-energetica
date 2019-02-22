package testsSobrePoligono;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import models.entities.zonaGeografica.Polygon2D;

public class TestCreacionPoligono {
	
	@Before
	public void init()
	{
		
		ArrayList <Point2D> points = new ArrayList<Point2D>() ;
		
		Point2D point1 = new Point2D.Double(-34.697878, -58.468897) ;
		points.add(point1) ;
		Point2D point2 = new Point2D.Double(-34.686919, -58.486813) ;
		points.add(point2) ;
		Point2D point3 = new Point2D.Double(-34.651905, -58.530758) ;
		points.add(point3) ;
		
		
		Point2D[] pointsVector = new Point2D[points.size()] ;
		
		for ( int i = 0 ; i < points.size() ; i++ )
		{
			 pointsVector[i] = points.get(i) ;
		}
		Polygon2D poligono = new Polygon2D(pointsVector)  ;
		System.out.println("Punto interior:"+poligono.getPuntoInterior());
		System.out.println(poligono);
		System.out.println(poligono.getBounds() );
	}
	
	@Test
	public void test()
	{
		System.out.println("piola");
	}
	
}
