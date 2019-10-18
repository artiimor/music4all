package es.uam.eps.padsof.gui;
import es.uam.eps.padsof.gui.treetable.*;
import es.uam.eps.padsof.music4all.EstadoUsuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class PanelBusquedaUsuario extends JPanel implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTreeTable busqueda;
	private JButton botonReproducir = new JButton("Reproducir");
	private JButton botonSuscribirse = new JButton("Suscribirse");
	private JButton botonVolver = new JButton("Volver");
	private JButton botonReportar = new JButton("Reportar Plagio");
	private JButton botonPlaylist = new JButton("add Playlist");
	private JButton botonLogout = new JButton("Cerrar Sesion");
	private JTabbedPane tabPestanias = new JTabbedPane();
	private Boolean estado;
	private List<String> fields = new ArrayList<String>();
	private Gui gui;
	
	PanelBusquedaUsuario (Gui gui, String cadena, List<Object[]> content, Boolean estado) {
		this.gui = gui;
		this.estado=estado;
		
		fields.add("Titulo");
		fields.add("Autor");
		fields.add("Duracion");
		fields.add("Id");
		
		busqueda = new JTreeTable(fields,content);
		busqueda.getColumnModel().getColumn(3).setMinWidth(0);
		busqueda.getColumnModel().getColumn(3).setMaxWidth(0);
		busqueda.getColumnModel().getColumn(3).setWidth(0);
		JScrollPane scrollingBusqueda = new JScrollPane(busqueda);
		tabPestanias.addTab("Resultado de la busqueda:" +cadena,  scrollingBusqueda);

		SpringLayout layout = new SpringLayout();
		this.setLayout(layout);
		this.add(tabPestanias);
		this.add(botonSuscribirse);
		this.add(botonReproducir);
		this.add(botonVolver);
		this.add(botonReportar);
		this.add(botonLogout);
		this.add(botonPlaylist);
		
		layout.putConstraint(SpringLayout.NORTH, botonReproducir, 10, SpringLayout.SOUTH, tabPestanias);
		layout.putConstraint(SpringLayout.NORTH, botonSuscribirse, 10, SpringLayout.SOUTH, tabPestanias);
		layout.putConstraint(SpringLayout.NORTH, botonVolver, 10, SpringLayout.SOUTH, botonReproducir);
		layout.putConstraint(SpringLayout.NORTH, botonReportar, 10, SpringLayout.SOUTH, botonReproducir);
		layout.putConstraint(SpringLayout.NORTH, botonLogout, 10, SpringLayout.SOUTH, botonReproducir);
		layout.putConstraint(SpringLayout.NORTH, botonPlaylist, 10, SpringLayout.SOUTH, tabPestanias);
		layout.putConstraint(SpringLayout.WEST, botonReproducir, 20, SpringLayout.EAST, botonReportar);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, botonVolver, 20, SpringLayout.HORIZONTAL_CENTER,this);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, botonPlaylist, 20, SpringLayout.HORIZONTAL_CENTER,this);
		layout.putConstraint(SpringLayout.WEST, botonSuscribirse, 90, SpringLayout.EAST, botonPlaylist);
		layout.putConstraint(SpringLayout.EAST, botonLogout, 0, SpringLayout.EAST, this);
		
		tabPestanias.setPreferredSize(new Dimension(720,307));
		this.setPreferredSize(new Dimension(725,400));
		this.setVisible(true);
		
		botonReproducir.addActionListener(this);
		botonVolver.addActionListener(this);
		botonSuscribirse.addActionListener(this);
		botonLogout.addActionListener(this);
		botonPlaylist.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object boton = e.getSource();
		if (boton==botonReproducir) {
			if (busqueda.getSelectedRow()==-1) {
				return;
			}	
			List<Integer> can = new ArrayList<Integer>();
			int[] rows = busqueda.getSelectedRows();
			for (int r: rows) {
				can.add((Integer)(busqueda.getValueAt(r, 3)));
			}
			gui.getControlador().controladorReproducir(can);
		} else if (boton==botonVolver) {
			if (estado) {
				gui.actionEntrar(true, EstadoUsuario.ESTANDAR);
			} else {
				gui.actionEntrar(true, EstadoUsuario.PREMIUM);
			}
		} else if (boton==botonLogout) {
			gui.getControlador().controladorLogout();
		} else if (boton==botonSuscribirse) {
			if (busqueda.getSelectedRow()==-1) {
				return;
			}
			int rows = busqueda.getSelectedRow();
			gui.actionRealizarSuscripcion((String)(busqueda.getValueAt(rows, 1)));
		} else if (boton==botonPlaylist) {
			int[] rows = busqueda.getSelectedRows();
			Integer[] id = new Integer[rows.length];
			String[] can = new String[rows.length];
			for (int i=0; i<rows.length;i++) {
				id[i] = ((Integer)(busqueda.getValueAt(rows[i], 3)));
				can[i] = ((String)(busqueda.getValueAt(rows[i], 0)));
			}
			gui.actionCrearPlaylist(can,id);
		}
	}
}