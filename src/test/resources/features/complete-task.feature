# language: es

@Completar
Característica: Completar tarea
  Como cliente de la API
  Quiero completar tareas existentes
  Para poder marcar tareas como completadas

  Escenario: Completar tarea exitosamente
    Dado un usuario autenticado "pepe" con permiso "write"
    Y una tarea existente con asunto "Tarea para completar"
    Cuando completa la tarea
    Entonces obtiene una respuesta exitosa
    Y la tarea tiene estado "completada"
    Y la tarea tiene fechaResolucion establecida

  Escenario: Completar tarea sin autenticación
    Dado un usuario anónimo
    Y una tarea existente con asunto "Tarea sin auth"
    Cuando completa la tarea
    Entonces obtiene una respuesta no autenticado

  Escenario: Completar tarea sin permiso write
    Dado un usuario autenticado "pepe" con permiso "read"
    Y una tarea existente con asunto "Tarea sin permisos"
    Cuando completa la tarea
    Entonces obtiene una respuesta no autorizada

  Escenario: Completar tarea que no existe
    Dado un usuario autenticado "pepe" con permiso "write"
    Cuando completa la tarea con uuid inexistente
    Entonces obtiene una respuesta de tarea no encontrada

  Escenario: Completar tarea de otro usuario
    Dado un usuario autenticado "pepe" con permiso "write"
    Y una tarea existente con asunto "Tarea de otro" del usuario "otro_usuario"
    Cuando completa la tarea
    Entonces obtiene una respuesta de tarea no encontrada

  Escenario: Completar tarea ya completada (idempotente)
    Dado un usuario autenticado "pepe" con permiso "write"
    Y una tarea existente completada con asunto "Tarea ya completada"
    Cuando completa la tarea
    Entonces obtiene una respuesta exitosa
    Y la tarea tiene estado "completada"
    Y la fechaResolucion no se modifica
