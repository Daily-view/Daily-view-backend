package com.dailyview.domain.provider

import org.springframework.data.auditing.DateTimeProvider
import org.springframework.stereotype.Component
import java.time.OffsetDateTime
import java.time.temporal.TemporalAccessor
import java.util.Optional

@Component("dateTimeProvider")
class CustomDateTimeProvider : DateTimeProvider {

    override fun getNow(): Optional<TemporalAccessor> {
        return Optional.of(OffsetDateTime.now())
    }
}
