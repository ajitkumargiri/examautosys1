package com.nscs.examautosys.service;

import com.nscs.examautosys.service.dto.AcademicBatchDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.nscs.examautosys.domain.AcademicBatch}.
 */
public interface AcademicBatchService {
    /**
     * Save a academicBatch.
     *
     * @param academicBatchDTO the entity to save.
     * @return the persisted entity.
     */
    AcademicBatchDTO save(AcademicBatchDTO academicBatchDTO);

    /**
     * Updates a academicBatch.
     *
     * @param academicBatchDTO the entity to update.
     * @return the persisted entity.
     */
    AcademicBatchDTO update(AcademicBatchDTO academicBatchDTO);

    /**
     * Partially updates a academicBatch.
     *
     * @param academicBatchDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AcademicBatchDTO> partialUpdate(AcademicBatchDTO academicBatchDTO);

    /**
     * Get all the academicBatches.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AcademicBatchDTO> findAll(Pageable pageable);

    /**
     * Get the "id" academicBatch.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AcademicBatchDTO> findOne(Long id);

    /**
     * Delete the "id" academicBatch.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
