package models.entities.dominio;

import java.security.NoSuchAlgorithmException;

import javax.persistence.*;

import models.encriptador.Encriptador;
import models.entities.transformadores.Ubicable;

@Entity
@Table(name = "usuario")
@DiscriminatorValue("usuario")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo")
public abstract class Usuario extends Ubicable{
	
	@Column(name = "nombre")
	private String nombre;
	@Column(name = "apellido")
	private String apellido;
	@Column(name = "numeroDocumento")
	private int numeroDocumento;
	@Column(name = "tipoDocumento")
	private String tipoDocumento;
	@Column(name = "telefono")
	private int telefono;
	@Column(name = "domicilio")
	private String domicilio;
	@Column(name = "aliasUsuario", unique=true)
	private String aliasUsuario;
	@Column(name = "contrasenia")
	private String contrasenia;
	@Column(name="salt")
	private byte[] salt;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public int getNumeroDocumento() {
		return numeroDocumento;
	}
	public void setNumeroDocumento(int numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public int getTelefono() {
		return telefono;
	}
	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}
	public String getDomicilio() {
		return domicilio;
	}
	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}
	public String getAliasUsuario() {
		return aliasUsuario;
	}
	public void setAliasUsuario(String aliasUsuario) {
		this.aliasUsuario = aliasUsuario;
	}
	public String getContrasenia() {
		return contrasenia;
	}
	public void setContrasenia(String contrasenia) throws NoSuchAlgorithmException {
		
		this.salt = Encriptador.get().getSalt();
		
		String contraseniaEncriptada = Encriptador.get().encriptar(contrasenia, this.salt);
		
		this.contrasenia = contraseniaEncriptada;
	}
	public byte[] getSalt() {
		return this.salt;
	}

}