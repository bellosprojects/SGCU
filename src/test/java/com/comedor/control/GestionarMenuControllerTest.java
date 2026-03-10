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
    void isValidInputs_TodosCamposValidos_DevuelveTrue(){
        assertTrue(controller.isValidInputs("4 enero 2024", "Pasticho", "Queso y carne", "39"));
    }

    @Test
    void isValidInputs_MultiplesCamposInvalidos_DevuelveFalse() {
        assertFalse(controller.isValidInputs("3 enero 2026", null , "aguacate", "2b0"));
    }

    @Test
    void isValidInputs_CamposVacios_DevuelveFalse() {
        assertFalse(controller.isValidInputs("","Pan","harina", "15"));
    }

    @Test
    void isValidInputs_CamposNull_DevuelveFalse() {
        assertFalse(controller.isValidInputs("11 octubre 2026",null,"harina", "15"));
    }

    @Test
    void isValidInputs_CuposSoloNumeros_DevuelveTrue() {
        assertTrue(controller.isValidInputs("10 febrero 2026","Compota","frutas", "20"));
    }

    @Test
    void isValidInputs_CuposVacio_DevuelveFalse() {
        assertFalse(controller.isValidInputs("10 febrero 2026","Compota","frutas", ""));
    }

    @Test
    void isValidInputs_CuposNull_DevuelveFalse() {
        assertFalse(controller.isValidInputs("10 febrero 2026","Compota","frutas", null));
    }

    @Test
    void isValidInputs_CuposCaracter_DevuelveFalse(){
        assertFalse(controller.isValidInputs("10 febrero 2026","Compota","frutas", "10a"));
    }

    @Test
    void isValidInputs_CuposNegativos_DevuelveFalse() {
        assertFalse(controller.isValidInputs("10 febrero 2026","Compota","frutas", "-5"));
    }
}

