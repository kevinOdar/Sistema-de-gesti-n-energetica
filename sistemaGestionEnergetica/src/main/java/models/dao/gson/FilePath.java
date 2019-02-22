package models.dao.gson;

/**
 * @author Gast�n Di Filippo
 * @version 1.0
 * @created 13-Apr-2018 8:57:52 PM
 */

import java.nio.file.Paths;

public class FilePath {
	
	public String filePath ;
	private static FilePath instance ;
	
	private FilePath ()
	{
		this.filePath = Paths.get(".").toAbsolutePath().normalize().toString(); 
	}
		
	public static FilePath getInstance()
	{
		if ( instance == null )
		{
			instance = new FilePath ();
		}
		return instance ;
	}
	
	public String getJsonPath( Object objectType )
	{
		return this.filePath + "\\"+ objectType.toString() +".json" ;
	}
	
}
