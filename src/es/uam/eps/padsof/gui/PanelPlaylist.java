package es.uam.eps.padsof.gui;
import es.uam.eps.padsof.gui.treetable.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class PanelPlaylist extends JPanel implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTreeTable tablaPlaylist;
	private JButton botonPlaylist = new JButton("Nueva Playlist");
	private JButton botonReproducir = new JButton("  Reproducir  ");
	private List<String> fields = new ArrayList<String>();
	private Gui gui;
	
	PanelPlaylist (Gui gui, List<Object[]> content) {
		this.gui = gui;
		
		fields.add("Titulo");
		fields.add("Autor");
		fields.add("Duracion");
		fields.add("Id");
		
		tablaPlaylist = new JTreeTable(fields,content);
		tablaPlaylist.getColumnModel().getColumn(3).setMinWidth(0);
		tablaPlaylist.getColumnModel().getColumn(3).setMaxWidth(0);
		tablaPlaylist.getColumnModel().getColumn(3).setWidth(0);
		JScrollPane scrollingPlaylist = new JScrollPane(tablaPlaylist);

		SpringLayout layout = new SpringLayout();
		this.setLayout(layout);
		this.add(scrollingPlaylist);
		this.add(botonPlaylist);
		this.add(botonReproducir);
		
		layout.putConstraint(SpringLayout.NORTH, botonReproducir, 10, SpringLayout.SOUTH, scrollingPlaylist);
		layout.putConstraint(SpringLayout.NORTH, botonPlaylist, 10, SpringLayout.SOUTH, scrollingPlaylist);
		layout.putConstraint(SpringLayout.EAST, botonPlaylist, 0, SpringLayout.EAST, this);
		
		scrollingPlaylist.setPreferredSize(new Dimension(720,270));
		this.setPreferredSize(new Dimension(720,315));
		this.setVisible(true);
		
		botonPlaylist.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object boton = e.getSource();
		if (boton==botonReproducir) {
			if (tablaPlaylist.getSelectedRow()==-1) {
				return;
			}	
			List<Integer> can = new ArrayList<Integer>();
			int[] rows = tablaPlaylist.getSelectedRows();
			for (int r: rows) {
				can.add((Integer)(tablaPlaylist.getValueAt(r, 3)));
			}
			gui.getControlador().controladorReproducir(can);
		} else if (boton==botonPlaylist) {
			int[] rows = tablaPlaylist.getSelectedRows();
			Integer[] id = new Integer[rows.length];
			String[] can = new String[rows.length];
			for (int i=0; i<rows.length;i++) {
				id[i] = ((Integer)(tablaPlaylist.getValueAt(rows[i], 3)));
				can[i] = ((String)(tablaPlaylist.getValueAt(rows[i], 0)));
			}
			gui.actionCrearPlaylist(can,id);
		}
	}
}
