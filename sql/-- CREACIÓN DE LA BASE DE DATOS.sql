-- CREACIÓN DE LA BASE DE DATOS

create database BreakPoint;


-- CREACIÓN DE LAS TABLAS

create table usuarios (
    id_usuario int unsigned auto_increment primary key,
    nombre varchar(50) not null,
    apellidos varchar(100) not null,
    email varchar(50) not null unique,
    telefono varchar(30) not null unique,
    fecha_registro datetime not null default current_timestamp
);

create table mesas (
    id_mesa tinyint unsigned auto_increment primary key,
    estado_mesa enum ('disponible','reservada','mantenimiento') default 'disponible' not null
);

create table socios (
    id_socio int unsigned auto_increment primary key,
    id_usuario int unsigned not null unique,
    fecha_alta datetime not null,
    fecha_baja datetime null,
    estado_socio enum ('activo','cancelado','mantenimiento') default 'activo' not null,
        -- activo = 30€/mes // mantenimiento = 5€/mes // cancelado = 0€/mes
    constraint fk_socios_usuarios foreign key (id_usuario) references usuarios(id_usuario)
        on delete restrict 
        on update cascade    
);

create table cuotas_socios (
    id_cuota int unsigned auto_increment primary key,
    id_socio int unsigned not null,
    fecha_pago datetime null,
    mes tinyint not null check (mes between 1 and 12),
    anio int unsigned not null,
    estado_cuota enum ('pendiente','pagada','vencida') default 'pendiente' not null,
    importe decimal(10,2) not null check (importe > 0) default 30.00,
    constraint cuota_unica unique (id_socio, mes, anio),
    constraint fk_socios_cuotas_socios foreign key (id_socio) references socios (id_socio)
        on delete restrict
        on update cascade
);

create table torneos (
    id_torneo int unsigned auto_increment primary key,
    nombre varchar(100) not null,
    modalidad enum ('bola_8','bola_9','bola_10') null,
    fecha_inicio datetime not null,
    fecha_fin datetime null,
        -- la fecha_fin es null hasta que finaliza el torneo
    max_participantes tinyint unsigned not null,
    premios text(250) not null,
    estado_torneo enum ('abierto','en_curso','finalizado') not null default 'abierto',    
    constraint chck_participantes check (max_participantes > 0),
    constraint chck_fechas_torneo check (fecha_fin > fecha_inicio)
);

create table reservas (
    id_reserva int unsigned auto_increment primary key,
    id_usuario int unsigned not null, 
    id_mesa int unsigned not null,
    hora_inicio datetime not null,
    hora_fin datetime not null,
    coste decimal(10,2) not null check (coste >= 0) default 0.00,
        -- Coste socio = 0.00€/h // Coste usuario 6€/h
        -- es necesario hacer una relacion con id_socio? cómo le hago saber en coste que socio es 0 pero usuario es X?
    estado_reserva enum ('confirmada','cancelada','completada') default 'confirmada' not null,
    constraint fk_reserva_usuario foreign key (id_usuario) references usuarios (id_usuario)
        on delete restrict
        on update cascade,
    constraint fk_reserva_mesa foreign key (id_mesa) references mesas (id_mesa)
        on delete restrict 
        on update cascade,
    constraint chck_horas check (hora_fin > hora_inicio)
        --si hora_fin es 17.00, permite que la siguiente hora_inicio sea 17.00?
);

create table inscripciones (
    id_inscripcion int unsigned auto_increment primary key,
    id_socio int unsigned not null,
    id_torneo int unsigned not null,
    fecha_inscripcion datetime not null default current_timestamp,
    resultado int null, 
        -- el resultado es null hasta que finaliza el torneo
    constraint fk_inscripcion_socio foreign key (id_socio) references socios (id_socio)
        on delete restrict
        on update cascade,
    constraint fk_inscripcion_torneo foreign key (id_torneo) references torneos (id_torneo)
        on delete restrict
        on update cascade,
    constraint inscrip_unica unique (id_socio, id_torneo)
);
