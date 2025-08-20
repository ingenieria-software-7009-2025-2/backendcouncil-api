package com.backendcouncil_team.backendcouncil_api.coments.repository;

import com.backendcouncil_team.backendcouncil_api.coments.domain.Comentario
import com.backendcouncil_team.backendcouncil_api.coments.repository.entity.Coment
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

/**
 * Interfaz donde las funciones buscan realizar operaciones CRUD al modelo relacionado con comentarios con query's buscando una response.
 */
interface ComentRepository: CrudRepository<Coment, Int> {

    /**
     * Función que busca un comentario para sumar un like.
     * @param incidenteId ID del comentario.
     * @return Cantidad de likes.
     */
    @Modifying
    @Transactional
    @Query(value = "UPDATE comentario SET likes = likes + 1 WHERE comentarioid = ?1", nativeQuery = true)
    fun giveLike(incidentId : Long) : Int

     /**
     * Función que busca un comentario para restar un like.
     * @param incidenteId ID del comentario.
     * @return Cantidad de likes.
     */
    @Modifying
    @Transactional
    @Query(value = "UPDATE comentario SET likes = likes - 1 WHERE comentarioid = ?1 AND likes > 0", nativeQuery = true)
    fun stoleLike(incidentId : Long) : Int

    /**
     * Función que regresa todos los comentarios relacionados con un incidente.
     * @param incidenteId ID del incidente.
     * @return Lista de comentarios relacionados.
     */
    @Query(value = "SELECT * FROM comentario WHERE incidenteid = ?1", nativeQuery = true)
    fun findAllByIncidentId(incidentId: Long): List<Coment>
}
