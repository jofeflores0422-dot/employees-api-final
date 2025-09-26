package com.demo.employees.infrastructure.configurations;

import com.demo.employees.infrastructure.configurations.interceptors.MdcCleanupInterceptor;
import com.demo.employees.infrastructure.configurations.interceptors.RequestLoggingInterceptor;
import com.demo.employees.infrastructure.configurations.interceptors.TraceIdInterceptor;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorsConfiguration implements WebMvcConfigurer {
    public static String TRACE_ID_HEADER;
    @Value("${traceIdHeader}")
    private String traceIdHeader;
    @PostConstruct
    public void init() {
        TRACE_ID_HEADER = traceIdHeader;
    }
    public static String getTraceIdHeader() {
        return TRACE_ID_HEADER;
    }

    private final RequestLoggingInterceptor requestLoggingInterceptor;
    private final TraceIdInterceptor traceIdInterceptor;
    private final MdcCleanupInterceptor mdcCleanupInterceptor;

    @Autowired
    public InterceptorsConfiguration(
            RequestLoggingInterceptor requestLoggingInterceptor,
            TraceIdInterceptor traceIdInterceptor,
            MdcCleanupInterceptor mdcCleanupInterceptor
    ) {
            this.requestLoggingInterceptor = requestLoggingInterceptor;
            this.traceIdInterceptor = traceIdInterceptor;
            this.mdcCleanupInterceptor = mdcCleanupInterceptor;
        }

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(traceIdInterceptor).order(1);
            registry.addInterceptor(requestLoggingInterceptor).order(2);
            registry.addInterceptor(mdcCleanupInterceptor).order(99);
        }
    }
