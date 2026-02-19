# language: es

@OpenAPI
Característica: Obtener documentación openapi
  Como cliente de la API
  Quiero obtener la documentación openapi
  Para poder utilizarla correctamente

  Antecedentes:
    Dado que el sistema tiene documentación openapi disponible

  Escenario: Endpoint de lista de tareas disponible
    Cuando el cliente accede a la documentación OpenAPI
    Entonces consulta la documentación openapi
    Y se describe el endpoint "/example/demo/todos"
