package es.uam.eps.padsof.gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class PanelUsuario extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JButton botonReportar = new JButton("Reportar Plagio");
	private JButton botonLogout = new JButton("Cerrar Sesion");
	private JTabbedPane tabPestanias = new JTabbedPane();
	private PanelCanciones panelmusica;
	private PanelNotificaciones panelNotificacion;
	private PanelRectificaciones panelRectificar;
	private PanelPlaylist panelplaylist;
	private Gui gui;

	public PanelUsuario(Gui gui) {
		this.gui = gui;

		actualizarTablas();

		SpringLayout layout = new SpringLayout();
		this.setLayout(layout);
		this.add(tabPestanias);
		this.add(botonReportar);
		this.add(botonLogout);
		
		layout.putConstraint(SpringLayout.NORTH, tabPestanias, 10, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.NORTH, botonReportar, 10, SpringLayout.SOUTH, tabPestanias);
		layout.putConstraint(SpringLayout.NORTH, botonLogout, 10, SpringLayout.SOUTH, tabPestanias);
		layout.putConstraint(SpringLayout.EAST, botonLogout, 0, SpringLayout.EAST, this);
		
		this.setPreferredSize(new Dimension(725,400));
		this.setVisible(true);
		
		botonLogout.addActionListener(this);
		botonReportar.addActionListener(this);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		Object action = e.getSource();
		
		if (action==botonLogout) {
			gui.getControlador().controladorLogout();
		} else {
			gui.actionReportar();
		}
	}
	
	public void actualizarTablas() {
		if (tabPestanias.getComponents()!=null) {
			tabPestanias.removeAll();
		}
		
		panelmusica = new PanelCanciones(gui,gui.getControlador().controladorGetSubidas());
		panelplaylist = new PanelPlaylist(gui,gui.getControlador().controladorGetPlayList());
		panelNotificacion = new PanelNotificaciones(gui.getControlador().controladorGetNotificaciones());
		panelRectificar = new PanelRectificaciones(gui,gui.getControlador().controladorGetRechazadas());
		tabPestanias.addTab("Mi Musica", panelmusica);
		tabPestanias.addTab("Mis Playlist",panelplaylist);
		tabPestanias.addTab("Suscripciones", panelNotificacion);
		tabPestanias.addTab("Rechazadas", panelRectificar);
		this.revalidate();
		this.repaint();
	}
}
