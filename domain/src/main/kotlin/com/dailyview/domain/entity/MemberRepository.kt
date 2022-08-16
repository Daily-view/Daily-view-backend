package com.dailyview.domain.entity

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository : JpaRepository<Members, Long> {

    fun existsByEmail(email: String): Boolean
    fun existsByNickname(nickname: String): Boolean

    fun findByEmail(email: String): Members?
}
