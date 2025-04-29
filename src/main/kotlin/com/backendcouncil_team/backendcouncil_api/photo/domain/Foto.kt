package com.backendcouncil_team.backendcouncil_api.photo.domain

import com.backendcouncil_team.backendcouncil_api.photo.repository.entity.Photo
import org.springframework.data.jpa.repository.Query

data class Foto(
    var fotoid: ByteArray? = null,
    var incidenteid: Long? = null
) {
}