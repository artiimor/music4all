package es.uam.eps.padsof.music4all;
import java.io.FileNotFoundException;
import java.util.*;
import pads.musicPlayer.exceptions.Mp3PlayerException;

/**
 * Esta clase representa el objeto Playlist
 *
 * @author Aitor Martin
 * @author Juan Carlos Merida
 * @author Arturo Morcillo
 * @version 1.0
 *
 */
public class Playlist extends ElementoReproducible {
	//datos de la clase Playlist
	private List<ElementoReproducible> elementos;
	
	/**
	 * Constructor de la clase playlist
	 * @param elementos Lista de los elementos reproducibles de la playlist
	 * @param titulo Titulo de la playlist
	 */
	public Playlist(List<ElementoReproducible> elementos, String titulo){
		super(titulo);
		this.elementos = elementos;
	}
	
	/**
	 * Se encarga de obtener el tipo de elemento reproducible
	 * @return una variable de tipo TipoElementoReproducible
	 */
	public TipoElementoReproducible getTipo() {
		return TipoElementoReproducible.PLAYLIST;
	}
	
	/**
	  * Metodo que elimina una cancion de una playlist
	  * @return true si se ha podido eliminar de la playlist, false en caso contrario
	  */
	 public boolean eliminarCancionPlaylist(Cancion c) {
		 for (ElementoReproducible aux : elementos) {
			 if (aux.getTipo()==TipoElementoReproducible.ALBUM) {
				 Album a = (Album) aux;
				 if (a.eliminarCancionAlbum(c)) {
					 if (a.numCanciones()==0) {
						 if (elementos.size()==1) {
							 Aplicacion.getInstance().eliminarElemento(this);
							 c.getAutor().eliminarElemento(this); 
						 }
						 elementos.remove(aux);
					 }
					 return true;
				 }
			 }else if (aux.getTipo()==TipoElementoReproducible.PLAYLIST) {
				 Playlist p = (Playlist) aux;
				 if (p.eliminarCancionPlaylist(c)) {
					 if (p.numElementos()==0) {
						 if (elementos.size()==1) {
							 Aplicacion.getInstance().eliminarElemento(this);
							 c.getAutor().eliminarElemento(this); 
						 }	 
						 elementos.remove(aux);
					 }
					 return true;
				 }
			 } else {
				 if (aux.equals(c)) {
					 if (elementos.size()==1) {
						 Aplicacion.getInstance().eliminarElemento(this);
						 c.getAutor().eliminarElemento(this);
						 elementos.remove(aux);
						 return true;
					 }	 
				 }
			 }
		 }
		 return false;
	 }	 
	 
	 /**
	  * Devuelve el numero de elementos de una Playlist
	  * @return numero de elementos
	  */
	 public int numElementos() {
		 return elementos.size();
	 }
	 
	/**
	 * Permite reproducir un album
	 * @return boolean si ha ido todo bien o no
	 */
	public void reproducirCancion(Usuario user) throws FileNotFoundException, Mp3PlayerException, InterruptedException {
		for (ElementoReproducible aux : elementos) {
			aux.reproducirCancion(user);
		}
	}
	
	/**
	 * Permite determinar la duracion de una playlist
	 * @return duracion de la playlist en segundos
	 */
	public double duracion() throws FileNotFoundException {
		double duracion=0;
		for (ElementoReproducible c : elementos) {
			duracion += c.duracion();
		}
		return duracion;
	}
}
