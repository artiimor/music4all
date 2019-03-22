package es.uam.eps.padsof.Music4all;
import java.util.*;

public class Subscripcion {
	private UsuarioRegistrado Autor;
	private UsuarioRegistrado Subscriptor;
	private List<Notificacion> notificaciones;
	
	private Subscripcion(UsuarioRegistrado aut, UsuarioRegistrado subc) {
		this.Autor = aut;
		this.Subscriptor = subc;
		this.notificaciones = new ArrayList<Notificacion> ();
	}
	
	public Boolean nuevaSubscripcion(UsuarioRegistrado aut, UsuarioRegistrado subc) {
		if (aut.getNombreUsuario().equal(subc.getNombreUsuario())) {
			return false;
		}
		
		Subscripcion sub = new Subscripcion (aut,subc);
		
		if (!aut.añadirSubscripcion(this)) {
			return false;
		}
		return true;
	}
	
	public UsuarioRegistrado getAutor() {
		return this.Autor;
	}
	
	public UsuarioRegistrado getSubscriptor() {
		return this.Subscriptor;
	}
	
	public Boolean añadirNotificacion(Notificacion not) {
		this.notificaciones.add(not);
		return true;
	}
	
	public List<Notificacion> mostarNotifaciones() {
		return this.notificaciones;
	}
}