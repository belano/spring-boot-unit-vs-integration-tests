package org.example;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * SpringBootTest without parameters (webEnvironment = WebEnvironment.MOCK) - no real HTTP server
 */
@SpringBootTest
class SmokeTest {

    @Autowired
    private GreetingController controller;

    @Test
    void contextLoads() {
        assertNotNull(controller);
    }
}
