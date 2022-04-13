package com.nscs.examautosys.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.nscs.examautosys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SubjectPaperTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubjectPaper.class);
        SubjectPaper subjectPaper1 = new SubjectPaper();
        subjectPaper1.setId(1L);
        SubjectPaper subjectPaper2 = new SubjectPaper();
        subjectPaper2.setId(subjectPaper1.getId());
        assertThat(subjectPaper1).isEqualTo(subjectPaper2);
        subjectPaper2.setId(2L);
        assertThat(subjectPaper1).isNotEqualTo(subjectPaper2);
        subjectPaper1.setId(null);
        assertThat(subjectPaper1).isNotEqualTo(subjectPaper2);
    }
}
