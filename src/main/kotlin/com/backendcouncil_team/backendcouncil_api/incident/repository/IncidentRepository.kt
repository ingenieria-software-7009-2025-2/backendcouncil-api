package com.backendcouncil_team.backendcouncil_api.incident.repository

import com.backendcouncil_team.backendcouncil_api.incident.repository.entity.Incident
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

/**
 * Interfaz donde las funciones buscan realizar operaciones CRUD al modelo relacionado con incidentes con query's buscando una response.
 */
interface IncidentRepository: CrudRepository<Incident, Int> {

    /**
     * Función que obtiene todos los incidentes.
     * @return Lista de incidentes.
     */
    @Query(value = "SELECT * FROM incidente", nativeQuery = true)
    override fun findAll() : List<Incident>

    /**
     * Función que obtiene un incidente por su ID.
     * @param id ID del incidente a buscar.
     * @return Incidente con el ID buscado.
     */
    @Query(value = "SELECT * FROM incidente WHERE incidenteid=?1", nativeQuery = true)
    fun findByIncidentId(id : Long) : Incident?

    /**
     * Función que elimina un incidente por su ID.
     * @param id ID del incidente a eliminar.
     * @return 1 si se eliminó correctamente, 0 si no se eliminó.
     */
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM incidente WHERE incidenteid=?1", nativeQuery = true)
    fun deleteIncident(id : Long) : Int

    /**
     * Función que busca un incidente para sumar un like.
     * @param incidenteId ID del incidente.
     * @return Cantidad de likes.
     */
    @Modifying
    @Transactional
    @Query(value = "UPDATE incidente SET likes = likes + 1 WHERE incidenteid = ?1", nativeQuery = true)
    fun giveLike(incidentId : Long) : Int

    /**
     * Función que busca un incidente para restarle un like.
     * @param incidenteId ID del incidente.
     * @return Cantidad de likes.
     */
    @Modifying
    @Transactional
    @Query(value = "UPDATE incidente SET likes = likes - 1 WHERE incidenteid = ?1 AND likes > 0", nativeQuery = true)
    fun stoleLike(incidentId : Long) : Int

    /**
     * Función que busca todos los incidentes relacionados con un autor.
     * @param clienteId ID del cliente.
     * @return Lista de incidentes con el autor dado.
     */
    @Query(value = "SELECT * FROM incidente where clienteid=?1", nativeQuery = true)
    fun findByClienteId(clienteId : Long) : List<Incident>?
}