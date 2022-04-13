package com.nscs.examautosys.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UniversityMapperTest {

    private UniversityMapper universityMapper;

    @BeforeEach
    public void setUp() {
        universityMapper = new UniversityMapperImpl();
    }
}
