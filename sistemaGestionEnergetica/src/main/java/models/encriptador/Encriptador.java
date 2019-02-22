package models.encriptador;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Encriptador {

	private static Encriptador instancia = null;

	private Encriptador() {
	}

	public static Encriptador get() {

		if (instancia == null)
			instancia = new Encriptador();


		return instancia;
	}

	public String encriptar(String password, byte[] salt) {
		String generatedPassword = null;

		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(salt);
			byte[] bytes = md.digest(password.getBytes());
			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return generatedPassword;
	}

	// Add salt
	public byte[] getSalt() throws NoSuchAlgorithmException {
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		byte[] salt = new byte[16];
		sr.nextBytes(salt);
		return salt;
	}

	public boolean contraseniaCorrecta(String contraseniaIngresada, String contraseniaBaseDatos, byte[] salt) {
		String contraseniaEsperada = this.encriptar(contraseniaIngresada, salt);

		return contraseniaEsperada.equals(contraseniaBaseDatos);
	}

}
