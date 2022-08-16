package com.dailyview.api.service.member

import com.dailyview.api.configuration.security.JwtTokenProvider
import com.dailyview.api.exception.BusinessException
import com.dailyview.api.exception.ErrorCode
import com.dailyview.api.generated.types.AuthToken
import com.dailyview.api.generated.types.LoginInput
import com.dailyview.api.service.auth.JwtDto
import com.dailyview.api.service.auth.RefreshTokenService
import com.dailyview.domain.entity.MemberRepository
import com.dailyview.domain.entity.redis.MemberRefreshTokenRepository
import io.jsonwebtoken.JwtException
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MemberAuthService(
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val jwtTokenProvider: JwtTokenProvider,
    private val refreshTokenService: RefreshTokenService,
    private val memberRepository: MemberRepository,
    private val refreshTokenRepository: MemberRefreshTokenRepository,
) {

    @Transactional(readOnly = true)
    fun login(input: LoginInput): JwtDto {
        try {
            val authenticationToken = UsernamePasswordAuthenticationToken(input.email, input.password)
            val authentication: Authentication = authenticationManagerBuilder.getObject()
                .authenticate(authenticationToken)
            val jwtDto = jwtTokenProvider.generateTokenDto(authentication)
            val member = memberRepository.findById(authentication.name.toLong())
                .orElseThrow { BusinessException(ErrorCode.MEMBER_DOES_NOT_EXISTS, "이메일을 확인해주세요.") }
            refreshTokenService.setRefreshToken(memberId = member.id!!, tokenValue = jwtDto.refreshToken)
            return jwtDto
        } catch (e: BadCredentialsException) {
            throw BusinessException(ErrorCode.CHECK_YOUR_ACCOUNT, "비밀번호를 확인해주세요.")
        }
    }

    fun reisuue(refreshToken: String): JwtDto? {
        if (!jwtTokenProvider.validation(refreshToken)) {
            throw BusinessException(ErrorCode.INVALID_JWT_TOKEN, "유효하지 않은 토큰입니다.")
        }

        var authentication: Authentication
        try {
            authentication = jwtTokenProvider.getAuthentication(refreshToken)
        } catch (e: JwtException) {
            throw BusinessException(ErrorCode.INVALID_JWT_TOKEN, "유효하지 않은 토큰입니다.")
        }
        val member = memberRepository.findById(authentication.name.toLong()).orElseThrow {
            BusinessException(ErrorCode.MEMBER_DOES_NOT_EXISTS, "존재하지 않는 유저입니다. id: ${authentication.name}")
        }

        val memberRefreshToken = refreshTokenRepository.findByTokenValueAndMemberId(refreshToken, member.id!!)
            ?: return null
        val generateTokenDto = jwtTokenProvider.generateTokenDto(authentication)
        refreshTokenService.updateRefreshToken(memberRefreshToken, generateTokenDto.refreshToken)
        return generateTokenDto
    }
}
