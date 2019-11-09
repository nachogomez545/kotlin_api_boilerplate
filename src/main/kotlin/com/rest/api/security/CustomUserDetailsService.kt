package com.rest.api.security

import com.rest.api.domain.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component

@Component
class CustomUserDetailsService @Autowired
constructor(private val users: UserRepository) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        return this.users.findByUsernameAndDeletedFalse(username)
                .orElseThrow { UsernameNotFoundException("Username: $username not found") }
    }
}
