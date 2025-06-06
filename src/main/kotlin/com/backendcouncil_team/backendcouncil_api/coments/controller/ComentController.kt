package com.backendcouncil_team.backendcouncil_api.coments.controller;

import com.backendcouncil_team.backendcouncil_api.coments.controller.body.ComentBody
import com.backendcouncil_team.backendcouncil_api.coments.controller.body.UpdateComBody
import com.backendcouncil_team.backendcouncil_api.coments.domain.Comentario
import com.backendcouncil_team.backendcouncil_api.coments.repository.entity.Coment
import com.backendcouncil_team.backendcouncil_api.coments.service.ComentService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
@Controller
@RequestMapping("/v1/comment")

class ComentController (var comentService: ComentService) {
    @PostMapping
    fun addComent(@RequestBody coment: ComentBody): ResponseEntity<Comentario> {
        val result = comentService.addComent(coment.clienteid,coment.incidenteid,coment.contenido)

        if (result != null) {
            return ResponseEntity.ok(result)
        }
        return ResponseEntity.notFound().build()
    }

    @PutMapping("/like")
    fun likeComent(@RequestBody coment: UpdateComBody): ResponseEntity<Int> {
        println(coment.comentarioid)
        val result = comentService.like(coment.comentarioid!!)
        return ResponseEntity.ok(result)
    }

    @PutMapping("/dislike")
    fun dislikeComent(@RequestBody coment: UpdateComBody): ResponseEntity<Int> {
        val result = comentService.dislike(coment.comentarioid!!)
        return ResponseEntity.ok(result)
    }

    @GetMapping("/all")
    fun getAllComents(): ResponseEntity<List<Comentario>> {
        return ResponseEntity.ok(comentService.findAll())
    }

    @PostMapping("/incident")
    fun getcommentsId(@RequestBody coment: UpdateComBody): ResponseEntity<List<Comentario>> {
        val result = comentService.getusr(coment.incidenteid!!)

        if (result != null) {
            return ResponseEntity.ok(result)
        }
        return ResponseEntity.notFound().build()
    }
}
