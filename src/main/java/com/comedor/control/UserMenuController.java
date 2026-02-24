package com.comedor.control;

import com.comedor.model.Menu;
import com.comedor.model.Menu.TipoMenu;
import com.comedor.model.PersistenciaManager;
import com.comedor.model.User;
import com.comedor.view.UserMenuView;

public class UserMenuController {
    private final NavigationDelegate delegate;
    private final PersistenciaManager persistenciaManager;
    private final UserMenuView menuView;
    private final String cedula;
    private String tipoUsuario;

    public UserMenuController(PersistenciaManager persistenciaManager, String cedula, UserMenuView menuView, NavigationDelegate delegate) {
        this.delegate = delegate;
        this.persistenciaManager = persistenciaManager;
        this.menuView = menuView;
        this.cedula = cedula;
        setup();

    }

    private void sendMenu() {
        Menu Desayuno = persistenciaManager.getMenu(TipoMenu.DESAYUNO);
        Menu Almuerzo = persistenciaManager.getMenu(TipoMenu.ALMUERZO);
        if (Desayuno != null && Desayuno.isValidMenu()) {
            menuView.setDesayuno(Desayuno);
        }
        if (Almuerzo != null && Almuerzo.isValidMenu()) {
            menuView.setAlmuerzo(Almuerzo);
        }
        Double precioFinal = (persistenciaManager.getCCB() * persistenciaManager.getPorcentajeFromRole(tipoUsuario)) / 100;
        menuView.setPrecio(precioFinal);
    }

    private void sendUser() {
        User user = new User(
            persistenciaManager.getNameFromCedula(cedula),
            cedula,
            "",
            "",
            "",
            persistenciaManager.getSaldoFromCedula(cedula),
            persistenciaManager.getRoleFromCedula(cedula)
        );

        this.tipoUsuario = user.getRole();
        menuView.setUser(user);
    }

    private void setup() {
        
        menuView.find("backBtn").onClick(b -> 
            delegate.onBackToLoginRequested()
        );

        
        sendUser();
        sendMenu();
    }
}