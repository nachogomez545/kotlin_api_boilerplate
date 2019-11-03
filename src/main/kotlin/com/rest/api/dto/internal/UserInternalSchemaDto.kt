package com.rest.api.dto.internal

import com.rest.api.dto.AbstractDto

data class UserInternalSchemaDto(var contact: ContactDto,
                                 var emails: List<EmailDto> = listOf(),
                                 var user: UserDto
                                 ) : AbstractDto()
