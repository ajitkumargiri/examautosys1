package com.nscs.examautosys.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.nscs.examautosys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ExamCenterTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExamCenter.class);
        ExamCenter examCenter1 = new ExamCenter();
        examCenter1.setId(1L);
        ExamCenter examCenter2 = new ExamCenter();
        examCenter2.setId(examCenter1.getId());
        assertThat(examCenter1).isEqualTo(examCenter2);
        examCenter2.setId(2L);
        assertThat(examCenter1).isNotEqualTo(examCenter2);
        examCenter1.setId(null);
        assertThat(examCenter1).isNotEqualTo(examCenter2);
    }
}
