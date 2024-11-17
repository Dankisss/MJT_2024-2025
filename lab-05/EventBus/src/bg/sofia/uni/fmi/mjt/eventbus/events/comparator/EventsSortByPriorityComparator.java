package bg.sofia.uni.fmi.mjt.eventbus.events.comparator;

import bg.sofia.uni.fmi.mjt.eventbus.events.Event;

import java.util.Comparator;

public class EventsSortByPriorityComparator<E extends Event<?>> implements Comparator<E> {
    @Override
    public int compare(E o1, E o2) {
        int priorities = Integer.compare(o1.getPriority(), o2.getPriority());

        if (priorities == 0) {
            return o2.getTimestamp().compareTo(o1.getTimestamp());
        }

        return priorities;
    }
}
