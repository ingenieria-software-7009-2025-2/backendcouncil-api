package com.backendcouncil_team.backendcouncil_api.coments.service;

import ch.qos.logback.core.net.server.Client
import com.backendcouncil_team.backendcouncil_api.coments.controller.body.UpdateComBody
import com.backendcouncil_team.backendcouncil_api.coments.domain.Comentario
import com.backendcouncil_team.backendcouncil_api.coments.repository.ComentRepository
import com.backendcouncil_team.backendcouncil_api.coments.repository.entity.Coment
import org.springframework.stereotype.Service

/**
 * Clase de servicio referente a comentarios.
 * @property comentRepository Repository que contiene las queries.
 */
@Service
class ComentService (private val comentRepository: ComentRepository, repository: ComentRepository) {

    /**
     * Función que agrega un comentario.
     * @param client ID del cliente.
     * @param incidente ID del incidente.
     * @param texto Contenido del comentario.
     * @return El comentario creado.
     */
    fun addComent(client: String, incidente:String, texto:String ): Comentario {
        val comentario = Coment(
            comentarioid = System.currentTimeMillis(),
            clienteid = client.toLong(),
            incidenteid = incidente.toLong(),
            contenido = texto,
            likes = 0.toBigDecimal()
        )

        val result = comentRepository.save(comentario)

        return Comentario(
            comentarioid = result.comentarioid,
            clienteid = result.clienteid,
            incidente = result.incidenteid,
            contenido = result.contenido,
            likes = result.likes,
        )
    }

    /**
     * Función que suma un like al comentario.
     * @param coment ID del comentario.
     * @return Cantidad de likes actualizada del comentario.
     */
    fun like (coment: Long): Int{
        println(coment)
        return comentRepository.giveLike(coment)
    }

    /**
     * Función que resta un like al comentario.
     * @param coment ID del comentario.
     * @return Cantidad de likes actualizada del comentario.
     */
    fun dislike (coment: Long): Int{
        return comentRepository.stoleLike(coment)
    }

    /**
     * Función que regresa todos los comentarios.
     * @return Lista con todos los comentarios.
     */
    fun findAll (): List<Comentario> {
        val lista = comentRepository.findAll()
        val respuesta: MutableList<Comentario> = mutableListOf()

        lista.forEach {
            coment ->
            respuesta.add(castComent(coment))
        }
        return respuesta
    }

    /**
     * Función que regresa todos los comentarios relacionados con un incidente.
     * @param coment ID del incidente.
     * @return Lita de comentarios relacionados al incidente.
     */
    fun getusr(coment: Long): List<Comentario> {
        val lista  = comentRepository.findAllByIncidentId(coment)
        val respuesta: MutableList<Comentario> = mutableListOf()

        lista.forEach {
            coment ->
            respuesta.add(castComent(coment))
        }
        return respuesta
    }

    /**
     * Función que convierte un comentario de la base de datos a un dominio.
     * @param coment Comentario a convertir.
     * @return Comentario convertido.
     */
    fun castComent(coment: Coment): Comentario {
        return Comentario(
            comentarioid = coment.comentarioid,
            incidente = coment.incidenteid,
            clienteid = coment.clienteid,
            contenido = coment.contenido,
            likes = coment.likes,
        )
    }
}
