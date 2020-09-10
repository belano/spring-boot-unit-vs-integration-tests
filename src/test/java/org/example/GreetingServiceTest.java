package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GreetingServiceTest {

    private GreetingService testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new GreetingService();
    }

    @Test
    void shouldGreet() {
        Greeting greeting = testSubject.greet("jdoe");

        assertAll("greeting",
                () -> assertEquals("Hello, jdoe!", greeting.getContent()),
                () -> assertTrue(greeting.getId() > 0)
        );
    }

    @Test
    void shouldSayGoodbye() {
        Greeting greeting = testSubject.goodBye("jdoe");

        assertAll("greeting",
                () -> assertEquals("Bye, jdoe!", greeting.getContent()),
                () -> assertTrue(greeting.getId() > 0)
        );
    }

    @Test
    void shouldThrowExceptionWhenInvalidName() {
        assertThrows(GreetingException.class, () -> testSubject.greet(null));
    }

    @Test
    void shouldCountGreetings() {
        testSubject.greet("jdoe");
        testSubject.goodBye("jdoe");

        assertEquals(2, testSubject.count());
    }

    @Test
    void shouldResetGreetingsCounter() {
        testSubject.greet("jdoe");
        testSubject.goodBye("jdoe");

        testSubject.resetCounter();

        assertEquals(0, testSubject.count());
    }
}