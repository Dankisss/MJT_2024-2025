package bg.sofia.uni.fmi.mjt.eventbus.events.payload;

public class IntegerPayload extends AbstractPayload<Integer> {
    private static final int INTEGER_SIZE = 16;
    public IntegerPayload(int payload) {
        super(payload);
    }

    @Override
    public int getSize() {
        return INTEGER_SIZE;
    }

    @Override
    public String toString() {
        return String.valueOf(payload);
    }

}
