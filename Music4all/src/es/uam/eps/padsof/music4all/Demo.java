package es.uam.eps.padsof.music4all;

import java.util.*;
import java.time.LocalDate;

public class Demo{
	
	@SuppressWarnings("unused")
	public static void main (String[] args) {
		//______________________________________________________________________________
		//___________________Comenzamos por crear la aplicacion_________________________
		//______________________________________________________________________________
		
		System.out.println("Creamos la aplicacion:");
		Aplicacion aplicacion = Aplicacion.getInstance();
		
		if(aplicacion == null) {
			System.out.println("[ERROR] La aplicacion no se ha creado");
			return;
		}
		
		System.out.println("[OK] La aplicacion se ha creado de forma satisfactoria:\nContiene la siguiente informacion:\n"+aplicacion+"\n\n");
		
		System.out.println("Prueba de crear usuarios:");
		
		if (aplicacion.registro("SenorX", "passSr1",LocalDate.now().withDayOfMonth(4).withMonth(8).withYear(1989))) {
			System.out.println("[OK] Se ha creado al usuario1 de forma correcta");
		} else {
			System.out.println("[ERROR] no se ha creado al usuario1 de forma correcta");
		}
		if (aplicacion.registro("SenorY", "passSr2",LocalDate.now().withDayOfMonth(3).withMonth(2).withYear(1997))) {
			System.out.println("[OK] Se ha creado al usuario1 de forma correcta");
		} else {
			System.out.println("[ERROR] no se ha creado al usuario1 de forma correcta");
		}
		
		System.out.println("anadimos los usuarios a la aplicacion y hacemos las pruebas para loguearse:\n");
		
		//______________________________________________________________________________
		//_____________________________Pruebas de login_________________________________
		//______________________________________________________________________________
		
		if(!aplicacion.login("SenorX", "passSr1")) {
			System.out.println("[ERROR al loguearse el usuario1]");
			return;
		}
		System.out.println("[OK] El usuario 1 se ha logueado de forma correcta.\n La aplicacion ahora mismo es de la siguiente forma:\n"+aplicacion+"\n");
		
		aplicacion.logout();
		
		if(aplicacion.getLoggedUser().getTipoUsuario() == TipoUsuario.GRATUITO) {
			System.out.println("[ERROR] no se ha hecho logout de forma correcta");
		}
		System.out.println("[OK] Se ha hecho logout.\nAhora la aplicacion tiene la siguiente informacion:\n"+aplicacion+"\n");
		
		if(!aplicacion.login("SenorY", "passSr2")) {
			System.out.println("[ERROR al loguearse el usuario1]");
			return;
		}
		System.out.println("[OK] El usuario 2 se ha logueado de forma correcta.\n La aplicacion ahora mismo es de la siguiente forma:\n"+aplicacion+"\n");
		
		aplicacion.logout();
		
		if(aplicacion.getLoggedUser().getTipoUsuario() == TipoUsuario.GRATUITO) {
			System.out.println("[ERROR] no se ha hecho logout de forma correcta");
		}
		System.out.println("[OK] Se ha hecho logout.\nAhora la aplicacion tiene la siguiente informacion:\n"+aplicacion+"\n");
		
		//Ahora probamos loguearnos con un usuario que no esta en la aplicacion
		if(aplicacion.login("senory", "pass") || !(aplicacion.getLoggedUser().getTipoUsuario() == TipoUsuario.GRATUITO)) {
			System.out.println("[ERROR] Nos hemos logueado con un usuario que no existe");
			return;
		}
		
		System.out.println("\n[OK] No hemos podido loguearnos con un usuario que no existe.\n\n");

		//______________________________________________________________________________
		//______________________Añadimos canciones a la app_____________________________
		//______________________________________________________________________________

		System.out.println("anadimos dos nuevas canciones a la lista que el admin tiene que validar");
		
		if(!aplicacion.login("SenorX", "passSr1")) {
			System.out.println("[ERROR al loguearse el usuario1]");
			return;
		}
		System.out.println("[OK] El usuario 1 se ha logueado de forma correcta.");
		UsuarioRegistrado user1 = (UsuarioRegistrado)aplicacion.getLoggedUser();
		
		if (!user1.subirCancion("chicle3", "chicle3.mp3")) {
			System.out.println("[ERROR] La cancion chicle3 no se ha anadido de forma correcta");
			return;
		}
		
		aplicacion.logout();
		user1 = null;
		
		if(!aplicacion.login("SenorY", "passSr2")) {
			System.out.println("[ERROR al loguearse el usuario2]");
			return;
		}
		System.out.println("[OK] El usuario 2 se ha logueado de forma correcta.");
		UsuarioRegistrado user2 = (UsuarioRegistrado)aplicacion.getLoggedUser();
		
		if (!user2.subirCancion("hive", "hive.mp3")) {
			System.out.println("[ERROR] La cancion hive no se ha anadido de forma correcta");
			return;
		}
		
		aplicacion.logout();
		user2 = null;
		
		System.out.println("[OK] Las canciones se han añadido de forma correcta al administrador:\n");
		aplicacion.logout();
		
		//______________________________________________________________________________
		//_____________________Pruebas de validar y rechazar____________________________
		//______________________________________________________________________________
		System.out.println("Ahora haremos login como administrador para validar las canciones");
		
		if(!aplicacion.login("password123")) {
			System.out.println("[ERROR al loguearse el admin]");
			return;
		}
		System.out.println("[OK] El admin se ha logueado de forma correcta.");
		
		Administrador admin = aplicacion.getAdministrador();
		List <Cancion> pendientes = admin.getCancionesPendientes();

		for(Cancion cancion : pendientes) {
			System.out.println(cancion+"\n");
		}
		
		admin.validarCancion(pendientes.get(0));
		admin.rechazarCancion(pendientes.get(0),"Cancion rechazada");

		if(!admin.getCancionesPendientes().isEmpty()) {
			System.out.println("[ERROR] Administrador sigue teniendo canciones");
			return;
		}
	
		System.out.println("[OK] Las canciones han sido administradas de forma correcta");
		admin = null;
		pendientes = null;
		aplicacion.logout();

		//______________________________________________________________________________
		//___________________Rectificamos una cancion y la aceptamos____________________
		//______________________________________________________________________________
		
		if(!aplicacion.login("SenorY", "passSr2")) {
			System.out.println("[ERROR al loguearse con el usuario1]");
			return;
		}
		
		System.out.println("[OK] El usuario2 se ha logueado de forma correcta.");
		
		user2 = (UsuarioRegistrado)aplicacion.getLoggedUser();
			
		System.out.println("\nCanciones a rectificar para el usuario2: ");
		for(Cancion cancion : ((UsuarioRegistrado)user2).getARectificar()) {
			System.out.println(cancion);
		}

		System.out.println("Ahora haremos que el usuario rectifique la cancion rechazada");
		user2.rectificarCancion(user2.getARectificar().get(0), "hive.mp3");
		
		if(!(user2.getARectificar().isEmpty())) {
			System.out.println("[ERROR] No se ha rectificado de forma correcta la cancion");
			return;
		}
		
		aplicacion.logout();
		user2 = null;
		
		if(!aplicacion.login("password123")) {
			System.out.println("[ERROR al loguearse el admin]");
			return;
		}
		System.out.println("[OK] El admin se ha logueado de forma correcta.");
		
		admin = aplicacion.getAdministrador();
		System.out.println("[OK] La cancion se ha rectificado y esta en la lista de pendientes del administrador:\n"+ 
		admin.getCancionesPendientes());
		pendientes = admin.getCancionesPendientes();
		admin.validarCancionExplicita(pendientes.get(0));

		if(!aplicacion.getAdministrador().getCancionesPendientes().isEmpty()) {
			System.out.println("[ERROR] La cancion no se ha aprobado correctamente");
			return;
		}
		
		System.out.println("[OK] Las canciones han sido administradas de forma correcta");
		admin = null;
		pendientes = null;
		aplicacion.logout();

		//______________________________________________________________________________
		//__________________________Pruebas de las denuncias____________________________
		//______________________________________________________________________________

		//Creamos dos notificaciones de plagio. Una la aceptamos y otra no
		
		System.out.println("Ahora vamos a ver que tal funcionan las denuncias por plagio:\n");
	}
}