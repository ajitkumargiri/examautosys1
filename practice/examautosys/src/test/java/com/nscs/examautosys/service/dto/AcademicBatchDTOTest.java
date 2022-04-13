package com.nscs.examautosys.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.nscs.examautosys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AcademicBatchDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AcademicBatchDTO.class);
        AcademicBatchDTO academicBatchDTO1 = new AcademicBatchDTO();
        academicBatchDTO1.setId(1L);
        AcademicBatchDTO academicBatchDTO2 = new AcademicBatchDTO();
        assertThat(academicBatchDTO1).isNotEqualTo(academicBatchDTO2);
        academicBatchDTO2.setId(academicBatchDTO1.getId());
        assertThat(academicBatchDTO1).isEqualTo(academicBatchDTO2);
        academicBatchDTO2.setId(2L);
        assertThat(academicBatchDTO1).isNotEqualTo(academicBatchDTO2);
        academicBatchDTO1.setId(null);
        assertThat(academicBatchDTO1).isNotEqualTo(academicBatchDTO2);
    }
}
