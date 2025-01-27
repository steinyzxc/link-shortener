package ru.steiny.cc.service

import io.jsonwebtoken.JwtParser
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import ru.steiny.cc.JwtProperties
import ru.steiny.cc.model.User
import java.util.*

@Service
class JwtService(
    jwtProperties: JwtProperties
) {
    private val secretKey = Keys.hmacShaKeyFor(
        jwtProperties.key.toByteArray()
    )

    val parser: JwtParser = Jwts.parser()
        .verifyWith(secretKey)
        .build()

    fun generateJwt(
        user: User,
    ): String =
        Jwts.builder()
            .claim("user_id", user.id)
            .issuedAt(Date(System.currentTimeMillis()))
            .signWith(secretKey)
            .compact()

    fun extractJwt(jwt: String): Long? {
        return try {
            (parser
                .parseSignedClaims(jwt)
                .payload["user_id"] as Int).toLong()
        } catch (ignored: Exception) {
            null
        }
    }
}