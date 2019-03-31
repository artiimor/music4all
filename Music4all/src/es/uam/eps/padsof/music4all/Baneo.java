package es.uam.eps.padsof.music4all;
import java.time.LocalDate;

/**
 * Esta clase representa el objeto Baneo
 *
 * @author Aitor Martin
 * @author Juan Carlos Merida
 * @author Arturo Morcillo
 * @version 1.0
 *
 */
public class Baneo {
	//datos de la clase Baneo
	private UsuarioRegistrado usuarioBaneado;
	private LocalDate fechaBaneo;
	
	/**
	 * Constructor de la clase baneo
	 * @param usuario usuario baneado por 30 dias
	 */
	Baneo(UsuarioRegistrado usuario) {
		this.usuarioBaneado = usuario;
		this.fechaBaneo = FechaSimulada.getHoy();
	}
	
	/**
	 * Devuelve el usuario baneado
	 * @return el usuario correspondiente al baneo
	 */
	UsuarioRegistrado getUsuarioBaneado() {
		return this.usuarioBaneado;
	}
	
	/**
	 * Devuelve la fecha del baneo
	 * @return la fecha correspondiente al baneado al usuario
	 */
	LocalDate getFechaBaneo() {
		return this.fechaBaneo;
	}
}
