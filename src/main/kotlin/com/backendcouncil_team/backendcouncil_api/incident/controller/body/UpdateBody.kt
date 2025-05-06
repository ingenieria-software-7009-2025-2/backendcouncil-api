package com.backendcouncil_team.backendcouncil_api.incident.controller.body

/**
 * Clase que simula el body, por sus datos; para la actualización de datos de un incidente.
 * @property incidenteid Identificador del incidente.
 * @property estatus Estado del incidente.
 */
data class UpdateBody (
    var incidenteid : Long? = 0,
    var estatus : String

){}