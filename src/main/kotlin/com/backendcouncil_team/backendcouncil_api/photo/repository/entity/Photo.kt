package com.backendcouncil_team.backendcouncil_api.photo.repository.entity

import jakarta.persistence.*

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