package com.rest.api.controllers

import com.rest.api.exceptions.InvalidJwtAuthenticationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.WebRequest
import java.util.stream.Collectors

import org.springframework.http.HttpStatus.UNAUTHORIZED
import org.springframework.http.ResponseEntity.status

@ControllerAdvice
@RestController
class UserControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): List<String?> {
        return ex.bindingResult
                .allErrors
                .map { it.defaultMessage }
                .toList()
    }

    @ExceptionHandler(value = [InvalidJwtAuthenticationException::class])
    fun invalidJwtAuthentication(ex: InvalidJwtAuthenticationException, request: WebRequest): ResponseEntity<*> {
        return status(UNAUTHORIZED).build<Any>()
    }
}