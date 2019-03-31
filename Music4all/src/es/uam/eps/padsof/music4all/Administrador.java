package es.uam.eps.padsof.music4all;
import java.time.LocalDate;
import java.util.*;

/**
 * Esta clase representa el objeto Administrador
 *
 * @author Aitor Martin
 * @author Juan Carlos Merida
 * @author Arturo Morcillo
 * @version 1.0
 *
 */
public class Administrador {
	// datos de la clase Administrador
	private String contrase�a;
	private List <Cancion> cancionesAValidar = new ArrayList<Cancion>();
	private List <NotificacionPlagio> denunciasAValidar = new ArrayList<NotificacionPlagio> ();
	
	/**
	 * Constructor de la clase Administrador
	 * @param password Contrase�a del administrador
	 */
	public Administrador (String password) {
		this.contrase�a = password;
	}
	
	/**
	 * Devuelve la contrase�a del administrador
	 * @return contrase�a del administrador
	 */
	public String getContrase�a() {
		return contrase�a;
	}
	
	/**
	 * Devuelve las canciones que el admin tiene que validar
	 * @return Una lista de canciones
	 */
	public List<Cancion> getCancionesPendientes(){
		return this.cancionesAValidar;
	}
	
	/**
	 * Metodo que permite anadir una cancion a la lista de canciones
	 * pendientes por validar
	 * @param cancion Cancion pendiente de validacion
	 * @return true si se ha podido a�adir la cancion, false en caso contrario
	 */
	public Boolean a�adirCancion (Cancion cancion) {
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
	 * Metodo que permite validar una cancion
	 * @param cancion Cancion a validar
	 * @return true si se ha podido validar la cancion, false si no se ha podido validar
	 */
	public Boolean validarCancion (Cancion cancion){
		if (cancionesAValidar.contains(cancion)) {
			for (Cancion aux: cancionesAValidar) {
				if (aux.equals(cancion)) {
					aux.setFecha(FechaSimulada.getHoy());
					aux.setEstado(EstadoCancion.VALIDADA);
					Aplicacion.getInstance().addElemento(aux);
					Aplicacion.getInstance().a�adirNotificacion(aux.getAutor(),aux.getTitulo());
					aux.getAutor().validarAlbum(aux);
					cancionesAValidar.remove(aux);
					return true;
				}
			}
		} 
		return false;
	}	
	
	/**
	 * Metodo que permite validar una cancion y la declara como explicita
	 * @param cancion Cancion a validar
	 * @return true si se ha podido validar la cancion, false si no se ha podido validar
	 */
	public Boolean validarCancionExplicita (Cancion cancion) {
		if (cancionesAValidar.contains(cancion)) {
			for (Cancion aux: cancionesAValidar) {
				if (aux.equals(cancion)) {
					aux.setFecha(FechaSimulada.getHoy());
					aux.setEstado(EstadoCancion.VALIDADA);
					aux.setExplicita();
					Aplicacion.getInstance().addElemento(cancion);
					if ((aux.getAutor()).mayorEdad()) {
						Aplicacion.getInstance().a�adirNotificacion(aux.getAutor(),aux.getTitulo());
					}
					aux.getAutor().validarAlbum(aux);
					cancionesAValidar.remove(aux);
					return true;
				}
			}
		} 
		return false;
	}	

	/**
	 * Metodo que rechaza una cancion y se la a�ade al usuario para que la valide
	 * junto con una nota
	 * @param cancion Cancion rechazada
	 * @param notaAdmin Nota que le pone el administrador
	 * @return true si ha podido rechazar la cancion o false en caso contrario
	 */
	public Boolean rechazarCancion (Cancion cancion, String notaAdmin) {
		if (cancionesAValidar.contains(cancion)) {
			for (Cancion aux: cancionesAValidar) {
				if (aux.equals(cancion)) {
					aux.setFecha(FechaSimulada.getHoy());
					aux.setEstado(EstadoCancion.RECHAZADA);
					aux.setNotaAdmin(notaAdmin);
					aux.getAutor().getARectificar().add(aux);
					cancionesAValidar.remove(aux);
					return true;
				}
			}
		} 
		return false;
	}	
	
	/**
	 * A�ade una denuncia pendiente a la lista de denuncias del administrador
	 * @param denuncia Denuncia a validar
	 * @return true si se ha podido a�adir la denuncia, false en caso contrario
	 */
	public Boolean a�adirDenuncia (NotificacionPlagio denuncia) {
		this.denunciasAValidar.add(denuncia);
		return true;
	}
	
	/**
	 * Metodo del administrador para validar una denuncia (la cancion ha plagiado)
	 * @param denuncia Denuncia a validar
	 * @return true si se ha podido validar la denuncia, false en caso contrario
	 */
	public Boolean validarDenuncia (NotificacionPlagio denuncia) {
		if (!denunciasAValidar.contains(denuncia)) {
			for (NotificacionPlagio aux: denunciasAValidar) {
				if (aux.equals(denuncia)) {
					if (denuncia.validarDenuncia()) {
						denunciasAValidar.remove(aux);
						return true;
					}
				}
			}
		}
		return false;
	}	
	
	/**
	 * Metodo del administrador para rechazar una denuncia (la cancion no ha sido plagiada)
	 * @param denuncia Denuncia a validar
	 * @return true si se ha podido rechazar la denuncia, false en caso contrario
	 */
	public Boolean rechazarDenuncia (NotificacionPlagio denuncia) {
		if (!denunciasAValidar.contains(denuncia)) {
			for (NotificacionPlagio aux: denunciasAValidar) {
				if (aux.equals(denuncia)) {
					if (denuncia.rechazarDenuncia()) {
						denunciasAValidar.remove(aux);
						return true;
					}
				}
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
