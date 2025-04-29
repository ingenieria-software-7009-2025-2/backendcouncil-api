package com.backendcouncil_team.backendcouncil_api.incident.repository

import com.backendcouncil_team.backendcouncil_api.incident.repository.entity.Incident
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface IncidentRepository: CrudRepository<Incident, Int> {

    @Query(value = "SELECT * FROM incidente", nativeQuery = true)
    override fun findAll() : List<Incident>

    @Query(value = "SELECT * FROM incidente WHERE incidenteid=?1", nativeQuery = true)
    fun findByIncidentId(id : Long) : Incident?

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM incidente WHERE incidenteid=?1", nativeQuery = true)
    fun deleteIncident(id : Long) : Int


}