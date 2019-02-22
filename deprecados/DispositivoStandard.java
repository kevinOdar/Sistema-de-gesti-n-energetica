package dispositivo;

import java.time.LocalDateTime;

public class DispositivoStandard implements EstadoDispositivo {

	private double consumo;
	private double horasUsoDiario;
	private LocalDateTime horaInicio ;
	private LocalDateTime horaFin ;
	Dispositivo dispositivo ;
	
	public DispositivoStandard ( Dispositivo myDispositivo)
	{
		this.dispositivo=myDispositivo;
	}
	
	@Override
	public void apagate() {
		
	}
	public double getConsumo() {
		return consumo;
	}

	public double getHorasUsoDiario() {
		return horasUsoDiario;
	}
	
	public LocalDateTime getHoraInicio() {
		return horaInicio;
	}
	public void setHoraInicio(LocalDateTime horaInicio) {
		this.horaInicio = horaInicio;
	}
	public LocalDateTime getHoraFin() {
		return horaFin;
	}
	public void setHoraFin(LocalDateTime horaFin) {
		this.horaFin = horaFin;
	}
	@Override
	public void ahorraEnergia() {
		
	}
	@Override
	public void bajaTuIntensidad() {
		
	}
		
	@Override
	public double cuantoConsumiste(double horas) {
		//cuantosDias pasaron * this.consumo
		return 0;
	}
	@Override
	public double cuantoConsumiste(LocalDateTime periodoInicial, LocalDateTime periodoFinal) {
		//cuantosDias pasaron * this.consumo
		return 0;
	}
	@Override
	public boolean estasApagado() {
		return false;
	}
	@Override
	public boolean estasPrendido() {
		return false;
	}
	@Override
	public void persistirCambioDeEstado() {
		
	}
	@Override
	public void prendete() {
		
	}
	@Override
	public void subiTuIntensidad() {
		
	}
	@Override
	public double cuantoConsumisPorHora() {
		return this.consumo;
	}
	@Override
	public double cuantoConsumisPorDia() {
		return this.cuantoConsumisPorHora() * this.getHorasUsoDiario() ;
	}
	@Override
	public void setConsumo (double myConsumo) {
		this.consumo = myConsumo ;
	}
	@Override
	public void setHorasUsoDiario( double myHoras ) {
		
		this.horasUsoDiario = myHoras ;
		
	}

}//end DispositivoStandard