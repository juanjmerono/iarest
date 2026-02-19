package es.um.example.demo.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TareaCompletadaEventTest {

    @Test
    void debeCrearEventoConTodosLosCampos() {
        String uuid = "test-uuid-123";
        String asunto = "Tarea completada";
        LocalDateTime fechaResolucion = LocalDateTime.of(2026, 2, 19, 15, 45);
        String usuarioId = "user1";

        TareaCompletadaEvent event = new TareaCompletadaEvent(uuid, asunto, fechaResolucion, usuarioId);

        assertEquals(uuid, event.uuid());
        assertEquals(asunto, event.asunto());
        assertEquals(fechaResolucion, event.fechaResolucion());
        assertEquals(usuarioId, event.usuarioId());
    }

    @Test
    void debeSerInmutable() {
        TareaCompletadaEvent event = new TareaCompletadaEvent("uuid", "asunto", LocalDateTime.now(), "user");
        
        assertDoesNotThrow(() -> event.uuid());
        assertDoesNotThrow(() -> event.asunto());
        assertDoesNotThrow(() -> event.fechaResolucion());
        assertDoesNotThrow(() -> event.usuarioId());
    }
}