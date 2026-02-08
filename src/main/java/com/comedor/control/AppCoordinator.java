package com.comedor.control;

import javax.swing.JFrame;

import com.comedor.model.PersistenciaManager;
import com.comedor.view.LoginView;
import com.comedor.view.RegisterView;
import com.comedor.view.UserMenuView;
import com.comedor.view.PanelAdminView;
import com.comedor.view.GestionarCCBView;
import com.comedor.view.GestionarMenuView;

public class AppCoordinator implements NavigationDelegate {
    private PersistenciaManager model;
    private JFrame mainFrame;

    public AppCoordinator() {
        this.model = new PersistenciaManager();
        this.mainFrame = null;
    }

    public void start() {
        showLogin();
    }

    private void showLogin() {
        if (mainFrame != null) {
            mainFrame.setVisible(false);
            mainFrame.dispose();
        }
        LoginView view = new LoginView();
        mainFrame = view;
        new LoginController(view, model, this);
        view.setVisible(true);
    }

    private void showRegister() {
        if (mainFrame != null) {
            mainFrame.setVisible(false);
            mainFrame.dispose();
        }
        RegisterView registerView = new RegisterView();
        mainFrame = registerView;
        new RegisterController(registerView, model, this);
        registerView.setVisible(true);
    }

    private void showUserMenu(String cedula) {
        if (mainFrame != null) {
            mainFrame.setVisible(false);
            mainFrame.dispose();
        }
        UserMenuView UserDashboardView = new UserMenuView();
        mainFrame = UserDashboardView;
        new UserMenuController(model, cedula, UserDashboardView, this);
        UserDashboardView.setVisible(true);
    }

    private void showAdminDashboard() {
        if (mainFrame != null) {
            mainFrame.setVisible(false);
            mainFrame.dispose();
        }
        PanelAdminView AdminDashboardView = new PanelAdminView();
        mainFrame = AdminDashboardView;
        new PanelAdminController(AdminDashboardView, model, this);
        AdminDashboardView.setVisible(true);
    }

    private void showGestionarMenuView() {
        if (mainFrame != null) {
            mainFrame.setVisible(false);
            mainFrame.dispose();
        }
        GestionarMenuView gestionarMenuView = new GestionarMenuView();
        mainFrame = gestionarMenuView;
        new GestionarMenuController(gestionarMenuView, model, this);
        gestionarMenuView.setVisible(true);
    }

    private void showCalcularCCBView() {
        if (mainFrame != null) {
            mainFrame.setVisible(false);
            mainFrame.dispose();
        }
        GestionarCCBView calcularCCBView = new GestionarCCBView();
        mainFrame = calcularCCBView;
        new CCBCalculoController(calcularCCBView, model, this);
        calcularCCBView.setVisible(true);
    }

    @Override
    public void onRegisterSuccess() {
        showLogin();
    }

    @Override
    public void onBackToLoginRequested() {
        showLogin();
    }

    @Override
    public void onRegisterRequested() {
        showRegister();
    }

    @Override
    public void onLoginSuccess(String cedula) {
        if (model.getRoleFromCedula(cedula).equals("Admin")) {
            showAdminDashboard();
        } else {
            showUserMenu(cedula);
        }
    }

    @Override
    public void onAdminPanelRequested() {
        showAdminDashboard();
    }

    @Override
    public void onGestionarMenuRequested() {
        showGestionarMenuView();
    }

    @Override
    public void onCalcularCCBRequested() {
        showCalcularCCBView();
    }

}
