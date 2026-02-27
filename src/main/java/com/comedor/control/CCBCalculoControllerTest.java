 package com.comedor.control;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class CCBCalculoControllerTest {

    private CCBCalculoController controller;

    
    void setUp() {
        
        controller = new CCBCalculoController();
    }


    @Test
void testProcesarCalculo_LimitesNB() {
    // Caso: Valor limite critico (NB=0)
    assertEquals(0.0, controller.procesarCalculo(100, 50, 0, 10), 0.001);

    // Caso: Valor limite minimo valido (NB=1)
    assertEquals(200.0, controller.procesarCalculo(100, 100, 1, 0), 0.001);
}

@Test
void testProcesarCalculo_ClasesEquivalencia() {
    // Caso: Caso estandar
    assertEquals(16.5, controller.procesarCalculo(100.0, 50.0, 10, 10.0), 0.001);

    // Caso: Clase invalida (Negativos)
    assertEquals(0.0, controller.procesarCalculo(50, 50, -5, 5), 0.001);
}


    @Test
    void testEsDecimalValido_VariosCasos() {

        assertTrue(controller.esDecimalValido("10.5"), "Debería aceptar decimales");
        assertTrue(controller.esDecimalValido("100"), "Debería aceptar enteros");
        assertFalse(controller.esDecimalValido("abc"), "No debería aceptar letras");
        assertFalse(controller.esDecimalValido("-5"), "No debería aceptar negativos");
    }
}