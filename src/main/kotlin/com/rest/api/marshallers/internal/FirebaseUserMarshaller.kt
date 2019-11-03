package com.rest.api.marshaller.internal

import com.rest.api.dto.input.CreateUserInterfaceDto
import com.rest.api.dto.input.CreateUserUsingTokenDto

import javax.validation.constraints.NotNull

class FirebaseUserMarshaller {
    companion object {
        fun fromFirebaseToUserInterfaceDto(userUsingTokenDto: CreateUserUsingTokenDto): CreateUserInterfaceDto {
            return CreateUserInterfaceDto(
                    userUsingTokenDto.name,
                    userUsingTokenDto.lastName,
                    userUsingTokenDto.email,
                    getUserByEmailAddress(userUsingTokenDto.email),
                    userUsingTokenDto.gender,
                    userUsingTokenDto.birthDate,
                    generatePasswordCuttingToken(userUsingTokenDto.token)
            )
        }

        private fun generatePasswordCuttingToken(@NotNull token: String): String {
            return token.substring(0, token.indexOf("."))
        }

        private fun getUserByEmailAddress(email: String): String {
            return email.substring(0, email.indexOf("@"))
        }
    }
}
