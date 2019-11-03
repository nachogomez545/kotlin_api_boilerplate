package com.rest.api.services

import com.rest.api.domain.entities.UserEntity
import com.rest.api.domain.repositories.UserRepository
import com.rest.api.dto.internal.UserDto
import com.rest.api.exceptions.NotFoundException
import com.rest.api.marshaller.internal.UserMarshaller
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import java.util.Optional
import java.util.stream.Collectors

@Service("userService")
class UserService @Autowired
constructor(private val userRepository: UserRepository) {

    fun create(userDto: UserDto): UserDto {
        val userEntity = UserMarshaller.fromDtoToEntity(userDto)
        return UserMarshaller.fromEntityToDto(
                userRepository.save(userEntity)
        )
    }

    fun findById(id: Long?): UserDto {
        val userEntity = userRepository.findById(id!!)
                .orElseThrow { NotFoundException("User", "id", id) }

        return UserMarshaller.fromEntityToDto(userEntity)
    }

    fun deleteById(id: Long?) {
        try {
            userRepository.deleteById(id!!)
        } catch (exception: EmptyResultDataAccessException) {
            throw NotFoundException("User", "id", id!!)
        }
    }

    fun update(userDto: UserDto, id: Long): UserDto {

        val userEntity = userRepository.findById(id)
                .orElseThrow { NotFoundException("User", "id", id) }

        merge(userEntity, userDto)

        return UserMarshaller.fromEntityToDto(userRepository.save(userEntity))
    }

    private fun merge(userEntity: UserEntity, userDto: UserDto) {
        if (!userDto.userName.equals(userEntity.username, ignoreCase = true)) {
            userEntity.username = userDto.userName
        }

        if (userDto.status != userEntity.status) {
            userEntity.status = userDto.status
        }
    }
}
