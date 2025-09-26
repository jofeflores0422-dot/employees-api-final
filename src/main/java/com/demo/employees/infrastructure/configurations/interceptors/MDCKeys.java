package com.demo.employees.infrastructure.configurations.interceptors;

public enum MDCKeys {
    HTTP_METHOD("httpMethod"),
    REQUEST_URI("requestUri"),
    QUERY_PARAMS("queryParams"),
    TRACE_ID("traceId"),
    MESSAGE_ID("messageId");

    private final String value;

    MDCKeys(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
