package com.dailyview.api.service

import com.dailyview.api.exception.BusinessException
import com.dailyview.api.exception.ErrorCode
import com.dailyview.api.generated.types.CreateMemberInput
import com.dailyview.domain.entity.MemberRepository
import com.dailyview.domain.entity.Members
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MemberService(
    private val encoder: BCryptPasswordEncoder,
    private val memberRepository: MemberRepository
) {

    fun signup(input: CreateMemberInput): Boolean {
        if (memberRepository.existsByEmail(input.email)) {
            throw BusinessException(ErrorCode.EMAIL_IS_DUPLICATED, "이메일이 중복입니다. email: ${input.email}")
        }
        if (memberRepository.existsByNickname(input.nickname)) {
            throw BusinessException(ErrorCode.NICKNAME_IS_DUPLICATED, "닉네임이 중복입니다. nickname: ${input.nickname}")
        }
        val member = Members(email = input.email, nickname = input.nickname, password = input.password)
        encoder.encode(member.password).also { member.password = it }
        memberRepository.save(member)
        return true
    }
}
