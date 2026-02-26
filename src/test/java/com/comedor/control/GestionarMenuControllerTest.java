package com.comedor.control;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.comedor.model.PersistenciaManager;
import com.comedor.view.GestionarMenuView;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class GestionarMenuControllerTest {

    private GestionarMenuController controller;
    //Mocks necesarios para el constructor de GestionarMenuController
    @Mock private GestionarMenuView viewMock;
    @Mock private PersistenciaManager persistenceMock;
    @Mock private NavigationDelegate delegateMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        viewMock = mock(GestionarMenuView.class, RETURNS_DEEP_STUBS);
        controller = new GestionarMenuController(viewMock, persistenceMock, delegateMock);
    }

    @Test
    void allFieldsValid_returnsTrue() {
        assertTrue(controller.isValidInputs("2026-01-01", "Plato", "Ingred", "10"));
    }

    @Test
    void fechaEmpty_invalidatesFecha() {
        assertFalse(controller.isValidInputs("", "P", "I", "5"));
    }

    @Test
    void platoEmpty_invalidatesPlato() {
        assertFalse(controller.isValidInputs("2026-01-01", "", "I", "5"));
    }

    @Test
    void ingredientesEmpty_invalidatesIngredientes() {
        assertFalse(controller.isValidInputs("2026-01-01", "P", "", "5"));
    }

    @Test
    void cuposEmpty_invalidatesCupos() {
        assertFalse(controller.isValidInputs("2026-01-01", "P", "I", ""));
    }

    @Test
    void cuposNotInteger_invalidatesCupos() {
        assertFalse(controller.isValidInputs("2026-01-01", "P", "I", "abc"));
    }

    @Test
    void multipleFieldsInvalid_invalidatesAll() {
        boolean result = controller.isValidInputs("", "", "", "x");
        assertFalse(result);
    }
}

