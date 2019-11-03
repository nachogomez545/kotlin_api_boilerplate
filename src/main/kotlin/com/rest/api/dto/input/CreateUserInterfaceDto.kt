package com.rest.api.dto.input

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import com.rest.api.dto.AbstractDto

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Past
import java.time.LocalDate

data class CreateUserInterfaceDto(
        @NotBlank(message = "user_name cannot be null nor blank")
        val userName: String,
        @NotBlank(message = "password cannot be null nor blank")
        val password: String,
        @Email(message = "value must be a valid email")
        val email: String,
        @NotBlank(message = "first_name cannot be null nor blank")
        val firstName: String,
        @NotBlank(message = "last_name cannot be null nor blank")
        val lastName: String,
        @JsonSerialize(using = ToStringSerializer::class)
        @NotNull(message = "birth_date cannot be null")
        @Past(message = "birthDate cannot be in the future")
        val birthDate: LocalDate,
        val gender: String
) : AbstractDto()
