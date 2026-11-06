-- =============================================
-- Script de creación de la base de datos
-- Gestión de Usuarios - Arquitectura Hexagonal
-- =============================================

CREATE DATABASE IF NOT EXISTS crud_usuarios
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE crud_usuarios;

CREATE TABLE IF NOT EXISTS users (
    id          VARCHAR(36)  NOT NULL PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    email       VARCHAR(150) NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    role        ENUM('ADMIN', 'MEMBER', 'REVIEWER') NOT NULL,
    status      ENUM('ACTIVE', 'INACTIVE', 'PENDING', 'BLOCKED') NOT NULL DEFAULT 'PENDING',
    created_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Usuario administrador inicial (password: Admin1234!)
INSERT INTO users (id, name, email, password, role, status)
VALUES (
    '00000000-0000-0000-0000-000000000001',
    'Administrador',
    'admin@example.com',
    '$2a$12$sfwkajsls.fktQb0Ngw5cOiSiV3z4LIwbucSAy4sw2wyNlxbUBt4q',
    'ADMIN',
    'ACTIVE'
);

-- =============================================
-- Tabla: vuelos
-- =============================================
CREATE TABLE IF NOT EXISTS vuelos (
    id                  VARCHAR(36)    NOT NULL PRIMARY KEY,
    fecha_compra        VARCHAR(50)    NOT NULL,
    fecha_salida        VARCHAR(50)    NOT NULL,
    fecha_llegada       VARCHAR(50)    NOT NULL,
    agencia_viajes      VARCHAR(150)   NOT NULL,
    aerolinea           VARCHAR(100)   NOT NULL,
    numero              VARCHAR(50)    NOT NULL,
    estado              VARCHAR(50)    NOT NULL,
    valor               DECIMAL(12, 2) NOT NULL,
    cliente             VARCHAR(150)   NOT NULL,
    puesto              VARCHAR(20)    NOT NULL,
    avion               VARCHAR(100)   NOT NULL,
    aereopuerto_salida  VARCHAR(150)   NOT NULL,
    aereopuerto_llegada VARCHAR(150)   NOT NULL,
    piloto              VARCHAR(150)   NOT NULL,
    usuario_id          VARCHAR(36)    NOT NULL,
    created_at          DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_vuelos_usuario_id (usuario_id),
    CONSTRAINT fk_vuelos_usuario FOREIGN KEY (usuario_id) REFERENCES users (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Vuelo inicial de ejemplo
INSERT INTO vuelos (
    id, fecha_compra, fecha_salida, fecha_llegada, agencia_viajes,
    aerolinea, numero, estado, valor, cliente, puesto, avion,
    aereopuerto_salida, aereopuerto_llegada, piloto, usuario_id
) VALUES (
    '001',
    '2026-11-06 10:00:00',
    '2026-11-10 08:30:00',
    '2026-11-10 10:45:00',
    'Despegar',
    'Avianca',
    'AV8520',
    'CONFIRMADO',
    450000.00,
    'Juan Perez',
    '12A',
    'Airbus A320',
    'Aeropuerto Internacional El Dorado (BOG)',
    'Aeropuerto Internacional Rafael Núñez (CTG)',
    'Capitán Carlos Ramírez',
    '00000000-0000-0000-0000-000000000001'
);

