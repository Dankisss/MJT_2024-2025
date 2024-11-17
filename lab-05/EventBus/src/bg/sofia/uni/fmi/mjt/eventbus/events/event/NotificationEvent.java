package bg.sofia.uni.fmi.mjt.eventbus.events.event;

import bg.sofia.uni.fmi.mjt.eventbus.events.Event;
import bg.sofia.uni.fmi.mjt.eventbus.events.Payload;

import java.time.Instant;

public class NotificationEvent<E extends Payload<?>> implements Event<E> {
    private final E payload;
    private final Instant time;
    private final int priority;

    public NotificationEvent(E payload, int priority) {
        time = Instant.now();
        this.payload = payload;
        this.priority = priority;
    }

    @Override
    public Instant getTimestamp() {
        return time;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public String getSource() {
        return payload.getPayload().toString();
    }

    @Override
    public E getPayload() {
        return null;
    }
}
