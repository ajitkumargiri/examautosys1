package com.nscs.examautosys.service;

import com.nscs.examautosys.service.dto.ExamCenterDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.nscs.examautosys.domain.ExamCenter}.
 */
public interface ExamCenterService {
    /**
     * Save a examCenter.
     *
     * @param examCenterDTO the entity to save.
     * @return the persisted entity.
     */
    ExamCenterDTO save(ExamCenterDTO examCenterDTO);

    /**
     * Updates a examCenter.
     *
     * @param examCenterDTO the entity to update.
     * @return the persisted entity.
     */
    ExamCenterDTO update(ExamCenterDTO examCenterDTO);

    /**
     * Partially updates a examCenter.
     *
     * @param examCenterDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ExamCenterDTO> partialUpdate(ExamCenterDTO examCenterDTO);

    /**
     * Get all the examCenters.
     *
     * @return the list of entities.
     */
    List<ExamCenterDTO> findAll();

    /**
     * Get the "id" examCenter.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ExamCenterDTO> findOne(Long id);

    /**
     * Delete the "id" examCenter.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
