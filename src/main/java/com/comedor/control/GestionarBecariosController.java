package com.comedor.control;

import com.comedor.model.PersistenciaManager;
import com.comedor.model.User;
import com.comedor.model.User.Role;
import com.comedor.view.GestionarMenuView;
import com.comedor.utils.ModelUtils;
import com.comedor.view.EstiloGral;
import com.comedor.view.gestionarBecariosView;

public class GestionarBecariosController {
    private final NavigationDelegate delegate;
    private final gestionarBecariosView gestionarBecariosView;
    private final PersistenciaManager persistenciaManager;

    public GestionarBecariosController(gestionarBecariosView gestionarBecariosView, PersistenciaManager persistenciaManager, NavigationDelegate delegate) {
        this.gestionarBecariosView = gestionarBecariosView;
        this.persistenciaManager = persistenciaManager;
        this.delegate = delegate;
        setupListeners();
    }

    private void setupListeners(){
        gestionarBecariosView.find("backBtn").onClick(b -> {
            salirDeVentana();
        });

        gestionarBecariosView.find("findUser").onClick(b -> {
            
        });

        gestionarBecariosView.find("becarBtn").onClick(b -> {
            String cedula = gestionarBecariosView.getCedula();
            if(isValidCedula(cedula)){
                gestionarBecariosView.showModal();
            }
        });

        gestionarBecariosView.find("exonerarBtn").onClick(b -> {
            // get cedula y todo eso
            String cedula = gestionarBecariosView.getCedula();
            if(isValidCedula(cedula)){
                guardarDatosDelUser(cedula, 0.0, Role.EXONERADO); // o -1.0
            }
        });

        gestionarBecariosView.find("confirmarBtn").onClick(b -> {
            String cedula = gestionarBecariosView.getCedula();
            Double descuento = gestionarBecariosView.getDescuento().doubleValue();
            guardarDatosDelUser(cedula, descuento, Role.BECARIO);
        });
    }
    
    private void guardarDatosDelUser(String cedula, Double descuento, Role nuevoRole) {
        
        if (!isValidInputs(descuento)) {
            return;
        }
        persistenciaManager.ActualizarDatosUser(cedula, descuento, nuevoRole);
        salirDeVentana();
    }

    public boolean isValidCedula(String cedula){
        boolean flag = true;
        if (cedula.isEmpty()) {
            gestionarBecariosView.InvalidateInputs("cedula");
            com.comedor.view.EstiloGral.ShowMessage("Ingrese una cedula para continuar", EstiloGral.ERROR_MESSAGE);
            flag = false;
        }
        if (!persistenciaManager.isCedulaRegistered(cedula)) {
            gestionarBecariosView.InvalidateInputs("cedula");
            com.comedor.view.EstiloGral.ShowMessage("Esta cedula no esta registrada en la base de datos", EstiloGral.ERROR_MESSAGE);
            flag = false;
        }
        
        return flag;
    }

    public boolean isValidInputs(Double descuento) {
        boolean flag = true;
        if(descuento == null || descuento < 0){
            gestionarBecariosView.InvalidateInputs("descuento");
            com.comedor.view.EstiloGral.ShowMessage("Ingrese un descuento valido", EstiloGral.ERROR_MESSAGE);
            flag = false;
        }
        
        Double DescuentoEstudiante = persistenciaManager.getPorcentajeFromRole("ESTUDIANTE");
        if(DescuentoEstudiante == null){
            gestionarBecariosView.InvalidateInputs("descuento");
            com.comedor.view.EstiloGral.ShowMessage("Error al obtener el descuento de Usuario", EstiloGral.ERROR_MESSAGE);
            flag = false;
        } else if(descuento >= DescuentoEstudiante){
            gestionarBecariosView.InvalidateInputs("descuento");
            com.comedor.view.EstiloGral.ShowMessage("Ingrese un descuento menor que el de Usuario", EstiloGral.ERROR_MESSAGE);
            flag = false;
        }
        return flag;
    }

    private void salirDeVentana() {
        delegate.onBackToPanelAdminRequested();
}
