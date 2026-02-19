package es.um.example.demo.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TareaCreadaEventTest {

    @Test
    void debeCrearEventoConTodosLosCampos() {
        String uuid = "test-uuid-123";
        String asunto = "Nueva tarea";
        LocalDate fecha = LocalDate.of(2026, 2, 19);
        String usuarioId = "user1";

        TareaCreadaEvent event = new TareaCreadaEvent(uuid, asunto, fecha, usuarioId);

        assertEquals(uuid, event.uuid());
        assertEquals(asunto, event.asunto());
        assertEquals(fecha, event.fecha());
        assertEquals(usuarioId, event.usuarioId());
    }

    @Test
    void debeSerInmutable() {
        TareaCreadaEvent event = new TareaCreadaEvent("uuid", "asunto", LocalDate.now(), "user");
        
        assertDoesNotThrow(() -> event.uuid());
        assertDoesNotThrow(() -> event.asunto());
        assertDoesNotThrow(() -> event.fecha());
        assertDoesNotThrow(() -> event.usuarioId());
    }
}
