package database;

public interface SchemaBBDD {

    // TABLA USUARIOS
    String TAB_USUARIO = "usuarios";
    String COL_ID_USUARIO = "id_usuario";
    String COL_NOMBRE = "nombre";
    String COL_APELLIDOS = "apellidos";
    String COL_EMAIL = "email";
    String COL_TELEFONO = "telefono";
    String COL_FECHA_REGISTRO = "fecha_registro";

    // TABLA MESAS
    String TAB_MESA = "mesas";
    String COL_ID_MESA = "id_mesa";
    String COL_ESTADO_MESA = "estado_mesa";

    // TABLA SOCIOS
    String TAB_SOCIO = "socios";
    String COL_ID_SOCIO = "id_socio";
    String COL_ID_USUARIO_FK = "id_usuario";
    String COL_FECHA_ALTA = "fecha_alta";
    String COL_FECHA_BAJA = "fecha_baja";
    String COL_ESTADO_SOCIO = "estado_socio";

    // TABLA CUOTAS_SOCIOS
    String TAB_CUOTA_SOCIO = "cuotas_socios";
    String COL_ID_CUOTA = "id_cuota";
    String COL_ID_SOCIO_FK = "id_socio";
    String COL_FECHA_PAGO = "fecha_pago";
    String COL_MES = "mes";
    String COL_ANIO = "anio";
    String COL_ESTADO_CUOTA = "estado_cuota";
    String COL_IMPORTE = "importe";

    // TABLA TORNEOS
    String TAB_TORNEO = "torneos";
    String COL_ID_TORNEO = "id_torneo";
    String COL_MODALIDAD = "modalidad";
    String COL_FECHA_INICIO = "fecha_inicio";
    String COL_FECHA_FIN = "fecha_fin";
    String COL_MAX_PARTICIPANTES = "max_participantes";
    String COL_PREMIOS = "premios";
    String COL_ESTADO_TORNEO = "estado_torneo";

    // TABLA RESERVAS
    String TAB_RESERVA = "reservas";
    String COL_ID_USUARIO_RESERVA_FK = "id_usuario";
    String COL_ID_MESA_FK = "id_mesa";
    String COL_HORA_INICIO = "hora_inicio";
    String COL_HORA_FIN = "hora_fin";
    String COL_COSTE = "coste";
    String COL_ESTADO_RESERVA = "estado_reserva";

    // TABLA INSCRIPCIONES
    String TAB_INSCRIPCION = "inscripciones";
    String COL_ID_SOCIO_INS_FK = "id_socio";
    String COL_ID_TORNEO_FK = "id_torneo";
    String COL_FECHA_INSCRIPCION = "fecha_inscripcion";
    String COL_RESULTADO = "resultado";
}
