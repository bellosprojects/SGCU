package com.comedor.control;

import com.comedor.model.PersistenciaManager;
import com.comedor.model.User;
import com.comedor.view.EstiloGral;
import com.comedor.view.RegisterView;

public class RegisterController {
    private final NavigationDelegate delegate;
    private final RegisterView registerView;
    private final PersistenciaManager persistenciaManager;

    public RegisterController(RegisterView registerView, PersistenciaManager persistenciaManager, NavigationDelegate delegate) {
        this.registerView = registerView;
        this.persistenciaManager = persistenciaManager;
        this.delegate = delegate;
        setupListeners();
    }

    private void setupListeners() {
        registerView.find("registerBtn").onClick(b -> {
            handleRegister();
        });
        registerView.find("nextBtn").onClick(b -> {
            if(registerView.getCedula().isEmpty()){
                registerView.InvalidateInputs("cedula");
                EstiloGral.ShowMessage("Ingrese su cedula para continuar", EstiloGral.ERROR_MESSAGE);
            } else if(!registerView.getUsername().trim().isEmpty()){
                EstiloGral.ShowMessage("Complete el siguiente formulario", EstiloGral.INFO_MESSAGE);
                registerView.nextStep(); 
            } else {
                registerView.InvalidateInputs("cedula");
                EstiloGral.ShowMessage("Esta cedula no esta registrada en la base de datos", EstiloGral.ERROR_MESSAGE);
            }
        });

        registerView.find("backBtn").onClick(b -> {
            goToLoginView();
        });
        registerView.find("findUser").onClick(b -> {

            String cedula = registerView.getCedula();
            User user = persistenciaManager.getUserFromCedulaInDataBase(cedula);

            if(user != null){
                registerView.setData(user);
            } else {
                registerView.InvalidateInputs("cedula");
                EstiloGral.ShowMessage("Esta cedula no esta registrada en la base de datos", EstiloGral.ERROR_MESSAGE);
            }

        });
    }

    private void goToLoginView() {
        delegate.onRegisterSuccess();
    }

    public boolean isEmailValid(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        String emailLower = email.toLowerCase();
        return (emailLower.endsWith("@gmail.com") && emailLower.length() > 10)
                || (emailLower.endsWith("@hotmail.com") && emailLower.length() > 12);
    }

    

    public boolean isValidRegister(String password, String confirmPassword, String email) {
        boolean flag = true;
        if (password == null || password.isEmpty()) {
            registerView.InvalidateInputs("password");
            flag = false;
        }
        if (confirmPassword == null || confirmPassword.isEmpty()) {

            registerView.InvalidateInputs("confirmPassword");
            flag = false;
        }

        if (password != null && password.length() < 8) {
            registerView.InvalidateInputs("password", "confirmPassword");
            EstiloGral.ShowMessage("La contraseña debe tener al menos 8 caracteres", EstiloGral.ERROR_MESSAGE);
            flag = false;
        }

        if (password != null && !password.equals(confirmPassword)) {
            registerView.InvalidateInputs("confirmPassword");
            EstiloGral.ShowMessage("Las contraseñas no coinciden", EstiloGral.ERROR_MESSAGE);
            flag = false;
        }
        if (!isEmailValid(email)) {
            registerView.InvalidateInputs("email");
            EstiloGral.ShowMessage("Ingrese un correo electrónico válido", EstiloGral.ERROR_MESSAGE);
            flag = false;
        }

        return flag;
    }

    private void handleRegister() {
        String fullname = registerView.getUsername();
        String cedula = registerView.getCedula().trim();
        String password = registerView.getPassword();
        String confirmPassword = registerView.getConfirmPassword();
        String email = registerView.getEmail();
        String facultadSeleccionada = registerView.getFacultad();
        String tipoUsuario = persistenciaManager.getUserFromCedulaInDataBase(cedula).getRole();

        if (isValidRegister(password, confirmPassword, email)) {
            //EstiloGral.ShowMessage("Complete todos los campos", EstiloGral.ERROR_MESSAGE);
            boolean isCedulaRegistered = persistenciaManager.isCedulaRegistered(cedula);
            if (isCedulaRegistered) {
                registerView.InvalidateInputs("cedula");
                EstiloGral.ShowMessage("La cédula ingresada ya está registrada en el sistema. Debes iniciar sesión", EstiloGral.ERROR_MESSAGE);
                return;
            }
            User user = new User(fullname, cedula, password, email, facultadSeleccionada, 0.0, tipoUsuario);
            persistenciaManager.guardarUsuario(user);
            EstiloGral.ShowMessage("Usuario Registrado con exito", EstiloGral.SUCCESS_MESSAGE);
            goToLoginView();
        } else {
            EstiloGral.ShowMessage("Complete todos los campos correctamente", EstiloGral.ERROR_MESSAGE);
        }
        
    }

}