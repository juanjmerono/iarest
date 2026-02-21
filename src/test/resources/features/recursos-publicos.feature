# language: es

@RecursosPublicos
Característica: Recursos públicos estáticos
  Como usuario no autenticado
  Quiero acceder a los recursos estáticos públicos
  Para poder utilizar la aplicación sin iniciar sesión

  Esquema del escenario: Acceso a recursos públicos sin autenticación
    Dado un usuario anónimo
    Cuando accede al recurso "<recurso>"
    Entonces obtiene una respuesta correcta
    Y el contenido es HTML

    Ejemplos:
      | recurso      |
      | /login.html  |
      | /callback.html |
      | /app.html    |
