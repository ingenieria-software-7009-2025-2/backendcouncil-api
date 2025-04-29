package com.backendcouncil_team.backendcouncil_api.incident.service

import com.backendcouncil_team.backendcouncil_api.incident.domain.Incidente
import com.backendcouncil_team.backendcouncil_api.incident.repository.IncidentRepository
import com.backendcouncil_team.backendcouncil_api.incident.repository.entity.Incident
import com.backendcouncil_team.backendcouncil_api.user.domain.Usuario
import org.apache.logging.log4j.util.Cast
import org.springframework.stereotype.Service
import java.time.Instant

/**
 * Clase de servicio referente a incidentes.
 * @property incidentRepository Repository que contiene las queries.
 */
@Service
class IncidentService(private val incidentRepository: IncidentRepository) {

    /**
     * Función que agrega un incidente.
     * @param cliente ID de cliente qeu reporta.
     * @param incidente Dominio de incidente con los datos a agregar.
     * @return El incidente que se ha creado.
     */
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

    fun findbyIncidentId(id : Long) : Incidente? {
        val response = incidentRepository.findByIncidentId(id)
        if (response != null) {
            return castIncident(response)
        }
        return null
    }

    fun updateStatus(id: Long, status: String) : Incidente? {
        val afectado = findbyIncidentId(id)

        if (afectado != null) {
            afectado.estado = status
            val newIncident = Incident(
                incidenteid = id,
                clienteid = afectado.clienteid,
                categoriaid = afectado.categoriaid,
                nombre = afectado.nombre,
                descripcion = afectado.descripcion,
                fecha = afectado.fecha,
                hora = afectado.hora,
                longitud = afectado.longitud,
                latitud = afectado.latitud,
                estado = afectado.estado
            )
            incidentRepository.save(newIncident)
            return afectado
        }
        return null
    }

    fun deleteIncident(id : Long) : Int {
        return incidentRepository.deleteIncident(id)
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