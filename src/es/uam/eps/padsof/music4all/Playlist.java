package es.uam.eps.padsof.music4all;
import java.util.*;

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
	private static final long serialVersionUID = 1L;
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
	  * Devuelve los elementos de una Playlist
	  * @return lista de elementos
	  */
	 public List<ElementoReproducible> getElementos(){
		 return Collections.unmodifiableList(elementos);
	 }
	 
	 /**
	  * Comprueba si un elemento se encuentra en la playlist
	  * @param name 
	  * @return devuelve el elemento si es que se encuentra contenido
	  */
	 public ElementoReproducible containsPlaylist(int id) {
		 for (ElementoReproducible elem: elementos) {
			 if (elem.getTipo()==TipoElementoReproducible.ALBUM) {
				 Cancion aux =((Album)elem).containsAlbum(id);
				 if (aux!=null){
					 return aux;
				 }
			 } else if (elem.getTipo()==TipoElementoReproducible.CANCION) { 
				 if (((Cancion)elem).getId()==id) {
				 return elem;
				 }
		 	} else {
		 		ElementoReproducible aux = ((Playlist)elem).containsPlaylist(id);
		 		if (aux!=null){
					 return aux;
		 		}
		 	}
		 }
		 return null;
	}
	 
	/**
	 * Permite reproducir un album
	 * @return true si se ha podido reproducir la playlist, false en caso contrario
	 */
	public Boolean reproducir(Usuario user) {
		for (ElementoReproducible aux : elementos) {
			aux.reproducir(user);
		}
		return true;
	}
	
	/**
	 * Permite determinar la duracion de una playlist
	 * @return duracion de la playlist en segundos
	 */
	public double duracion() {
		double duracion=0;
		for (ElementoReproducible c : elementos) {
			duracion += c.duracion();
		}
		return duracion;
	}

	public void anadirCanciones(List<ElementoReproducible> can) {
		this.elementos.addAll(can);
		
	}


}
