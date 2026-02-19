package es.um.example.demo.application.command;

import es.um.example.demo.domain.model.EstadoTarea;
import es.um.example.demo.domain.model.Tarea;
import es.um.example.demo.domain.model.TareaCompletadaEvent;
import es.um.example.demo.domain.port.TareaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompletarTareaCommandHandlerEventTest {

    @Mock
    private TareaRepository tareaRepository;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    private CompletarTareaCommandHandler handler;

    @BeforeEach
    void setUp() {
        handler = new CompletarTareaCommandHandler(tareaRepository, eventPublisher);
    }

    @Test
    void debePublicarEventoTrasCompletarTarea() {
        String uuid = "test-uuid-123";
        String usuarioId = "user1";
        CompletarTareaCommand command = new CompletarTareaCommand(uuid, usuarioId);

        Tarea tarea = new Tarea();
        tarea.setUuid(uuid);
        tarea.setAsunto("Tarea pendiente");
        tarea.setEstado(EstadoTarea.pendiente);
        tarea.setUsuarioId(usuarioId);
        tarea.setFecha(LocalDateTime.now());

        when(tareaRepository.findByUuid(uuid)).thenReturn(Optional.of(tarea));
        when(tareaRepository.save(any(Tarea.class))).thenReturn(tarea);

        handler.handle(command);

        verify(eventPublisher, times(1)).publishEvent(any(TareaCompletadaEvent.class));
    }

    @Test
    void debePublicarEventoConDatosCorrectos() {
        String uuid = "test-uuid-123";
        String usuarioId = "user1";
        String asunto = "Tarea de prueba";
        CompletarTareaCommand command = new CompletarTareaCommand(uuid, usuarioId);

        Tarea tarea = new Tarea();
        tarea.setUuid(uuid);
        tarea.setAsunto(asunto);
        tarea.setEstado(EstadoTarea.pendiente);
        tarea.setUsuarioId(usuarioId);
        tarea.setFecha(LocalDateTime.now());

        when(tareaRepository.findByUuid(uuid)).thenReturn(Optional.of(tarea));
        when(tareaRepository.save(any(Tarea.class))).thenAnswer(invocation -> {
            Tarea saved = invocation.getArgument(0);
            saved.setFechaResolucion(LocalDateTime.of(2026, 2, 19, 15, 45));
            return saved;
        });

        handler.handle(command);

        ArgumentCaptor<TareaCompletadaEvent> captor = ArgumentCaptor.forClass(TareaCompletadaEvent.class);
        verify(eventPublisher).publishEvent(captor.capture());

        TareaCompletadaEvent event = captor.getValue();
        assertEquals(uuid, event.uuid());
        assertEquals(asunto, event.asunto());
        assertEquals(LocalDateTime.of(2026, 2, 19, 15, 45), event.fechaResolucion());
        assertEquals(usuarioId, event.usuarioId());
    }

    @Test
    void noDebePublicarEventoSiTareaYaCompletada() {
        String uuid = "test-uuid-123";
        String usuarioId = "user1";
        CompletarTareaCommand command = new CompletarTareaCommand(uuid, usuarioId);

        Tarea tarea = new Tarea();
        tarea.setUuid(uuid);
        tarea.setAsunto("Tarea completada");
        tarea.setEstado(EstadoTarea.completada);
        tarea.setUsuarioId(usuarioId);
        tarea.setFecha(LocalDateTime.now());
        tarea.setFechaResolucion(LocalDateTime.now());

        when(tareaRepository.findByUuid(uuid)).thenReturn(Optional.of(tarea));

        handler.handle(command);

        verify(eventPublisher, never()).publishEvent(any(TareaCompletadaEvent.class));
    }
}