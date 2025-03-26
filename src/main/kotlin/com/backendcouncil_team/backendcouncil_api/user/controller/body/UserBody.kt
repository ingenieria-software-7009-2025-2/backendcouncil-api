package com.backendcouncil_team.backendcouncil_api.user.controller.body

data class UserBody(
    var nombre: String = "",
    var apPaterno: String = "",
    var apMaterno: String = "",
    var password: String = "",
    var correo: String = "",
    var username: String = ""
)