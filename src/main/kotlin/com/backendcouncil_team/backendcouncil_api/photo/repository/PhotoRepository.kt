package com.backendcouncil_team.backendcouncil_api.photo.repository

import com.backendcouncil_team.backendcouncil_api.photo.repository.entity.Photo
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository


interface PhotoRepository : CrudRepository<Photo, Long> {
    @Query(value = "SELECT * FROM foto WHERE incidentid=?1", nativeQuery = true)
    fun findAllByIncidentId(id: Long): List<Photo>

    @Query(value = "INSERT INTO foto (fotoid,incidenteid) VALUES (?1,?2)", nativeQuery = true)
    fun save (id:ByteArray, incidenteid:Long) : Boolean
}