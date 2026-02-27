package com.comedor.model;

import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import com.comedor.utils.ModelUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;


public class PersistenciaManagerTest {
// --- Pruebas unitarias para el método autenticar(String cedula, String password) ---
    @TempDir
    Path tempRuta;

    private PersistenciaManager manager;

    @BeforeEach
    void setUp() throws Exception {
        manager = new PersistenciaManager();
        //Creamos una ruta temporal para el archivo de usuarios
        Path tempUsers = tempRuta.resolve("UsuariosRegistrados.json");
        Files.createFile(tempUsers);
        Field usersField = PersistenciaManager.class.getDeclaredField("usersFile");
        usersField.setAccessible(true);
        usersField.set(manager, tempUsers);
    }

    private void agregarUser(String cedula, String rawPassword) throws Exception {
        String passEncriptada = ModelUtils.encriptar(rawPassword);
        User user = new User("Javier García", cedula, passEncriptada, "prueba@gmail.com", "", 0.0, "Estudiante");
        Files.writeString(
            getUsersFilePath(),
            user.toJson() + System.lineSeparator(),
            StandardOpenOption.APPEND
        );
    }

    private Path getUsersFilePath() throws Exception {
        Field usersField = PersistenciaManager.class.getDeclaredField("usersFile");
        usersField.setAccessible(true);
        return (Path) usersField.get(manager);
    }

    @Test
    void autenticar_conCredencialesValidas_devuelveTrue() throws Exception {
        agregarUser("30714998", "contrasena");

        assertTrue(manager.autenticar("30714998", "contrasena"));
    }

    @Test
    void autenticar_conPasswordVacia_devuelveFalse() throws Exception {
        agregarUser("30714998", "contrasena");

        assertFalse(manager.autenticar("30714998", ""));
    }

    @Test
    void autenticar_usuarioNoRegistrado_devuelveFalse() {
        assertFalse(manager.autenticar("123456", "agucate"));
    }

    @Test
    void autenticar_CedulaVacia_devuelveFalse() throws Exception {
        agregarUser("30714998", "contrasena");
        assertFalse(manager.autenticar("","contrasena"));
    }

    @Test
    void autenticar_CedulaConCaracteres_devuelveFalse() throws Exception {
        agregarUser("32100249", "contrasena");
        assertFalse(manager.autenticar("321aa249","contrasena"));
    }

    @Test
    void autenticar_PasswordMenor8_devuelveFalse() throws Exception {
        agregarUser("32100249", "12345678");
        assertFalse(manager.autenticar("32100249","123456"));
    }

    @Test
    void autenticar_PasswordMayorIgual8_devuelveTrue() throws Exception {
        agregarUser("32100249", "123456789");
        assertTrue(manager.autenticar("32100249","123456789"));
    }

}
