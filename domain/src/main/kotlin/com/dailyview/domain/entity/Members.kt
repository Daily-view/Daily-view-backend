package com.dailyview.domain.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "members")
class Members(
    @Column(length = 50, unique = true)
    val email: String,
    @Column(length = 10, unique = true)
    val nickname: String,
    @Column(length = 20)
    val password: String,
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}