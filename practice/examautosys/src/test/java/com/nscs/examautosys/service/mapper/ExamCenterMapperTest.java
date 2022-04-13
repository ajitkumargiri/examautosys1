package com.nscs.examautosys.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ExamCenterMapperTest {

    private ExamCenterMapper examCenterMapper;

    @BeforeEach
    public void setUp() {
        examCenterMapper = new ExamCenterMapperImpl();
    }
}
