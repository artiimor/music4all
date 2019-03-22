/**
 * Esta clase administra la clase ElementoReproducible de la que posteriormente derivran
 * Cancion, Album y Playlist
 * 
 * @author Aitor Martin, Juan Carlos Mérida y Arturo Morcillo
 *
 */
public abstract class ElementoReproducible {
	protected String titulo;
	
	/**
	 * constructor de la clase
	 * 
	 * @param titulo el titulo del elemento reproducible
	 */
	public ElementoReproducible (String titulo){
		this.titulo = titulo;
	}
	
	/**
	 * cambia el titulo de ElementoReproducible
	 * 
	 * @param newTitulo
	 */
	void setTitulo (String newTitulo) {
		titulo = newTitulo;
	}
	
	/**
	 * 
	 * @return El titulo del Elemento reproducible (String)
	 */
	public String getTitulo() {
		return titulo;
	}
	
	/**
	 * Se encarga de reproducir el elemento mediante el reproductor
	 */
	public void Reproducir() {
		/*TODO hacer esta*/
	}
	
}