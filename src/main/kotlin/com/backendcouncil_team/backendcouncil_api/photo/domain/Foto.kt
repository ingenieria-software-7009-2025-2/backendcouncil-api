package com.backendcouncil_team.backendcouncil_api.photo.domain

import com.backendcouncil_team.backendcouncil_api.photo.repository.entity.Photo
import org.springframework.data.jpa.repository.Query
/**
 * Dominio referente a fotos para recabar los datos provenientes del modelo como instancias.
 * @property fotoid ID de la foto.
 * @property incidenteid ID del incidente al que pertenece la foto.
 */
data class Foto(
    var fotoid: ByteArray? = null,
    var incidenteid: Long? = null
) {}