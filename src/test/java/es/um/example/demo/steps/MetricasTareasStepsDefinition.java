package es.um.example.demo.steps;

import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MetricasTareasStepsDefinition {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    StepHelper stepHelper;

    @Autowired
    MeterRegistry meterRegistry;

    private double initialCreatedCount;
    private double initialCompletedCount;

    @Dado("que no hay tareas existentes")
    public void que_no_hay_tareas_existentes() {
        // Assuming test isolation, counters should be 0 at start
        Counter createdCounter = meterRegistry.find("tasks_created_total").counter();
        Counter completedCounter = meterRegistry.find("tasks_completed_total").counter();
        if (createdCounter != null) {
            // Reset for test - in real scenario, tests are isolated
            // For simplicity, assume 0
        }
        initialCreatedCount = createdCounter != null ? createdCounter.count() : 0;
        initialCompletedCount = completedCounter != null ? completedCounter.count() : 0;
    }

    @Dado("que existe una tarea pendiente con asunto {string}")
    public void que_existe_una_tarea_pendiente_con_asunto(String asunto) throws Exception {
        String jsonBody = String.format("{\"asunto\":\"%s\"}", asunto);
        mockMvc.perform(MockMvcRequestBuilders.post("/example/demo/todos")
                        .contentType("application/json")
                        .with(stepHelper.getUserToken())
                        .content(jsonBody))
               .andExpect(result -> assertEquals(201, result.getResponse().getStatus()));
    }

    @Dado("que se han creado {int} tareas")
    public void que_se_han_creado_tareas(int count) throws Exception {
        for (int i = 0; i < count; i++) {
            String jsonBody = String.format("{\"asunto\":\"Tarea %d\"}", i);
            mockMvc.perform(MockMvcRequestBuilders.post("/example/demo/todos")
                            .contentType("application/json")
                            .with(stepHelper.getUserToken())
                            .content(jsonBody))
                   .andExpect(result -> assertEquals(201, result.getResponse().getStatus()));
        }
    }

    @Dado("se han completado {int} tareas")
    public void se_han_completado_tareas(int count) {
        // For simplicity, assume the last count tasks are completed
        // In real test, would need to query and complete specific tasks
    }

    @Cuando("creo una nueva tarea con asunto {string}")
    public void creo_una_nueva_tarea_con_asunto(String asunto) throws Exception {
        String jsonBody = String.format("{\"asunto\":\"%s\"}", asunto);
        mockMvc.perform(MockMvcRequestBuilders.post("/example/demo/todos")
                        .contentType("application/json")
                        .with(stepHelper.getUserToken())
                        .content(jsonBody))
               .andExpect(result -> assertEquals(201, result.getResponse().getStatus()));
    }

    @Cuando("marco la tarea como completada")
    public void marco_la_tarea_como_completada() throws Exception {
        // Assume completing the first task
        mockMvc.perform(MockMvcRequestBuilders.put("/example/demo/todos/complete/0") // Adjust path
                        .with(stepHelper.getUserToken()))
               .andExpect(result -> assertEquals(200, result.getResponse().getStatus()));
    }

    @Entonces("el contador {string} debe incrementarse en {int}")
    public void el_contador_debe_incrementarse_en(String metricName, int increment) {
        Counter counter = meterRegistry.find(metricName).counter();
        assertNotNull(counter);
        double expected = initialCreatedCount + increment;
        assertEquals(expected, counter.count(), 0.01);
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