package src.main.com.novatec.graphql.usermanagement.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class JwtUserDetails(private val userName: String, private val password: String, private val token: String, grantedAuthorities: List<GrantedAuthority>) : UserDetails {

    private val authorities: Collection<GrantedAuthority>

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return authorities
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return userName
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return false
    }

    override fun isEnabled(): Boolean {
        return true
    }

    init {
        authorities = grantedAuthorities
    }
}