package src.main.com.novatec.graphql.usermanagement.security

import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import src.main.com.novatec.graphql.usermanagement.service.JwtUserDetailsService
import java.lang.Error
import java.util.*
import java.util.function.Predicate.not
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
        getToken(request)
            .also { println(it) }
            //.let { jwtUserDetailsService.loadUserByToken(it) }
    }

    private fun getToken(request: HttpServletRequest): String? {
        return request.getHeader(AUTHORIZATION_HEADER)
            ?.split("Bearer ")
            ?.last()
    }
}
