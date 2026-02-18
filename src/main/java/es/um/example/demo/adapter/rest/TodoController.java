package es.um.example.demo.adapter.rest;

import es.um.example.demo.application.dto.TodoResponse;
import es.um.example.demo.application.query.ObtenerTareasQuery;
import es.um.example.demo.application.query.ObtenerTareasQueryHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/example/demo/todos")
@Tag(name = "Tareas", description = "API para gestión de tareas")
public class TodoController {

    private final ObtenerTareasQueryHandler queryHandler;
    private final TodoModelAssembler assembler;

    public TodoController(ObtenerTareasQueryHandler queryHandler, TodoModelAssembler assembler) {
        this.queryHandler = queryHandler;
        this.assembler = assembler;
    }

    @Operation(summary = "Obtener todas las tareas", description = "Retorna la lista de todas las tareas con su información básica")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de tareas obtenida correctamente",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = TodoResponse.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    @PreAuthorize("hasPermission('Tareas','read')")
    public CollectionModel<EntityModel<TodoResponse>> obtenerTareas(@AuthenticationPrincipal Jwt jwt) {
        return assembler.toDecoratedCollectionModel(queryHandler.handle(new ObtenerTareasQuery.ObtenerTareasQueryRequest()));
    }
}
