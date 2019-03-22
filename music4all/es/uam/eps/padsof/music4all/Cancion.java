/**
 * Aqui se administra la clase Cancion
 * 
 * @author Aitor Martin, Juan Carlos Mérida y Arturo Morcillo
 * 
 */
import java.sql.Date;



public class Cancion extends ElementoReproducible {
	int id;
	String titulo;
	Date fecha;
	String archivo;
	int tamanno;
	UsuarioRegistrado autor;
	EstadoCancion estado;
	boolean explicita;
	String notaAdmin;
	
	
	//CONSTRUCTOR
	/**
	 * 
	 * Constructor de la clase cancion
	 * 
	 * @param id
	 * @param titulo
	 * @param fecha
	 * @param archivo
	 * @param tamanno
	 * @param autor
	 * @param estado
	 * @param explicita
	 * @param notaAdmin
	 */
	public Cancion (int id,String titulo ,Date fecha, String archivo, int tamanno, UsuarioRegistrado autor, EstadoCancion estado, boolean explicita, String notaAdmin){
		this.id = id;
		this.titulo = titulo;
		this.fecha = fecha;
		this.archivo = archivo;
		this.tamanno = tamanno;
		this.autor = autor;
		this.estado = estado;
		this.explicita = explicita;
		this.notaAdmin = notaAdmin;
	}
	
	
	
	//METODOS SETTERS
	
	/**
	 * setter de id. Cambia el id
	 * 
	 * @param newId
	 */
	void setId (int newId) {
		this.id = newId;
	}
	

	 /**
	  * setter de fecha. Cambia la fecha de subida
	  * 
	  * 
	  * @param newFecha
	  */
	 void setFecha(Date newFecha) {
		 this.fecha = newFecha;
	 }
	 
	 /**
	  * setter de Archivo. Cambia la ruta del archivo mp3
	  * 
	  * 
	  * @param newArchivo
	  */
	 void setArchivo(String newArchivo) {
		 this.archivo = newArchivo;
	 }
	 
	 /**
	  * setter de tamanno. Cambia el tamanno
	  * 
	  * 
	  * @param newTamanno
	  */
	 void setTamanno(int newTamanno) {
		 this.tamanno = newTamanno;
	 }
	 
	 /**
	  * setter de autor. Cambia el autor
	  * 
	  * 
	  * @param newAutor
	  */
	 void setAutor (UsuarioRegistrado newAutor) {
		 this.autor = newAutor;
	 }
	 
	 /**
	  * setter de Estado. Cambia el estado en el que se encuentra la cancion
	  * @param newEstado
	  */
	 void setEstado(EstadoCancion newEstado) {
		 this.estado = newEstado;
	 }
	 
	 /**
	  * setter de Explicita. Cambia si una cancion es explicita o no
	  * 
	  * 
	  * @param newExplicita
	  */
	 void setExplicita (boolean newExplicita) {
		 this.explicita = newExplicita;
	 }
	 
	 /**
	  * setter de NotaAdmin. Cambia la nota que tiene la cancion
	  * 
	  * 
	  * @param newNotaAdmin
	  */
	 void setNotaAdmin (String newNotaAdmin) {
		 this.notaAdmin = newNotaAdmin;
	 }

	
	
	//METODOS GETTERS
	
	 /**
		 * getter de id. Devuelve el id
		 * 
		 *
		 *@return int con el id de la cancion
		 */
		int setId () {
			return this.id;
		}

		 /**
		  * getter de fecha. Devuelve la fecha de subida
		  * 
		  * 
		  * @return Date con la fecha de subida
		  */
		 Date getFecha() {
			 return this.fecha;
		 }
		 
		 /**
		  * getter de Archivo. devuelve la ruta del archivo mp3
		  * 
		  * 
		  * @return String con la ruta del archivo
		  */
		 String getArchivo() {
			 return this.archivo;
		 }
		 
		 /**
		  * getter de tamanno. devuelve el tamanno
		  * 
		  * 
		  * @return int con el tamanno
		  */
		 int sgetTamanno() {
			 return this.tamanno;
		 }
		 
		 /**
		  * getter de autor. Devuelve el autor
		  * 
		  * 
		  * @return UsuarioRegistrado con el autor
		  */
		 UsuarioRegistrado getAutor () {
			 return this.autor;
		 }
		 
		 /**
		  * getter de Estado. Devuelve el estado en el que se encuentra la cancion
		  * 
		  * 
		  * @return EstadoCancion con el estado de la cancion
		  */
		 EstadoCancion getEstado() {
			 return this.estado;
		 }
		 
		 /**
		  * getter de Explicita. Devuelve si una cancion es explicita o no
		  * 
		  * 
		  * @return boolean si es explicita o no
		  */
		 boolean getExplicita () {
			 return this.explicita;
		 }
		 
		 /**
		  * getter de NotaAdmin. Devuelve la nota que tiene la cancion
		  * 
		  * 
		  * @return String con la nota del admin
		  */
		 String getNotaAdmin () {
			 return this.notaAdmin;
		 }
	
	
		 
		 
	//METODOS DEL DIAGRAMA DE CLASES
	/**
	 * nuevaCancion se encarga de crear una cancion con los parametros
	 * introducidos llamando al constructor de cancion
	 * 
	 * @param id
	 * @param titulo
	 * @param fecha
	 * @param archivo
	 * @param tamanno
	 * @param autor
	 * @param admin admin con la lista de canciones pendientes de validacion
	 * @return bool si ha ido todo bien o no
	 */
	boolean nuevaCancion (int id, String titulo, Date fecha, String archivo,int tamanno ,UsuarioRegistrado autor, Administrador admin) {
		Cancion nuevaCancion;
		
		nuevaCancion = new Cancion(id, titulo, fecha, archivo, tamanno, autor, EstadoCancion.PENDIENTEVALIDACION, false, null);
		
		//control de errores
		if(nuevaCancion == null)
			return false;
		
		//añadir a la lista de canciones pendientes de validacion
		admin.anadirCancion(nuevaCancion);
		
		return true;
	}
	
	/**
	 * borrarCancion se encarga de eliminar una cancion
	 */
	
	void borrarCancion() {
		/*TODO hacer esta*/
	}
	
	/**
	 * Permite a un usuario volver a subir una cancion en el caso de que 
	 * haya sido rechazada en un primer lugar
	 * 
	 * @param newArchivo
	 * @param admin 
	 * @return boolean si ha ido todo bien o no
	 */
	boolean rectificarCancion(String newArchivo, Administrador admin) {
		if (this.notaAdmin == null || this.estado != EstadoCancion.NOVALIDADA) {
			return false;
		}
		
		this.archivo = newArchivo;
		this.estado = EstadoCancion.PENDIENTEVALIDACION;
		this.notaAdmin = null;
		
		admin.anadirCancion(this);
		return true;
	}
	
	/**
	 * Permite poner una cancion como bloqueada
	 * 
	 * @return bool si ha ido todo bien o no
	 */
	boolean bloquearCancion() {
		if (this.estado == EstadoCancion.BLOQUEADA)
			return false;
		
		this.estado = EstadoCancion.BLOQUEADA;
		return true;
	}
	
	/**
	 * Permite reproducir una cancion
	 * 
	 * @return boolean si ha ido todo bien o no
	 */
	boolean reproducirCancion() {
		/*TODO hacer esta*/
	}
	
	
	
	
	
	
}