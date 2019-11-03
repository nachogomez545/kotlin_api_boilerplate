package com.rest.api.marshaller.internal

import com.rest.api.domain.entities.UserEntity
import com.rest.api.dto.internal.UserDto

class UserMarshaller {
    companion object{
        fun fromEntityToDto(entity: UserEntity): UserDto {

            return UserDto(
                    id = entity.id,
                    userName = entity.username,
                    status = entity.status)
        }

        fun fromDtoToEntity(userDto: UserDto): UserEntity {
            val userEntity = UserEntity()
            userEntity.roles = listOf("ROLE_USER")
            userEntity.id = userDto.id
            userEntity.username = userDto.userName
            userEntity.password = userDto.password
            userEntity.status = userDto.status

            return userEntity
        }
    }
}
