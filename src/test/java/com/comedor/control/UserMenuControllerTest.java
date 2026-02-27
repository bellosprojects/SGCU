package com.comedor.control;

import static org.junit.jupiter.api.Assertions.*;
// Importante para los mocks
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.comedor.model.PersistenciaManager;
import com.comedor.view.UserMenuView;

public class UserMenuControllerTest {
    //Pruebas Unitarias para boolean isValidInputs(String montoStr, String numeroReferencia)
    private UserMenuController controller; // clase que queremos probar
    @Mock private UserMenuView menuViewMock;
    @Mock private PersistenciaManager persistenciaMock;
    @Mock private NavigationDelegate delegateMock;

    @BeforeEach //Arrange
    void setUp() {
        MockitoAnnotations.openMocks(this);
        menuViewMock = mock(UserMenuView.class, RETURNS_DEEP_STUBS);

        controller = new UserMenuController(persistenciaMock, "123456", menuViewMock, delegateMock);
    }

    @Test
    void testMont_Vacio_RetornaFalse() {
        String montoVacio = "";
        String refValida = "123456789012";

        assertFalse(controller.isValidInputs(montoVacio, refValida));
    }

    @Test
    void testMonto_NoNumerico_RetornaFalse() {
        // GIVEN
        String montoInvalido = "100.00abc";
        String refValida = "123456789012";

        // WHEN
        boolean resultado = controller.isValidInputs(montoInvalido, refValida);

        // THEN
        assertFalse(resultado);
    }

    @Test
    void testNumeroReferencia_Vacio_RetornaFalse() {
        String montoValido = "250.4";
        String refVacio = "";

        assertFalse(controller.isValidInputs(montoValido, refVacio));
    }

    @Test
    void testNumeroReferencia_LongitudMenor12_RetornaFalse() {
        String montoValido = "100.39";
        String refCorta = "12";

        assertFalse(controller.isValidInputs(montoValido, refCorta));
    }

    @Test
    void testNumeroReferencia_LongitudMayor12_RetornaFalse() {
        String montoValido = "100.39";
        String refCorta = "121212121212121212";

        assertFalse(controller.isValidInputs(montoValido, refCorta));
    }

    @Test
    void test_InputsValidos_RetornaTrue() {
        String montoValido = "123.45";
        String refValida = "000111222333";

        assertTrue(controller.isValidInputs(montoValido, refValida));
    }

    // --- pruebas unitarias de void recargarSaldo() ----------------------

    @Test
    void testRecargarSaldo_InvalidInputs_ReturnaMenos1() {
        when(menuViewMock.getMonto()).thenReturn("");
        when(menuViewMock.getNumeroReferencia()).thenReturn("123");

        double result = controller.recargarSaldo();

        assertEquals(-1, result);
    }

    @Test
    void testRecargarSaldo_MontoMenorIgualCero_ReturnaMenus1() {
        when(menuViewMock.getMonto()).thenReturn("0");
        when(menuViewMock.getNumeroReferencia()).thenReturn("123456789012");

        double result = controller.recargarSaldo();

        assertEquals(-1, result);
    }

    @Test
    void testRecargarSaldo_ValidInput_GuardaYActualizaSaldo(){
        when(menuViewMock.getMonto()).thenReturn("150");
        when(menuViewMock.getNumeroReferencia()).thenReturn("123456789012");
        when(persistenciaMock.getSaldoFromCedula("123456")).thenReturn(650.0);

        double result = controller.recargarSaldo();

        assertEquals(150.0, result, 0.0001);
    }
}

