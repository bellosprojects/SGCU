package com.comedor.control;

import com.comedor.model.PersistenciaManager;
import com.comedor.utils.ModelUtils;
import com.comedor.view.EstiloGral;
import com.comedor.view.LoginView;

public class LoginController {

    private final NavigationDelegate delegate;
    private final LoginView loginView;
    private final PersistenciaManager persistenciaManager;

    public LoginController(LoginView loginView, PersistenciaManager persistenciaManager, NavigationDelegate delegate) {
        this.loginView = loginView;
        this.persistenciaManager = persistenciaManager;
        this.delegate = delegate;
        setupListeners();
    }

    private void setupListeners() {
        
        loginView.find("loginBtn").onClick(b -> 
            handleLogin()
        );

        loginView.find("registerBtn").onClick(b -> 
            goToRegisterView()
        );

    }

    private void handleLogin() {
        String cedula = loginView.getCedula();
        String password = loginView.getPassword();

        if (!isValidInputs(cedula, password)) {
            EstiloGral.ShowMessage("Por favor completa todos los campos", EstiloGral.ERROR_MESSAGE); 
            return;
        }

        boolean isAuthenticated = persistenciaManager.autenticar(cedula, password);

        if (isAuthenticated) {
            goToMenuView(cedula);
        } else {
            loginView.InvalidateInputs("cedula", "password");
            EstiloGral.ShowMessage("Cédula o contraseña incorrectas. Por favor, inténtalo de nuevo.", EstiloGral.ERROR_MESSAGE);
        }
    }

    private boolean isValidInputs(String cedula, String password) {
        boolean flag = true;
        if (cedula.isEmpty() || !ModelUtils.esEnteroValido(cedula)) {
            loginView.InvalidateInputs("cedula");
            flag = false;
        }

        if (password.isEmpty()) {
            loginView.InvalidateInputs("password");
            flag = false;

        }
        return flag;
    }

    private void goToRegisterView() {
        delegate.onRegisterRequested();
    }

    private void goToMenuView(String cedula) {
        delegate.onLoginSuccess(cedula);
    }

}