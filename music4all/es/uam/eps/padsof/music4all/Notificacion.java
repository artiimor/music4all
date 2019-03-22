package es.uam.eps.padsof.Music4all;
import java.util.*;

public class Notificacion {
	private String mensaje;
	private Date fecha;
	
	private Notificacion (String men) {
		this.mensaje = men;
		this.fecha = new Date();
	}
	
	public String getMensaje() {
		return this.mensaje;
	}
	
	public Date getFecha() {
		return this.fecha;
	}
}