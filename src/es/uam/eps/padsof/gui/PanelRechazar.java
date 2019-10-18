package es.uam.eps.padsof.gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PanelRechazar extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton boton = new JButton("Siguiente");
	private JLabel etiquetaCancion;
	private JLabel etiquetaMensaje = new JLabel("Mensaje");
	private JTextField mensaje = new JTextField(26);
	private int id;
	private Gui gui;

	PanelRechazar(Gui gui, String cancion, int id) {
		this.gui = gui;
		this.id = id;

		etiquetaCancion = new JLabel("Rechazar cancion: "+cancion);
		
		SpringLayout layout = new SpringLayout();
		this.setLayout(layout);
		this.add(boton);
		this.add(mensaje);
		this.add(etiquetaMensaje);
		this.add(etiquetaCancion);

		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, etiquetaCancion, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.NORTH, etiquetaCancion, 25, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, etiquetaMensaje, 5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, etiquetaMensaje, 10, SpringLayout.SOUTH, etiquetaCancion);
		layout.putConstraint(SpringLayout.WEST, mensaje, 5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, mensaje, 5, SpringLayout.SOUTH, etiquetaMensaje);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, boton, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.NORTH, boton, 35, SpringLayout.SOUTH, mensaje);

		this.setPreferredSize(new Dimension(300, 200));
		this.setVisible(true);
		
		boton.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		gui.getControlador().controladorRechazar(id,mensaje.getText());
	}
}
