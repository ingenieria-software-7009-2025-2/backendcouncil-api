package com.backendcouncil_team.backendcouncil_api.user.repository.entity

import jakarta.persistence.*

/**
 * Entidad referente a clientes/usuarios provenientes del modelo.
 * @property clienteid ID del usuario.
 * @property nombre Nombre del Usuario.
 * @property rolid ID del rol del usuario.
 * @property username Nombre de Usuario del mismo.
 * @property apPaterno Apellido paterno del usuario.
 * @property apMaterno Apellido materno del usuario.
 * @property password Contraseña del usuario.
 * @property mail Correo electrónico del usuario.
 * @property token Tóken del usuario.
 */

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