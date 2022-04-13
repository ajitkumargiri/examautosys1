package com.nscs.examautosys.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.nscs.examautosys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ExamCenterDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExamCenterDTO.class);
        ExamCenterDTO examCenterDTO1 = new ExamCenterDTO();
        examCenterDTO1.setId(1L);
        ExamCenterDTO examCenterDTO2 = new ExamCenterDTO();
        assertThat(examCenterDTO1).isNotEqualTo(examCenterDTO2);
        examCenterDTO2.setId(examCenterDTO1.getId());
        assertThat(examCenterDTO1).isEqualTo(examCenterDTO2);
        examCenterDTO2.setId(2L);
        assertThat(examCenterDTO1).isNotEqualTo(examCenterDTO2);
        examCenterDTO1.setId(null);
        assertThat(examCenterDTO1).isNotEqualTo(examCenterDTO2);
    }
}
