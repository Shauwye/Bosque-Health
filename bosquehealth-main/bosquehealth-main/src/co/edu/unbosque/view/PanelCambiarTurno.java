package co.edu.unbosque.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import co.edu.unbosque.model.EspecialistaDTO;
import co.edu.unbosque.model.TurnoDTO;

public class PanelCambiarTurno extends JPanel implements ActionListener{
	
	private JComboBox<EspecialistaDTO> comboEspecialista;
	private JComboBox<TurnoCombo> comboTurno;
	private JTextField textEmail;
	private PanelGestionTurno panelPadre;
    
    public PanelCambiarTurno(PanelGestionTurno panelPadre) {
    	this.panelPadre = panelPadre;
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // ---- Turno ----
        JLabel lblTurno = new JLabel("Turno", SwingConstants.CENTER);
        lblTurno.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1; // Regresa a una sola columna
        this.add(lblTurno, gbc);

        comboTurno = new JComboBox<>();
        gbc.gridx = 1;
        gbc.gridy = 1;
        this.add(comboTurno, gbc);

        // ---- Especialista ----
        JLabel lblEspecialista = new JLabel("Especialista", SwingConstants.CENTER);
        lblEspecialista.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        this.add(lblEspecialista, gbc);

        comboEspecialista = new JComboBox<>();
        comboEspecialista.setActionCommand("ESPECIALISTA_SELECCIONADO");
        comboEspecialista.addActionListener(this);
        gbc.gridx = 1;
        gbc.gridy = 2;
        this.add(comboEspecialista, gbc);

        // ---- Correo ----
        JLabel lblCorreo = new JLabel("Correo Electronico", SwingConstants.CENTER);
        lblCorreo.setFont(new Font("Arial", Font.PLAIN, 14));
       
        gbc.gridx = 0;
        gbc.gridy = 3;
        this.add(lblCorreo, gbc);

        textEmail = new JTextField("Email", 15);
        textEmail.setEnabled(false);
        gbc.gridx = 1;
        gbc.gridy = 3;
        this.add(textEmail, gbc);

        // ---- Panel de botones ----
      
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2; // Ocupa ambas columnas
        PanelBotonCambiarTurno panelBotones = new PanelBotonCambiarTurno(this);
        add(panelBotones, gbc);
    }
    
    public void actualizarEspecialistas(List<EspecialistaDTO> especialistas) {
    	comboEspecialista.removeAllItems();
		for (EspecialistaDTO especialista : especialistas) {
			comboEspecialista.addItem(especialista);
		}
	}
    
    public void actualizarTurnos(List<TurnoCombo> turnos) {
    	comboTurno.removeAllItems();
    	for(TurnoCombo turno: turnos) {
    		comboTurno.addItem(turno);
    	}
    }
    
    private void cerrarPanel() {
    	 Window window = SwingUtilities.getWindowAncestor(this);
         if (window != null) {
             window.dispose(); // Cerrar la ventana
         }
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		EspecialistaDTO especialistaSeleccionado = null;
		
		switch(e.getActionCommand()) {
		case "ESPECIALISTA_SELECCIONADO":
			especialistaSeleccionado = (EspecialistaDTO) comboEspecialista.getSelectedItem();
			textEmail.setText(especialistaSeleccionado.getEmail());
			break;
			
		case "GUARDAR_CAMBIAR_TURNO":
			especialistaSeleccionado = (EspecialistaDTO) comboEspecialista.getSelectedItem();
		
			TurnoCombo turnoSeleccionado = (TurnoCombo) comboTurno.getSelectedItem();
			
			panelPadre.solicitarCambioTurno(especialistaSeleccionado, turnoSeleccionado.getIdTurno());
			
			cerrarPanel();
			break;
			
		case "CANCELAR_CAMBIAR_TURNO":
			cerrarPanel();
			break;
		}
	
		
	}
}
