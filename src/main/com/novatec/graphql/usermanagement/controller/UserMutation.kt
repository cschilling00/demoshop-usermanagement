package src.main.com.novatec.graphql.usermanagement.controller

import src.main.com.novatec.graphql.usermanagement.model.User
import src.main.com.novatec.graphql.usermanagement.service.UserService
import com.expediagroup.graphql.spring.operations.Mutation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class UserMutation : Mutation {

    @Autowired
    private lateinit var userService: UserService

    fun editUser(user: User?): User? {
        return user?.let { userService.updateUser(it) }
    }

    fun createUser(user: User?): User? {
        return user?.let { userService.createUser(it) }
    }

    fun deleteUser(userId: String): String? {
        var deletedUser = userService.getUserById(userId)
        userService.deleteUser(userId)
        return ("User deleted: $deletedUser")
    }
}