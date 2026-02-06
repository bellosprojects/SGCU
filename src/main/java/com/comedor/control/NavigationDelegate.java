package com.comedor.control;

public interface NavigationDelegate {
    void onRegisterSuccess();
    void onBackToLoginRequested(); 
    void onRegisterRequested(); 
    void onLoginSuccess(String cedula);
}
