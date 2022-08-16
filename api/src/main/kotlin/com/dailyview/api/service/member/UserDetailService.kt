package com.dailyview.api.service.member

import com.dailyview.api.exception.BusinessException
import com.dailyview.api.exception.ErrorCode
import com.dailyview.domain.entity.MemberRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailService(private val memberRepository: MemberRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val member = memberRepository.findByEmail(username)
            ?: throw BusinessException(ErrorCode.MEMBER_DOES_NOT_EXISTS, "이메일을 확인해주세요.")
        return UserDetailsImpl(member)
    }
}
