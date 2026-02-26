package com.comedor.control;

import com.comedor.model.Menu;
import com.comedor.model.PersistenciaManager;
import com.comedor.model.Menu.TipoMenu;
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

    }

    private void guardarDatosDelMenu() {

        String fecha = gestionarMenuView.getFechaText();
        String plato = gestionarMenuView.getPlatoText();
        String ingredientes = gestionarMenuView.getingredientesText();
        String tipo = gestionarMenuView.getTipo();
        String cupos= gestionarMenuView.getCupos();
        TipoMenu tipoMenu = tipo.equals("Desayuno") ? TipoMenu.DESAYUNO : TipoMenu.ALMUERZO;

        if (!isValidInputs(fecha, plato, ingredientes)) {
            EstiloGral.ShowMessage("Datos invalidos", EstiloGral.ERROR_MESSAGE); 
            return;
        }

        persistenciaManager.guardarMenu(new Menu(plato, ingredientes, tipoMenu, fecha, cupos));
        EstiloGral.ShowMessage("Menú guardado exitosamenmte", EstiloGral.SUCCESS_MESSAGE);  
        salirDeVentana();
    }

    private boolean isValidInputs(String fecha, String plato, String ingredientes) {
        boolean flag = true;
        if (fecha.isEmpty()) {
            gestionarMenuView.InvalidateInputs(gestionarMenuView.getFechaComponent());
            flag = false;
        }
        if (plato.isEmpty()) {
            gestionarMenuView.InvalidateInputs(gestionarMenuView.getPlatoComponent());
            flag = false;
        }
        if (ingredientes.isEmpty()) {
            gestionarMenuView.InvalidateInputs(gestionarMenuView.getIngredientsComponents());
            flag = false;
        }
        return flag;
    }

    private void salirDeVentana() {
        delegate.onAdminPanelRequested();
    }
}