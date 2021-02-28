package src.main.com.novatec.graphql.usermanagement.controller

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import graphql.GraphQLContext
import graphql.schema.DataFetchingEnvironment
import graphql.servlet.context.GraphQLServletContext
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import src.main.com.novatec.graphql.usermanagement.model.Token
import src.main.com.novatec.graphql.usermanagement.model.User
import src.main.com.novatec.graphql.usermanagement.service.JwtUserDetailsService
import src.main.com.novatec.graphql.usermanagement.service.UserService
import javax.servlet.http.HttpServletRequest

@Component
class UserMutation(
        val userService: UserService
) : GraphQLMutationResolver {

    @PreAuthorize("hasAuthority('user')")
    fun editUser(user: User?): User? {
        return user?.let { userService.updateUser(it) }
    }

    @PreAuthorize("isAnonymous()")
    fun createUser(user: User?): User? {
        return user?.let { userService.createUser(it) }
    }

    @PreAuthorize("hasAuthority('user')")
    fun deleteUser(userId: String): String? {
        return userService.deleteUser(userId)
    }



    @PreAuthorize("hasAuthority('user')")
    fun verifyToken(env: DataFetchingEnvironment): String? {
        val request = env.getContext<GraphQLServletContext>().httpServletRequest
        val token = request.getHeader("Authorization")
                .split("Bearer ")
                .last()
        println("verifyToken: " + token)
        return userService.verifyToken(token).toString()
    }
}
