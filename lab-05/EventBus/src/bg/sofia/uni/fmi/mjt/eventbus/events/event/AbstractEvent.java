package bg.sofia.uni.fmi.mjt.eventbus.events.event;

import bg.sofia.uni.fmi.mjt.eventbus.events.Event;
import bg.sofia.uni.fmi.mjt.eventbus.events.Payload;

import java.time.Instant;

public abstract class AbstractEvent<E extends Payload<?>> implements Event<E> {
    private final E payload;
    private final Instant time;
    private final int priority;

    AbstractEvent(E payload, Instant time, int priority) {
        this.payload = payload;
        this.time = time;
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
        return payload.toString();
    }

    @Override
    public E getPayload() {
        return payload;
    }

    @Override
    public String toString() {
        return "payload=" + payload +
                ", time=" + time +
                ", priority=" + priority +
                '}';
    }
}
