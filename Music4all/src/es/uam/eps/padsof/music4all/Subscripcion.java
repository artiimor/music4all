package es.uam.eps.padsof.music4all;
import java.util.*;

/**
 * Esta clase representa el objeto Subcripcion
 *
 * @author Aitor Martin
 * @author Juan Carlos Merida
 * @author Arturo Morcillo
 * @version 1.0
 *
 */
public class Subscripcion {
	//Datos de la clase Subscripcion
	private UsuarioRegistrado Autor;
	private UsuarioRegistrado Subscriptor;
	private List<Notificacion> notificaciones;
	
	/**
     * Constructor de una Subscripcion
     * @param aut UsuarioRegistrado al que se desea subscribirse
     * @param subc UsuarioRegistrado que solicita la subscripcion
     */
	public Subscripcion(UsuarioRegistrado aut, UsuarioRegistrado subc) {
		this.Autor = aut;
		this.Subscriptor = subc;
		this.notificaciones = new ArrayList<Notificacion> ();
	}
	
	/**
     * Devuelve el autor al que se esta subscrito
     * @return autor 
     */
	public UsuarioRegistrado getAutor() {
		return this.Autor;
	}
	
	/**
     * Devuelve el subscriptor
     * @return subscriptor
     */
	public UsuarioRegistrado getSubscriptor() {
		return this.Subscriptor;
	}
	
	/**
     * Añade una notificacion a la Subscripcion
     * @param 
     * @return subscriptor
     */
	public Boolean añadirNotificacion(Notificacion not) {
		this.notificaciones.add(not);
		return true;
	}
	
	/**
     * Devuelve las notificaciones de una subcripcion
     * @return lista de notificaciones
     */
	public List<Notificacion> mostarNotifaciones() {
		return this.notificaciones;
	}
}