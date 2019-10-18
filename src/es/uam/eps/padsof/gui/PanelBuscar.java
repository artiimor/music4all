package es.uam.eps.padsof.gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PanelBuscar extends JPanel implements ActionListener, MouseListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField busqueda = new JTextField("Introduce Nombre,Tntulo...",50);
	private JButton botonBusqueda = new JButton("Buscar");
	private JRadioButton opcion1 = new JRadioButton("Autor");
	private JRadioButton opcion2 = new JRadioButton("Cancion");
	private JRadioButton opcion3 = new JRadioButton("Album");
	private ButtonGroup opciones = new ButtonGroup();
	private Boolean usuario=false;
	private Gui gui;
	
	PanelBuscar (Gui gui) {
		this.gui = gui;
		
		opciones.add(opcion1);
		opciones.add(opcion2);
		opciones.add(opcion3);

		this.setLayout(new FlowLayout());
		this.add(busqueda);
		this.add(botonBusqueda);
		this.add(opcion1);
		this.add(opcion2);
		this.add(opcion3);
		
		this.setPreferredSize(new Dimension(650,125)); 
		this.setVisible(true);
		
		botonBusqueda.addActionListener(this);
		busqueda.addMouseListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!busqueda.getText().isEmpty() && (opcion1.isSelected() ||
				opcion2.isSelected() || opcion3.isSelected())) {
			if (opcion1.isSelected()) {
				gui.getControlador().controladorBuscar(busqueda.getText(),opcion1.getText(),usuario);
			} else if (opcion2.isSelected()) {
				gui.getControlador().controladorBuscar(busqueda.getText(),opcion2.getText(),usuario);
			} else if (opcion3.isSelected()) {
				gui.getControlador().controladorBuscar(busqueda.getText(),opcion3.getText(),usuario);
			}
		}
	}
	
	public void cambiarTamBusqueda(int tam) {
		if (tam<0) {
			return;
		}
		busqueda.setColumns(tam);
	}
	
	public void cambiarTamano(int width, int height) {
		this.setPreferredSize(new Dimension (width,height));
	}
	
	public void cambiarAUsuarioRegistrado() {
		this.usuario=true;
	}
	
	public void cambiarAUsuarioNormal() {
		this.usuario=false;
	}
	
	public void limpiarCampos() {
		this.busqueda.setText("Introduce Nombre,Tntulo...");
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		busqueda.setText("");
		busqueda.setForeground(java.awt.Color.black);
	}

	//Metodos sin implementar
	public void mouseExited(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
}
