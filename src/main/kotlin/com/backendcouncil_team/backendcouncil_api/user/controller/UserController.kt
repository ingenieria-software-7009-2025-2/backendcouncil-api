package com.backendcouncil_team.backendcouncil_api.user.controller

import com.backendcouncil_team.backendcouncil_api.user.controller.body.DeleteUserBody
import com.backendcouncil_team.backendcouncil_api.user.controller.body.LoginUserBody
import com.backendcouncil_team.backendcouncil_api.user.controller.body.UserBody
import com.backendcouncil_team.backendcouncil_api.user.domain.Usuario
import com.backendcouncil_team.backendcouncil_api.user.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.annotations.responses.*
import io.swagger.v3.oas.annotations.security.SecurityRequirement

/**
 * Controlador para gestionar las operaciones relacionadas con los clientes/usuarios.
 * @property UserService Instancia de una clase de servicio dedicada a usuarios.
 */
@Controller
@RequestMapping("/v1/users")
@Tag(
    name = "users",
    description = "Operaciones relacionadas con usuarios."
)

class UserController(var userService: UserService) {

    /**
     * Endpoint para registrar un nuevo usuario.
     * @param userBody Datos del usuario que se recibirán en la petición.
     * @return ResponseEntity con la respuesta del servicio.
     */
    @Operation(
        summary = "Registra un usuario",
        description = "Usando los datos brindados, registra un usuario.",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Usuario registrado",
                content = [Content(
                    schema = Schema(implementation = Usuario::class),
                    mediaType = "aplication/json"
                )]
            ),
            ApiResponse(
                responseCode = "406",
                description = "usuario existente",
                content = [Content()]
            ),ApiResponse(
                responseCode = "407",
                description = "correo existente",
                content = [Content()]
            ),ApiResponse(
                responseCode = "400",
                description = "Ocurrió un error inesperado",
                content = [Content()]
            ),
        ]
    )
    @PostMapping
    fun addUser(@RequestBody userBody: UserBody): ResponseEntity<Any> {
        // Convertir los datos del request a un objeto del dominio
        val usuario = Usuario(
            nombre = userBody.nombre,
            apPaterno = userBody.apPaterno,
            apMaterno = userBody.apMaterno,
            password = userBody.password,
            correo = userBody.correo,
            userName = userBody.username
        )
        val response = userService.addUser(usuario)

        if (response.clienteid?.toInt() == 0){
            return ResponseEntity.status(406).body("usuario existente")
        } else if (response.clienteid?.toInt() == 1){
            return ResponseEntity.status(407).body("correo existente")
        }
        return ResponseEntity.ok(response)
    }

    /**
     * Endpoint para obtener la lista de todos los usuarios registrados (old).
     * @return ResponseEntity con la lista de usuarios.
     */
    @Operation(
        summary = "Regresa a todos los usuarios",
        description = "Regresa a todos los usuarios registrados",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Operación realizada con éxito",
                content = [Content(
                    array = ArraySchema(
                        schema = Schema(implementation = Usuario::class)
                    ),
                    mediaType = "aplication/json"
                )]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Ocurrió un error inesperado",
                content = [Content()]
            ),
        ]
    )
    @GetMapping
    fun getAllUsers(): ResponseEntity<Any> {
        val result = userService.retrieveAllUser()
        return ResponseEntity.ok(result)
    }

    /**
     * Endpoint para obtener la lista de todos los usuarios registrados.
     * @return ResponseEntity con la lista de usuarios.
     */
    @Operation(
        summary = "Regresa a todos los usuarios",
        description = "Regresa a todos los usuarios registrados",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Operación realizada con éxito",
                content = [Content(
                    array = ArraySchema(
                        schema = Schema(implementation = Usuario::class)
                    ),
                    mediaType = "aplication/json"
                )]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Ocurrió un error inesperado",
                content = [Content()]
            ),
        ]
    )
    @GetMapping("/toolkit")
    fun GetAllUsers() : ResponseEntity<List<Usuario>> {
        return ResponseEntity.ok(userService.findAll())
    }

    /**
     * Endpoint para borrar cuenta.
     * @param userBody Datos del usuario que se recibirán en la petición (correo, password, token).
     * @return ResponseEntity con la información del usuario y la autenticación es exitosa, o 400 si falla.
     */
    @Operation(
        summary = "Borrar una cuenta",
        description = "Usando el correo, confirmación de contraseña, y un tóken, elimina la cuenta.",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Cuenta borrada con éxito.",
                content = [Content(
                    schema = Schema(implementation = Usuario::class),
                    mediaType = "aplication/json"
                )]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Usuario no encontrado / Mal contraseña / Ocurrió un error inesperado",
                content = [Content()]
            ),
        ]
    )
    @DeleteMapping
    fun deleteUser(@RequestBody userBody: DeleteUserBody): ResponseEntity<Any> {
        val response = userService.delete(userBody.correo,userBody.password,userBody.token.removePrefix("Bearer "))
        if (response < 1){
            return ResponseEntity.badRequest().body("no se pudo eliminar ese usuario")
        } else {
            return ResponseEntity.ok(response)
        }
    }

    /**
     * Endpoint para iniciar sesión.
     * @param loginUserBody Datos del usuario (correo y contraseña) para autenticación.
     * @return ResponseEntity con la información del usuario si la autenticación es exitosa, o 404 si falla.
     */
    @Operation(
        summary = "Iniciado de sesión",
        description = "Usando los datos brindados, intenta inicia sesión.",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Sesión iniciada",
                content = [Content(
                    schema = Schema(implementation = Usuario::class),
                    mediaType = "aplication/json"
                )]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Usuario no encontrado / Ocurrió un error inesperado",
                content = [Content()]
            ),
        ]
    )
    @PostMapping("/login")
    fun login(@RequestBody loginUserBody: LoginUserBody): ResponseEntity<Usuario> {
        if (esCorreoValido(loginUserBody.correo)) {
            val result = userService.login(loginUserBody.correo, loginUserBody.password)
            return if (result == null) {
                ResponseEntity.notFound().build()
            } else {
                ResponseEntity.ok(result)
            }
        } else {
            val result = userService.loginUser(loginUserBody.correo, loginUserBody.password)
            return if (result == null) {
                ResponseEntity.notFound().build()
            } else {
                ResponseEntity.ok(result)
            }
        }
    }

    /**
     * Función auxiliar que regresa si dada una cadena, esta cumple con el patrón para ser un correo electrónico.
     * @param correo Cadena a probar que cumple el patrón de correo.
     */
    fun esCorreoValido(correo: String): Boolean {
        val regexCorreo = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
        return regexCorreo.matches(correo)
    }

    /**
     * Endpoint para cerrar sesión.
     * @param token Token de autorización proporcionado en la cabecera.
     * @return ResponseEntity con mensaje de éxito o error en caso de fallo.
     */
    @Operation(
        summary = "Cierra la sesión",
        description = "Dada la sesión iniciada, cierra la sesión",
        security = [SecurityRequirement(name = "BearerAuth")],
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Sesión finalizada",
                content = [Content(
                    mediaType = "plain/text"
                )]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Ocurrió un error inesperado",
                content = [Content()]
            ),
        ]
    )
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
    @Operation(
        summary = "Obtiene la información del usuario",
        description = "Dada la sesión iniciada, obtiene su información",
        security = [SecurityRequirement(name = "BearerAuth")],
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Operación realizada con éxito",
                content = [Content(
                    schema = Schema(implementation = Usuario::class),
                    mediaType = "aplication/json"
                )]
            ),
            ApiResponse(
                responseCode = "401",
                description = "Sin autorización / Ocurrió un error inesperado",
                content = [Content()]
            ),
        ]
    )
    @GetMapping("/me")
    fun me(@RequestHeader("Authorization") token: String): ResponseEntity<Usuario> {
        val response = userService.getInfoAboutMe(token.removePrefix("Bearer "))
        return if (response != null) {
            ResponseEntity.ok(response)
        } else {
            ResponseEntity.status(401).build()
        }
    }

    /**
     * Endpoint para modificar la información del usuario autenticado.
     * @param token Token de autorización.
     * @param userBody Datos del usuario a modificar.
     * @return ResponseEntity con la información del usuario o un estado 401 si no es válido.
     */
    @Operation(
        summary = "Modifica la información del usuario",
        description = "Dada la sesión iniciada, modifica su información",
        security = [SecurityRequirement(name = "BearerAuth")],
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Operación realizada con éxito",
                content = [Content(
                    schema = Schema(implementation = Usuario::class),
                    mediaType = "aplication/json"
                )]
            ),
            ApiResponse(
                responseCode = "401",
                description = "Sin autorización / Ocurrió un error inesperado",
                content = [Content()]
            ),
        ]
    )
    @PutMapping("/me")
    fun update(@RequestHeader("Authorization") token: String, @RequestBody userBody: UserBody): ResponseEntity<Usuario> {

        val usuario = Usuario(
            nombre = userBody.nombre,
            apPaterno = userBody.apPaterno,
            apMaterno = userBody.apMaterno,
            password = userBody.password,
            correo = userBody.correo,
            userName = userBody.username
        )

        val response = userService.update(token.removePrefix("Bearer "),usuario,)

        return if (response != null){
            ResponseEntity.ok(response)
        } else {
            ResponseEntity.status(401).build()
        }
    }

    /**
     * Endpoint para modificar el rol de un usuario.
     * @param userBody Datos del usuario a modificar.
     * @return ResponseEntity con la información del usuario o un estado 401 si no es válido.
     */
    @Operation(
        summary = "Modifica el rol de un usuario",
        description = "Modifica el rol de un usuario",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Operación realizada con éxito",
                content = [Content(
                    schema = Schema(implementation = Usuario::class),
                    mediaType = "aplication/json"
                )]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Usuario no encontrado / Ocurrió un error inesperado",
                content = [Content()]
            ),
        ]
    )
    @PutMapping("/toolkit")
    fun updateRole(@RequestBody userBody: UserBody): ResponseEntity<Usuario> {
        val response = userService.updateRol(userBody.username, userBody.rolid.toInt())
        if(response != null){
            return ResponseEntity.ok(response)
        } else {
            return ResponseEntity.status(404).build()
        }
    }

    /**
     * Endpoint para verificar la igualdad de contraseñas de un usuario.
     * @param userBody Datos del usuario que se recibirán en la petición.
     * @return ResponseEntity con la respuesta del servicio.
     */
    @Operation(
        summary = "Verifica a un usuario por su contraseña",
        description = "Verifica a un usuario por su contraseña.",
        security = [SecurityRequirement(name = "BearerAuth")],
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Autenticado",
                content = [Content(
                    schema = Schema(implementation = Usuario::class),
                    mediaType = "aplication/json"
                )]
            ),
            ApiResponse(
                responseCode = "401",
                description = "Sin autorización / Ocurrió un error inesperado",
                content = [Content()]
            ),
        ]
    )
    @PostMapping("toolkit/verify-password")
    fun verifyPassword(@RequestHeader("Authorization") token : String, @RequestBody userBody: UserBody): ResponseEntity<UserBody>{
        val pass = userService.getPassword(token.removePrefix("Bearer "))

        if (pass.equals(userBody.password)){
            return ResponseEntity.ok(userBody)
        }
        return ResponseEntity.status(401).body(null)
    }

    /**
     * Endpoint para verificar la existencia de un username.
     * @return ResponseEntity con `boolean` mostrando si se cumple con la existencia.
     */
    @Operation(
        summary = "Verificación de existencia de username",
        description = "Verifica si el username existe en el modelo",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Operación realizada con éxito",
                content = [Content(
                    schema = Schema(implementation = Boolean::class),
                    mediaType = "aplication/json"
                )]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Ocurrió un error inesperado",
                content = [Content()]
            ),
        ]
    )
    @GetMapping("/check-username")
    fun checkUsername(@RequestParam username: String): ResponseEntity<Boolean> {
        val usernameDisponible = userService.validarUsername(username)
        if (!usernameDisponible)
            return ResponseEntity.badRequest().build()
        return ResponseEntity.ok(usernameDisponible)
    }

    /**
     * Endpoint para verificar la existencia de un email.
     * @return ResponseEntity con `boolean` mostrando si se cumple con la existencia.
     */
    @Operation(
        summary = "Verificación de existencia de mail",
        description = "Verifica si el mail existe en el modelo",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Operación realizada con éxito",
                content = [Content(
                    schema = Schema(implementation = Boolean::class),
                    mediaType = "aplication/json"
                )]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Ocurrió un error inesperado",
                content = [Content()]
            ),
        ]
    )
    @GetMapping("/check-email")
    fun checkEmail(@RequestParam username: String): ResponseEntity<Boolean> {
        val usernameDisponible = userService.validarMail(username)
        if (!usernameDisponible)
            return ResponseEntity.badRequest().build()
        return ResponseEntity.ok(usernameDisponible)
    }
}