package es.uam.eps.padsof.gui;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;


import es.uam.eps.padsof.gui.treetable.JTreeTable;

public class PanelCanciones extends JPanel implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTreeTable tablaMusica;
	private List<String> fields = new ArrayList<String>();;
	private JButton botonReproductor = new JButton("  Reproducir  ");
	private JButton botonEliminar = new JButton("   Eliminar   ");
	private JButton botonSubirCancion = new JButton("Subir cancion");
	private JButton botonNuevoAlbum = new JButton("Nuevo album");
	private Gui gui;
	
	public PanelCanciones(Gui gui, List<Object[]> content) {
		this.gui = gui;
		
		fields.add("Titulo");
		fields.add("Duracion");
		fields.add("Fecha");
		fields.add("Estado");
		fields.add("Id");
		
		tablaMusica = new JTreeTable(fields,content);
		tablaMusica.getColumnModel().getColumn(4).setMinWidth(0);
		tablaMusica.getColumnModel().getColumn(4).setMaxWidth(0);
		tablaMusica.getColumnModel().getColumn(4).setWidth(0);
		JScrollPane scrollingMusica = new JScrollPane(tablaMusica);
		
		SpringLayout layout = new SpringLayout();
		this.setLayout(layout);
		this.add(scrollingMusica);
		this.add(botonReproductor);
		this.add(botonEliminar);
		this.add(botonSubirCancion);
		this.add(botonNuevoAlbum);
		
		layout.putConstraint(SpringLayout.NORTH, botonReproductor, 10, SpringLayout.SOUTH, scrollingMusica);
		layout.putConstraint(SpringLayout.NORTH, botonSubirCancion, 10, SpringLayout.SOUTH, scrollingMusica);
		layout.putConstraint(SpringLayout.NORTH, botonEliminar, 10, SpringLayout.SOUTH, scrollingMusica);
		layout.putConstraint(SpringLayout.NORTH, botonNuevoAlbum, 10, SpringLayout.SOUTH, scrollingMusica);
		layout.putConstraint(SpringLayout.WEST, botonEliminar, 10, SpringLayout.EAST, botonReproductor);
		layout.putConstraint(SpringLayout.EAST, botonNuevoAlbum, 10, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.EAST, botonSubirCancion, 10, SpringLayout.WEST, botonNuevoAlbum);

		scrollingMusica.setPreferredSize(new Dimension(720,270));
		this.setPreferredSize(new Dimension(720,315));
		this.setVisible(true);
		
		botonSubirCancion.addActionListener(this);
		botonNuevoAlbum.addActionListener(this);
		botonReproductor.addActionListener(this);
		botonEliminar.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object boton = e.getSource();
		if (boton == botonSubirCancion) {
			gui.actionSubirCancion();
		} else if (boton == botonNuevoAlbum) {
			int[] rows = tablaMusica.getSelectedRows();
			Integer[] id = new Integer[rows.length];
			String[] can = new String[rows.length];
			for (int i=0; i<rows.length;i++) {
				id[i] = ((Integer)(tablaMusica.getValueAt(rows[i], 4)));
				can[i] = ((String)(tablaMusica.getValueAt(rows[i], 0)));
			}
			if (gui.getControlador().controladorConfirmarCanciones(id)) {
				gui.actionCrearAlbum(can,id);
			}
			return;
		} else if (boton == botonReproductor) {
			List<Integer> can = new ArrayList<Integer>();
			int[] rows = tablaMusica.getSelectedRows();
			for (int r: rows) {
				can.add((Integer)(tablaMusica.getValueAt(r, 4)));
			}
			gui.getControlador().controladorReproducirElementosUser(can);
		} else if (boton == botonEliminar) {
		/*	int[] rows = tablaMusica.getSelectedRows();
			if (rows.length == 0 || rows.length>1) {
				return;
			}
			gui.getControlador().controladorEliminarCancion((String)(tablaMusica.getValueAt(rows[0], 0)));*/
		}
	}
}
