package bg.sofia.uni.fmi.mjt.eventbus.events.payload;

public class StringPayload extends AbstractPayload<String> {
    public StringPayload(String payload) {
        super(payload);
    }

    @Override
    public String toString() {
        return payload;
    }

    @Override
    public int getSize() {
        return payload.length();
    }

}
