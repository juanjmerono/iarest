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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import es.um.example.demo.application.dto.TodoResponse;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CompletarTareaStepsDefinition {

    static final String API_TODOS_PATH = "/example/demo/todos";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    StepHelper stepHelper;

    @Autowired
    JsonMapper jsonMapper;

    @Dado("una tarea existente con asunto {string}")
    public void una_tarea_existente_con_asunto(String asunto) throws Exception {
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

    @Dado("una tarea existente completada con asunto {string}")
    public void una_tarea_existente_completada_con_asunto(String asunto) throws Exception {
        String jsonBody = String.format("{\"asunto\":\"%s\"}", asunto);
        mockMvc.perform(MockMvcRequestBuilders.post(API_TODOS_PATH)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .with(stepHelper.getUserToken())
                        .content(jsonBody))
               .andDo(stepHelper.readResponse)
               .andReturn();
        
        int status = stepHelper.getStatusCode();
        if (status == 201 || status == 200) {
            EntityModel<TodoResponse> em = 
                jsonMapper.readValue(stepHelper.getResponseBody(),new TypeReference<EntityModel<TodoResponse>>() {});
            stepHelper.createdTaskUUid(em.getContent().getUuid());
        }

        mockMvc.perform(MockMvcRequestBuilders.patch(API_TODOS_PATH + "/" + stepHelper.getCreatedTaskUuid() + "/completar")
                        .with(stepHelper.getUserToken()))
               .andDo(stepHelper.readResponse);
    }

    @Dado("una tarea existente con asunto {string} del usuario {string}")
    public void una_tarea_existente_con_asunto_del_usuario(String asunto, String usuario) throws Exception {
        String originalUser = stepHelper.getCurrentUser();
        stepHelper.enableUserToken(usuario, "write");
        
        String jsonBody = String.format("{\"asunto\":\"%s\"}", asunto);
        mockMvc.perform(MockMvcRequestBuilders.post(API_TODOS_PATH)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .with(stepHelper.getUserToken())
                        .content(jsonBody))
               .andDo(stepHelper.readResponse)
               .andReturn();
        
        int status = stepHelper.getStatusCode();
        if (status == 201 || status == 200) {
            EntityModel<TodoResponse> em = 
                jsonMapper.readValue(stepHelper.getResponseBody(),new TypeReference<EntityModel<TodoResponse>>() {});
            stepHelper.createdTaskUUid(em.getContent().getUuid());
        }

        stepHelper.enableUserToken(originalUser, "write");
    }

    @Cuando("completa la tarea")
    public void completa_la_tarea() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch(API_TODOS_PATH + "/" + stepHelper.getCreatedTaskUuid() + "/completar")
                        .with(stepHelper.getUserToken()))
               .andDo(stepHelper.readResponse);
    }

    @Cuando("completa la tarea con uuid inexistente")
    public void completa_la_tarea_con_uuid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch(API_TODOS_PATH + "/inexistente/completar")
                        .with(stepHelper.getUserToken()))
               .andDo(stepHelper.readResponse);
    }

    @Entonces("obtiene una respuesta exitosa")
    public void obtiene_una_respuesta_exitosa() {
        assertEquals(HttpStatus.OK.value(), stepHelper.getStatusCode()); 
    }

    @Y("la tarea tiene fechaResolucion establecida")
    public void la_tarea_tiene_fechaResolucion_establecida() throws Exception {
        String body = stepHelper.getResponseBody();
        assertNotNull(body);
        assertTrue(body.contains("\"fechaResolucion\":"), "Expected fechaResolucion field but got: " + body);
        assertFalse(body.matches(".*\"fechaResolucion\"\\s*:\\s*null.*"), "Expected fechaResolucion to not be null but got: " + body);
    }

    @Entonces("obtiene una respuesta de tarea no encontrada")
    public void obtiene_una_respuesta_de_tarea_no_encontrada() {
        assertEquals(HttpStatus.NOT_FOUND.value(), stepHelper.getStatusCode()); 
    }

    @Y("la fechaResolucion no se modifica")
    public void la_fechaResolucion_no_se_modifica() throws Exception {
        String body = stepHelper.getResponseBody();
        assertNotNull(body);
        assertTrue(body.contains("\"fechaResolucion\":"), "Expected fechaResolucion field but got: " + body);
    }
}
