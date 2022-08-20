package com.dailyview.api.service.member

import com.dailyview.api.configuration.security.JwtTokenProvider
import com.dailyview.api.service.auth.RefreshTokenService
import com.dailyview.domain.entity.member.MemberRepository
import com.dailyview.domain.entity.redis.MemberRefreshTokenRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.mockk
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder

class MemberAuthServiceTest : BehaviorSpec({
    val memberRepository: MemberRepository = mockk()
    val jwtTokenProvider: JwtTokenProvider = mockk()
    val refreshTokenService: RefreshTokenService = mockk()
    val refreshTokenRepository: MemberRefreshTokenRepository = mockk()
    val authenticationManagerBuilder: AuthenticationManagerBuilder = mockk()
    val memberAuthService = MemberAuthService(
        authenticationManagerBuilder, jwtTokenProvider, refreshTokenService, memberRepository, refreshTokenRepository
    )

})
