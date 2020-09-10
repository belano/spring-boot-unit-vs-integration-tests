package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * MockMvc standalone - no Spring context is being loaded, explicitly configure our Controller under test,
 * the Controller Advice and our HTTP Filter
 */
@ExtendWith(MockitoExtension.class)
class WebMockStandaloneTest {
    private MockMvc mockMvc;

    // This object will be magically initialized by the initFields method below.
    private JacksonTester<Greeting> jsonGreeting;

    @Mock
    private GreetingService greetingService;

    @InjectMocks
    private GreetingController greetingController;

    @BeforeEach
    void setUp() {
        // Here we can't use @AutoConfigureJsonTesters because there isn't a Spring context
        JacksonTester.initFields(this, new ObjectMapper());
        // MockMvc standalone approach
        mockMvc = MockMvcBuilders.standaloneSetup(greetingController)
                .setControllerAdvice(new GreetingExceptionHandler())
                .addFilters(new GreetingFilter())
                .build();
    }

    @Test
    void greetingShouldReturnMessageFromService() throws Exception {
        Greeting expectedGreeting = new Greeting(1, "Hello, Mock");
        when(greetingService.greet(anyString())).thenReturn(expectedGreeting);

        MockHttpServletResponse response = this.mockMvc.perform(get("/greeting?name=Mock"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn()
                .getResponse();

        assertEquals(jsonGreeting.write(expectedGreeting)
                .getJson(), response.getContentAsString());
    }

    @Test
    void greetingShouldReturnBadRequestWhenGreetingException() throws Exception {
        when(greetingService.greet(anyString())).thenThrow(new GreetingException("Some error"));
        MockHttpServletResponse response = this.mockMvc.perform(get("/greeting?name=Mock"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse();
        assertTrue(response.getContentAsString()
                .isEmpty());
    }

    @Test
    void greetingHeaderShouldBePresent() throws Exception {
        when(greetingService.greet(anyString())).thenReturn(new Greeting(1, "Hello, Mock"));
        MockHttpServletResponse response = this.mockMvc.perform(get("/greeting?name=Mock"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        assertTrue(Objects.requireNonNull(response.getHeaders("X-GREETING"))
                .contains("greeting-header"));
    }
}
