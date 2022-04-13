package com.nscs.examautosys.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CollegeMapperTest {

    private CollegeMapper collegeMapper;

    @BeforeEach
    public void setUp() {
        collegeMapper = new CollegeMapperImpl();
    }
}
