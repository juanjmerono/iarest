package es.um.example.demo.steps;

import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.json.JsonMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import es.um.example.demo.application.dto.TodoResponse;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ObtenerTareasStepsDefinition {

    static final String API_TODOS_PATH = "/example/demo/todos";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    StepHelper stepHelper;

    @Autowired
    JsonMapper jsonMapper;

    @Dado("un usuario anónimo")
    public void un_usuario_anónimo() {
        stepHelper.enableUserToken(null,null);
    }

    @Dado("un usuario autenticado {string} con permiso {string}")
    public void un_usuario_autenticado(String user, String scope) {
        stepHelper.enableUserToken(user, scope);
    }

    @Cuando("consulta la lista de tareas")
    public void consulta_la_lista_de_tareas() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(API_TODOS_PATH)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .with(stepHelper.getUserToken()))
               .andDo(stepHelper.readResponse);
    }

    @Entonces("obtiene una respuesta no autenticado")
    public void obtiene_una_respuesta_no_autenticado() {
        assertEquals(HttpStatus.UNAUTHORIZED.value(), stepHelper.getStatusCode()); 
    }

    @Entonces("obtiene una respuesta no autorizada")
    public void obtiene_una_respuesta_no_autorizada() {
        assertEquals(HttpStatus.FORBIDDEN.value(), stepHelper.getStatusCode()); 
    }

    @Entonces("obtiene una respuesta correcta")
    public void obtiene_una_respuesta_correcta() {
        assertEquals(HttpStatus.OK.value(), stepHelper.getStatusCode()); 
    }

    @Y("una lista de tareas no vacía")
    public void una_lista_de_tareas_no_vacia() throws Exception {
        String body = stepHelper.getResponseBody();
        assertNotNull(body);

        CollectionModel<EntityModel<TodoResponse>> cm = 
            jsonMapper.readValue(body, 
                    new TypeReference<CollectionModel<EntityModel<TodoResponse>>>() {});

        assertTrue(cm.getContent().size() > 0, "Expected non-empty list but got: " + body);
    }

    @Y("cada tarea contiene los campos: uuid, asunto, fecha, estado, usuarioId")
    public void cada_tarea_contiene_los_campos_uuid_asunto_fecha_estado_usuarioId() throws Exception {
        String body = stepHelper.getResponseBody();
        assertNotNull(body);

        CollectionModel<EntityModel<TodoResponse>> cm = 
            jsonMapper.readValue(body, 
                    new TypeReference<CollectionModel<EntityModel<TodoResponse>>>() {});

        assertTrue(cm.getContent().stream().allMatch(c -> !c.getContent().getAsunto().isBlank()));
    }

    @Dado("el sistema tiene tareas de otros usuarios")
    public void el_sistema_tiene_tareas_de_otros_usuarios() {
        // Tasks are preloaded in test-data-todo-list.sql with different usuarioId
    }

    @Y("la lista no contiene tareas de otros usuarios")
    public void la_lista_no_contiene_tareas_de_otros_usuarios() throws Exception {
        String body = stepHelper.getResponseBody();
        String currentUser = stepHelper.getCurrentUser();
        assertNotNull(body);
        assertNotNull(currentUser);

        CollectionModel<EntityModel<TodoResponse>> cm = 
            jsonMapper.readValue(body, 
                    new TypeReference<CollectionModel<EntityModel<TodoResponse>>>() {});

        boolean notFromOtherUsers = cm.getContent().stream()
            .allMatch(e -> currentUser.equals(e.getContent().getUsuarioId()));

        assertTrue(notFromOtherUsers, "La lista contiene tareas de otros usuarios");
    }

}
