package com.dailyview.api.service

import com.dailyview.domain.entity.MemberRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailService(private val memberRepository: MemberRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val member = memberRepository.findById(username.toLong()).orElseThrow { RuntimeException() }
        return UserDetailsImpl(member)
    }
}