package com.backendcouncil_team.backendcouncil_api.coments.repository;

import com.backendcouncil_team.backendcouncil_api.coments.domain.Comentario
import com.backendcouncil_team.backendcouncil_api.coments.repository.entity.Coment
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface ComentRepository: CrudRepository<Coment, Int> {
    @Modifying
    @Transactional
    @Query(value = "UPDATE comentario SET likes = likes + 1 WHERE comentarioid = ?1", nativeQuery = true)
    fun giveLike(incidentId : Long) : Int

    @Modifying
    @Transactional
    @Query(value = "UPDATE comentario SET likes = likes - 1 WHERE comentarioid = ?1 AND likes > 0", nativeQuery = true)
    fun stoleLike(incidentId : Long) : Int

    @Query(value = "SELECT * FROM comentario WHERE incidenteid = ?1", nativeQuery = true)
    fun findAllByIncidentId(incidentId: Long): List<Coment>
}
