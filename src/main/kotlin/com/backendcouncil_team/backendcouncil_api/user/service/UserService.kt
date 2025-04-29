package com.backendcouncil_team.backendcouncil_api.user.service

import com.backendcouncil_team.backendcouncil_api.user.domain.Usuario
import com.backendcouncil_team.backendcouncil_api.user.repository.UserRepository
import com.backendcouncil_team.backendcouncil_api.user.repository.entity.User
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.UUID

/**
 * Clase de servicio referente a clientes/usuarios.
 * @property userRepository Repository que contiene las queries.
 */
@Service
class UserService(private var userRepository: UserRepository) {

    /**
     * Función usada para agregar un usuario.
     * @param usuario Dominio de un cliente/usuario.
     * @return Dominio del usuario creado.
     */
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
        
        val userNameSearch = userRepository.findByUsername(usuarioDB.username)

        if (emailSearch != null) {
            val result = Usuario(
                clienteid = 1,
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
        } else if (userNameSearch != null) {
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

    /**
     * Función que regresa a todos los usuarios.
     * @return Lista de dominios de usuarios.
     */
    fun validarUsername (nombre : String): Boolean {
        val resultado = userRepository.findByUsername(nombre)
        return resultado != null
    }

    fun validarMail (nombre : String): Boolean {
        val resultado = userRepository.findByEmail(nombre)
        return resultado != null
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

    /**
     * Función que borra un usuario.
     * @param mail email del usuario a eliminar.
     * @param password contraseña del usuario a eliminar.
     * @param token token del Usuario a eliminar.
     * @return Dominio de usuario recién borrado o `NULL` si no se encontró o no se pudo autenticar.
     */
    fun delete(mail:String, password: String, token: String) : Int {
        val response = userRepository.deleteUser(mail, password, token)

        return response
    }


    /**
     * Hace un intento de log-in sobre los datos brindados.
     * @param user Nombre de usuario potencial.
     * @param password Contraseña del usuario potencial.
     * @return Dominio de usuario creado o `NULL` si no fue exitoso.
     */

    fun login(mail: String, password: String): Usuario? {
        val userFound = userRepository.findByEmailAndPassword(mail, password)

        return if (userFound != null) {
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

    /**
     * Hace un intento de log-in sobre los datos brindados.
     * @param user Nombre de usuario potencial.
     * @param password Contraseña del usuario potencial.
     * @return Dominio de usuario creado o `NULL` si no fue exitoso.
     */
    fun loginUser(user: String, password: String): Usuario? {
        val userFound = userRepository.findByUserAndPassword(user, password)


        return if (userFound != null) {
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

    /**
     * Función que updatea el token de un usuario.
     * @param user Entidad de usuario con los nuevos datos.
     * @param token Tóken nuevo.
     */
    fun updateTokenUser(user: User, token: String) {
        user.token = token
        userRepository.save(user)
    }


    /**
     * Hace log-out de la cuenta activa.
     * @param token Tóken de la cuenta del cliente activa.
     * @return Boolean si la cuenta pudo ser cerrada.
     */
    fun logout(token: String): Boolean {

        val userFound = userRepository.findByToken(token)

        if (userFound != null) {
            userFound.token = null
            userRepository.save(userFound)
            return true
        } else return false
    }

    /**
     * Regresa toda la información de un cliente dado un tóken.
     * @param token Tóken del usuario.
     * @return Dominio de usuario.
     */
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

    /**
     * Función que hace una actualización de los datos de un usuario, según su tóken.
     * @param token Tóken del usuario.
     * @param Usuario Dominio del usuario con la actualización de datos.
     * @return Dominio de usuario con los datos actualizados.
     */
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

    /**
     * Función que dados dos strings devuelve el primer parámetro, si no es vacío, en otro caso, devuelve el segundo.
     * @param primero Cadena, usada para veríficar si es vacía.
     * @param segundo Cadena.
     * @return Si `primero` no es vacía, la regresa; en otro caso regresa `segundo`.
     */
    fun obtenerNoVacio(primero: String, segundo: String): String {
        return if (primero.isNotEmpty()) primero else segundo
    }

    fun findAll(): List<Usuario> {
        val lista = userRepository.findAll()
        val respuesta : MutableList<Usuario> = mutableListOf()
        lista.forEach{
            user ->
            respuesta.add(castUser(user))
        }

        return  respuesta


    }

    fun updateRol (username : String, rol : Int ) : Usuario {
        val userFound = userRepository.findByUsername(username)
        if (userFound != null) {
            val newUser = User(
                clienteid = userFound.clienteid,
                rolid = rol,
                username = userFound.username,
                nombre = userFound.nombre,
                apPaterno = userFound.apPaterno,
                apMaterno = userFound.apMaterno,
                mail = userFound.mail,
                password = userFound.password,
                token = userFound.token,
                )
            userRepository.save(newUser)

            return Usuario(
                clienteid = newUser.clienteid,
                rolid = newUser.rolid,
                nombre = newUser.nombre,
                apPaterno = newUser.apPaterno,
                apMaterno = newUser.apMaterno,
                correo =  newUser.mail,
                password = "*******",
                token = newUser.token,
                userName =  newUser.username,
            )
        } else {
            userFound
        }
        return Usuario(
            clienteid = 0,
            rolid = 0,
            nombre = "",
            apPaterno = "",
            apMaterno = "",
            correo =  "",
            password = "*******",
            token = "",
            userName =  "",
        )
    }

    fun castUser (user: User) : Usuario {
        return Usuario(
            clienteid = user.clienteid,
            rolid = user.rolid,
            nombre = user.nombre,
            apPaterno = user.apPaterno,
            apMaterno = user.apMaterno,
            correo = user.mail,
            password = "********",
            token = user.token,
            userName = user.username,
            isActive = false
        )
    }

    fun getPassword(token : String) : String{
        val respuesta =  userRepository.findByToken(token)
        return respuesta?.password ?: ""
    }

}