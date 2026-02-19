package es.um.example.demo.application;

import es.um.example.demo.domain.model.TareaCompletadaEvent;
import es.um.example.demo.domain.model.TareaCreadaEvent;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class TaskMetricsEventListener {

    private Counter tasksCreatedCounter;
    private Counter tasksCompletedCounter;
    private MeterRegistry registry;

    public TaskMetricsEventListener(MeterRegistry registry) {
        this.registry = registry;
        registerMetrics();
    }

    @EventListener
    public void onTaskCreated(TareaCreadaEvent event) {
        tasksCreatedCounter.increment();
    }

    @EventListener
    public void onTaskCompleted(TareaCompletadaEvent event) {
        tasksCompletedCounter.increment();
    }

    private double calculateCompletionRatio() {
        double created = tasksCreatedCounter.count();
        double completed = tasksCompletedCounter.count();
        if (created == 0) {
            return 0.0;
        }
        double gaugeValue = (completed / created) * 100.0;
        return gaugeValue;
    }

    private void registerMetrics() {
        this.tasksCreatedCounter = Counter.builder("tasks_created_total")
                .description("Total number of tasks created")
                .register(registry);

        this.tasksCompletedCounter = Counter.builder("tasks_completed_total")
                .description("Total number of tasks completed")
                .register(registry);

        // Gauge for completion ratio
        Gauge.builder("tasks_completion_ratio", this, TaskMetricsEventListener::calculateCompletionRatio)
                .description("Ratio of completed tasks to created tasks (as percentage)")
                .register(registry);
    }

    public void resetMetrics() {
        registry.clear();
        registerMetrics();
    }

}