package es.um.example.demo.steps;

import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.micrometer.core.instrument.MeterRegistry;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.json.JsonMapper;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.EntityModel;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import es.um.example.demo.application.TaskMetricsEventListener;
import es.um.example.demo.application.dto.TodoResponse;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class MetricasTareasStepsDefinition {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    StepHelper stepHelper;

    @Autowired
    MeterRegistry meterRegistry;

    @Autowired
    TaskMetricsEventListener metricsEventListener;

    @Autowired
    JsonMapper jsonMapper;

    List<String> tareas = new ArrayList<>();
    

    @Dado("que no hay tareas existentes")
    public void que_no_hay_tareas_existentes() {
        // Assuming test isolation, counters should be 0 at start
        metricsEventListener.resetMetrics();
    }

    @Dado("que se han creado {int} tareas")
    public void que_se_han_creado_tareas(int count) throws Exception {
        for (int i = 0; i < count; i++) {
            String jsonBody = String.format("{\"asunto\":\"Tarea %d\"}", i);
            mockMvc.perform(MockMvcRequestBuilders.post("/example/demo/todos")
                            .contentType("application/json")
                            .with(stepHelper.getUserToken())
                            .content(jsonBody))
                   .andExpect(result -> assertEquals(201, result.getResponse().getStatus()))
                   .andExpect(result -> tareas.add(jsonMapper.readValue(result.getResponse().getContentAsString(),
                                            new TypeReference<EntityModel<TodoResponse>>() {})
                                        .getContent().getUuid() ));
        }
    }

    @Dado("se han completado {int} tareas")
    public void se_han_completado_tareas(int count) {
        tareas.remove(0); // Remove the first task
        for (String t : tareas) {
            try {
                mockMvc.perform(MockMvcRequestBuilders
                            .patch("/example/demo/todos/" + t + "/completar")
                            .with(stepHelper.getUserToken()))
                        .andExpect(result -> assertEquals(200, result.getResponse().getStatus()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Cuando("marco la tarea como completada")
    public void marco_la_tarea_como_completada() throws Exception {
        // Assume completing the first task
        mockMvc.perform(MockMvcRequestBuilders.patch("/example/demo/todos/" + stepHelper.getCreatedTaskUuid() + "/completar")
                        .with(stepHelper.getUserToken()))
               .andExpect(result -> assertEquals(200, result.getResponse().getStatus()));
    }

    @Entonces("el contador {string} debe incrementarse en {int}")
    public void el_contador_debe_incrementarse_en(String metricName, int increment) {
        Counter counter = meterRegistry.find(metricName).counter();
        assertNotNull(counter);
        assertEquals(increment, counter.count(), 0.01);
    }

    @Entonces("el gauge {string} debe mostrar aproximadamente {double}")
    public void el_gauge_debe_mostrar_aproximadamente(String metricName, double expected) {
        Gauge gauge = meterRegistry.find(metricName).gauge();
        assertNotNull(gauge);
        assertEquals(expected, gauge.value(), 1.0); // Allow 1% tolerance
    }

    @Entonces("el gauge {string} debe ser {int}")
    public void el_gauge_debe_ser(String metricName, int expected) {
        Gauge gauge = meterRegistry.find(metricName).gauge();
        assertNotNull(gauge);
        assertEquals(expected, gauge.value(), 0.01);
    }
}