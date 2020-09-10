package org.example;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/greeting")
public class GreetingController {

    private final GreetingService greetingService;

    public GreetingController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @GetMapping
    public Greeting greeting(@RequestParam(value = "name") String name) {
        return greetingService.greet(name);
    }

    @GetMapping("/count")
    public long count() {
        return greetingService.count();
    }

    @DeleteMapping
    public void resetGreetingCounter() {
        greetingService.resetCounter();
    }
}
