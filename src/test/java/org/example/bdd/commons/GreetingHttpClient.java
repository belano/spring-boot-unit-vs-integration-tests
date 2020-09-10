package org.example.bdd.commons;

import io.cucumber.spring.ScenarioScope;
import org.example.Greeting;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@ScenarioScope
public class GreetingHttpClient {

    private static final String SERVER_URL = "http://localhost";
    private static final String GREETING_ENDPOINT = "/greeting";
    private static final String GREETING_COUNT_ENDPOINT = "/greeting/count";

    @LocalServerPort
    private int port;
    private final RestTemplate restTemplate = new RestTemplate();

    private String greetingEndpoint() {
        return SERVER_URL + ":" + port + GREETING_ENDPOINT;
    }

    private String getGreetingCountEndpoint() {
        return SERVER_URL + ":" + port + GREETING_COUNT_ENDPOINT;
    }

    public ResponseEntity<Greeting> greet(String name) {
        return restTemplate.getForEntity(greetingEndpoint() + "?name={name}", Greeting.class, name);
    }

    public void reset() {
        restTemplate.delete(greetingEndpoint());
    }

    public Long count() {
        return restTemplate.getForObject(getGreetingCountEndpoint(), Long.TYPE);
    }

}
