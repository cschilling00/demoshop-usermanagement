package src.main.com.novatec.graphql.usermanagement.controller

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import src.main.com.novatec.graphql.usermanagement.model.Token
import src.main.com.novatec.graphql.usermanagement.model.User
import src.main.com.novatec.graphql.usermanagement.service.JwtUserDetailsService
import src.main.com.novatec.graphql.usermanagement.service.UserService

@Component
class UserQuery(val userService: UserService,
                val authenticationProvider: AuthenticationProvider,
                val jwtUserDetailsService: JwtUserDetailsService) : GraphQLQueryResolver {

    @PreAuthorize("hasAuthority('user')")
    fun getUsers(): List<User?> {
        return userService.getUser()
    }

    @PreAuthorize("hasAuthority('user')")
    fun getUserById(id: String): User? {
        return userService.getUserById(id)
    }

    @PreAuthorize("isAnonymous()")
    fun login(credentials: Map<String, String>): Token {
        println("provided credentials: " + credentials["username"] + credentials["password"])
        val credentials = UsernamePasswordAuthenticationToken(credentials["username"], credentials["password"])
                .also { println("credentials: " + it) }
        SecurityContextHolder.getContext().authentication = authenticationProvider.authenticate(credentials)
                .also { println("auth: " + it) }
        return Token(jwtUserDetailsService.createToken(userService.getCurrentUser()))

    }

}
