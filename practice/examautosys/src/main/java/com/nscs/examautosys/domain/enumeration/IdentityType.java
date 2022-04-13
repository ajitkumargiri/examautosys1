package com.nscs.examautosys.domain.enumeration;

/**
 * The IdentityType enumeration.
 */
public enum IdentityType {
    ADHAR("Adhar"),
    PAN_CARD("Pancard"),
    COLLEGE_ID_CARD("CollegeIDCard");

    private final String value;

    IdentityType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
