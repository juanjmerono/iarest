# OpenSpec - Guía de Uso

## Inicializar el sistema

Para inicializar las especificaciones de tu sistema puedes ejecutar `openspec init` que inicializará el directorio openspec y el fichero `config.yaml`, en el que se describen las características del proyecto.

## Inicializar un nuevo cambio

Para crear una nueva propuesta de cambio desde cero:

1. **Inicia el flujo**: Ejecuta el comando `/opsx-new` o describe lo que quieres construir.

2. **Responde a las preguntas**:
   - El sistema te preguntará qué cambio quieres realizar
   - Proporciona una descripción clara de la funcionalidad

3. **El sistema creará**:
   - Directorio del cambio en `openspec/changes/<nombre-del-cambio>/`
   - Estructura con artifacts vacíos (proposal, design, specs, tasks)

## Flujo de artifacts

El esquema `spec-driven` sigue esta secuencia:

| Artifact | Propósito |
|----------|-----------|
| `proposal.md` | Define el "qué" y "por qué" del cambio |
| `design.md` | Documenta decisiones técnicas |
| `specs/*.md` | Especificaciones detalladas de requerimientos (casos de uso) |
| `tasks.md` | Lista de tareas de implementación |

## Implementar el cambio

Una vez revisado el plan se puede solicitar la implementación con `/opsx-apply` que comenzará a realizar las tareas detalladas en la propuesta.

Para finalizar puedes sincronizar (incorporar) las especificaciones del cambio a las especificaciones generales del producto y archivar el cambio, que lo trasladará a un directorio de cambios archivados.

## Ejemplo de uso

```
> /opsx-new
> Quiero añadir la eliminación de tareas
> [El sistema crea el cambio y muestra el primer artifact]

> Si, continuamos (/opsx-continue)
> [El sistema crea proposal, luego design, luego specs, luego tasks]

> /opsx-apply
> [El sistema implementa las tareas definidas]

> /opsx-sync
> [El sistema incorpora los cambios a la especificación general del proyecto]

> /opsx-archive
> [El sistema archiva las especificaciones de cambios realizados]
```

## Explorar una idea

OpenSpec ofrece la posibilidad de explorar ideas, proponer soluciones sin modificar el proyecto mediante el comando `/opsx-explore`. Una vez hayas trabajado las ideas puedes iniciar una propuesta de cambio.
