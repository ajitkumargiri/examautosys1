package com.nscs.examautosys.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.nscs.examautosys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ExamApplicationFormDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExamApplicationFormDTO.class);
        ExamApplicationFormDTO examApplicationFormDTO1 = new ExamApplicationFormDTO();
        examApplicationFormDTO1.setId(1L);
        ExamApplicationFormDTO examApplicationFormDTO2 = new ExamApplicationFormDTO();
        assertThat(examApplicationFormDTO1).isNotEqualTo(examApplicationFormDTO2);
        examApplicationFormDTO2.setId(examApplicationFormDTO1.getId());
        assertThat(examApplicationFormDTO1).isEqualTo(examApplicationFormDTO2);
        examApplicationFormDTO2.setId(2L);
        assertThat(examApplicationFormDTO1).isNotEqualTo(examApplicationFormDTO2);
        examApplicationFormDTO1.setId(null);
        assertThat(examApplicationFormDTO1).isNotEqualTo(examApplicationFormDTO2);
    }
}
