package com.dailyview.api.service.member

import com.dailyview.api.configuration.security.JwtTokenProvider
import com.dailyview.api.exception.BusinessException
import com.dailyview.api.exception.ErrorCode
import com.dailyview.api.generated.types.LoginInput
import com.dailyview.api.service.auth.RefreshTokenService
import com.dailyview.domain.entity.MemberRepository
import com.dailyview.domain.entity.redis.MemberRefreshTokenRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

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
