package src.usuario;
/**
 * Clase base de la que parten los distintos tipos de usuario
 * 
 * @author Aitor Martin/Juan Carlos Merida/Arturo Morcillo
 * @version 1.0
 */

public abstract class Usuario {
	private int contadorCanciones;
	private int limiteCanciones;
	private Busqueda search;
	
	/**
	 * Funcion que inicializa el contador de canciones a un valor dado
	 * 
	 * @param cont valor a inicializar en el contador
	 */
	public void setContador(int cont) {
		contadorCanciones=cont;
	}
	/**
	 * Metodo que muestra el valor del contador de canciones reproducidas
	 * 
	 * @return valor del contador
	 */
	public int getContador() {
		return contadorCanciones;
	}
	/**
	 * Metodo que inicializa el valor del limite a las canciones que un usuario dado puede reproducir
	 * 
	 * @param lim maximo de canciones que se podran reproducir
	 */
	public void setLimite(int lim) {
		limiteCanciones=lim;
	}
	/**
	 * Metodo que muestra el valor del limite de canciones reproducibles
	 * 
	 * @return el valor del limite de canciones
	 */
	public int getLimite() {
		return limiteCanciones;
	}
	
	/**
	 * Metodo que aumenta el contador de canciones en uno
	 */
	public void cancionEscuchada() {
		contadorCanciones++;
	}
	/**
	 * Metodo que realiza una busqueda mediante el objeto search 
	 * 
	 * @param e Tipo de busqueda que se va a realizar
	 * @param busqueda Palabra clave a partir de la que se va a realizar la busqueda
	 */
	public void realizarBusqueda(EstadoBusqueda e, String busqueda) {
		search = search.nuevaBusqueda(this,e,busqueda);		
	}
	public list<ElementoReproducible> mostrarResultadosBusqueda() {
		return search.mostrarResultados();
	}
}
