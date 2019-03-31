package es.uam.eps.padsof.music4all;
import java.io.FileNotFoundException;
import java.util.*;
import pads.musicPlayer.exceptions.Mp3PlayerException;

/**
 * Esta clase representa el objeto Album
 *
 * @author Aitor Martin
 * @author Juan Carlos Merida
 * @author Arturo Morcillo
 * @version 1.0
 *
 */
public class Album extends ElementoReproducible {
	// datos de la clase Album
	private List<Cancion> canciones = new ArrayList<Cancion>();
	private UsuarioRegistrado autor;
	boolean explicito;
	EstadoAlbum estado = EstadoAlbum.VALIDADO;
	
	/**
	 * Constructor de la clase Album
	 * @param canciones Canciones a añadir
	 * @param titulo Titulo del album
	 * @param autor Autor del album
	 */
	public Album(List<Cancion> canciones, String titulo, UsuarioRegistrado autor) {
		super(titulo);
		this.autor = autor;
		for (Cancion aux: canciones) {
			if (aux.getEstado() == EstadoCancion.PENDIENTE || aux.getEstado() == EstadoCancion.RECHAZADA) {
				this.estado = EstadoAlbum.NOVALIDADO;
			} else if (aux.getExplicita()) {
				this.explicito = true;
			}
			this.canciones.add(aux);
			aux.getAutor().eliminarCancion(aux);
		}
	}	
	
	/**
	 * Devuelve si un album se encuentra validado o no
	 * @return EstadoAlbum
	 */
	 public EstadoAlbum getEstadoAlbum() {
		 return this.estado;
	 }
	 
	 /**
	  * Devuelve si un album es explicito o no
	  * @return true si es explicito, false en caso contrario
	  */
	 public boolean getExplicito() {
		 return this.explicito;
	 }
	 
	 /**
	  * Devuelve el autor de un album
	  * @return autor
	  */
	 public UsuarioRegistrado getAutor() {
		 return this.autor;
	 }
	 
	/**
	 * Cambia el estado de un Album
	 * @return EstadoAlbum Nuevo estado
	 */
	 public void setEstadoAlbum(EstadoAlbum estado) {
		 this.estado=estado;
	 }
		 
	 /**
	  * Comprueba si hay canciones sin validar en el album
	  * @return true si hay canciones sin validar, false en caso contrario
	  */
	 public Boolean cancionSinValidar() {
		for (Cancion c: canciones) {
			if (c.getExplicita()) {
				return false;
			}
		}
		return true;
	}
	 
	 /**
	  * Comprueba si la cancion se encuentra en el album
	  * @param can Cancion a comprobar
	  * @return true si la cancion esta el album, false en caso contrario
	  */
	 public Boolean containsAlbum(Cancion can) {
		return canciones.contains(can);
	}

	 
	 /**
	  * Devuelve el numero de canciones de un Album
	  * @return numero de canciones
	  */
	 public int numCanciones() {
		 return canciones.size();
	 }
	 
	 /**
	  * Metodo que elimina una cancion de un album
	  * @return true si se ha podido eliminar del album, false en caso contrario
	  */
	 public boolean eliminarCancionAlbum(Cancion c) {
		 for (Cancion aux : canciones) {
			 if (aux.equals(c)) {
				 if (canciones.size()==1) {
					 Aplicacion.getInstance().eliminarElemento(this);
					 aux.getAutor().eliminarElemento(this);	 
				 }
				 canciones.remove(aux);
				 return true;
			 }
		 }
		 return false;
	 }
	 
	/**
	 * Metodo que permite reproducir un album
	 */
	public void reproducirCancion(Usuario user) throws FileNotFoundException, Mp3PlayerException, InterruptedException {
		for (Cancion c : canciones) {
			c.reproducirCancion(user);
		}
	}
	
	/**
	 * Permite determinar la duracion de un album
	 * @return duracion del album en segundos
	 */
	public double duracion() throws FileNotFoundException {
		double duracion=0;
		for (Cancion c : canciones) {
			duracion += c.duracion();
		}
		return duracion;
	}
	
	/**
	 * Se encarga de obtener el tipo de elemento reproducible
	 * @return una variable de tipo TipoElementoReproducible
	 */
	public TipoElementoReproducible getTipo() {
		return TipoElementoReproducible.ALBUM;
	}
	
	//TODO VALIDAR ALBUMES
}
