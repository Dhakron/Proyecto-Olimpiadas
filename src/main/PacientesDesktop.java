package main;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class PacientesDesktop extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PacientesDesktop frame = new PacientesDesktop();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PacientesDesktop() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 643, 434);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
//		ConsultarRegistros panel = new ConsultarRegistros();
//		contentPane.add(panel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(250, 126, 363, 248);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		ButtonGroup rGroup = new ButtonGroup();
		JRadioButton rdbtnNewRadioButton = new JRadioButton("Obtener el N Seg.Social a partir del DNI");
		rdbtnNewRadioButton.setBounds(11, 48, 341, 29);
		rGroup.add(rdbtnNewRadioButton);
		panel_1.add(rdbtnNewRadioButton);
		JRadioButton rdbtnNewRadioButton2 = new JRadioButton("Obtener DNI a partir del N SegSocial");
		rGroup.add(rdbtnNewRadioButton2);
		rdbtnNewRadioButton2.setBounds(11, 85, 341, 29);
		panel_1.add(rdbtnNewRadioButton2);
		JRadioButton rdbtnNewRadioButton3 = new JRadioButton("Obtener DNI/DNIs a partir del nombre");
		rdbtnNewRadioButton3.setBounds(11, 122, 341, 29);
		rGroup.add(rdbtnNewRadioButton3);
		panel_1.add(rdbtnNewRadioButton3);
		JLabel lblNewLabel = new JLabel("\u00BFQue dato desea consultar?");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(15, 16, 337, 20);
		panel_1.add(lblNewLabel);
		
		JButton btnAceptar_1 = new JButton("Consultar");
		btnAceptar_1.setBounds(233, 187, 115, 29);
		panel_1.add(btnAceptar_1);
		
		textField = new JTextField();
		textField.setBounds(11, 188, 207, 26);
		panel_1.add(textField);
		textField.setColumns(10);
		
	}
	private class ConsultarRegistros extends JPanel{
		JRadioButton rbDNI;
		JRadioButton rbSegSocial;
		JRadioButton rbNombre;
		JButton btnAceptar;
		JTextField tfConsulta;
		JTextArea taInfo;
		MapaTriple datos;
		public ConsultarRegistros(MapaTriple datos) {
			super();
			setBounds(250, 126, 363, 248);
			setVisible(true);
			setLayout(null);
			this.datos= datos;
			ButtonGroup rGroup = new ButtonGroup();
			
			rbSegSocial = new JRadioButton("Obtener el N Seg.Social a partir del DNI");
			rbSegSocial.setBounds(11, 48, 341, 29);
			rGroup.add(rbSegSocial);
			add(rbSegSocial);
			
			rbDNI = new JRadioButton("Obtener DNI a partir del N SegSocial");
			rbDNI.setBounds(11, 85, 341, 29);
			rGroup.add(rbDNI);
			
			rbNombre = new JRadioButton("Obtener DNI/DNIs a partir del nombre");
			rbNombre.setBounds(11, 122, 341, 29);
			rGroup.add(rbNombre);
			add(rbNombre);
			JLabel lblNewLabel = new JLabel("Que datos desea consultar?");
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
			lblNewLabel.setBounds(83, 16, 226, 20);
			add(lblNewLabel);
			
			btnAceptar = new JButton("Consultar");
			btnAceptar.setBounds(233, 187, 115, 29);
			add(btnAceptar);
			
			tfConsulta = new JTextField();
			tfConsulta.setBounds(11, 188, 207, 26);
			add(tfConsulta);
		}
		private void consultar() {
			if(rbDNI.isSelected()) {
				consultarDNIconSegSocial();
			}else if(rbSegSocial.isSelected()) {
				consultarSegSocialconDNI();
			}else {
				conultarDNIconNombre();
			}
		}
		private void conultarDNIconNombre() {
			if(validarNombre(tfConsulta.getText())){
				try {
					taInfo.append(datos.dni(tfConsulta.getText()));
				} catch (NoExiste e) {
					taInfo.append(e.getMessage());
				}
			}else {
				taInfo.append("Nombre no valido");
			}
		}
		private void consultarSegSocialconDNI() {
			if(validarDNI(tfConsulta.getText())){
				try {
					taInfo.append(datos.segsocial(tfConsulta.getText()).toString());
				} catch (NoExiste e) {
					taInfo.append(e.getMessage());
				}
			}else {
				taInfo.append("DNI no valido");
			}
		}
		private void consultarDNIconSegSocial() {
			if(validarSegSocial(tfConsulta.getText())){
				try {
					taInfo.append(datos.dni(Integer.parseInt(tfConsulta.getText())));
				} catch (NoExiste e) {
					taInfo.append(e.getMessage());
				}
			}else {
				taInfo.append("Nombre no valido");
			}
		}
		
	}
	private static Boolean validarNombre(String nombre) {
		if (nombre.length() < 1 || nombre.startsWith(" ") || nombre.endsWith(" ")) {
			return false;
		} else {
			for (int i = 0; i < nombre.length(); i++) {
				if (!Character.isLetter(nombre.charAt(i))) {
					if (nombre.charAt(i) == ' ' && nombre.charAt(i - 1) != ' ') {
						continue;
					} else {
						return false;
					}
				}
			}
			return true;
		}
	}

	private static Boolean validarSegSocial(String segSocial) {
		for (int i = 0; i < segSocial.length(); i++) {
			if (!Character.isDigit(segSocial.charAt(i))) {
				return false;
			}
		}
		if (Long.parseLong(segSocial) < 0 || String.valueOf(segSocial).length() != 12) {
			return false;
		} else {
			int nProvincia = Integer.parseInt(segSocial.substring(0, 2));
			int nAfiliado = Integer.parseInt(segSocial.substring(2, 10));
			int codControl = Integer.parseInt(segSocial.substring(10));
			long npYna = Long.parseLong(segSocial.substring(0, 10));
			if ((nProvincia > 0 && nProvincia < 54) || nProvincia == 66) {
				if (nAfiliado < 10000000) {
					if (codControl == (nAfiliado + nProvincia * 10000000) % 97) {
						return true;
					}
				} else {
					if (codControl == npYna % 97) {
						return true;
					}
				}
			}
			return false;
		}
	}

	private static Boolean validarDNI(String dni) {
		char[] letras = { 'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H',
				'L', 'C', 'K', 'E' };
		if (dni.startsWith("X")) {
			dni = "0" + dni.substring(1);
		} else if (dni.startsWith("Y")) {
			dni = "1" + dni.substring(1);
		} else if (dni.startsWith("Z")) {
			dni = "2" + dni.substring(1);
		}
		if (dni.length() != 9 || dni.contains(" ")) {
			return false;
		} else {
			for (int i = 0; i < dni.length(); i++) {
				if (Character.isLetter(dni.charAt(i)) && i != 8) {
					return false;
				}
			}
			if (letras[Integer.parseInt(dni.substring(0, 8)) % 23] == dni.charAt(8)) {
				return true;
			} else {
				return false;
			}
		}
	}
}
