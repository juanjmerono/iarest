package es.um.example.demo.adapter.rest;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import es.um.example.demo.application.dto.TodoResponse;
import es.um.example.demo.application.query.ObtenerTareasQuery.ObtenerTareasQueryResult;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.hateoas.CollectionModel;

@Component
public class TodoModelAssembler implements RepresentationModelAssembler<TodoResponse, EntityModel<TodoResponse>> {

    public CollectionModel<EntityModel<TodoResponse>> toDecoratedCollectionModel(ObtenerTareasQueryResult result) {
        CollectionModel<EntityModel<TodoResponse>> collectionModel = toCollectionModel(result.tareas());
        collectionModel.add(linkTo(methodOn(TodoController.class).obtenerTareas(null)).withSelfRel());
        collectionModel.add(linkTo(methodOn(TodoController.class).crearTarea(null, null)).withRel("create"));
        return collectionModel;
    }


    @Override
    public EntityModel<TodoResponse> toModel(TodoResponse tarea) {
        return EntityModel.of(tarea,
            linkTo(methodOn(TodoController.class).obtenerTareas(null)).withRel("todos"),
            linkTo(methodOn(TodoController.class).crearTarea(null, null)).withSelfRel());
    }

    @Override
    public CollectionModel<EntityModel<TodoResponse>> toCollectionModel(Iterable<? extends TodoResponse> entities) {
        return StreamSupport
			.stream(entities.spliterator(), false)
			.map(this::toModel)
			.collect(Collectors.collectingAndThen(Collectors.toList(), CollectionModel::of));
    }
}
