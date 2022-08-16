package com.dailyview.api.service.member

import com.dailyview.api.exception.BusinessException
import com.dailyview.api.exception.ErrorCode
import com.dailyview.api.generated.types.LoginInput
import com.dailyview.domain.entity.MemberRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class MemberAuthServiceTest : BehaviorSpec({
    val memberRepository: MemberRepository = mockk()
    val encoder: BCryptPasswordEncoder = mockk()
    val memberAuthService = MemberAuthService(encoder, memberRepository)

    Given("Setup login input") {
        val loginInput = LoginInput(email = "popawaw@naver.com", password = "12345678")
        When("email 을 찾을수 없어서") {
            every { memberRepository.findByEmail(loginInput.email) } returns null
            Then("예외가 발생한다.") {
                val result = shouldThrow<BusinessException> { memberAuthService.login(loginInput) }
                result.errorCode shouldBe ErrorCode.MEMBER_DOES_NOT_EXISTS
            }
        }
    }
})
