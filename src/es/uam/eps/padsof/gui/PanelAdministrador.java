package es.uam.eps.padsof.gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelAdministrador extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JButton botonLogout = new JButton("Cerrar Sesion");
	private JTabbedPane tabPestanias = new JTabbedPane();
	private JLabel admin = new JLabel("Administrador");
	private PanelDenuncia panelDenuncia;
	private PanelValidaciones panelValidaciones;
	private Gui gui;

	public PanelAdministrador(Gui gui) {
		this.gui = gui;

		admin.setFont(new Font("Circular", Font.PLAIN, 40));
		tabPestanias = new JTabbedPane();
		actualizarTablas();

		SpringLayout layout = new SpringLayout();
		this.setLayout(layout);
		this.add(tabPestanias);
		this.add(admin);
		this.add(botonLogout);
		
		layout.putConstraint(SpringLayout.NORTH, admin, 25, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, tabPestanias, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.NORTH, tabPestanias, 20, SpringLayout.SOUTH, admin);
		layout.putConstraint(SpringLayout.NORTH, botonLogout, 13, SpringLayout.SOUTH, tabPestanias);
		layout.putConstraint(SpringLayout.EAST, botonLogout, 0, SpringLayout.EAST, this);
		
		this.setPreferredSize(new Dimension(725,600));
		this.setVisible(true);
		
		botonLogout.addActionListener(this);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		Object action = e.getSource();
		
		if (action==botonLogout) {
			gui.getControlador().controladorLogout();
		}
	}
	
	public void actualizarTablas() {
		if (tabPestanias.getComponents()!=null) {
			tabPestanias.removeAll();
		}
		
		panelValidaciones = new PanelValidaciones(gui,gui.getControlador().controladorGetValidaciones());
		panelDenuncia = new PanelDenuncia(gui,gui.getControlador().controladorGetDenuncias());
		tabPestanias.addTab("Validaciones", panelValidaciones);
		tabPestanias.addTab("Reportes", panelDenuncia);
		this.revalidate();
		this.repaint();
	}
}
