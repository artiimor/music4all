package es.uam.eps.padsof.music4all;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

/**
 * Esta clase representa el objeto Administrador
 *
 * @author Aitor Martin
 * @author Juan Carlos Merida
 * @author Arturo Morcillo
 * @version 2.0
 *
 */
public class Administrador implements Serializable{
	private static final long serialVersionUID = 1L;
	// datos de la clase Administrador
	private String contrasena;
	private List <Cancion> cancionesAValidar = new ArrayList<Cancion>();
	private List <NotificacionPlagio> denunciasAValidar = new ArrayList<NotificacionPlagio> ();
	
	/**
	 * Constructor de la clase Administrador
	 * @param password Contrasena del administrador
	 */
	public Administrador (String password) {
		this.contrasena = password;
	}
	
	/**
	 * Devuelve la contrasena del administrador
	 * @return contrasena del administrador
	 */
	public String getContrasena() {
		return contrasena;
	}
	
	/**
	 * Devuelve las canciones que el admin tiene que validar
	 * @return Una lista de canciones
	 */
	public List<Cancion> getCancionesPendientes(){
		return Collections.unmodifiableList(this.cancionesAValidar);
	}
	
	/**
	 * Devuelve una canciones que el admin tiene que validar
	 * @param id identificador de la cancion
	 * @return Cancion
	 */
	public Cancion getCancionPendiente(Integer id){
		for (Cancion aux : cancionesAValidar) {
			if (aux.getId()==id) {
				return aux;
			}
		}
		return null;
	}
	
	/**
	 * Devuelve las denuncias que el admin tiene que validar
	 * @return Una lista de denuncias
	 */
	public List<NotificacionPlagio> getDenunciasPendientes(){
		return Collections.unmodifiableList(this.denunciasAValidar);
	}
	
	/**
	 * Elimina una cancion no validada del administrador
	 * @return Una lista de denuncias
	 */
	public void eliminarCancion(Cancion cancion){
		if (cancionesAValidar.contains(cancion)) {
			cancionesAValidar.remove(cancion);
		}
	}
	
	/**
	 * Metodo que permite anadir una cancion a la lista de canciones
	 * pendientes por validar
	 * @param cancion Cancion pendiente de validacion
	 * @return true si se ha podido anadir la cancion, false en caso contrario
	 */
	public Boolean anadirCancion (Cancion cancion) {
		if (!cancionesAValidar.contains(cancion)) {
			if (cancion.getEstado() != EstadoCancion.PENDIENTE) {
				return false;
			}
			this.cancionesAValidar.add(cancion);
			return true;
		}	
		return false;
	}
	
	/**
	 * Metodo que permite validar una cancion y permite declarar si es explicita
	 * @param cancion Cancion a validar
	 * @return true si se ha podido validar la cancion, false si no se ha podido validar
	 */
	public Boolean validarCancion (Cancion cancion, Boolean explicita) {
		if (cancionesAValidar.contains(cancion)) {
			cancion.setFecha(FechaSimulada.getHoy());
			cancion.setEstado(EstadoCancion.VALIDADA);
			if (explicita) 
			{
				cancion.setExplicita();
			}
			
			Aplicacion.getInstance().addElemento(cancion);
			Aplicacion.getInstance().anadirNotificacion(cancion.getAutor(),cancion.getTitulo(),explicita);
			cancion.getAutor().validarAlbum(cancion);
			cancionesAValidar.remove(cancion);
			return true;
		} 
		return false;
	}		

	/**
	 * Metodo que rechaza una cancion y se la anade al usuario para que la valide
	 * junto con una nota
	 * @param cancion Cancion rechazada
	 * @param notaAdmin Nota adjunta por el administrador
	 * @return true si ha podido rechazar la cancion o false en caso contrario
	 */
	public Boolean rechazarCancion (Cancion cancion, String notaAdmin) {
		if (cancionesAValidar.contains(cancion)) {
			cancion.setFecha(FechaSimulada.getHoy());
			cancion.setEstado(EstadoCancion.RECHAZADA);
			cancion.setNotaAdmin(notaAdmin);
			cancion.getAutor().getARectificar().add(cancion);
			cancionesAValidar.remove(cancion);
			return true;
		} 
		return false;
	}	
	
	/**
	 * Anade una denuncia pendiente a la lista de denuncias del administrador
	 * @param denuncia Denuncia a validar
	 * @return true si se ha podido anadir la denuncia, false en caso contrario
	 */
	public Boolean anadirDenuncia (NotificacionPlagio denuncia) {
		this.denunciasAValidar.add(denuncia);
		return true;
	}
	
	/**
	 * Metodo del administrador obtener una denuncia
	 * @param id identificador de la cancion plagiada
	 * @param id identificador de la cancion original
	 * @return notificacion de plagio
	 */
	public NotificacionPlagio getDenuncia (int idP, int idO) {
		for (NotificacionPlagio aux: denunciasAValidar) {
			if (aux.getCancionDenunciada().getId()==idP &&
					aux.getCancionOriginal().getId()==idO) {
				return aux;
			}
		}
		return null;
	}
	
	/**
	 * Metodo del administrador para validar una denuncia (la cancion ha sido plagiada)
	 * @param denuncia Denuncia a validar
	 * @return true si se ha podido validar la denuncia, false en caso contrario
	 */
	public Boolean validarDenuncia (NotificacionPlagio denuncia) {
		if (denunciasAValidar.contains(denuncia)) {
			if (denuncia.validarDenuncia()) {
				denunciasAValidar.remove(denuncia);
				return true;
			}
		}
		return false;
	}	
	
	/**
	 * Metodo del administrador para rechazar una denuncia (la cancion no ha sido plagiada)
	 * @param denuncia Denuncia a rechazar
	 * @return true si se ha podido rechazar la denuncia, false en caso contrario
	 */
	public Boolean rechazarDenuncia (NotificacionPlagio denuncia) {
		if (denunciasAValidar.contains(denuncia)) {
			if (denuncia.rechazarDenuncia()) {
				denunciasAValidar.remove(denuncia);
				return true;
			}
		}
		return false;
	}	
	
	/**
	 * Metodo que se encarga de eliminar las canciones que no se han validado en un plazo de 3 dias
	 * @param LocalDate fecha actual
	 */
	public void actualizarCancionesNoAdministradas(LocalDate fecha) {
		for (Cancion aux: cancionesAValidar) {
			if ((aux.getFecha()).getYear()<fecha.getYear()) {
				cancionesAValidar.remove(aux);
				aux.getAutor().eliminarCancion(aux);
			} else if ((aux.getFecha()).getMonthValue()<fecha.getMonthValue()) {
				cancionesAValidar.remove(aux);
				aux.getAutor().eliminarCancion(aux);
			} else if ((fecha.getDayOfMonth()-(aux.getFecha()).getDayOfMonth())>3) {
				cancionesAValidar.remove(aux);
				aux.getAutor().eliminarCancion(aux);
			}
		}
	}	
}
