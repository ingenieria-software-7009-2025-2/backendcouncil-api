package com.backendcouncil_team.backendcouncil_api

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/users")

class UsuarioController {

    @PostMapping
    fun postUsuario(@RequestBody usuario: Usuario): ResponseEntity<Usuario> {
        val myUsuario = Usuario(name = usuario.name, mail = usuario.mail,
            password = usuario.password, token = usuario.token)
        //return ResponseEntity(myUsuario, HttpStatus.CREATED)
        return ResponseEntity.ok(myUsuario)
    }

    @PostMapping("/login")
    fun postLogin(@RequestBody usuario: Usuario): ResponseEntity<Usuario> {
        if(usuario.mail.isEmpty() || usuario.password.isEmpty()){
            return ResponseEntity.badRequest().body(Usuario())
        }
        return ResponseEntity.ok(Usuario())
    }

    @PostMapping("/logout")
    fun postLogout(): ResponseEntity<String> {
        return ResponseEntity.ok("sesion cerrada")
    }

    @GetMapping("/me")
    fun getMe(): ResponseEntity<Usuario> {
        val isaac = Usuario(name = "Isaac",mail = "Isaacwebi@gmail.com",password = "webiwabo", token = "proteinas")
        return ResponseEntity.ok(isaac)
    }
}