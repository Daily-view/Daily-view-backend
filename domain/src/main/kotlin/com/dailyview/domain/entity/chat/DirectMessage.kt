package com.dailyview.domain.entity.chat

import com.dailyview.domain.entity.member.Members
import java.time.OffsetDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType.LAZY
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "direct_message")
class DirectMessage(
    @Column(nullable = true, length = 50)
    var message: String,
    @Column(nullable = true, length = 200)
    var photo: String
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "sender_id")
    var sender: Members? = null

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "receiver_id")
    var receiver: Members? = null

    var createdAt: OffsetDateTime = OffsetDateTime.now()
}
