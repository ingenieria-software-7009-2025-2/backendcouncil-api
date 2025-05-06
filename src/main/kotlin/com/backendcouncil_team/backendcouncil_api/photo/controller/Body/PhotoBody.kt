package com.backendcouncil_team.backendcouncil_api.photo.controller.Body

import com.backendcouncil_team.backendcouncil_api.incident.domain.Incidente

/**
 * Clase que simula el body, por sus datos; para uso general de una foto.
 * @property fotoid ID de la foto.
 * @property incidenteid ID del incidente al que pertenece la foto.
 */
data class PhotoBody (
    var fotoid: ByteArray,
    var incidenteid: String = ""
) {}