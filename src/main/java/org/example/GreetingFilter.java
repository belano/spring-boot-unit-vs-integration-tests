package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GreetingFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(GreetingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("X-GREETING", "greeting-header");
        LOGGER.info(">>Greeting filter");
        chain.doFilter(request, response);
        LOGGER.info("<<Greeting filter");
    }
}
