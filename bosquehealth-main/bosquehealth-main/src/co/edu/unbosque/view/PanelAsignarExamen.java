package co.edu.unbosque.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import org.jdesktop.swingx.JXDatePicker;
import co.edu.unbosque.model.PacienteDTO;
import co.edu.unbosque.model.EspecialistaDTO;

public class PanelAsignarExamen extends JPanel {
    private JComboBox<PacienteDTO> comboPaciente;
    private JComboBox<EspecialistaDTO> comboEspecialista;
    private JComboBox<String> comboTipoExamen;
    private JXDatePicker datePicker;
    private JTextArea textCorreo;

    public PanelAsignarExamen(ActionListener listener) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // ---- Paciente ----
        JLabel lblPaciente = new JLabel("Paciente", SwingConstants.CENTER);
        lblPaciente.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(lblPaciente, gbc);

        comboPaciente = new JComboBox<>();
        gbc.gridx = 1;
        gbc.gridy = 0;
        this.add(comboPaciente, gbc);

        // ---- Especialista ----
        JLabel lblEspecialista = new JLabel("Especialista", SwingConstants.CENTER);
        lblEspecialista.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(lblEspecialista, gbc);

        comboEspecialista = new JComboBox<>();
        gbc.gridx = 1;
        gbc.gridy = 1;
        this.add(comboEspecialista, gbc);

        // ---- Tipo de examen ----
        JLabel lblTipoExamen = new JLabel("Tipo de Examen", SwingConstants.CENTER);
        lblTipoExamen.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        this.add(lblTipoExamen, gbc);

        comboTipoExamen = new JComboBox<>(new String[] { "Seleccionar", "Radiografía", "Análisis de Sangre", "MRI" });
        gbc.gridx = 1;
        gbc.gridy = 2;
        this.add(comboTipoExamen, gbc);

        // ---- Fecha ----
        JLabel lblFecha = new JLabel("Fecha", SwingConstants.CENTER);
        lblFecha.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        this.add(lblFecha, gbc);

        datePicker = new JXDatePicker();
        gbc.gridx = 1;
        gbc.gridy = 3;
        this.add(datePicker, gbc);

        // ---- Correo ----
        JLabel lblCorreo = new JLabel("Correo Electrónico", SwingConstants.CENTER);
        lblCorreo.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 4;
        this.add(lblCorreo, gbc);

        textCorreo = new JTextArea(1, 15);
        gbc.gridx = 1;
        gbc.gridy = 4;
        this.add(textCorreo, gbc);

        // ---- Panel de botones ----
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        PanelBotonAsignarExamen panelBotones = new PanelBotonAsignarExamen(listener);
        add(panelBotones, gbc);
    }

    // Getters for selected data
    public PacienteDTO getPacienteSeleccionado() {
        return (PacienteDTO) comboPaciente.getSelectedItem();
    }

    public EspecialistaDTO getEspecialistaSeleccionado() {
        return (EspecialistaDTO) comboEspecialista.getSelectedItem();
    }

    public String getTipoExamenSeleccionado() {
        return (String) comboTipoExamen.getSelectedItem();
    }

    public java.util.Date getFechaSeleccionada() {
        return datePicker.getDate();
    }

    public String getCorreoPaciente() {
        return textCorreo.getText();
    }

    public void actualizarPacientes(List<PacienteDTO> pacientes) {
        comboPaciente.removeAllItems();
        for (PacienteDTO paciente : pacientes) {
            comboPaciente.addItem(paciente);
        }
    }

    public void actualizarEspecialistas(List<EspecialistaDTO> especialistas) {
        comboEspecialista.removeAllItems();
        for (EspecialistaDTO especialista : especialistas) {
            comboEspecialista.addItem(especialista);
        }
    }
}
