package co.edu.unbosque.view;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelBotonCambiarTurno extends JPanel {

    public PanelBotonCambiarTurno(ActionListener listener) {
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton btnCambiarTurno = new JButton("Cambiar turno");
        btnCambiarTurno.setActionCommand("GUARDAR_CAMBIAR_TURNO");
        btnCambiarTurno.addActionListener(listener);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setActionCommand("CANCELAR_CAMBIAR_TURNO");
        btnCancelar.addActionListener(listener);

        add(btnCambiarTurno);
        add(btnCancelar);
    }
}
