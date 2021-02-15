package src.main.com.novatec.graphql.usermanagement.security

import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtSuccessHandler : AuthenticationSuccessHandler{

    @Throws(IOException::class, ServletException::class)
    override fun onAuthenticationSuccess(httpServletRequest: HttpServletRequest?, httpServletResponse: HttpServletResponse?, authentication: Authentication?) {
        println("Successfully Authentication")
    }
}