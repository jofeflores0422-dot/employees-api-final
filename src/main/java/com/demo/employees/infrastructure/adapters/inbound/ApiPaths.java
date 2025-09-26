package com.demo.employees.infrastructure.adapters.inbound;

public final class ApiPaths {
    private ApiPaths() {}

    public static final String EMPLOYEES = "/employees";

    public static final String DOCS = "/v3/api-docs/**";
    public static final String SWAGGER_UI_HTML = "/swagger-ui.html";
    public static final String SWAGGER_UI = "/swagger-ui/**";
    public static final String ACTUATOR_HEALTH = "/actuator/health";
    public static final String DOCS_YAML      = "/v3/api-docs.yaml";
}
