package com.backendcouncil_team.backendcouncil_api.photo.controller

import com.backendcouncil_team.backendcouncil_api.photo.controller.Body.PhotoBody
import com.backendcouncil_team.backendcouncil_api.photo.domain.Foto
import com.backendcouncil_team.backendcouncil_api.photo.repository.entity.Photo
import com.backendcouncil_team.backendcouncil_api.photo.service.PhotoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView


@Controller
@RequestMapping("/v1/photos")
class PhotoController(var photoService: PhotoService) {

    @GetMapping
    fun getPhotos(@RequestBody photo: PhotoBody): ResponseEntity<List<Foto>> {
        return ResponseEntity.ok(photoService.getPhotos(photo))
    }

    @PostMapping
    fun addPhoto(@RequestBody photo: PhotoBody): ResponseEntity<Any>{
        println("entro aqui")
        val response = photoService.addPhoto(photo)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }
}