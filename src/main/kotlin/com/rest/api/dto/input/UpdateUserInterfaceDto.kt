package com.rest.api.dto.input

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import com.rest.api.dto.AbstractDto
import com.rest.api.dto.internal.EmailDto
import com.rest.api.enums.UserStatus

import javax.validation.constraints.Past
import java.time.LocalDate

data class UpdateUserInterfaceDto(
        var firstName: String,
        var lastName: String,
        var password: String,
        @JsonSerialize(using = ToStringSerializer::class)
        @Past(message = "birth_date cannot be in the future")
        var birthDate: LocalDate,
        var status: UserStatus,
        var isNew: Boolean,
        var emails: List<EmailDto>) : AbstractDto()
