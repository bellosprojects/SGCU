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
        // Preparamos los datos de Saldo y Precio
        // El menú cuesta 30 y el usuario tiene 100 (Saldo suficiente)
        when(persistenciaMock.getPrecioForUser(cedulaPrueba)).thenReturn(30.0);
        when(persistenciaMock.getSaldoFromCedula(cedulaPrueba)).thenReturn(100.0);

        controller = new CajeroController(viewMock, persistenciaMock, delegateMock);

        // Simulamos la ventana, las fotos y la comparación 
        try (
            // Simulamos que JFileChooser se abre y el usuario elige una foto falsa
            MockedConstruction<JFileChooser> mockedChooser = mockConstruction(JFileChooser.class, (mock, context) -> {
                when(mock.showOpenDialog(any())).thenReturn(JFileChooser.APPROVE_OPTION);
                File fakeFile = mock(File.class);
                when(fakeFile.getAbsolutePath()).thenReturn("foto_falsa.jpg");
                when(mock.getSelectedFile()).thenReturn(fakeFile);
            });
            // 2. Evitamos que Java intente leer una foto real del disco duro
            MockedStatic<ImageIO> mockedImageIO = mockStatic(ImageIO.class);
            // 3. Forzamos a que el método que compara los rostros diga "TRUE" (Son iguales)
            MockedStatic<ModelUtils> mockedUtils = mockStatic(ModelUtils.class)
        ) {
            
            // Configuramos las respuestas de los mocks estáticos
            mockedImageIO.when(() -> ImageIO.read(any(File.class))).thenReturn(mock(BufferedImage.class));
            mockedUtils.when(() -> ModelUtils.compararRostros(any(), any())).thenReturn(true);

            // 2. ACT: Usamos Reflection para invocar el método privado verificarFaceId SIN usar botones
            Method metodo = CajeroController.class.getDeclaredMethod("verificarFaceId", TipoMenu.class, String.class);
            metodo.setAccessible(true); // Rompemos la seguridad del "private"
            
            // ¡Disparamos el método!
            metodo.invoke(controller, TipoMenu.ALMUERZO, cedulaPrueba);

            Thread.sleep(3000);

            // 3. ASSERT: Verificamos que se ejecutó la lógica de Éxito
            // Validamos que se aceptó la reserva
            verify(persistenciaMock).aceptarReserva(cedulaPrueba, TipoMenu.ALMUERZO);
            
            // Validamos que la matemática funcionó y se le descontaron los 30 de saldo
            verify(persistenciaMock).sumarSaldo(cedulaPrueba, -30.0);
        }
    }

    @Test
    public void testVerificarFaceId_ImagenesIguales_SaldoInsuficiente() throws Exception {
        // 1. ARRANGE: El menú cuesta 30, pero el usuario SOLO TIENE 10
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
            // Simulamos que las fotos SÍ son iguales
            mockedUtils.when(() -> ModelUtils.compararRostros(any(), any())).thenReturn(true);

            // 2. ACT: Disparamos el método por Reflexión
            Method metodo = CajeroController.class.getDeclaredMethod("verificarFaceId", TipoMenu.class, String.class);
            metodo.setAccessible(true);
            metodo.invoke(controller, TipoMenu.ALMUERZO, cedulaPrueba);

            Thread.sleep(3000);

            // 3. ASSERT: Verificamos que NUNCA se haya aceptado la reserva ni restado saldo
            // El modificador never() asegura que esos métodos no se llamaron
            verify(persistenciaMock, never()).aceptarReserva(anyString(), any(TipoMenu.class));
            verify(persistenciaMock, never()).sumarSaldo(anyString(), anyDouble());
        }
    }


    @Test
    public void testVerificarFaceId_ImagenesDistintas() throws Exception {
        // 1. ARRANGE
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
            // Ya no necesitamos mockedUtils, solo interceptamos las fotos
            MockedStatic<ImageIO> mockedImageIO = mockStatic(ImageIO.class)
        ) {
            
            // EL GRAN TRUCO: Creamos dos imágenes falsas con tamaños diferentes
            BufferedImage fotoFalsa1 = mock(BufferedImage.class);
            when(fotoFalsa1.getWidth()).thenReturn(10);
            
            BufferedImage fotoFalsa2 = mock(BufferedImage.class);
            when(fotoFalsa2.getWidth()).thenReturn(20);

            // Le ordenamos a ImageIO que devuelva la foto 1 y luego la foto 2
            mockedImageIO.when(() -> ImageIO.read(any(File.class))).thenReturn(fotoFalsa1, fotoFalsa2);

            // 2. ACT
            Method metodo = CajeroController.class.getDeclaredMethod("verificarFaceId", TipoMenu.class, String.class);
            metodo.setAccessible(true);
            metodo.invoke(controller, TipoMenu.ALMUERZO, cedulaPrueba);

            Thread.sleep(3000);

            // 3. ASSERT: Como los tamaños son distintos, la validación falla y NUNCA se hace reserva
            verify(persistenciaMock, never()).aceptarReserva(anyString(), any(TipoMenu.class));
            verify(persistenciaMock, never()).sumarSaldo(anyString(), anyDouble());
        }
    }
}


