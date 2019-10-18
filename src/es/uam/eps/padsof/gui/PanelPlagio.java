package es.uam.eps.padsof.gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PanelPlagio extends JPanel implements ActionListener, MouseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton botonSiguiente = new JButton("Siguiente");
	private JLabel labelCancionPlagiada = new JLabel("Cancion Plagiada:");
	private JTextField campoCancionPlagiada = new JTextField(21);
	private JLabel labelUsuarioPlagiado = new JLabel("Usuario Plagiado");
	private JTextField campoUsuarioPlagiado = new JTextField(21);
	private JLabel labelCancionDenunciada = new JLabel("Cancion Denunciada:");
	private JTextField campoCancionDenunciada = new JTextField(21);
	private JLabel labelUsuarioDenunciado = new JLabel("Usuario Denunciado");
	private JTextField campoUsuarioDenunciado = new JTextField(21);
	private Gui gui;

	PanelPlagio(Gui gui) {
		this.gui = gui;

		SpringLayout layout = new SpringLayout();
		this.setLayout(layout);
		this.add(labelCancionPlagiada);
		this.add(campoCancionPlagiada);
		this.add(labelUsuarioPlagiado);
		this.add(campoUsuarioPlagiado);
		this.add(botonSiguiente);
		this.add(labelCancionDenunciada);
		this.add(campoCancionDenunciada);
		this.add(labelUsuarioDenunciado);
		this.add(campoUsuarioDenunciado);
		this.add(botonSiguiente);

		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, labelCancionPlagiada, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.NORTH, labelCancionPlagiada, 25, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, campoCancionPlagiada, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.NORTH, campoCancionPlagiada, 5, SpringLayout.SOUTH, labelCancionPlagiada);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, labelUsuarioPlagiado, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.NORTH, labelUsuarioPlagiado, 5, SpringLayout.SOUTH, campoCancionPlagiada);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, campoUsuarioPlagiado, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.NORTH, campoUsuarioPlagiado, 5, SpringLayout.SOUTH, labelUsuarioPlagiado);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, labelCancionDenunciada, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.NORTH, labelCancionDenunciada, 5, SpringLayout.SOUTH, campoUsuarioPlagiado);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, campoCancionDenunciada, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.NORTH, campoCancionDenunciada, 5, SpringLayout.SOUTH, labelCancionDenunciada);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, labelUsuarioDenunciado, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.NORTH, labelUsuarioDenunciado, 5, SpringLayout.SOUTH, campoCancionDenunciada);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, campoUsuarioDenunciado, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.NORTH, campoUsuarioDenunciado, 5, SpringLayout.SOUTH, labelUsuarioDenunciado);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, botonSiguiente, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.NORTH, botonSiguiente, 20, SpringLayout.SOUTH, campoUsuarioDenunciado);

		this.setPreferredSize(new Dimension(500, 500));
		this.setVisible(true);
		
		botonSiguiente.addActionListener(this);
		campoCancionPlagiada.addMouseListener(this);
		campoUsuarioPlagiado.addMouseListener(this);
		campoCancionDenunciada.addMouseListener(this);
		campoUsuarioDenunciado.addMouseListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!gui.getControlador().controladorNuevaDenuncia(campoCancionPlagiada.getText(), 
				campoUsuarioPlagiado.getText(), campoCancionDenunciada.getText(), 
				campoUsuarioDenunciado.getText())) {
			setError();
		}
	}
	
	public void limpiarCampos() {
		campoCancionPlagiada.setText("");
		campoUsuarioPlagiado.setText("");
		campoCancionDenunciada.setText("");
		campoUsuarioDenunciado.setText("");
	}
	
	public void setError() {
		campoCancionPlagiada.setText("Error en los campos");
		campoCancionPlagiada.setForeground(java.awt.Color.red);
		campoUsuarioPlagiado.setText("Error en los campos");
		campoUsuarioPlagiado.setForeground(java.awt.Color.red);
		campoCancionDenunciada.setText("Error en los campos");
		campoCancionDenunciada.setForeground(java.awt.Color.red);
		campoUsuarioDenunciado.setText("Error en los campos");
		campoUsuarioDenunciado.setForeground(java.awt.Color.red);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Object campo = e.getSource();
		if (campo==campoCancionPlagiada) {
			campoCancionPlagiada.setText("");
			campoCancionPlagiada.setForeground(java.awt.Color.black);
		} else if (campo==campoUsuarioPlagiado) {
			campoUsuarioPlagiado.setText("");
			campoUsuarioPlagiado.setForeground(java.awt.Color.black);
		} else if (campo==campoCancionDenunciada) {
			campoCancionDenunciada.setText("");
			campoCancionDenunciada.setForeground(java.awt.Color.black);
		} else if (campo == campoUsuarioDenunciado) {
			campoUsuarioDenunciado.setText("");
			campoUsuarioDenunciado.setForeground(java.awt.Color.black);
		}
	}

	//Metodos sin implementar
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}
}