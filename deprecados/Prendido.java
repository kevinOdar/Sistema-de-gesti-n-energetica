package dispositivo;

import java.time.LocalDateTime;

public class Prendido implements EstadoDispositivo {

	private LocalDateTime horaInicio;
	private LocalDateTime horaFin ;
	private double consumo ;
	Dispositivo dispositivo ;
	int intensidad ;
	
	@Override
	public void bajaTuIntensidad()
	{
		--this.intensidad ;
	}
	
	@Override
	public void subiTuIntensidad()
	{
		++this.intensidad ;
	}
	
	public Prendido ( Dispositivo myDispositivo)
	{
		this.dispositivo=myDispositivo;
	}

	@Override
	public void apagate() {
		System.out.println("apagando "+this.dispositivo.getNombre()+"...");
//		Agregar estado actual al historial
//		this.dispositivo.() ;
		this.dispositivo.cambiarDeEstado(this.dispositivo.getEstadoApagado()) ;
	}

	public LocalDateTime getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(LocalDateTime horaInicio) {
		this.horaInicio = horaInicio;
	}

	public LocalDateTime getHoraFin() {
		return this.horaFin;
	}

	public void setHoraFin(LocalDateTime hoarFin) {
		this.horaFin = hoarFin;
	}

	public double getConsumo() {
		return consumo;
	}

	public void setConsumo(double consumo) {
		this.consumo = consumo;
	}

	@Override
	public double cuantoConsumiste(double horas) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean estasApagado() {
		return false;
	}

	@Override
	public boolean estasPrendido() {
		return true;
	}

	@Override
	public void persistirCambioDeEstado() {
		
	}

	@Override
	public void prendete() {
		
	}

	@Override
	public void ahorraEnergia() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double cuantoConsumisPorHora() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setHorasUsoDiario( double horaas) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getHorasUsoDiario() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double cuantoConsumisPorDia() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double cuantoConsumiste(LocalDateTime periodoInicial, LocalDateTime periodoFinal) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	
}//end Prendido