package com.comedor.control;

import com.comedor.view.LoginView;
import com.comedor.view.RegisterView;
import com.comedor.view.AdminDashboard;
import com.comedor.view.UserMenuView;
import com.comedor.model.PersistenciaManager;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginController implements ActionListener{

    private LoginView loginView;
    private PersistenciaManager persistenciaManager;

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
                    goToUserDashboardView(cedula);
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

    private void goToUserDashboardView(String cedula){
        loginView.setVisible(false);
        loginView.dispose();
        UserMenuView UserDashboardView = new UserMenuView();
        new UserMenuController(persistenciaManager, cedula, UserDashboardView);
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