import java.util.*;

public class NotificacionPlagio {
	UsuarioRegistrado denunciante;
	Cancion cancionDenunciada;
	Cancion cancionOriginal;
	EstadoDenuncia estado;
	
	public NotificacionPlagio (UsuarioRegistrado den, Cancion cDen, Cancion cOri, EstadoDenuncia e) {
		this.denunciante = den;
		this.cancionDenunciada = cDen;
		this.cancionOriginal = cOri;
		this.estado = e;
	}
	
	public NotificacionPlagio nuevaDenuncia (UsuarioRegistrado den, Cancion cDen, Cancion cOri, EstadoDenuncia e) {
		NotificacionPlagio not = new NotificacionPlagio(den,cDen,cOri,e);
		return not;
	}
	
	public void setEstado(EstadoDenuncia estado) {
		this.estado = estado;
	}
	
	public Boolean validarDenuncia() {
		setEstado(EstadoDenuncia.LEGITIMA);
		return true;
	}
	
	public Boolean rechazarDenuncia() {
		setEstado(EstadoDenuncia.NOLEGITIMA);
		return true;
	}
}