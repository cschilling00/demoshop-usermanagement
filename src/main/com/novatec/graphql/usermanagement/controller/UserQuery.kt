package src.main.com.novatec.graphql.usermanagement.controller

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Component
import src.main.com.novatec.graphql.usermanagement.model.User
import src.main.com.novatec.graphql.usermanagement.service.UserService

@Component
class UserQuery(val userService: UserService) : GraphQLQueryResolver {

    @PreAuthorize("hasAuthority('user')")
    fun getUsers(): List<User?> {
        return userService.getUser()
    }

    @PreAuthorize("hasAuthority('user')")
    fun getUserById(id: String): User? {
        return userService.getUserById(id)
    }

}
