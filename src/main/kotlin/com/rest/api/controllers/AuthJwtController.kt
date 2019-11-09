package com.rest.api.controllers

import com.rest.api.dto.input.AuthenticationRequestDto
import com.rest.api.dto.input.CreateUserInterfaceDto
import com.rest.api.dto.output.UserInterfaceDto
import com.rest.api.dto.output.UserSignedDto
import com.rest.api.services.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping(value = ["\${api.v1.auth.root}"])
class AuthJwtController @Autowired
constructor(private val service: AuthService){

    @PostMapping("\${api.v1.auth.jwt_login}")
    fun login(@RequestBody userCredentials: AuthenticationRequestDto): ResponseEntity<UserSignedDto> {
        val response = service.signIn(userCredentials.userName, userCredentials.password)
        return ResponseEntity.status(HttpStatus.OK).body<UserSignedDto>(response)
    }

    @PostMapping("\${api.v1.auth.jwt_signup}")
    fun registerUser(@RequestBody @Valid createUserInterfaceDto: CreateUserInterfaceDto): ResponseEntity<UserInterfaceDto> {
        val response = service.signUp(createUserInterfaceDto)
        return ResponseEntity.status(HttpStatus.CREATED).body<UserInterfaceDto>(response)
    }
}