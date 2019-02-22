package models.controllers.helpers;

public final class Sesion {

	private String alias;
	private String tipoUsuario;
	private boolean estaLogueado;
	
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getTipoUsuario() {
		return tipoUsuario;
	}
	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}
	public boolean getEstaLogueado() {
		return estaLogueado;
	}
	public void setEstaLogueado(boolean estaLogueado) {
		this.estaLogueado = estaLogueado;
	}
	
}
