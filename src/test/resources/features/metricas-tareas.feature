# language: es

@Metricas
Característica: Métricas de tareas en Prometheus
  Como cliente de la API
  Quiero consultar estadísticas de uso
  Para poder conocer ratio de tareas creadas y completadas

  Escenario: Incremento del contador de tareas creadas
    Dado que no hay tareas existentes
    Dado un usuario autenticado "pepe" con permiso "write"
    Cuando crea una tarea con asunto "Tarea de prueba"
    Entonces el contador "tasks_created_total" debe incrementarse en 1

  Escenario: Incremento del contador de tareas completadas
    Dado un usuario autenticado "pepe" con permiso "write"
    Dado que existe una tarea pendiente con asunto "Tarea pendiente"
    Cuando marco la tarea como completada
    Entonces el contador "tasks_completed_total" debe incrementarse en 1

  Escenario: Cálculo del ratio de completación
    Dado que se han creado 3 tareas
    Y se han completado 2 tareas
    Entonces el gauge "tasks_completion_ratio" debe mostrar aproximadamente 66.67

  Escenario: Ratio cero cuando no hay tareas creadas
    Dado que no hay tareas existentes
    Entonces el gauge "tasks_completion_ratio" debe ser 0