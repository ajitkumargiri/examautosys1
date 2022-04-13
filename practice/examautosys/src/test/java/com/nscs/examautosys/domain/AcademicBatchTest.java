package com.nscs.examautosys.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.nscs.examautosys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AcademicBatchTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AcademicBatch.class);
        AcademicBatch academicBatch1 = new AcademicBatch();
        academicBatch1.setId(1L);
        AcademicBatch academicBatch2 = new AcademicBatch();
        academicBatch2.setId(academicBatch1.getId());
        assertThat(academicBatch1).isEqualTo(academicBatch2);
        academicBatch2.setId(2L);
        assertThat(academicBatch1).isNotEqualTo(academicBatch2);
        academicBatch1.setId(null);
        assertThat(academicBatch1).isNotEqualTo(academicBatch2);
    }
}
