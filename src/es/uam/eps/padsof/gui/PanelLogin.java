package es.uam.eps.padsof.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PanelLogin extends JPanel implements ActionListener, MouseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton botonLogin = new JButton("Siguiente");
	private JLabel etiquetaUser = new JLabel("Usuario");
	private JTextField name = new JTextField(19);
	private JLabel etiquetaPassword = new JLabel("Contraseña");
	private JTextField password = new JTextField(19);
	private Gui gui;

	PanelLogin(Gui gui) {
		this.gui = gui;

		SpringLayout layout = new SpringLayout();
		this.setLayout(layout);
		this.add(etiquetaUser);
		this.add(name);
		this.add(etiquetaPassword);
		this.add(password);
		this.add(botonLogin);

		layout.putConstraint(SpringLayout.WEST, etiquetaUser, 45, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, etiquetaUser, 20, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, name, 45, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, name, 5, SpringLayout.SOUTH, etiquetaUser);
		layout.putConstraint(SpringLayout.WEST, etiquetaPassword, 45, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, etiquetaPassword, 5, SpringLayout.SOUTH, name);
		layout.putConstraint(SpringLayout.WEST, password, 45, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, password, 5, SpringLayout.SOUTH, etiquetaPassword);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, botonLogin, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.NORTH, botonLogin, 25, SpringLayout.SOUTH, password);

		this.setPreferredSize(new Dimension(300, 200));
		this.setVisible(true);
		
		botonLogin.addActionListener(this);
		name.addMouseListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		gui.getControlador().controladorLogin(name.getText(), password.getText());
	}

	public void setError() {
		name.setText("Usuario o contraseña incorrectos");
		name.setForeground(java.awt.Color.red);
		password.setText("");
	}

	public void limpiarCampos() {
		password.setText("");
		name.setText("");
		name.setForeground(java.awt.Color.black);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		name.setText("");
		name.setForeground(java.awt.Color.black);
	}
	
	public void mouseExited(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
}