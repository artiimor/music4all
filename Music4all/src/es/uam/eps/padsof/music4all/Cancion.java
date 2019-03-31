package es.uam.eps.padsof.music4all;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import pads.musicPlayer.Mp3Player;
import pads.musicPlayer.exceptions.Mp3PlayerException;

/**
 * Esta clase representa el objeto Cancion
 *
 * @author Aitor Martin
 * @author Juan Carlos Merida
 * @author Arturo Morcillo
 * @version 1.0
 *
 */
public class Cancion extends ElementoReproducible {
	//datos de la clase Cancion
	private int id;
	private LocalDate fecha;
	private String archivo;
	private UsuarioRegistrado autor;
	private EstadoCancion estado = EstadoCancion.PENDIENTE;
	private boolean explicita = false;
	private String notaAdmin;
	
	/**
	 * 
	 * Constructor de la clase Cancion
	 * @param titulo Titulo de la cancion
	 * @param autor Autor de la cancion
	 */
	public Cancion (String titulo, UsuarioRegistrado autor, String archivo){
		super(titulo);
		this.autor = autor;
		this.archivo = archivo;
		this.fecha = FechaSimulada.getHoy();
		this.id = Aplicacion.getInstance().getId();
		Aplicacion.getInstance().añadirCancionAdmin(this);
	}
	 
	 /**
	  * Cambia el estado en el que se encuentra la cancion
	  * @param newEstado Nuevo estado
	  */
	 public void setEstado(EstadoCancion newEstado) {
		 this.estado = newEstado;
	 }
	 
	 /**
	  * Cambia el archivo de la cancion
	  * @param newArchivo Nuevo Archivo
	  */
	 public void setArchivo(String newArchivo) {
		 this.archivo = newArchivo;
	 }
	 
	 /**
	  * Cambia la de una cancion
	  * @param newDate Nueva fecha
	  */
	 public void setFecha(LocalDate newDate) {
		 this.fecha = newDate;
	 }
	 
	 /**
	  * Metodo que establece una cancion como explicita
	  */
	 public void setExplicita() {
		 this.explicita = true;
	 }
	 
	 /**
	  * Cambia la nota del administrador que tiene la cancion
	  * @param newNotaAdmin Nota del administrador
	  */
	 public void setNotaAdmin (String newNotaAdmin) {
		 this.notaAdmin = newNotaAdmin;
	 }
	
	 /**
	  * Devuelve el id de una cancion
	  *@return id de la cancion
	  */
	  public int getId () {
		  return this.id;
	  }
  
	  /**
	   * Devuelve la fecha de subida de una cancion
	   * @return fecha de subida
	   */
	  public LocalDate getFecha() {
		  return this.fecha;
	  }
		 
	  /**
	   * Devuelve el autor
	   * @return autor de la cancion
	   */
	  public UsuarioRegistrado getAutor () {
		  return this.autor;
	  }
		 
	  /**
	   * Devuelve el estado en el que se encuentra la cancion
	   * @return EstadoCancion con el estado de la cancion
	   */
	  public EstadoCancion getEstado() {
		  return this.estado;
	  }
		 
	  /**
	   * Devuelve si una cancion es explicita o no
	   * @return true si la cancion es explicita, false en caso contrario
	   */
	  public boolean getExplicita () {
		  return this.explicita;
	  }
	  
	  /**
	   * Devuelve el nombre del archivo de una cancion
	   * @return nombre del archivo
	   */
	  public String getArchivo () {
		  return this.archivo;
	  }
	
	/**
	 * Permite a un usuario rectificar una cancion en el caso de que 
	 * haya sido rechazada en un primer lugar
	 * @param newArchivo Archivo de la cancion
	 */
	public boolean rectificarCancion(String newArchivo) {
		if (this.notaAdmin == null || this.estado != EstadoCancion.RECHAZADA) {
			return false;
		}	
		this.archivo = newArchivo;
		this.estado = EstadoCancion.PENDIENTE;
		this.notaAdmin = null;
		Aplicacion.getInstance().añadirCancionAdmin(this);
		return true;
	}
	
	/**
	 * Se encarga de obtener el tipo de elemento reproducible
	 * @return una cariable de tipo TipoElementoReproducible con el tipo
	 */
	public TipoElementoReproducible getTipo() {
		return TipoElementoReproducible.CANCION;
	}	
	
	/**
	 * Metodo que permite reproducir una cancion
	 */
	public void reproducirCancion(Usuario user) throws FileNotFoundException, Mp3PlayerException, InterruptedException {
		if (user.getTipoUsuario() == TipoUsuario.NORMAL) {
			//Si ha excedido el limite:
			if(user.getContador() >= user.getLimite()) {
				System.out.println("Se ha alcanzado el maximo de reproducciones\n");
				return;
			}
			user.cancionEscuchada();
		} else {
			if(((UsuarioRegistrado)user).getPrivilegios() != EstadoUsuario.PREMIUM) {
				if(((UsuarioRegistrado)user).getContador() >= ((UsuarioRegistrado)user).getLimite()) {
					System.out.println("Se ha alcanzado el maximo de reproducciones mensuales\n");
					System.out.println("si desea aumentar sus reproducciones actualice a premium");
					return;
				}
			}
			
		}
		Mp3Player player = new Mp3Player();
		double dur = duracion();
		player.add(getArchivo());
		System.out.println("Reproduciendo cancion: "+getTitulo()+"\t Duracion:"+dur+"\t Autor:"+getAutor());
		player.play();
		Thread.sleep((long)(dur*1000));
		return;
	}
	
	/**
	 * Permite determinar la duracion de una cancion
	 * @return duracion de la cancion en segundos
	 */
	public double duracion() throws FileNotFoundException {
		return Mp3Player.getDuration(getArchivo());
	}
	
	/**
	 * Cadena que representa al objeto
	 * @return cadena
	 */
	public String toString() {
		double dur=0;
		try {
			dur = duracion();
		} catch (FileNotFoundException e) {
			System.out.println("Error en la lectura del archivo de audio\n");
		} 
		return (super.toString()+"\n\tID cancion: "+this.id+"\n\tFecha subida: "+this.fecha+
					"\n\tduracion: "+dur+" secs\n\tAutor: "+this.autor.getNombreUsuario()+
						"\n\tEstado: "+this.estado+"\n\tExplicita: "+this.explicita+
							"\n\tNota Admin: "+this.notaAdmin+"\n\tUbicacion: "+this.archivo+"\n\n" );
	}
}