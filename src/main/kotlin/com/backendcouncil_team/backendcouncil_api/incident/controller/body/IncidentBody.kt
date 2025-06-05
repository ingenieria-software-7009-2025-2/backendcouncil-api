package com.backendcouncil_team.backendcouncil_api.incident.controller.body

import java.math.BigDecimal

/**
 * Clase que simula el body, por sus datos; para uso general de un incidente.
 * @property token token del registrador
 * @property nombre Nombre del incidente.
 * @property descripcion Descripción del incidente.
 * @property fecha Fecha del incidente.
 * @property hora Hora del incidente.
 * @property latitud Latitud del incidente.
 * @property longitud Longitud del incidente.
 */
data class IncidentBody (
    var token: String,
    var nombre: String,
    var descripcion: String,
    var fecha:  String,
    var hora:  String,
    var latitud:  BigDecimal,
    var longitud:  BigDecimal,
){}