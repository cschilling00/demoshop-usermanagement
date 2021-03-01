package src.main.com.novatec.graphql.usermanagement.controller

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import graphql.schema.DataFetchingEnvironment
import graphql.servlet.context.GraphQLServletContext
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import src.main.com.novatec.graphql.usermanagement.model.User
import src.main.com.novatec.graphql.usermanagement.service.UserService
import java.lang.Error

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

    @PreAuthorize("permitAll()")
    fun getAuthorities(env: DataFetchingEnvironment): String? {
        try{
            return SecurityContextHolder.getContext().authentication.authorities.joinToString { it -> it.authority}
        }catch (e: Error){
            return "error"
        }

    }
}
