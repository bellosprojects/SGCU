package com.comedor.control;

import com.comedor.view.UserMenuView;
import com.comedor.model.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserMenuController implements ActionListener {
    private NavigationDelegate delegate;
    private PersistenciaManager persistenciaManager;
    private UserMenuView menuView;
    private String cedula;
    private String tipoUsuario;

    public UserMenuController(PersistenciaManager persistenciaManager, String cedula, UserMenuView menuView,
            NavigationDelegate delegate) {
        this.delegate = delegate;
        this.persistenciaManager = persistenciaManager;
        this.menuView = menuView;
        this.cedula = cedula;
        this.menuView.getSalirButton().addActionListener(this);
        sendUser();
        sendMenu();

    }

    public void sendMenu() {
        Menu Desayuno = persistenciaManager.getMenu(true);
        Menu Almuerzo = persistenciaManager.getMenu(false);
        if (Desayuno.isValidMenu()) {
            menuView.setDesayuno(Desayuno);
        }
        if (Almuerzo.isValidMenu()) {
            menuView.setAlmuerzo(Almuerzo);
        }
        Double precioFinal = (persistenciaManager.getCCB() * persistenciaManager.getPorcentajeFromRole(tipoUsuario))
                / 100;
        menuView.setPrecio(precioFinal);
    }

    public void sendUser() {
        User user = persistenciaManager.getUserFromCedula(this.cedula);
        this.tipoUsuario = user.getRole();
        menuView.setUser(user);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menuView.getSalirButton()) {
            delegate.onBackToLoginRequested();
        }
    }
}