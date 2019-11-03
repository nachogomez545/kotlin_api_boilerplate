package com.rest.api.dto.output

import com.rest.api.dto.AbstractDto

data class UserSignedDto(var id: Long = 0,
                    var userName: String = "",
                    var token: String = "",
                    var error: String = "") : AbstractDto(){

    constructor(error: String): this(){
        this.error = error
    }
    constructor(id: Long, userName: String, token: String): this(){
        this.id = id
        this.userName
        this.token = token
    }
}
