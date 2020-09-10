package org.example.bdd;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * To make Cucumber aware of your test configuration you can annotate a configuration class on your glue path
 * with @CucumberContextConfiguration
 */
@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CucumberTestContextConfiguration {
}

