# Initial Concept
API Rest para gestión de tareas centrada en la interacción con agentes IA.

# Product Definition - IA API Rest

## Project Overview
**IA API Rest** es un proyecto de demostración y aprendizaje centrado en la implementación de APIs REST utilizando tecnologías modernas de Java (Spring Boot 4.0.2) y patrones arquitectónicos avanzados (Arquitectura Hexagonal + CQRS). Su propósito principal es servir como laboratorio para el desarrollo asistido por agentes de IA, optimizando la estructura del código y la documentación para facilitar la interacción con modelos de lenguaje.

## Target Audience
- **Desarrolladores y Practicantes de IA**: Usuarios que buscan entender cómo estructurar proyectos para que sean fácilmente navegables y modificables por agentes de IA.
- **Arquitectos de Software y Estudiantes**: Personas interesadas en ejemplos prácticos de Arquitectura Hexagonal y patrones CQRS en el ecosistema Spring.

## Core Value Proposition (Differentiators)
1. **AI Agent-friendly Architecture**: El código está organizado de manera lógica y modular (CQRS), lo que reduce la carga cognitiva para los agentes de IA al analizar el impacto de los cambios.
2. **Pureza Arquitectónica**: Estricta separación entre el dominio, la aplicación y los adaptadores de infraestructura.
3. **Enfoque en BDD**: Las pruebas de aceptación con Cucumber garantizan que el comportamiento del negocio esté siempre validado.

## Functional Goals
- **Gestión de Tareas (MVP)**: API completa para la creación, consulta y actualización de tareas (TODO list).
- **API Autodocumentada**: Uso extensivo de OpenAPI (Swagger) para proporcionar metadatos claros tanto a humanos como a agentes.
- **Monitoreo Preparado para Producción**: Integración con Micrometer y Prometheus para la recolección de métricas operacionales.

## API Strategy
- **Madurez HATEOAS (Nivel 3)**: El API utiliza enlaces hipermedia para mejorar la descubribilidad de recursos y acciones disponibles.
