package bg.sofia.uni.fmi.mjt.eventbus.events.payload;

import bg.sofia.uni.fmi.mjt.eventbus.events.Payload;

public abstract class AbstractPayload<E> implements Payload<E> {
    E payload;

    public AbstractPayload(E payload) {
        this.payload = payload;
    }

    @Override
    public abstract int getSize();

    @Override
    public E getPayload() {
        return payload;
    }

    @Override
    public String toString() {
        return "payload= " + payload;

    }
}
