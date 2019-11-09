package com.rest.api.dto.input

import com.rest.api.dto.AbstractDto

import javax.validation.constraints.NotBlank

data class AuthenticationRequestDto(
        @NotBlank(message = "username cannot be null nor blank")
        var userName: String,
        @NotBlank(message = "password cannot be null nor blank")
        var password: String
) : AbstractDto()