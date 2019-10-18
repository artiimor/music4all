package es.uam.eps.padsof.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;

public class PanelRegistrar extends JPanel implements ActionListener, MouseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String[] meses = { "enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto", "septiembre",
			"octubre", "noviembre", "diciembre" };
	private String[] dias = new String[31];
	private String[] anos = new String[100];
	private JLabel etiquetaTitulo = new JLabel("Crear cuenta");
	private JButton botonReg = new JButton("Completar registro");
	private JLabel etiquetaUser = new JLabel("Nombre usuario");
	private JTextField name = new JTextField(19);
	private JLabel etiquetaPassword = new JLabel("Contrasena");
	private JTextField password = new JTextField(13);
	private JLabel cetiquetaPassword = new JLabel("Confirmacion");
	private JTextField cpassword = new JTextField(13);
	private JLabel etiquetaFecha = new JLabel("Fecha de nacimiento");
	private JComboBox<String> dia;
	private JComboBox<String> mes = new JComboBox<String>(meses);
	private JComboBox<String> ano;
	private Gui gui;

	PanelRegistrar(Gui gui) {
		this.gui = gui;

		dia = new JComboBox<String>();
		rellenarDias("enero");

		for (int i = 0; i <= 99; i++) {
			anos[i] = Integer.toString(i + 1920);
		}
		ano = new JComboBox<String>(anos);
		ano.setSelectedIndex(99);

		SpringLayout layout = new SpringLayout();
		this.setLayout(layout);
		this.add(etiquetaTitulo);
		this.add(etiquetaUser);
		this.add(name);
		this.add(etiquetaPassword);
		this.add(cetiquetaPassword);
		this.add(password);
		this.add(cpassword);
		this.add(etiquetaFecha);
		this.add(dia);
		this.add(mes);
		this.add(ano);
		this.add(botonReg);

		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, etiquetaTitulo, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.NORTH, etiquetaTitulo, 15, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, etiquetaUser, 35, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, etiquetaUser, 35, SpringLayout.SOUTH, etiquetaTitulo);
		layout.putConstraint(SpringLayout.WEST, name, 35, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, name, 5, SpringLayout.SOUTH, etiquetaUser);
		layout.putConstraint(SpringLayout.WEST, etiquetaPassword, 35, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, etiquetaPassword, 20, SpringLayout.SOUTH, name);
		layout.putConstraint(SpringLayout.WEST, password, 35, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, password, 5, SpringLayout.SOUTH, etiquetaPassword);
		layout.putConstraint(SpringLayout.WEST, cetiquetaPassword, 90, SpringLayout.EAST, etiquetaPassword);
		layout.putConstraint(SpringLayout.NORTH, cetiquetaPassword, 20, SpringLayout.SOUTH, name);
		layout.putConstraint(SpringLayout.WEST, cpassword, 10, SpringLayout.EAST, password);
		layout.putConstraint(SpringLayout.NORTH, cpassword, 5, SpringLayout.SOUTH, cetiquetaPassword);
		layout.putConstraint(SpringLayout.WEST, etiquetaFecha, 35, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, etiquetaFecha, 20, SpringLayout.SOUTH, password);
		layout.putConstraint(SpringLayout.WEST, dia, 65, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, dia, 5, SpringLayout.SOUTH, etiquetaFecha);
		layout.putConstraint(SpringLayout.WEST, mes, 25, SpringLayout.EAST, dia);
		layout.putConstraint(SpringLayout.NORTH, mes, 5, SpringLayout.SOUTH, etiquetaFecha);
		layout.putConstraint(SpringLayout.WEST, ano, 25, SpringLayout.EAST, mes);
		layout.putConstraint(SpringLayout.NORTH, ano, 5, SpringLayout.SOUTH, etiquetaFecha);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, botonReg, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.NORTH, botonReg, 50, SpringLayout.SOUTH, mes);

		this.setPreferredSize(new Dimension(375, 375));
		this.setVisible(true);
		
		botonReg.addActionListener(this);
		mes.addActionListener(this);
		password.addMouseListener(this);
		name.addMouseListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object action = e.getSource();
		
		if (action==botonReg) {	
			if (cpassword.getText().contentEquals(password.getText()) && !(password.getText().isEmpty())
					&& !(name.getText().isEmpty())) {
				LocalDate date = LocalDate.now().withDayOfMonth(dia.getSelectedIndex() + 1)
						.withMonth(mes.getSelectedIndex() + 1).withYear(ano.getSelectedIndex() + 1920);
				gui.getControlador().controladorRegistro(name.getText(), password.getText(), date);
				return;
			}
			setError(true);
		} else if (action==mes) {
			dia.removeAllItems();
			String mesSelec = (String)mes.getSelectedItem();
			rellenarDias(mesSelec);
		}
	}

	public void setError(Boolean error) {
		limpiarCampos();
		if (error) {
			password.setText("La contrasena no coincide");
			password.setForeground(java.awt.Color.red);
		} else {
			name.setText("El nombre de usuario ya existe");
			name.setForeground(java.awt.Color.red);
		}
	}
	
	public void limpiarCampos() {
		cpassword.setText("");
		password.setText("");
		name.setText("");
	}
	
	public void rellenarDias(String mes) {
		if (mes.contentEquals("enero")|| mes.contentEquals("marzo")||
				mes.contentEquals("mayo") || mes.contentEquals("julio") ||
					mes.contentEquals("agosto")|| mes.contentEquals("octubre")||
						mes.contentEquals("diciembre")) {		
			for (int i = 0; i <= 30; i++) {
				dias[i] = Integer.toString(i + 1);
				dia.addItem(dias[i]);
			}
		}
		else if (mes.contentEquals("abril")|| mes.contentEquals("junio")||
					mes.contentEquals("septiembre") || mes.contentEquals("noviembre")) {
			for (int i = 0; i < 30; i++) {
				dias[i] = Integer.toString(i + 1);
				dia.addItem(dias[i]);
			}
		} else if (mes.contentEquals("febrero")) {
			for (int i = 0; i < 28; i++) {
				dias[i] = Integer.toString(i + 1);
				dia.addItem(dias[i]);
			}
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		Object action = e.getSource();
		if (action==password) {
			password.setText("");
			password.setForeground(java.awt.Color.black);
		} else if (action==name) {
			name.setText("");
			name.setForeground(java.awt.Color.black);
		}
	}

	// Metodos sin implementar
	public void mouseExited(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
}
