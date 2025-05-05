package com.backendcouncil_team.backendcouncil_api.photo.repository

import com.backendcouncil_team.backendcouncil_api.photo.repository.entity.Photo
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

/**
 * Interfaz que contiene las operaciones CRUD y queries para el manejo de las fotos en la base de datos.
 */
interface PhotoRepository : CrudRepository<Photo, Long> {
    /**
     * Función que busca todas las fotos de un incidente.
     * @param id ID del incidente a buscar.
     * @return Lista de fotos con su información.
     */
    @Query(value = "SELECT * FROM foto WHERE incidentid=?1", nativeQuery = true)
    fun findAllByIncidentId(id: Long): List<Photo>

    /**
     * Función que guarda una foto y la relaciona con un incidente.
     * @param id ID de la foto a guardar.
     * @param incidenteid ID del incidente al que se relaciona la foto.
     * @return `true` si se guardó correctamente, `false` si no.
     */
    @Query(value = "INSERT INTO foto (fotoid,incidenteid) VALUES (?1,?2)", nativeQuery = true)
    fun save (id:ByteArray, incidenteid:Long) : Boolean
}