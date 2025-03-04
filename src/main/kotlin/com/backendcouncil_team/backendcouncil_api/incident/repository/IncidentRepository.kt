package com.backendcouncil_team.backendcouncil_api.incident.repository

import com.backendcouncil_team.backendcouncil_api.incident.repository.entity.Incident
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface IncidentRepository: CrudRepository<Incident, Int> {

}