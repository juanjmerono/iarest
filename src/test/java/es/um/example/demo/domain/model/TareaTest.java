package es.um.example.demo.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TareaTest {

    @Test
    void fechaDebeIncluirHora() {
        LocalDateTime fechaConHora = LocalDateTime.of(2026, 2, 19, 10, 30, 45);
        
        Tarea tarea = new Tarea();
        tarea.setFecha(fechaConHora);
        
        assertEquals(fechaConHora, tarea.getFecha());
        assertEquals(10, tarea.getFecha().getHour());
        assertEquals(30, tarea.getFecha().getMinute());
        assertEquals(45, tarea.getFecha().getSecond());
    }

    @Test
    void fechaResolucionDebeEstablecerseCorrectamente() {
        LocalDateTime fechaResolucion = LocalDateTime.of(2026, 2, 19, 15, 45, 30);
        
        Tarea tarea = new Tarea();
        tarea.setFechaResolucion(fechaResolucion);
        
        assertEquals(fechaResolucion, tarea.getFechaResolucion());
        assertNotNull(tarea.getFechaResolucion());
    }
}