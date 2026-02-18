# language: es

Característica: Obtener lista de tareas
  Como cliente de la API
  Quiero obtener la lista de tareas
  Para poder ver todas mis tareas pendientes con su información básica

  #Antecedentes:
  #  Dado que el sistema tiene datos de tareas disponibles

  Escenario: Obtener la lista de tareas no autenticado
    Dado un usuario anónimo
    Cuando consulta la lista de tareas
    Entonces obtiene una respuesta no autenticado

  Escenario: Obtener la lista de tareas autenticado sin permisos
    Dado un usuario autenticado "pepe" con permiso "ninguno"
    Cuando consulta la lista de tareas
    Entonces obtiene una respuesta no autorizada

  Escenario: Obtener la lista de tareas autenticado
    Dado un usuario autenticado "pepe" con permiso "read"
    Cuando consulta la lista de tareas
    Entonces obtiene una respuesta correcta
    Y una lista de tareas no vacía
    Y cada tarea contiene los campos: id, asunto, fecha, estado
