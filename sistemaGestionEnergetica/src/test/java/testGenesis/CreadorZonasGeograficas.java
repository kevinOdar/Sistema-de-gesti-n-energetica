package testGenesis;

import java.awt.Polygon;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.spatial4j.core.context.jts.JtsSpatialContext;
import com.spatial4j.core.context.jts.JtsSpatialContextFactory;
import com.spatial4j.core.exception.InvalidShapeException;
import com.spatial4j.core.io.jts.JtsWKTReaderShapeParser;


public class CreadorZonasGeograficas {

	private static CSVParser parser = null;
	private static List<ZonaDelCSV> zonasCSV;

	public static void main(String[] args) {

		zonasCSV = new ArrayList<>();

		File archivo = new File("src/test/resources/barrios.csv");
		try {
			parser = CSVParser.parse(archivo, StandardCharsets.UTF_8, CSVFormat.EXCEL.withHeader());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (parser != null) {
			for (CSVRecord unObjeto : parser) {
				ZonaDelCSV unaZona = new ZonaDelCSV();

				String poligono = unObjeto.get(0).toString();

				unaZona.setPoligono(poligono);
				unaZona.setBarrio(unObjeto.get(1).toString());
				unaZona.setComuna(unObjeto.get(2).toString());
				zonasCSV.add(unaZona);
			}
		}

		if (zonasCSV != null) {
			
			
			JtsWKTReaderShapeParser parser = new JtsWKTReaderShapeParser(JtsSpatialContext.GEO, new JtsSpatialContextFactory());
			
			for(ZonaDelCSV unaZona : zonasCSV) {
				
				try {
					Polygon poligon = (Polygon) parser.parse(unaZona.getPoligono());
				} catch (InvalidShapeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
