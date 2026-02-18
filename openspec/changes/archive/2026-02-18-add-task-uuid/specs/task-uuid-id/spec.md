## ADDED Requirements

### Requirement: Identificador UUID para tareas
El sistema DEBE usar un identificador único universal (UUID) para cada tarea en lugar de un número secuencial.

#### Scenario: Crear tarea retorna UUID
- **WHEN** usuario crea una tarea exitosamente
- **THEN** la respuesta contiene un campo `uuid` con formato UUID válido

#### Scenario: Obtener tareas incluye UUID
- **WHEN** usuario obtiene la lista de tareas
- **THEN** cada tarea en la respuesta contiene el campo `uuid`

#### Scenario: UUID es único
- **WHEN** se crean múltiples tareas
- **THEN** cada tarea tiene un UUID diferente
