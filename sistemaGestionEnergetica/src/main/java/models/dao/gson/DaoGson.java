package models.dao.gson;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import models.dao.DataAccessObject;


public class DaoGson<T> implements DataAccessObject<T>{
		
	private BufferedReader archivo;
	private FileReader lectorDeArchivo = null;

	Type tipoT;
	Gson gson = new Gson();
	DateFormat fecha = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	Date fechaModificacion = Calendar.getInstance().getTime();        
	String fechaEnString = fecha.format(fechaModificacion);
	
	public void setTipo(Type tipo)
	{
		this.tipoT = tipo;
	}

	public void abrirArchivo(String ubicacion) 
	{
		try {
			lectorDeArchivo = new FileReader(ubicacion);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		archivo = new BufferedReader(lectorDeArchivo);
	}		
		

		@Override
		public void agregar(T objetoAPersistir) 
		{
//			Type tipoObjetos = new TypeToken<T>(){}.getType();
//			//List<Class<?>> todosLosObjetos =  gson.fromJson(archivo,tipoObjetos);
//			//Class claseDelObjeto = objetoAPersistir.getClass();
//			//Type objetoDeDominio = new TypeToken<Collection<claseDelObjeto>>(){}.getType();
//			
//			List<T> objetosPersistidos = gson.fromJson(archivo, tipoObjetos);
//			
//		
//			if (!(objetosPersistidos.contains(objetoAPersistir))) 
//			{
//				objetosPersistidos.add(objetoAPersistir);
//			}
//			try {
//				gson.toJson(objetoAPersistir, new FileWriter("categorias"+fechaEnString+".json"));
//			} catch (JsonIOException | IOException e) {
//				e.printStackTrace();
//			}
		}

		

	@Override
	public void borrar(T claseObjetoAPersistir) 
	{
//		List<T> claseObjetosAPersistir = Arrays.asList(gson.fromJson(archivo, T.class));
//		if (claseObjetosAPersistir.contains(claseObjetoAPersistir)) 
//		{
//			int ubicacion = claseObjetosAPersistir.indexOf(claseObjetoAPersistir);
//			Arrays.asList(claseObjetosAPersistir).remove(ubicacion);
////				try {
////					gson.toJson(categorias, new FileWriter("categorias"+fechaEnString+".json"));
////				} catch (JsonIOException | IOException e) {
////					e.printStackTrace();
////				}
//		}
	}
	
	@Override
	public List<T> dameTodos()
	{
		return gson.fromJson(this.archivo, createJavaUTilListParameterizedType(tipoT));
	}	
	
	static ParameterizedType createJavaUTilListParameterizedType(final Type elementType)
	{
		return (ParameterizedType) TypeToken.getParameterized(List.class, elementType).getType();
	}

	@Override
	public T buscar(int id) {
		return null;
	}

	@Override
	public void modificar(T objetoAModificar) {
		// TODO Auto-generated method stub
		
	}
}