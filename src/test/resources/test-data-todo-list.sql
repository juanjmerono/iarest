-- Script de datos de prueba para tests de lista de tareas
-- Tabla: TAREA

CREATE TABLE IF NOT EXISTS TAREA (
    UUID VARCHAR(36) PRIMARY KEY,
    ASUNTO VARCHAR(255) NOT NULL,
    FECHA TIMESTAMP NOT NULL,
    ESTADO VARCHAR(20) NOT NULL,
    USUARIO_ID VARCHAR(255) NOT NULL,
    FECHA_RESOLUCION TIMESTAMP
);

-- Datos de prueba para tests de obtener lista de tareas
-- user1 tiene 2 tareas, user2 tiene 1 tarea
INSERT INTO TAREA (UUID, ASUNTO, FECHA, ESTADO, USUARIO_ID, FECHA_RESOLUCION) VALUES 
    ('550e8400-e29b-41d4-a716-446655440000', 'Terminar informe mensual', TIMESTAMP '2025-02-20 10:00:00', 'pendiente', 'user1', NULL),
    ('550e8400-e29b-41d4-a716-446655440001', 'Revisar código del módulo auth', TIMESTAMP '2025-02-18 14:30:00', 'en_progreso', 'user1', NULL),
    ('550e8400-e29b-41d4-a716-446655440002', 'Actualizar documentación API', TIMESTAMP '2025-02-15 09:00:00', 'completada', 'user2', TIMESTAMP '2025-02-15 16:00:00');