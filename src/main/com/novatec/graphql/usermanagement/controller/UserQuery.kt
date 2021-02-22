package src.main.com.novatec.graphql.usermanagement.controller

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Component
import src.main.com.novatec.graphql.usermanagement.model.User
import src.main.com.novatec.graphql.usermanagement.service.UserService

@Component
class UserQuery(val userService: UserService) : GraphQLQueryResolver {

    @PreAuthorize("isAuthenticated()")
    fun getUsers(): List<User?> {
        return userService.getUser()
    }

    @PreAuthorize("isAuthenticated()")
    fun getUserById(id: String): User? {
        return userService.getUserById(id)
    }

}
