package com.backendcouncil_team.backendcouncil_api.incident.controller


import com.backendcouncil_team.backendcouncil_api.incident.controller.body.GetBody
import com.backendcouncil_team.backendcouncil_api.incident.controller.body.IncidentBody
import com.backendcouncil_team.backendcouncil_api.incident.controller.body.UpdateBody
import com.backendcouncil_team.backendcouncil_api.incident.domain.Estado
import com.backendcouncil_team.backendcouncil_api.incident.domain.Incidente
import com.backendcouncil_team.backendcouncil_api.incident.service.IncidentService
import com.backendcouncil_team.backendcouncil_api.user.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

/**
 * Controlador para gestionar las operaciones relacionadas con cada incidente.
 * @property incidentService
 * @property userService
 */
@Controller
@RequestMapping("/v1/incident")
@Tag(
    name = "incident",
    description = "Operaciones relacionadas con incidentes."
)
class IncidentController(var incidentService: IncidentService,var userService: UserService) {

    /**
     * Endpoint para registrar un nuevo incidente.
     * @param incidentBody Datos del incidente que se recibirán en la petición.
     * @return ResponseEntity con la respuesta del servicio.
     */
    @Operation(
        summary = "Registra un incidente",
        description = "Usando los datos brindados, registra un incidente.",
        security = [SecurityRequirement(name = "BearerAuth")],
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Incidente registrado",
                content = [Content(
                    schema = Schema(implementation = Incidente::class),
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
    fun addIncident(@RequestHeader("Authorization") token: String, @RequestBody incidentBody: IncidentBody ): ResponseEntity<Incidente> {
        println(incidentBody.toString())
        val user = userService.getInfoAboutMe(token.removePrefix("Bearer "))
        val incident = Incidente(
            clienteid = user?.clienteid!!,
            nombre =  incidentBody.nombre!!,
            descripcion =  incidentBody.descripcion,
            fecha = incidentBody.fecha,
            hora =  incidentBody.hora,
            latitud = incidentBody.latitud,
            longitud =  incidentBody.longitud,
            estado = Estado.reportado.toString(),
            categoriaid = incidentBody.categoriaid,
            likes = 0.toBigDecimal(),
            )

        if (user != null) {
            return ResponseEntity.ok(
                incidentService.addIncident(
                    cliente = user.clienteid!!,
                    incidente = incident
                )
            )

        } else return ResponseEntity.notFound().build()
    }

    @PutMapping("/like")
    fun likeIncidente(@RequestBody updateBody: UpdateBody): ResponseEntity<Int> {
        val result = incidentService.like(updateBody.incidenteid!!)

        if(result != null) {
            return ResponseEntity.ok(result)
        }
        return ResponseEntity.notFound().build()
    }


    @PutMapping("/dislike")
    fun dislikeIncidente(@RequestBody updateBody: UpdateBody): ResponseEntity<Int> {
        val result = incidentService.dislike(updateBody.incidenteid!!)

        if(result != null) {
            return ResponseEntity.ok(result)
        }
        return ResponseEntity.notFound().build()
    }
    /**
     * Endpoint que regresa todos los incidentes.
     * @return ResponseEntity con la respuesta del servicio y una lista de todos los incidentes si no han ocurrido
     * errores.
     */
    @Operation(
        summary = "Regresa todos los incidentes",
        description = "Regresa una lista con todos los incidentes",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Operación realizada con éxito",
                content = [Content(
                    array = ArraySchema(
                        schema = Schema(implementation = Incidente::class)
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
    fun getAll(): ResponseEntity<List<Incidente>>{
        return ResponseEntity.ok(incidentService.findAll())
    }

    /**
     * Endpoint para modificar la información de un incidente.
     * @param token Token de autorización.
     * @param updateBody Datos del incidente a modificar.
     * @return ResponseEntity con la información del incidente actualizada o un estado 404 en caso de no encontrarse.
     */
    @Operation(
        summary = "Modifica la información del incidente",
        description = "Modifica datos de un incidente en específico",
        security = [SecurityRequirement(name = "BearerAuth")],
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Operación realizada con éxito",
                content = [Content(
                    schema = Schema(implementation = Incidente::class),
                    mediaType = "aplication/json"
                )]
            ),
            ApiResponse(
                responseCode = "404",
                description = "No se encontró",
                content = [Content()]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Error inesperado",
                content = [Content()]
            ),
        ]
    )
    @PutMapping("/toolkit")
    fun updateStatus(@RequestHeader("Authorization") token: String,@RequestBody updateBody: UpdateBody): ResponseEntity<Incidente> {
        val response =  incidentService.updateStatus(updateBody.incidenteid!!,updateBody.estatus)
        if (response != null) {
            return ResponseEntity.ok(response)
        }
        return ResponseEntity.notFound().build()
    }

    /**
     * Endpoint para borrar un incidente.
     * @param token tóken de autorización.
     * @param updateBody Datos del incidente.
     * @return ResponseEntity con la información del incidente borrado al ser exitosa, o error si falla.
     */
    @Operation(
        summary = "Borrar un incidente",
        description = "Elimina un incidente del registro",
        security = [SecurityRequirement(name = "BearerAuth")],
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Incidente borrado con éxito.",
                content = [Content(
                    schema = Schema(implementation = Incidente::class),
                    mediaType = "aplication/json"
                )]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Incidente no encontrado",
                content = [Content()]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Ocurrió un error inesperado",
                content = [Content()]
            ),
        ]
    )
    @DeleteMapping("/toolkit")
    fun deleteStatus(@RequestHeader("Authorization") token: String,@RequestBody updateBody: UpdateBody): ResponseEntity<Int> {
        val response = incidentService.deleteIncident(updateBody.incidenteid!!)
        if (response != 0) {
            return ResponseEntity.ok(response)
        }
        return ResponseEntity.notFound().build()
    }

    @PostMapping("/user")
    fun getAllincidentsUsr(@RequestBody getBody: GetBody): ResponseEntity<List<Incidente>>{
        val response = incidentService.getUsr(getBody.clienteid)

        if(response != null){
            return ResponseEntity.ok(response)
        }
        return ResponseEntity.notFound().build()
    }
}