package bg.sofia.uni.fmi.mjt.eventbus.events.event;

import bg.sofia.uni.fmi.mjt.eventbus.events.payload.system.StatusPayload;
import java.time.Instant;

public class SystemStatusEvent extends AbstractEvent<StatusPayload> {

    public SystemStatusEvent(StatusPayload payload, int priority) {
        super(payload, Instant.now(), priority);
    }

    public SystemStatusEvent(StatusPayload payload, Instant time, int priority) {
        super(payload, time, priority);
    }
}
