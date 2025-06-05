package com.backendcouncil_team.backendcouncil_api.coments.service;

import ch.qos.logback.core.net.server.Client
import com.backendcouncil_team.backendcouncil_api.coments.domain.Comentario
import com.backendcouncil_team.backendcouncil_api.coments.repository.ComentRepository
import com.backendcouncil_team.backendcouncil_api.coments.repository.entity.Coment
import org.springframework.stereotype.Service

@Service
class ComentService (private val comentRepository: ComentRepository, repository: ComentRepository) {

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

    fun like (coment: Long): Int{
        return comentRepository.giveLike(coment)
    }
    fun dislike (coment: Long): Int{
        return comentRepository.stoleLike(coment)
    }

    fun findAll (): List<Comentario> {
        val lista = comentRepository.findAll()
        val respuesta: MutableList<Comentario> = mutableListOf()

        lista.forEach {
            coment ->
            respuesta.add(castComent(coment))
        }
        return respuesta
    }

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
