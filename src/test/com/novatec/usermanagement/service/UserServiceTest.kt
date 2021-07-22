package src.test.com.novatec.usermanagement.service

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import src.main.com.novatec.usermanagement.model.JwtUserDetails
import src.main.com.novatec.usermanagement.model.User
import src.main.com.novatec.usermanagement.repository.UserRepository
import src.main.com.novatec.usermanagement.service.JwtUserDetailsService
import src.main.com.novatec.usermanagement.service.UserService
import java.util.*
import kotlin.NoSuchElementException

@ExtendWith(
    MockKExtension::class)
internal class UserServiceTest{

    @MockK
    private val userRepository = mockk<UserRepository>()

    @MockK
    private val passwordEncoder = mockk<PasswordEncoder>()

    @MockK
    private val jwtUserDetailsService = mockk<JwtUserDetailsService>()

    @MockK
    private val securityContext = mockk<SecurityContext>()

    @MockK
    private val authentication = mockk<Authentication>()

    @InjectMockKs
    lateinit var userService: UserService

    @Test
    fun `should get current user from mocked Repository`() {
        val user = User("602a74164f9ff6408aad5da6", "user", "\$2y\$10\$mt1Ev5vlAx2/RZrlFicF1uQNJk3SCGiCYLn.exBGEHL09hwWJfUNi", "user", "cs00@test.de", listOf("Friedrichstr. 70", "88433 Ingerkingen"))

        every { authentication.principal } returns JwtUserDetails("user", "\$2y\$10\$mt1Ev5vlAx2/RZrlFicF1uQNJk3SCGiCYLn.exBGEHL09hwWJfUNi",
            listOf("user") as List<SimpleGrantedAuthority>, "bearer token")
        every { securityContext.authentication } returns authentication
        SecurityContextHolder.setContext(securityContext);
        every { SecurityContextHolder.getContext().authentication.name } returns "user"
        every { userRepository.findByUsername("user") } returns user

        val serviceResult = userService.getCurrentUser()
        assertEquals(userRepository.findByUsername("user"), serviceResult)
    }

    @Test
    fun `should throw exception while trying to get current user logging in with wrong credentials from mocked Repository`() {
        every { authentication.principal } returns JwtUserDetails("user1", "\$2y\$10\$mt1Ev5vlAx2/RZrlFicF1uQNJk3SCGiCYLn.exBGEHL09hwWJfUNi",
            listOf("user") as List<SimpleGrantedAuthority>, "bearer token")
        every { securityContext.authentication } returns authentication
        SecurityContextHolder.setContext(securityContext);
        every { SecurityContextHolder.getContext().authentication.name } returns "user1"
        every { userRepository.findByUsername("user1") } returns null

        val exception = assertThrows(Exception::class.java, { userService.getCurrentUser() })
        assertEquals("User with matching username and password not found", exception.localizedMessage);
    }

}