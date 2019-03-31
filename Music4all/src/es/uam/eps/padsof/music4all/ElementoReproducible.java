package es.uam.eps.padsof.music4all;
import java.io.FileNotFoundException;
import pads.musicPlayer.exceptions.Mp3PlayerException;

/**
 * Esta clase representa el objeto ElementoReproducible
 *
 * @author Aitor Martin
 * @author Juan Carlos Merida
 * @author Arturo Morcillo
 * @version 1.0
 *
 */
public abstract class ElementoReproducible {
	//datos de la clase ElementoReproducible
	private String titulo;
	
	/**
	 * Constructor de la clase ElementoReproducible
	 * @param titulo el titulo(nombre del archivo) del elemento reproducible
	 */
	public ElementoReproducible (String titulo){
		this.titulo = titulo;
	}
	
	/**
	 * Devuelve el titulo de un ElementoReproducible
	 * @return titulo del elemento reproducible
	 */
	public String getTitulo() {
		return titulo;
	}
	
	/**
	 * Se encarga de obtener el tipo de elemento reproducible
	 * @return una variable de tipo TipoElementoReproducible
	 */
	public abstract TipoElementoReproducible getTipo();
	
	/**
	 * Se encarga de reproducir un elemento
	 */
	public abstract void reproducirCancion(Usuario user) throws FileNotFoundException, Mp3PlayerException, InterruptedException;
	
	/**
	 * Se encarga de determinar la duracion de un elemento reproducible
	 */
	public abstract double duracion() throws FileNotFoundException;
	
	/**
	 * Cadena que representa al objeto
	 * @return cadena
	 */
	public String toString() {
		return ("\tTitulo: "+this.titulo);
	}
}