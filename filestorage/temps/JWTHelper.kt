package templates.temps

import com.example.uploader.dao.UserDao
import com.example.uploader.mappers.JWTDataToClaimsMapper
import com.example.uploader.security.constans.JWT_EXPIRATION_TIME
import com.example.uploader.security.constans.JWT_HEADER_NAME
import com.example.uploader.security.constans.JWT_TOKEN_PREFIX
import com.example.uploader.security.constans.secretKey
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import java.util.*

@Service
class JWTHelper(
        private val jwtDataToClaimsMapper: JWTDataToClaimsMapper
) {
    fun convertUserAccountToJwtString(userAccount: UserDao): String {
        val customClaims = jwtDataToClaimsMapper.parse(
                JWTData(
                        accountId = userAccount.id?.toLong()!!,
                        roles = userAccount.roles.map { it.Name!! }.toSet()
                )
        )

        return Jwts.builder()
                .setClaims(customClaims)
                .setId(UUID.randomUUID().toString())
                .setExpiration(Date(System.currentTimeMillis() + JWT_EXPIRATION_TIME))
                .setIssuedAt(Date())
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact()
    }

    fun getHeaders(jwtString: String): MultiValueMap<String, String> {
        return LinkedMultiValueMap<String, String>(1).also {
            it.add(JWT_HEADER_NAME, JWT_TOKEN_PREFIX + jwtString)
        }
    }
}