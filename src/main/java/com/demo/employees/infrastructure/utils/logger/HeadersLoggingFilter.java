
package com.demo.employees.infrastructure.utils.logger;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Enumeration;

@Component
public class HeadersLoggingFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(HeadersLoggingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest req) {
            StringBuilder sb = new StringBuilder("Incoming request headers: ");
            Enumeration<String> names = req.getHeaderNames();
            boolean first = true;
            while (names.hasMoreElements()) {
                String name = names.nextElement();
                if (name.equalsIgnoreCase("authorization")) continue;
                String value = req.getHeader(name);
                if (value != null && value.length() > 200) value = value.substring(0, 200) + "...";
                if (!first) sb.append(", ");
                sb.append(name).append("=").append(value);
                first = false;
            }
            log.info(sb.toString());
        }
        chain.doFilter(request, response);
    }
}
