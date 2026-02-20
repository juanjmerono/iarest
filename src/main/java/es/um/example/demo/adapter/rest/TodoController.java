package es.um.example.demo.adapter.rest;

import es.um.example.demo.application.command.CompletarTareaCommand;
import es.um.example.demo.application.command.CompletarTareaCommandHandler;
import es.um.example.demo.application.command.CrearTareaCommandHandler;
import es.um.example.demo.application.dto.CrearTareaRequest;
import es.um.example.demo.application.dto.TodoResponse;
import es.um.example.demo.application.query.ObtenerTareasQuery;
import es.um.example.demo.application.query.ObtenerTareasQueryHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/example/demo","${springdoc.server.path}"})
@Tag(name = "Tareas", description = "API para gestión de tareas")
public class TodoController {

    private final ObtenerTareasQueryHandler queryHandler;
    private final CrearTareaCommandHandler crearTareaCommandHandler;
    private final CompletarTareaCommandHandler completarTareaCommandHandler;
    private final TodoModelAssembler assembler;

    public TodoController(ObtenerTareasQueryHandler queryHandler, 
                          CrearTareaCommandHandler crearTareaCommandHandler,
                          CompletarTareaCommandHandler completarTareaCommandHandler,
                          TodoModelAssembler assembler) {
        this.queryHandler = queryHandler;
        this.crearTareaCommandHandler = crearTareaCommandHandler;
        this.completarTareaCommandHandler = completarTareaCommandHandler;
        this.assembler = assembler;
    }

    @Operation(summary = "Obtener todas las tareas", description = "Retorna la lista de todas las tareas con su información básica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de tareas obtenida correctamente",
            content = @Content(mediaType = "application/hal+json")),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @SecurityRequirement(name = "oidc", scopes = {"Tareas:read"})
    @GetMapping(path = "/todos", produces = "application/hal+json")
    @PreAuthorize("hasPermission('Tareas','read')")
    public CollectionModel<EntityModel<TodoResponse>> obtenerTareas(@AuthenticationPrincipal Jwt jwt) {
        String usuarioId = jwt.getSubject();
        return assembler.toDecoratedCollectionModel(queryHandler.handle(new ObtenerTareasQuery.ObtenerTareasQueryRequest(), usuarioId));
    }

    @Operation(summary = "Crear una nueva tarea", description = "Crea una nueva tarea en la lista. La fecha se establece automáticamente a la fecha actual y el estado inicial es PENDIENTE.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Tarea creada correctamente",
            content = @Content(mediaType = "application/hal+json",
            schema = @Schema(implementation = TodoResponse.class))),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "401", description = "No autenticado"),
        @ApiResponse(responseCode = "403", description = "No autorizado - requiere scope write")
    })
    @SecurityRequirement(name = "oidc", scopes = {"Tareas:write"})
    @PostMapping(path = "/todos", consumes = "application/json", produces = "application/hal+json")
    @PreAuthorize("hasPermission('Tareas','write')")
    public ResponseEntity<EntityModel<TodoResponse>> crearTarea(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody CrearTareaRequest request) {
        String usuarioId = jwt.getSubject();
        var result = crearTareaCommandHandler.handle(request, usuarioId);
        TodoResponse response = result.toTodoResponse();
        EntityModel<TodoResponse> entityModel = assembler.toModel(response);
        return ResponseEntity.status(HttpStatus.CREATED).body(entityModel);
    }

    @Operation(summary = "Completar una tarea", description = "Marca una tarea como completada. La operación es idempotente: completar una tarea ya completada devuelve éxito.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tarea completada correctamente",
            content = @Content(mediaType = "application/hal+json")),
        @ApiResponse(responseCode = "401", description = "No autenticado"),
        @ApiResponse(responseCode = "403", description = "No autorizado - requiere scope write"),
        @ApiResponse(responseCode = "404", description = "Tarea no encontrada o no pertenece al usuario")
    })
    @SecurityRequirement(name = "oidc", scopes = {"Tareas:write"})
    @PatchMapping(path = "/todos/{uuid}/completar", produces = "application/hal+json")
    @PreAuthorize("hasPermission('Tareas','write')")
    public ResponseEntity<EntityModel<TodoResponse>> completarTarea(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable String uuid) {
        String usuarioId = jwt.getSubject();
        var command = new CompletarTareaCommand(uuid, usuarioId);
        var result = completarTareaCommandHandler.handle(command);
        
        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        TodoResponse response = result.get().toTodoResponse();
        EntityModel<TodoResponse> entityModel = assembler.toModel(response);
        return ResponseEntity.ok(entityModel);
    }
}
