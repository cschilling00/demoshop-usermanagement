package src.main.com.novatec.graphql.usermanagement.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import src.main.com.novatec.graphql.usermanagement.model.User
import src.main.com.novatec.graphql.usermanagement.repository.UserRepository
import src.main.com.novatec.graphql.usermanagement.security.SecurityProperties
import java.time.Instant
import java.util.*
import kotlin.NoSuchElementException

@Service
class UserService(
    val securityProperties: SecurityProperties,
    val userRepository: UserRepository,
    val algorithm: Algorithm
) {

    fun getUserById(id: String): User? {
        return userRepository.findById(id).orElseThrow {
            throw NoSuchElementException("User with id ´$id´ not found")
        }
    }

    fun getUser(): List<User?> {
        return userRepository.findAll()
    }

    fun updateUser(user: User): User? {
        if (user.id == null) {
            throw IllegalArgumentException("No Id given")
        } else {
            userRepository.findById(user.id).orElseThrow {
                throw NoSuchElementException("User with id ´$user.id´ not found")
            }
            return userRepository.save(user)
        }
    }

    fun createUser(user: User): User {
        return userRepository.save(user)
    }

    fun deleteUser(userId: String): String {
        userRepository.findById(userId).orElseThrow {
            throw NoSuchElementException("User with id ´$userId´ not found")
        }
        userRepository.deleteById(userId)
        return "User successfully deleted"
    }

    fun getCurrentUser(): User {
        SecurityContextHolder.getContext().authentication
            .let { it.name }
            .let { userRepository.findByUsername(it) }
            ?.let { return it } ?: throw Error()
    }

    fun createToken(user: User): String {
        val now = Instant.now()
        val expiry = Instant.now().plus(securityProperties.tokenExpiration)
        return JWT
            .create()
            .withIssuer(securityProperties.tokenIssuer)
            .withIssuedAt(Date.from(now))
            .withExpiresAt(Date.from(expiry))
            .withSubject(user.email)
            .sign(algorithm)
    }
}
