package com.backendcouncil_team.backendcouncil_api.incident.domain

import java.math.BigDecimal

/**
 * Dominio de incidente propio para recabar los datos provenientes del modelo como instancias.
 * @property incidenteid ID del incidente
 * @property clienteid ID del reportador.
 * @property categoriaid ID de la categoría del incidente.
 * @property nombre Nombre del incidente.
 * @property descripcion Descripción del incidente.
 * @property fecha Fecha del incidente.
 * @property hora Hora del incidente.
 * @property latitud Latitud del incidente.
 * @property longitud Longitud del incidente.
 */
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
    var estado: Estado,
    var likes: BigDecimal
){}