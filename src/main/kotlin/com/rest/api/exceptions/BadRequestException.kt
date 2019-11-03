package com.rest.api.exceptions

import org.springframework.http.HttpStatus

class BadRequestException : CustomException {

    constructor(code: String, message: String) : super(message, HttpStatus.BAD_REQUEST, code)

    constructor(code: String, message: String, details: String) : super(message, HttpStatus.BAD_REQUEST, code, details)
}