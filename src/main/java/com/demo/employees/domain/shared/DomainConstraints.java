package com.demo.employees.domain.shared;

public final class DomainConstraints {
    private DomainConstraints() {}
    public static final int FIRST_NAME_MAX = 60;
    public static final int MIDDLE_NAME_MAX = 60;
    public static final int LAST_NAME_MAX = 80;
    public static final int SECOND_LAST_NAME_MAX = 80;
    public static final int POSITION_MAX = 120;
    public static final int AGE_MIN = 0;
    public static final int AGE_MAX = 120;
    public static final String SEX = "sex";
}
