package com.comedor.control;

import javax.swing.JFrame;

import com.comedor.model.PersistenciaManager;
import com.comedor.view.AdminDashboard;
import com.comedor.view.LoginView;
import com.comedor.view.RegisterView;
import com.comedor.view.UserMenuView;

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
        // El coordinador se pasa a sí mismo como el que maneja la navegación
        new LoginController(view, model, this);
        view.setVisible(true);
    }

    public void showRegister() {
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
        new UserMenuController(model, cedula, UserDashboardView, this);
        UserDashboardView.setVisible(true);
    }

    private void showAdminDashboard() {
        if (mainFrame != null) {
            mainFrame.setVisible(false);
            mainFrame.dispose();
        }
        AdminDashboard AdminDashboardView = new AdminDashboard();
        new AdminMenuController(AdminDashboardView, model, this);
        AdminDashboardView.setVisible(true);
    }

    @Override
    public void onRegisterSuccess() {
        // Lógica para manejar el éxito del registro
        showLogin();
    }

    @Override
    public void onBackToLoginRequested() {
        showLogin();
        // Lógica para manejar la solicitud de volver al login
    }

    @Override
    public void onRegisterRequested() {
        showRegister();
        // Lógica para manejar la solicitud de registro
    }

    @Override
    public void onLoginSuccess(String cedula) {
        if ("admin" == model.getRoleFromCedula(cedula)) {
            showAdminDashboard();
        } else {
            showUserMenu(cedula);
        }
    }

}
