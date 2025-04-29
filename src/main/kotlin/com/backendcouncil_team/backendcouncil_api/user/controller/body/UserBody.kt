package com.backendcouncil_team.backendcouncil_api.user.controller.body


/**
 * Clase que simula el body, por sus datos; para uso general de un cliente/usuario.
 * @property nombre Nombre de la cuenta a crear del usuario.
 * @property apPaterno Apellido paterno de la cuenta a crear del usuario.
 * @property apMaterno Apellido materno de la cuenta a crear del usuario.
 * @property password Contraseña de la cuenta a crear del usuario.
 * @property correo Correo de la cuenta a crear del usuario.
 * @property username Nombre de usuario de la cuenta a crear del usuario.
 */

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