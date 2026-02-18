package es.um.example.demo.steps;

import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CrearTareaStepsDefinition {

    static final String API_TODOS_PATH = "/example/demo/todos";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    StepHelper stepHelper;

    @Cuando("crea una tarea con asunto {string}")
    public void crea_una_tarea_con_asunto(String asunto) throws Exception {
        String jsonBody = String.format("{\"asunto\":\"%s\"}", asunto);
        mockMvc.perform(MockMvcRequestBuilders.post(API_TODOS_PATH)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .with(stepHelper.getUserToken())
                        .content(jsonBody))
               .andDo(stepHelper.readResponse);
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

    @Entonces("obtiene una respuesta de error de validación")
    public void obtiene_una_respuesta_de_error_de_validación() {
        assertEquals(HttpStatus.BAD_REQUEST.value(), stepHelper.getStatusCode()); 
    }

}
