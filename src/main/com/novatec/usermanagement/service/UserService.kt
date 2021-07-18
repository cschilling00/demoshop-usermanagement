package src.main.com.novatec.usermanagement.service

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import src.main.com.novatec.usermanagement.model.User
import src.main.com.novatec.usermanagement.repository.UserRepository
import java.lang.Exception

@Service
class UserService(val userRepository: UserRepository) {


    fun getCurrentUser(): User {
        SecurityContextHolder.getContext().authentication
            .let { it.name }
            .let { userRepository.findByUsername(it) }
            ?.let { return it } ?: throw Exception("User with matching username and password not found")
    }

}
