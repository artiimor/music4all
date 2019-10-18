package es.uam.eps.padsof.music4all;

import java.io.Serializable;

/**
 * Esta clase representa el objeto NotificacionPlagio
 *
 * @author Aitor Martin
 * @author Juan Carlos Merida
 * @author Arturo Morcillo
 * @version 1.0
 *
 */
public class NotificacionPlagio implements Serializable{
	private static final long serialVersionUID = 1L;
	//datos de la clase NotificacionPlagio
	UsuarioRegistrado denunciante;
	Cancion cancionDenunciada;
	Cancion cancionOriginal;
	
	/**
	 * Constructor de la clase NotificacionPlagio
	 * @param den Usuario denunciante
	 * @param cDen Cancion que ha plagiado
	 * @param cOri Cancion original
	 */
	public NotificacionPlagio (UsuarioRegistrado den, Cancion cDen, Cancion cOri) {
		this.denunciante = den;
		this.cancionDenunciada = cDen;
		this.cancionOriginal = cOri;
	}
	
	/**
	 * Devuelve la cancion denunciada
	 * @return cancion
	 */
	public Cancion getCancionDenunciada() {
		return this.cancionDenunciada;
	}
	
	/**
	 * Devuelve la cancion original
	 * @return cancion
	 */
	public Cancion getCancionOriginal() {
		return this.cancionOriginal;
	}
	
	/**
	 * Devuelve el usuario que ha denunciado el plagio
	 * @return usuario
	 */
	public UsuarioRegistrado getDenunciante() {
		return this.denunciante;
	}
	
	/**
	 * Metodo que valida una denuncia
	 * @return true si la denuncia ha sido validada, false en caso contrario
	 */
	public Boolean validarDenuncia() {			
		Cancion cden = getCancionDenunciada();
		UsuarioRegistrado autor = cden.getAutor();
		cden.setEstado(EstadoCancion.BLOQUEADA);
		autor.setPrivilegios(EstadoUsuario.BLOQUEADO);
		return true;
	}
	
	/**
	 * Metodo que rechaza una denuncia
	 * @return true si la denuncia ha sido rechazada, false en caso contrario
	 */
	public Boolean rechazarDenuncia() {
		getDenunciante().setPrivilegios(EstadoUsuario.BANEADO);
		getDenunciante().setFechaBaneo(FechaSimulada.getHoy());
		Aplicacion.getInstance().addBaneo(getDenunciante());
		return true;
	}
	
	/**
	 * Cadena que representa al objeto
	 * @return cadena
	 */
	public String toString() {
		return ("\n\tUsuario denunciante: "+this.denunciante.getNombreUsuario()+
				"\n\tCancion denunciada: "+this.cancionDenunciada.getTitulo()+
				"\n\tCancion original: "+this.cancionOriginal.getTitulo()+"\n");
	}
}