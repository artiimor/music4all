package es.uam.eps.padsof.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PanelSubirCancion extends JPanel implements ActionListener, MouseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton botonSubir = new JButton("Subir");
	private JLabel nCancion = new JLabel("Titulo");
	private JTextField name = new JTextField(19);
	private JLabel aCancion = new JLabel("Nombre del archivo");
	private JTextField archive = new JTextField(19);
	private Gui gui;

	public PanelSubirCancion(Gui gui) {
		this.gui = gui;

		SpringLayout layout = new SpringLayout();
		this.setLayout(layout);
		this.add(nCancion);
		this.add(name);
		this.add(aCancion);
		this.add(archive);
		this.add(botonSubir);

		layout.putConstraint(SpringLayout.WEST, nCancion, 45, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, nCancion, 20, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, name, 45, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, name, 5, SpringLayout.SOUTH, nCancion);
		layout.putConstraint(SpringLayout.WEST, aCancion, 45, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, aCancion, 5, SpringLayout.SOUTH, name);
		layout.putConstraint(SpringLayout.WEST, archive, 45, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, archive, 5, SpringLayout.SOUTH, aCancion);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, botonSubir, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.NORTH, botonSubir, 20, SpringLayout.SOUTH, archive);

		this.setPreferredSize(new Dimension(300, 200));
		this.setVisible(true);
		
		botonSubir.addActionListener(this);
		archive.addMouseListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		gui.getControlador().controladorSubirCancion(name.getText(), archive.getText());
	}

	public void setError() {
		limpiarCampos();
		archive.setText("Nombre o Formato incorrectos");
		archive.setForeground(java.awt.Color.red);
	}
	
	public void limpiarCampos() {
		name.setText("");
		archive.setText("");
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		archive.setText("");
		archive.setForeground(java.awt.Color.black);
	}
	
	public void mouseExited(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
}