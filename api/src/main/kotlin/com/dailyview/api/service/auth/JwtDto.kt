package com.dailyview.api.service.auth

data class JwtDto(val tokenType: String = "Bearer", val token: String, val refreshToken: String)