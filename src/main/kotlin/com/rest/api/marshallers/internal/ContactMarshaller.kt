package com.rest.api.marshallers.internal

import com.rest.api.domain.entities.ContactEntity
import com.rest.api.domain.entities.UserEntity
import com.rest.api.dto.internal.ContactDto

class ContactMarshaller {

    companion object{
        fun contactEntityToContactDto(entity: ContactEntity): ContactDto {
            return ContactDto(
                    entity.id,
                    entity.user.id,
                    entity.firstName,
                    entity.lastName,
                    entity.media_url,
                    entity.birthDate,
                    entity.gender)
        }

        fun contactDtoToContactEntity(contactDto: ContactDto, user: UserEntity): ContactEntity {
            return ContactEntity(
                    contactDto.id,
                    user,
                    contactDto.firstName,
                    contactDto.lastName,
                    contactDto.mediaUrl,
                    contactDto.birthDate,
                    contactDto.gender)
        }
    }
}
