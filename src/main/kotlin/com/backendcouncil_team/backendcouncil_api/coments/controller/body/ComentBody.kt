package com.backendcouncil_team.backendcouncil_api.coments.controller.body;

/**
 * Clase que simula el body, por sus datos; para uso general de comentarios.
 * @property clienteid Identificador del cliente autor del comentario.
 * @property incidenteid Identificador del incidente al cual se le relaciona el comentario.
 * @property contenido Contenido del comentario.
 */
class ComentBody (
    var clienteid: String,
    var incidenteid: String,
    var contenido: String,
    ){}
