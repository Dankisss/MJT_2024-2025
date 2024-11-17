package bg.sofia.uni.fmi.mjt.eventbus.events.event;

import bg.sofia.uni.fmi.mjt.eventbus.events.payload.StringPayload;

import java.time.Instant;

public class MyEvent extends AbstractEvent<StringPayload> {
    MyEvent(StringPayload payload, Instant time, int priority) {
        super(payload, time, priority);
    }
}
