import java.util.*;

public class Administrador {
	private String contrasena;
	private List <Cancion> CancionesAValidar;
	private List <NotificacionPlagio> denuncia;

	private Administrador (String password) {
		this.contrasena = password;
	}
	
	public Boolean anadirCancion (Cancion c) {
		return true;
	}
	
	public Boolean anadirDenuncia (Cancion c) {
		return true;
	}
	
	public Boolean valiadarDenuncia (NotificacionPlagio denuncia) {
		return true;
	}
	
	public Boolean validarCancion (Cancion c) {
		return true;
	}
	
	public Boolean rechazarCancion (Cancion c) {
		return true;
	}
	
	public Boolean validarCancionExplicita (Cancion c) {
		return true;
	}
}
