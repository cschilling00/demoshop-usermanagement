package src.main.com.novatec.graphql.usermanagement.security

import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Component
import src.main.com.novatec.graphql.usermanagement.model.User


@Component
class JwtValidator {
    
    val secret = "Graphql"

    fun validate(token: String?): User? {
        var user: User? = null
        try {
            val body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .body
            user = User("", body["sub"] as String, body["password"] as String, body["role"] as String, "", listOf(""), listOf(""))
            println(user.toString())
        } catch (e: Exception) {
            println(e)
        }
        return user
    }
}