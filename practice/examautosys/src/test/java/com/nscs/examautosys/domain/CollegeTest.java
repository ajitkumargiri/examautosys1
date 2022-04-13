package com.nscs.examautosys.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.nscs.examautosys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CollegeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(College.class);
        College college1 = new College();
        college1.setId(1L);
        College college2 = new College();
        college2.setId(college1.getId());
        assertThat(college1).isEqualTo(college2);
        college2.setId(2L);
        assertThat(college1).isNotEqualTo(college2);
        college1.setId(null);
        assertThat(college1).isNotEqualTo(college2);
    }
}
