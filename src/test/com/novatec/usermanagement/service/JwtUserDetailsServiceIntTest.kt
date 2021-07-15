package src.test.com.novatec.usermanagement.service

import org.junit.jupiter.api.*

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.test.context.junit.jupiter.SpringExtension
import src.main.com.novatec.usermanagement.UserManagementApplication
import src.main.com.novatec.usermanagement.model.User
import src.main.com.novatec.usermanagement.repository.UserRepository
import src.main.com.novatec.usermanagement.service.JwtUserDetailsService
import java.lang.Exception
import java.util.*

@ExtendWith(
    SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [UserManagementApplication::class])
internal class JwtUserDetailsServiceIntTest(@Autowired val jwtUserDetailsService: JwtUserDetailsService, @Autowired val userRepository: UserRepository){

    @BeforeAll
    fun loadRepository(){
        userRepository.deleteAll()
        userRepository.save(User("602a74164f9ff6408aad5da6", "user", "\$2y\$10\$mt1Ev5vlAx2/RZrlFicF1uQNJk3SCGiCYLn.exBGEHL09hwWJfUNi", "user", "cs00@test.de", listOf("Reuteäckerstr. 70", "88433 Ingerkingen")))
        userRepository.save(User("602a74164f9ff6408aad5da7", "admin", "\$2y\$10\$mt1Ev5vlAx2/RZrlFicF1uQNJk3SCGiCYLn.exBGEHL09hwWJfUNi", "user,admin", "cs00@test.de", listOf("Reuteäckerstr. 70", "88433 Ingerkingen")))
    }

    @Test
    fun `should load user by username`() {
        val user = User("602a74164f9ff6408aad5da6", "user", "\$2y\$10\$mt1Ev5vlAx2/RZrlFicF1uQNJk3SCGiCYLn.exBGEHL09hwWJfUNi", "user", "cs00@test.de", listOf("Reuteäckerstr. 70", "88433 Ingerkingen"))

        val details = jwtUserDetailsService.loadUserByUsername("user")
        assertEquals(user.username, details.username)
        assertEquals(user.password, details.password)
        assertEquals(listOf(user.role).toString(), details.authorities.toString())
    }

    @Test
    fun `should throw exception while loading user by non existing username`() {
        assertThrows(Exception::class.java, { jwtUserDetailsService.loadUserByUsername("user1") })
    }

    @Test
    fun `should create token`() {
        val user = User("602a74164f9ff6408aad5da6", "user", "\$2y\$10\$mt1Ev5vlAx2/RZrlFicF1uQNJk3SCGiCYLn.exBGEHL09hwWJfUNi", "user", "cs00@test.de", listOf("Reuteäckerstr. 70", "88433 Ingerkingen"))

        val details = jwtUserDetailsService.createToken(user)
        println("details: "+ details)
        assert(details.isNotEmpty())
        assert(details.startsWith("eyJ0eXAiOiJKV1QiLC"))
    }

    @Test
    fun `should load user by token`() {
        val user = User("602a74164f9ff6408aad5da6", "user", "\$2y\$10\$mt1Ev5vlAx2/RZrlFicF1uQNJk3SCGiCYLn.exBGEHL09hwWJfUNi", "user", "cs00@test.de", listOf("Reuteäckerstr. 70", "88433 Ingerkingen"))
        val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIiwiaXNzIjoicHJvZHVjdHNlcnZpY2UtYXBpIiwiaWQiOiI2MDJhNzQxNjRmOWZmNjQwOGFhZDVkYTYiLCJpYXQiOjE2MjU2ODAzNjN9.TIm2oPpBxcMqVh8lfV_0aj-bcLgK84jn2HJ0wpchZRzWyu-DkozJr5QkPMwCPPBnnYjUQIM1C5c0WjBKgCEgAQ"
        val details = jwtUserDetailsService.loadUserByToken(token)
        assertEquals(user.username, details?.username)
        assertEquals(user.password, details?.password)
        assertEquals(listOf(user.role).toString(), details?.authorities.toString())
    }

    @Test
    fun `should throw error while loading user with invalid token`() {
        val token = "Invalid token"
        assertThrows(Error::class.java, { jwtUserDetailsService.loadUserByToken(token) })
    }
}