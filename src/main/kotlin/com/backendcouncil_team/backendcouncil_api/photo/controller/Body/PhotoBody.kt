package com.backendcouncil_team.backendcouncil_api.photo.controller.Body

import com.backendcouncil_team.backendcouncil_api.incident.domain.Incidente

data class PhotoBody (
    var fotoid: ByteArray,
    var incidenteid: String = ""
) {
}