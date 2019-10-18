package es.uam.eps.padsof.test;
import es.uam.eps.padsof.gui.Gui;
import es.uam.eps.padsof.music4all.*;
import es.uam.eps.padsof.controlador.*;

public class Main {
	public static void main(String[] args) {
		Aplicacion app = Aplicacion.getInstance();
		Gui gui = new Gui("Music4all");
		Controlador control = new Controlador(gui,app);
		gui.setControlador(control);
	}
}
