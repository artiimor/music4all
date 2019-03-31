package es.uam.eps.padsof.music4all;
import java.util.*;

import pads.musicPlayer.Mp3Player;

import java.time.LocalDate;
import java.time.Period;

/**
 * Esta clase representa el objeto UsuarioRegistrado
 *
 * @author Aitor Martin
 * @author Juan Carlos Merida
 * @author Arturo Morcillo
 * @version 1.0
 *
 */
public class UsuarioRegistrado extends Usuario{
	//datos de la clase UsuarioRegistrado
	private String nombreUsuario;
	private String password;
	private LocalDate fechaNacimiento;
	private EstadoUsuario privilegios;
	private int contadorPremium;
	private int limitePremium;
	private ArrayList<ElementoReproducible> musica = new ArrayList<ElementoReproducible>();
	private ArrayList<Subscripcion> subscripciones = new ArrayList<Subscripcion>();
	private ArrayList<Cancion> aRectificar = new ArrayList<Cancion>();
	private LocalDate premium;
	
	/**
	 * Constructor de la clase Usuario
	 * @param limite de reproducciones de un Usuario
	 */
	public UsuarioRegistrado(String nombreUsuario, String password, LocalDate fechaNacimiento, int limite) {
		super (30);
		this.nombreUsuario = nombreUsuario;
		this.password = password;
		this.fechaNacimiento = fechaNacimiento;
		this.privilegios = EstadoUsuario.ESTANDAR;
		this.contadorPremium = 0;
		this.limitePremium = limite;
	}
	
	/**
	 * Modifica el valor que determina del contradorPremium
	 * @param lim Nuevo valor del limite
	 */
	public void setLimitePremium(int lim) {
		limitePremium=lim;
	}
	
	/**
	 * Modifica los privilegios del usuario
	 * @param privilegio Nuevo privilegio que se desea establecer
	 */
	public void setPrivilegios(EstadoUsuario privilegio) {
		this.privilegios = privilegio;
	}
	
	/**
	 * Devuelve los privilegios de un usuario
	 * @return el TipoUsuario
	 */
	public EstadoUsuario getPrivilegios() {
		return privilegios;
	}
	
	/**
	 * Devuelve el valor del limite para pasar a premium
	 * @return limite para pasar a premium
	 */
	public int getLimitePremium() {
		return limitePremium;
	}

	/**
	 * Devuelve el valor del contador para pasar a premium
	 * @return valor del contador
	 */
	public int getContadorPremium() {
		return contadorPremium;
	}

	/**
	 * Metodo que duvuelve las canciones a rectificar por parte del usuario
	 * @return lista de canciones
	 */
	public List<Cancion> getARectificar() {
		return this.aRectificar;
	}
	
	/**
	 * Devuelve el nombre de un usuario
	 * @return nombre
	 */
	public String getNombreUsuario () {
		return this.nombreUsuario;
	}
	
	/**
	 * Devuelve la contraseña de un usuario
	 * @return password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Se encarga de incrementar las reproducciones que ha tenido ese usuario
	 */
	public void incrContadorPremium() {
		contadorPremium++;
	}
	
	/**
	 * Metido que se encarga de comprobar si un usuario es o no mayor de edad
	 * @return true si el usuario es mayor de edad, false en caso contrario
	 */
	public boolean mayorEdad() {
		LocalDate fecha = LocalDate.now();
		Period periodo = Period.between(fechaNacimiento,fecha);
		if (periodo.getYears()>17) {
			return true;
		}
		return false;
	}
	
	/**
	 * Permite obtener una lista de notificaciones de las subscripciones del usuario
	 * @return una lista con todas sus notificaciones
	 */
	public List<Notificacion> mostrarSubscripciones() {
		List<Notificacion> notificaciones = new ArrayList<Notificacion>();
		
		for (Subscripcion sub: subscripciones) {
			notificaciones.addAll(sub.mostarNotifaciones());
		}
		
		return notificaciones;
	}
	
	/**
	 * Permite anadir una subscripcion al usuario
	 * @param sub Nueva subscripcion
	 */
	public void añadirSubscripcion(Subscripcion s) {
		this.subscripciones.add(s);
	}
	
	/**
	 * Permite subcribirse a un autor
	 * @param sub Nueva subscripcion
	 * @return un boolean que indica que todo ha ido bien
	 */
	public boolean añadirSubscripcion(UsuarioRegistrado user) {
		if (user.equals(this)) {
			return false;
		}
		
		for (Subscripcion sub: subscripciones) {
			if (sub.getAutor().equals(user)) {
				return false;
			}
		}
		
		Subscripcion sub= new Subscripcion(user,this);
		this.subscripciones.add(sub);
		return true;
	}
	
	/**
	 * Permite anadir una cancion a la lista de canciones subidas por el usuario
	 * @param c la cancion a anadir
	 * @return un boolean que indica si todo ha ido bien o no
	 */
	public boolean subirCancion(String titulo , String archivo){
		if (!Mp3Player.isValidMp3File(archivo)) {
			return false;
		}
		Cancion can = new Cancion(titulo,this,archivo);
		Aplicacion.getInstance().añadirCancionAdmin(can);
		this.musica.add(can);
		return true;
	}
	
	/**
	 * Permite eliminar una cancion de un usuario
	 * @param c Cancion a eliminar
	 * @return un boolean que indica que todo ha ido bien
	 */
	public boolean eliminarCancion(Cancion c){
		if (musica.contains(c)) {
			for (ElementoReproducible elem: musica) {
				if (elem.getTipo()==TipoElementoReproducible.ALBUM) {
					if (((Album)elem).eliminarCancionAlbum(c)) {
						return true;
					}
				} else if (elem.getTipo()==TipoElementoReproducible.PLAYLIST) {
					if (((Playlist)elem).eliminarCancionPlaylist(c)) {
						return true;
					}
				} if (elem.equals(c)) {
					musica.remove(elem);
					Aplicacion.getInstance().eliminarElemento(elem);
					return true;
				}
			}
		}
		return false;
	}	
	
	/**
	 * Permite eliminar un elemento reproducible del usuario
	 * @param elem Elemento a eliminar
	 * @return un boolean que indica que todo ha ido bien
	 */
	public void eliminarElemento (ElementoReproducible elem) {
		this.musica.remove(elem);
	}
	
	/**
	 * Permite validar el album de un usuario
	 * @param 
	 */
	public Boolean validarAlbum(Cancion aux) {
		for (ElementoReproducible elem: musica) {
			if (elem.getTipo()==TipoElementoReproducible.ALBUM) {
				if (((Album)elem).containsAlbum(aux)) {
					if (!((Album)elem).cancionSinValidar()) {
						((Album)elem).setEstadoAlbum(EstadoAlbum.VALIDADO);
						Aplicacion.getInstance().addElemento(elem);
						return true;
					}
				}
			}
		}
		return false;
	}	
	
	/**
	 * Permite obtener la lista de canciones subidas por el usuario
	 * @return lista con las canciones que ha subido el usuario
	 */
	public List<Cancion> mostrarObras(){
		List<Cancion> obrasUsuario = new ArrayList<Cancion>();
		for (ElementoReproducible elem: musica) {
			if (elem.getTipo()==TipoElementoReproducible.CANCION) {
				obrasUsuario.add((Cancion)elem);
			}
		}
		return obrasUsuario;
	}
	
	/**
	 * Metodo que permite ver las canciones que el usuario tiene que rectificar
	 * @return lista con las canciones a rectificar
	 */
	public List<Cancion> mostrarCancionesARectificar(){
		return this.aRectificar;
	}
	
	/**
	 * Metodo que permite que el usuario rectifique una cancion rechazada por el administrador
	 * @param cancion Cancion a rectificar
	 * @param archivo Nombre del archivo
	 * @return un boolean que indica si todo ha ido de forma correcta
	 */
	public Boolean rectificarCancion(Cancion cancion, String archivo) {
		if (!Mp3Player.isValidMp3File(archivo)) {
			return false;
		}
		for (Cancion can: aRectificar) {
			if (can.equals(cancion)) {
				can.setFecha(FechaSimulada.getHoy());
				can.setArchivo(archivo);
				can.setEstado(EstadoCancion.PENDIENTE);
				Aplicacion.getInstance().añadirCancionAdmin(can);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Metodo que elimina todas las canciones que no se han rectificado en el plazo acordado.
	 * @param fechaActual Fecha actual
	 */
	public void actualizarCancionesNoRectificadas(LocalDate fecha) {
		for (Cancion aux: aRectificar) {
			if ((aux.getFecha()).getYear()<fecha.getYear()) {
				aRectificar.remove(aux);
				aux.getAutor().eliminarCancion(aux);
			} else if ((aux.getFecha()).getMonthValue()<fecha.getMonthValue()) {
				aRectificar.remove(aux);
				aux.getAutor().eliminarCancion(aux);
			} else if ((fecha.getDayOfMonth()-(aux.getFecha()).getDayOfMonth())>3) {
				aRectificar.remove(aux);
				aux.getAutor().eliminarCancion(aux);
			}
		}
	}
	
	/**
	 * Incrementa el contador para ser premium.
	 */
	public void anadirReproduccionPremium() {
		this.contadorPremium++;
	}
	
	/**
	 * Metodo que permite que el usuario denuncie un plagio
	 * 
	 * @param plagio la cancion que es un supuesto plagio
	 * @param original la cancion plagiada
	 * @return true si se ha realizado la denuncia, false en caso contrario
	 */
	public Boolean denunciarCancion(Cancion plagio, Cancion original) {
		if (plagio.getId()==original.getId()) {
			return false;
		}
		Aplicacion.getInstance().añadirNoticacionAdmin(new NotificacionPlagio(this,plagio,original));
		return true;
	}
	
	/**
	 * Metodo que le permite al usuario crear un nuevo album
	 * @param titulo Titulo del album
	 * @param canciones Lista de las canciones que estaran en el album
	 * @return true si se ha podido crear el album, false en caso contrario
	 */
	public boolean nuevoAlbum(String titulo, List<Cancion> canciones) {
		Album album = new Album(canciones, titulo, this);
		this.musica.add(album);
		if (!album.getExplicito()) {
			Aplicacion.getInstance().addElemento(album);
		}
		return true;
	}
	
	/**
	 * Metodo que le permite al usuario crear una nueva playlist
	 * 
	 * @param titulo Titulo de la playlist
	 * @param elementos lista de los elementos reproducibles que estaran en la playlist
	 * @return true si se ha podido crear la playlist, false en caso contrario
	 */
	public boolean nuevaPlaylist(String titulo, List<ElementoReproducible> elementos) {
		Playlist playlist = new Playlist(elementos, titulo);
		this.musica.add(playlist);
		return true;
	}

	/**
	 * Metodo que devuelve si un usario es normal o sin registro (o menor)
	 */
	public TipoUsuario isUsuarioNormal() {
		if (mayorEdad()) {
			return TipoUsuario.NORMAL;
		}
		return TipoUsuario.MENOR;
	}
	
	
	//TODO hacer esta.
	public Boolean pagarPremium() {
		return true;
	}

	
	/**
	 * Metodo toString
	 * 
	 * @return un String con la informacion del usuario registrado
	 */
	public String toString() {
		
		String returnValue;
		
		returnValue = ("\tNickname: "+this.nombreUsuario+"\n\tPassword: "+this.password+
				"\n\tFecha de nacimiento: "+this.fechaNacimiento+
				"\n\tPrivilegios: "+this.privilegios+"\n\tHa reproducido "
				+this.getLimite()+" de "+this.getContador()+"\n\tPara ser premium ha sido escuchado "
				+this.contadorPremium+" de "+this.limitePremium+"\n\tEl usuario es premium hasta: \n\n");
		
		return returnValue;
	}
}
