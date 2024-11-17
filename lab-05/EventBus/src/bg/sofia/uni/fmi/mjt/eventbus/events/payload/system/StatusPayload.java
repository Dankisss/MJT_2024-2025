package bg.sofia.uni.fmi.mjt.eventbus.events.payload.system;

import bg.sofia.uni.fmi.mjt.eventbus.events.payload.AbstractPayload;

public class StatusPayload extends AbstractPayload<SystemStatus> {
    public StatusPayload(SystemStatus systemStatus) {
        super(systemStatus);
    }

    @Override
    public int getSize() {
        return getPayload().toString().length();
    }
}
