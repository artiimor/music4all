package es.uam.eps.padsof.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PanelRectificar extends JPanel implements ActionListener, MouseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton botonRectificar = new JButton("Siguente");
	private JLabel nCancion;
	private JLabel aCancion = new JLabel("Nombre del archivo");
	private JLabel nota;
	private JTextField archive = new JTextField(19);
	private int id;
	private Gui gui;

	public PanelRectificar(Gui gui, String nombre,String notaAdmin, int id) {
		this.gui = gui;
		this.id = id;
		
		nCancion = new JLabel("Titulo: "+nombre);
		nota = new JLabel("Nota administrador: "+notaAdmin);

		SpringLayout layout = new SpringLayout();
		this.setLayout(layout);
		this.add(nCancion);
		this.add(aCancion);
		this.add(nota);
		this.add(archive);
		this.add(botonRectificar);

		layout.putConstraint(SpringLayout.WEST, nCancion, 45, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, nCancion, 20, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, nota, 45, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, nota, 5, SpringLayout.SOUTH, nCancion);
		layout.putConstraint(SpringLayout.WEST, aCancion, 45, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, aCancion, 5, SpringLayout.SOUTH, nota);
		layout.putConstraint(SpringLayout.WEST, archive, 45, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, archive, 5, SpringLayout.SOUTH, aCancion);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, botonRectificar, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.NORTH, botonRectificar, 20, SpringLayout.SOUTH, archive);

		this.setPreferredSize(new Dimension(300, 200));
		this.setVisible(true);
		
		botonRectificar.addActionListener(this);
		archive.addMouseListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (archive.getText().contentEquals("")) {
			setError();
			return;
		}
		gui.getControlador().controladorRectificarCancion(id, archive.getText());
	}

	public void setError() {
		limpiarCampos();
		archive.setText("Formato incorrecto");
		archive.setForeground(java.awt.Color.red);
	}
	
	public void limpiarCampos() {
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
