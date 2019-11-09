package com.rest.api.controllers

import com.rest.api.dto.input.CreateUserInterfaceDto
import com.rest.api.dto.input.UpdateUserInterfaceDto
import com.rest.api.dto.output.UserInterfaceDto
import com.rest.api.services.UserInterfaceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*

import javax.validation.Valid

@RestController
@RequestMapping(value = ["\${api.v1.users.root}"])
class UserInterfaceController @Autowired
constructor(private val userInterfaceService: UserInterfaceService,
            private val passwordEncoder: PasswordEncoder) {

    @GetMapping("\${api.v1.users.find}")
    fun findById(@PathVariable(value = "id") id: Long): UserInterfaceDto {
        return userInterfaceService.findById(id)
    }

    @PostMapping("\${api.v1.users.create}")
    fun create(@RequestBody @Valid createUserInterfaceDto: CreateUserInterfaceDto): ResponseEntity<UserInterfaceDto> {
        createUserInterfaceDto.password = passwordEncoder.encode(createUserInterfaceDto.password)
        val response = userInterfaceService.create(createUserInterfaceDto)
        return ResponseEntity.status(HttpStatus.CREATED).body<UserInterfaceDto>(response)
    }

    @DeleteMapping("\${api.v1.users.delete}")
    fun delete(@PathVariable(value = "id") id: Long) {
        userInterfaceService.deleteById(id)
    }

    @PutMapping("\${api.v1.users.update}")
    fun update(@PathVariable(value = "id") id: Long,
               @RequestBody @Valid updateUserInterfaceDto: UpdateUserInterfaceDto): UserInterfaceDto {
        updateUserInterfaceDto.password = passwordEncoder.encode(updateUserInterfaceDto.password)
        return userInterfaceService.update(id, updateUserInterfaceDto)
    }
}