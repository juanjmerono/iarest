package es.um.example.demo.steps;

import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Y;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RecursosPublicosStepsDefinition {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    StepHelper stepHelper;

    @Cuando("accede al recurso {string}")
    public void accede_al_recurso(String recurso) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(recurso)
                        .with(stepHelper.getUserToken()))
               .andDo(stepHelper.readResponse);
    }

    @Y("el contenido es HTML")
    public void el_contenido_es_html() {
        String contentType = stepHelper.getResponse().getResponse().getContentType();
        assertNotNull(contentType);
        assertTrue(contentType.contains(MediaType.TEXT_HTML_VALUE),
                "Expected HTML content but got: " + contentType);
    }
}
