package com.backendcouncil_team.backendcouncil_api.photo.service

import com.backendcouncil_team.backendcouncil_api.photo.controller.Body.PhotoBody
import com.backendcouncil_team.backendcouncil_api.photo.domain.Foto
import com.backendcouncil_team.backendcouncil_api.photo.repository.PhotoRepository
import com.backendcouncil_team.backendcouncil_api.photo.repository.entity.Photo
import com.backendcouncil_team.backendcouncil_api.user.domain.Usuario
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

/**
 * Clase de servicio de gestión de operaciones referente a fotos.
 * @property photoRepository Repository que contiene las queries.
 */
@Service
class PhotoService(private var photoRepository: PhotoRepository) {

    /**
     * Función que obtiene todas las fotos de un incidente.
     * @param photo Dominio de foto con el ID del incidente.
     * @return Lista de fotos con su información.
     */
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

    /**
     * Función que agrega una foto a un incidente.
     * @param photo Dominio de foto con la información a agregar.
     * @return Dominio de la foto creada.
     */
    fun addPhoto(photo: PhotoBody): Foto{

        val foto = Photo(
            fotoid = photo.fotoid,
            incideteid = photo.incidenteid.toLong()
        )
        val result = photoRepository.save(foto)

        return Foto(
            fotoid = result.fotoid,
            incidenteid = result.incideteid
        )
    }

    /**
     * Función que convierte un objeto photo de la base de datos a un objeto de dominio.
     * @param photo Objeto photo recibido de la base de datos.
     * @return Dominio de la foto con la información organizada.
     */
    fun castPhoto(photo: Photo): Foto {
        return Foto(
            fotoid = photo.fotoid,
            incidenteid = photo.incideteid
        )
    }
}