package src.main.com.novatec.graphql.usermanagement.security

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import src.main.com.novatec.graphql.usermanagement.service.JwtUserDetailsService

@Configuration
class SecurityConfig {

    @Autowired
    private lateinit var securityProperties: SecurityProperties

    @Bean
    fun jwtAlgorithm(): Algorithm? {
        return Algorithm.HMAC512(securityProperties.tokenSecret)
    }

    @Bean
    fun verifier(algorithm: Algorithm?): JWTVerifier {
        return JWT
            .require(algorithm)
            .withIssuer(securityProperties.tokenIssuer)
            .build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder? {
        return BCryptPasswordEncoder(securityProperties.passwordStrength)
    }

    @Bean
    fun authenticationProvider(userService: JwtUserDetailsService?, passwordEncoder: PasswordEncoder?): AuthenticationProvider? {
        val provider = DaoAuthenticationProvider()
        provider.setUserDetailsService(userService)
        provider.setPasswordEncoder(passwordEncoder)
        return provider
    }
}
