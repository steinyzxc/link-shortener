package ru.steiny.cc.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.steiny.cc.model.User

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findNullableByLogin(login: String): User?

    fun findByLogin(login: String): User

    fun findNullableById(id: Long): User?

}