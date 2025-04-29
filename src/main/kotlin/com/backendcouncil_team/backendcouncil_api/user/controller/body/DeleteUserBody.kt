package com.backendcouncil_team.backendcouncil_api.user.controller.body

/**
 * Clase que simula el body, por sus datos; para uso específico al momento de querer borrar una cuenta.
 * @property password Contraseña de la cuenta activa.
 * @property correo Correo de la cuenta activa.
 * @property token Tóken de la cuenta activa.
 */
data class DeleteUserBody(
    var password: String,
    var correo: String,
    var token:String
)