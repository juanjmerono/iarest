package es.um.example.demo.steps;

import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.json.JsonMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import es.um.example.demo.application.dto.TodoResponse;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CrearTareaStepsDefinition {

    static final String API_TODOS_PATH = "/example/demo/todos";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    StepHelper stepHelper;

    @Autowired
    JsonMapper jsonMapper;

    @Cuando("crea una tarea con asunto {string}")
    public void crea_una_tarea_con_asunto(String asunto) throws Exception {
        String jsonBody = String.format("{\"asunto\":\"%s\"}", asunto);
        mockMvc.perform(MockMvcRequestBuilders.post(API_TODOS_PATH)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .with(stepHelper.getUserToken())
                        .content(jsonBody))
               .andDo(stepHelper.readResponse)
               .andDo(MockMvcResultHandlers.print())
               .andReturn();

        int status = stepHelper.getStatusCode();
        if (status == 201 || status == 200) {
            EntityModel<TodoResponse> em = 
                jsonMapper.readValue(stepHelper.getResponseBody(),new TypeReference<EntityModel<TodoResponse>>() {});
            stepHelper.createdTaskUUid(em.getContent().getUuid());
        }
        
    }

    @Entonces("obtiene una respuesta de creación exitosa")
    public void obtiene_una_respuesta_de_creación_exitosa() {
        assertEquals(HttpStatus.CREATED.value(), stepHelper.getStatusCode()); 
    }

    @Entonces("la tarea contiene el asunto {string}")
    public void la_tarea_contiene_el_asunto(String asunto) throws Exception {
        String body = stepHelper.getResponseBody();
        assertNotNull(body);
        assertTrue(body.contains("\"asunto\":\"" + asunto + "\""));
    }

    @Y("la tarea tiene estado {string}")
    public void la_tarea_tiene_estado(String estado) throws Exception {
        String body = stepHelper.getResponseBody();
        assertNotNull(body);
        assertTrue(body.contains("\"estado\":\"" + estado + "\""));
    }

    @Y("la tarea tiene fecha actual")
    public void la_tarea_tiene_fecha_actual() throws Exception {
        String body = stepHelper.getResponseBody();
        assertNotNull(body);
        assertTrue(body.contains("\"fecha\""));
    }

    @Y("la tarea tiene un uuid válido")
    public void la_tarea_tiene_un_uuid_válido() throws Exception {
        String body = stepHelper.getResponseBody();
        assertNotNull(body);
        assertTrue(body.contains("\"uuid\""));
        assertTrue(body.matches(".*\"uuid\"\\s*:\\s*\"[0-9a-f-]{36}\".*"));
    }

    @Y("la tarea tiene el usuarioId del usuario autenticado")
    public void la_tarea_tiene_el_usuarioId_del_usuario_autenticado() throws Exception {
        String body = stepHelper.getResponseBody();
        String expectedUser = stepHelper.getCurrentUser();
        assertNotNull(body);
        assertNotNull(expectedUser);
        assertTrue(body.contains("\"usuarioId\":\"" + expectedUser + "\""));
    }

    @Entonces("obtiene una respuesta de error de validación")
    public void obtiene_una_respuesta_de_error_de_validación() {
        assertEquals(HttpStatus.BAD_REQUEST.value(), stepHelper.getStatusCode()); 
    }

}
