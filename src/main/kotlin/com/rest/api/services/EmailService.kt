package com.rest.api.services

import com.rest.api.domain.entities.ContactEntity
import com.rest.api.domain.entities.EmailEntity
import com.rest.api.domain.repositories.EmailRepository
import com.rest.api.dto.internal.EmailDto
import com.rest.api.exceptions.NotFoundException
import com.rest.api.marshallers.internal.EmailMarshaller
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service

@Service("emailService")
class EmailService @Autowired
constructor(private val emailRepository: EmailRepository) {

    fun create(emailDto: EmailDto, contact: ContactEntity): EmailDto {
        val emailEntity = EmailMarshaller.emailDtoToEmailEntity(emailDto, contact)
        return EmailMarshaller.emailEntityToEmailDto(
                emailRepository.save(emailEntity)
        )
    }

    fun findById(id: Long): EmailDto {
        val emailEntity = emailRepository.findById(id)
                .orElseThrow { NotFoundException("Email", "id", id) }

        return EmailMarshaller.emailEntityToEmailDto(emailEntity)
    }

    fun deleteById(id: Long) {
        try {
            emailRepository.deleteById(id)
        } catch (exception: EmptyResultDataAccessException) {
            throw NotFoundException("Email", "id", id)
        }
    }

    fun deleteByContactId(contact: ContactEntity) {
        try {
            val emailDtoList = findByContactId(contact.id)
            val emailEntities = emailDtoList.map { EmailMarshaller.emailDtoToEmailEntity(it, contact) }

            emailRepository.deleteAll(emailEntities)
        } catch (exception: EmptyResultDataAccessException) {
            throw NotFoundException("Email", "contact_id", contact)
        }
    }

    fun update(emailDto: EmailDto, id: Long): EmailDto {
        val emailEntity = emailRepository.findById(id).orElseThrow { NotFoundException("Email", "id", id) }

        merge(emailEntity, emailDto)

        val emailEntityResponse = emailRepository.save(emailEntity)
        return EmailMarshaller.emailEntityToEmailDto(emailEntityResponse)
    }

    fun updateOrCreateByContactId(contactEntity: ContactEntity, emails: List<EmailDto>): List<EmailDto> {
        if (emails.isEmpty()) return findByContactId(contactEntity.id)

        for (rowEmailDto in emails) {
            if (rowEmailDto.id > 0) {
                update(rowEmailDto, rowEmailDto.id)
            } else {
                val createEmailDto = EmailMarshaller.setEmailDto(
                        contactId = contactEntity.id,
                        value = rowEmailDto.value
                )
                create(createEmailDto, contactEntity)
            }
        }

        return findByContactId(contactEntity.id)
    }

    fun findByContactId(contactId: Long): List<EmailDto> {
        val emailEntityList = emailRepository.findByContactId(contactId)
                .orElseThrow { NotFoundException("Email", "contact_id", contactId) }

        return emailEntityList.map { EmailMarshaller.emailEntityToEmailDto(it) }
    }

    fun findByValue(value: String): EmailDto {
        val emailEntity = emailRepository.findByValue(value)
                .orElseThrow { NotFoundException("Email", "value", value) }

        return EmailMarshaller.emailEntityToEmailDto(emailEntity)
    }

    private fun merge(emailEntity: EmailEntity, emailDto: EmailDto) {
        if (emailDto.contactId > 0) emailEntity.contact.id = emailDto.contactId
        emailEntity.value = emailDto.value
    }
}
