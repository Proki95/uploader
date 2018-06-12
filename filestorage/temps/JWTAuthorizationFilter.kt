package templates.temps

import com.example.uploader.mappers.ClaimsToJWTDataMapper
import com.example.uploader.security.constans.JWT_HEADER_NAME
import com.example.uploader.security.constans.JWT_TOKEN_PREFIX
import com.example.uploader.security.constans.secretKey
import io.jsonwebtoken.Jwts
import mu.KLogging
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTAuthorizationFilter(
        authManager: AuthenticationManager,
        private val claimsToJWTDataMapper: ClaimsToJWTDataMapper
) : BasicAuthenticationFilter(authManager) {
    override fun doFilterInternal(
            req: HttpServletRequest,
            res: HttpServletResponse,
            chain: FilterChain
    ) {
        val header = req.getHeader(JWT_HEADER_NAME)

        /* When header is null or doesn't start with a required prefix*/
        if (header === null || !header.startsWith(JWT_TOKEN_PREFIX)) {
            logger.trace { "Request has no $JWT_HEADER_NAME or it doesn't start with $JWT_TOKEN_PREFIX" }
            chain.doFilter(req, res)
            return
        }

        val jwtToken = header.replaceFirst(JWT_TOKEN_PREFIX, "")

        SecurityContextHolder.getContext().authentication = getAuthentication(jwtToken)
        chain.doFilter(req, res)
    }

    private fun getAuthentication(jwtToken: String): Authentication {
        val jwtClaims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwtToken)
                .getBody()

        val jwtData = claimsToJWTDataMapper.parse(jwtClaims)

        return JWTAuthenticationToken(jwtData)

//        return if (user !== null) {
//            JWTAuthenticationToken(user, null, ArrayList<GrantedAuthority>())
//        } else null
    }

    companion object : KLogging()
}