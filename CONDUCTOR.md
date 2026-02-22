# CONDUCTOR.md - Guía de la Extensión Conductor

Este archivo detalla cómo inicializar y gestionar cambios en el proyecto utilizando la extensión **Conductor**. Conductor organiza el desarrollo en "tracks" (vías) con planes de implementación y especificaciones claras.

## Inicializar el Proyecto

Para inicializar conductor en un repositorio, lanzamos dentro de gemini-cli `/conductor:setup` que nos guía en el proceso principal de creación de los archivos principales de conductor.

- `conductor/index.md`: El índice principal que enlaza a los documentos del proyecto.
- `conductor/product.md`: Definición del producto y objetivos.
- `conductor/tech-stack.md`: Descripción de la pila tecnológica utilizada.
- `conductor/workflow.md`: Definición del flujo de trabajo y convenciones.
- `conductor/product-guidelines.md`: Guías y principios del producto.
- `conductor/tracks.md`: El registro de todos los tracks activos y finalizados.

## Cómo Crear Cambios (Tracks)

Cada cambio significativo o funcionalidad debe gestionarse mediante un **Track**, lanzando el comando `/conductor:newTrack` que crea los archivos:

1. **Directorio del Track**: `conductor/tracks/<track_id>/`
2. **Definir la Especificación**: Crea `conductor/tracks/<track_id>/spec.md` detallando el "qué" y el "por qué".
3. **Crear el Plan de Implementación**: Crea `conductor/tracks/<track_id>/plan.md` con los pasos técnicos detallados.
4. **Metadatos**: Incluye un `metadata.json` con información técnica del track.
5. **Registrar**: Añade el nuevo track al archivo `conductor/tracks.md`.

### Implementar los cambios

Una vez revises el plan y confirmes que se ajusta a lo que consideras que debe hacerse para solucionar el cambio propuesto puedes pasar a implementar `/conductor:implement`.

