package com.dailyview.api.fetcher

import com.dailyview.api.configuration.security.REFRESH_TOKEN_EXPIRES
import com.dailyview.api.exception.BusinessException
import com.dailyview.api.exception.ErrorCode
import com.dailyview.api.generated.DgsConstants
import com.dailyview.api.generated.types.AuthToken
import com.dailyview.api.generated.types.CreateMemberInput
import com.dailyview.api.generated.types.LoginInput
import com.dailyview.api.service.auth.JwtDto
import com.dailyview.api.service.member.MemberAuthService
import com.dailyview.api.service.member.MemberService
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.InputArgument
import com.netflix.graphql.dgs.internal.DgsWebMvcRequestData
import javax.servlet.ServletRequest
import javax.servlet.http.Cookie
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.context.request.ServletWebRequest


@DgsComponent
class MemberMutationFetcher(
    private val memberService: MemberService,
    private val memberAuthService: MemberAuthService,
    @Value("\${cookie-domain}")
    private val cookieDomain: String
) {
    @DgsMutation(field = DgsConstants.MUTATION.CreateMember)
    fun signup(@InputArgument input: CreateMemberInput): Boolean {
        return memberService.signup(input)
    }

    @DgsMutation(field = DgsConstants.MUTATION.Login)
    fun login(
        @InputArgument input: LoginInput,
        dfe: DgsDataFetchingEnvironment
    ): AuthToken {
        val jwtDto = memberAuthService.login(input)
        setCookie(jwtDto, dfe)
        return AuthToken(token_type = jwtDto.tokenType, token = jwtDto.token)
    }


    @DgsMutation(field = DgsConstants.MUTATION.Reissue)
    fun reissue(
        @CookieValue(required = false) refreshToken: Cookie?,
        dfe: DgsDataFetchingEnvironment
    ): AuthToken {
        if (refreshToken == null) {
            throw BusinessException(ErrorCode.INVALID_JWT_TOKEN, "refresh token 을 확인해주세요.")
        }
        val jwtDto = memberAuthService.reisuue(refreshToken = refreshToken.value)
            ?: throw BusinessException(ErrorCode.REFRESH_TOKEN_EXPIRED, "refresh token 이 만료되었습니다.")
        setCookie(jwtDto, dfe)
        return AuthToken(token_type = jwtDto.tokenType, token = jwtDto.token)
    }

    private fun setCookie(jwtDto: JwtDto, dfe: DgsDataFetchingEnvironment) {
        val cookie = Cookie("refreshToken", jwtDto.refreshToken).apply {
            this.maxAge = REFRESH_TOKEN_EXPIRES
            this.domain = cookieDomain
            this.path = "/"
        }
        addCookieForResponse(dfe, cookie)
    }

    private fun addCookieForResponse(
        dfe: DgsDataFetchingEnvironment,
        cookie: Cookie
    ) {
        val requestData = dfe.getDgsContext().requestData as DgsWebMvcRequestData
        val serverRequest = requestData.webRequest as ServletWebRequest?
        serverRequest?.response?.addCookie(cookie)
    }
}
