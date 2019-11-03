package com.rest.api.dto.internal

import com.rest.api.dto.AbstractDto

import javax.validation.constraints.Email
import javax.validation.constraints.NotNull
import javax.validation.constraints.PositiveOrZero

data class EmailDto(
        var id: Long = 0,
        var contactId: Long = 0,
        var value: String = "") : AbstractDto(){
    constructor(contactId: Long, value: String): this(){
        this.contactId = contactId
        this.value = value
    }
}
