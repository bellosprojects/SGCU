package com.comedor.control;

import com.comedor.view.LoginView;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginController implements ActionListener{

    public LoginView loginView;

    public LoginController(LoginView loginView) {
        this.loginView = loginView;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
    }

}