-- INSERCION DE DATOS

    -- fecha_modificacion DATETIME DEFAULT CURRENT_TIMESTAMP 
    -- ON UPDATE CURRENT_TIMESTAMP 
        -- Se guarda el momento exacto de cuando se haga un update

    --USUARIOS
        -- 8 ejemplos de usuario
insert into usuarios (nombre, apellidos, email, telefono, fecha_registro) values 
('Carlos',    'Fernández López',   'carlos.fernandez@email.com',  '600111222', '2024-01-10 10:00:00'),
('Laura',     'Gómez Martínez',    'laura.gomez@email.com',       '600333444', '2024-01-15 11:30:00'),
('Sergio',    'Ramos Díaz',        'sergio.ramos@email.com',      '600555666', '2024-02-01 09:00:00'),
('Ana',       'Torres Vidal',      'ana.torres@email.com',        '600777888', '2024-02-14 16:00:00'),
('Miguel',    'Pérez Sánchez',     'miguel.perez@email.com',      '600999000', '2024-03-05 12:00:00'),
('Patricia',  'Navarro Ruiz',      'patricia.navarro@email.com',  '601000111', '2024-03-20 10:30:00'),
('Javier',    'Molina Castro',     'javier.molina@email.com',     '601222333', '2024-04-01 09:00:00'),
('Beatriz',   'Ortega Fuentes',    'beatriz.ortega@email.com',    '601444555', '2024-05-10 17:00:00');

    --SOCIOS
        -- 5 ejemplos donde contemplamos todas las opciones:
            -- activo: Carlos, Laura, Sergio
            -- mantenimiento: Ana
            -- cancelado: Miguel
insert into socios (id_usuario, fecha_alta, fecha_baja, estado_socio) values 
(1, '2024-01-10 10:00:00', NULL,                    'activo'),        -- Carlos
(2, '2024-01-15 11:30:00', NULL,                    'activo'),        -- Laura
(3, '2024-02-01 09:00:00', NULL,                    'activo'),        -- Sergio
(4, '2024-02-14 16:00:00', NULL,                    'mantenimiento'), -- Ana
(5, '2024-03-05 12:00:00', '2024-11-01 00:00:00',   'cancelado');     -- Miguel


    --COUTAS_SOCIOS
        --  Al menos una cuota de cada estado_socio:
            -- activo: 30.00€
            -- mantenimiento: 5.00€
            -- cancelado: 0.00€ (se refleja el historico antes de cancelar)
-- Carlos (id_socio=1, activo, 30€)
INSERT INTO cuotas_socios (id_socio, fecha_pago, mes, anio, estado_cuota, importe) VALUES
(1, '2025-01-05 10:00:00', 1,  2025, 'pagada',   30.00),
(1, '2025-02-03 10:00:00', 2,  2025, 'pagada',   30.00),
(1, NULL,                  3,  2025, 'pendiente', 30.00);

-- Laura (id_socio=2, activo, 30€)
INSERT INTO cuotas_socios (id_socio, fecha_pago, mes, anio, estado_cuota, importe) VALUES
(2, '2025-01-06 09:00:00', 1,  2025, 'pagada',   30.00),
(2, NULL,                  2,  2025, 'vencida',  30.00),  -- no pagó febrero
(2, NULL,                  3,  2025, 'pendiente', 30.00);

-- Sergio (id_socio=3, activo, 30€)
INSERT INTO cuotas_socios (id_socio, fecha_pago, mes, anio, estado_cuota, importe) VALUES
(3, '2025-01-08 11:00:00', 1,  2025, 'pagada',   30.00),
(3, '2025-02-07 11:00:00', 2,  2025, 'pagada',   30.00),
(3, '2025-03-06 11:00:00', 3,  2025, 'pagada',   30.00);

-- Ana (id_socio=4, mantenimiento, 5€)
INSERT INTO cuotas_socios (id_socio, fecha_pago, mes, anio, estado_cuota, importe) VALUES
(4, '2025-01-10 12:00:00', 1,  2025, 'pagada',    5.00),
(4, NULL,                  2,  2025, 'vencida',   5.00),  -- venció sin pagar
(4, NULL,                  3,  2025, 'pendiente',  5.00);

-- Miguel (id_socio=5, cancelado — solo cuotas históricas de cuando era activo)
INSERT INTO cuotas_socios (id_socio, fecha_pago, mes, anio, estado_cuota, importe) VALUES
(5, '2024-03-10 10:00:00', 3,  2024, 'pagada',   30.00),
(5, '2024-04-05 10:00:00', 4,  2024, 'pagada',   30.00),
(5, NULL,                  10, 2024, 'vencida',  30.00);  -- no pagó antes de cancelar


    --MESAS
insert into mesas (estado_mesa) values
('disponible'),
('disponible'),
('reservada'),
('disponible'),
('mantenimiento'),
--('disponible'),
--('mantenimiento'),
--('mantenimiento'),
--('mantenimiento');


    --TORNEOS
INSERT INTO torneos (nombre, modalidad, fecha_inicio, fecha_fin, max_participantes, premios, estado_torneo) VALUES
('Torneo de Invierno 2025', 'bola_8',  '2025-01-15 10:00:00', '2025-01-16 20:00:00', 8,  'Taco profesional + trofeo',          'finalizado'),
('Copa BreakPoint 2025',    'bola_9',  '2025-03-01 10:00:00', NULL,                  16, 'Premio en metálico 200€',            'en_curso'),
('Torneo de Primavera 2025','bola_10', '2025-05-01 10:00:00', NULL,                  12, 'Entrada + Hotel Campeonato de España 2025',    'abierto');


    --INSCRIPCIONES
-- Torneo de Invierno (finalizado) 
INSERT INTO inscripciones (id_socio, id_torneo, fecha_inscripcion, resultado) VALUES
(1, 1, '2025-01-10 10:00:00', 1),   -- Carlos,  1er puesto
(3, 1, '2025-01-11 09:00:00', 2),   -- Sergio,  2º puesto
(2, 1, '2025-01-12 11:00:00', 3);   -- Laura,   3er puesto

-- Copa BreakPoint (en curso) 
INSERT INTO inscripciones (id_socio, id_torneo, fecha_inscripcion, resultado) VALUES
(1, 2, '2025-02-20 10:00:00', NULL),
(2, 2, '2025-02-21 11:00:00', NULL),
(3, 2, '2025-02-22 09:00:00', NULL),

-- Torneo de Primavera (abierto) 
INSERT INTO inscripciones (id_socio, id_torneo, fecha_inscripcion, resultado) VALUES
(1, 3, '2025-03-15 10:00:00', NULL);


    --  RESERVAS
-- Confirmadas (futuras o en curso)
INSERT INTO reservas (id_usuario, id_mesa, hora_inicio, hora_fin, coste, estado_reserva) VALUES
(1, 2, '2025-04-01 10:00:00', '2025-04-01 12:00:00', 0.00,  'confirmada'),  -- Carlos, socio
(3, 1, '2025-04-01 16:00:00', '2025-04-01 18:00:00', 0.00,  'confirmada'),  -- Sergio, socio
(6, 4, '2025-04-02 18:00:00', '2025-04-02 20:00:00', 12.00, 'confirmada'),  -- Patricia, usuario (6€*2h)
(7, 3, '2025-04-03 10:00:00', '2025-04-03 11:00:00', 6.00,  'confirmada'),  -- Javier, usuario (6€*1h)

-- Completadas
INSERT INTO reservas (id_usuario, id_mesa, hora_inicio, hora_fin, coste, estado_reserva) VALUES
(1, 1, '2025-03-01 10:00:00', '2025-03-01 12:00:00', 0.00,  'completada'),  -- Carlos, socio
(2, 2, '2025-03-01 11:00:00', '2025-03-01 13:00:00', 0.00,  'completada'),  -- Laura, socio
(6, 3, '2025-03-02 16:00:00', '2025-03-02 17:30:00', 9.00,  'completada'),  -- Patricia, usuario (6€*1.5h)
(7, 4, '2025-03-03 18:00:00', '2025-03-03 20:00:00', 12.00, 'completada'),  -- Javier, usuario (6€*2h)
(3, 1, '2025-03-05 10:00:00', '2025-03-05 12:00:00', 0.00,  'completada'),  -- Sergio, socio

-- Canceladas
INSERT INTO reservas (id_usuario, id_mesa, hora_inicio, hora_fin, coste, estado_reserva) VALUES
(8, 2, '2025-03-10 17:00:00', '2025-03-10 19:00:00', 12.00, 'cancelada'),   -- Beatriz, usuario
(2, 3, '2025-03-12 10:00:00', '2025-03-12 11:00:00', 0.00,  'cancelada'),   -- Laura, socio

