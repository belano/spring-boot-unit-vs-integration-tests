package org.example.bdd;

import io.cucumber.java8.En;
import org.example.Greeting;
import org.example.bdd.commons.GreetingHttpClient;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

public class GreetingCucumberStepDefinitions implements En {

    @Autowired
    private GreetingHttpClient greetingHttpClient;

    private Greeting greeting;

    public GreetingCucumberStepDefinitions() {
        Before(() -> greetingHttpClient.reset());

        Given("the greeting counter is reset",
                () -> assertEquals(0, greetingHttpClient.count()));

        When("^I greet (.+)$",
                (String name) -> greeting = greetingHttpClient.greet(name)
                        .getBody()
        );

        Then("the greeting contains {string}",
                (String expectedGreeting) -> assertAll("greeting",
                        () -> assertEquals(expectedGreeting, greeting.getContent()),
                        () -> assertTrue(greeting.getId() > 0)
                )
        );
    }
}
