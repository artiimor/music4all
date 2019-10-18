package es.uam.eps.padsof.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

public class PanelLogueoRegistro extends JPanel implements ActionListener{ 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton botonLogin = new JButton("Iniciar sesion");
	private JButton botonReg = new JButton ("Registrarse");
	private Gui gui;
	
	PanelLogueoRegistro(Gui gui) {
		this.gui = gui;
		
		botonReg.setPreferredSize(new Dimension(144,30));
		botonLogin.setPreferredSize(new Dimension(144,30));
		
		SpringLayout layout = new SpringLayout();
		this.setLayout(layout);
		this.add(botonLogin);
		this.add(botonReg);
		
		layout.putConstraint(SpringLayout.NORTH, botonLogin, 15, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, botonLogin, 13, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.SOUTH, botonReg, 38, SpringLayout.SOUTH, botonLogin);
		layout.putConstraint(SpringLayout.WEST, botonReg, 13, SpringLayout.WEST, this);
		
		this.setBorder(BorderFactory.createTitledBorder("Area Usuario"));
		this.setPreferredSize(new Dimension(180,125));
		this.setVisible(true);
		
		botonReg.addActionListener(this);
		botonLogin.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object boton = e.getSource();
		if (boton==botonReg) {
			gui.actionRegistrarse();
		}
		else if (boton==botonLogin) {
			gui.actionLoguearse();
		}
	}
}
