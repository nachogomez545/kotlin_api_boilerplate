package com.rest.api.services

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseToken
import com.rest.api.domain.entities.UserEntity
import com.rest.api.domain.repositories.EmailRepository
import com.rest.api.domain.repositories.UserRepository
import com.rest.api.dto.input.CreateUserUsingTokenDto
import com.rest.api.dto.internal.UserDto
import com.rest.api.dto.output.UserSignedDto
import com.rest.api.enums.UserStatus
import com.rest.api.marshaller.internal.FirebaseUserMarshaller
import com.rest.api.marshaller.internal.UserMarshaller
import io.reactivex.Single
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

import javax.transaction.Transactional
import java.util.concurrent.ExecutionException

@Service("authFirebaseService")
class AuthFirebaseService @Autowired
constructor(private val emailRepository: EmailRepository,
            private val userRepository: UserRepository,
            private val userInterfaceService: UserInterfaceService,
            private val passwordEncoder: PasswordEncoder){

    @Throws(Exception::class)
    fun signIn(token: String): Single<UserSignedDto> {
        return Single.create {
            try {
                val fireBaseToken = authenticateFirebaseToken(token)
                val userDto = userInterfaceService.signInWithEmail(fireBaseToken.email)
                it.onSuccess(createUserSigned(userDto, token))
            } catch (e: InterruptedException) {
                it.onError( InterruptedException("User Not Authenticated"))
            } catch (e: ExecutionException) {
                it.onError( Exception("User Not Authenticated"))
            }
        }
    }

    @Transactional
    @Throws(Exception::class)
    fun createUserUsingToken(userUsingTokenDto: CreateUserUsingTokenDto): UserSignedDto {
        try {
            val firebaseToken = authenticateFirebaseToken(userUsingTokenDto.token)

            userInterfaceService.create(FirebaseUserMarshaller.fromFirebaseToUserInterfaceDto(userUsingTokenDto))

            val userCreated = userRepository.saveAndFlush(createUserFromFirebaseToken(firebaseToken))
            return createUserSigned(UserMarshaller.fromEntityToDto(userCreated), userUsingTokenDto.token)
        } catch (e: InterruptedException) {
            throw Exception("User Not Authenticated")
        } catch (e: ExecutionException) {
            throw Exception("User Not Authenticated")
        }
    }

    /**
     * @param authToken Firebase access token string
     * @return the computed result
     * @throws Exception
     */
    @Throws(Exception::class)
    private fun authenticateFirebaseToken(authToken: String): FirebaseToken {
        val app = FirebaseAuth.getInstance().verifyIdTokenAsync(authToken)

        return app.get()
    }

    private fun createUserSigned(user: UserDto, firebaseToken: String): UserSignedDto {
        return UserSignedDto(user.id, user.userName, firebaseToken)
    }

    private fun createUserFromFirebaseToken(firebaseToken: FirebaseToken): UserEntity {
        return UserEntity(
                getUserByEmailAddress(firebaseToken.email),
                createPasswordUsingUid(firebaseToken.uid),
                UserStatus.ACTIVE,
                listOf("ROLE_USER")
        )
    }

    private fun createPasswordUsingUid(uid: String): String {
        return this.passwordEncoder.encode(uid)
    }

    private fun getUserByEmailAddress(email: String): String {
        return email.substring(0, email.indexOf("@"))
    }
}
