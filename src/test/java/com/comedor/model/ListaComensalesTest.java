package com.comedor.model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.comedor.model.Menu.TipoMenu;
import com.comedor.model.User.Role;

public class ListaComensalesTest {

    private PersistenciaManager pm;
    private final Path localDir = Path.of("C:", "SGCU", "data");
    private final Path desayunoFile = localDir.resolve("ListaComensalesDesayuno.json");
    private final Path almuerzoFile = localDir.resolve("ListaComensalesAlmuerzo.json");

    @BeforeEach
    void setup() throws IOException {
        pm = new PersistenciaManager();
        pm.resetearListaComensales(TipoMenu.DESAYUNO);
        pm.resetearListaComensales(TipoMenu.ALMUERZO);
    }

    @Test
    void testGetComensalesPorServicio_emptyAfterReset() {
        ComensalesPorServicio desayuno = pm.getComensalesPorServicio(TipoMenu.DESAYUNO);
        assertNotNull(desayuno, "El objeto no debe ser nulo incluso con archivo vacío");
        assertEquals(0, desayuno.getCantidadEstudiante(), "Estudiantes debe ser 0");
        assertEquals(0, desayuno.getCantidadProfesor(), "Profesores debe ser 0");
        assertEquals(0, desayuno.getCantidadTrabajador(), "Trabajadores debe ser 0");
        assertEquals(0, desayuno.getCantidadExonerado(), "Exonerados debe ser 0");
        assertEquals(0, desayuno.getCantidadBecario(), "Becarios debe ser 0");


        ComensalesPorServicio almuerzo = pm.getComensalesPorServicio(TipoMenu.ALMUERZO);
        assertNotNull(almuerzo);
        assertEquals(0, almuerzo.getCantidadEstudiante());
        assertEquals(0, almuerzo.getCantidadProfesor());
        assertEquals(0, almuerzo.getCantidadTrabajador());
        assertEquals(0, almuerzo.getCantidadExonerado());
        assertEquals(0, almuerzo.getCantidadBecario());
    }

    @Test
    void testGetComensalesPorServicio_withDataPersists() {
        pm.agregarComensalesPorServicio(TipoMenu.DESAYUNO, Role.EXONERADO);
        pm.agregarComensalesPorServicio(TipoMenu.DESAYUNO, Role.TRABAJADOR);
        pm.agregarComensalesPorServicio(TipoMenu.DESAYUNO, Role.TRABAJADOR);

        ComensalesPorServicio persisted = pm.getComensalesPorServicio(TipoMenu.DESAYUNO);
        assertNotNull(persisted);
        assertEquals(0, persisted.getCantidadEstudiante());
        assertEquals(0, persisted.getCantidadProfesor());
        assertEquals(2, persisted.getCantidadTrabajador(), "Dos trabajadores deberán leerse");
        assertEquals(1, persisted.getCantidadExonerado(), "Un exonerado deberá leerse");
        assertEquals(0, persisted.getCantidadBecario());
    }

    @Test
    void testResetearListaComensales_clearsExistingData() {
        pm.agregarComensalesPorServicio(TipoMenu.DESAYUNO, Role.ESTUDIANTE);
        pm.agregarComensalesPorServicio(TipoMenu.DESAYUNO, Role.PROFESOR);
        pm.agregarComensalesPorServicio(TipoMenu.DESAYUNO, Role.BECARIO);

        ComensalesPorServicio before = pm.getComensalesPorServicio(TipoMenu.DESAYUNO);
        assertNotNull(before);
        assertEquals(1, before.getCantidadEstudiante());
        assertEquals(1, before.getCantidadProfesor());
        assertEquals(0, before.getCantidadTrabajador());
        assertEquals(0, before.getCantidadExonerado());
        assertEquals(1, before.getCantidadBecario());

        pm.resetearListaComensales(TipoMenu.DESAYUNO);
        ComensalesPorServicio after = pm.getComensalesPorServicio(TipoMenu.DESAYUNO);
        assertNotNull(after);
        assertEquals(0, after.getCantidadEstudiante());
        assertEquals(0, after.getCantidadProfesor());
        assertEquals(0, after.getCantidadTrabajador());
        assertEquals(0, after.getCantidadExonerado());
        assertEquals(0, after.getCantidadBecario());
    }

}
