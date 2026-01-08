package co.edu.unbosque.view;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelBotonAsignarExamen extends JPanel {

    public PanelBotonAsignarExamen(ActionListener listener) {
        setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton btnGuardar = new JButton("GUARDAR");
        btnGuardar.setActionCommand("GUARDAR_EXAMEN");
        btnGuardar.addActionListener(listener);
        
        JButton btnLimpiar = new JButton("LIMPIAR");
        btnLimpiar.setActionCommand("LIMPIAR_EXAMEN");
        btnLimpiar.addActionListener(listener);

        add(btnGuardar);
        add(btnLimpiar);
    }
}
