package es.uam.eps.padsof.test;

import java.util.*;

import es.uam.eps.padsof.music4all.Administrador;
import es.uam.eps.padsof.music4all.Aplicacion;
import es.uam.eps.padsof.music4all.Cancion;
import es.uam.eps.padsof.music4all.ElementoReproducible;
import es.uam.eps.padsof.music4all.EstadoBusqueda;
import es.uam.eps.padsof.music4all.FechaSimulada;
import es.uam.eps.padsof.music4all.NotificacionPlagio;
import es.uam.eps.padsof.music4all.TipoUsuario;
import es.uam.eps.padsof.music4all.Usuario;
import es.uam.eps.padsof.music4all.UsuarioRegistrado;
import es.uam.eps.padsof.telecard.FailedInternetConnectionException;
import es.uam.eps.padsof.telecard.InvalidCardNumberException;
import es.uam.eps.padsof.telecard.OrderRejectedException;
import pads.musicPlayer.exceptions.Mp3PlayerException;
import java.io.IOException;
import java.time.LocalDate;

public class Demo{
	
	public static void main (String[] args) throws InterruptedException, Mp3PlayerException, InvalidCardNumberException, FailedInternetConnectionException, OrderRejectedException, IOException, ClassNotFoundException{
		//______________________________________________________________________________
		//___________________Comenzamos por crear la aplicacion_________________________
		//______________________________________________________________________________
		System.out.println("Hola Jaime, bienvenido a la demo de Music4all !!!!\nEsperamos que le guste!!!\n\n");
		Thread.sleep(0);
		System.out.println("--------------------------------------------------");
		System.out.println("\tPrueba de creacion de la aplicacion");
		System.out.println("--------------------------------------------------");
		System.out.println("Creamos la aplicacion:");
		
		Aplicacion aplicacion = Aplicacion.getInstance();
		
		if(aplicacion == null) {
			System.out.println("[ERROR] La aplicacion no se ha creado");
			return;
		}
		
		System.out.println("[OK] La aplicacion se ha creado de forma satisfactoria:\n");
		
		System.out.println("La aplicacion contiene la siguiente informacion:\n"+aplicacion+"\n\n");
		Thread.sleep(3000);
		
		//______________________________________________________________________________
		//_________________________Pruebas de registro y logueo_________________________
		//______________________________________________________________________________
		
		System.out.println("--------------------------------------------------");
		System.out.println("\t  Prueba de registro y logueo");
		System.out.println("--------------------------------------------------");
		System.out.println("Añadimos dos usuarios a la aplicacion:\n");
		
		if (aplicacion.registro("SenorX", "passSr1",LocalDate.now().withDayOfMonth(4).withMonth(8).withYear(1989))) {
			System.out.println("[OK] Se ha creado al usuario1 de forma correcta");
		} else {
			System.out.println("[ERROR] no se ha creado al usuario1 de forma correcta");
			return;
		}
		if (aplicacion.registro("SenorY", "passSr2",LocalDate.now().withDayOfMonth(3).withMonth(2).withYear(2005))) {
			System.out.println("[OK] Se ha creado al usuario2 de forma correcta");
		} else {
			System.out.println("[ERROR] no se ha creado al usuario2 de forma correcta");
			return;
		}
		
		Thread.sleep(0);
		System.out.println("Los usuario se han dado de alta correntamente\n\n");
		
		System.out.println("Demostracion del funcionamiento de loguearse: \n");
		if(!aplicacion.login("SenorX", "passSr1")) {
			System.out.println("[ERROR al loguearse el usuario1]");
			return;
		}
		
		Thread.sleep(0);
		System.out.println("[OK] El usuario 1 se ha logueado de forma correcta.\n La aplicacion ahora mismo es de la siguiente forma:\n");
		Thread.sleep(0);
		System.out.println(aplicacion);
		Thread.sleep(0);
		aplicacion.logout();
		
		if(aplicacion.getLoggedUser().getTipoUsuario() != TipoUsuario.GRATUITO) {
			System.out.println("[ERROR] no se ha hecho logout de forma correcta");
			return;
		}
		
		System.out.println("[OK] Se ha hecho logout.\n\nAhora la aplicacion tiene la siguiente informacion:\n");
		Thread.sleep(0);
		System.out.println(aplicacion+"\n");
		Thread.sleep(0);
		
	
		if(!aplicacion.login("SenorY", "passSr2")) {
			System.out.println("[ERROR al loguearse el usuario1]");
			return;
		}
		
		System.out.println("[OK] El usuario 2 se ha logueado de forma correcta.\nLa aplicacion ahora mismo es de la siguiente forma:\n");
		Thread.sleep(0);
		System.out.println(aplicacion);
		Thread.sleep(0);
		aplicacion.logout();
		
		if(aplicacion.getLoggedUser().getTipoUsuario() != TipoUsuario.GRATUITO) {
			System.out.println("[ERROR] no se ha hecho logout de forma correcta");
			return;
		}
		
		System.out.println("[OK] Se ha hecho logout.\n\nAhora la aplicacion tiene la siguiente informacion:\n");
		Thread.sleep(0);
		System.out.println(aplicacion+"\n");
		Thread.sleep(0);
	
		//Ahora probamos loguearnos con un usuario que no esta en la aplicacion
		if(aplicacion.login("senorz", "pass") || !(aplicacion.getLoggedUser().getTipoUsuario() == TipoUsuario.GRATUITO)) {
			System.out.println("[ERROR] Nos hemos logueado con un usuario que no existe");
			return;
		}
		
		System.out.println("[OK] No hemos podido loguearnos con un usuario que no existe.\n");
		
		System.out.println("Nos logueamos con el admin:\n");
		Thread.sleep(0);
		
		aplicacion.login("password123");
		
		if(aplicacion.getAdministrador() == null) {
			System.out.println("[ERROR] No nos hemos podido loguear con el administrador");
			return;
		}
		
		System.out.println("[OK] Nos hemos logueado con el administrador.");
		aplicacion.logout();
		System.out.println("[OK] Se ha hecho logout.\n\n");
		Thread.sleep(3000);

		//______________________________________________________________________________
		//______________________Añadimos canciones a la app_____________________________
		//______________________________________________________________________________

		System.out.println("--------------------------------------------------");
		System.out.println("\t  Prueba subir canciones");
		System.out.println("--------------------------------------------------");
		
		System.out.println("Añadimos dos nuevas canciones a la lista que el admin tiene que validar\n");
		
		if(!aplicacion.login("SenorX", "passSr1")) {
			System.out.println("[ERROR al loguearse el usuario1]");
			return;
		}
		System.out.println("[OK] El usuario 1 se ha logueado de forma correcta.");
		Thread.sleep(0);
		UsuarioRegistrado user1 = (UsuarioRegistrado)aplicacion.getLoggedUser();
		
		if (!user1.subirCancion("chicle3", "chicle3.mp3")) {
			System.out.println("[ERROR] La cancion chicle3 no se ha añadido de forma correcta");
			return;
		}
		
		System.out.println("[OK] La cancion chicle3 se ha añadido de forma correcta");
		Thread.sleep(0);
		System.out.println("[OK] Se ha hecho logout.\n");
		aplicacion.logout();
		user1 = null;
		
		if(!aplicacion.login("SenorY", "passSr2")) {
			System.out.println("[ERROR al loguearse el usuario2]");
			return;
		}
		System.out.println("[OK] El usuario 2 se ha logueado de forma correcta.");
		UsuarioRegistrado user2 = (UsuarioRegistrado)aplicacion.getLoggedUser();
		
		if (!user2.subirCancion("hive", "chicle3.mp3")) {
			System.out.println("[ERROR] La cancion hive no se ha anadido de forma correcta");
			return;
		}
		
		System.out.println("[OK] La cancion hive se ha añadido de forma correcta");
		System.out.println("[OK] Se ha hecho logout.\n\n");
		Thread.sleep(3000);
		aplicacion.logout();
		user2 = null;
		
		//______________________________________________________________________________
		//_____________________Pruebas de validar y rechazar____________________________
		//______________________________________________________________________________
		
		System.out.println("--------------------------------------------------");
		System.out.println("\t  Prueba de validar y rechazar");
		System.out.println("--------------------------------------------------");
		
		System.out.println("Ahora haremos login como administrador para validar las canciones\n");
		
		if(!aplicacion.login("password123")) {
			System.out.println("[ERROR al loguearse el admin]");
			return;
		}
		System.out.println("[OK] El admin se ha logueado de forma correcta.");
		Thread.sleep(0);
	
		System.out.println("Se muestran las canciones por validar\n");
		Administrador admin = aplicacion.getAdministrador();
		List <Cancion> pendientes = admin.getCancionesPendientes();

		for(Cancion cancion : pendientes) {
			System.out.println(cancion);
		}
		
		Cancion original = pendientes.get(0);
		System.out.println("Se valida la cancion chicle3");
		if (!(admin.validarCancion(pendientes.get(0),false))) {
			System.out.println("[ERROR] El admin no ha podido validar la cancion chicle3");
			return;
		}
		
		System.out.println("[OK] El admin a validado la cancion chicle3\n");
		Thread.sleep(0);
		
		System.out.println("Se rechaza la cancion hive");
		if (!(admin.rechazarCancion(pendientes.get(0),"Cancion rechazada"))) {
			System.out.println("[ERROR] El admin no ha podido rechazar la cancion hive");
			return;
		}
		
		System.out.println("[OK] El admin a rechazado la cancion hive\n");
		Thread.sleep(0);

		if(!admin.getCancionesPendientes().isEmpty()) {
			System.out.println("[ERROR] Administrador sigue teniendo canciones");
			return;
		}
	
		System.out.println("[OK] Las canciones han sido administradas de forma correcta");
		admin = null;
		pendientes = null;
		Thread.sleep(3000);
		System.out.println("[OK] Se ha hecho logout.\n\n");
		aplicacion.logout();

		//______________________________________________________________________________
		//___________________Rectificamos una cancion y la aceptamos____________________
		//______________________________________________________________________________
		
		System.out.println("--------------------------------------------------");
		System.out.println("\t  Prueba rectificar cancion");
		System.out.println("--------------------------------------------------");
		System.out.println("Ahora rectificamos la cancion rechazada y el admin la vuelve a validar\n");
		
		if(!aplicacion.login("SenorY", "passSr2")) {
			System.out.println("[ERROR al loguearse con el usuario2]");
			return;
		}
		System.out.println("[OK] El usuario2 se ha logueado de forma correcta.");
		
		user2 = (UsuarioRegistrado)aplicacion.getLoggedUser();
			
		System.out.println("Canciones a rectificar para el usuario2:\n");
		for(Cancion cancion : ((UsuarioRegistrado)user2).getARectificar()) {
			System.out.println(cancion);
		}
		
		Thread.sleep(0);
		System.out.println("Ahora haremos que el usuario rectifique la cancion rechazada");
		user2.rectificarCancion(user2.getARectificar().get(0), "hive.mp3");
		
		if(!user2.getARectificar().isEmpty()) {
			System.out.println("[ERROR] No se ha rectificado de forma correcta la cancion");
			return;
		}
		
		System.out.println("[OK] Se ha rectificado de forma correcta la cancion");
		
		aplicacion.logout();
		System.out.println("[OK] Se ha hecho logout.\n");
		user2 = null;
		
		if(!aplicacion.login("password123")) {
			System.out.println("[ERROR al loguearse el admin]");
			return;
		}
		System.out.println("[OK] El admin se ha logueado de forma correcta.");
		Thread.sleep(0);
		
		admin = aplicacion.getAdministrador();
		System.out.println("[OK] La cancion esta en la lista de pendientes del administrador:\n"+ 
		admin.getCancionesPendientes());
		
		Thread.sleep(0);
		pendientes = admin.getCancionesPendientes();
		admin.validarCancion(pendientes.get(0),true);
		
		if(!aplicacion.getAdministrador().getCancionesPendientes().isEmpty()) {
			System.out.println("[ERROR] La cancion no se ha aprobado correctamente");
			return;
		}
		
		System.out.println("[OK] Las canciones han sido administradas de forma correcta");
		admin = null;
		pendientes = null;
		aplicacion.logout();
		System.out.println("[OK] Se ha hecho logout.\n\n");

		//______________________________________________________________________________
		//__________________________Pruebas de las denuncias____________________________
		//______________________________________________________________________________
		
		//Creamos dos notificaciones de plagio. Una la aceptamos y otra no
		System.out.println("--------------------------------------------------");
		System.out.println("\t  Prueba de las denuncias");
		System.out.println("--------------------------------------------------");
		System.out.println("Ahora vamos a ver que tal funcionan las denuncias por plagio:\n");
		Thread.sleep(0);
		
		System.out.println("Se crea un nuevo usario que realizara un plagio:\n");
		if (aplicacion.registro("SenorZ", "passSr3",LocalDate.now().withDayOfMonth(3).withMonth(2).withYear(1997))) {
			System.out.println("[OK] Se ha creado al usuario3 de forma correcta");
		} else {
			System.out.println("[ERROR] no se ha creado al usuario3 de forma correcta");
		}
		
		if(!aplicacion.login("SenorZ", "passSr3")) {
			System.out.println("[ERROR al loguearse el usuario3]");
			return;
		}
		System.out.println("[OK] El usuario 3 se ha logueado de forma correcta.");
		Thread.sleep(0);
		UsuarioRegistrado user3 = (UsuarioRegistrado)aplicacion.getLoggedUser();
		
		if (!user3.subirCancion("chicle2", "chicle3.mp3")) {
			System.out.println("[ERROR] La cancion chicle2 no se ha añadido de forma correcta");
			return;
		}
		
		System.out.println("[OK] La cancion chicle2 se ha añadido de forma correcta");
		Thread.sleep(0);
		System.out.println("[OK] Se ha hecho logout.\n");
		aplicacion.logout();
		user3 = null;
		
		System.out.println("Se crea un nuevo usario que no realizara un plagio:\n");
		
		if (aplicacion.registro("SenorW", "passSr4",LocalDate.now().withDayOfMonth(2).withMonth(9).withYear(1998))) {
			System.out.println("[OK] Se ha creado al usuario4 de forma correcta");
		} else {
			System.out.println("[ERROR] no se ha creado al usuario4 de forma correcta");
		}
		
		if(!aplicacion.login("SenorW", "passSr4")) {
			System.out.println("[ERROR al loguearse el usuario4]");
			return;
		}
		System.out.println("[OK] El usuario 4 se ha logueado de forma correcta.");
		Thread.sleep(0);
		UsuarioRegistrado user4 = (UsuarioRegistrado)aplicacion.getLoggedUser();
		
		if (!user4.subirCancion("mar", "mar.mp3")) {
			System.out.println("[ERROR] La cancion mar no se ha añadido de forma correcta");
			return;
		}
		
		System.out.println("[OK] La cancion mar se ha añadido de forma correcta");
		Thread.sleep(0);
		System.out.println("[OK] Se ha hecho logout.\n");
		aplicacion.logout();
		user3 = null;
		
		System.out.println("El admin valida las canciones\n");
		if(!aplicacion.login("password123")) {
			System.out.println("[ERROR al loguearse el admin]");
			return;
		}
		System.out.println("[OK] El admin se ha logueado de forma correcta.");
		Thread.sleep(0);
	
		admin = aplicacion.getAdministrador();
		pendientes = admin.getCancionesPendientes();
		Cancion copia1 = pendientes.get(0);
		
		System.out.println("Se valida la cancion chicle2");
		if (!(admin.validarCancion(pendientes.get(0),false))) {
			System.out.println("[ERROR] El admin no ha podido validar la cancion chicle2");
			return;
		}
		
		System.out.println("[OK] El admin a validado la cancion chicle2");
		Thread.sleep(0);
		
		Cancion copia2 = pendientes.get(0);
		System.out.println("Se validado la cancion mar");
		if (!(admin.validarCancion(pendientes.get(0),false))) {
			System.out.println("[ERROR] El admin no ha podido validar la cancion mar");
			return;
		}
		
		System.out.println("[OK] El admin a valida la cancion mar");
		Thread.sleep(0);

		if(!admin.getCancionesPendientes().isEmpty()) {
			System.out.println("[ERROR] Administrador sigue teniendo canciones");
			return;
		}
	
		System.out.println("[OK] Las canciones han sido administradas de forma correcta");
		admin = null;
		pendientes = null;
		Thread.sleep(0);
		System.out.println("[OK] Se ha hecho logout.\n");
		aplicacion.logout();
		
		System.out.println("El usuario 1 crea dos denuncias\n");
		Thread.sleep(0);
		
		if(!aplicacion.login("SenorX", "passSr1")) {
			System.out.println("[ERROR al loguearse el usuario1]");
			return;
		}
		System.out.println("[OK] El usuario 1 se ha logueado de forma correcta.");
		
		user1 = (UsuarioRegistrado)aplicacion.getLoggedUser();
		
		if (!user1.denunciarCancion(copia1, original)) {
			System.out.println("[ERROR] No se ha podido crear la denuncia.");
		}
		System.out.println("[OK] Se ha podido crear la denuncia.");
		Thread.sleep(0);
		
		if (!user1.denunciarCancion(copia2, original)) {
			System.out.println("[ERROR] No se ha podido crear la denuncia.");
		}

		System.out.println("[OK] Se ha podido crear la denuncia.");
		Thread.sleep(0);
		System.out.println("[OK] Se ha hecho logout.\n");
		aplicacion.logout();
		
		System.out.println("El admin valida las denuncias\n");
		if(!aplicacion.login("password123")) {
			System.out.println("[ERROR] al loguearse el admin");
			return;
		}
		System.out.println("[OK] El admin se ha logueado de forma correcta.");
		Thread.sleep(0);
	
		admin = aplicacion.getAdministrador();
		List <NotificacionPlagio> denuncias = admin.getDenunciasPendientes();
		
		for(NotificacionPlagio not: denuncias) {
			System.out.println(not);
		}
		
		if (!admin.validarDenuncia(denuncias.get(0))) {
			System.out.println("[ERROR] el admin no ha podido validar la denuncia");
		}
		System.out.println("[OK] el admin ha podido validar la denuncia");
	
		if (!admin.rechazarDenuncia(denuncias.get(0))) {
			System.out.println("[ERROR] el admin no ha podido rechazar la denuncia");
		}
		System.out.println("[OK] el admin ha podido rechazar la denuncia");
		
		System.out.println("[OK] Las denuncias han sido administradas de forma correcta");
		admin = null;
		pendientes = null;
		Thread.sleep(0);
		System.out.println("[OK] Se ha hecho logout.\n");
		aplicacion.logout();
		
		System.out.println("Se loguea con los usarios afectados para comprobar que esten bloqueados o baneados\n");
		if(aplicacion.login("SenorX", "passSr1")) {
			System.out.println("[ERROR] El usuario 1 se ha logueado");
			return;
		}
		System.out.println("[OK] El usuario 1 no ha consegudio loguearse.\n");
		Thread.sleep(0);
		
		if(aplicacion.login("SenorZ", "passSr3")) {
			System.out.println("[ERROR] El usuario 3 se ha logueado");
			return;
		}
		System.out.println("[OK] El usuario 3 no ha consegudio loguearse.\n\n");
		Thread.sleep(0);
		
		//______________________________________________________________________________
		//_____________________Vamos a reproducir algunas canciones_____________________
		//______________________________________________________________________________
		System.out.println("--------------------------------------------------");
		System.out.println("\t  Prueba busqueda y reproduccion");
		System.out.println("--------------------------------------------------");
		Thread.sleep(0);
		
		System.out.println("Realizamos una busqueda con el usuario invitado\n");
		Usuario user = aplicacion.getLoggedUser();
		List<ElementoReproducible> bus = user.realizarBusqueda(EstadoBusqueda.PORTITULO, "mar");
		System.out.println("[OK] Resultado de busqueda por titulo: mar");
		if (bus!=null) {
			System.out.println(bus);	
		}
		
		System.out.println("Reproducimos la cancion mar\n");
		((Cancion)bus.get(0)).reproducir(user);
		Thread.sleep(0);
		bus = null;
		System.out.println("[OK] Se ha conseguido reproducir la cancion: mar\n");
		System.out.println(aplicacion);
		System.out.println("\n[OK] A aumentado el contador de reproducciones del usario invitado \n");
		
		Thread.sleep(0);
		System.out.println("Realizamos otra busqueda con el usuario invitado\n");
		bus = user.realizarBusqueda(EstadoBusqueda.PORTITULO, "hive");
		if (bus.isEmpty()) {
			System.out.println("[OK] la cancion hive es explicita no se muestra al usuario\n");	
		} else {
			System.out.println("[ERROR] la cancion hive es explicita y se ha mostrado al usuario\n");
			return;
		}
		System.out.println("[OK] la cancion hive es explicita no se muestra al usuario\n");
		Thread.sleep(0);
		
		System.out.println("Realizamos una busqueda con el usuario4\n");
		if(!aplicacion.login("SenorW", "passSr4")) {
			System.out.println("[ERROR al loguearse el usuario4]");
			return;
		}
		
		System.out.println("[OK] El usuario 4 se ha logueado de forma correcta.");
		Thread.sleep(0);
		user4 = (UsuarioRegistrado)aplicacion.getLoggedUser();
		System.out.println(user4);
		System.out.println("[OK] Comprobamos que ha aumentado el contador a premium");
		Thread.sleep(0);
		
		bus = user4.realizarBusqueda(EstadoBusqueda.PORTITULO, "hive");
		System.out.println("[OK] Resultado de busqueda por titulo: hive");
		if (bus!=null) {
			System.out.println(bus);	
		}
		
		System.out.println("Reproducimos la cancion hive\n");
		((Cancion)bus.get(0)).reproducir(user4);
		Thread.sleep(0);
		bus = null;
		System.out.println("[OK] Se ha conseguido reproducir la cancion: chicle3\n");
		System.out.println(user4);
		System.out.println("[OK]  Comprobamos que ha aumentado el contador de reproducciones\n");
		aplicacion.logout();
		System.out.println("[OK] Se ha hecho logout.\n");
		user4 = null;
		Thread.sleep(3000);
		
		//____________________________________________________________________________
		//______________Vamos a pagarle al usuario 2 el premium_______________________
		//____________________________________________________________________________
		
		System.out.println("--------------------------------------------------");
		System.out.println("\t  Prueba pago premium");
		System.out.println("--------------------------------------------------");
		Thread.sleep(0);
		
		System.out.println("Probamos pagar el Premium del usuario anterior con un numero erroneo: (1234) que tiene menos de 16 digitos\n");
		Thread.sleep(0);
		
		if(!aplicacion.login("SenorW", "passSr4")) {
			System.out.println("[ERROR al loguearse el usuario4]");
			return;
		}
		
		System.out.println("[OK] El usuario 4 se ha logueado de forma correcta.");
		Thread.sleep(0);
		user4 = (UsuarioRegistrado)aplicacion.getLoggedUser();
		user4.pagarPremium("1234");
		Thread.sleep(0);
		System.out.println(user4+"\nObviamente no es premium\n");
		
		System.out.println("Utilicemos un numero de 16 digitos (5412751234123456) \n");
		Thread.sleep(0);
		user4.pagarPremium("5412751234123456");
		Thread.sleep(0);
		System.out.println(user4+"El usuario se ha convertido en premium de forma exitosa\n");
		aplicacion.logout();
		System.out.println("[OK] Se ha hecho logout.\n");
		Thread.sleep(3000);
	
		//____________________________________________________________________________
		//_________________________Prueba actualizar aplicacion_______________________
		//____________________________________________________________________________
		
		System.out.println("--------------------------------------------------");
		System.out.println("\t  Prueba actualizar aplicacion");
		System.out.println("--------------------------------------------------");
		Thread.sleep(0);
		
		System.out.println("Guardamos el estado de la aplicacion\n");
		Thread.sleep(0);
		aplicacion.backup();
		
		System.out.println("Vamos a actualizar la app para dentro de un tiempo y veremos como el"
				+ " usuario pierde el premium y se terminan los baneos: ");
		LocalDate fechaAux = FechaSimulada.fijarFecha(01,05,2017);
		aplicacion.actualizarApp(fechaAux);
		Thread.sleep(0);
		
		System.out.println("Recuperamos el estado de la aplicacion previamente guardado");
		aplicacion.loadBackup();
		System.out.println("Probamos a loguearnos con un usuario baneado\n");
		
		if(aplicacion.login("SenorX", "passSr1")) {
			System.out.println("[ERROR] al loguearse el usuario1");
			return;
		}
		
		System.out.println("[OK] El usuario sigue baneado puesto que hemos recuperado la aplicacion despues"
				+ "de la actualizacion.\n");
		Thread.sleep(0);
		
		System.out.println("Volvemos a actualizar la app para dentro de un tiempo y veremos como el"
				+ " usuario pierde el premium y se terminan los baneos: \n");
		aplicacion.actualizarApp(fechaAux);
		Thread.sleep(0);
		
		System.out.println("Volvemos a probar a loguearnos con un usuario baneado\n");
		
		if(!aplicacion.login("SenorX", "passSr1")) {
			System.out.println("[ERROR] al loguearse el usuario1");
			return;
		}
		
		user1 = (UsuarioRegistrado)aplicacion.getLoggedUser();
		System.out.println(user1);
		aplicacion.logout();
		System.out.println("[OK] Se ha hecho logout.\n");
		user1=null;
		
		
		System.out.println("Probamos a loguearnos con un usuario premium\n");
		
		if(!aplicacion.login("SenorW", "passSr4")) {
			System.out.println("[ERROR al loguearse el usuario4]");
			return;
		}
		
		System.out.println("[OK] El usuario 4 se ha logueado correctamente");
		Thread.sleep(0);
		user4 = (UsuarioRegistrado)aplicacion.getLoggedUser();
		System.out.println(user4);
		System.out.println("[OK] Como podemos comprobar el usario 4 ya no es premium y su contador ha sido reiniciado");
		aplicacion.logout();
		System.out.println("[OK] Se ha hecho logout.\n");
		user4 = null;
		Thread.sleep(3000);
		
		//____________________________________________________________________________
		//_______________________Intentemos crear un album____________________________
		//____________________________________________________________________________
		
		System.out.println("--------------------------------------------------");
		System.out.println("\t       Prueba creacion album");
		System.out.println("--------------------------------------------------");
		Thread.sleep(0);
		
		System.out.println("Añadimos una nueva canciones al usuario1 para crear un album\n");
		
		if(!aplicacion.login("SenorX", "passSr1")) {
			System.out.println("[ERROR al loguearse el usuario1]");
			return;
		}
		System.out.println("[OK] El usuario 1 se ha logueado de forma correcta.");
		Thread.sleep(0);
		user1 = (UsuarioRegistrado)aplicacion.getLoggedUser();
		
		if (!user1.subirCancion("np", "np.mp3")) {
			System.out.println("[ERROR] La cancion np no se ha añadido de forma correcta");
			return;
		}
		
		System.out.println("[OK] La cancion np se ha añadido de forma correcta\n");
		List<Cancion> canciones = user1.mostrarObras();
		System.out.println(canciones);
		Thread.sleep(0);
		
		System.out.println("El usuario1 crea un album\n");

		if (!user1.nuevoAlbum("Musica", canciones)) {
			System.out.println("[ERROR] El album Musica no se ha añadido de forma correcta");
			return;
		}
		
		System.out.println("[OK] El album Musica se ha añadido de forma correcta");
		Thread.sleep(0);
		System.out.println(user1.mostrarAlbumes().get(0));
		
		System.out.println("[OK] Se ha hecho logout.\n");
		aplicacion.logout();
		canciones = null;
		user1 = null;
		
		System.out.println("El admin valida las canciones\n");
		if(!aplicacion.login("password123")) {
			System.out.println("[ERROR al loguearse el admin]");
			return;
		}
		System.out.println("[OK] El admin se ha logueado de forma correcta.");
		Thread.sleep(0);
	
		admin = aplicacion.getAdministrador();
		pendientes = admin.getCancionesPendientes();
		
		System.out.println("Se valida la cancion np");
		if (!(admin.validarCancion(pendientes.get(0),false))) {
			System.out.println("[ERROR] El admin no ha podido validar la cancion np");
			return;
		}
		
		System.out.println("[OK] El admin a validado la cancion np");
		Thread.sleep(0);
	
		admin = null;
		pendientes = null;
		Thread.sleep(0);
		System.out.println("[OK] Se ha hecho logout.\n");
		aplicacion.logout();
		
		
		System.out.println("Comprobamos que el album ya se encuentre validado\n");
		
		if(!aplicacion.login("SenorX", "passSr1")) {
			System.out.println("[ERROR] al loguearse el usuario1");
			return;
		}
		System.out.println("[OK] El usuario 1 se ha logueado de forma correcta.");
		Thread.sleep(0);
		user1 = (UsuarioRegistrado)aplicacion.getLoggedUser();
	
		System.out.println(user1.mostrarAlbumes().get(0));
		
		System.out.println("[OK] El album ha sido validado");
		Thread.sleep(0);
		System.out.println("[OK] Se ha hecho logout.\n");
		aplicacion.logout();
		user1 = null;
		
		System.out.println("Esta ha sido la demo. Esperamos que te haya gustado.\n"
				+ "Obviamente no estan todos los casos posibles para no hacerla tediosa, pero permite captar la idea.\n\nUn saludo. :D");
	}	
}