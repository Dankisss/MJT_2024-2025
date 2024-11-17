package bg.sofia.uni.fmi.mjt.eventbus.events.payload;

import bg.sofia.uni.fmi.mjt.eventbus.events.Payload;

public class NumberPayload<T extends Number> implements Payload<T> {
    private T payload;

    @Override
    public int getSize() {
        return payload.toString().length();
    }

    @Override
    public T getPayload() {
        return payload;
    }
}
