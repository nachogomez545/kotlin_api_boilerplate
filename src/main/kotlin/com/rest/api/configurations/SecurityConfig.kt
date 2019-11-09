package com.rest.api.configurations

import com.rest.api.security.JwtConfigurer
import com.rest.api.security.JwtTokenProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class SecurityConfig @Autowired
constructor(private val jwtTokenProvider: JwtTokenProvider): WebSecurityConfigurerAdapter() {

    @Value("\${api.v1.auth.root}")
    private lateinit var authRouteRoot : String

    @Value("\${api.v1.auth.jwt_login}")
    private lateinit var authRouteLogin : String

    @Value("\${api.v1.auth.jwt_signup}")
    private lateinit var authRouteSignUp : String

    @Value("\${api.v1.users.root}")
    private lateinit var usersRouteRoot : String

    @Bean
    @Throws(Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        //@formatter:off
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, authRouteRoot + authRouteLogin).permitAll()
                .antMatchers(HttpMethod.POST, authRouteRoot + authRouteSignUp).permitAll()
                .antMatchers(HttpMethod.DELETE, "$usersRouteRoot/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .apply(JwtConfigurer(jwtTokenProvider))
        //@formatter:on
    }

    @Bean(name = ["passwordEncoder"])
    fun passwordEncoder(): PasswordEncoder {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder()
    }

}