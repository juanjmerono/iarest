package es.um.example.demo.steps;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;

@SpringBootTest
@AutoConfigureMockMvc
public class OpenAPIStepsDefinition extends GenericStepsDefinition {
 
    static final String API_DOCS_PATH = "/api-docs";

    @Autowired
    MockMvc mockMvc;

    @Dado("que el sistema tiene documentación openapi disponible")
    public void que_el_sistema_tiene_documentación_openapi_disponible() throws Exception {
        // Check that the OpenAPI documentation endpoint is available
        mockMvc.perform(MockMvcRequestBuilders.get(API_DOCS_PATH)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Cuando("el cliente accede a la documentación OpenAPI")
    public void el_cliente_accede_a_la_documentación_open_api() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(API_DOCS_PATH)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
               .andDo(readResponse);
    }

    @Entonces("consulta la documentación openapi")
    public void consulta_la_documentación_openapi() throws Exception {
        String body = getResponseBody();
        assertNotNull(body);
        assertTrue(body.contains("\"openapi\""));
        assertTrue(body.contains("\"info\""));
        assertTrue(body.contains("\"paths\""));
    }

    @Y("se describe el endpoint {string}")
    public void se_describe_el_endpoint(String endpoint) throws Exception {
        String body = getResponseBody();
        assertNotNull(body);
        assertTrue(body.contains("\"" + endpoint + "\""));
    }
}
