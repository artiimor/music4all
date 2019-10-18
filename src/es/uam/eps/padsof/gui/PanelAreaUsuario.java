package es.uam.eps.padsof.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

public class PanelAreaUsuario extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton botonPremium = new JButton("Actualizar a Premium");
	private JLabel etiquetaReproducciones = new JLabel();
	private JLabel etiquetaNombre = new JLabel();
	private JLabel etiquetaPremium = new JLabel("Usuario Premium");
	private Gui gui;

	PanelAreaUsuario(Gui gui, Boolean premium) {
		this.gui = gui;

		if (!premium) {
			this.gui.getControlador().controladorDatosUsuario(etiquetaNombre, etiquetaReproducciones);

			SpringLayout layout = new SpringLayout();
			this.setLayout(layout);
			this.add(botonPremium);
			this.add(etiquetaReproducciones);
			this.add(etiquetaNombre);

			layout.putConstraint(SpringLayout.NORTH, etiquetaNombre, 5, SpringLayout.NORTH, this);
			layout.putConstraint(SpringLayout.WEST, etiquetaNombre, 10, SpringLayout.WEST, this);
			layout.putConstraint(SpringLayout.NORTH, etiquetaReproducciones, 15, SpringLayout.SOUTH, etiquetaNombre);
			layout.putConstraint(SpringLayout.WEST, etiquetaReproducciones, 10, SpringLayout.WEST, this);
			layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, botonPremium, 0, SpringLayout.HORIZONTAL_CENTER, this);
			layout.putConstraint(SpringLayout.NORTH, botonPremium, 70, SpringLayout.NORTH, this);

			this.setBorder(BorderFactory.createTitledBorder("Area Usuario"));
			this.setPreferredSize(new Dimension(200, 125));
			this.setVisible(true);
		} else {
			this.gui.getControlador().controladorDatosUsuario(etiquetaNombre, etiquetaReproducciones);

			SpringLayout layout = new SpringLayout();
			this.setLayout(layout);
			this.add(etiquetaPremium);
			this.add(etiquetaNombre);

			layout.putConstraint(SpringLayout.NORTH, etiquetaNombre, 5, SpringLayout.NORTH, this);
			layout.putConstraint(SpringLayout.WEST, etiquetaNombre, 10, SpringLayout.WEST, this);
			layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, etiquetaPremium, 0, SpringLayout.HORIZONTAL_CENTER, this);
			layout.putConstraint(SpringLayout.NORTH, etiquetaPremium, 25, SpringLayout.SOUTH, etiquetaNombre);

			this.setBorder(BorderFactory.createTitledBorder("Area Usuario"));
			this.setPreferredSize(new Dimension(200, 160));
			this.setVisible(true);	
		}

		botonPremium.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		gui.actionPagarPremium();
	}
}
