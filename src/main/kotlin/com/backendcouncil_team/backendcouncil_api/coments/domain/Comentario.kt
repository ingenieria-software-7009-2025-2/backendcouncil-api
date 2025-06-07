package com.backendcouncil_team.backendcouncil_api.coments.domain;

import java.math.BigDecimal;

data class Comentario (
        var comentarioid: Long? = null,
        var clienteid: Long? = null,
        var incidente: Long? = null,
        var contenido: String,
        var likes: BigDecimal,
){}