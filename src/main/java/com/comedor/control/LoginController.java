package com.comedor.control;

import com.comedor.view.LoginView;
import com.comedor.model.PersistenciaManager;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginController implements ActionListener {
    private NavigationDelegate delegate;
    private LoginView loginView;
    private PersistenciaManager persistenciaManager;

    public LoginController(LoginView loginView, PersistenciaManager persistenciaManager, NavigationDelegate delegate) {
        this.loginView = loginView;
        this.persistenciaManager = persistenciaManager;
        this.delegate = delegate;
        setupListeners();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginView.getLoginButton()) {
            handleLogin();
        } else if (e.getSource() == loginView.getRegisterButton()) {
            goToRegisterView();
        } else if (e.getSource() == loginView.getForgotPassButton()) {
            // Lógica para recuperar la contraseña
        }
    }

    private void setupListeners() {
        loginView.getCedulaInput().addActionListener(this);
        loginView.getPassInput().addActionListener(this);
        loginView.getLoginButton().addActionListener(this);
        loginView.getRegisterButton().addActionListener(this);
        loginView.getForgotPassButton().addActionListener(this);
    }

    private void handleLogin() {
        String cedula = loginView.getCedulaText();
        String password = loginView.getPassText();

        if (cedula.isEmpty() || !isAllNumbers(cedula)) {
            loginView.InvalidateInputs(loginView.getCedulaInput());
            return;
        }

        boolean isAuthenticated = persistenciaManager.autenticar(cedula, password);

        if (isAuthenticated) {
            goToMenuView(cedula);
        } else {
            loginView.InvalidateInputs(loginView.getCedulaInput());
            loginView.InvalidateInputs(loginView.getPassInput());
        }
    }

    private void goToRegisterView() {
        delegate.onRegisterRequested();
    }

    private void goToMenuView(String cedula) {
        delegate.onLoginSuccess(cedula);
    }

    public static boolean isAllNumbers(String str) {
        return str != null && str.matches("\\d+");
    }

}