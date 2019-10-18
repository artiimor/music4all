package es.uam.eps.padsof.gui;
import es.uam.eps.padsof.gui.treetable.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class PanelBusqueda extends JPanel implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTreeTable busqueda;
	private JButton botonReproducir = new JButton("Reproducir");
	private JButton botonVolver = new JButton("   Volver   ");
	private JLabel resultado;
	private List<String> fields = new ArrayList<String>();
	private Gui gui;
	
	PanelBusqueda (Gui gui, String cadena, List<Object[]> content) {
		this.gui = gui;
		
		fields.add("Titulo");
		fields.add("Autor");
		fields.add("Duracion");
		fields.add("Id");
		
		resultado = new JLabel("Resultado de la busqueda: "+cadena);
		busqueda = new JTreeTable(fields,content);
		busqueda.getColumnModel().getColumn(3).setMinWidth(0);
		busqueda.getColumnModel().getColumn(3).setMaxWidth(0);
		busqueda.getColumnModel().getColumn(3).setWidth(0);
		busqueda.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollingbusqueda = new JScrollPane(busqueda);
		
		SpringLayout layout = new SpringLayout();
		this.setLayout(layout);
		this.add(resultado);
		this.add(scrollingbusqueda);
		this.add(botonReproducir);
		this.add(botonVolver);
		
		layout.putConstraint(SpringLayout.NORTH, scrollingbusqueda, 10, SpringLayout.SOUTH, resultado);
		layout.putConstraint(SpringLayout.NORTH, botonReproducir, 12, SpringLayout.SOUTH, scrollingbusqueda);
		layout.putConstraint(SpringLayout.NORTH, botonVolver, 12, SpringLayout.SOUTH, scrollingbusqueda);
		layout.putConstraint(SpringLayout.EAST, botonVolver, 0, SpringLayout.EAST, this);
		
		scrollingbusqueda.setPreferredSize(new Dimension(700,300));
		this.setPreferredSize(new Dimension(700,400)); 
		this.setVisible(true);
		
		botonReproducir.addActionListener(this);
		botonVolver.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object boton = e.getSource();
		if (boton == botonReproducir) {
			if (busqueda.getSelectedRow()==-1) {
				return;
			}
			int rows = busqueda.getSelectedRow();
			List<Integer> can = new ArrayList<Integer>();
			can.add((Integer)busqueda.getValueAt(rows, 3));
			gui.getControlador().controladorReproducir(can);
		} else if (boton == botonVolver) {
			gui.actionInicial();
		}
	}
}