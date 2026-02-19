package es.um.example.demo.application.command;

public record CompletarTareaCommand(
        String uuid,
        String usuarioId
) {
}
