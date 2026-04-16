-- CONSULTAS - BreakPoint Club de Billar


USE BreakPoint;


-- 1. GESTIÓN DIARIA DE RECEPCIÓN

-- 1.1) Ver las mesas disponibles ahora mismo
SELECT id_mesa, estado_mesa
FROM mesas
WHERE estado_mesa = 'disponible'
ORDER BY id_mesa;

-- 1.2) Reservas de hoy: quién viene, a qué mesa y en qué horario
SELECT r.id_reserva,
       u.nombre,
       u.apellidos,
       r.id_mesa,
       r.hora_inicio,
       r.hora_fin,
       r.estado_reserva
FROM reservas r
JOIN usuarios u ON u.id_usuario = r.id_usuario
WHERE DATE(r.hora_inicio) = CURDATE()
ORDER BY r.hora_inicio;

-- 1.3) Buscar un usuario por teléfono o email. 
-- Introducir ambos valores o eliminar uno de ellos
-- Sustituir %email% por email indicado o --telefono-- por el telefono indicado
SELECT id_usuario, nombre, apellidos, email, telefono, fecha_registro
FROM usuarios
WHERE email      LIKE '%email%'
   OR telefono   LIKE --telefono--
ORDER BY apellidos, nombre;

-- 1.4) Comprobar si un usuario concreto es socio activo. Sustituir --telefono-- por el telf indicado
SELECT u.id_usuario,
       u.nombre,
       u.apellidos,
       s.estado_socio,
       s.fecha_alta,
       s.fecha_baja
FROM usuarios u
LEFT JOIN socios s ON s.id_usuario = u.id_usuario
WHERE u.telefono = --telefono--;


-- 2. GESTIÓN DE SOCIOS Y CUOTAS

-- 2.1) Consultar el histórico de cuotas por estado y telefono
          -- 'vencida' // 'pagada' // 'pendiente'
          -- Sustituir --telefono-- por el telf indicado
SELECT s.id_socio,
       u.nombre,
       u.apellidos,
       u.email,
       cs.mes,
       cs.anio,
       cs.importe
FROM cuotas_socios cs
JOIN socios s   ON s.id_socio = cs.id_socio
JOIN usuarios u ON u.id_usuario = s.id_usuario
WHERE cs.estado_cuota = 'pagada'
and u.telefono = --telefono--
ORDER BY cs.anio, cs.mes, u.apellidos;

-- 2.2) Recaudación total de cuotas cobradas por mes y año
-- Desglosado por año y mes
SELECT anio,
       mes,
       COUNT(*) AS cuotas_pagadas,
       SUM(importe) AS total_recaudado
FROM cuotas_socios
WHERE estado_cuota = 'pagada'
GROUP BY anio, mes
ORDER BY anio, mes;
-- Desglosado por total de cuotas pagadas por año
/*
SELECT anio,
       COUNT(*) AS cuotas_pagadas,
       SUM(importe) AS total_recaudado
FROM cuotas_socios
WHERE estado_cuota = 'pagada'
GROUP BY anio
ORDER BY anio;
*/

-- 2.3) Socios con 3 o más cuotas impagadas (candidatos a cancelación)
SELECT s.id_socio,
       u.nombre,
       u.apellidos,
       u.email,
       u.telefono,
       COUNT(*) AS cuotas_pendientes_o_vencidas
FROM cuotas_socios cs
JOIN socios s   ON s.id_socio = cs.id_socio
JOIN usuarios u ON u.id_usuario = s.id_usuario
WHERE cs.estado_cuota IN ('vencida','pendiente')
  AND s.estado_socio <> 'cancelado'
GROUP BY s.id_socio, u.nombre, u.apellidos, u.email, u.telefono
HAVING COUNT(*) >= 3
ORDER BY cuotas_pendientes_o_vencidas DESC;


-- 3. TORNEOS

-- 3.1) Torneos abiertos actualmente (inscripción disponible)
SELECT id_torneo,
       nombre,
       modalidad,
       fecha_inicio,
       max_participantes,
       premios
FROM torneos
WHERE estado_torneo = 'abierto'
ORDER BY fecha_inicio;

-- 3.2) Clasificación de un torneo finalizado
-- (Cambiar --id_torneo-- por el id_torneo que se quiera consultar)
SELECT i.resultado AS posicion,
       u.nombre,
       u.apellidos
FROM inscripciones i
JOIN socios s   ON s.id_socio = i.id_socio
JOIN usuarios u ON u.id_usuario = s.id_usuario
WHERE i.id_torneo = --id_torneo--
  AND i.resultado IS NOT NULL
ORDER BY i.resultado;

-- 3.3) Participantes apuntados a un torneo próximo
-- (Cambiar --id_torneo-- por el id_torneo que se quiera consultar)
SELECT u.nombre,
       u.apellidos,
       u.email,
       i.fecha_inscripcion
FROM inscripciones i
JOIN socios s   ON s.id_socio = i.id_socio
JOIN usuarios u ON u.id_usuario = s.id_usuario
WHERE i.id_torneo = --id_torneo--
ORDER BY i.fecha_inscripcion;

-- 3.4) Inscripciones y clasificacion de un torneo concreto con los datos del socio
-- (Cambiar --id_torneo-- por el id_torneo que se quiera consultar)
SELECT t.nombre AS torneo,
       u.nombre,
       u.apellidos,
       u.email,
       u.telefono,
       i.fecha_inscripcion,
       i.resultado
FROM inscripciones i
JOIN torneos t  ON t.id_torneo = i.id_torneo
JOIN socios s   ON s.id_socio = i.id_socio
JOIN usuarios u ON u.id_usuario = s.id_usuario
WHERE i.id_torneo = --id_torneo--
ORDER BY i.resultado, u.apellidos;


-- 4. RESERVAS

-- 4.1) Ingresos generados por reservas (solo no socios) en un periodo
-- (Ajustar fechas según se necesite)
SELECT COUNT(*) AS total_reservas,
       SUM(coste) AS ingresos_totales
FROM reservas
WHERE estado_reserva IN ('completada','confirmada')
  AND coste > 0
  AND hora_inicio BETWEEN '2025-01-01' AND '2025-12-31';

-- 4.2) Horas punta del club 
SELECT HOUR(hora_inicio) AS hora_del_dia,
       COUNT(*) AS numero_reservas
FROM reservas
WHERE estado_reserva <> 'cancelada'
GROUP BY HOUR(hora_inicio)
ORDER BY numero_reservas DESC;


-- 5. ANÁLISIS DE NEGOCIO

-- 5.1) Usuarios no socios con 5 o más reservas (candidatos para ofrecerles hacerse socios)
SELECT u.id_usuario,
       u.nombre,
       u.apellidos,
       u.email,
       u.telefono,
       COUNT(r.id_reserva) AS total_reservas
FROM usuarios u
JOIN reservas r ON r.id_usuario = u.id_usuario
LEFT JOIN socios s ON s.id_usuario = u.id_usuario
WHERE s.id_socio IS NULL
  AND r.estado_reserva IN ('completada','confirmada')
GROUP BY u.id_usuario, u.nombre, u.apellidos, u.email, u.telefono
HAVING COUNT(r.id_reserva) >= 5
ORDER BY total_reservas DESC;

