package com.backendcouncil_team.backendcouncil_api.incident.controller


import com.backendcouncil_team.backendcouncil_api.incident.controller.body.IncidentBody
import com.backendcouncil_team.backendcouncil_api.incident.domain.Incidente
import com.backendcouncil_team.backendcouncil_api.incident.repository.entity.Incident
import com.backendcouncil_team.backendcouncil_api.incident.service.IncidentService
import com.backendcouncil_team.backendcouncil_api.user.domain.Usuario
import com.backendcouncil_team.backendcouncil_api.user.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping


@Controller
@RequestMapping("/v1/incident")
class IncidentController(var incidentService: IncidentService,var userService: UserService) {
    @PostMapping
    fun addIncident(@RequestHeader("Authorization") token: String, @RequestBody incidentBody: IncidentBody ): ResponseEntity<Incidente> {
        val user = userService.getInfoAboutMe(token.removePrefix("Bearer "))

        val incident = Incidente(
            clienteid = user?.clienteid!!,
            nombre =  incidentBody.nombre,
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
}