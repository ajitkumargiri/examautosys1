package com.nscs.examautosys.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SubjectPaperMapperTest {

    private SubjectPaperMapper subjectPaperMapper;

    @BeforeEach
    public void setUp() {
        subjectPaperMapper = new SubjectPaperMapperImpl();
    }
}
