package es.uam.eps.padsof.Music4all;
import java.util.*;

public class Notificacion {
	private String mensaje;
	private Date fecha;
	
	private Notificacion (String men, Date fec) {
		this.mensaje = men;
		this.fecha = fec;
	}
	
	public String getMensaje() {
		return this.mensaje;
	}
	
	public Date getFecha() {
		return this.fecha;
	}
}
