import java.util.*;

public class Busqueda {
	private Usuario usuario;
	private EstadoBusqueda tipoBusqueda;
	private String cadena;
	private List<ElementoReproducible> elem;
	
	private Busqueda (Usuario user,EstadoBusqueda state, String texto) {
		this.usuario = user;
		this.tipoBusqueda = state;
		this.cadena = texto;
	}
	
	public Busqueda nuevaBusqueda(Usuario user,EstadoBusqueda state, String texto) {
		Busqueda bus = new Busqueda(user,state,texto);
		return bus;
	}
	
	private Usuario getUsuario() {
		return usuario;
	}
	
	private EstadoBusqueda getTipoBusqueda() {
		return tipoBusqueda;
	}
	
	private ElementoReproducible getElementoReproducible(int i) {
		return elem.get(i);
	}
	
	private int getNumelementos() {
		return elem.size();
	}
	
	private String getCadena() {
		return cadena;
	}
	
	private Boolean anadirElementos(List<ElementoReproducible> lelem) {
		elem = new ArrayList<ElementoReproducible>(lelem);
		return true;
	}
	
	private void mostrarResultados() {
		return;
	}
	
}
