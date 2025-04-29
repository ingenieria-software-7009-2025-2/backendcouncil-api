package com.backendcouncil_team.backendcouncil_api.incident.controller


import com.backendcouncil_team.backendcouncil_api.incident.controller.body.IncidentBody
import com.backendcouncil_team.backendcouncil_api.incident.controller.body.UpdateBody
import com.backendcouncil_team.backendcouncil_api.incident.domain.Incidente
import com.backendcouncil_team.backendcouncil_api.incident.repository.entity.Incident
import com.backendcouncil_team.backendcouncil_api.incident.service.IncidentService
import com.backendcouncil_team.backendcouncil_api.user.domain.Usuario
import com.backendcouncil_team.backendcouncil_api.user.controller.body.UserBody
import com.backendcouncil_team.backendcouncil_api.user.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
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
        val user = userService.getInfoAboutMe(token.removePrefix("Bearer "))

        val incident = Incidente(
            clienteid = user?.clienteid!!,
            nombre =  incidentBody.nombre!!,
            descripcion =  incidentBody.descripcion,
            fecha = incidentBody.fecha,
            hora =  incidentBody.hora,
            latitud = incidentBody.latitud,
            longitud =  incidentBody.longitud,
            estado = "reportado",
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
    
    @GetMapping("/toolkit")
    fun getAll(): ResponseEntity<List<Incidente>>{
        return ResponseEntity.ok(incidentService.findAll())
    }

    @PutMapping("/toolkit")
    fun updateStatus(@RequestHeader("Authorization") token: String,@RequestBody updateBody: UpdateBody): ResponseEntity<Incidente> {
        val response =  incidentService.updateStatus(updateBody.incidenteid!!,updateBody.estatus)
        println(updateBody.incidenteid!!)
        println(updateBody.estatus)
        if (response != null) {
            return ResponseEntity.ok(response)
        }
        return ResponseEntity.notFound().build()
    }

    @DeleteMapping("/toolkit")
    fun deleteStatus(@RequestHeader("Authorization") token: String,@RequestBody updateBody: UpdateBody): ResponseEntity<Int> {
        val response = incidentService.deleteIncident(updateBody.incidenteid!!)
        if (response != null) {
            return ResponseEntity.ok(response)
        }
        return ResponseEntity.notFound().build()
    }
}