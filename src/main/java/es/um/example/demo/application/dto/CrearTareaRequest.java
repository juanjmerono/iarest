package es.um.example.demo.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request para crear una nueva tarea")
public class CrearTareaRequest {

    @Schema(description = "Asunto o título de la tarea", example = "Terminar informe mensual", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "El asunto no puede estar vacío")
    private String asunto;

    public CrearTareaRequest() {
    }

    public CrearTareaRequest(String asunto) {
        this.asunto = asunto;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }
}
