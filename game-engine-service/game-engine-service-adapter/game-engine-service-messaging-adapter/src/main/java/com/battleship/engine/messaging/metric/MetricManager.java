package com.battleship.engine.messaging.metric;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.stereotype.Component;

@Component
public class MetricManager implements MeterBinder {
    private Counter lobbyCreateFailure;

    @Override
    public void bindTo(MeterRegistry meterRegistry) {
        this.lobbyCreateFailure = Counter.
                builder("com.battleship.gameengine.mq.counter").
                description("Lobby create failure counter").
                register(meterRegistry);
    }

    public void increment() {
        lobbyCreateFailure.increment();
    }
}
