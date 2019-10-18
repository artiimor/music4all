package es.uam.eps.padsof.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PanelSuscripcion extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton botonSiguiente = new JButton("Confirmar");
	private JLabel etiqueta;
	private String autor;
	private Gui gui;

	PanelSuscripcion(Gui gui,String autor) {
		this.gui = gui;
		this.autor = autor;

		etiqueta = new JLabel("Desea suscribirse a el autor: "+autor);
		
		SpringLayout layout = new SpringLayout();
		this.setLayout(layout);
		this.add(etiqueta);
		this.add(botonSiguiente);

		layout.putConstraint(SpringLayout.NORTH, etiqueta, 40, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, etiqueta, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, botonSiguiente, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.NORTH, botonSiguiente, 40, SpringLayout.SOUTH, etiqueta);

		this.setPreferredSize(new Dimension(300, 200));
		this.setVisible(true);
		
		botonSiguiente.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		gui.getControlador().controladorSuscribirse(autor);
	}
}
