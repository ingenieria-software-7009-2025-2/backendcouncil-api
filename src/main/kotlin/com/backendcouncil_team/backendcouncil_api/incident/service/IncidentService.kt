package com.backendcouncil_team.backendcouncil_api.incident.service

import com.backendcouncil_team.backendcouncil_api.incident.domain.Estado
import com.backendcouncil_team.backendcouncil_api.incident.domain.Incidente
import com.backendcouncil_team.backendcouncil_api.incident.repository.IncidentRepository
import com.backendcouncil_team.backendcouncil_api.incident.repository.entity.Incident
import org.springframework.stereotype.Service

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
                estado = Estado.reportado
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
            estado = result.estado!!,
            likes = result.likes
        )
    }

    /**
     * Función que obtiene todos los incidentes.
     * @return Lista de incidentes.
     */
    fun findAll(): List<Incidente> {
        val lista = incidentRepository.findAll()
        val respuesta : MutableList<Incidente> = mutableListOf()
        lista.forEach{
                incident ->
            respuesta.add(castIncident(incident))

        }
        return respuesta

    }

    fun like(incidenteid: Long): Int{
        return incidentRepository.giveLike(incidenteid)
    }

    fun dislike(incidenteid: Long): Int{
        return incidentRepository.stoleLike(incidenteid)
    }

    fun getUsr(cliente: Long):List<Incidente>{
        val lista = incidentRepository.findByClienteId(cliente)
        val respuesta : MutableList<Incidente> = mutableListOf()
        lista?.forEach{ incident ->
            respuesta.add(castIncident(incident))

        }
        return respuesta

    }
    /**
     * Función que obtiene un incidente por su ID.
     * @param id ID del incidente a buscar.
     * @return Incidente vinculado al ID.
     */
    fun findbyIncidentId(id : Long) : Incidente? {
        val response = incidentRepository.findByIncidentId(id)
        if (response != null) {
            return castIncident(response)
        }
        return null
    }

    /**
     * Función que actualiza el estado de un incidente.
     * @param id ID del incidente a actualizar.
     * @param status Nuevo estado del incidente.
     * @return Incidente actualizado.
     */
    fun updateStatus(id: Long, status: String) : Incidente? {
        val afectado = findbyIncidentId(id)

        if (afectado != null) {
            afectado.estado = castEnum(status)
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
                estado = afectado.estado,
            )
            incidentRepository.save(newIncident)
            return afectado
        }
        return null
    }

    fun castEnum(estado: String): Estado {
        if (estado.equals("reportado", true)) {
            return Estado.reportado
        } else if (estado.equals("revision", true)) {
            return Estado.revision
        } else if (estado.equals("resuelto", true)) {
            return Estado.resuelto
        }
        return Estado.reportado
    }
    /**
     * Función que elimina un incidente por su ID.
     * @param id ID del incidente a eliminar.
     * @return 1 si se eliminó, 0 si no se eliminó.
     */
    fun deleteIncident(id : Long) : Int {
        return incidentRepository.deleteIncident(id)
    }

    /**
     * Función que convierte un incidente de la base de datos a un dominio.
     * @param incident Incidente a convertir.
     * @return Incidente convertido.
     */
    fun castIncident(incident :Incident) : Incidente {
        println(incident.toString())
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
            estado = incident.estado!!,
            likes = incident.likes
        )
    }

}