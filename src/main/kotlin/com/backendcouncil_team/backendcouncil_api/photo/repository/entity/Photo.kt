package com.backendcouncil_team.backendcouncil_api.photo.repository.entity

import jakarta.persistence.*

/**
 * Entidad referente a fotos provenientes del modelo.
 * @property fotoid ID de la foto.
 * @property incideteid ID del incidente al que pertenece la foto.
 */
@Entity
@Table(name = "foto")
class Photo constructor(
    @Id
    @Column(name = "fotoid")
    val fotoid: ByteArray,
    @Column(name = "incidenteid")
    var incideteid: Long = 0
){

}