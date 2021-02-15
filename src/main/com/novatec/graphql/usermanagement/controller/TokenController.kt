package src.main.com.novatec.graphql.usermanagement.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import src.main.com.novatec.graphql.usermanagement.model.User
import src.main.com.novatec.graphql.usermanagement.repository.UserRepository
import src.main.com.novatec.graphql.usermanagement.security.JwtGenerator
import src.main.com.novatec.graphql.usermanagement.service.UserService

@RestController
class TokenController(
        val jwtGenerator: JwtGenerator,
        val userService: UserService) {

    @PostMapping("/login")
    fun generate(@RequestBody jwtUser: User): String? {
        if(userService.checkCredentials(jwtUser)){
            return jwtUser?.let { jwtGenerator?.generate(it) }
        }
        throw ResponseStatusException(HttpStatus.BAD_REQUEST)
    }

    @GetMapping("/getUser")
    fun getName(): List<User?> {
        return userService.getUser()
    }
}