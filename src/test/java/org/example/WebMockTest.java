package org.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

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
 * MockMVC and WebMvcTest example - With the @WebMvcTest annotation Spring will load only a partial context
 * (the controller and its surrounding configuration like filters and advices)
 */
@WebMvcTest(GreetingController.class)
@AutoConfigureJsonTesters
class WebMockTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GreetingService service;

    // This object will be initialized thanks to @AutoConfigureJsonTesters
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private JacksonTester<Greeting> jsonGreeting;

    @Test
    void greetingShouldReturnMessageFromService() throws Exception {
        Greeting expectedGreeting = new Greeting(1, "Hello, Mock");
        when(service.greet(anyString())).thenReturn(expectedGreeting);

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
        when(service.greet(anyString())).thenThrow(new GreetingException("Some error"));

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
        when(service.greet(anyString())).thenReturn(new Greeting(1, "Hello, Mock"));

        MockHttpServletResponse response = this.mockMvc.perform(get("/greeting?name=Mock"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        assertTrue(Objects.requireNonNull(response.getHeaders("X-GREETING"))
                .contains("greeting-header"));
    }

}
