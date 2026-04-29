-- CREACIÓN DE LA BASE DE DATOS

drop database if exists breakpoint;
create database breakpoint;
use breakpoint;


-- CREACIÓN DE LAS TABLAS

create table usuarios (
    id_usuario int auto_increment primary key,
    nombre varchar(50) not null,
    apellidos varchar(100) not null,
    email varchar(50) not null unique,
    telefono varchar(30) not null unique,
    fecha_registro datetime not null default current_timestamp
);

create table mesas (
    id_mesa int auto_increment primary key,
    estado_mesa enum ('disponible','reservada','mantenimiento') default 'disponible' not null
);

create table socios (
    id_socio int auto_increment primary key,
    id_usuario int not null unique,
    fecha_alta datetime not null,
    fecha_baja datetime null,
    estado_socio enum ('activo','cancelado','mantenimiento') default 'activo' not null,
        -- activo = 30€/mes // mantenimiento = 5€/mes // cancelado = 0€/mes
    constraint fk_socios_usuarios foreign key (id_usuario) references usuarios(id_usuario)
        on delete restrict 
        on update cascade    
);

create table cuotas_socios (
    id_cuota int auto_increment primary key,
    id_socio int not null,
    fecha_pago datetime null,
    mes int not null check (mes between 1 and 12),
    anio int not null,
    estado_cuota enum ('pendiente','pagada','vencida') default 'pendiente' not null,
    importe decimal(10,2) not null default 30.00 check (importe > 0),
    constraint cuota_unica unique (id_socio, mes, anio),
    constraint fk_socios_cuotas_socios foreign key (id_socio) references socios (id_socio)
        on delete restrict
        on update cascade
);

create table torneos (
    id_torneo int auto_increment primary key,
    nombre varchar(100) not null,
    modalidad enum ('bola_8','bola_9','bola_10') null,
    fecha_inicio datetime not null,
    fecha_fin datetime null,
        -- la fecha_fin es null hasta que finaliza el torneo
    max_participantes int not null,
    premios varchar(250) not null,
    estado_torneo enum ('abierto','en_curso','finalizado') not null default 'abierto',    
    constraint chck_participantes check (max_participantes > 0),
    constraint chck_fechas_torneo check (fecha_fin > fecha_inicio)
);

create table reservas (
    id_usuario int not null, 
    id_mesa int not null,
    hora_inicio datetime not null,
    hora_fin datetime not null,
    coste decimal(10,2) not null default 0.00 check (coste >= 0),
        -- Coste socio = 0.00€/h // Coste usuario 6€/h
        -- el coste se determina en la aplicación comprobando si id_usuario tiene socio activo
    estado_reserva enum ('confirmada','cancelada','completada') default 'confirmada' not null,
    primary key (id_usuario, id_mesa, hora_inicio),
    constraint fk_reserva_usuario foreign key (id_usuario) references usuarios (id_usuario)
        on delete restrict
        on update cascade,
    constraint fk_reserva_mesa foreign key (id_mesa) references mesas (id_mesa)
        on delete restrict 
        on update cascade,
    constraint chck_horas check (hora_fin > hora_inicio)
);

create table inscripciones (
    id_socio int not null,
    id_torneo int not null,
    fecha_inscripcion datetime not null default current_timestamp,
    resultado int null, 
        -- el resultado es null hasta que finaliza el torneo
    primary key (id_socio, id_torneo),
    constraint fk_inscripcion_socio foreign key (id_socio) references socios (id_socio)
        on delete restrict
        on update cascade,
    constraint fk_inscripcion_torneo foreign key (id_torneo) references torneos (id_torneo)
        on delete restrict
        on update cascade
);
