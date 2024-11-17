import bg.sofia.uni.fmi.mjt.eventbus.EventBus;
import bg.sofia.uni.fmi.mjt.eventbus.EventBusImpl;
import bg.sofia.uni.fmi.mjt.eventbus.events.Event;
import bg.sofia.uni.fmi.mjt.eventbus.events.event.MyEvent;
import bg.sofia.uni.fmi.mjt.eventbus.events.event.SystemStatusEvent;
import bg.sofia.uni.fmi.mjt.eventbus.events.payload.system.StatusPayload;
import bg.sofia.uni.fmi.mjt.eventbus.events.payload.system.SystemStatus;
import bg.sofia.uni.fmi.mjt.eventbus.subscribers.DeferredEventSubscriber;
import bg.sofia.uni.fmi.mjt.eventbus.subscribers.Subscriber;

import java.time.Instant;

public class Main {
    public static void main(String[] args) {
        EventBus eventBus = new EventBusImpl();
        Subscriber<MyEvent> deferredSubscriber = new DeferredEventSubscriber<>();
        Subscriber<SystemStatusEvent> subscriber = new DeferredEventSubscriber<>();

        SystemStatusEvent event = new SystemStatusEvent(new StatusPayload(new SystemStatus(0.1, 0.2, 0.3)), Instant.now(), 5);

        eventBus.subscribe(SystemStatusEvent.class, subscriber);

        eventBus.publish(event);
        eventBus.subscribe(MyEvent.class, deferredSubscriber);

        //eventBus.clear();

        for (Event<?> curEvent : eventBus.getEventLogs(
                SystemStatusEvent.class,
                Instant.now().minusSeconds(100),
                Instant.now())) {
            System.out.println(curEvent);
        }
        //System.out.println();

        System.out.println(eventBus.getSubscribersForEvent(SystemStatusEvent.class).isEmpty());
        System.out.println(event.getClass());
    }
}
