package es.uam.eps.padsof.music4all;
import java.io.Serializable;
import java.util.*;

/**
 * Esta clase representa el objeto Usuario
 *
 * @author Aitor Martin
 * @author Juan Carlos Merida 
 * @author Arturo Morcillo
 * @version 1.0
 *
 */
public class Usuario implements Serializable{
	private static final long serialVersionUID = 1L;
	//datos de la clase Usuario
	private int contadorCanciones;
	private int limiteCanciones;
	
	/**
	 * Constructor de la clase Usuario
	 * @param limite de reproducciones de un Usuario
	 */
	public Usuario (int limite) {
		this.limiteCanciones = limite;
		this.contadorCanciones = 0;
	}

	/**
	 * Metodo que devuele el contador de canciones reproducidas
	 * @return valor del contador
	 */
	public int getContador() {
		return contadorCanciones;
	}
	
	/**
	 * Metodo que reinicia el contador de reproducciones
	 */
	public void reiniciarContador() {
		 contadorCanciones=0;
	}
	
	/**
	 * Metodo que cambia el limite a las canciones que un usuario puede reproducir
	 * @param lim Maximo de canciones que se podran reproducir
	 */
	public void setLimite(int lim) {
		limiteCanciones=lim;
	}
	
	/**
	 * Metodo que devuelve el valor del limite de canciones reproducibles
	 * @return valor del limite de canciones
	 */
	public int getLimite() {
		return limiteCanciones;
	}
	
	/**
	 * Metodo que aumenta el contador de canciones
	 */
	public void cancionEscuchada() {
		contadorCanciones++;
	}
	
	/**
	 * Metodo que realiza una busqueda mediante el objeto search 
	 * @param e Tipo de busqueda que se va a realizar
	 * @param busqueda Palabra clave a partir de la que se va a realizar la busqueda
	 */
	public List<ElementoReproducible> realizarBusqueda(EstadoBusqueda estado, String busqueda) {
		List <ElementoReproducible> bus = null;
		bus = Aplicacion.getInstance().buscarElementoReproducible(estado, busqueda,getTipoUsuario());
		
		if (bus.isEmpty()) {
			System.out.println("No se han encontrado resultados para la busqueda ("+estado+") :"+busqueda);
			return bus;
		}
		return Collections.unmodifiableList(bus);
	}
	
	/**
	 * Metodo que devuelve si un usario es normal o sin registro (o menor)
	 */
	public TipoUsuario getTipoUsuario() {
		return TipoUsuario.GRATUITO;
	}
	
	/**
	 * Cadena que representa al objeto
	 * @return string con la informacion del usuario
	 */
	public String toString() {
		return ("\tHa escuchado "+this.contadorCanciones+" de "+this.limiteCanciones+" canciones.");
	}
}
