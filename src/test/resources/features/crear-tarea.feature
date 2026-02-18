# language: es

Característica: Crear nueva tarea
  Como cliente de la API
  Quiero crear nuevas tareas en la lista
  Para poder añadir tareas pendientes a mi lista

  Escenario: Crear tarea exitosamente
    Dado un usuario autenticado "pepe" con permiso "write"
    Cuando crea una tarea con asunto "Nueva tarea de prueba"
    Entonces obtiene una respuesta de creación exitosa
    Y la tarea contiene el asunto "Nueva tarea de prueba"
    Y la tarea tiene estado "pendiente"
    Y la tarea tiene fecha actual
    Y la tarea tiene un uuid válido
    Y la tarea tiene el usuarioId del usuario autenticado

  Escenario: Crear tarea sin autenticación
    Dado un usuario anónimo
    Cuando crea una tarea con asunto "Tarea sin auth"
    Entonces obtiene una respuesta no autenticado

  Escenario: Crear tarea sin scope write
    Dado un usuario autenticado "pepe" con permiso "read"
    Cuando crea una tarea con asunto "Tarea sin permisos"
    Entonces obtiene una respuesta no autorizada

  Escenario: Crear tarea con asunto vacío
    Dado un usuario autenticado "pepe" con permiso "write"
    Cuando crea una tarea con asunto ""
    Entonces obtiene una respuesta de error de validación
