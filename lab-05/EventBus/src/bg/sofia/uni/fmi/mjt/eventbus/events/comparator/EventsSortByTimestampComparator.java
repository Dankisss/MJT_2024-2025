package bg.sofia.uni.fmi.mjt.eventbus.events.comparator;

import bg.sofia.uni.fmi.mjt.eventbus.events.Event;

import java.util.Comparator;

public class EventsSortByTimestampComparator<T extends Event<?>> implements Comparator<T> {
    @Override
    public int compare(T o1, T o2) {
        int timestamp = o1.getTimestamp().compareTo(o2.getTimestamp());

        if (timestamp == 0) {
            return Integer.compare(o1.getPriority(), o2.getPriority());
        }

        return timestamp;
    }
}
