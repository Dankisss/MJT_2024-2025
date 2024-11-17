package bg.sofia.uni.fmi.mjt.eventbus.events.payload;

import java.util.Collection;
import java.util.TreeSet;

public class SortedCollectionPayload<E extends Comparable<? super E>> extends CollectionPayload<E> {
    public SortedCollectionPayload(Collection<E> payload) {
        super(new TreeSet<>(payload));
    }

//    public static void main(String[] args) {
//        SortedCollectionPayload<Integer> collectionPayload =
//              new SortedCollectionPayload<>(List.of(10, 3, 5, 1 ,3 ,4));
//
//        System.out.println(collectionPayload.getPayload());
//    }
}
