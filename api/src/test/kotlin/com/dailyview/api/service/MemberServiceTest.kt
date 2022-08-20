package com.dailyview.api.service

import com.dailyview.api.exception.BusinessException
import com.dailyview.api.exception.ErrorCode
import com.dailyview.api.generated.types.CreateMemberInput
import com.dailyview.api.service.member.MemberService
import com.dailyview.domain.entity.member.MemberRepository
import com.dailyview.domain.entity.member.Members
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class MemberServiceTest : BehaviorSpec({
    val memberRepository: MemberRepository = mockk()
    val encoder: BCryptPasswordEncoder = mockk()
    val memberService = MemberService(encoder, memberRepository)

    Given("Create member input set up") {
        val mockMember = Members(email = "popawaw@naver.com", nickname = "종윤", password = "12345678").apply { id = 1L }
        val input = CreateMemberInput(email = "popawaw@naver.com", "종윤", "12345678")
        When("Email 이 중복되면") {
            every { memberRepository.existsByEmail(input.email) } returns true
            Then("예외가 발생한다.") {
                val result = shouldThrow<BusinessException> { memberService.signup(input) }
                result.errorCode shouldBe ErrorCode.EMAIL_IS_DUPLICATED
            }
        }
        When("nickname 이 중복되면") {
            every { memberRepository.existsByEmail(input.email) } returns false
            every { memberRepository.existsByNickname(input.nickname) } returns true
            Then("예외가 발생한다.") {
                val result = shouldThrow<BusinessException> { memberService.signup(input) }
                result.errorCode shouldBe ErrorCode.NICKNAME_IS_DUPLICATED
            }
        }
        When("모든조건을 충족하면") {
            every { memberRepository.existsByEmail(input.email) } returns false
            every { memberRepository.existsByNickname(input.nickname) } returns false
            every { encoder.encode(input.password) } returns "encodepassword"
            every { memberRepository.save(any()) } returns mockMember
            Then("signup 에 성공한다.") {
                val result = withContext(Dispatchers.IO) {
                    memberService.signup(input)
                }
                result shouldBe true
            }
        }
    }
})
