package com.dailyview.domain.entity.member

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "member_detail")
class MemberDetail(
    @Column(length = 50)
    var statusMessage: String,
    @Column(length = 200)
    var profileImage: String,
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @OneToOne(optional = false)
    @JoinColumn(name = "member_id")
    var member: Members? = null
}
