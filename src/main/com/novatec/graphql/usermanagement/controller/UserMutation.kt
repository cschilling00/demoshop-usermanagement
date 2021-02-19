package src.main.com.novatec.graphql.usermanagement.controller

import com.expediagroup.graphql.spring.operations.Mutation
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import src.main.com.novatec.graphql.usermanagement.model.User
import src.main.com.novatec.graphql.usermanagement.service.UserService

@Component
class UserMutation(
    val userService: UserService,
    val authenticationProvider: AuthenticationProvider
) : Mutation {

    fun editUser(user: User?): User? {
        return user?.let { userService.updateUser(it) }
    }

    fun createUser(user: User?): User? {
        return user?.let { userService.createUser(it) }
    }

    fun deleteUser(userId: String): String? {
        return userService.deleteUser(userId)
    }

    @PreAuthorize("isAnonymous()")
    fun login(username: String, password: String): User {
        val credentials = UsernamePasswordAuthenticationToken(username, password).also { println("credentials: " + it) }
        SecurityContextHolder.getContext().authentication = authenticationProvider.authenticate(credentials)
            return userService.getCurrentUser()
    }

    @PreAuthorize("isAuthenticated()")
    fun getToken(user: User): String {
        return userService.createToken(user)
    }

}
