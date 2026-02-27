package com.comedor.control;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.comedor.model.PersistenciaManager;
import com.comedor.view.RegisterView;

public class RegisterControllerTest {

    private RegisterController controller;

    @Mock private RegisterView viewMock;
    @Mock private PersistenciaManager persistenceMock;
    @Mock private NavigationDelegate delegateMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        viewMock = mock(RegisterView.class, RETURNS_DEEP_STUBS);
        controller = new RegisterController(viewMock, persistenceMock, delegateMock);
    }

    @Test
    void isValidRegister_EmailVacio_DevuelveFalse() {
        assertFalse(controller.isValidRegister("12345678","12345678", ""));
    }

    @Test
    void isValidRegister_EmailDominioValido_DevuelveTrue() {
        assertTrue(controller.isValidRegister("12345678","12345678", "a@gmail.com"));
    }

    @Test
    void isValidRegister_EmailDominioInvalido_DevuelveFalse() {
        assertFalse(controller.isValidRegister("12345678","12345678", "agmail.com"));
    }

    @Test
    void isValidRegister_EmailNull_DevuelveFalse() {
        assertFalse(controller.isValidRegister("12345678","12345678", null));
    }

    @Test
    void isValidRegister_CamposValidos_DevuelveTrue() {
        assertTrue(controller.isValidRegister("contraseña","contraseña", "correo@gmail.com"));
    }

    @Test
    void isValidRegister_CamposInvalidos_DevuelveFalse() {
        assertFalse(controller.isValidRegister("123","12345678", ""));
    }

    @Test
    void isValidRegister_PasswordsDiferentes_DevuelveFalse() {
        assertFalse(controller.isValidRegister("contraseña2","contraseña", "correo@gmail.com"));
    }

    @Test
    void isValidRegister_PasswordsIguales_DevuelveTrue() {
        assertTrue(controller.isValidRegister("contraseña","contraseña","jacinto@gmail.com" ));
    }

    @Test
    void isValidRegister_PasswordsMenor8_DevuelveFalse() {
        assertFalse(controller.isValidRegister("contra","contra","jacinto@gmal.com"));
    }

}