package com.rest.api.dto.internal

import com.rest.api.dto.AbstractDto
import java.time.LocalDate

data class ContactDto(
        var id: Long = 0,
        var userId: Long = 0,
        var firstName: String = "",
        var lastName: String = "",
        var mediaUrl : String = "",
        var birthDate: LocalDate = LocalDate.now(),
        var gender: String = ""
) : AbstractDto(){
    constructor(userId: Long, firstName: String, lastName: String, birthDate: LocalDate, gender: String) : this(){
        this.userId = userId
        this.firstName = firstName
        this.lastName = lastName
        this.birthDate = birthDate
        this.gender = gender
    }
}
