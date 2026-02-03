package com.comedor;

import javax.swing.SwingUtilities;

import com.comedor.control.LoginController;
import com.comedor.view.LoginView;

public class Main {
    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(() -> {
            
            LoginView loginView = new LoginView();

            new LoginController(loginView);

            loginView.setVisible(true);
        });

    }
}
