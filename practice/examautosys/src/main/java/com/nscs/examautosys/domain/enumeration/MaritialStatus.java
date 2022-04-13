package com.nscs.examautosys.domain.enumeration;

/**
 * The MaritialStatus enumeration.
 */
public enum MaritialStatus {
    MARRIED("Married"),
    UNMARRIED("Unmarried");

    private final String value;

    MaritialStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
