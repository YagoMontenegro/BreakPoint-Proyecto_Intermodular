-- CREACIÓN DE LA BASE DE DATOS

create database BreakPoint;


-- CREACIÓN DE LAS TABLAS

create table usuarios (
    id_usuario int not null auto_increment primary key,
    nombre varchar(50) not null,
    apellidos varchar(100) not null,
    email varchar(50) not null unique,
    telefono varchar(30) not null unique,
    fecha_registro datetime not null default current_timestamp
);

create table socios (
    codigo_socio int not null auto_increment primary key,
    id usuario int not null unique,
    fecha_alta datetime not null,
    fecha_baja datetime null,
    estado_socio enum ('Activa','Cancelada','Mantenimiento') default 'Activa' not null,
    constraint fk_socios_usuarios foreign key (id_usuario) references usuarios(id_usuario)
        on delete restrict 
        on update cascade
);

create table cuotas_socios (
    id_cuota int not null auto_increment primary key,
    id_socio int not null,
    fecha_pago datetime null,
    mes tinyint not null check (mes between 1 and 12),
    anio int not null,
    unique (id_socio, mes, anio),
    estado_cuota enum ('Pendiente','Pagada','Vencida') default 'Pendiente' not null,
    importe decimal(10,2) not null check (importe > 0)
    constraint fk_socios_cuotas_socios foreign key (id_socio) references socios (id_socio)
        on delete restrict
        on update cascade
);

create table reservas (
    id_reserva int not null auto_increment primary key,
    id_usuario int not null, 
    id_mesa int not null,
    hora_inicio datetime not null,
    hora_fin datetime not null,
    coste decimal(10,2) not null check (coste >= 0) default 0.00,
        -- Coste socio = 0.00€/h // Coste usuario 6€/h
        -- es necesario hacer una relacion con id_socio? cómo le hago saber en coste que socio es 0 pero usuario es X?
    estado_reserva enum ('Confirmada','Cancelada','Completada') default 'Confirmada' not null,
    constraint fk_reserva_usuario foreign key (id_usuario) references usuarios (id_usuario)
        on delete restrict
        on update cascade,
    constraint fk_reserva_mesa foreign key (id_mesa) references mesas (id_mesa)
        on delete restrict 
        on update cascade,
    constraint chck_horas check (hora_fin > hora_inicio)
        --si hora_fin es 17.00, permite que la siguiente hora_inicio sea 17.00?
);

create table mesas (
    id_mesa int not null auto_increment primary key,
    estado_mesa enum ('Disponible','Reservada','Mantenimiento') default 'Disponible' not null
);



