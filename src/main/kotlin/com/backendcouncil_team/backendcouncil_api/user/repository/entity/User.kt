package com.backendcouncil_team.backendcouncil_api.user.repository.entity

import jakarta.persistence.*

@Entity
@Table(name = "cliente")
class User constructor(
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    val clienteid: Long = 0,
    @Column(name = "nombre")
    var nombre: String = "",
    @Column(name = "rolid")
    var rolid: Int = 0,
    @Column (name = "username")
    var username: String = "",
    @Column(name = "appaterno")
    var apPaterno: String = "",
    @Column(name = "apmaterno")
    var apMaterno: String = "",
    @Column(name = "password")
    var password: String = "",
    @Column(name = "correo")
    var mail: String="",
    @Column(name = "token")
    var token: String? = null
) {


}