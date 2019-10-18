package es.uam.eps.padsof.gui;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PanelRectificaciones extends JPanel implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable tablaRectificar;
	private String[]fields = {"Titulo","Duracion","Fecha","Estado","Nota Adminstrador","Id"};
	private JButton botonReproductor = new JButton("  Reproducir  ");
	private JButton botonRectificar = new JButton("  Rectificar  ");
	private JButton botonEliminar = new JButton("  Eliminar  ");
	private Gui gui;
	
	public PanelRectificaciones(Gui gui, Object[][] content) {
		this.gui = gui;
		
		DefaultTableModel modeloDatos = new DefaultTableModel(content,fields);
		tablaRectificar = new JTable(modeloDatos);
		tablaRectificar.getColumnModel().getColumn(5).setMinWidth(0);
		tablaRectificar.getColumnModel().getColumn(5).setMaxWidth(0);
		tablaRectificar.getColumnModel().getColumn(5).setWidth(0);
		tablaRectificar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollingRectificar = new JScrollPane(tablaRectificar);
		
		SpringLayout layout = new SpringLayout();
		this.setLayout(layout);
		this.add(scrollingRectificar);
		this.add(botonReproductor);
		this.add(botonEliminar);
		this.add(botonRectificar);
		
		layout.putConstraint(SpringLayout.NORTH, botonReproductor, 10, SpringLayout.SOUTH, scrollingRectificar);
		layout.putConstraint(SpringLayout.NORTH, botonRectificar, 10, SpringLayout.SOUTH, scrollingRectificar);
		layout.putConstraint(SpringLayout.NORTH, botonEliminar, 10, SpringLayout.SOUTH, scrollingRectificar);
		layout.putConstraint(SpringLayout.WEST, botonEliminar, 10, SpringLayout.EAST, botonReproductor);
		layout.putConstraint(SpringLayout.EAST, botonRectificar, 0, SpringLayout.EAST, this);
		
		scrollingRectificar.setPreferredSize(new Dimension(720,270));
		this.setPreferredSize(new Dimension(720,315));
		this.setVisible(true);
		
		botonRectificar.addActionListener(this);
		botonReproductor.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object boton = e.getSource();
		if (boton == botonRectificar) {
			int row = tablaRectificar.getSelectedRow();
			gui.actionRectificarCancion((String)tablaRectificar.getValueAt(row, 0),
					(String)tablaRectificar.getValueAt(row, 4),(Integer)tablaRectificar.getValueAt(row,5));
		/*} else if (boton == botonNuevoAlbum) {
			int[] rows = tablaMusica.getSelectedRows();
			String[] can = new String[rows.length];
			for (int i=0; i<rows.length;i++) {
				can[i] = ((String)(tablaMusica.getValueAt(rows[i], 0)));
			}
			if (gui.getControlador().controladorConfirmarCanciones(can)) {
				gui.actionCrearAlbum(can);
			}
			setError();
			return;*/
		} else if (boton == botonReproductor) {
			List<Integer> can = new ArrayList<Integer>();
			int[] rows = tablaRectificar.getSelectedRows();
			for (int r: rows) {
				can.add((Integer)(tablaRectificar.getValueAt(r, 0)));
			}
			gui.getControlador().controladorReproducirElementosUser(can);
		}
	}
	
	void setError() {
		
	}
}
