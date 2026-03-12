package com.comedor.control;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.comedor.model.PersistenciaManager;
import com.comedor.view.UserMenuView;

public class SaldoPanaTest {

    @Mock private PersistenciaManager persistenciaMock;
    @Mock
    private UserMenuView menuViewMock;
    @Mock private NavigationDelegate delegateMock;

    private UserMenuController controller;
    private final String miCedula = "25000000";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        aura.core.AuraBox backBtnMock = mock(aura.core.AuraBox.class);
        aura.core.AuraBox rechargeBtnMock = mock(aura.core.AuraBox.class);
        when(menuViewMock.find("backBtn")).thenReturn(backBtnMock);
        when(menuViewMock.find("rechargeBtn")).thenReturn(rechargeBtnMock);
        // metodos que son llamados en el test, pero no necesarios para este caso
        doNothing().when(menuViewMock).hideSaldoPana();
        doNothing().when(menuViewMock).updateSaldo(anyDouble());
        doNothing().when(menuViewMock).InvalidateInputs(anyString());

        controller = new UserMenuController(persistenciaMock, miCedula, menuViewMock, delegateMock);
    }

    // --- PRUEBAS PARA isValidMontoForSaldoPana() ---

    @Test
    public void testIsValidMonto_MontoValido() {
        when(persistenciaMock.getSaldoFromCedula(miCedula)).thenReturn(100.0);
        
        boolean resultado = controller.isValidMontoForSaldoPana("50.0");
        
        assertTrue(resultado); // Debería ser true para un monto válido y con saldo suficiente
    }

    @Test
    public void testIsValidMonto_SaldoInsuficiente() {
        when(persistenciaMock.getSaldoFromCedula(miCedula)).thenReturn(100.0); // Saldo actual 100
        
        boolean resultado = controller.isValidMontoForSaldoPana("150.0"); // Intenta pasar 150
        
        assertFalse(resultado); // Debería ser false porque el monto supera el saldo actual
    }

    @Test
    public void testIsValidMonto_MontoNegativoOCero() {
        boolean resultadoCero = controller.isValidMontoForSaldoPana("0.0");
        boolean resultadoNegativo = controller.isValidMontoForSaldoPana("-10.0");
        
        assertFalse(resultadoCero); // Debería ser false para monto 0
        assertFalse(resultadoNegativo); // Debería ser false para monto negativo
    }

    // --- PRUEBAS PARA recargarSaldoPana() ---

    @Test
    public void testRecargarSaldoPana_Exito() {
        String cedulaPana = "27000000";
        String monto = "40.0";
        String clave = "1234";

        when(menuViewMock.getMontoForSaldoPana()).thenReturn(monto);
        when(menuViewMock.getConfirmacionSaldoPana()).thenReturn(clave);
        
        when(persistenciaMock.autenticar(miCedula, clave)).thenReturn(true);
        when(persistenciaMock.getSaldoFromCedula(miCedula)).thenReturn(100.0); // Tiene saldo
        
        double resultado = controller.recargarSaldoPana(cedulaPana);

        assertEquals(40.0, resultado); // Debería retornar el monto transferido
        
        verify(persistenciaMock).sumarSaldo(cedulaPana, 40.0);
        verify(persistenciaMock).sumarSaldo(miCedula, -40.0);
    }

    @Test
    public void testRecargarSaldoPana_FallaAutenticacion() {
        String cedulaPana = "27000000";
        String claveMala = "claveEquivocada";

        when(menuViewMock.getConfirmacionSaldoPana()).thenReturn(claveMala);
        when(persistenciaMock.autenticar(miCedula, claveMala)).thenReturn(false);
        
        double resultado = controller.recargarSaldoPana(cedulaPana);

        assertEquals(-1.0, resultado); // Debería retornar -1 si la clave es incorrecta
        verify(persistenciaMock, never()).sumarSaldo(anyString(), anyDouble());
    }
}