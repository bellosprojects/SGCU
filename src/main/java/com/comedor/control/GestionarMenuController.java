package com.comedor.control;

import com.comedor.model.Menu;
import com.comedor.model.Menu.TipoMenu;
import com.comedor.model.PersistenciaManager;
import com.comedor.utils.ModelUtils;
import com.comedor.view.EstiloGral;
import com.comedor.view.GestionarMenuView;

public class GestionarMenuController {
    private final NavigationDelegate delegate;
    private final GestionarMenuView gestionarMenuView;
    private final PersistenciaManager persistenciaManager;

    public GestionarMenuController(GestionarMenuView gestionarMenuView, PersistenciaManager persistenciaManager, NavigationDelegate delegate) {
        this.gestionarMenuView = gestionarMenuView;
        this.persistenciaManager = persistenciaManager;
        this.delegate = delegate;
        setupListeners();
    }

    private void setupListeners(){
        gestionarMenuView.find("backBtn").onClick(b -> {
            salirDeVentana();
        });

        gestionarMenuView.find("clear").onClick(b -> {
            gestionarMenuView.limpiarFormulario();
        });

        gestionarMenuView.find("save").onClick(b -> {
            guardarDatosDelMenu();
        });
    }

    private void guardarDatosDelMenu() {

        String fecha = gestionarMenuView.getFecha();
        String plato = gestionarMenuView.getPlato();
        String ingredientes = gestionarMenuView.getIngredientes();
        String tipo = gestionarMenuView.getTipo();
        String cupos= gestionarMenuView.getCupos();
        TipoMenu tipoMenu = tipo.equals("Desayuno") ? TipoMenu.DESAYUNO : TipoMenu.ALMUERZO;

        if (!isValidInputs(fecha, plato, ingredientes, cupos)) {
            EstiloGral.ShowMessage("Datos invalidos", EstiloGral.ERROR_MESSAGE); 
            return;
        }

        persistenciaManager.guardarMenu(new Menu(plato, ingredientes, tipoMenu, fecha, cupos), false);
        EstiloGral.ShowMessage("Menú guardado exitosamenmte", EstiloGral.SUCCESS_MESSAGE);  
        salirDeVentana();
    }

    public boolean isValidInputs(String fecha, String plato, String ingredientes, String cupos) {
        boolean flag = true;
        if (fecha.isEmpty()) {
            gestionarMenuView.InvalidateInputs("fecha");
            flag = false;
        }
        if (plato.isEmpty()) {
            gestionarMenuView.InvalidateInputs("plato");
            flag = false;
        }
        if (ingredientes.isEmpty()) {
            gestionarMenuView.InvalidateInputs("ingredientes");
            flag = false;
        }
        if (cupos.isEmpty() || !ModelUtils.esEnteroValido(cupos)) {
            gestionarMenuView.InvalidateInputs("cupos");
            flag = false;
        }
        return flag;
    }

    private void salirDeVentana() {
        delegate.onAdminPanelRequested();
    }
}