package es.uam.eps.padsof.music4all;
import java.time.LocalDate;

/**
 * Esta clase representa el objeto Notificacion
 *
 * @author Aitor Martin
 * @author Juan Carlos Merida
 * @author Arturo Morcillo
 * @version 1.0
 *
 */
public class Notificacion {
	//datos de la clase Notificacion
	private String mensaje;
	private LocalDate fecha;
	
	/**
	 * Constructor de la clase Notificacion
	 * @param men Mensaje de notificacion
	 */
	public Notificacion (String men) {
		this.mensaje = men;
		this.fecha = FechaSimulada.getHoy();
	}
	
	/**
	 * Devuelve el mensaje de una notificacion
	 * @return mensaje 
	 */
	public String getMensaje() {
		return this.mensaje;
	}
	
	/**
	 * Devuelve la fecha de una notificacion
	 * @return fecha 
	 */
	public LocalDate getFecha() {
		return this.fecha;
	}
}