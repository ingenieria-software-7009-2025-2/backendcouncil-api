package com.backendcouncil_team.backendcouncil_api.user.service

import com.backendcouncil_team.backendcouncil_api.user.domain.Usuario
import com.backendcouncil_team.backendcouncil_api.user.repository.UserRepository
import com.backendcouncil_team.backendcouncil_api.user.repository.entity.User
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.UUID

@Service
class UserService(private var userRepository: UserRepository) {


    fun addUser(usuario: Usuario): Usuario {

        //Convertimos el objeto del dominio al objeto que necesita nuestra BD
        val usuarioDB =
            User(
                clienteid = System.currentTimeMillis(),
                nombre = usuario.nombre,
                rolid = 2,
                apPaterno = usuario.apPaterno,
                apMaterno = usuario.apMaterno,
                password = usuario.password!!,
                mail = usuario.correo,
                token = usuario.token,
                username = usuario.userName

            )

        val emailSearch = userRepository.findByEmail(usuarioDB.mail)
        println(emailSearch)
        val userNameSearch = userRepository.findByUsername(usuarioDB.username)

        if (emailSearch != null || userNameSearch != null) {
            val result = Usuario(
                clienteid = 0,
                rolid = 1,
                nombre = usuario.nombre,
                apPaterno = usuario.apPaterno,
                apMaterno = usuario.apMaterno,
                password = usuario.password!!,
                correo = usuario.correo,
                token = usuario.token,
                userName = usuario.userName

            )
            return result
        }
        val result = userRepository.save(usuarioDB)
        // Convertimos el objeto de nuestra BD a un objeto de nuestro dominio.
        val usuarioCreado = Usuario(
            clienteid = result.clienteid,
            rolid = result.rolid,
            nombre = result.nombre,
            apPaterno = result.apPaterno,
            apMaterno = result.apMaterno,
            correo = result.mail,
            token = result.token,
            password = result.password,
            userName = result.username,
            isActive = false
        )
        return usuarioCreado
    }


    fun retrieveAllUser(): List<Usuario> {

        val myUsers = mutableListOf<Usuario>()

        val result = userRepository.findAll()

        result.forEach { user: User ->
            // Convertimos el objeto de nuestra BD a un objeto de nuestro dominio.
            val userFound = Usuario(
                clienteid = user.clienteid,
                rolid = user.rolid,
                nombre = user.nombre,
                apPaterno = user.apPaterno,
                apMaterno = user.apMaterno,
                correo = user.mail,
                token = user.token,
                password = user.password,
                userName = user.username,
                isActive = false
            )

            myUsers.add(userFound)
        }

        return myUsers
    }

    fun delete(mail:String, password: String, token: String) : Int {
        val response = userRepository.deleteUser(mail, password, token)

        return response
    }

    fun login(mail: String, password: String): Usuario? {
        val userFound = userRepository.findByEmailAndPassword(mail, password)

        return if (userFound != null) {
            if (userFound.token != null){
                return null
            }
            val token = UUID.randomUUID().toString()
            updateTokenUser(userFound, token)
            Usuario(
                clienteid = userFound.clienteid,
                rolid = userFound.rolid,
                nombre = userFound.nombre,
                apPaterno =  userFound.apPaterno,
                apMaterno = userFound.apMaterno,
                correo = userFound.mail,
                token = token,
                password = userFound.password,
                userName = userFound.username,
                isActive = false
            )
        } else {
            userFound
        }

    }

    fun loginUser(user: String, password: String): Usuario? {
        val userFound = userRepository.findByUserAndPassword(user, password)


        return if (userFound != null) {
            if (userFound.token != null){
                return null
            }
            val token = UUID.randomUUID().toString()
            updateTokenUser(userFound, token)
            Usuario(
                clienteid = userFound.clienteid,
                rolid = userFound.rolid,
                nombre = userFound.nombre,
                apPaterno =  userFound.apPaterno,
                apMaterno = userFound.apMaterno,
                correo = userFound.mail,
                token = token,
                password = userFound.password,
                userName = userFound.username,
                isActive = false
            )
        } else {
            userFound
        }

    }

    fun updateTokenUser(user: User, token: String) {
        user.token = token
        userRepository.save(user)
    }


    fun logout(token: String): Boolean {

        val userFound = userRepository.findByToken(token)

        if (userFound != null) {
            userFound.token = null
            userRepository.save(userFound)
            return true
        } else return false
    }

    fun getInfoAboutMe(token: String): Usuario?{
        val userFound = userRepository.findByToken(token)

        if (userFound != null) {
            return Usuario(
                clienteid = userFound.clienteid,
                rolid = userFound.rolid,
                nombre = userFound.nombre,
                apPaterno =  userFound.apPaterno,
                apMaterno = userFound.apMaterno,
                correo = userFound.mail,
                token = "*******",
                password = userFound.password,
                userName = userFound.username,
                isActive = false
            )
        } else return null
    }

    fun update (token: String, usuario: Usuario): Usuario? {
        val userFound = userRepository.findByToken(token)
        if (userFound != null) {
            println("de postman: " + usuario.userName)
            println(userFound.username)
            val newUser = User(
                clienteid = userFound.clienteid,
                rolid = userFound.rolid,
                username = obtenerNoVacio(usuario.userName,userFound.username),
                nombre = obtenerNoVacio(usuario.nombre,userFound.nombre),
                apPaterno = obtenerNoVacio(usuario.apPaterno,userFound.apPaterno),
                apMaterno = obtenerNoVacio(usuario.apMaterno,userFound.apMaterno),
                mail = obtenerNoVacio(usuario.correo,userFound.mail),
                password = obtenerNoVacio(usuario.password!!,userFound.password),
                token = userFound.token,

            )
            userRepository.save(newUser)
            return Usuario(
                clienteid = newUser.clienteid,
                rolid = newUser.rolid,
                nombre = newUser.nombre,
                apPaterno = newUser.apPaterno,
                apMaterno = newUser.apMaterno,
                correo = newUser.mail,
                password = "********",
                token = newUser.token,
                userName = newUser.username,
                isActive = false
            )
        } else return null
    }
    fun obtenerNoVacio(primero: String, segundo: String): String {
        return if (primero.isNotEmpty()) primero else segundo
    }
}