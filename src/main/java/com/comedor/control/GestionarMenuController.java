package com.comedor.control;

import com.comedor.model.PersistenciaManager;
import com.comedor.view.GestionarMenuView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;


public class GestionarMenuController implements ActionListener {
    private GestionarMenuView vista;
    PersistenciaManager pm = new PersistenciaManager();
  
public GestionarMenuController(GestionarMenuView vista) {
        this.vista = vista;
        

        this.vista.btnPublicar.addActionListener(this);
        this.vista.btnCancelarMenu.addActionListener(this);
        this.vista.btnVolver.addActionListener(this);
    }


    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == vista.btnPublicar){
            guardarDatosDelMenu();
        }else if (e.getSource() == vista.btnCancelarMenu) {
            limpiarFormulario();
        }else if (e.getSource() == vista.btnVolver) {
        salirDeVentana();
        }   
    }
    
    private void guardarDatosDelMenu() {

        String fecha = vista.getTxtFecha().getText(); //esto es solo del view de prueba que hice, modifica si necesitas el como llegan los datos del form a las variables
        String plato = vista.getTxtPlatoPrincipal().getText();
        String ingredientes = vista.getTxtIngredientes().getText();
        String tipo = "";

        if(vista.getCmbTipoComida().getSelectedItem() != null) {
             tipo = vista.getCmbTipoComida().getSelectedItem().toString();
        }

        if (fecha.isEmpty() || plato.isEmpty() || ingredientes.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Por favor, llene todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean exito = pm.guardarMenu(plato, ingredientes, tipo, fecha); //la funcion de guardar sea boolean y retorne true para que esto funcione

        if (exito) {
            JOptionPane.showMessageDialog(vista, "¡Menú guardado exitosamente en menus_db.txt!");
            limpiarFormulario();
        } else {
            JOptionPane.showMessageDialog(vista, "Hubo un error al guardar el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


private void limpiarFormulario() {
    vista.getTxtFecha().setText("");
    vista.getTxtPlatoPrincipal().setText("");
    vista.getTxtIngredientes().setText("");
    
    vista.getCmbTipoComida().setSelectedIndex(0);
}


private void salirDeVentana() {
    int confirmacion = JOptionPane.showConfirmDialog(vista,
    "¿Desea volver al menú principal?", "Salir", JOptionPane.YES_NO_OPTION);
    
    if (confirmacion == JOptionPane.YES_OPTION) {
        vista.dispose();         
    }
}

    
}
