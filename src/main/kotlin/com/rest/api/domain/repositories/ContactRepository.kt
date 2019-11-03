package com.rest.api.domain.repositories

import com.rest.api.domain.entities.ContactEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

import java.util.Optional

@Repository
interface ContactRepository : JpaRepository<ContactEntity, Long> {

    fun findByUserId(userId: Long): Optional<ContactEntity>

    fun countByUserId(userId: Long): Long
}