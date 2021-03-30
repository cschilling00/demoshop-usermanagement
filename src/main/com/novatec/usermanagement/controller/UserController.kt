package src.main.com.novatec.usermanagement.controller

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import src.main.com.novatec.usermanagement.model.Token
import src.main.com.novatec.usermanagement.model.User
import src.main.com.novatec.usermanagement.service.JwtUserDetailsService
import src.main.com.novatec.usermanagement.service.UserService

@RestController
@RequestMapping("users")
class UserController(
        val userService: UserService,
        val authenticationProvider: AuthenticationProvider,
        val jwtUserDetailsService: JwtUserDetailsService
) {

    @PreAuthorize("hasAuthority('user')")
    @GetMapping
    fun getUsers(): List<User?> {
        return userService.getUser()
    }

    @PreAuthorize("hasAuthority('user')")
    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: String): User? {
        return userService.getUserById(id)
    }
    @PostMapping
    @PreAuthorize("isAnonymous()")
    fun createUser(@RequestBody user: User?): User? {
        return user?.let { userService.createUser(it) }
    }

    @PreAuthorize("hasAuthority('user')")
    @PatchMapping
    fun editUser(@RequestBody user: User?): User? {
        return user?.let { userService.updateUser(it) }
    }

    @PreAuthorize("hasAuthority('user')")
    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: String): String? {
        return userService.deleteUser(id)
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/authorities")
    fun getAuthorities(): String? {
        return try {
            SecurityContextHolder.getContext().authentication.authorities.joinToString { it -> it.authority }
        } catch (e: Error) {
            "error"
        }

    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/login")
    fun login(@RequestBody credentials: Map<String, String>): Token {
        println("provided credentials: " + credentials["username"] + credentials["password"])
        val authenticationToken = UsernamePasswordAuthenticationToken(credentials["username"], credentials["password"])
            .also { println("credentials: " + it) }
        SecurityContextHolder.getContext().authentication = authenticationProvider.authenticate(authenticationToken)
            .also { println("auth: " + it) }
        return Token(jwtUserDetailsService.createToken(userService.getCurrentUser()), userService.getCurrentUser().id)

    }

}
