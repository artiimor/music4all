package es.uam.eps.padsof.gui;
import java.awt.Dimension;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PanelNotificaciones extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable tablaNotificaciones;
	private String[] fields = {"Autor","Mensaje"};
	
	public PanelNotificaciones(Object[][] content) {

		DefaultTableModel modeloDatos = new DefaultTableModel(content,fields);
		tablaNotificaciones = new JTable(modeloDatos);
		JScrollPane scrollingNotificacion = new JScrollPane(tablaNotificaciones);
		
		this.add(scrollingNotificacion);
		
		scrollingNotificacion.setPreferredSize(new Dimension(720,310));
		this.setPreferredSize(new Dimension(720,315));
		this.setVisible(true);
	}
}