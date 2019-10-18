package es.uam.eps.padsof.gui;
import es.uam.eps.padsof.controlador.*;
import es.uam.eps.padsof.music4all.*;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.event.*;

public class Gui extends JFrame implements ChangeListener, WindowListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PanelBuscar panelBusqueda;
	private PanelLogo panelLogo;
	private PanelRegistrar panelRegistrar;
	private PanelLogueoRegistro panelLogReg;
	private PanelLogin panelLogin;
	private PanelBusqueda panelResultado;
	private PanelAreaUsuario panelAreaUsuario;
	private PanelUsuario panelUsuario;
	private PanelSubirCancion panelSubida;
	private PanelCrearAlbum panelCrearAlbum;
	private PanelAdministrador panelAdministrador;
	private PanelRechazar panelRechazar;
	private PanelRectificar panelRectificar;
	private PanelBusquedaUsuario panelBusquedaUsuario;
	private PanelPago panelPago;
	private PanelPlagio panelPlagio;
	private PanelSuscripcion panelSuscripcion;
	private PanelCrearPlaylist panelCrearPlaylist;
	private JFrame aux;
	private Controlador control;

	public Gui(String titulo) {
		super(titulo);
		this.aux = new JFrame();	
		
		//Se inicializan los paneles
		panelLogReg = new PanelLogueoRegistro(this);
		panelLogo = new PanelLogo();
		panelBusqueda = new PanelBuscar(this);
		panelLogin = new PanelLogin(this);
		panelRegistrar = new PanelRegistrar(this);
		
		this.getContentPane().setLayout(new SpringLayout());
		aux.getContentPane().setLayout(new FlowLayout());
		
		actionInicial();
		
		this.setMinimumSize(new Dimension(800,600));
		this.setVisible(true);
		this.aux.setResizable(false);
		aux.addWindowListener(this);
		this.addWindowListener(this);
	}

	public void actionRegistrarse() {
		aux.setTitle("Registro");
		
		Container contenedor = aux.getContentPane();
		contenedor.removeAll();
		contenedor.add(panelRegistrar);
		
		panelRegistrar.limpiarCampos();
		aux.setVisible(true);
		aux.setSize(400,375);
		this.revalidate();
		this.repaint();
		bloquearVentana();
	}
	
	public void actionCompletarRegistro(Boolean bool) {
		if (bool) {
			aux.setVisible(false);
			desbloquearVentana();
		} else {
			panelRegistrar.setError(false);
		}
	}
	
	public void actionLoguearse() {
		aux.setTitle("Iniciar sesion");
		
		Container contenedor = aux.getContentPane();
		contenedor.removeAll();
		contenedor.add(panelLogin);
	
		panelLogin.limpiarCampos();
		aux.setVisible(true);
		aux.setSize(375,225);
		this.revalidate();
		this.repaint();
		bloquearVentana();
	}
	
	public void actionEntrar(Boolean bool,EstadoUsuario estado) {
		if (bool) {
			if (estado == EstadoUsuario.ESTANDAR) {
				panelAreaUsuario = new PanelAreaUsuario(this,false);
			} else if (estado == EstadoUsuario.PREMIUM) {
				panelAreaUsuario = new PanelAreaUsuario(this,true);
			}
			
			panelUsuario = new PanelUsuario(this);

			Container contenedor = this.getContentPane();
			contenedor.removeAll();
			SpringLayout layout = (SpringLayout)contenedor.getLayout();

			panelBusqueda.cambiarTamano(500, 100);
			panelBusqueda.cambiarTamBusqueda(36);
			panelBusqueda.cambiarAUsuarioRegistrado();
			panelLogo.cambiarTamano(30);
			
			contenedor.add(panelAreaUsuario);
			contenedor.add(panelUsuario);
			contenedor.add(panelBusqueda);
			contenedor.add(panelLogo);
			layout.putConstraint(SpringLayout.WEST, panelLogo, 200, SpringLayout.EAST, panelAreaUsuario);
			layout.putConstraint(SpringLayout.NORTH, panelLogo, 10, SpringLayout.NORTH, contenedor);
			layout.putConstraint(SpringLayout.WEST, panelBusqueda, 36, SpringLayout.EAST, panelAreaUsuario);
			layout.putConstraint(SpringLayout.NORTH, panelBusqueda, 50, SpringLayout.NORTH, panelLogo);
			layout.putConstraint(SpringLayout.WEST, panelAreaUsuario, 10, SpringLayout.WEST, contenedor);
			layout.putConstraint(SpringLayout.NORTH, panelAreaUsuario, 10, SpringLayout.NORTH, contenedor);
			layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, panelUsuario, 0, SpringLayout.HORIZONTAL_CENTER, contenedor);
			layout.putConstraint(SpringLayout.NORTH, panelUsuario, 10, SpringLayout.SOUTH, panelAreaUsuario);
			
			aux.setVisible(false);
			desbloquearVentana();
			this.revalidate();
			this.repaint();
		} else {
			if (estado == EstadoUsuario.BLOQUEADO) {
				aux.setVisible(false);
				desbloquearVentana();
				JOptionPane.showMessageDialog(null, "Usuario bloqueado del sistema", "Error", JOptionPane.ERROR_MESSAGE);
			} else if (estado == EstadoUsuario.BANEADO) {
				aux.setVisible(false);
				desbloquearVentana();
				JOptionPane.showMessageDialog(null, "Usuario baneado durante 30 dias", "Error", JOptionPane.WARNING_MESSAGE);
			} else if ( estado ==EstadoUsuario.ESTANDAR) {
				panelLogin.setError();
			}
		}
	}
	
	public void actionAdmin(Boolean bool) {
		if (bool) {
			panelAdministrador = new PanelAdministrador(this);

			Container contenedor = this.getContentPane();
			contenedor.removeAll();
			SpringLayout layout = (SpringLayout)contenedor.getLayout();

			contenedor.add(panelAdministrador);
			layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, panelAdministrador, 0, SpringLayout.HORIZONTAL_CENTER, contenedor);
			
			aux.setVisible(false);
			desbloquearVentana();
			this.revalidate();
			this.repaint();
		} else {
			panelLogin.setError();
		}
	}
	
	public void actionSubirCancion() {
		aux.setTitle("Subir Cancion");
		panelSubida = new PanelSubirCancion(this);

		Container contenedor = aux.getContentPane();
		contenedor.removeAll();
		contenedor.add(panelSubida);

		panelSubida.limpiarCampos();
		aux.setVisible(true);
		aux.setSize(375, 225);
		bloquearVentana();
	}
	
	public void actionAniadirCancion(Boolean bool) {
		if (bool) {
			panelUsuario.actualizarTablas();
			aux.setVisible(false);
		} else {
			panelSubida.setError();
		}
	}
	
	public void actionEliminarCancion() {
		panelUsuario.actualizarTablas();
		aux.setVisible(false);
	}
	
	public void actionAniadirAlbum(Boolean bool) {
		if (bool) {
			panelUsuario.actualizarTablas();
			aux.setVisible(false);
		} else {
			panelCrearAlbum.setError();
		}
	}
	
	public void actionAniadirPlaylist(Boolean bool) {
		if (bool) {
			panelUsuario.actualizarTablas();
			aux.setVisible(false);
		} else {
			panelCrearPlaylist.setError();
		}
	}
	
	public void actionValidarCancion(Boolean bool) {
		if (bool) {
			panelAdministrador.actualizarTablas();
		}
	}
	
	public void actionPagarPremium() {
		aux.setTitle("Premium");
		panelPago = new PanelPago(this);

		Container contenedor = aux.getContentPane();
		contenedor.removeAll();
		contenedor.add(panelPago);

		panelPago.limpiarCampos();
		aux.setVisible(true);
		aux.setSize(350, 180);
		bloquearVentana();
	}
	
	public void actionPagoRealizado(Boolean bool) {
		if (bool) {
			JOptionPane.showMessageDialog(null, "Pago completado correctamente", "Confirmacion", JOptionPane.INFORMATION_MESSAGE);
			this.actionEntrar(true,EstadoUsuario.PREMIUM);
		} else {
			panelPago.setError();
		}
	}
	
	public void actionRealizarDenuncia(Boolean bool) {
		if (bool) {
			JOptionPane.showMessageDialog(null, "Denuncia enviada", "Confirmacion", JOptionPane.INFORMATION_MESSAGE);
			this.actionEntrar(true,EstadoUsuario.PREMIUM);
		} else {
			panelPlagio.setError();
		}
	}
	
	public void actionFinalizarSuscripcion(Boolean bool) {
		if (bool) {
			JOptionPane.showMessageDialog(null, "Suscripcion realizada", "Confirmacion", JOptionPane.INFORMATION_MESSAGE);
			panelUsuario.actualizarTablas();
			aux.setVisible(false);
		}
	}
	
	public void actionRealizarSuscripcion(String autor) {
		aux.setTitle("Suscripcion");
		panelSuscripcion = new PanelSuscripcion(this,autor);

		Container contenedor = aux.getContentPane();
		contenedor.removeAll();
		contenedor.add(panelSuscripcion);

		aux.setVisible(true);
		aux.setSize(350, 200);
		bloquearVentana();
	}
	
	public void actionAdministrarDenuncia (Boolean bool) {
		if (bool) {
			panelAdministrador.actualizarTablas();
		}
	}
	
	public void actionRechazarCancion(String cancion, int id) {
		aux.setTitle("Rechazar");
		panelRechazar = new PanelRechazar(this,cancion,id);

		Container contenedor = aux.getContentPane();
		contenedor.removeAll();
		contenedor.add(panelRechazar);

		aux.setVisible(true);
		aux.setSize(375, 225);
		bloquearVentana();
	}
	
	public void actionRechazada(Boolean bool) {
		if (bool) {
			panelAdministrador.actualizarTablas();
			aux.setVisible(false);
		}
	}
	
	public void actionCrearAlbum(String[] canciones, Integer[] ids) {
		aux.setTitle("Nuevo Album");
		panelCrearAlbum = new PanelCrearAlbum(this,canciones,ids);

		Container contenedor = aux.getContentPane();
		contenedor.removeAll();
		contenedor.add(panelCrearAlbum);

		panelCrearAlbum.limpiarCampos();
		aux.setVisible(true);
		aux.setSize(375, 350);
		bloquearVentana();
	}
	
	public void actionCrearPlaylist(String[] canciones, Integer[] ids) {
		aux.setTitle("Nueva Playlist");
		panelCrearPlaylist = new PanelCrearPlaylist(this,canciones,ids);

		Container contenedor = aux.getContentPane();
		contenedor.removeAll();
		contenedor.add(panelCrearPlaylist);

		panelCrearPlaylist.limpiarCampos();
		aux.setVisible(true);
		aux.setSize(375, 350);
		bloquearVentana();
	}
	
	public void actionRectificarCancion(String cancion, String notaAdmin, int id) {
		aux.setTitle("Rectificar");
		panelRectificar = new PanelRectificar(this,cancion,notaAdmin,id);

		Container contenedor = aux.getContentPane();
		contenedor.removeAll();
		contenedor.add(panelRectificar);

		panelRectificar.limpiarCampos();
		aux.setVisible(true);
		aux.setSize(375, 225);
		bloquearVentana();
	}	
	
	public void actionAniadirCancionRectificada(Boolean bool) {
		System.out.println("paso");
		if (bool) {
			panelUsuario.actualizarTablas();
			aux.setVisible(false);
		} else {
			panelRectificar.setError();
		}
	}
	
	public void actionReportar() {
		aux.setTitle("Reportar plagio");
		panelPlagio = new PanelPlagio(this);

		Container contenedor = aux.getContentPane();
		contenedor.removeAll();
		contenedor.add(panelPlagio);

		/*panelPlagio.limpiarCampos();*/
		aux.setVisible(true);
		aux.setSize(375, 325);
		bloquearVentana();
	}
	
	public void actionBuscar(List<Object[]> busqueda,String bus) {
		panelResultado = new PanelBusqueda(this,bus, busqueda);
		
		Container contenedor = this.getContentPane();
		contenedor.removeAll();
		SpringLayout layout = (SpringLayout)contenedor.getLayout();

		panelBusqueda.cambiarTamano(500, 100);
		panelBusqueda.cambiarTamBusqueda(36);
		panelLogo.cambiarTamano(30);
		
		contenedor.add(panelBusqueda);
		contenedor.add(panelLogo);
		contenedor.add(panelResultado);
		contenedor.add(panelLogReg);
		layout.putConstraint(SpringLayout.WEST, panelLogReg, 10, SpringLayout.WEST, contenedor);
		layout.putConstraint(SpringLayout.NORTH, panelLogReg, 10, SpringLayout.NORTH, contenedor);
		layout.putConstraint(SpringLayout.WEST, panelLogo, 200, SpringLayout.EAST, panelLogReg);
		layout.putConstraint(SpringLayout.NORTH, panelLogo, 10, SpringLayout.NORTH, contenedor);
		layout.putConstraint(SpringLayout.WEST, panelBusqueda, 36, SpringLayout.EAST, panelLogReg);
		layout.putConstraint(SpringLayout.NORTH, panelBusqueda, 50, SpringLayout.NORTH, panelLogo);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, panelResultado, 0, SpringLayout.HORIZONTAL_CENTER, contenedor);
		layout.putConstraint(SpringLayout.SOUTH, panelResultado, 20, SpringLayout.SOUTH, contenedor);
		
		desbloquearVentana();
		contenedor.revalidate();
		contenedor.repaint();
	}
	
	public void actionBuscarUsuario(List<Object[]> busqueda,String bus,Boolean tipoUsuario) {
		panelBusquedaUsuario = new PanelBusquedaUsuario(this,bus, busqueda,tipoUsuario);
		
		Container contenedor = this.getContentPane();
		contenedor.removeAll();
		SpringLayout layout = (SpringLayout)contenedor.getLayout();
		
		contenedor.add(panelAreaUsuario);
		contenedor.add(panelBusquedaUsuario);
		contenedor.add(panelBusqueda);
		contenedor.add(panelLogo);
		layout.putConstraint(SpringLayout.WEST, panelLogo, 200, SpringLayout.EAST, panelAreaUsuario);
		layout.putConstraint(SpringLayout.NORTH, panelLogo, 10, SpringLayout.NORTH, contenedor);
		layout.putConstraint(SpringLayout.WEST, panelBusqueda, 36, SpringLayout.EAST, panelAreaUsuario);
		layout.putConstraint(SpringLayout.NORTH, panelBusqueda, 50, SpringLayout.NORTH, panelLogo);
		layout.putConstraint(SpringLayout.WEST, panelAreaUsuario, 10, SpringLayout.WEST, contenedor);
		layout.putConstraint(SpringLayout.NORTH, panelAreaUsuario, 10, SpringLayout.NORTH, contenedor);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, panelBusquedaUsuario, 0, SpringLayout.HORIZONTAL_CENTER, contenedor);
		layout.putConstraint(SpringLayout.NORTH, panelBusquedaUsuario, 20, SpringLayout.SOUTH, panelAreaUsuario);
		
		desbloquearVentana();
		contenedor.revalidate();
		contenedor.repaint();
	}
	
	@Override
	public void stateChanged(ChangeEvent arg0) {
	}
	
	public void actionInicial() {
		Container contenedor = this.getContentPane();
		contenedor.removeAll();
		SpringLayout layout = (SpringLayout)contenedor.getLayout();
		
		panelBusqueda.cambiarTamano(650,125);
		panelBusqueda.cambiarTamBusqueda(50);
		panelBusqueda.limpiarCampos();
		panelBusqueda.cambiarAUsuarioNormal();
		panelLogo.cambiarTamano(73);
		
		contenedor.add(panelLogReg);
		contenedor.add(panelLogo);
		contenedor.add(panelBusqueda);
		layout.putConstraint(SpringLayout.WEST, panelLogReg, 10, SpringLayout.WEST, contenedor);
		layout.putConstraint(SpringLayout.NORTH, panelLogReg, 10, SpringLayout.NORTH, contenedor);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, panelLogo, 16, SpringLayout.HORIZONTAL_CENTER, contenedor);
		layout.putConstraint(SpringLayout.NORTH, panelLogo, 180, SpringLayout.NORTH, contenedor);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, panelBusqueda, 16, SpringLayout.HORIZONTAL_CENTER, contenedor);
		layout.putConstraint(SpringLayout.NORTH, panelBusqueda, 280, SpringLayout.NORTH, contenedor);
		
		desbloquearVentana();
		contenedor.revalidate();
		contenedor.repaint();
	}
	
	public void setControlador(Controlador control) {
		this.control = control;
	}
	
	public Controlador getControlador() {
		return this.control;
	}
	
	public void desbloquearVentana() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void bloquearVentana() {
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}

	public void windowClosing(WindowEvent e) {
		Object frame = e.getSource();
		if (frame==aux) {
			desbloquearVentana();
		} else {
			this.getControlador().controladorBackup();
		}
		
	}
	
	public void windowActivated(WindowEvent arg0) {}
	public void windowClosed(WindowEvent arg0) {}
	public void windowDeactivated(WindowEvent arg0) {}
	public void windowDeiconified(WindowEvent arg0) {}
	public void windowIconified(WindowEvent arg0) {}
	public void windowOpened(WindowEvent arg0) {}
}
