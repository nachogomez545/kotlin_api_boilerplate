package com.rest.api.dto.input

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import com.rest.api.dto.AbstractDto

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Past
import java.time.LocalDate

data class CreateUserUsingTokenDto(
        @JsonSerialize(using = ToStringSerializer::class)
        @NotNull(message = "birth_date cannot be null")
        @Past(message = "birth_date cannot be in the future")
        var birthDate: LocalDate,
        @NotNull(message = "country cannot be null")
        @NotBlank(message = "country cannot be empty")
        var country: String,
        @Email(message = "value must be a valid email")
        var email: String,
        @NotNull(message = "gender cannot be null")
        @NotBlank(message = "gender cannot be empty")
        var gender: String,
        @NotNull(message = "last_name cannot be null")
        @NotBlank(message = "last_name cannot be empty")
        var lastName: String,
        @NotNull(message = "name cannot be null")
        @NotBlank(message = "name cannot be empty")
        var name: String,
        @NotNull(message = "token cannot be null")
        @NotBlank(message = "token cannot be empty")
        var token: String
) : AbstractDto()
