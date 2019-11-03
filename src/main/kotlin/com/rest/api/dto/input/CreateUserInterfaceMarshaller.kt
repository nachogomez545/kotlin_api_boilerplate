package com.rest.api.dto.input

import com.rest.api.dto.internal.ContactDto
import com.rest.api.dto.internal.EmailDto
import com.rest.api.dto.internal.UserDto
import com.rest.api.enums.UserStatus

class CreateUserInterfaceMarshaller {
    companion object{
        fun payloadCreateUserDto(createUserInterfaceDto: CreateUserInterfaceDto,
                                 userStatus: UserStatus): UserDto {
            return UserDto(
                    createUserInterfaceDto.userName,
                    createUserInterfaceDto.password,
                    userStatus
            )
        }

        fun payloadCreateContactDto(createUserInterfaceDto: CreateUserInterfaceDto,
                                    userId: Long?): ContactDto {
            return ContactDto(
                    userId!!,
                    createUserInterfaceDto.firstName,
                    createUserInterfaceDto.lastName,
                    createUserInterfaceDto.birthDate,
                    createUserInterfaceDto.gender
            )
        }

        fun payloadCreateEmailDto(createUserInterfaceDto: CreateUserInterfaceDto,
                                  contactId: Long?): EmailDto {
            return EmailDto(
                    contactId!!,
                    createUserInterfaceDto.email
            )
        }
    }
}
