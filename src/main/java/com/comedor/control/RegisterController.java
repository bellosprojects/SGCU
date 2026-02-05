package com.comedor.control;

import com.comedor.view.LoginView;
import com.comedor.view.RegisterView;
import com.comedor.model.PersistenciaManager;
import com.comedor.model.User;

import java.awt.event.ActionListener;

import javax.swing.JFileChooser;

import java.awt.event.ActionEvent;

public class RegisterController implements ActionListener{

    public RegisterView registerView;
    public PersistenciaManager persistenciaManager;

    public RegisterController(RegisterView registerView, PersistenciaManager persistenciaManager) {
        this.registerView = registerView;
        this.persistenciaManager = persistenciaManager;
        setupListeners();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == registerView.getRegisterButton()){
            String username = registerView.getUsernameInput().getText();
            String cedula = registerView.getCedulaInput().getText();
            char[] rawPassword = registerView.getPassInput().getPassword();
            char[] rawConfirmPassword = registerView.getConfirmPassInput().getPassword();
            String password = new String(rawPassword);
            String confirmPassword = new String(rawConfirmPassword);
            String email = registerView.getEmailInput().getText();
            String facultadSeleccionada = registerView.getFacultadSelect();
            String imagePath = registerView.getProfileImagePath();

            if(!isValidRegister(username, cedula, password, confirmPassword, email)){
                return;
            }
            
            User user = new User(username, cedula, confirmPassword, email,facultadSeleccionada,imagePath);
            persistenciaManager.guardarUsuario(user);
            
            goToLoginView();

        } else if(e.getSource() == registerView.getBackButton()){
            
            goToLoginView();

        } else if(e.getSource() == registerView.getProfileImagFileChooser()){
            // Seleccionar imagen y guardo en
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                String selectedFile = fileChooser.getSelectedFile().getAbsolutePath();
                registerView.setImagePath(selectedFile);
            }

        } 
        
    }

    private void setupListeners(){
        registerView.getRegisterButton().addActionListener(this);
        registerView.getCedulaInput().addActionListener(this);
        registerView.getPassInput().addActionListener(this);
        registerView.getConfirmPassInput().addActionListener(this);
        registerView.getBackButton().addActionListener(this); 
        registerView.getUsernameInput().addActionListener(this);
        registerView.getEmailInput().addActionListener(this);
        registerView.getProfileImagFileChooser().addActionListener(this);
    }

    private void goToLoginView(){
        registerView.setVisible(false);
        registerView.dispose();
        LoginView loginView = new LoginView();
        new LoginController(loginView, persistenciaManager);
        loginView.setVisible(true);
    }

    public boolean isEmailValid(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        String emailLower = email.toLowerCase();
        return emailLower.endsWith("@gmail.com") || emailLower.endsWith("@hotmail.com");
    }

    public boolean isAllNumbers(String str) {
        return str != null && str.matches("\\d+");
    }

    public boolean isValidRegister(String username, String cedula, String password, String confirmPassword, String email) {
        boolean flag = true;
        if(username == null || username.isEmpty()){
            registerView.InvalidateInputs(registerView.getUsernameInput());
            flag = false;
        }
        if( persistenciaManager.isUsernameRegistered(username) ){
            registerView.InvalidateInputs(registerView.getUsernameInput());
            flag = false;
        }
        
        if (cedula == null || cedula.isEmpty()) {
            registerView.InvalidateInputs(registerView.getCedulaInput());
            flag = false;
        }
        if( persistenciaManager.isCedulaRegistered(cedula) ){
            registerView.InvalidateInputs(registerView.getCedulaInput());
            flag = false;
        }

        if (password == null || password.isEmpty()) {
            registerView.InvalidateInputs(registerView.getPassInput());
            flag = false;
        }
        if (confirmPassword == null || confirmPassword.isEmpty()) {
            
            registerView.InvalidateInputs(registerView.getConfirmPassInput());
            flag = false;
        }

        if (!password.equals(confirmPassword) || password.length() < 8) {
            registerView.InvalidateInputs(registerView.getPassInput());
            flag = false;
        }
        if (!isEmailValid(email)) {
            registerView.InvalidateInputs(registerView.getEmailInput());
            flag = false;
        }

        if(!isAllNumbers(cedula)){
            registerView.InvalidateInputs(registerView.getCedulaInput());
            flag = false;
        }
        return flag;
    }

    
}
