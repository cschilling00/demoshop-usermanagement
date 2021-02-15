package src.main.com.novatec.graphql.usermanagement.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import src.main.com.novatec.graphql.usermanagement.model.JwtToken
import src.main.com.novatec.graphql.usermanagement.model.JwtUserDetails


@Component
class JwtProvider : AbstractUserDetailsAuthenticationProvider() {

    @Autowired
    private lateinit var validator: JwtValidator


    @Throws(AuthenticationException::class)
    override fun additionalAuthenticationChecks(userDetails: UserDetails?, usernamePasswordAuthenticationToken: UsernamePasswordAuthenticationToken?) {
    }

    @Throws(AuthenticationException::class)
    override fun retrieveUser(username: String?, usernamePasswordAuthenticationToken: UsernamePasswordAuthenticationToken?): UserDetails {

        val jwtAuthenticationToken: JwtToken = usernamePasswordAuthenticationToken as JwtToken
        val token: String = jwtAuthenticationToken.token

        val jwtUser = validator.validate(token) ?: throw RuntimeException("JWT Token is incorrect")
        println("jwtUser" + jwtUser)
        val grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList(jwtUser.role)
        println(grantedAuthorities)
        return JwtUserDetails(jwtUser.username, jwtUser.password,
                token,
                grantedAuthorities)
    }
}