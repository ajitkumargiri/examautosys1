package com.nscs.examautosys.repository;

import com.nscs.examautosys.domain.SubjectPaper;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SubjectPaper entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubjectPaperRepository extends JpaRepository<SubjectPaper, Long> {}
