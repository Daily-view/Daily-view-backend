package com.dailview.ws

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import reactor.core.publisher.FluxSink
import java.util.concurrent.BlockingQueue
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingQueue
import java.util.function.Consumer

@Component
class GreetingsPublisher : Consumer<FluxSink<String>> {

    private val logger = LoggerFactory.getLogger(this::class.java)

    val queue: BlockingQueue<String> = LinkedBlockingQueue()
    val executor = Executors.newSingleThreadExecutor()

    fun push(greeting: String): Boolean {
        return queue.offer(greeting)
    }

    override fun accept(sink: FluxSink<String>) {
        executor.execute {
            while (true) {
            }
        }
    }
}
