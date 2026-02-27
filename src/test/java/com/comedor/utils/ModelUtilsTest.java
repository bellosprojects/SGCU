package com.comedor.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ModelUtilsTest {

    @Test
    void testEsDecimalValido_VariosCasos() {
        // validos
        assertTrue(ModelUtils.esDecimalValido("10.5"));          
        assertTrue(ModelUtils.esDecimalValido("100"));           
        // invalidos
        assertFalse(ModelUtils.esDecimalValido(""));           
        assertFalse(ModelUtils.esDecimalValido("abc"));        
        assertFalse(ModelUtils.esDecimalValido("-5"));          
    }
}
