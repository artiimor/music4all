package es.uam.eps.padsof.controlador;
import es.uam.eps.padsof.gui.Gui;
import es.uam.eps.padsof.music4all.*;
import java.time.LocalDate;
import java.util.*;
import javax.swing.JLabel;

public class Controlador {
	private Gui gui;
	private Aplicacion app;
	
	public Controlador(Gui gui, Aplicacion app) {
		this.gui = gui;
		this.app = app;
		app.actualizarApp(FechaSimulada.getHoy());
	}
	
	public void controladorLogin(String name, String passwd) {
		if (name.contentEquals("")) {
			gui.actionAdmin(app.login(passwd));
			return;
		}
		if (app.login(name,passwd)) {
			UsuarioRegistrado user = ((UsuarioRegistrado)app.getLoggedUser());
			if (user.getPrivilegios()==EstadoUsuario.PREMIUM) {
				gui.actionEntrar(true, EstadoUsuario.PREMIUM);
			} else if (user.getPrivilegios()==EstadoUsuario.ESTANDAR) {
				gui.actionEntrar(true, EstadoUsuario.ESTANDAR);
			}
		} else {
			if (app.getErrorLogin()==EstadoUsuario.BLOQUEADO) {
				gui.actionEntrar(false, EstadoUsuario.BLOQUEADO);
			} else if (app.getErrorLogin()==EstadoUsuario.BANEADO) {
				gui.actionEntrar(false, EstadoUsuario.BANEADO);
			}
		}
		gui.actionEntrar(false, EstadoUsuario.ESTANDAR);
		return;
	}
	
	public void controladorRegistro(String name, String passwd, LocalDate date) {
		gui.actionCompletarRegistro(app.registro(name, passwd, date));
		return;
	}
	
	public void controladorBuscar(String busqueda, String estado, Boolean usuarioRegistrado) {
		EstadoBusqueda estadoBus = EstadoBusqueda.PORALBUM;
		
		if (estado.contentEquals("Autor")) {
			estadoBus = EstadoBusqueda.PORAUTOR;
		} else if (estado.contentEquals("Album")) {
			estadoBus = EstadoBusqueda.PORALBUM;
		} else if (estado.contentEquals("Cancion")) {
			estadoBus = EstadoBusqueda.PORTITULO;
		}
		
		List<ElementoReproducible> resultado = app.getLoggedUser().realizarBusqueda(estadoBus, busqueda);
		List<Object[]> bus = new ArrayList<Object[]>();
		for (ElementoReproducible elem : resultado) {
			if (elem.getTipo()==TipoElementoReproducible.CANCION) {
				Cancion aux = (Cancion)elem;
				bus.add(new String[] {""});
				bus.add(new Object[] {aux.getTitulo(),aux.getAutor().getNombreUsuario(),aux.duracion()+"secs",
						aux.getId()});
			} else if (elem.getTipo()==TipoElementoReproducible.ALBUM) {
				Album aux = (Album)elem;
				List<Cancion> can = aux.getCanciones();
				bus.add(new String[] {""});
				bus.add(new Object[] {aux.getTitulo(),aux.getAutor().getNombreUsuario(),aux.duracion()+"secs",
						aux.getId()});
				for (Cancion c:can) {
					bus.add(new Object[] {c.getTitulo(),c.getAutor().getNombreUsuario(),c.duracion()+"secs",
							c.getId()});
				}
			}
		}
		
		if (usuarioRegistrado) {
			if (((UsuarioRegistrado)app.getLoggedUser()).getPrivilegios()==EstadoUsuario.ESTANDAR) {
				gui.actionBuscarUsuario(bus,busqueda,true);
			} else if (((UsuarioRegistrado)app.getLoggedUser()).getPrivilegios()==EstadoUsuario.PREMIUM) {
				gui.actionBuscarUsuario(bus,busqueda,false);
			}
		} else {
			gui.actionBuscar(bus,busqueda);
		}
		return;
	}
	
	public void controladorSubirCancion(String nombre, String ubicacion) {
		UsuarioRegistrado currentUser = (UsuarioRegistrado) app.getLoggedUser();
		gui.actionAniadirCancion(currentUser.subirCancion(nombre, ubicacion));
		return;
	}
	
	public void controladorSubirAlbum(String nombre, Integer[] canciones) {
		List<Cancion> can = new ArrayList<Cancion>();
		UsuarioRegistrado currentUser = (UsuarioRegistrado) app.getLoggedUser();
		for (Integer aux: canciones) {
			can.add((Cancion)currentUser.getElementoReproducible(aux));
		}
		gui.actionAniadirAlbum(currentUser.nuevoAlbum(nombre, can));
		return;
	}
	
	public void controladorSubirPlaylist(String nombre, Integer[] canciones) {
		List<ElementoReproducible> can = new ArrayList<ElementoReproducible>();
		UsuarioRegistrado currentUser = (UsuarioRegistrado) app.getLoggedUser();
		for (Integer aux: canciones) {
			can.add(app.getElementoReproducible(aux));
		}
		for(Playlist p : currentUser.getPlaylist()) {
			if(p.getTitulo().equals(nombre)){
				p.anadirCanciones(can);
				gui.actionAniadirPlaylist(true);
				return;
			}
		}
		gui.actionAniadirPlaylist(currentUser.nuevaPlaylist(nombre, can));
		return;
	}
	
	public void controladorDatosUsuario(JLabel nombre, JLabel reproducciones) {
		UsuarioRegistrado currentUser = (UsuarioRegistrado) app.getLoggedUser();	
		nombre.setText("Bienvenido, " + currentUser.getNombreUsuario());
		reproducciones.setText("Rep. restantes : " + (currentUser.getLimite()-currentUser.getContador()));
	}
	
	public List<Object[]> controladorGetSubidas() {
		UsuarioRegistrado currentUser = (UsuarioRegistrado) app.getLoggedUser();
		List<ElementoReproducible> musica = currentUser.getElementosUsuario(true);
		List<Object[]> content = new ArrayList<Object[]>();
		
		for (ElementoReproducible elem : musica) {
			if (elem.getTipo()==TipoElementoReproducible.ALBUM) {
				Album alm = ((Album)elem);
				content.add(new String[] {""});
				content.add(new Object[] {alm.getTitulo(),alm.duracion()+"secs",alm.getFecha().toString()
						,alm.getEstadoAlbum().toString(),alm.getId()});
				List<Cancion> album = ((Album)(elem)).getCanciones();
				for (Cancion aux: album) {
					content.add(new Object[] {aux.getTitulo(),aux.duracion()+"secs",aux.getFecha().toString(),
							aux.getEstado().toString(),aux.getId()});
				}
			} else if (((Cancion)(elem)).getEstado()!=EstadoCancion.RECHAZADA) {
				Cancion can = ((Cancion)elem);
				content.add(new String[] {""});
				content.add(new Object[] {can.getTitulo(),can.duracion()+"secs",can.getFecha().toString()
						,can.getEstado().toString(),can.getId()});
	
			}
		}
		return content;
 	}
	
	public Object[][] controladorGetValidaciones() {
		Administrador admin = app.getAdministrador();
		List<Cancion> validaciones = admin.getCancionesPendientes();
		Object[][] content = new Object[validaciones.size()][6];
		int i=0;
		for (Cancion aux:validaciones) {
			content[i][0] = aux.getTitulo();
			content[i][1] = aux.getAutor().getNombreUsuario();
			content[i][2] = aux.duracion()+"secs";
			content[i][3] = aux.getFecha().toString();
			content[i][4] = aux.getArchivo();
			content[i][5] = aux.getId();
			i++;
		}
		return content;
 	}
	
	public String[][] controladorGetNotificaciones() {
		UsuarioRegistrado user = (UsuarioRegistrado)app.getLoggedUser();
		List<Notificacion> not = user.mostrarSubscripciones();
		String[][] content = new String[not.size()][2];
		int i=0;
		for (Notificacion aux:not) {
			content[i][0] = aux.getMensaje();
			content[i][1] = aux.getFecha().toString();
			i++;
		}
		return content;
 	}
	
	public List<Object[]> controladorGetPlayList() {
		UsuarioRegistrado currentUser = (UsuarioRegistrado) app.getLoggedUser();
		List<ElementoReproducible> playlist = currentUser.getElementosUsuario(false);
		System.out.println(playlist);
		List<Object[]> content = new ArrayList<Object[]>();

		for (ElementoReproducible aux: playlist) {
			content.add(new String[] {""});
			content.add(new Object[] {aux.getTitulo(),"",aux.duracion()+"secs",aux.getId()});
			List<ElementoReproducible> elem = ((Playlist)aux).getElementos();
			for (ElementoReproducible a: elem) {
				if (a.getTipo()==TipoElementoReproducible.ALBUM) {
					Album alm = ((Album)a);
					content.add(new String[] {"n"});
					content.add(new Object[] {alm.getTitulo(),alm.getAutor().getNombreUsuario(),alm.duracion()+"secs"+
					alm.getId()});
					List<Cancion> album = alm.getCanciones();
					for (Cancion c: album) {
						content.add(new Object[] {c.getTitulo(),c.getAutor().getNombreUsuario(),
								c.duracion()+"secs",c.getId()});
					}
				} else if (a.getTipo()==TipoElementoReproducible.CANCION){
					Cancion can = ((Cancion)a);
					content.add(new String[] {"n"});
					content.add(new Object[] {can.getTitulo(),can.getAutor().getNombreUsuario(),
							can.duracion()+"secs",can.getId()});
				} 
			}	
		}
		return content;
	}
	
	public void controladorReproducirElementosUser(List<Integer> canciones) {
		UsuarioRegistrado currentUser = (UsuarioRegistrado) app.getLoggedUser();
		for (Integer aux: canciones) {
			currentUser.getElementoReproducible(aux).reproducir(currentUser);
		}
	}
	
	public void controladorPagoPremium(String tarjeta) {
		UsuarioRegistrado currentUser = (UsuarioRegistrado) app.getLoggedUser();
		gui.actionPagoRealizado(currentUser.pagarPremium(tarjeta));
		return;
	}
	
	public void controladorReproducir(List<Integer> id) {
		Usuario currentUser = app.getLoggedUser();
		for (Integer aux : id) {
			app.getElementoReproducible(aux).reproducir(currentUser);
		}
		if (currentUser.getTipoUsuario()==TipoUsuario.GRATUITO) {
			if (app.getAdministrador()==null) {
				gui.actionInicial();
			}
		}
		return;
	}
	
	public void controladorAdministrarDenuncia(Integer idPlagio, Integer idOriginal, Boolean plagio) {
		Administrador admin = app.getAdministrador();
		NotificacionPlagio pl = admin.getDenuncia(idPlagio, idOriginal);
		if (pl!=null) {
			if (plagio) {
				gui.actionAdministrarDenuncia(admin.validarDenuncia(pl));
			} else {
				gui.actionAdministrarDenuncia(admin.rechazarDenuncia(pl));
			}
		}
	}

	public Boolean controladorConfirmarCanciones(Integer[] identificadores) {
		Boolean bool = false;
		UsuarioRegistrado currentUser = (UsuarioRegistrado) app.getLoggedUser();
		for (int aux: identificadores) {
			if (currentUser.getElementoReproducible(aux).getTipo()!=TipoElementoReproducible.CANCION ||
					currentUser.albumContainsCancion(aux)==true) {
				return false;
			}
			bool = true;
		}
		return bool;
	}
	
	public void controladorReproducirAdmin(int id) {
		Administrador admin = app.getAdministrador();
		List<Cancion> val = admin.getCancionesPendientes();
		for (Cancion aux: val) {
			if (aux.getId()==id) {
				aux.reproducir(app.getLoggedUser());
			}
		}
	}
	
	public void controladorValidar(int id, String estado) {
		Administrador admin = app.getAdministrador();
		Boolean explicita=false;
		if (estado.contentEquals("Normal")) {
			explicita = false;
		} else if (estado.contentEquals("Explicita")) {
			explicita= true;
		}
		
		gui.actionValidarCancion(admin.validarCancion(admin.getCancionPendiente(id), explicita));
	}
	
	public void controladorRechazar(int id,String notaAdmin) {
		Administrador admin = app.getAdministrador();	
		gui.actionRechazada(admin.rechazarCancion(admin.getCancionPendiente(id), notaAdmin));
	}
	
	public List<Object[]> controladorGetDenuncias() {
		Administrador admin = app.getAdministrador();
		List<NotificacionPlagio> denuncias = admin.getDenunciasPendientes();
		List<Object[]> content = new ArrayList<Object[]>();
		for (NotificacionPlagio aux : denuncias) {
			Cancion canO = aux.getCancionOriginal();
			Cancion canP = aux.getCancionDenunciada();
			content.add(new String[] {""});
			content.add(new String[] {"Denuncia realizada por: "+aux.getDenunciante().getNombreUsuario(),"",});
			content.add(new Object[] {canO.getTitulo(),canO.getAutor().getNombreUsuario(),canO.duracion()+"secs",
					canO.getFecha().toString(),canO.getArchivo(),canO.getId()});
			content.add(new Object[] {canP.getTitulo(),canP.getAutor().getNombreUsuario(),canP.duracion()+"secs",
					canP.getFecha().toString(),canP.getArchivo(),canP.getId()});
		}
		return content;
 	}
	
	public Object[][] controladorGetRechazadas() {
		UsuarioRegistrado user = (UsuarioRegistrado)app.getLoggedUser();
		List<Cancion> rectificaciones = user.mostrarCancionesARectificar();
		Object[][] content = new String[rectificaciones.size()][6];
		int i=0;
		for (Cancion aux:rectificaciones) {
			content[i][0] = aux.getTitulo();
			content[i][1] = aux.duracion()+"secs";
			content[i][2] = aux.getFecha().toString();
			content[i][3] = aux.getEstado().toString();
			content[i][4] = aux.getNotaAdmin();
			content[i][5] = aux.getId();
			i++;
		}
		return content;
 	}
	
	public void controladorRectificarCancion(int id, String ubicacion) {
		UsuarioRegistrado currentUser = (UsuarioRegistrado) app.getLoggedUser();
		gui.actionAniadirCancion(currentUser.rectificarCancion((Cancion)currentUser.getElementoReproducible(id), 
				ubicacion));
		return;
	}
	/*
	public void controladorEliminarCancion(String cancion) {
		UsuarioRegistrado currentUser = (UsuarioRegistrado)app.getLoggedUser();
		if (currentUser.getElementoReproducible(cancion).getTipo() == TipoElementoReproducible.ALBUM) {
			return;
		}
		currentUser.eliminarCancion((Cancion)currentUser.getElementoReproducible(cancion));
		gui.actionEliminarCancion();
	}
*/
	public Boolean controladorNuevaDenuncia(String Original, String uPlagiado, String Plagio, String uDenunciado) {
		UsuarioRegistrado currentUser = (UsuarioRegistrado) app.getLoggedUser();
		UsuarioRegistrado userP = app.getUsuario(uPlagiado);
		UsuarioRegistrado userD = app.getUsuario(uDenunciado);
		if (userP!=null) {
			if (userD!=null) {
				if (userP.elementoSubido(Original)!=null && userD.elementoSubido(Plagio)!=null) {
					gui.actionRealizarDenuncia(currentUser.denunciarCancion
						(userD.elementoSubido(Plagio),userP.elementoSubido(Original)));
					return true;
				}
			}
		}
		return false;
	}
	
	public void controladorSuscribirse(String name) {
		UsuarioRegistrado currentUser = (UsuarioRegistrado) app.getLoggedUser();
		UsuarioRegistrado sus = app.getUsuario(name);
		if (sus!=null) {
			gui.actionFinalizarSuscripcion(currentUser.nuevaSubscripcion(sus));
		}
	}
	
	public void controladorLogout() {
		app.logout();
		app.backup();
		gui.actionInicial();
	}
	
	public void controladorBackup() {
		app.logout();
		app.backup();
	}
}
