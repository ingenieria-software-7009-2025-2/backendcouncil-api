package com.backendcouncil_team.backendcouncil_api.coments.controller;

import com.backendcouncil_team.backendcouncil_api.coments.controller.body.ComentBody
import com.backendcouncil_team.backendcouncil_api.coments.controller.body.UpdateComBody
import com.backendcouncil_team.backendcouncil_api.coments.domain.Comentario
import com.backendcouncil_team.backendcouncil_api.coments.repository.entity.Coment
import com.backendcouncil_team.backendcouncil_api.coments.service.ComentService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador para gestionar las operaciones relacionadas con los clientes/usuarios.
 * @property ComentService Instancia de una clase de servicio dedicada a comentarios.
 */
@Tag(name = "comment", description = "Operaciones relacionadas con el uso de comentarios.")
@Controller
@RequestMapping("/v1/comment")
class ComentController (var comentService: ComentService) {

    /**
     * Endpoint para registrar un comentario.
     * @param coment Datos del comentario que se recibirán la petición.
     * @return ResponseEntity con la información del comentario creado o 404 si algo falló.
     */
    @Operation(
        summary = "Registra un comentario.",
        description = "Usando los datos brindados, registra un comentario.",
        responses = [
            ApiResponse(responseCode = "200", description = "Comentario registrado.", content = [Content(
                schema = Schema(implementation = Comentario::class), mediaType = "aplication/json"
            )]),
            ApiResponse(responseCode = "404", description = "Ocurrió un error inesperado.", content = [Content()]),
        ]
    )
    @PostMapping
    fun addComent(@RequestBody coment: ComentBody): ResponseEntity<Comentario> {
        val result = comentService.addComent(coment.clienteid,coment.incidenteid,coment.contenido)

        if (result != null) {
            return ResponseEntity.ok(result)
        }
        return ResponseEntity.notFound().build()
    }

    /**
     * Endpoint para sumar un like a un comentario
     * @param coment Datos necesarios del comentario para su búsqueda.
     * @return ResponseEntity con la información del comentario, nulo si no se encontró.
     */
    @Operation(
        summary = "Likea un comentario.",
        description = "Suma un like a un comentario.",
        responses = [
            ApiResponse(responseCode = "200", description = "Like registrado.", content = [Content(
                schema = Schema(implementation = Comentario::class), mediaType = "aplication/json"
            )]),
        ]
    )
    @PutMapping("/like")
    fun likeComent(@RequestBody coment: UpdateComBody): ResponseEntity<Int> {
        println(coment.comentarioid)
        val result = comentService.like(coment.comentarioid!!)
        return ResponseEntity.ok(result)
    }

    /**
     * Endpoint para restar un like a un comentario.
     * @param coment Datos necesarios del comentario para su búsqueda.
     * @return ResponseEntity con la información del comentario, nulo si no se encontró.
     */
    @Operation(
        summary = "Dislikea un comentario.",
        description = "Resta un like a un comentario.",
        responses = [
            ApiResponse(responseCode = "200", description = "Dislike registrado.", content = [Content(
                schema = Schema(implementation = Comentario::class), mediaType = "aplication/json"
            )]),
        ]
    )
    @PutMapping("/dislike")
    fun dislikeComent(@RequestBody coment: UpdateComBody): ResponseEntity<Int> {
        val result = comentService.dislike(coment.comentarioid!!)
        return ResponseEntity.ok(result)
    }

    /**
     * Endpoint para obtener todos los comentarios.
     * @return ResponseEntity con la información de todos los comentarios.
     */
    @Operation(
        summary = "Regresa a todos los comentarios.",
        description = "Regresa a todos los comentarios registrados.",
        responses = [
            ApiResponse(responseCode = "200", description = "Operación realizada con éxito.", content = [Content(
                array = ArraySchema(schema = Schema(implementation = Comentario::class)), mediaType = "aplication/json"
            )]),
        ]
    )
    @GetMapping("/all")
    fun getAllComents(): ResponseEntity<List<Comentario>> {
        return ResponseEntity.ok(comentService.findAll())
    }

    /**
     * Endpoint para obtener todos los comentarios relacionados con un incidente.
     * @param coment Datos suficientes para la búsqueda.
     */
    @Operation(
        summary = "Regresa a todos los comentarios de un incidente.",
        description = "Regresa a todos los comentarios registrado de un incidente.",
        responses = [
            ApiResponse(responseCode = "200", description = "Operación realizada con éxito.", content = [Content(
                array = ArraySchema(schema = Schema(implementation = Comentario::class)), mediaType = "aplication/json"
            )]),
            ApiResponse(responseCode = "404", description = "Incidente no encontrado / Ocurrió un error inesperado.", content = [Content()]),
        ]
    )
    @PostMapping("/incident")
    fun getcommentsId(@RequestBody coment: UpdateComBody): ResponseEntity<List<Comentario>> {
        val result = comentService.getusr(coment.incidenteid!!)

        if (result != null) {
            return ResponseEntity.ok(result)
        }
        return ResponseEntity.notFound().build()
    }
}
