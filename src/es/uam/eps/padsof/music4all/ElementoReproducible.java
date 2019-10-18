package es.uam.eps.padsof.music4all;
import java.io.Serializable;

/**
 * Esta clase representa el objeto ElementoReproducible
 *
 * @author Aitor Martin
 * @author Juan Carlos Merida
 * @author Arturo Morcillo
 * @version 1.0
 *
 */
public abstract class ElementoReproducible implements Serializable{
	private static final long serialVersionUID = 1L;
	//datos de la clase ElementoReproducible
	private String titulo;
	private int id;
	
	/**
	 * Constructor de la clase ElementoReproducible
	 * @param titulo el titulo(nombre del archivo) del elemento reproducible
	 */
	public ElementoReproducible (String titulo){
		this.titulo = titulo;
		this.id = Aplicacion.getInstance().getId();
	}
	
	/**
	 * Devuelve el titulo de un ElementoReproducible
	 * @return titulo del elemento reproducible
	 */
	public String getTitulo() {
		return titulo;
	}
	
	 /**
	  * Devuelve el id de un elementoReproducible
	  *@return id de la cancion
	  */
	  public int getId () {
		  return this.id;
	  }
	  
	/**
	 * Se encarga de obtener el tipo de elemento reproducible
	 * @return una variable de tipo TipoElementoReproducible
	 */
	public abstract TipoElementoReproducible getTipo();
	
	/**
	 * Se encarga de reproducir un elemento
	 * @return true si se ha podido escuchar el elemento, false en caso contrario
	 */
	public abstract Boolean reproducir(Usuario user);
	
	/**
	 * Se encarga de determinar la duracion de un elemento reproducible
	 */
	public abstract double duracion();
	
	/**
	 * Cadena que representa al objeto
	 * @return cadena
	 */
	public String toString() {
		return ("\tTitulo: "+this.titulo);
	}
}