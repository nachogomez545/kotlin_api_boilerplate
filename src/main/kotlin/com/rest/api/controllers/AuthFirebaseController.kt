package com.rest.api.controllers

import com.rest.api.dto.input.CreateUserUsingTokenDto
import com.rest.api.dto.output.UserSignedDto
import com.rest.api.services.AuthFirebaseService
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import javax.validation.Valid

@RestController
@RequestMapping(value = ["\${api.v1.auth.root}"])
class AuthFirebaseController @Autowired
constructor(private val firebaseService: AuthFirebaseService){

    @PostMapping("\${api.v1.auth.firebase_login}")
    fun loginViaFirebase(@RequestHeader(value = "token") token: String): Single<UserSignedDto> {
        return firebaseService.signIn(token)
                .subscribeOn(Schedulers.io())
                .doOnSuccess { userSignedDto -> ResponseEntity.status(HttpStatus.OK).body(userSignedDto) }
                .doOnError{throwable -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(UserSignedDto(throwable.localizedMessage)) }
    }

    @PostMapping("\${api.v1.auth.firebase_signup}")
    fun signUpViaFirebase(@RequestBody @Valid userUsingTokenDto: CreateUserUsingTokenDto): ResponseEntity<UserSignedDto> {
        val response: UserSignedDto
        var posibleError = ""
        try {
            response = firebaseService.createUserUsingToken(userUsingTokenDto)
            return ResponseEntity.status(HttpStatus.OK).body(response)
        } catch (e: Exception) {
            posibleError = e.message!!
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(UserSignedDto(posibleError))
    }

}
