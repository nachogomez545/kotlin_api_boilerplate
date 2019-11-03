package com.rest.api.domain.repositories

import com.rest.api.domain.entities.EmailEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface EmailRepository : JpaRepository<EmailEntity, Long> {

    fun findByContactId(contactId: Long): Optional<List<EmailEntity>>

    fun findByValue(value: String): Optional<EmailEntity>
}