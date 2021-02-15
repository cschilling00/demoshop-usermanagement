package src.main.com.novatec.graphql.usermanagement.controller


import src.main.com.novatec.graphql.usermanagement.model.User
import src.main.com.novatec.graphql.usermanagement.service.UserService
import com.expediagroup.graphql.spring.operations.Query
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component


@Component
class UserQuery : Query {

    @Autowired
    private lateinit var userService: UserService

    fun getUser(): List<User?> {
        return userService.getUser()
    }

    fun getUserById(id: String): User? {
        return userService.getUserById(id)
    }

}
