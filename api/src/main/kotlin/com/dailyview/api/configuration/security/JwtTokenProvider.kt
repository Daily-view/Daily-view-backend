package com.dailyview.api.configuration.security

import com.dailyview.api.service.auth.JwtDto
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import java.security.Key
import java.util.Date
import javax.servlet.http.HttpServletRequest

const val REFRESH_TOKEN_EXPIRES = 60 * 60 * 24 * 14 // 14 day

@Component
class JwtTokenProvider(
    @Value("\${jwt.key}")
    private val key: ByteArray
) {
    private val jwtKey: Key = Keys.hmacShaKeyFor(key)
    private val BEARER_TYPE = "Bearer"
    private val CLAIM_JWT_TYPE_KEY = "type"
    private val CLAIM_AUTHORITIES_KEY = "authorities"

    private val tokenValidTime = 30 * 60 * 1000L

    fun generateTokenDto(authentication: Authentication): JwtDto {
        val authorities = authentication.authorities.map { it.authority }
        val now = Date()
        val authoritiesString = authentication.authorities.joinToString(",")
        val accessToken = Jwts.builder()
            .setSubject(authentication.name)
            .claim(CLAIM_JWT_TYPE_KEY, BEARER_TYPE)
            .claim(CLAIM_AUTHORITIES_KEY, authorities[0])
            .setIssuedAt(now)
            .setExpiration(Date(now.time + tokenValidTime))
            .signWith(jwtKey, SignatureAlgorithm.HS512)
            .compact()

        val refreshToken = Jwts.builder()
            .setSubject(authentication.name)
            .setExpiration(Date(now.time + REFRESH_TOKEN_EXPIRES))
            .claim(CLAIM_JWT_TYPE_KEY, BEARER_TYPE)
            .claim(CLAIM_AUTHORITIES_KEY, authorities[0])
            .setIssuedAt(now)
            .signWith(jwtKey, SignatureAlgorithm.HS512)
            .compact()
        return JwtDto(tokenType = BEARER_TYPE, token = accessToken, refreshToken = refreshToken)
    }

    fun getAuthentication(token: String): Authentication {
        val claims = parseClaims(token)
        val authorities =
            claims[CLAIM_AUTHORITIES_KEY]?.toString()?.split(",")?.map { SimpleGrantedAuthority(it) } ?: emptyList()
        val principal = User(claims.subject, "", authorities)
        return UsernamePasswordAuthenticationToken(principal, token, authorities)
    }

    fun parseClaims(token: String): Claims {
        return Jwts.parserBuilder().setSigningKey(jwtKey).build().parseClaimsJws(token).body
    }

    fun resolveToken(request: HttpServletRequest): String? {
        return request.getHeader("Authorization")
    }

    fun validation(token: String): Boolean {
        val claims = parseClaims(token)
        val exp = claims.expiration
        return exp.after(Date())
    }
}
