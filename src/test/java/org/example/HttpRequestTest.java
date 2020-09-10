package org.example;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

/**
 * SpringBootTest example with a Real Web Server - testing with a real HTTP server
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Tag("integration")
class HttpRequestTest {

    private static final String SERVER_URL = "http://localhost";
    private static final String GREETING_ENDPOINT = "/greeting";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String greetingEndpoint() {
        return SERVER_URL + ":" + port + GREETING_ENDPOINT;
    }

    @Test
    void greetingShouldGreet() {
        ResponseEntity<Greeting> greetingResponse =
                this.restTemplate.getForEntity(greetingEndpoint() + "?name={name}", Greeting.class, "jdoe");
        assertAll("Greeting properties",
                () -> assertEquals(HttpStatus.OK, greetingResponse.getStatusCode()),
                () -> assertEquals("Hello, jdoe!", Objects.requireNonNull(greetingResponse.getBody())
                        .getContent()),
                () -> assertTrue(Objects.requireNonNull(greetingResponse.getBody())
                        .getId() > 0)
        );
    }

    @Test
    void greetingShouldReturnBadRequest() {
        ResponseEntity<Greeting> greetingResponse =
                this.restTemplate.getForEntity(greetingEndpoint() + "?name={name}", Greeting.class, "");
        assertAll("Greeting properties",
                () -> assertNull(greetingResponse.getBody()),
                () -> assertEquals(HttpStatus.BAD_REQUEST, greetingResponse.getStatusCode())
        );
    }

    @Test
    void greetingHeaderShouldBePresent() {
        ResponseEntity<Greeting> greetingResponse =
                this.restTemplate.getForEntity(greetingEndpoint() + "?name={name}", Greeting.class, "jdoe");

        assertAll("Greeting properties",
                () -> assertEquals(HttpStatus.OK, greetingResponse.getStatusCode()),
                () -> assertTrue(Objects.requireNonNull(greetingResponse.getHeaders()
                        .get("X-GREETING"))
                        .contains("greeting-header"))
        );
    }
}
