package com.nscs.examautosys.repository;

import com.nscs.examautosys.domain.ExamCenter;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ExamCenter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExamCenterRepository extends JpaRepository<ExamCenter, Long> {}
