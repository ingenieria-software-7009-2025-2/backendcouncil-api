package com.backendcouncil_team.backendcouncil_api.incident.service

import com.backendcouncil_team.backendcouncil_api.incident.domain.Incidente
import com.backendcouncil_team.backendcouncil_api.incident.repository.IncidentRepository
import com.backendcouncil_team.backendcouncil_api.incident.repository.entity.Incident
import com.backendcouncil_team.backendcouncil_api.user.domain.Usuario
import org.apache.logging.log4j.util.Cast
import org.springframework.stereotype.Service
import java.time.Instant
@Service
class IncidentService(private val incidentRepository: IncidentRepository) {
    fun addIncident(cliente: Long ,incidente: Incidente): Incidente {
        val incidentDB =
            Incident(
                incidenteid = System.currentTimeMillis(),
                clienteid =  cliente,
                categoriaid = incidente.categoriaid,
                nombre = incidente.nombre,
                descripcion =  incidente.descripcion,
                fecha = incidente.fecha,
                hora = incidente.hora,
                longitud = incidente.longitud,
                latitud = incidente.latitud,
                estado = incidente.estado
            )
        val result = incidentRepository.save(incidentDB)

        return Incidente(
            incidenteid =  result.incidenteid,
            clienteid = result.clienteid,
            categoriaid = result.categoriaid,
            nombre = result.nombre,
            descripcion = result.descripcion,
            fecha = result.fecha,
            hora = result.hora,
            latitud =  result.latitud,
            longitud = result.longitud,
            estado = result.estado
        )
    }

    fun findAll(): List<Incidente> {
        val lista = incidentRepository.findAll()
        val respuesta : MutableList<Incidente> = mutableListOf()
        lista.forEach{
                incident ->
            respuesta.add(castIncident(incident))

        }
        return respuesta

    }

    fun castIncident(incident :Incident) : Incidente {
        return Incidente(
            incidenteid =  incident.incidenteid,
            clienteid = incident.clienteid,
            categoriaid = incident.categoriaid,
            nombre = incident.nombre,
            descripcion = incident.descripcion,
            fecha = incident.fecha,
            hora = incident.hora,
            latitud =  incident.latitud,
            longitud = incident.longitud,
            estado = incident.estado
        )
    }

}