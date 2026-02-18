## 1. Tests BDD (TDD - empezar con tests fallando)

- [x] 1.1 Actualizar feature `crear-tarea.feature` para verificar campo usuarioId
- [x] 1.2 Actualizar feature `obtener-tareas.feature` para verificar filtrado por usuario
- [x] 1.3 Crear scenario para verificar que usuario no ve tareas de otros

## 2. Capa de Dominio

- [x] 2.1 Modificar `Tarea.java` - añadir campo `usuarioId` de tipo `String`

## 3. Capa de Persistencia

- [x] 3.1 Modificar `TareaEntity.java` - añadir columna `USUARIO_ID`
- [x] 3.2 Modificar `TareaJpaRepository.java` - añadir método `findByUsuarioId`
- [x] 3.3 Modificar `TareaRepositoryAdapter.java` - añadir método `findByUsuarioId` y actualizar mapeo

## 4. Capa de Aplicación

- [x] 4.1 Modificar `CrearTareaCommand.java` - añadir campo usuarioId
- [x] 4.2 Modificar `CrearTareaCommandHandler.java` - recibir usuario del controller
- [x] 4.3 Modificar `ObtenerTareasQueryHandler.java` - filtrar por usuario
- [x] 4.4 Modificar `TodoResponse.java` - añadir campo usuarioId

## 5. Capa Adapter REST

- [x] 5.1 Modificar `TodoController` - pasar usuario del JWT al handler

## 6. Datos de Test

- [x] 6.1 Actualizar `test-data-todo-list.sql` - añadir usuarioId a los datos

## 7. Verificación

- [x] 7.1 Ejecutar `./mvnw test` y verificar que pasan
- [x] 7.2 Verificar con `./mvnw verify`
