package src.main.com.novatec.graphql.usermanagement.security

import org.springframework.security.web.authentication.WebAuthenticationDetails
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken
import src.main.com.novatec.graphql.usermanagement.model.JwtUserDetails

class JWTPreAuthenticationToken : PreAuthenticatedAuthenticationToken {

    constructor(principal: JwtUserDetails, details: WebAuthenticationDetails ) :
            super(principal, details, principal.getAuthorities()) {

    }

    override fun getCredentials(): Any? {
        return null
    }
}