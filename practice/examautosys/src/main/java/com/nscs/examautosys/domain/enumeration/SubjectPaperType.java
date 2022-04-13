package com.nscs.examautosys.domain.enumeration;

/**
 * The SubjectPaperType enumeration.
 */
public enum SubjectPaperType {
    PRACTICAL("Practical"),
    THEORY("Theory"),
    PROJECT_WORK("ProjectWork");

    private final String value;

    SubjectPaperType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
