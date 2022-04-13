package com.nscs.examautosys.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AcademicBatchMapperTest {

    private AcademicBatchMapper academicBatchMapper;

    @BeforeEach
    public void setUp() {
        academicBatchMapper = new AcademicBatchMapperImpl();
    }
}
