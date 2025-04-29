package com.backendcouncil_team.backendcouncil_api.photo.service

import com.backendcouncil_team.backendcouncil_api.photo.controller.Body.PhotoBody
import com.backendcouncil_team.backendcouncil_api.photo.domain.Foto
import com.backendcouncil_team.backendcouncil_api.photo.repository.PhotoRepository
import com.backendcouncil_team.backendcouncil_api.photo.repository.entity.Photo
import com.backendcouncil_team.backendcouncil_api.user.domain.Usuario
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class PhotoService(private var photoRepository: PhotoRepository) {

    fun getPhotos(photo: PhotoBody): List<Foto>? {
        val result = photoRepository.findAllByIncidentId(photo.incidenteid.toLong())

        val respuesta : MutableList<Foto> = mutableListOf()

        if (result.isEmpty()) {
            return null
        }

        result.forEach{
                foto ->
            respuesta.add(castPhoto(foto))
        }

        return respuesta
    }

    fun addPhoto(photo: PhotoBody): Foto{

        val foto = Photo(
            fotoid = photo.fotoid,
            incideteid = photo.incidenteid.toLong()
        )
        println("antes de hacer la query")
        val result = photoRepository.save(foto)

        return Foto(
            fotoid = result.fotoid,
            incidenteid = result.incideteid
        )
    }

    fun castPhoto(photo: Photo): Foto {
        return Foto(
            fotoid = photo.fotoid,
            incidenteid = photo.incideteid
        )
    }
}