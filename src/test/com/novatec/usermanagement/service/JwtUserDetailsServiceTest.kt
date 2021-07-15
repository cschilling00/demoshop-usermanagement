package src.test.com.novatec.usermanagement.service

import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import src.main.com.novatec.usermanagement.model.JwtUserDetails
import src.main.com.novatec.usermanagement.model.User
import src.main.com.novatec.usermanagement.repository.UserRepository
import src.main.com.novatec.usermanagement.security.SecurityProperties
import src.main.com.novatec.usermanagement.service.JwtUserDetailsService
import java.time.Duration
import java.util.*

@ExtendWith(
    MockKExtension::class)
internal class JwtUserDetailsServiceTest{

    @MockK
    private val userRepository = mockk<UserRepository>()

    @MockK
    private val verifier = mockk<JWTVerifier>()

    @MockK
    private val algorithm = mockk<Algorithm>()

    @MockK
    private val securityProperties = mockk<SecurityProperties>()

    @InjectMockKs
    lateinit var jwtUserDetailsService: JwtUserDetailsService

    //Should be integration test
    @Test
    fun `Service should load user by username from mocked Repository`() {
        val user = User("602a74164f9ff6408aad5da6", "user", "\$2y\$10\$mt1Ev5vlAx2/RZrlFicF1uQNJk3SCGiCYLn.exBGEHL09hwWJfUNi", "user", "cs00@test.de", listOf("Reuteäckerstr. 70", "88433 Ingerkingen"))

        every { userRepository.findByUsername("user") } returns user
        every { securityProperties.tokenExpiration } returns Duration.ofHours(4)
        every { securityProperties.tokenIssuer } returns "productservice-api"
        every { algorithm.name } returns "HMAC512"
//        every { algorithm.signingKeyId } returns "productservice-secret" //wrong signing key id

        val serviceResult = jwtUserDetailsService.loadUserByUsername("user")
        assertEquals(userRepository.findByUsername("user"), serviceResult)
    }

//    @Test
//    fun `Service should create token`() {
//        val user = User("602a74164f9ff6408aad5da6", "user", "\$2y\$10\$mt1Ev5vlAx2/RZrlFicF1uQNJk3SCGiCYLn.exBGEHL09hwWJfUNi", "user", "cs00@test.de", listOf("Reuteäckerstr. 70", "88433 Ingerkingen"))
//
//        every { securityProperties.tokenExpiration } returns Duration.ofHours(4)
//        every { securityProperties.tokenIssuer } returns "productservice-api"
//        every { algorithm.name } returns "HMAC512"
//        every { algorithm.signingKeyId } returns "productservice-secret"
//
//        val serviceResult = jwtUserDetailsService.createToken(user)
//        assertEquals(userRepository.findByUsername("user"), serviceResult)
//    }


}