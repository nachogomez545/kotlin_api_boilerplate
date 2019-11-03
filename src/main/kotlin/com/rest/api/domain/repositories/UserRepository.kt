package com.rest.api.domain.repositories

import com.rest.api.domain.entities.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UserRepository : JpaRepository<UserEntity, Long> {
    fun findByUsernameAndDeletedFalse(username: String): Optional<UserEntity>

    fun findByUsername(username: String): Optional<UserEntity>
}