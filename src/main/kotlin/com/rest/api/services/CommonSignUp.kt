package com.rest.api.services

import com.rest.api.dto.input.CreateUserInterfaceDto
import com.rest.api.dto.output.UserInterfaceDto

interface CommonSignUp {

    fun create(createUserInterfaceDto: CreateUserInterfaceDto): UserInterfaceDto

}