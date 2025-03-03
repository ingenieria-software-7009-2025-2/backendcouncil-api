package com.backendcouncil_team.backendcouncil_api.incident.controller.body

import ch.qos.logback.core.subst.Token
import java.math.BigDecimal

data class IncidentBody (
    var token: String,
    var nombre: String,
    var descripcion: String,
    var fecha:  String,
    var hora:  String,
    var latitud:  BigDecimal,
    var longitud:  BigDecimal,
){}