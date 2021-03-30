package src.main.com.novatec.usermanagement.service

import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Service
import src.main.com.novatec.usermanagement.model.User
import src.main.com.novatec.usermanagement.repository.UserRepository
import src.main.com.novatec.usermanagement.security.JWTPreAuthenticationToken
import java.lang.Exception
import kotlin.NoSuchElementException

@Service
class UserService(
        val userRepository: UserRepository,
        val passwordEncoder: PasswordEncoder,
        val jwtUserDetailsService: JwtUserDetailsService
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
        if (user.id == "") {
            throw IllegalArgumentException("No Id given")
        } else {
            userRepository.findById(user.id).orElseThrow {
                throw NoSuchElementException("User with id ´${user.id}´ not found")
            }
            return userRepository.save(user)
        }
    }

    fun createUser(user: User): User {
        if (userRepository.findByUsername(user.username) != null) throw Exception("The given username ${user.username} already exists")
        user.password = passwordEncoder.encode(user.password)
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
            ?.let { return it } ?: throw Exception("User with matching username and password not found")
    }

}
