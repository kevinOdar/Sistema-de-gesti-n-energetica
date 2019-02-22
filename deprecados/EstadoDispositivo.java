package dispositivo;

import java.time.LocalDateTime;

public interface EstadoDispositivo {

	public void apagate();
	
	public void ahorraEnergia() ;

	public void bajaTuIntensidad() ;

	public double cuantoConsumisPorHora() ;
	
	public void setHorasUsoDiario( double horas ) ;
	
	public double getHorasUsoDiario() ;
	
	public void setConsumo(double consumo) ;
	
	public double cuantoConsumisPorDia() ;

	public double cuantoConsumiste(double horas);

	public double cuantoConsumiste(LocalDateTime periodoInicial, LocalDateTime periodoFinal) ;

	public boolean estasApagado() ;

	public boolean estasPrendido() ;

	public void persistirCambioDeEstado() ;

	public void prendete() ;

	public void subiTuIntensidad() ;
		
}//end Estado