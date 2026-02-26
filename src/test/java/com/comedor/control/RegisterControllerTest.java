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
    void passwordNull_invalidatesPassword() {
        boolean result = controller.isValidRegister(null, "abc", "user@gmail.com");
        assertFalse(result);
    }

    @Test
    void confirmPasswordNull_invalidatesConfirm() {
        boolean result = controller.isValidRegister("password123", null, "user@gmail.com");
        assertFalse(result);
    }

    @Test
    void passwordTooShort_invalidatesBothPasswords() {
        boolean result = controller.isValidRegister("short", "short", "user@gmail.com");
        assertFalse(result);
    }

    @Test
    void passwordsDoNotMatch_invalidatesConfirm() {
        boolean result = controller.isValidRegister("password123", "password321", "user@gmail.com");
        assertFalse(result);
    }

    @Test
    void invalidEmail_invalidatesEmail() {
        boolean result = controller.isValidRegister("password123", "password123", "bad_email");
        assertFalse(result);
    }

    @Test
    void allValid_returnsTrue_andDoesNotInvalidate() {
        boolean result = controller.isValidRegister("strongPass1", "strongPass1", "usuario@gmail.com");
        assertTrue(result);
    }
}