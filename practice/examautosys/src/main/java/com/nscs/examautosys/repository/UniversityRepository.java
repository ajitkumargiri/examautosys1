package com.nscs.examautosys.repository;

import com.nscs.examautosys.domain.University;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the University entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UniversityRepository extends JpaRepository<University, Long> {}
