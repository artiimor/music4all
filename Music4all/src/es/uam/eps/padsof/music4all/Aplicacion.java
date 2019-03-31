package es.uam.eps.padsof.music4all;
import java.time.LocalDate;
import java.util.*;

/**
 * Esta clase representa el objeto Aplicacion
 *
 * @author Aitor Martin
 * @author Juan Carlos Merida
 * @author Arturo Morcillo
 * @version 1.0
 *
 */
public class Aplicacion {
	//datos de la clase Aplicacion
	public static Aplicacion INSTANCE;
	private int idCancion = 0;
	private Usuario guestUser;
	private Usuario currentUser;
	private Administrador administrador;
	private Boolean adminActivo = false;
	private List<ElementoReproducible> elementos = new ArrayList<ElementoReproducible>();
	private List<UsuarioRegistrado> usuarios = new ArrayList<UsuarioRegistrado>();
	private List<Baneo> baneos = new ArrayList<Baneo>();
	private List<Subscripcion> subscripciones = new ArrayList<Subscripcion> ();

	/**
	 * Constructor de la clase aplicacion
	 */
	private Aplicacion(){
		this.administrador = new Administrador ("password123");	
		this.guestUser = new Usuario(100);
		this.currentUser = guestUser;
	}
	
	/**
	 * Esto permite obtener la aplicacion desde otras clases
	 * @return aplicacion
	 */
	public static Aplicacion getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new Aplicacion();
		}
		return INSTANCE;
    }
	
	/**
	 * Devuelve el id para crear una nueva cancion
	 * @return id
	 */
	public int getId() {	
		idCancion++;
		return this.idCancion;
	}
	
	/**
	 * Devuelve el usuario actual del sistema
	 * @return devuelve el usuario que esta utilizando la aplicacion en este momento
	 */
	public Usuario getLoggedUser() {	
		return this.currentUser;
	}
	
	/**
	 * Devuelve el administrador del sistema
	 * @return devuelve el usuario que esta utilizando la aplicacion en este momento
	 */
	public Administrador getAdministrador() {
		if (this.adminActivo) {
			return this.administrador;
		}
		return null;
	}
	
	/**
	 * Permite a un usuario loguearse
	 * @param nickname Nombre de usuario
	 * @param password Contrasena del usuario
	 * @return true si se podido hacer login, false en caso contrario
	 */
	public Boolean login(String nickname, String password) {
		if (!this.currentUser.equals(guestUser)) {
			return false;
		}
		
		for (UsuarioRegistrado user: usuarios) {
			if (user.getNombreUsuario().contentEquals(nickname) && 
					user.getPassword().contentEquals(password)) {
				if (user.getPrivilegios()==EstadoUsuario.BLOQUEADO) {
					System.out.println("Usuario bloqueado del sistema\n");
					return false;
				}
				for (Baneo ban: baneos) {
					if (ban.getUsuarioBaneado().equals(user)) {
						System.out.println("El usario esta actualmente baneado\n");
						return false;
					}
				}
				this.currentUser = user;
				return true;
			}
		}
		return false;
	}	
	
	/**
	 * Permite al administrador loguearse
	 * @param password del administrador
	 * @return true si se podido hacer login, false en caso contrario
	 */
	public Boolean login(String password) {
		if (password.contentEquals(administrador.getContraseña())) {
			adminActivo = true;
			return true;
		}
		return false;
	}	
	
	/**
	 * Permite a un usuario desconectarse de la aplicacion 
	 * @return true si se podido hacer logout, false en caso contrario
	 */
	public Boolean logout() {
		if (this.adminActivo) {
			adminActivo = false;
			return false;
		}
		if (this.currentUser.equals(guestUser)) {
			return false;
		}
		
		currentUser = guestUser;
		return true;
	}
	
	/**
	 * Metodo que da de alta a un usario
	 * @param name Nombre del usuario
	 * @param password Contraseña del usuario
	 * @param fechaNac Fecha nacimiento usuario
	 */
	public Boolean registro(String name,String password,LocalDate fechaNac) {
		for (UsuarioRegistrado aux: usuarios) {
			if (aux.getNombreUsuario().contentEquals(name)) {
				System.out.println("El nombre de usuario ya existe\n");
				return false;
			}
		}
		UsuarioRegistrado user = new UsuarioRegistrado(name,password,fechaNac,100);
		usuarios.add(user);
		return true;
	}
	
	/**
	 * Permite añadir un elemento a la lista de elementos reproducibles
	 * @param elemento Elemento a añadir
	 */
	public void addElemento(ElementoReproducible elemento) {
		this.elementos.add(elemento);
	}
	
	/**
	 * Permite eliminar un elemento reproducible
	 * @param elemento Elemento reproducible a eliminar
	 * @return true si se ha podido eliminar el elemento, falso en caso contrario
	 */
	public void eliminarElemento(ElementoReproducible elemento) {
		elementos.remove(elemento);
	}
	
	/**
	 * Permite añadir un baneo a la lista de baneos
	 * @param baneo Baneo que va a se añadido
	 */
	public void addBaneo(Baneo baneo) {
		this.baneos.add(baneo);
	}
	
	/**
	 * Permite eliminar un baneo de la lista de baneos de la aplicacion
	 * 
	 * @param baneo el baneo a eliminar
	 * @return un boolean que indica si ha eliminado de forma correcta el baneo o no
	 */
	public boolean eliminarBaneo(Baneo baneo) {
		return baneos.remove(baneo);
	}

	/**
	 * Se encarga de revisar los baneos y si ha pasado el plazo lo elimina y
	 * el usuario baneado obtiene los privilegios de un usuario estandar.
	 * @param LocalDate Fecha actual
	 */
	public void actualizarBaneos(LocalDate fecha) {
		for (Baneo ban: baneos) {
			if ((ban.getFechaBaneo()).getYear()<fecha.getYear() ||
					(ban.getFechaBaneo()).getMonthValue()<fecha.getMonthValue() &&
					(fecha.getDayOfMonth()+(30-(ban.getFechaBaneo()).getDayOfMonth())>29)) {
				(ban.getUsuarioBaneado()).setPrivilegios(EstadoUsuario.ESTANDAR);
				baneos.remove(ban);
			}
		}
	}
	
	/**
	 * Metodo que se encarga de revisar todos aquellos metodos que necesiten
	 * actualizarse
	 */
	public void actualizarApp() {
		LocalDate fecha = FechaSimulada.getHoy();
		actualizarBaneos(fecha);
		administrador.actualizarCancionesNoAdministradas(fecha);
		for (UsuarioRegistrado user: usuarios) {
			user.actualizarCancionesNoRectificadas(fecha);
		}
	}
	
	/**
	 * Permite añadir una subscripcion a la lista de subscripciones de la aplicacion
	 * @param subcripcion Subcripcion que se desea añadir
	 */
	public void addSubscripcion(Subscripcion subscripcion) {
		this.subscripciones.add(subscripcion);
	}
	
	/**
	 * Añade una cancion al administrador para que la valide
	 * @param can Cancion que se tiene que revisar
	 */
	public void añadirCancionAdmin(Cancion can) {
		administrador.añadirCancion(can);
	}
	
	/**
	 * Añade una denuncia al administrador para que la valide
	 * @param denuncia Denuncia que tiene que revisar
	 */
	public void añadirNoticacionAdmin(NotificacionPlagio denuncia) {
		administrador.añadirDenuncia(denuncia);
	}
	
	/**
	 * Metodo que permite realizar una busqueda
	 * @param estado Tipo de busqueda que se desea hacer
	 * @param busqueda Texto de la busqueda
	 */
	public List <ElementoReproducible> buscarElementoReproducible (EstadoBusqueda estado, String busqueda, TipoUsuario tipoUser) {
		List <ElementoReproducible> bus = new ArrayList <ElementoReproducible> ();
		List <ElementoReproducible> lista = elementos;
		
		if (estado==EstadoBusqueda.PORAUTOR) {
			for (ElementoReproducible aux: lista) {
				if (aux.getTipo()==TipoElementoReproducible.CANCION) {
					UsuarioRegistrado user = ((Cancion) aux).getAutor();
					if (user.getNombreUsuario().equals(busqueda)) {
						if ((tipoUser == TipoUsuario.NORMAL) && ((Cancion)aux).getExplicita()) {
							bus.add(aux);
						} else if (!((Cancion)aux).getExplicita()){
							bus.add(aux);
						}
					}
				}
			}
		} else if (estado==EstadoBusqueda.PORALBUM) {
			for (ElementoReproducible aux: lista) {
				if (aux.getTipo()==TipoElementoReproducible.ALBUM) {
					if (aux.getTitulo().equals(busqueda)) {
						if ((tipoUser == TipoUsuario.NORMAL) && ((Album)aux).getExplicito()) {
							bus.add(aux);
						} else if (!((Album)aux).getExplicito()){
							bus.add(aux);
						}
					}
				}
			}	
		} else if (estado==EstadoBusqueda.PORTITULO) {
			for (ElementoReproducible aux: lista) {
				if (aux.getTipo()==TipoElementoReproducible.ALBUM) {
					if (aux.getTitulo().equals(busqueda)) {
						if ((tipoUser == TipoUsuario.NORMAL) && ((Album)aux).getExplicito()) {
							bus.add(aux);
						} else if (!((Album)aux).getExplicito()){
							bus.add(aux);
						}
					}
				}
				if (aux.getTipo()==TipoElementoReproducible.CANCION) {
					UsuarioRegistrado user = ((Cancion) aux).getAutor();
					if (user.getNombreUsuario().equals(busqueda)) {
						if ((tipoUser == TipoUsuario.NORMAL) && ((Cancion)aux).getExplicita()) {
							bus.add(aux);
						} else if (!((Cancion)aux).getExplicita()){
							bus.add(aux);
						}
					}
				}
			}
		}
		return bus;
	}
	
	
	/**
	 * Se encarga de comprobar hasta cuando tienen premium los distintos usuarios y actualiza su estado
	 */
	/**
	public void revisarPremium() {
		//necesito una fecha
		Calendar calendar = Calendar.getInstance();
		Date fecha =  (Date) calendar.getTime();
				
		Aplicacion aplicacion = Aplicacion.getInstance();
		List<UsuarioRegistrado> usuarios = aplicacion.getUsuarios();
		
		for(UsuarioRegistrado usuario : usuarios) {
			usuario.actualizarPremium(fecha);
		}
	}*/
	
	public void añadirNotificacion (UsuarioRegistrado usuario, String titulo) {
		for (Subscripcion aux: subscripciones) {
			if (aux.getAutor().equals(usuario)) {
				Notificacion not = new Notificacion ("El autor "+usuario.getNombreUsuario()+ "acaba de subir" +titulo);
				aux.añadirNotificacion(not);
			}
		}
	}
	
	/**
	 * Cadena que representa a este objeto
	 * @return cadena
	 */
	public String toString() {
		return("Usuario logueado en este momento: \n"+getLoggedUser());
	}
}
