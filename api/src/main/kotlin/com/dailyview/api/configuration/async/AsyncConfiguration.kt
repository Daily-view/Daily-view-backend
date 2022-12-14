package com.dailyview.api.configuration.async

import org.slf4j.LoggerFactory
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.AsyncConfigurer
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor

@EnableAsync
@Configuration
class AsyncConfiguration : AsyncConfigurer {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun getAsyncExecutor(): Executor {
        return asyncTaskExecutor()
    }

    override fun getAsyncUncaughtExceptionHandler(): AsyncUncaughtExceptionHandler {
        return AsyncUncaughtExceptionHandler { ex, method, params ->
            logger.warn("unhandled exception: {} {} {}", method, params, ex.message)
        }
    }

    fun asyncTaskExecutor(): Executor {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = 20
        executor.maxPoolSize = 100
        executor.queueCapacity = 500
        executor.setThreadNamePrefix("DailViewAsyncTaskExecutor-")
        executor.initialize()
        return executor
    }
}
