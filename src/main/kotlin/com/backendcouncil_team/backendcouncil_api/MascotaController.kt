package com.backendcouncil_team.backendcouncil_api

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/mascotas")

class MascotaController {

    @GetMapping
    fun retrieveMascota() : ResponseEntity<Mascota> {
        val miMascota = Mascota(tipo = "Acero,Bicho", name = "Scizor", comidaFav = "Carameloraro")
        return ResponseEntity.ok(miMascota)
    }

    @PostMapping
    fun createMascota(@RequestBody silksong: MascotaBody) : ResponseEntity<Mascota> {
        val miMascota = Mascota(tipo = silksong.tipo, name = silksong.name, comidaFav = silksong.comidaFav)
        return ResponseEntity.ok(miMascota)
    }
}