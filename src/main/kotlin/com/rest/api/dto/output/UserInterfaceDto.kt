package com.rest.api.dto.output

import com.rest.api.dto.AbstractDto
import com.rest.api.dto.internal.EmailDto
import com.rest.api.enums.UserStatus
import java.time.LocalDate

data class UserInterfaceDto(
        val id: Long = 0,
        val firstName: String = "",
        val lastName: String = "",
        val birthDate: LocalDate = LocalDate.now(),
        val status: UserStatus = UserStatus.INACTIVE,
        val emails: List<EmailDto> = listOf()
) : AbstractDto()
