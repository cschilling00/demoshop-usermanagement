package src.main.com.novatec.graphql.usermanagement.service

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import src.main.com.novatec.graphql.usermanagement.model.JwtUserDetails
import src.main.com.novatec.graphql.usermanagement.model.User
import src.main.com.novatec.graphql.usermanagement.repository.UserRepository
import src.main.com.novatec.graphql.usermanagement.security.SecurityProperties
import java.time.Instant
import java.util.*

@Service
class JwtUserDetailsService(
    val verifier: JWTVerifier,
    val algorithm: Algorithm,
    val userRepository: UserRepository,
    val securityProperties: SecurityProperties
) : UserDetailsService {

    override fun loadUserByUsername(username: String): JwtUserDetails {
        return userRepository
            .findByUsername(username)
            ?.let { getUserDetails(it, getToken(it)) } //
            ?: throw Exception("Username or password didn't match")
    }

    fun getToken(user: User): String {
        val now = Instant.now()
        val expiry = Instant.now().plus(securityProperties.tokenExpiration)
        return JWT
            .create()
            .withIssuer(securityProperties.tokenIssuer)
            .withIssuedAt(Date.from(now))
            .withExpiresAt(Date.from(expiry))
            .withSubject(user.username)
            .sign(algorithm)
    }

    private fun getUserDetails(user: User?, token: String): JwtUserDetails {
        user ?: throw Error()
        return JwtUserDetails(
            user.username,
            user.password,
            listOf(user.role).map { SimpleGrantedAuthority(it) },
            token
        )
    }

    fun loadUserByToken(token: String): JwtUserDetails {
        return getDecodedToken(token)
            ?.let { it.subject }
            ?.let { userRepository.findByUsername(it) }
            ?.let { getUserDetails(it, token) } ?: throw Error()
    }

    private fun getDecodedToken(token: String): DecodedJWT? {
        return verifier.verify(token)
    }
}
