package com.nscs.examautosys.service;

import com.nscs.examautosys.service.dto.CollegeDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.nscs.examautosys.domain.College}.
 */
public interface CollegeService {
    /**
     * Save a college.
     *
     * @param collegeDTO the entity to save.
     * @return the persisted entity.
     */
    CollegeDTO save(CollegeDTO collegeDTO);

    /**
     * Updates a college.
     *
     * @param collegeDTO the entity to update.
     * @return the persisted entity.
     */
    CollegeDTO update(CollegeDTO collegeDTO);

    /**
     * Partially updates a college.
     *
     * @param collegeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CollegeDTO> partialUpdate(CollegeDTO collegeDTO);

    /**
     * Get all the colleges.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CollegeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" college.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CollegeDTO> findOne(Long id);

    /**
     * Delete the "id" college.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
