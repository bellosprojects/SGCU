package com.comedor.control;

import com.comedor.view.LoginView;
import com.comedor.view.RegisterView;
import com.comedor.view.AdminDashboard;
import com.comedor.view.UserDashboard;
import com.comedor.model.PersistenciaManager;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginController implements ActionListener{

    public LoginView loginView;
    public PersistenciaManager persistenciaManager;

    public LoginController(LoginView loginView, PersistenciaManager persistenciaManager) {
        this.loginView = loginView;
        this.persistenciaManager = persistenciaManager;
        setupListeners();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == loginView.getLoginButton()){
            String cedula = loginView.getCedulaInput().getText();
            char[] rawPassword = loginView.getPassInput().getPassword();
            String password = new String(rawPassword);
            if( password == persistenciaManager.getPasswordFromCedula(cedula) ){
                if("admin" == persistenciaManager.getRoleFromCedula(cedula)){
                    // Login exitoso dashboard admin
                    goToAdminDashboardView();
                    return;
                } else {
                    // Login exitoso dashboard user
                    goToUserDashboardView();
                    return;
                }
            } else {
                loginView.InvalidateInputs(loginView.getCedulaInput());
                loginView.InvalidateInputs(loginView.getPassInput());
            }

        } else if(e.getSource() == loginView.getRegisterButton()){
            
            goToRegisterView();
            
        } else if(e.getSource() == loginView.getForgotPassButton()){
            // Lógica para recuperar la contraseña
        } 
    }

    private void setupListeners(){
        loginView.getCedulaInput().addActionListener(this);
        loginView.getPassInput().addActionListener(this);
        loginView.getLoginButton().addActionListener(this);
        loginView.getRegisterButton().addActionListener(this);
        loginView.getForgotPassButton().addActionListener(this);
    }

    private void goToAdminDashboardView(){
        loginView.setVisible(false);
        loginView.dispose();
        AdminDashboard AdminDashboardView = new AdminDashboard();
        new AdminMenuController(AdminDashboardView, persistenciaManager);
        AdminDashboardView.setVisible(true);
    }

    private void goToUserDashboardView(){
        loginView.setVisible(false);
        loginView.dispose();
        UserDashboard UserDashboardView = new UserDashboard();
        new UserMenuController(UserDashboardView, persistenciaManager);
        UserDashboardView.setVisible(true);
    }

    private void goToRegisterView(){
        loginView.setVisible(false);
        loginView.dispose();
        RegisterView RegisterView = new RegisterView();
        new RegisterController(RegisterView, persistenciaManager);
        RegisterView.setVisible(true);
    }

    public static boolean isAllNumbers(String str) {
        return str != null && str.matches("\\d+");
    }

}