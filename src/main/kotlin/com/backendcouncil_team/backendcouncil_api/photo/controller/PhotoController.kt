package com.backendcouncil_team.backendcouncil_api.photo.controller

import com.backendcouncil_team.backendcouncil_api.incident.domain.Incidente
import com.backendcouncil_team.backendcouncil_api.photo.controller.Body.PhotoBody
import com.backendcouncil_team.backendcouncil_api.photo.domain.Foto
import com.backendcouncil_team.backendcouncil_api.photo.repository.entity.Photo
import com.backendcouncil_team.backendcouncil_api.photo.service.PhotoService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView

/**
 * Controlador para gestionar las operaciones relacionadas a fotos.
 * @property photoService Instancia de la clase servicio que contiene la lógica para las operaciones de fotos.
 */
@Controller
@RequestMapping("/v1/photos")
@Tag(
    name = "photo",
    description = "Operaciones relacionadas con las fotos de los incidentes."
)

class PhotoController(var photoService: PhotoService) {

    /**
     * Endpoint que regresa todos los incidentes.
     * @return ResponseEntity con la respuesta del servicio y una lista de todos los incidentes si no han ocurrido
     * errores.
     */
    @Operation(
        summary = "Regresa todas las fotos",
        description = "Regresa una lista con todos las fotos de un incdente",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Proceso éxitoso",
                content = [Content(
                    array = ArraySchema(
                        schema = Schema(implementation = Foto::class)
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
    fun getPhotos(@RequestBody photo: PhotoBody): ResponseEntity<List<Foto>> {
        return ResponseEntity.ok(photoService.getPhotos(photo))
    }

    /**
     * Endpoint para registrar una nueva foto de un incidente.
     * @param photoBody Datos de la foto del incidente que se recibirán en la petición.
     * @return ResponseEntity con la respuesta del servicio.
     */
    @Operation(
        summary = "Registra una foto de un incidente",
        description = "Usando los datos brindados, registra una foto de un incidente.",
        responses = [
            ApiResponse(
                responseCode = "201",
                description = "Fotografía almacenada",
                content = [Content(
                    schema = Schema(implementation = Foto::class),
                    mediaType = "aplication/json"
                )]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Ocurrió un error inesperado",
                content = [Content()]
            ),
        ]

    )
    @PostMapping
    fun addPhoto(@RequestBody photo: PhotoBody): ResponseEntity<Any>{
        val response = photoService.addPhoto(photo)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }
}