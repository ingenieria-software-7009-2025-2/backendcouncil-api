package com.backendcouncil_team.backendcouncil_api.user

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * User/Usuario controller
 * DefaultMapping "/v1/users"
 * This class has the task to control the flow with the possible REST services related to the user
 *
 * @author The Backend Council
 * @see com.backendcouncil_team.backendcouncil_api.Usuario
 *
 */

@RestController
@RequestMapping("/v1/users")

class UsuarioController {

    /**
     * POST: to create a user/[usuario].
     * Mapping: "/v1/users"
     * @param[usuario] the user cast from the json.
     * @return [org.springframework.http.ResponseEntity] with status 201 (created) and with the user.
     */
    @PostMapping
    fun postUsuario(@RequestBody usuario: Usuario): ResponseEntity<Usuario> {
        val myUsuario = Usuario(
            name = usuario.name, mail = usuario.mail,
            password = usuario.password, token = usuario.token
        )
        return ResponseEntity(myUsuario, HttpStatus.CREATED)
    }

    /**
     * POST: to login a user/[usuario].
     * Mapping: "/v1/users/login"
     * @param[usuario] the user cast from the json.
     * @return if the data recieved matched with `password` and `mail`, then
     *          [org.springframework.http.ResponseEntity.ok] with a status 200 (ok) with the user.
     *         if the data recieved from `password` and `mail` is blank or doesn't match, then
     *          [org.springframework.http.ResponseEntity.badrequest] with a status 400 (bad request) and a *blank* user.
     */
    @PostMapping("/login")
    fun postLogin(@RequestBody usuario: Usuario): ResponseEntity<Usuario> {
        if (usuario.mail.isEmpty() || usuario.password.isEmpty()) {
            return ResponseEntity.badRequest().body(Usuario())
        }
        return ResponseEntity.ok(Usuario())
    }

    /**
     * POST: to logout a user/[usuario].
     * Mapping: "/v1/users/logout"
     * @return [org.springframework.http.ResponseEntity.ok] with a 200 status (ok) and a `"sesion cerrada"`.
     */
    @PostMapping("/logout")
    fun postLogout(): ResponseEntity<String> {
        return ResponseEntity.ok("sesion cerrada")
    }

    /**
     * POST: to logout a user/[usuario].
     * Mapping: "/v1/users/me"
     * @return [org.springframework.http.ResponseEntity.ok] with a 200 status (ok).
     */
    @GetMapping("/me")
    fun getMe(): ResponseEntity<Usuario> {
        val isaac = Usuario(
            name = "Isaac",
            mail = "webi.wabo@duckmail.com",
            password = "0b4W_1b3w",
            token = "proteinas")
        return ResponseEntity.ok(isaac)
    }
}