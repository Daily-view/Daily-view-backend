package com.dailyview.domain.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfiguration(
    @Value("\${redis.dailyview.host}") private val redisHost: String,
    @Value("\${redis.dailyview.port}") private val redisPort: Int,
) {

    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {
        return LettuceConnectionFactory(redisHost, redisPort)
    }

    @Bean
    fun redisTemplate(): RedisTemplate<*, *> {
        val redisTemplate = RedisTemplate<ByteArray, ByteArray>()
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.valueSerializer = GenericJackson2JsonRedisSerializer()
        redisTemplate.hashKeySerializer = StringRedisSerializer()
        redisTemplate.hashValueSerializer = GenericJackson2JsonRedisSerializer()

        redisTemplate.setConnectionFactory(redisConnectionFactory())
        redisTemplate.setEnableTransactionSupport(true)
        return redisTemplate
    }
}
