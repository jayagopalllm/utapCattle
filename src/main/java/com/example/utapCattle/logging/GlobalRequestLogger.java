package com.example.utapCattle.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalRequestLogger {

    private static final Logger logger = LoggerFactory.getLogger(GlobalRequestLogger.class);

    @ModelAttribute
    public void logRequestUrl(HttpServletRequest request) {
        String requestUrl = request.getRequestURL().toString();
        String httpMethod = request.getMethod();
        logger.info("Incoming request: Method: {}, URL: {}", httpMethod, requestUrl);
    }
}

