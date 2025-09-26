package com.demo.employees.infrastructure.configurations.interceptors;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import static com.demo.employees.infrastructure.utils.logger.LoggingConstants.NO_QUERY_PARAMS;

@Component
public class RequestLoggingInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String fullUri = UriComponentsBuilder.fromUriString(request.getRequestURI())
                .query(request.getQueryString())
                .build()
                .toUriString();
        MDC.put(MDCKeys.HTTP_METHOD.getValue(), request.getMethod());
        MDC.put(MDCKeys.REQUEST_URI.getValue(), fullUri);
        MDC.put(MDCKeys.QUERY_PARAMS.getValue(), request.getQueryString() != null ? request.getQueryString() : NO_QUERY_PARAMS);

        logger.debug("[request start]");

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        logger.debug("[request end]");
    }
}
