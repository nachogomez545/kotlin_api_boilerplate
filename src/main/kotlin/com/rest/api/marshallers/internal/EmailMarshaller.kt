package com.rest.api.marshallers.internal

import com.rest.api.domain.entities.ContactEntity
import com.rest.api.domain.entities.EmailEntity
import com.rest.api.dto.internal.EmailDto

class EmailMarshaller {
    companion object{
        fun emailEntityToEmailDto(emailEntity: EmailEntity): EmailDto {
            return EmailDto(emailEntity.id, emailEntity.contact.id, emailEntity.value)
        }

        fun emailDtoToEmailEntity(emailDto: EmailDto, contactEntity: ContactEntity): EmailEntity {
            return EmailEntity(contactEntity, emailDto.value)
        }

        fun setEmailDto(id: Long = 0, contactId: Long = 0, value: String = ""): EmailDto {
            return EmailDto(id = id, contactId = contactId, value = value)
        }
    }
}
