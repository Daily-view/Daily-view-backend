package com.dailyview.domain.entity.redis

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRefreshTokenRepository : CrudRepository<MemberRefreshToken, String> {

    fun findByMemberId(memberId: Long): MemberRefreshToken?

    fun findByTokenValueAndMemberId(token: String, memberId: Long): MemberRefreshToken?
}