package com.nscs.examautosys.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ExamApplicationFormMapperTest {

    private ExamApplicationFormMapper examApplicationFormMapper;

    @BeforeEach
    public void setUp() {
        examApplicationFormMapper = new ExamApplicationFormMapperImpl();
    }
}
