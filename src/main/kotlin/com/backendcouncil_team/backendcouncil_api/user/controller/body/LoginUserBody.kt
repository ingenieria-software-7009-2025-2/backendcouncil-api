package com.backendcouncil_team.backendcouncil_api.user.controller.body

/**
 * Clase que simula el body, por sus datos; para uso específico al momento de querer hacer log-in una cuenta.
 * @property password Contraseña de la cuenta que se intenta acceder.
 * @property correo Correo de la cuenta que se intenta acceder.
 */
data class LoginUserBody(
    var password: String,
    var correo: String
)