package com.dailyview.api.service

import com.dailyview.domain.entity.MemberRepository
import org.springframework.stereotype.Service

@Service
class MemberService(
    val memberRepository: MemberRepository
)
