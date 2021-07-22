package src.main.com.novatec.usermanagement.controller

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import src.main.com.novatec.usermanagement.model.Token
import src.main.com.novatec.usermanagement.model.User
import src.main.com.novatec.usermanagement.service.JwtUserDetailsService
import src.main.com.novatec.usermanagement.service.UserService

@Component
class UserQuery(val userService: UserService,
                val authenticationProvider: AuthenticationProvider,
                val jwtUserDetailsService: JwtUserDetailsService) : GraphQLQueryResolver {


    @PreAuthorize("isAnonymous()")
    fun login(credentials: Map<String, String>): Token {
        println("provided credentials: " + credentials["username"] + credentials["password"])
        val credentials = UsernamePasswordAuthenticationToken(credentials["username"], credentials["password"])
                .also { println("credentials: " + it) }
        SecurityContextHolder.getContext().authentication = authenticationProvider.authenticate(credentials)
                .also { println("auth: " + it) }
        return Token(jwtUserDetailsService.createToken(userService.getCurrentUser()), userService.getCurrentUser().id)

    }

    @PreAuthorize("permitAll()")
    fun getAuthorities(): String? {
        return SecurityContextHolder.getContext().authentication.authorities.joinToString { it -> it.authority}
    }

}
