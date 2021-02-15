package src.main.com.novatec.graphql.usermanagement.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import src.main.com.novatec.graphql.usermanagement.model.User

@Component
class JwtGenerator {

    fun generate(user: User): String? {
        val claims = Jwts.claims()
                .setSubject(user.username)
        claims["password"] = user.password
        claims["role"] = user.role
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, "Graphql")
                .compact()
    }
}