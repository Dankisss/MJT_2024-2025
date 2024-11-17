package bg.sofia.uni.fmi.mjt.eventbus.events.payload;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CollectionPayload<E> extends AbstractPayload<Collection<E>> {
    public CollectionPayload(Collection<E> payload) {
        super(payload);
    }

    @Override
    public int getSize() {
        return payload.size();
    }

    @Override
    public Collection<E> getPayload() {
        return Collections.unmodifiableCollection(payload);
    }

    public static void main(String[] args) {
        CollectionPayload<String> collectionPayload = new CollectionPayload<>(List.of("abc", "cde", "efg"));

        System.out.println(collectionPayload.getPayload());
    }
}
