package com.rest.api.dto.internal

import com.rest.api.dto.AbstractDto
import com.rest.api.enums.UserStatus

data class UserDto(
        var id: Long = 0,
        var userName: String = "",
        var password: String = "",
        var status: UserStatus = UserStatus.INACTIVE
) : AbstractDto() {
    constructor(id: Long, username: String) : this() {
        this.id = id
        this.userName = userName
    }

    constructor(username: String, password: String, status: UserStatus) : this() {
        this.userName = userName
        this.password = password
        this.status = status
    }
}