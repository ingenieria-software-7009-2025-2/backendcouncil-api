package com.backendcouncil_team.backendcouncil_api.user.domain

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