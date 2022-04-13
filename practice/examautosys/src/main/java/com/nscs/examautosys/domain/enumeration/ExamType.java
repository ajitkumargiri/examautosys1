package com.nscs.examautosys.domain.enumeration;

/**
 * The ExamType enumeration.
 */
public enum ExamType {
    REGULAR("Regular"),
    EX_REGULAR("ExRegular");

    private final String value;

    ExamType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
