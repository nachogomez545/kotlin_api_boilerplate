package com.rest.api.services

import com.rest.api.domain.repositories.UserRepository
import com.rest.api.dto.input.CreateUserInterfaceDto
import com.rest.api.dto.output.UserInterfaceDto
import com.rest.api.dto.output.UserSignedDto
import com.rest.api.security.JwtTokenProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody
import javax.validation.Valid

@Service("authService")
class AuthService @Autowired
constructor(private val authenticationManager: AuthenticationManager,
            private val jwtTokenProvider: JwtTokenProvider,
            private val passwordEncoder: PasswordEncoder,
            private val userRepository: UserRepository,
            private val commonSignUp: CommonSignUp){

    fun signIn(username: String, password: String): UserSignedDto {
        try {
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username, password))
            val user = this.userRepository
                    .findByUsernameAndDeletedFalse(username).orElseThrow { UsernameNotFoundException("Username " + username + "not found") }
            val token = jwtTokenProvider
                    .createToken(username, user.roles)
            return UserSignedDto(user.id, username, token)
        } catch (e: AuthenticationException) {
            throw BadCredentialsException("Invalid username/password supplied")
        } catch (e: Exception){
            throw Exception(e.localizedMessage)
        }
    }

    fun signUp(@RequestBody @Valid createUserInterfaceDto: CreateUserInterfaceDto): UserInterfaceDto {
        createUserInterfaceDto.password = passwordEncoder.encode(createUserInterfaceDto.password)
        return commonSignUp.create(createUserInterfaceDto)
    }
}