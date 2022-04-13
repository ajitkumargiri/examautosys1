package com.nscs.examautosys.repository;

import com.nscs.examautosys.domain.AcademicBatch;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AcademicBatch entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AcademicBatchRepository extends JpaRepository<AcademicBatch, Long> {}
