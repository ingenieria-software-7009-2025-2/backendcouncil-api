package com.backendcouncil_team.backendcouncil_api.user.repository

import com.backendcouncil_team.backendcouncil_api.user.repository.entity.User
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository


interface UserRepository : CrudRepository<User, Int> {

    @Query(value = "SELECT * FROM cliente WHERE correo=?1", nativeQuery = true)
    fun findByEmail(email: String): User?

    @Query(value = "SELECT * FROM cliente WHERE username=?1", nativeQuery = true)
    fun findByUsername(username: String): User?

    @Query(value = "SELECT * FROM cliente WHERE correo=?1 AND password=?2", nativeQuery = true)
    fun findByEmailAndPassword(email: String, password: String): User?

    @Query(value = "SELECT * FROM cliente WHERE token=?1", nativeQuery = true)
    fun findByToken(token: String): User?

    @Query(value = "SELECT * FROM cliente WHERE username=?1 AND password=?2", nativeQuery = true)
    fun findByUserAndPassword(email: String, password: String): User?

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM cliente WHERE correo=?1 AND password=?2 AND token=?3", nativeQuery = true)
    fun deleteUser(email : String,password: String,token: String): Int
}