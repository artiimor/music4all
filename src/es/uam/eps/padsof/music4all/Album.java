package es.uam.eps.padsof.music4all;
import java.time.LocalDate;
import java.util.*;

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
	private static final long serialVersionUID = 1L;
	// datos de la clase Album
	private List<Cancion> canciones = new ArrayList<Cancion>();
	private UsuarioRegistrado autor;
	private LocalDate fecha;
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
		this.fecha = FechaSimulada.getHoy();
		for (Cancion aux: canciones) {
			if (aux.getEstado() == EstadoCancion.PENDIENTE || aux.getEstado() == EstadoCancion.RECHAZADA) {
				this.estado = EstadoAlbum.NOVALIDADO;
			} else if (aux.getExplicita()) {
				this.explicito = true;
			}
			this.canciones.add(aux);
			aux.getAutor().eliminarElemento(aux);
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
	  * Devuelve la fecha de un album
	  * @return autor
	  */
	 public LocalDate getFecha() {
		 return this.fecha;
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
			if (c.getEstado()==EstadoCancion.PENDIENTE) {
				return true;
			}
		}
		return false;
	 }
	 
	 /**
	  * Devuelve las lista de canciones que tiene un album
	  * @return lista de canciones del album
	  */
	 public List<Cancion> getCanciones() {
		return Collections.unmodifiableList(canciones);
	 }
	 
	 /**
	  * Comprueba si la cancion se encuentra en el album
	  * @param id identificador de la cancion
	  * @return devuelve la cancion si es que se encuentra contenido
	  */
	 public Cancion containsAlbum(int id) {
		 for (Cancion can: canciones) {
			 if (can.getId()==id) {
				 return can;
			 }
		 }
		return null;
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
	 * @return true si se ha podido escuchar el album, false en caso contrario
	 */
	public Boolean reproducir(Usuario user) {
		for (Cancion c : canciones) {
			c.reproducir(user);
		}
		return true;
	}
	
	/**
	 * Permite determinar la duracion de un album
	 * @return duracion del album en segundos
	 */
	public double duracion() {
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
	
	/**
	 * Cadena que representa al objeto
	 * @return cadena
	 */
	public String toString() {
		return("[ALBUM]:\n\tTitulo: "+getTitulo()+"\nid:"+getId()+"\n\tAutor:"+this.getAutor().getNombreUsuario()+
				"\n\tEstadoAlbum: "+this.estado+"\n\tExplicito: "+this.getExplicito()+
				"\n\tCanciones: \n\n"+this.canciones);
	}
}
