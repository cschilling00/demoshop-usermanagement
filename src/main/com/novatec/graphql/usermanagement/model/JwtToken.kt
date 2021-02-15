package src.main.com.novatec.graphql.usermanagement.model

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken


class JwtToken(var token: String) : UsernamePasswordAuthenticationToken(null, null) {

    override fun getCredentials(): Any? {
        return null
    }

    override fun getPrincipal(): Any? {
        return null
    }
}

data class authRequest(
    private  var username: String,
    private  var password: String){
}

data class authResponse(private  var token: String){
}