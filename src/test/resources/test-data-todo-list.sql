-- Script de datos de prueba para tests de lista de tareas
-- Tabla: TAREA

CREATE TABLE IF NOT EXISTS TAREA (
    UUID VARCHAR(36) PRIMARY KEY,
    ASUNTO VARCHAR(255) NOT NULL,
    FECHA DATE NOT NULL,
    ESTADO VARCHAR(20) NOT NULL,
    USUARIO_ID VARCHAR(255) NOT NULL
);

-- Datos de prueba para tests de obtener lista de tareas
-- user1 tiene 2 tareas, user2 tiene 1 tarea
INSERT INTO TAREA (UUID, ASUNTO, FECHA, ESTADO, USUARIO_ID) VALUES 
    ('550e8400-e29b-41d4-a716-446655440000', 'Terminar informe mensual', DATE '2025-02-20', 'pendiente', 'user1'),
    ('550e8400-e29b-41d4-a716-446655440001', 'Revisar código del módulo auth', DATE '2025-02-18', 'en_progreso', 'user1'),
    ('550e8400-e29b-41d4-a716-446655440002', 'Actualizar documentación API', DATE '2025-02-15', 'completada', 'user2');