package es.uam.eps.padsof.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PanelPago extends JPanel implements ActionListener, MouseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton botonSiguiente = new JButton("Siguiente");
	private JLabel nTarjeta = new JLabel("Numero tarjeta");
	private JTextField campoTarjeta = new JTextField(19);
	private Gui gui;

	PanelPago(Gui gui) {
		this.gui = gui;

		SpringLayout layout = new SpringLayout();
		this.setLayout(layout);
		this.add(nTarjeta);
		this.add(campoTarjeta);
		this.add(botonSiguiente);

		layout.putConstraint(SpringLayout.NORTH, nTarjeta, 20, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, nTarjeta, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, campoTarjeta, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.NORTH, campoTarjeta, 10, SpringLayout.SOUTH, nTarjeta);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, botonSiguiente, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.NORTH, botonSiguiente, 20, SpringLayout.SOUTH, campoTarjeta);

		this.setPreferredSize(new Dimension(300, 200));
		this.setVisible(true);
		
		botonSiguiente.addActionListener(this);
		campoTarjeta.addMouseListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		gui.getControlador().controladorPagoPremium(campoTarjeta.getText());
	}

	public void setError() {
		campoTarjeta.setText("Numero de tarjeta incorrecto");
		campoTarjeta.setForeground(java.awt.Color.red);
	}
	
	public void limpiarCampos() {
		campoTarjeta.setText("");
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		campoTarjeta.setText("");
		campoTarjeta.setForeground(java.awt.Color.BLACK);
	}

	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
}
