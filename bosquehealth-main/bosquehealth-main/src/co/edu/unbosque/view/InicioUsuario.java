package co.edu.unbosque.view;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


public class InicioUsuario extends JPanel {
	private JComboBox<String> comboTipoUsuario;
	private JTextField txtUsuario;
	private JPasswordField txtContrasena;
	private JButton btnIngresar;
	private JButton btnRegistrarse;

	public InicioUsuario(ActionListener listener) {
		FlatLightLaf.setup();
		initComponents(listener);
	}

	private void initComponents(ActionListener listener) {
		setLayout(new BorderLayout());
		setBackground(Color.WHITE);

		String imagePath = "img/Fondo.jpeg";
		JLabel background = new JLabel(new ImageIcon(imagePath));
		background.setLayout(new BorderLayout());
		this.add(background, BorderLayout.CENTER);

		JPanel formPanel = new JPanel(new GridBagLayout());
		formPanel.setOpaque(false);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		leftPanel.setOpaque(false);

		// Tipo de usuario
		comboTipoUsuario = new JComboBox<>(new String[] { "Especialista", "Director Medico" });
		comboTipoUsuario.setFont(new Font("Arial", Font.PLAIN, 18));
		comboTipoUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);
		leftPanel.add(comboTipoUsuario);
		leftPanel.add(Box.createVerticalStrut(10));

		// Campo de usuario con texto "usuario" en gris
		txtUsuario = new JTextField("usuario", 15);
		txtUsuario.setFont(new Font("Arial", Font.PLAIN, 18));
		txtUsuario.setForeground(Color.GRAY);
		leftPanel.add(txtUsuario);
		leftPanel.add(Box.createVerticalStrut(10));
		txtUsuario.addFocusListener(new java.awt.event.FocusAdapter() {
			@Override
			public void focusGained(java.awt.event.FocusEvent e) {
				if (txtUsuario.getText().equals("usuario")) {
					txtUsuario.setText("");
					txtUsuario.setForeground(Color.BLACK);
				}
			}
			@Override
			public void focusLost(java.awt.event.FocusEvent e) {
				if (txtUsuario.getText().isEmpty()) {
					txtUsuario.setForeground(Color.GRAY);
					txtUsuario.setText("usuario");
				}
			}
		});

		// Campo de contraseña con texto "contraseña" en gris
		txtContrasena = new JPasswordField("contraseña", 15);
		txtContrasena.setFont(new Font("Arial", Font.PLAIN, 18));
		txtContrasena.setForeground(Color.GRAY);
		leftPanel.add(txtContrasena);
		txtContrasena.setEchoChar((char) 0); // No oculta texto al cargar

		txtContrasena.addFocusListener(new java.awt.event.FocusAdapter() {
			@Override
			public void focusGained(java.awt.event.FocusEvent e) {
				if (new String(txtContrasena.getPassword()).equals("contraseña")) {
					txtContrasena.setText("");
					txtContrasena.setForeground(Color.BLACK);
					txtContrasena.setEchoChar('•'); // Muestra puntos para ocultar texto
				}
			}
			@Override
			public void focusLost(java.awt.event.FocusEvent e) {
				if (new String(txtContrasena.getPassword()).isEmpty()) {
					txtContrasena.setForeground(Color.GRAY);
					txtContrasena.setText("contraseña");
					txtContrasena.setEchoChar((char) 0); // Muestra texto sin ocultar
				}
			}
		});

		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		rightPanel.setOpaque(false);

		// Botón Ingresar
		btnIngresar = new JButton("Ingresar");
		btnIngresar.setFont(new Font("Arial", Font.PLAIN, 18));
		btnIngresar.setActionCommand("INGRESAR");
		btnIngresar.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnIngresar.setBackground(Color.WHITE);
		btnIngresar.setForeground(Color.BLACK);
		btnIngresar.putClientProperty("JButton.buttonType", "roundRect");
		btnIngresar.addActionListener(listener);
		rightPanel.add(btnIngresar);
		rightPanel.add(Box.createVerticalStrut(10));

		// Botón Registrarse
		btnRegistrarse = new JButton("Registrarse");
		btnRegistrarse.setFont(new Font("Arial", Font.PLAIN, 18));
		btnRegistrarse.setActionCommand("REGISTRARSE");
		btnRegistrarse.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnRegistrarse.setBackground(Color.WHITE);
		btnRegistrarse.setForeground(Color.BLACK);
		btnRegistrarse.putClientProperty("JButton.buttonType", "roundRect");
		btnRegistrarse.addActionListener(listener);
		rightPanel.add(btnRegistrarse);

		// Añadir los paneles al formulario
		gbc.gridx = 0;
		gbc.gridy = 0;
		formPanel.add(leftPanel, gbc);

		gbc.gridx = 1;
		formPanel.add(rightPanel, gbc);
		background.add(formPanel, BorderLayout.SOUTH);
	}

	public String getTipoUsuarioSeleccionado() {
		return (String) comboTipoUsuario.getSelectedItem();
	}

	public String getUsuario() {
		return txtUsuario.getText().equals("usuario") ? "" : txtUsuario.getText();
	}

	public String getContrasena() {
		String password = new String(txtContrasena.getPassword());
		return password.equals("contraseña") ? "" : password;
	}
}