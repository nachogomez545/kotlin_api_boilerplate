package com.rest.api.services

import com.rest.api.domain.entities.ContactEntity
import com.rest.api.domain.entities.UserEntity
import com.rest.api.domain.repositories.ContactRepository
import com.rest.api.dto.internal.ContactDto
import com.rest.api.enums.ErrorCodes
import com.rest.api.exceptions.BadRequestException
import com.rest.api.exceptions.NotFoundException
import com.rest.api.marshallers.internal.ContactMarshaller
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service

import javax.validation.Valid

@Service("contactService")
class ContactService @Autowired
constructor(private val contactRepository: ContactRepository) {

    fun create(contactDto: ContactDto, user: UserEntity): ContactDto {
        this.validateCreationSchema(contactDto)

        val contactEntity = ContactMarshaller.contactDtoToContactEntity(contactDto, user)
        return ContactMarshaller.contactEntityToContactDto(
                contactRepository.save(contactEntity)
        )
    }

    fun findById(id: Long): ContactDto {
        val contactEntity = contactRepository.findById(id)
                .orElseThrow { NotFoundException("Contact", "id", id) }

        return ContactMarshaller.contactEntityToContactDto(contactEntity)
    }

    private fun checkIfContactAlreadyExistByUserId(userId: Long): Boolean {
        val count = contactRepository.countByUserId(userId)
        return count > 0
    }

    fun deleteById(id: Long) {
        try {
            contactRepository.deleteById(id)
        } catch (exception: EmptyResultDataAccessException) {
            throw NotFoundException("Contact", "id", id)
        }
    }

    private fun validateCreationSchema(contactDto: ContactDto) {
        val contactAlreadyExist = this.checkIfContactAlreadyExistByUserId(contactDto.userId)
        if (contactAlreadyExist)
            throw BadRequestException(
                    ErrorCodes.CONTACT_ALREADY_EXIST.toString(),
                    "Contact already exist"
            )
    }

    fun update(contactDto: ContactDto, id: Long): ContactDto {

        val contactEntity = contactRepository.findById(id)
                .orElseThrow { NotFoundException("Contact", "id", id) }

        merge(contactEntity, contactDto)

        val contactEntityResponse = contactRepository.save(contactEntity)
        return ContactMarshaller.contactEntityToContactDto(contactEntityResponse)

    }

    fun findByUserId(userId: Long): ContactDto {
        val contactEntity = contactRepository.findByUserId(userId)
                .orElseThrow { NotFoundException("Contact", "user_id", userId) }

        return ContactMarshaller.contactEntityToContactDto(contactEntity)
    }

    private fun merge(contactEntity: ContactEntity, contactDto: ContactDto) {
        if (!contactDto.birthDate.isEqual(contactEntity.birthDate)) contactEntity.birthDate = contactDto.birthDate
        if (contactDto.userId != contactEntity.user.id) contactEntity.user.id = contactDto.userId
        if (contactDto.firstName != contactEntity.firstName) contactEntity.firstName = contactDto.firstName
        if (contactDto.lastName != contactEntity.lastName) contactEntity.lastName = contactDto.lastName
    }
}
