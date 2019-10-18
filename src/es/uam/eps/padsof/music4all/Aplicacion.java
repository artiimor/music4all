package es.uam.eps.padsof.music4all;
import java.io.*;
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
public class Aplicacion implements Serializable{
	private static final long serialVersionUID = 1L;
	//datos de la clase Aplicacion
	public static Aplicacion INSTANCE;
	private int idCancion = 0;
	private Usuario guestUser;
	private Usuario currentUser;
	private Administrador administrador;
	private Boolean adminActivo = false;
	private EstadoUsuario errorLogin = EstadoUsuario.ESTANDAR;
	private List<ElementoReproducible> elementos = new ArrayList<ElementoReproducible>();
	private List<UsuarioRegistrado> usuarios = new ArrayList<UsuarioRegistrado>();
	private List<UsuarioRegistrado> baneos = new ArrayList<UsuarioRegistrado>();
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
	 * Devuelve que estado posee el usuario que ha intentado hacer login
	 * @return estado del usuario
	 */
	public EstadoUsuario getErrorLogin() {	
		return errorLogin;
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
					errorLogin = EstadoUsuario.BLOQUEADO;
					return false;
				} else if (user.getPrivilegios()==EstadoUsuario.BANEADO) {
					System.out.println("El usario esta actualmente baneado\n");
					errorLogin = EstadoUsuario.BANEADO;
					return false;
				}
				errorLogin = EstadoUsuario.ESTANDAR;
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
		if (password.contentEquals(administrador.getContrasena())) {
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
	 * @param password Contrase�a del usuario
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
		File directorio = new File (System.getProperty("user.dir")+"\\APP-Files\\"+name);
		directorio.mkdir();
		usuarios.add(user);
		return true;
	}
	
	/**
	 * Permite a�adir un elemento a la lista de elementos reproducibles
	 * @param elemento Elemento a a�adir
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
	 * Metodo que devuelve un elemento de las lista de elementos reproducibles
	 * @param id identificador del elemento
	 * @return elemento reproducible
	 */
	public ElementoReproducible getElementoReproducible(Integer id){
		for (ElementoReproducible elem : elementos) {
			if (elem.getId()==id) {
				return elem;
			}
		}
		return null;
	}
	
	/**
	 * Metodo que devuelve un usuario de las lista de usuarios registrados
	 * @param name nombre del usuario
	 * @return usuario registrado
	 */
	public UsuarioRegistrado getUsuario(String name){
		for (UsuarioRegistrado aux : usuarios) {
			if (aux.getNombreUsuario().contentEquals(name)) {
				return aux;
			}
		}
		return null;
	}
	
	/**
	 * Permite a�adir un usuario baneado a la lista de baneos
	 * @param baneo usuario que va a se a�adido
	 */
	public void addBaneo(UsuarioRegistrado baneo) {
		this.baneos.add(baneo);
	}
	
	/**
	 * Permite eliminar un usuario baneado de la lista de baneos de la aplicacion
	 * @param baneo usuario a eliminar
	 * @return true si ha eliminado de forma correcta, false en caso contrario
	 */
	public boolean eliminarBaneo(UsuarioRegistrado baneo) {
		return baneos.remove(baneo);
	}

	/**
	 * Se encarga de revisar los baneos y si ha pasado el plazo lo elimina y
	 * el usuario baneado obtiene los privilegios de un usuario estandar.
	 * @param LocalDate Fecha actual
	 */
	public void actualizarBaneos(LocalDate fecha) {
		for (Iterator<UsuarioRegistrado> it = baneos.iterator();it.hasNext();) {
			UsuarioRegistrado ban = it.next(); 
			if (((ban.getFechaBaneo()).getYear()<fecha.getYear() ||
					(ban.getFechaBaneo()).getMonthValue()<fecha.getMonthValue() &&
					(fecha.getDayOfMonth()+(30-(ban.getFechaBaneo()).getDayOfMonth())>29))) {
				ban.setPrivilegios(EstadoUsuario.ESTANDAR);
				it.remove();
			}
		}
	}
	
	/**
	 * Se encarga de revisar los usuarios y si nos encontramos en el primer dia 
	 * del mes ,se resetean los contadores del usuario el usuario
	 * @param LocalDate Fecha actual
	 */
	public void actualizarUsuarios(LocalDate fecha) {
		for (UsuarioRegistrado user: usuarios) {
			user.resetearContadores();
		}
	}
	
	/**
	 * Metodo que se encarga de revisar todos aquellos metodos que necesiten
	 * actualizarse
	 */
	public void actualizarApp(LocalDate fecha) {
		loadBackup();
		actualizarBaneos(fecha);
		administrador.actualizarCancionesNoAdministradas(fecha);
		for (UsuarioRegistrado user: usuarios) {
			user.actualizarCancionesNoRectificadas(fecha);
			if (user.getPrivilegios() == EstadoUsuario.PREMIUM) {
				user.gestionarPremium(fecha);
			}
			if (fecha.getDayOfMonth()==1) {
				user.convertirPremiumVisitas(fecha);
				actualizarUsuarios(fecha);
				guestUser.reiniciarContador();
			}
			
		}
	}
	
	/**
	 * Permite a�adir una subscripcion a la lista de subscripciones de la aplicacion
	 * @param subcripcion Subcripcion que se desea a�adir
	 */
	public void addSubscripcion(Subscripcion subscripcion) {
		this.subscripciones.add(subscripcion);
	}
	
	/**
	 * A�ade una cancion al administrador para que la valide
	 * @param can Cancion que se tiene que revisar
	 */
	public void anadirCancionAdmin(Cancion can) {
		administrador.anadirCancion(can);
	}
	
	/**
	 * Elimina una cancion del administrador
	 * @param can Cancion que se eliminar
	 */
	public void eliminarCancionAdmin(Cancion can) {
		administrador.eliminarCancion(can);
	}
	
	/**
	 * A�ade una denuncia al administrador para que la valide
	 * @param denuncia Denuncia que tiene que revisar
	 */
	public void anadirNoticacionAdmin(NotificacionPlagio denuncia) {
		administrador.anadirDenuncia(denuncia);
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
					if (user.getNombreUsuario().contentEquals(busqueda)) {
						if ((tipoUser == TipoUsuario.NORMAL) && ((Cancion)aux).getExplicita() && 
								((Cancion)aux).getEstado()!=EstadoCancion.BLOQUEADA) {
							bus.add(aux);
						} else if (!((Cancion)aux).getExplicita() && 
								((Cancion)aux).getEstado()!=EstadoCancion.BLOQUEADA){
							bus.add(aux);
						}
					}
				}
			}
		} else if (estado==EstadoBusqueda.PORALBUM) {
			for (ElementoReproducible aux: lista) {
				if (aux.getTipo()==TipoElementoReproducible.ALBUM) {
					if (aux.getTitulo().contentEquals(busqueda)) {
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
					if (aux.getTitulo().contentEquals(busqueda)) {
						if ((tipoUser == TipoUsuario.NORMAL) && ((Album)aux).getExplicito()) {
							bus.add(aux);
						} else if (!((Album)aux).getExplicito()){
							bus.add(aux);
						}
					}
				}
				if (aux.getTipo()==TipoElementoReproducible.CANCION) {
					if (aux.getTitulo().contentEquals(busqueda)) {
						if ((tipoUser == TipoUsuario.NORMAL) && ((Cancion)aux).getExplicita() &&
								((Cancion)aux).getEstado()!=EstadoCancion.BLOQUEADA) {
							bus.add(aux);
						} else if (!((Cancion)aux).getExplicita() &&
								((Cancion)aux).getEstado()!=EstadoCancion.BLOQUEADA){
							bus.add(aux);
						}
					}
				}
			}
		}
		return bus;
	}
	
	/**
	 * Metodo que a�ade una notificacion a un usario
	 * @param usuario  Usuario que a subido un elemento
	 * @param string Titulo del elemento
	 * @param explicita true si la cancion es explicita, false en caso contrario
	 */
	public void anadirNotificacion (UsuarioRegistrado usuario, String titulo, Boolean explicita) {
		for (Subscripcion aux: subscripciones) {
			if (aux.getAutor().equals(usuario)) {
				if (explicita) {
					if (aux.getSubscriptor().mayorEdad()) {
						Notificacion not = new Notificacion ("El autor ["+usuario.getNombreUsuario()+ "] acaba de subir [" +titulo+"]");
						aux.anadirNotificacion(not);
					}
				} else {
					Notificacion not = new Notificacion ("El autor ["+usuario.getNombreUsuario()+ "] acaba de subir [" +titulo+"]");
					aux.anadirNotificacion(not);
				}
				
			}
		}
	}
	
	/**
	 * Metodo que guarda el estado de la aplicacion
	 */
	public void backup(){
		try {
			ObjectOutputStream salidaAplicacion =
					new ObjectOutputStream(
					new FileOutputStream( "aplicacion.objectData" ) );
			
			salidaAplicacion.writeObject(administrador);
			salidaAplicacion.writeObject(guestUser);
			salidaAplicacion.writeObject(currentUser);
			salidaAplicacion.writeObject(adminActivo);
			salidaAplicacion.writeObject(elementos);
			salidaAplicacion.writeObject(usuarios);
			salidaAplicacion.writeObject(baneos);
			salidaAplicacion.writeObject(subscripciones);
			salidaAplicacion.writeObject(idCancion);
			
			salidaAplicacion.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo que restaura el estado de la aplicacion
	 */
	@SuppressWarnings("unchecked")
	public void loadBackup() {
		try {
			ObjectInputStream entradaAplicacion =
					new ObjectInputStream(
					new FileInputStream( "aplicacion.objectData" ) );
			
			administrador = (Administrador) entradaAplicacion.readObject();
			guestUser = (Usuario) entradaAplicacion.readObject();
			currentUser = (Usuario) entradaAplicacion.readObject();
			adminActivo = (Boolean) entradaAplicacion.readObject();
			elementos = (List<ElementoReproducible>) entradaAplicacion.readObject();
			usuarios = (List<UsuarioRegistrado>) entradaAplicacion.readObject();
			baneos = (List<UsuarioRegistrado>) entradaAplicacion.readObject();
			subscripciones = (List<Subscripcion>) entradaAplicacion.readObject();
			idCancion = (int) entradaAplicacion.readObject();
			INSTANCE = this;
			
			entradaAplicacion.close();
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
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
