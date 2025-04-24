package com.backendcouncil_team.backendcouncil_api.incident.repository

import com.backendcouncil_team.backendcouncil_api.incident.repository.entity.Incident
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

/**
 * Interfaz donde las funciones buscan realizar operaciones CRUD al modelo relacionado con incidentes con query's buscando una response.
 */

interface IncidentRepository: CrudRepository<Incident, Int> {

}