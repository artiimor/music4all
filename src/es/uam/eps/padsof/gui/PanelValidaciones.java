package es.uam.eps.padsof.gui;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PanelValidaciones extends JPanel implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable tablaValidaciones;
	private String[] fields = {"Nombre","Autor","Duracion","Fecha","Archivo","Id"};
	private JButton botonReproductor = new JButton("Reproducir");
	private JButton botonValidar = new JButton("Validar");
	private JButton botonRechazar = new JButton("Rechazar");
	private JRadioButton opcion1 = new JRadioButton("Normal");
	private JRadioButton opcion2 = new JRadioButton("Explicita");
	private ButtonGroup opciones = new ButtonGroup();
	private Gui gui;
	
	public PanelValidaciones(Gui gui, Object[][] content) {
		this.gui = gui;
		
		opciones.add(opcion1);
		opciones.add(opcion2);
		
		DefaultTableModel modeloDatos = new DefaultTableModel(content,fields);
		tablaValidaciones = new JTable(modeloDatos);
		tablaValidaciones.getColumnModel().getColumn(5).setMinWidth(0);
		tablaValidaciones.getColumnModel().getColumn(5).setMaxWidth(0);
		tablaValidaciones.getColumnModel().getColumn(5).setWidth(0);
		tablaValidaciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollingValidacion = new JScrollPane(tablaValidaciones);
		
		SpringLayout layout = new SpringLayout();
		this.setLayout(layout);
		this.add(scrollingValidacion);
		this.add(botonReproductor);
		this.add(botonValidar);
		this.add(botonRechazar);
		this.add(opcion1);
		this.add(opcion2);
		
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, scrollingValidacion, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.NORTH, botonReproductor, 8, SpringLayout.SOUTH, scrollingValidacion);
		layout.putConstraint(SpringLayout.NORTH, opcion1, 8, SpringLayout.SOUTH, scrollingValidacion);
		layout.putConstraint(SpringLayout.NORTH, opcion2, 8, SpringLayout.SOUTH, scrollingValidacion);
		layout.putConstraint(SpringLayout.NORTH, botonValidar, 8, SpringLayout.SOUTH, scrollingValidacion);
		layout.putConstraint(SpringLayout.NORTH, botonRechazar, 8, SpringLayout.SOUTH, scrollingValidacion);
		layout.putConstraint(SpringLayout.EAST, botonReproductor, 0, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.WEST, botonValidar, 8, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.WEST, opcion1, 10, SpringLayout.EAST, botonValidar);
		layout.putConstraint(SpringLayout.WEST, opcion2, 10, SpringLayout.EAST, opcion1);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, botonRechazar, 0, SpringLayout.HORIZONTAL_CENTER, this);
		
		scrollingValidacion.setPreferredSize(new Dimension(720,325));
		this.setPreferredSize(new Dimension(720,365));
		this.setVisible(true);
		
		botonReproductor.addActionListener(this);
		botonValidar.addActionListener(this);
		botonRechazar.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (tablaValidaciones.getSelectedRow()==-1) {
			return;
		}
		Object boton = e.getSource();
		if (boton == botonValidar) {
			int row = tablaValidaciones.getSelectedRow();
			int can = (Integer)tablaValidaciones.getValueAt(row, 5);
			if (opcion1.isSelected() || opcion2.isSelected()) {
				if (opcion1.isSelected()) {
					gui.getControlador().controladorValidar(can,opcion1.getText());
				} else if (opcion2.isSelected()) {
					gui.getControlador().controladorValidar(can,opcion2.getText());
				}
			}
		} else if (boton == botonRechazar) {
			int row = tablaValidaciones.getSelectedRow();
			String can = (String)tablaValidaciones.getValueAt(row, 0);
			int id = (Integer)tablaValidaciones.getValueAt(row, 5);
			gui.actionRechazarCancion(can,id);
		} else if (boton == botonReproductor) {
			int row = tablaValidaciones.getSelectedRow();
			gui.getControlador().controladorReproducirAdmin((Integer)tablaValidaciones.getValueAt(row, 5));
		}
	}
}
