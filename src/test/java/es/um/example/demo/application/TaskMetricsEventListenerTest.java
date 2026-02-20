package es.um.example.demo.application;

import es.um.example.demo.domain.model.TareaCompletadaEvent;
import es.um.example.demo.domain.model.TareaCreadaEvent;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskMetricsEventListenerTest {

    private MeterRegistry registry;
    private TaskMetricsEventListener listener;

    @BeforeEach
    void setUp() {
        registry = new SimpleMeterRegistry();
        listener = new TaskMetricsEventListener(registry);
    }

    @Test
    void shouldIncrementCreatedCounterOnTaskCreatedEvent() {
        // Given
        Counter counter = registry.find("tasks_created_total").counter();
        double initialCount = counter.count();

        // When
        TareaCreadaEvent event = new TareaCreadaEvent("uuid", "asunto", null, "user");
        listener.onTaskCreated(event);

        // Then
        assertEquals(initialCount + 1, counter.count());
    }

    @Test
    void shouldIncrementCompletedCounterOnTaskCompletedEvent() {
        // Given
        Counter counter = registry.find("tasks_completed_total").counter();
        double initialCount = counter.count();

        // When
        TareaCompletadaEvent event = new TareaCompletadaEvent("uuid", "asunto", null, "user");
        listener.onTaskCompleted(event);

        // Then
        assertEquals(initialCount + 1, counter.count());
    }

    @Test
    void shouldCalculateCompletionRatio() {
        // Given
        // Create 3 tasks
        for (int i = 0; i < 3; i++) {
            listener.onTaskCreated(new TareaCreadaEvent("uuid" + i, "asunto", null, "user"));
        }
        // Complete 2
        for (int i = 0; i < 2; i++) {
            listener.onTaskCompleted(new TareaCompletadaEvent("uuid" + i, "asunto", null, "user"));
        }

        // When
        Gauge gauge = registry.find("tasks_completion_ratio").gauge();

        // Then
        assertEquals((2.0 / 3.0) * 100.0, gauge.value(), 0.01);
    }

    @Test
    void shouldReturnZeroRatioWhenNoTasksCreated() {
        // Given no tasks

        // When
        Gauge gauge = registry.find("tasks_completion_ratio").gauge();

        // Then
        assertEquals(0.0, gauge.value());
    }
}