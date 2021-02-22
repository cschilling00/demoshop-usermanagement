package src.main.com.novatec.graphql.usermanagement.controller

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import src.main.com.novatec.graphql.usermanagement.model.User
import src.main.com.novatec.graphql.usermanagement.service.JwtUserDetailsService
import src.main.com.novatec.graphql.usermanagement.service.UserService

@Component
class UserMutation(
    val userService: UserService,
    val authenticationProvider: AuthenticationProvider,
    val jwtUserDetailsService: JwtUserDetailsService
) : GraphQLMutationResolver {

    @PreAuthorize("hasAuthority('user')")
    fun editUser(user: User?): User? {
        return user?.let { userService.updateUser(it) }
    }

    @PreAuthorize("hasAuthority('user')")
    fun createUser(user: User?): User? {
        return user?.let { userService.createUser(it) }
    }

    @PreAuthorize("hasAuthority('user')")
    fun deleteUser(userId: String): String? {
        return userService.deleteUser(userId)
    }

    @PreAuthorize("isAnonymous()")
    fun login(username: String, password: String): String {
        println("provided credentials: " + username + password)
        val credentials = UsernamePasswordAuthenticationToken(username, password)
                .also { println("credentials: " + it) }
        SecurityContextHolder.getContext().authentication = authenticationProvider.authenticate(credentials).also { println("auth: " + it) }
        return jwtUserDetailsService.getToken(userService.getCurrentUser())
    }
}
