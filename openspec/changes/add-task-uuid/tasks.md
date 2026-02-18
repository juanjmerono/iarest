## 1. Tests BDD (TDD - empezar con tests fallando)

- [x] 1.1 Actualizar archivo feature `crear-tarea.feature` para verificar campo `uuid`
- [x] 1.2 Actualizar archivo feature `obtener-tareas.feature` para verificar campo `uuid`

## 2. Capa de Dominio

- [x] 2.1 Modificar `Tarea.java` - añadir campo `uuid` de tipo `String`
- [x] 2.2 Modificar `TareaRepository.java` - añadir método `findByUuid()` si es necesario

## 3. Capa de Persistencia

- [x] 3.1 Modificar `TareaEntity.java` - cambiar `id` a `uuid`, cambiar generación
- [x] 3.2 Modificar `TareaRepositoryAdapter.java` - actualizar mapeo

## 4. Capa de Aplicación

- [x] 4.1 Modificar `CrearTareaCommand.java` - generar UUID al crear
- [x] 4.2 Modificar `TodoResponse.java` - cambiar `id` a `uuid`

## 5. Capa Adapter REST

- [x] 5.1 Actualizar `TodoModelAssembler.java` - actualizar enlaces HATEOAS

## 6. Verificación

- [x] 6.1 Ejecutar `./mvnw test` y verificar que pasan
- [x] 6.2 Verificar con `./mvnw verify`
