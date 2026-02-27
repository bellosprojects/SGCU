package com.comedor.control;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.comedor.model.PersistenciaManager;
import com.comedor.view.GestionarCCBView;

public class CCBCalculoControllerTest {

   private CCBCalculoController controller;

   @Mock private GestionarCCBView mockView;
   @Mock private PersistenciaManager mockPersistence;
   @Mock private NavigationDelegate mockDelegate;

   @BeforeEach
   void setup() {
       MockitoAnnotations.openMocks(this);
       mockView = mock(GestionarCCBView.class, RETURNS_DEEP_STUBS);
       when(mockPersistence.getCCB()).thenReturn(null);
       controller = new CCBCalculoController(mockView, mockPersistence, mockDelegate);
   }

   @Test
   void testProcesarCalculo_valoresValidos() {
       // (CF+CV)/NB * (1 + porcMerma/100)
       double resultado = controller.procesarCalculo(100.0, 100.0, 2, 0.0);
       assertEquals(100.0, resultado, 0.0001);
   }

   @Test
   void testProcesarCalculo_CeroBandejas_retornaCero() {
       double resultado = controller.procesarCalculo(100, 50, 0, 10.0);
       assertEquals(0.0, resultado);
   }
   @Test
   void testProcesarCalculo_valoresValidosLimite() {
       // (CF+CV)/NB * (1 + porcMerma/100)
       double resultado = controller.procesarCalculo(100.0, 100.0, 1, 0.0);
       assertEquals(200.0, resultado, 0.0001);
   }

   @Test
   void testProcesarCalculo_BandejasNegativas_retornaCero() {
       double resultado = controller.procesarCalculo(200, 200, -5, 2.0);
       assertEquals(0.0, resultado);
   }

   @Test
   void testProcesarCalculo_sinMerma() {
       double resultado = controller.procesarCalculo(300, 300, 6, 0.0);
       // (300+300)/6 == 100
       assertEquals(100.0, resultado, 0.0001);
   }

   @Test
   void testProcesarCalculo_conMerma() {
       double resultado = controller.procesarCalculo(50, 50, 5, 50.0);
       // (100/5)*(1+0.5) = 20*1.5 = 30
       assertEquals(30.0, resultado, 0.0001);
   }

   @Test
   void testProcesarCalculo_CostoNegativo_retornaCero() {
       double resultado = controller.procesarCalculo(-50, 50, 10, 5.0);
       assertEquals(0.0, resultado);
   }
}