package com.nscs.examautosys.repository;

import com.nscs.examautosys.domain.ExamApplicationForm;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ExamApplicationForm entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExamApplicationFormRepository extends JpaRepository<ExamApplicationForm, Long> {}
