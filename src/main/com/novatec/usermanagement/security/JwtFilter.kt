package src.main.com.novatec.usermanagement.security

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import src.main.com.novatec.usermanagement.service.JwtUserDetailsService
import java.lang.Error
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtFilter(
    val jwtUserDetailsService: JwtUserDetailsService
) : OncePerRequestFilter() {
    private val AUTHORIZATION_HEADER = "Authorization"

    @Throws
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        try{
            getToken(request)
                    .also { println("Token: " + it) }
                    ?.let { jwtUserDetailsService.loadUserByToken(it) }

                    ?.let { jwtUserDetails -> JWTPreAuthenticationToken(
                            jwtUserDetails, WebAuthenticationDetailsSource().buildDetails(request)) }
                    ?.also { println("context: "+ SecurityContextHolder.getContext().authentication)    }
                    ?.let { SecurityContextHolder.getContext().authentication = it}
                    ?.also { println("context: "+SecurityContextHolder.getContext().authentication)    }
        }catch (e: Error){
            println(e.localizedMessage)
        }

        filterChain.doFilter(request, response) }

    private fun getToken(request: HttpServletRequest): String? {
        return request.getHeader(AUTHORIZATION_HEADER)
            ?.split("Bearer ")
            ?.last()
    }
}
