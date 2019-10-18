package es.uam.eps.padsof.music4all;
import java.util.*;
import es.uam.eps.padsof.telecard.*;
import pads.musicPlayer.Mp3Player;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
	private static final long serialVersionUID = 1L;
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
	private LocalDate fechaBaneo;
	
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
	 * Establece la fecha en la que el usario se hizo premium
	 * @param fechap fecha premium
	 */
	public void setFechaPremium(LocalDate fechap) {
		this.premium = fechap;
	}
	
	/**
	 * Establece la fecha en la que el usario fue baneado
	 * @param fechab fecha baneo
	 */
	public void setFechaBaneo(LocalDate fechab) {
		this.fechaBaneo = fechab;
	}
	
	/**
	 * Devuelve los privilegios de un usuario
	 * @return el TipoUsuario
	 */
	public EstadoUsuario getPrivilegios() {
		return privilegios;
	}
	
	/**
	 * Devuelve la fecha en la que un usuario fue baneado
	 * @return fecha baneo
	 */
	public LocalDate getFechaBaneo() {
		return this.fechaBaneo;
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
	 * Devuelve la contrasena de un usuario
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
		LocalDate fecha = FechaSimulada.getHoy();
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
	public void anadirSubscripcion(Subscripcion s) {
		this.subscripciones.add(s);
	}
	
	/**
	 * Permite subcribirse a un autor
	 * @param sub Nueva subscripcion
	 * @return un boolean que indica que todo ha ido bien
	 */
	public boolean nuevaSubscripcion(UsuarioRegistrado user) {
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
		Aplicacion.getInstance().addSubscripcion(sub);
		return true;
	}
	
	/**
	 * Permite anadir una cancion a la lista de canciones subidas por el usuario
	 * @param c la cancion a anadir
	 * @return un boolean que indica si todo ha ido bien o no
	 * @throws IOException 
	 */
	public boolean subirCancion(String titulo , String archivo) {
		String root = System.getProperty("user.dir");
		String des = root+"/APP-Files/"+getNombreUsuario()+"/"+archivo;
		String org = root+"/USER-Files/"+archivo;
		
		if (!Mp3Player.isValidMp3File(org) || (new File (des)).exists() ||
				 	(elementoSubido(titulo)!=null)) {
			return false;
		}

		File origen = new File(org);
	    File destino = new File(des);
		try {  
			Files.copy(Paths.get(origen.getAbsolutePath()), Paths.get(destino.getAbsolutePath()));
		} catch (IOException e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		Cancion can = new Cancion(titulo,this,des);
		Aplicacion.getInstance().anadirCancionAdmin(can);
		this.musica.add(can);
		return true;
	}
	
	/**
	 * Permite eliminar una cancion de un usuario
	 * @param c Cancion a eliminar
	 * @return un boolean que indica que todo ha ido bien
	 */
	public boolean eliminarCancion(Cancion c){
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
				if (c.getEstado()==EstadoCancion.PENDIENTE) {
					Aplicacion.getInstance().eliminarCancionAdmin(c);
				}

				musica.remove(elem);
				Aplicacion.getInstance().eliminarElemento(elem);
				return true;
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
				if (((Album)elem).containsAlbum(aux.getId())!=null) {
					if (!((Album)elem).cancionSinValidar()) {
						((Album)elem).setEstadoAlbum(EstadoAlbum.VALIDADO);
						Aplicacion.getInstance().addElemento(elem);
						Aplicacion.getInstance().anadirNotificacion(this, elem.getTitulo(),
								((Album)elem).getExplicito());
						return true;
					}
				}
			}
		}
		return false;
	}	
	
	/**
	 * Permite obtener tanto las obras subidas por el usuario
	 * en funcion de la variable tipoElemento
	 * @param tipoElemento, true si se desean las obras subidas, false si se desean las playlist
	 * @return lista de elementos reproducibles
	 */
	public List<ElementoReproducible> getElementosUsuario(Boolean tipoElemento){
		List<ElementoReproducible> lista = new ArrayList<ElementoReproducible>();
		for (ElementoReproducible elem: musica) {
			if (tipoElemento) {
				if (elem.getTipo()==TipoElementoReproducible.CANCION ||
						elem.getTipo()==TipoElementoReproducible.ALBUM) {
					lista.add(elem);
				} 
			} else {
				if (elem.getTipo()==TipoElementoReproducible.PLAYLIST) {
					lista.add(elem);
				} 
			}
		}
		return lista;
	}
	
	/**
	 * Permite determinar si una cancion o un album ya han sido subidos
	 * @param name nombre de la cancion o del album
	 * @return el elemento si se encuentra, false en caso contrario
	 */
	public Cancion elementoSubido(String name){
		for (ElementoReproducible elem: musica) {
				if (elem.getTipo()==TipoElementoReproducible.CANCION) {
					if (((Cancion)elem).getTitulo().contentEquals(name)) {
						return (Cancion)elem;
				} 
			} 
		}
		return null;
	}
	
	/**
	 * Metodo que permite ver las canciones que el usuario tiene que rectificar
	 * @return lista con las canciones a rectificar
	 */
	public List<Cancion> mostrarCancionesARectificar(){
		return this.aRectificar;
	}
	
	/**
	 * Metodo que devuelve un elemento de las lista de elementos reproducibles
	 * @param name nombre del elemento
	 * @return elemento reproducible
	 */
	public ElementoReproducible getElementoReproducible(int id){
		for (ElementoReproducible elem : musica) {
			if (elem.getId()==id) {
				return elem;
			} else if (elem.getTipo()==TipoElementoReproducible.ALBUM){
				Cancion aux =((Album)elem).containsAlbum(id);
				System.out.println(aux);
				if (aux!=null){
					return aux;
				}
			} else if (elem.getTipo()==TipoElementoReproducible.PLAYLIST) {
				ElementoReproducible aux = ((Playlist)elem).containsPlaylist(id);
		 		if (aux!=null){
					 return aux;
		 		}
			}
		}
		return null;
	}
	
	/**
	 * Metodo que permite que el usuario rectifique una cancion rechazada por el administrador
	 * @param cancion Cancion a rectificar
	 * @param archivo Nombre del archivo
	 * @return un boolean que indica si todo ha ido de forma correcta
	 * @throws IOException 
	 */
	public Boolean rectificarCancion(Cancion cancion, String archivo) {
		String root = System.getProperty("user.dir");
		String des = root+"\\APP-Files\\"+getNombreUsuario()+"\\"+archivo;
		String org = root+"\\USER-Files\\"+archivo;
		if (!Mp3Player.isValidMp3File(org) || 
				 (new File (des).exists())) {
			return false;
		}
		
		if (aRectificar.contains(cancion)) {
			File oldArchive = new File (cancion.getArchivo());
			oldArchive.delete();
			Path origen = Paths.get(org);
			Path destino = Paths.get(des);
			try
			{
				Files.copy(origen,destino);
			} catch (IOException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			
			cancion.setFecha(FechaSimulada.getHoy());
			cancion.setEstado(EstadoCancion.PENDIENTE);
			cancion.setArchivo(des);
			aRectificar.remove(cancion);
			Aplicacion.getInstance().anadirCancionAdmin(cancion);
			return true;
		}

		return false;
	}
	
	/**
	 * Metodo que elimina todas las canciones que no se han rectificado en el plazo acordado.
	 * @param fechaActual Fecha actual
	 */
	public void actualizarCancionesNoRectificadas(LocalDate fecha) {
		for (Iterator<Cancion> it = aRectificar.iterator();it.hasNext();)  {
			Cancion aux = it.next();
			if ((aux.getFecha()).getYear()<fecha.getYear()) {
				aux.getAutor().eliminarCancion(aux);
				it.remove();
			} else if ((aux.getFecha()).getMonthValue()<fecha.getMonthValue()) {
				aux.getAutor().eliminarCancion(aux);
				it.remove();
			} else if ((fecha.getDayOfMonth()-(aux.getFecha()).getDayOfMonth())>3) {
				aux.getAutor().eliminarCancion(aux);
				it.remove();
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
	 * Reinicia los contadores del usario
	 */
	public void resetearContadores() {
		this.contadorPremium=0;
		reiniciarContador();
	}
	
	/**
	 * Metodo que permite que el usuario denuncie un plagio
	 * @param plagio la cancion que es un supuesto plagio
	 * @param original la cancion plagiada
	 * @return true si se ha realizado la denuncia, false en caso contrario
	 */
	public Boolean denunciarCancion(Cancion plagio, Cancion original) {
		if (plagio.getId()==original.getId()) {
			return false;
		}
		Aplicacion.getInstance().anadirNoticacionAdmin(new NotificacionPlagio(this,plagio,original));
		return true;
	}
	
	/**
	 * Metodo que le permite al usuario crear un nuevo album
	 * @param titulo Titulo del album
	 * @param canciones Lista de las canciones que estaran en el album
	 * @return true si se ha podido crear el album, false en caso contrario
	 */
	public boolean nuevoAlbum(String titulo, List<Cancion> canciones) {
		if (elementoSubido(titulo)!=null) {
			return false;
		}
		for (Cancion aux:canciones) {
			if (!aux.getAutor().equals(this)) {
				return false;
			}
		}
		
		Album album = new Album(canciones, titulo, this);
		this.musica.add(album);
		if (album.getEstadoAlbum()!=EstadoAlbum.NOVALIDADO) {
			Aplicacion.getInstance().addElemento(album);
			Aplicacion.getInstance().anadirNotificacion(this, album.getTitulo(), album.getExplicito());
		}
		return true;
	}
	
	/**
	 * Metodo que le permite al usuario crear una nueva playlist
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
	public TipoUsuario getTipoUsuario() {
		if (mayorEdad()) {
			return TipoUsuario.NORMAL;
		}
		return TipoUsuario.MENOR;
	}
	
	
	/**
	 * Metodo que permite saber si una cancion esta contenida en algun album
	 * @param nombre de la cancion
	 * @return true si se encuentra, false en caso contrario
	 */
	public Boolean albumContainsCancion(int id) {
		for (ElementoReproducible aux : musica) {
			if (aux.getTipo()==TipoElementoReproducible.ALBUM) {
				if (((Album)aux).containsAlbum(id)!=null) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Metodo que gestiona un pago
	 * @param numTarjeta Numero de Tarjeta
	 */
	public Boolean pagarPremium(String numTarjeta) {
		if (!TeleChargeAndPaySystem.isValidCardNumber(numTarjeta)) {
			return false;
		}
		
		try {
			TeleChargeAndPaySystem.charge(numTarjeta,"Pago premium",-12.99,true);
		} catch (InvalidCardNumberException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (FailedInternetConnectionException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (OrderRejectedException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
	    }
			
		setPrivilegios(EstadoUsuario.PREMIUM);
		setFechaPremium(FechaSimulada.getHoy());
		return true;
	}
	 
	/**
	 * Metodo que gestiona a los usuarios Premium
	 * @param currentDate Fecha actual
	 * @return true si el usuario deja de ser premium, false en caso contrario
	 */
	public Boolean gestionarPremium(LocalDate currentDate) {
		if (currentDate.getYear()>premium.getYear()) {
			setPrivilegios(EstadoUsuario.ESTANDAR);
			premium = null;
			return true;
		}
		if ((currentDate.getDayOfYear()-premium.getDayOfYear())>29) {
			setPrivilegios(EstadoUsuario.ESTANDAR);
			premium = null;
			return true;
		}

		return false;
	}

	/**
	 * Procedimiento que convierte a un usuario normal a premium en funcion del 
	 * numero de reproducciones que sus canciones han tenido
	 * @param LocalDate Fecha actual
	 * @return true si el usuario se ha convertido en premium, false en caso contrario
	 */
	public Boolean convertirPremiumVisitas(LocalDate currentDate) {
		if (contadorPremium < limitePremium) {
			return false;
		}
		
		setPrivilegios(EstadoUsuario.PREMIUM);
		setFechaPremium(currentDate);
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
				+this.contadorPremium+" de "+this.limitePremium+"\n\tUsuario premium desde: "
				+this.premium+"\n\n");
		
		return returnValue;
	}

	public List<Playlist> getPlaylist() {
		List<Playlist> playlist = new ArrayList<Playlist>();
		for (ElementoReproducible e : this.musica) {
			if (e.getTipo() == TipoElementoReproducible.PLAYLIST)
				playlist.add(((Playlist)e));
		}
		return playlist;
	}
}
