package es.uam.eps.padsof.tests;

import es.uam.eps.padsof.Music4all.Notificacion;
import java.util.*;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tester para notificaciones de las subscripciones
 * 
 * @author Aitor Martin
 *
 */
public class NotificacionTest {

	@Test
	public void test() {
		Date aux = new Date();
		Notificacion N1 = new Notificacion("Test", aux);
		
		//Se comprueba que se asignan los valores correctamente
		assertEquals("Test", N1.getMensaje());
		assertEquals(aux, N1.getFecha());
	}

}
