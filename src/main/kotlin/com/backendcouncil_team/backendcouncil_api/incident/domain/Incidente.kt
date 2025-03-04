package com.backendcouncil_team.backendcouncil_api.incident.domain

import java.math.BigDecimal

data class Incidente (
    var incidenteid: Long? = null,
    var clienteid: Long,
    var categoriaid : Long = 246832,
    var nombre: String,
    var descripcion: String,
    var fecha:  String,
    var hora:  String,
    var latitud: BigDecimal,
    var longitud: BigDecimal,
    var estado: String
){}