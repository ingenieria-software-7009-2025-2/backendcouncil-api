package com.backendcouncil_team.backendcouncil_api.user.repository

import com.backendcouncil_team.backendcouncil_api.user.repository.entity.User
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

/**
 * Interfaz donde las funciones buscan realizar operaciones CRUD al modelo relacionado con usuarios con query's buscando una response.
 */
interface UserRepository : CrudRepository<User, Int> {

    /**
     * Función que busca un cliente por email.
     * @param email Correo electrónico del cliente a buscar.
     * @return Entidad si se encontró o `NULL` si el cliente no existe.
     */
    @Query(value = "SELECT * FROM cliente WHERE correo=?1", nativeQuery = true)
    fun findByEmail(email: String): User?

    /**
     * Función que busca un cliente por username.
     * @param username Nombre de usuario del cliente a buscar.
     * @return Entidad si se encontró o `NULL` si el cliente no existe.
     */
    @Query(value = "SELECT * FROM cliente WHERE username=?1", nativeQuery = true)
    fun findByUsername(username: String): User?

    /**
     * Función que busca un cliente por email y password.
     * @param email Correo electrónico del cliente a buscar.
     * @param password Contraseña del cliente a buscar.
     * @return Entidad si se encontró o `NULL` si el cliente no existe.
     */
    @Query(value = "SELECT * FROM cliente WHERE correo=?1 AND password=?2", nativeQuery = true)
    fun findByEmailAndPassword(email: String, password: String): User?

    /**
     * Función que busca un cliente por tóken.
     * @param token Tóken del cliente a buscar.
     * @return Entidad si se encontró o `NULL` si el cliente no existe.
     */
    @Query(value = "SELECT * FROM cliente WHERE token=?1", nativeQuery = true)
    fun findByToken(token: String): User?

    /**
     * Función que busca un cliente por username y contraseña.
     * @param email Nombre de usuario del cliente a buscar.
     * @param password Contraseña del cliente a buscar.
     * @return Entidad si se encontró o `NULL` si el cliente no existe.
     */
    @Query(value = "SELECT * FROM cliente WHERE username=?1 AND password=?2", nativeQuery = true)
    fun findByUserAndPassword(email: String, password: String): User?

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM cliente WHERE correo=?1 AND password=?2 AND token=?3", nativeQuery = true)
    fun deleteUser(email : String,password: String,token: String): Int

    @Query(value = "SELECT * FROM cliente", nativeQuery = true)
    override fun findAll(): List<User>
}

