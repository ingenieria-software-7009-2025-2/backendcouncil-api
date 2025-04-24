package com.backendcouncil_team.backendcouncil_api.user.domain

/**
 * Dominio usuario propio para recabar los datos provenientes del modelo como instancias.
 * @property clienteid ID del usuario.
 * @property rolid ID del rol del usuario.
 * @property userName Nombre de Usuario del mismo.
 * @property nombre Nombre del Usuario.
 * @property apPaterno Apellido paterno del usuario.
 * @property apMaterno Apellido materno del usuario.
 * @property correo Correo electrónico del usuario.
 * @property token Tóken del usuario.
 * @property password Contraseña del usuario.
 * @property isActive Refiere a si el usuario tiene una cuenta activa.
 */
data class Usuario(
    var clienteid: Long? = null,
    var rolid: Int? = null,
    var userName: String,
    var nombre: String,
    var apPaterno: String,
    var apMaterno: String,
    var correo: String,
    var token: String? = null,
    var password: String? = null,
    var isActive: Boolean? = null
)