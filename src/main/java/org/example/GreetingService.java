package org.example;

import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class GreetingService {

    private static final String GREETING_TEMPLATE = "Hello, %s!";
    private static final String GOODBYE_TEMPLATE = "Bye, %s!";
    private final AtomicLong counter = new AtomicLong();

    public Greeting greet(String name) {
        requireName(name);
        return new Greeting(counter.incrementAndGet(), String.format(GREETING_TEMPLATE, name));
    }

    public Greeting goodBye(String name) {
        requireName(name);
        return new Greeting(counter.incrementAndGet(), String.format(GOODBYE_TEMPLATE, name));
    }

    public void resetCounter() {
        counter.set(0L);
    }

    public long count() {
        return counter.get();
    }

    private static void requireName(String name) {
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new GreetingException("Invalid name!");
        }
    }
}
