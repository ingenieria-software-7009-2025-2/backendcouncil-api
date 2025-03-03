package com.backendcouncil_team.backendcouncil_api.user.controller

import com.backendcouncil_team.backendcouncil_api.user.controller.body.LoginUserBody
import com.backendcouncil_team.backendcouncil_api.user.controller.body.UserBody
import com.backendcouncil_team.backendcouncil_api.user.domain.Usuario
import com.backendcouncil_team.backendcouncil_api.user.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

/**
 * Controlador para gestionar las operaciones relacionadas con los usuarios.
 */
@Controller
@RequestMapping("/v1/users")
class   UserController(var userService: UserService) {

    /**
     * Endpoint para registrar un nuevo usuario.
     * @param userBody Datos del usuario que se recibirán en la petición.
     * @return ResponseEntity con la respuesta del servicio.
     */
    @PostMapping
    fun addUser(@RequestBody userBody: UserBody): ResponseEntity<Any> {
        // Convertir los datos del request a un objeto del dominio
        val usuario = Usuario(
            nombre = userBody.nombre,
            apPaterno = userBody.apPaterno,
            apMaterno = userBody.apMaterno,
            password = userBody.password,
            correo = userBody.correo
        )
        val response = userService.addUser(usuario)
        return ResponseEntity.ok(response)
    }

    /**
     * Endpoint para iniciar sesión.
     * @param loginUserBody Datos del usuario (correo y contraseña) para autenticación.
     * @return ResponseEntity con la información del usuario si la autenticación es exitosa, o 404 si falla.
     */
    @PostMapping("/login")
    fun login(@RequestBody loginUserBody: LoginUserBody): ResponseEntity<Usuario> {
        val result = userService.login(loginUserBody.correo, loginUserBody.password)
        return if (result == null) {
            ResponseEntity.notFound().build()
        } else {
            ResponseEntity.ok(result)
        }
    }

    /**
     * Endpoint para cerrar sesión.
     * @param token Token de autorización proporcionado en la cabecera.
     * @return ResponseEntity con mensaje de éxito o error en caso de fallo.
     */
    @PostMapping("/logout")
    fun logout(@RequestHeader("Authorization") token: String): ResponseEntity<String> {
        val successLogout = userService.logout(token.removePrefix("Bearer "))
        return if (!successLogout) {
            ResponseEntity.badRequest().build()
        } else {
            ResponseEntity.ok("Sesión finalizada")
        }
    }

    /**
     * Endpoint para obtener la información del usuario autenticado.
     * @param token Token de autorización.
     * @return ResponseEntity con la información del usuario o un estado 401 si no es válido.
     */
    @GetMapping("/me")
    fun me(@RequestHeader("Authorization") token: String): ResponseEntity<Usuario> {
        val response = userService.getInfoAboutMe(token.removePrefix("Bearer "))
        return if (response != null) {
            ResponseEntity.ok(response)
        } else {
            ResponseEntity.status(401).build()
        }
    }

    @PutMapping("/me")
    fun update(@RequestHeader("Authorization") token: String, @RequestBody userBody: UserBody): ResponseEntity<Usuario> {

        val usuario = Usuario(
            nombre = userBody.nombre,
            apPaterno = userBody.apPaterno,
            apMaterno = userBody.apMaterno,
            password = userBody.password,
            correo = userBody.correo,
        )

        val response = userService.update(token.removePrefix("Bearer "),usuario,)

        return if (response != null){
            ResponseEntity.ok(response)
        } else {
            ResponseEntity.status(401).build()
        }
    }
    /**
     * Endpoint para obtener la lista de todos los usuarios registrados.
     * @return ResponseEntity con la lista de usuarios.
     */
    @GetMapping
    fun getAllUsers(): ResponseEntity<Any> {
        val result = userService.retrieveAllUser()
        return ResponseEntity.ok(result)
    }
}