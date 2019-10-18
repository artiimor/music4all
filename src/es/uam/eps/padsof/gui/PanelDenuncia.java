package es.uam.eps.padsof.gui;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import es.uam.eps.padsof.gui.treetable.*;
import javax.swing.*;

public class PanelDenuncia extends JPanel implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTreeTable tablaDenuncias;
	private List<String> fields = new ArrayList<String>();
	private JButton botonReproductor = new JButton("Reproducir");
	private JButton botonValidar = new JButton("Aceptar plagio");
	private JButton botonRechazar = new JButton("Rechazar plagio");
	private Gui gui;
	
	public PanelDenuncia(Gui gui, List<Object[]> content) {
		this.gui = gui;
		
		fields.add("Titulo");
		fields.add("Autor");
		fields.add("Duracion");
		fields.add("Fecha");
		fields.add("Archivo");
		fields.add("Id");

		tablaDenuncias = new JTreeTable(fields,content);
		tablaDenuncias.getColumnModel().getColumn(5).setMinWidth(0);
		tablaDenuncias.getColumnModel().getColumn(5).setMaxWidth(0);
		tablaDenuncias.getColumnModel().getColumn(5).setWidth(0);
		tablaDenuncias.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollingDenuncia = new JScrollPane(tablaDenuncias);
		
		SpringLayout layout = new SpringLayout();
		this.setLayout(layout);
		this.add(scrollingDenuncia);
		this.add(botonReproductor);
		this.add(botonValidar);
		this.add(botonRechazar);
		
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, scrollingDenuncia, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.NORTH, botonReproductor, 8, SpringLayout.SOUTH, scrollingDenuncia);
		layout.putConstraint(SpringLayout.NORTH, botonValidar, 8, SpringLayout.SOUTH, scrollingDenuncia);
		layout.putConstraint(SpringLayout.NORTH, botonRechazar, 8, SpringLayout.SOUTH, scrollingDenuncia);
		layout.putConstraint(SpringLayout.EAST, botonReproductor, 0, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.WEST, botonValidar, 8, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.WEST, botonRechazar, 10, SpringLayout.EAST, botonValidar);
		
		scrollingDenuncia.setPreferredSize(new Dimension(720,325));
		this.setPreferredSize(new Dimension(720,365));
		this.setVisible(true);
		
		botonReproductor.addActionListener(this);
		botonValidar.addActionListener(this);
		botonRechazar.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (tablaDenuncias.getSelectedRow()==-1) {
			return;
		}
		Object boton = e.getSource();
		if (boton == botonValidar) {
			int row = tablaDenuncias.getSelectedRow();
			if ((Integer)tablaDenuncias.getValueAt(row, 5)!=null) {
				if ((Integer)tablaDenuncias.getValueAt(row+1, 5)!=null) {
					int canO = (Integer)tablaDenuncias.getValueAt(row, 5);
					int canP = (Integer)tablaDenuncias.getValueAt(row+1, 5);
					gui.getControlador().controladorAdministrarDenuncia(canP,canO,true);
				} else {
					int canO = (Integer)tablaDenuncias.getValueAt(row-1, 5);
					int canP = (Integer)tablaDenuncias.getValueAt(row, 5);
					gui.getControlador().controladorAdministrarDenuncia(canP,canO,true);
				}
			}
		} else if (boton == botonRechazar) {
			int row = tablaDenuncias.getSelectedRow();
			if ((Integer)tablaDenuncias.getValueAt(row, 5)!=null) {
				if ((Integer)tablaDenuncias.getValueAt(row+1, 5)!=null) {
					int canO = (Integer)tablaDenuncias.getValueAt(row, 0);
					int canP = (Integer)tablaDenuncias.getValueAt(row+1, 0);
					gui.getControlador().controladorAdministrarDenuncia(canP,canO,false);
				} else {
					int canO = (Integer)tablaDenuncias.getValueAt(row-1, 0);
					int canP = (Integer)tablaDenuncias.getValueAt(row, 0);
					gui.getControlador().controladorAdministrarDenuncia(canP,canO,false);
				}
			}
		} else if (boton == botonReproductor) {
			int row = tablaDenuncias.getSelectedRow();
			if ((Integer)tablaDenuncias.getValueAt(row, 5)!=null) {
				List<Integer> id = new ArrayList<Integer>();
				id.add((Integer)tablaDenuncias.getValueAt(row, 5));
				gui.getControlador().controladorReproducir(id);
			}
		}
	}
}
