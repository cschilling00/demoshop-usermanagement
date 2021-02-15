package src.main.com.novatec.graphql.usermanagement.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import src.main.com.novatec.graphql.usermanagement.security.JwtEntryPoint
import src.main.com.novatec.graphql.usermanagement.security.JwtProvider
import src.main.com.novatec.graphql.usermanagement.security.JwtSuccessHandler
import src.main.com.novatec.graphql.usermanagement.security.JwtTokenFilter


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity(debug = true)
class SpringSecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    private lateinit var authenticationProvider: JwtProvider
    @Autowired
    private lateinit var entryPoint: JwtEntryPoint

    @Bean
    override fun authenticationManager(): AuthenticationManager? {
        return ProviderManager(authenticationProvider)
    }

    @Bean
    fun authenticationTokenFilter(): JwtTokenFilter? {
        val filter = JwtTokenFilter("/**")
        filter.setAuthenticationManager(authenticationManager())
        filter.setAuthenticationSuccessHandler(JwtSuccessHandler())
        return filter
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
//        http.authorizeRequests().antMatchers("**").permitAll()
//        http.csrf().disable()
//                .authorizeRequests().antMatchers("/login").permitAll().and()
//                .authorizeRequests().antMatchers("/getUser").hasAuthority("user").and()
//                .authorizeRequests().antMatchers("/graphql").hasAuthority("user").and()
//                .exceptionHandling().authenticationEntryPoint(entryPoint)
//                .and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//
//        http.addFilterBefore(authenticationTokenFilter(), UsernamePasswordAuthenticationFilter::class.java);
//        http.headers().cacheControl()
    }
}