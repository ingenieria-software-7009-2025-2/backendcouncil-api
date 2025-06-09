package com.backendcouncil_team.backendcouncil_api.coments.domain;

import java.math.BigDecimal;

/**
 * Dominio de comentario propio para recabar los datos provenientes del modelo como instancias.
 * @property comentarioid ID del cliente.
 * @property clienteid ID del cliente autor.
 * @property incidente ID del incidente relacionado.
 * @property contenido Contenido del cometario.
 * @property likes Cantidad de likes del comentario.
 */
data class Comentario (
        var comentarioid: Long? = null,
        var clienteid: Long? = null,
        var incidente: Long? = null,
        var contenido: String,
        var likes: BigDecimal,
){}