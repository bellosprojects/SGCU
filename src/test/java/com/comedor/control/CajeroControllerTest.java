package com.comedor.control;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;

import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Method;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import com.comedor.model.Menu.TipoMenu;
import com.comedor.model.PersistenciaManager;
import com.comedor.utils.ModelUtils;
import com.comedor.view.CajeroView;

public class CajeroControllerTest {

    @Mock private PersistenciaManager persistenciaMock;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS) private CajeroView viewMock;
    @Mock private NavigationDelegate delegateMock;

    private CajeroController controller;
    private final String cedulaPrueba = "25000000";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testVerificarFaceId_ImagenesIguales_SaldoSuficiente() throws Exception {
        when(persistenciaMock.getPrecioForUser(cedulaPrueba)).thenReturn(30.0);
        when(persistenciaMock.getSaldoFromCedula(cedulaPrueba)).thenReturn(100.0);

        controller = new CajeroController(viewMock, persistenciaMock, delegateMock);

        try (
            MockedConstruction<JFileChooser> mockedChooser = mockConstruction(JFileChooser.class, (mock, context) -> {
                when(mock.showOpenDialog(any())).thenReturn(JFileChooser.APPROVE_OPTION);
                File fakeFile = mock(File.class);
                when(fakeFile.getAbsolutePath()).thenReturn("foto_falsa.jpg");
                when(mock.getSelectedFile()).thenReturn(fakeFile);
            });
            MockedStatic<ImageIO> mockedImageIO = mockStatic(ImageIO.class);
            MockedStatic<ModelUtils> mockedUtils = mockStatic(ModelUtils.class)
        ) {
            
            mockedImageIO.when(() -> ImageIO.read(any(File.class))).thenReturn(mock(BufferedImage.class));
            mockedUtils.when(() -> ModelUtils.compararRostros(any(), any())).thenReturn(true);

            Method metodo = CajeroController.class.getDeclaredMethod("verificarFaceId", TipoMenu.class, String.class);
            metodo.setAccessible(true); 
            
            metodo.invoke(controller, TipoMenu.ALMUERZO, cedulaPrueba);

            Thread.sleep(3000);

           
            verify(persistenciaMock).aceptarReserva(cedulaPrueba, TipoMenu.ALMUERZO);
            
            verify(persistenciaMock).sumarSaldo(cedulaPrueba, -30.0);
        }
    }

    @Test
    public void testVerificarFaceId_ImagenesIguales_SaldoInsuficiente() throws Exception {
        when(persistenciaMock.getPrecioForUser(cedulaPrueba)).thenReturn(30.0);
        when(persistenciaMock.getSaldoFromCedula(cedulaPrueba)).thenReturn(10.0); // ¡SALDO BAJO!

        controller = new CajeroController(viewMock, persistenciaMock, delegateMock);

        try (
            MockedConstruction<JFileChooser> mockedChooser = mockConstruction(JFileChooser.class, (mock, context) -> {
                when(mock.showOpenDialog(any())).thenReturn(JFileChooser.APPROVE_OPTION);
                File fakeFile = mock(File.class);
                when(fakeFile.getAbsolutePath()).thenReturn("foto_falsa.jpg");
                when(mock.getSelectedFile()).thenReturn(fakeFile);
            });
            MockedStatic<ImageIO> mockedImageIO = mockStatic(ImageIO.class);
            MockedStatic<ModelUtils> mockedUtils = mockStatic(ModelUtils.class)
        ) {
            mockedImageIO.when(() -> ImageIO.read(any(File.class))).thenReturn(mock(BufferedImage.class));
            mockedUtils.when(() -> ModelUtils.compararRostros(any(), any())).thenReturn(true);

            Method metodo = CajeroController.class.getDeclaredMethod("verificarFaceId", TipoMenu.class, String.class);
            metodo.setAccessible(true);
            metodo.invoke(controller, TipoMenu.ALMUERZO, cedulaPrueba);

            Thread.sleep(3000);

            
            verify(persistenciaMock, never()).aceptarReserva(anyString(), any(TipoMenu.class));
            verify(persistenciaMock, never()).sumarSaldo(anyString(), anyDouble());
        }
    }


    @Test
    public void testVerificarFaceId_ImagenesDistintas() throws Exception {
        when(persistenciaMock.getPrecioForUser(cedulaPrueba)).thenReturn(30.0);
        when(persistenciaMock.getSaldoFromCedula(cedulaPrueba)).thenReturn(100.0);

        controller = new CajeroController(viewMock, persistenciaMock, delegateMock);

        try (
            MockedConstruction<JFileChooser> mockedChooser = mockConstruction(JFileChooser.class, (mock, context) -> {
                when(mock.showOpenDialog(any())).thenReturn(JFileChooser.APPROVE_OPTION);
                File fakeFile = mock(File.class);
                when(fakeFile.getAbsolutePath()).thenReturn("foto_falsa.jpg");
                when(mock.getSelectedFile()).thenReturn(fakeFile);
            });
            MockedStatic<ImageIO> mockedImageIO = mockStatic(ImageIO.class)
        ) {
            
            BufferedImage fotoFalsa1 = mock(BufferedImage.class);
            when(fotoFalsa1.getWidth()).thenReturn(10);
            
            BufferedImage fotoFalsa2 = mock(BufferedImage.class);
            when(fotoFalsa2.getWidth()).thenReturn(20);

            mockedImageIO.when(() -> ImageIO.read(any(File.class))).thenReturn(fotoFalsa1, fotoFalsa2);

            Method metodo = CajeroController.class.getDeclaredMethod("verificarFaceId", TipoMenu.class, String.class);
            metodo.setAccessible(true);
            metodo.invoke(controller, TipoMenu.ALMUERZO, cedulaPrueba);

            Thread.sleep(3000);

            verify(persistenciaMock, never()).aceptarReserva(anyString(), any(TipoMenu.class));
            verify(persistenciaMock, never()).sumarSaldo(anyString(), anyDouble());
        }
    }
}


