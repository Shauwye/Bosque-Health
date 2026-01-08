package co.edu.unbosque.view;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelBotonEditarCita extends JPanel {

    public PanelBotonEditarCita(ActionListener listener) {
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton btnGuardarCambios = new JButton("Guardar Cambios");
        btnGuardarCambios.setActionCommand("GuardarCambiosCita");
        btnGuardarCambios.addActionListener(listener);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setActionCommand("CancelarEdicionCita");
        btnCancelar.addActionListener(listener);

        add(btnGuardarCambios);
        add(btnCancelar);
    }
}
