package com.nscs.examautosys.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.nscs.examautosys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SubjectPaperDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubjectPaperDTO.class);
        SubjectPaperDTO subjectPaperDTO1 = new SubjectPaperDTO();
        subjectPaperDTO1.setId(1L);
        SubjectPaperDTO subjectPaperDTO2 = new SubjectPaperDTO();
        assertThat(subjectPaperDTO1).isNotEqualTo(subjectPaperDTO2);
        subjectPaperDTO2.setId(subjectPaperDTO1.getId());
        assertThat(subjectPaperDTO1).isEqualTo(subjectPaperDTO2);
        subjectPaperDTO2.setId(2L);
        assertThat(subjectPaperDTO1).isNotEqualTo(subjectPaperDTO2);
        subjectPaperDTO1.setId(null);
        assertThat(subjectPaperDTO1).isNotEqualTo(subjectPaperDTO2);
    }
}
