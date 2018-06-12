package templates.temps

import org.springframework.security.core.authority.SimpleGrantedAuthority

data class JWTData(
        val accountId: Long,
        val roles: Set<String>
) {
    fun getGrantedAuthorities() =
            roles.map { SimpleGrantedAuthority(it) }

    fun `is`(role: String): Boolean {
        return roles.indexOf(role) >= 0
    }
}