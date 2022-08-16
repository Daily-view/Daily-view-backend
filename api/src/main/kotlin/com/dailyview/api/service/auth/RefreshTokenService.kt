package com.dailyview.api.service.auth

import com.dailyview.domain.entity.redis.MemberRefreshToken
import com.dailyview.domain.entity.redis.MemberRefreshTokenRepository
import org.springframework.stereotype.Service

@Service
class RefreshTokenService(
    private val refreshTokenRepository: MemberRefreshTokenRepository,
) {

    fun setRefreshToken(memberId: Long, tokenValue: String) {
        refreshTokenRepository.findByMemberId(memberId)?.let { refreshTokenRepository.delete(it) }
        val memberRefreshToken = MemberRefreshToken(tokenValue = tokenValue, memberId = memberId)
        refreshTokenRepository.save(memberRefreshToken)
    }

    fun updateRefreshToken(old: MemberRefreshToken, newToken: String) {
        refreshTokenRepository.delete(old)
        refreshTokenRepository.save(MemberRefreshToken(tokenValue = newToken, memberId = old.memberId))
    }
}
