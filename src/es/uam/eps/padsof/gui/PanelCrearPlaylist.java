package es.uam.eps.padsof.gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PanelCrearPlaylist extends JPanel implements ActionListener, MouseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton boton = new JButton("Siguiente");
	private JLabel etiquetaPlaylist = new JLabel("Titulo");
	private JTextField titulo = new JTextField(19);
	private JLabel etiquetaElementos = new JLabel("Elementos de la Playlist");
	private JList<String> canciones;
	private Integer[] ids;
	private JScrollPane scroll;
	private Gui gui;

	public PanelCrearPlaylist(Gui gui, String[] can, Integer[] ids) {
		this.gui = gui;
		this.ids = ids;

		canciones = new JList<String>(can);
		scroll = new JScrollPane(canciones);
		
		SpringLayout layout = new SpringLayout();
		this.setLayout(layout);
		this.add(etiquetaPlaylist);
		this.add(titulo);
		this.add(etiquetaElementos);
		this.add(scroll);
		this.add(boton);

		layout.putConstraint(SpringLayout.WEST, etiquetaPlaylist, 45, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, etiquetaPlaylist, 20, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, titulo, 45, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, titulo, 5, SpringLayout.SOUTH, etiquetaPlaylist);
		layout.putConstraint(SpringLayout.WEST, etiquetaElementos, 45, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, etiquetaElementos, 5, SpringLayout.SOUTH, titulo);
		layout.putConstraint(SpringLayout.WEST, scroll, 45, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, scroll, 5, SpringLayout.SOUTH, etiquetaElementos);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, boton, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.NORTH, boton, 25, SpringLayout.SOUTH, scroll);

		canciones.setPreferredSize(new Dimension(100,100));
		this.setPreferredSize(new Dimension(300, 350));
		this.setVisible(true);
		
		boton.addActionListener(this);
		titulo.addMouseListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		gui.getControlador().controladorSubirPlaylist(titulo.getText(),ids);
	}

	public void setError() {
		titulo.setText("Error elige otro titulo");
		titulo.setForeground(java.awt.Color.red);
	}

	public void limpiarCampos() {
		titulo.setText("");
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		titulo.setText("");
		titulo.setForeground(java.awt.Color.black);
	}
	
	public void mouseExited(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
}
