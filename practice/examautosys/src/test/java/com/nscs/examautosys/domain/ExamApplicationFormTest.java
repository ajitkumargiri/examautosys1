package com.nscs.examautosys.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.nscs.examautosys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ExamApplicationFormTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExamApplicationForm.class);
        ExamApplicationForm examApplicationForm1 = new ExamApplicationForm();
        examApplicationForm1.setId(1L);
        ExamApplicationForm examApplicationForm2 = new ExamApplicationForm();
        examApplicationForm2.setId(examApplicationForm1.getId());
        assertThat(examApplicationForm1).isEqualTo(examApplicationForm2);
        examApplicationForm2.setId(2L);
        assertThat(examApplicationForm1).isNotEqualTo(examApplicationForm2);
        examApplicationForm1.setId(null);
        assertThat(examApplicationForm1).isNotEqualTo(examApplicationForm2);
    }
}
