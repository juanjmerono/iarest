# Historial de Git del Proyecto

```mermaid
gitGraph
   commit id: "656dffc Initial import"
   commit id: "2a01153 Obtener tareas endpoint"
   commit id: "d12513c Proposal: Nuevo endpoint..."
   commit id: "e077048 Proposal implement..."
   commit id: "b9e1e22 Archive proposal..."
   commit id: "ce7df6f feat: add UUID..."
   commit id: "dac58f9 feat: add UUID identifier..."
   branch add-task-uuid
   checkout main
   commit id: "bad304f chore: archive..."
   checkout add-task-uuid
   commit id: "4991c52 feat: add proposal..."
   commit id: "40df901 feat: add multi-user..."
   checkout main
   merge add-task-uuid
   commit id: "c253446 Add prod db..."
   commit id: "cbd27a2 Fix auth in swagger"
   commit id: "cea3942 feat: añadir evento..."
   commit id: "1fcd252 feat: sincronizar..."
   branch feature/add-task-created-event
   checkout main
   commit id: "0b02551 feat: sincronizar spec..."
   checkout feature/add-task-created-event
   commit id: "cce34a3 Add to main specs"
   commit id: "c8c893a Add jacoco report"
   commit id: "57c6791 Add sonar-like report"
   checkout main
   merge feature/add-task-created-event
   commit id: "4358c69 Remove all ai info"
   commit id: "29b3b5b Minimal AI config"
   branch openspec
   commit id: "b00d500 OpenSpec init + basic..."
   commit id: "dc9ed28 Basic testing app..."
   commit id: "7a5ddee Add test to ensure..."
   commit id: "d5f6951 conductor(setup): Add..."
   checkout main
   commit id: "624a863 Add readme files"
   commit id: "6db28a2 Nueva propuesta..."
```

## Ramas del proyecto

- **main**: Rama principal con el código en producción
- **conductor**: Rama para configuración de conductor
- **openspec**: Rama para especificación abierta
- **scratch**: Rama de trabajo actual

## Último Commit

- **6db28a2**: Nueva propuesta de ejemplo
