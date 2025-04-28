package com.backendcouncil_team.backendcouncil_api.user.controller.body

import ch.qos.logback.core.net.server.Client

data class UserBody(
    var nombre: String = "",
    var apPaterno: String = "",
    var apMaterno: String = "",
    var password: String = "",
    var correo: String = "",
    var username: String = "",
    var rolid: String = "",
    var clienteid: String = ""
)