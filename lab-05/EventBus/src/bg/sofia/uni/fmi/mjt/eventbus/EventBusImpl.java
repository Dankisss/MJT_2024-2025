package bg.sofia.uni.fmi.mjt.eventbus;

import bg.sofia.uni.fmi.mjt.eventbus.events.Event;
import bg.sofia.uni.fmi.mjt.eventbus.events.Payload;
import bg.sofia.uni.fmi.mjt.eventbus.events.comparator.EventsSortByTimestampComparator;
import bg.sofia.uni.fmi.mjt.eventbus.exception.MissingSubscriptionException;
import bg.sofia.uni.fmi.mjt.eventbus.subscribers.Subscriber;

import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

public class EventBusImpl implements EventBus {
    private final Map<Class<? extends Event<?>>, Set<Subscriber<? extends Event<?>>>> subscriptions;
    private final Map<Class<? extends Event<?>>, Queue<Event<? extends Payload<?>>>> eventLog;

    public EventBusImpl() {
        subscriptions = new HashMap<>();
        eventLog = new HashMap<>();
    }

    @Override
    public <T extends Event<?>> void subscribe(Class<T> eventType, Subscriber<? super T> subscriber) {
        checkIsNull(eventType);
        checkIsNull(subscriber);

        eventLog.putIfAbsent(eventType, new PriorityQueue<>(new EventsSortByTimestampComparator<>()));
        subscriptions.putIfAbsent(eventType, new HashSet<>());
        subscriptions.get(eventType).add(subscriber);

        for (Event<? extends Payload<?>> event : eventLog.get(eventType)) {
            subscriber.onEvent((T) event);
        }
    }

    @Override
    public <T extends Event<?>> void unsubscribe(Class<T> eventType, Subscriber<? super T> subscriber)
            throws MissingSubscriptionException {
        checkIsNull(eventType);
        checkIsNull(subscriber);

        checkSubscription(eventType, subscriber);

        subscriptions.get(eventType).remove(subscriber);
    }

    @Override
    public <T extends Event<?>> void publish(T event) {
        checkIsNull(event);

        Class<T> eventClass = (Class<T>) event.getClass();

        eventLog.putIfAbsent(eventClass, new PriorityQueue<>(new EventsSortByTimestampComparator<>()));
        eventLog.get(eventClass).add(event);

        for (Subscriber<?> subscriber : subscriptions.getOrDefault(eventClass, new HashSet<>())) {
            ((Subscriber<T>) subscriber).onEvent(event);
        }
    }

    @Override
    public void clear() {
        subscriptions.clear();
        eventLog.clear();
    }

    @Override
    public Collection<? extends Event<?>> getEventLogs(Class<? extends Event<?>> eventType, Instant from, Instant to) {
        checkIsNull(eventType);
        checkIsNull(from);
        checkIsNull(to);

        Set<Event<? extends Payload<?>>> events = new TreeSet<>(new EventsSortByTimestampComparator<>());

        Queue<Event<? extends Payload<?>>> curEvents = eventLog.getOrDefault(eventType, new PriorityQueue<>());

        for (Event<? extends Payload<?>> event : curEvents) {
            if ((event.getTimestamp().equals(from) || event.getTimestamp().isAfter(from))
                    && event.getTimestamp().isBefore(to)) {
                events.add(event);
            }
        }

        return Set.copyOf(events);
    }

    @Override
    public <T extends Event<?>> Collection<Subscriber<?>> getSubscribersForEvent(Class<T> eventType) {
        checkIsNull(eventType);

        return Set.copyOf(subscriptions.getOrDefault(eventType, new HashSet<>()));
    }

    private <T> void checkIsNull(T ref) {
        if (ref == null) {
            throw new IllegalArgumentException("The reference is null.");
        }
    }

    private <T extends Event<?>> void checkSubscription(Class<T> eventType, Subscriber<? super T> subscriber)
            throws MissingSubscriptionException {

        Set<? super Subscriber<?>> set = subscriptions.getOrDefault(eventType, new HashSet<>());

        if (!set.contains(subscriber)) {
            throw new MissingSubscriptionException("The event is missing subscription");
        }
    }
}
