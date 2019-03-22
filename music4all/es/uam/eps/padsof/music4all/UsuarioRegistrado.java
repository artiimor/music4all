package src.usuario;

import java.util.*;
/**
 * Clase que representa a los usuarios registrados
 * 
 * @author Aitor Martin/Juan Carlos Merida/Arturo Morcillo
 * @version 1.0
 */

public class UsuarioRegistrado extends Usuario{
	private String nombreUsuario;
	private String password;
	private Date fechaNacimiento;
	private TipoUsuario privilegios;
	private int contadorPremium;
	private int limitePremium;
	private ArrayList<ElementoReproducible> Musica;
	private ArrayList<Subscripcion> Subscripciones;
	
	private UsuarioRegistrado(String name, String pass, Date fecha) {
		nombreUsuario=name;
		password=pass;
		fechaNacimiento=fecha;
		privilegios=TipoUsuario.Estandar;
		limitePremium=100;
		contadorPremium=0;
	}
	/**
	 * Modifica el valor que determina el paso a premium
	 * 
	 * @param lim nuevo valor del limite
	 */
	public void setLimitePremium(int lim) {
		limitePremium=lim;
	}
	/**
	 * Devuelve el valor del limite para pasar a premium
	 * 
	 * @return el limite para pasar a premium
	 */
	public int getLimitePremium() {
		return limitePremium;
	}
	/**
	 * Funcion para inicializar el valor del contador premium
	 * 
	 * @param cont valor de inicializacion
	 */
	public void setContadorPremium(int cont) {
		contadorPremium=cont;
	}
	/**
	 * Devuelve el valor del contador para pasar a premium
	 * 
	 * @return el valor del contador
	 */
	public int getContadorPremium() {
		return contadorPremium;
	}
	/**
	 * Aumenta el valor del contador en uno (cuenta una reproduccion)
	 */
	public void incrContadorPremium() {
		contadorPremium++;
	}
	
	public boolean mayorEdad() {
		return true;/* Con fecha en aplicacion TODO */
	}
	public List<Notificacion> mostrarSubscripciones() {
		return null; /*TODO*/
	}
	public boolean añadirSubscripcion(Subscripcion s) {
		return true;/*TODO*/
	}
	public boolean añadirCancion(Cancion c){		
		return Musica.add(c);
	}
	public boolean eliminarCancion(Cancion c){
		return Musica.remove(c);/*TODO*/
	}
	public List<Cancion> mostrarObras(){
		for(int i=0;)/*TODO*/
	}
}
