package com.rest.api.services

import com.rest.api.dto.input.CreateUserInterfaceDto
import com.rest.api.dto.input.CreateUserInterfaceMarshaller
import com.rest.api.dto.input.UpdateUserInterfaceDto
import com.rest.api.dto.internal.UserDto
import com.rest.api.dto.internal.UserInternalSchemaDto
import com.rest.api.dto.output.UserInterfaceDto
import com.rest.api.dto.output.UserInterfaceMarshaller
import com.rest.api.enums.UserStatus
import com.rest.api.exceptions.BadRequestException
import com.rest.api.exceptions.NotFoundException
import com.rest.api.marshaller.internal.UserMarshaller
import com.rest.api.marshallers.internal.ContactMarshaller
import com.rest.api.marshallers.internal.UserInternalSchemaMarshaller
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service("userInterfaceService")
class UserInterfaceService @Autowired
constructor(private val userService: UserService,
            private val emailService: EmailService,
            private val contactService: ContactService): CommonSignUp {

    fun findById(id: Long): UserInterfaceDto {

        val (contact, emails, user) = findUserInternalSchemaById(id)

        return UserInterfaceMarshaller.userInterfaceDto(contact,
                emails,
                user
        )
    }

    @Transactional
    override fun create(createUserInterfaceDto: CreateUserInterfaceDto): UserInterfaceDto {
        val (id) = findEmail(createUserInterfaceDto.email)

        if (id > 0) {
            throw BadRequestException(
                    "EMAIL_ALREADY_USED",
                    "This email is already used"
            )
        }

        val payloadCreateUserDto = CreateUserInterfaceMarshaller.payloadCreateUserDto(
                createUserInterfaceDto,
                UserStatus.ACTIVE
        )

        val userDto = userService.create(payloadCreateUserDto)

        val payloadCreateContactDto = CreateUserInterfaceMarshaller.payloadCreateContactDto(createUserInterfaceDto, userDto.id)
        val contactDto = contactService.create(payloadCreateContactDto, UserMarshaller.fromDtoToEntity(userDto))

        val payloadCreatedEmailDto = CreateUserInterfaceMarshaller.payloadCreateEmailDto(createUserInterfaceDto, contactDto.id)
        val emailDto = emailService.create(payloadCreatedEmailDto, ContactMarshaller.contactDtoToContactEntity(contactDto, UserMarshaller.fromDtoToEntity(userDto)))

        val listEmail = listOf(emailDto)

        return UserInterfaceMarshaller.userInterfaceDto(contactDto,
                listEmail,
                userDto
        )
    }

    fun signInWithEmail(email: String): UserDto {

        val (_, contactId) = emailService.findByValue(email)

        val (_, userId) = contactService.findById(contactId)

        return userService.findById(userId)
    }

    fun findEmail(email: String): UserInterfaceDto {
        try {
            val (_, contactId) = emailService.findByValue(email)
            val (_, userId) = contactService.findById(contactId)
            return findById(userId)
        } catch (ex: NotFoundException) {
            return UserInterfaceDto()
        }
    }

    fun deleteById(id: Long) {
        val user = userService.findById(id)
        val contact = contactService.findByUserId(user.id)

        emailService.deleteByContactId(ContactMarshaller.contactDtoToContactEntity(contact, UserMarshaller.fromDtoToEntity(user) ))
        contactService.deleteById(contact.id)
        userService.deleteById(user.id)
    }

    fun update(id: Long, updateUserInterfaceDto: UpdateUserInterfaceDto): UserInterfaceDto {
        val userInternalSchemaDto = findUserInternalSchemaById(id)
        merge(userInternalSchemaDto, updateUserInterfaceDto)

        val contactDto = contactService.update(userInternalSchemaDto.contact, userInternalSchemaDto.contact.id)

        val userDto = userService.update(userInternalSchemaDto.user, id)

        val emailDto = emailService.updateOrCreateByContactId(ContactMarshaller.contactDtoToContactEntity(contactDto, UserMarshaller.fromDtoToEntity(userDto) ), userInternalSchemaDto.emails)

        return UserInterfaceMarshaller.userInterfaceDto(contactDto,
                emailDto,
                userDto
        )
    }

    private fun merge(userInternalSchemaDto: UserInternalSchemaDto,
                      updateUserInterfaceDto: UpdateUserInterfaceDto) {

        if (!updateUserInterfaceDto.firstName.equals(userInternalSchemaDto.contact.firstName, ignoreCase = true)) {
            userInternalSchemaDto.contact.firstName = updateUserInterfaceDto.firstName
        }

        if (!updateUserInterfaceDto.lastName.equals(userInternalSchemaDto.contact.lastName, ignoreCase = true)) {
            userInternalSchemaDto.contact.lastName = updateUserInterfaceDto.lastName
        }

        if (updateUserInterfaceDto.birthDate != userInternalSchemaDto.contact.birthDate) {
            userInternalSchemaDto.contact.birthDate = updateUserInterfaceDto.birthDate
        }

        if (updateUserInterfaceDto.status != userInternalSchemaDto.user.status) {
            userInternalSchemaDto.user.status = updateUserInterfaceDto.status
        }

        if (!updateUserInterfaceDto.emails.isNullOrEmpty() && updateUserInterfaceDto.emails.isNotEmpty()) {
            userInternalSchemaDto.emails = updateUserInterfaceDto.emails
        }
    }

    private fun findUserInternalSchemaById(id: Long): UserInternalSchemaDto {
        val userDto = userService.findById(id)
        val contactDto = contactService.findByUserId(userDto.id)
        val emails = emailService.findByContactId(contactDto.id)

        return UserInternalSchemaMarshaller.userInternalSchemaDto(
                contactDto,
                emails,
                userDto
        )
    }
}
