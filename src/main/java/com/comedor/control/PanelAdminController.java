package com.comedor.control;
import com.comedor.model.PersistenciaManager;
import com.comedor.view.PanelAdminView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class PanelAdminController implements ActionListener {

    private PanelAdminView vista;
    private PersistenciaManager PM;

    public PanelAdminController(PanelAdminView vista) {
        this.vista = vista;
        this.PM = new PersistenciaManager(); 

        this.vista.btnCambiarPorcTrabajador.addActionListener(this);
        this.vista.btnCambiarPorcProfesor.addActionListener(this);
        this.vista.btnIngresarCCB.addActionListener(this);
    }

    private void actualizarTarifa(String tipo) {
        try {
            
            double precioCCB = Double.parseDouble(vista.txtPrecioCCB.getText());
            
            double porcentaje;
            double tarifaFinal;

           
            if (tipo.equals("Trabajador")) {
                porcentaje = Double.parseDouble(vista.txtPorcTrabajador.getText());
                tarifaFinal = precioCCB + (precioCCB * (porcentaje / 100));
                PM.guardarDatosTrabajador(precioCCB, porcentaje, tarifaFinal);
                
                JOptionPane.showMessageDialog(vista, "Tarifa Trabajador actualizada: " + tarifaFinal + " Bs.");
            
            } else if (tipo.equals("Profesor")) {
                porcentaje = Double.parseDouble(vista.txtPorcProfesor.getText());
                tarifaFinal = precioCCB + (precioCCB * (porcentaje / 100));
                PM.guardarDatosProfesor(precioCCB, porcentaje, tarifaFinal);
                
                JOptionPane.showMessageDialog(vista, "Tarifa Profesor actualizada: " + tarifaFinal + " Bs.");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "Error: Asegúrate de ingresar números válidos en CCB y Porcentaje.", 
                "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }

     public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnCambiarPorcTrabajador) {
            actualizarTarifa("Trabajador");
        } 
        else if (e.getSource() == vista.btnCambiarPorcProfesor) {
            actualizarTarifa("Profesor");
        }
    }
}