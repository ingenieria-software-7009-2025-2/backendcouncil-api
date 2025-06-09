package com.backendcouncil_team.backendcouncil_api.incident.controller.body

/**
 * Clase que simula el body, por sus datos; para us particular de la obtención de incidentes filtrando por autor.
 * @property clienteid Identificador del autor del cual se buscan los incidentes.
 */
data class GetBody (
    var clienteid: Long,
){}