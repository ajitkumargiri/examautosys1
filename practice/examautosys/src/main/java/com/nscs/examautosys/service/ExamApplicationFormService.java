package com.nscs.examautosys.service;

import com.nscs.examautosys.service.dto.ExamApplicationFormDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.nscs.examautosys.domain.ExamApplicationForm}.
 */
public interface ExamApplicationFormService {
    /**
     * Save a examApplicationForm.
     *
     * @param examApplicationFormDTO the entity to save.
     * @return the persisted entity.
     */
    ExamApplicationFormDTO save(ExamApplicationFormDTO examApplicationFormDTO);

    /**
     * Updates a examApplicationForm.
     *
     * @param examApplicationFormDTO the entity to update.
     * @return the persisted entity.
     */
    ExamApplicationFormDTO update(ExamApplicationFormDTO examApplicationFormDTO);

    /**
     * Partially updates a examApplicationForm.
     *
     * @param examApplicationFormDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ExamApplicationFormDTO> partialUpdate(ExamApplicationFormDTO examApplicationFormDTO);

    /**
     * Get all the examApplicationForms.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ExamApplicationFormDTO> findAll(Pageable pageable);

    /**
     * Get the "id" examApplicationForm.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ExamApplicationFormDTO> findOne(Long id);

    /**
     * Delete the "id" examApplicationForm.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
