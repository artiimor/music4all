package es.uam.eps.padsof.gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelLogo extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel logo = new JLabel("Music4all");
	
	PanelLogo () {
		logo.setFont(new Font("Circular", Font.PLAIN, 73));
		logo.setForeground(new Color(20, 220, 150));
		
		this.add(logo);
		this.setVisible(true);
	}
	
	public void cambiarTamano(int tam) {
		if (tam<0) {
			return;
		}
		
		logo.setFont(new Font("Circular", Font.PLAIN, tam));
	}
}
