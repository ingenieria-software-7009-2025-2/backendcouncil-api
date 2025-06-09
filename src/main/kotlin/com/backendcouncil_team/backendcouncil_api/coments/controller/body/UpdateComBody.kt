package com.backendcouncil_team.backendcouncil_api.coments.controller.body

/**
 * Clase que simula el body, por sus datos; para uso general de comentarios.
 * @property comentarioid Identificador propio del comentario.
 * @property incidenteid Identificador del incidente al cual se le relaciona el comentario.
 */
class UpdateComBody (
    var comentarioid: Long? = 0,
    var incidenteid: Long? = 0,
    ){}