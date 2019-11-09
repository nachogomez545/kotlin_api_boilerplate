package com.rest.api

import com.rest.api.domain.entities.UserEntity
import com.rest.api.domain.repositories.UserRepository
import com.rest.api.enums.UserStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class DataInitializer @Autowired
constructor(private val users: UserRepository): CommandLineRunner {

    private val passwordEncoder: PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()

    @Throws(Exception::class)
    override fun run(vararg args: String) {

        this.users.save(
                UserEntity(
                        "user",
                        this.passwordEncoder.encode("user"),
                        UserStatus.ACTIVE,
                        listOf("ROLE_USER"))
        )

        this.users.save(
                UserEntity(
                        "admin",
                        this.passwordEncoder.encode("admin"),
                        UserStatus.ACTIVE,
                        listOf("ROLE_USER", "ROLE_ADMIN"))
        )
    }
}