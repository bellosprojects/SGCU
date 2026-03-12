package com.comedor.control;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.comedor.control.PanelAdminController;
import com.comedor.control.NavigationDelegate;
import com.comedor.model.ComensalesPorServicio;
import com.comedor.model.Menu.TipoMenu;
import com.comedor.model.PersistenciaManager;
import com.comedor.view.PanelAdminView;

public class ListaComensalesTest {

    @Mock private PersistenciaManager persistenciaMock;
    
    @Mock(answer = Answers.RETURNS_DEEP_STUBS) 
    private PanelAdminView panelAdminViewMock;
    
    @Mock private NavigationDelegate delegateMock;

    private PanelAdminController controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new PanelAdminController(panelAdminViewMock, persistenciaMock, delegateMock);
    }

    @Test
    public void testReset_CargaListaComensalesDesagregada() {
        // Preparamos los datos simulados 
        ComensalesPorServicio comensalesDesayuno = mock(ComensalesPorServicio.class);
        ComensalesPorServicio comensalesAlmuerzo = mock(ComensalesPorServicio.class);
        
        // Simulamos que el PersistenciaManager devuelve estas listas al controlador
        when(persistenciaMock.getComensalesPorServicio(TipoMenu.DESAYUNO)).thenReturn(comensalesDesayuno);
        when(persistenciaMock.getComensalesPorServicio(TipoMenu.ALMUERZO)).thenReturn(comensalesAlmuerzo);

        when(persistenciaMock.getCCB()).thenReturn(100.0);
        when(persistenciaMock.getPorcentajeFromRole(anyString())).thenReturn(10.0);

        controller.reset();

        verify(persistenciaMock).getComensalesPorServicio(TipoMenu.DESAYUNO);
        verify(persistenciaMock).getComensalesPorServicio(TipoMenu.ALMUERZO);
        
        verify(panelAdminViewMock).setListado(comensalesDesayuno, TipoMenu.DESAYUNO);
        verify(panelAdminViewMock).setListado(comensalesAlmuerzo, TipoMenu.ALMUERZO);
    }

    @Test
    public void testReset_CargaListaComensalesNula_SinAsistencia() {
        when(persistenciaMock.getComensalesPorServicio(TipoMenu.DESAYUNO)).thenReturn(null);
        when(persistenciaMock.getComensalesPorServicio(TipoMenu.ALMUERZO)).thenReturn(null);

        when(persistenciaMock.getCCB()).thenReturn(100.0);
        when(persistenciaMock.getPorcentajeFromRole(anyString())).thenReturn(10.0);

        controller.reset();

        verify(panelAdminViewMock).setListado(null, TipoMenu.DESAYUNO);
        verify(panelAdminViewMock).setListado(null, TipoMenu.ALMUERZO);
    }
}