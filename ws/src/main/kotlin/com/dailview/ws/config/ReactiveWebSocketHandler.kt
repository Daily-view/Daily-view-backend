package com.dailview.ws.config

import com.dailview.ws.GreetingsPublisher
import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class ReactiveWebSocketHandler(
    val greetingsPublisher: GreetingsPublisher
) : WebSocketHandler {

    private val publisher = Flux.create(greetingsPublisher).share()
    override fun handle(session: WebSocketSession): Mono<Void> {
        session
            .receive()
            .map { it.payloadAsText }
            .doOnNext { greetingsPublisher.push(it) }
            .subscribe()

        val message = publisher.map { session.textMessage(it) }
        return session.send(message)
    }
}
