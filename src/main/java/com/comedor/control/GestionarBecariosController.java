package com.comedor.control;

import javax.swing.SwingUtilities;

import com.comedor.model.PersistenciaManager;
import com.comedor.model.User.Role;
import com.comedor.utils.ModelUtils;
import com.comedor.view.EstiloGral;
import com.comedor.view.GestionarBecariosView;

public class GestionarBecariosController {
    private final NavigationDelegate delegate;
    private final GestionarBecariosView view;
    private final PersistenciaManager persistenciaManager;

    public GestionarBecariosController(GestionarBecariosView view, PersistenciaManager persistenciaManager, NavigationDelegate delegate) {
        this.view = view;
        this.persistenciaManager = persistenciaManager;
        this.delegate = delegate;
        setupListeners();
    }

    private void setupListeners(){
        view.find("backBtn").onClick(b -> {
            salirDeVentana();
        });

        view.find("becarBtn").onClick(b -> {
            String cedula = view.getCedula();
            if(isValidCedulaForBeca(cedula)){
                view.showModal();
            }
        });

        SwingUtilities.invokeLater(() -> 

            view.getModal().find("confirmarBtn").onClick(b -> {
                handleBecar();
            })
            
        );

        view.find("exonerarBtn").onClick(b -> {
            handleExonerar();
        });
    }


    void handleBecar(){
        String cedula = view.getCedula();
        String descuento = view.getDescuento();
        if(isValidDescuento(descuento)){
            guardarDatosDelUser(cedula, Double.parseDouble(descuento), Role.BECARIO); // o -1.0
            view.hideModal();
        }
    }

    private void handleExonerar(){
        String cedula = view.getCedula();
        if(isValidCedulaForExonerado(cedula)){
            guardarDatosDelUser(cedula, 0.0, Role.EXONERADO); // o -1.0
        }
    }
    
    private void guardarDatosDelUser(String cedula, Double descuento, Role nuevoRole) {
        persistenciaManager.ActualizarDatosUser(cedula, descuento, nuevoRole);
        if(nuevoRole == Role.BECARIO){
            EstiloGral.ShowMessage("Usuario becado exitosamente", EstiloGral.SUCCESS_MESSAGE);
        } else if (nuevoRole == Role.EXONERADO){
            EstiloGral.ShowMessage("Usuario exonerado exitosamente", EstiloGral.SUCCESS_MESSAGE);
        }
    }

    public boolean isValidCedula(String cedula){
        boolean flag = true;
        if (cedula == null || cedula.trim().isEmpty()) {
            view.InvalidateInputs("cedula");
            EstiloGral.ShowMessage("Ingrese una cedula para continuar", EstiloGral.ERROR_MESSAGE);
            flag = false;
        }
        if (!persistenciaManager.isCedulaRegistered(cedula)) {
            view.InvalidateInputs("cedula");
            EstiloGral.ShowMessage("Esta cedula no esta registrada en la base de datos", EstiloGral.ERROR_MESSAGE);
            flag = false;
        }
        return flag;
    }

    public boolean isValidCedulaForBeca(String cedula){
        boolean flag = true;
        if (!isValidCedula(cedula)){
           return false;
        }

        Role rolUser = persistenciaManager.getRoleFromCedula(cedula);
        if(rolUser == Role.EXONERADO){
            view.InvalidateInputs("cedula");
            EstiloGral.ShowMessage("Este usuario ya es exonerado, no se puede becar", EstiloGral.ERROR_MESSAGE);
            flag = false;
        } else if(rolUser == Role.BECARIO){
            view.InvalidateInputs("cedula");
            EstiloGral.ShowMessage("Este usuario ya esta becado", EstiloGral.ERROR_MESSAGE);
            flag = false;
        } else if(rolUser != Role.ESTUDIANTE){
            view.InvalidateInputs("cedula");
            EstiloGral.ShowMessage("Este usuario no es elegible para ser becado", EstiloGral.ERROR_MESSAGE);
            return false;
        }
        
        return flag;
    }

    public boolean isValidCedulaForExonerado(String cedula){
        boolean flag = true;
        if (!isValidCedula(cedula)){
            return false;
        }

        Role rolUser = persistenciaManager.getRoleFromCedula(cedula);
        if(rolUser == Role.EXONERADO){
            view.InvalidateInputs("cedula");
            EstiloGral.ShowMessage("Este usuario ya esta exonerado", EstiloGral.ERROR_MESSAGE);
            flag = false;
        } else if(rolUser != Role.ESTUDIANTE && rolUser != Role.BECARIO){
            view.InvalidateInputs("cedula");
            EstiloGral.ShowMessage("Este usuario no es elegible para ser exonerado", EstiloGral.ERROR_MESSAGE);
            return false;
        }
        
        return flag;
    }

    public boolean isValidDescuento(String descuentoStr) {
        boolean flag = true;
        if(descuentoStr == null || descuentoStr.trim().isEmpty()){
            view.InvalidateInputs("descuento");
            com.comedor.view.EstiloGral.ShowMessage("Ingrese un descuento valido", EstiloGral.ERROR_MESSAGE);
            flag = false;
        }

        if(!ModelUtils.esDecimalValido(descuentoStr)){
            view.InvalidateInputs("descuento");
            com.comedor.view.EstiloGral.ShowMessage("Ingrese numeros validos", EstiloGral.ERROR_MESSAGE);
            flag = false;
        }
        Double descuento = Double.parseDouble(descuentoStr);
        Double DescuentoEstudiante = persistenciaManager.getPorcentajeFromRole("ESTUDIANTE");
        if(DescuentoEstudiante == null){
            view.InvalidateInputs("descuento");
            com.comedor.view.EstiloGral.ShowMessage("Error al obtener el descuento de Usuario", EstiloGral.ERROR_MESSAGE);
            flag = false;
        } else if(descuento >= DescuentoEstudiante){
            view.InvalidateInputs("descuento");
            com.comedor.view.EstiloGral.ShowMessage("Ingrese un descuento menor que el de Usuario", EstiloGral.ERROR_MESSAGE);
            flag = false;
        }
        return flag;
    }

    private void salirDeVentana() {
        delegate.onAdminPanelRequested();
}
}
