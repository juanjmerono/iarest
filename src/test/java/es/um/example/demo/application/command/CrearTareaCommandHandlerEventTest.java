package es.um.example.demo.application.command;

import es.um.example.demo.application.dto.CrearTareaRequest;
import es.um.example.demo.domain.model.TareaCreadaEvent;
import es.um.example.demo.domain.port.TareaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CrearTareaCommandHandlerEventTest {

    @Mock
    private TareaRepository tareaRepository;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    private CrearTareaCommandHandler handler;

    @BeforeEach
    void setUp() {
        handler = new CrearTareaCommandHandler(tareaRepository, eventPublisher);
    }

    @Test
    void debePublicarEventoTrasCrearTarea() {
        String usuarioId = "user1";
        String uuid = "test-uuid-123";
        CrearTareaRequest request = new CrearTareaRequest("Nueva tarea");

        when(tareaRepository.save(any())).thenAnswer(invocation -> {
            var tarea = invocation.getArgument(0, es.um.example.demo.domain.model.Tarea.class);
            tarea.setUuid(uuid);
            tarea.setFecha(LocalDate.now());
            return tarea;
        });

        handler.handle(request, usuarioId);

        verify(eventPublisher, times(1)).publishEvent(any(TareaCreadaEvent.class));
    }

    @Test
    void debePublicarEventoConDatosCorrectos() {
        String usuarioId = "user1";
        String uuid = "test-uuid-123";
        String asunto = "Tarea de prueba";
        LocalDate fecha = LocalDate.of(2026, 2, 19);
        CrearTareaRequest request = new CrearTareaRequest(asunto);

        when(tareaRepository.save(any())).thenAnswer(invocation -> {
            var tarea = invocation.getArgument(0, es.um.example.demo.domain.model.Tarea.class);
            tarea.setUuid(uuid);
            tarea.setFecha(fecha);
            return tarea;
        });

        handler.handle(request, usuarioId);

        ArgumentCaptor<TareaCreadaEvent> captor = ArgumentCaptor.forClass(TareaCreadaEvent.class);
        verify(eventPublisher).publishEvent(captor.capture());

        TareaCreadaEvent event = captor.getValue();
        assertEquals(uuid, event.uuid());
        assertEquals(asunto, event.asunto());
        assertEquals(fecha, event.fecha());
        assertEquals(usuarioId, event.usuarioId());
    }
}
