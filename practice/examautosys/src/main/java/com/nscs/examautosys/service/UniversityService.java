package com.nscs.examautosys.service;

import com.nscs.examautosys.service.dto.UniversityDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.nscs.examautosys.domain.University}.
 */
public interface UniversityService {
    /**
     * Save a university.
     *
     * @param universityDTO the entity to save.
     * @return the persisted entity.
     */
    UniversityDTO save(UniversityDTO universityDTO);

    /**
     * Updates a university.
     *
     * @param universityDTO the entity to update.
     * @return the persisted entity.
     */
    UniversityDTO update(UniversityDTO universityDTO);

    /**
     * Partially updates a university.
     *
     * @param universityDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<UniversityDTO> partialUpdate(UniversityDTO universityDTO);

    /**
     * Get all the universities.
     *
     * @return the list of entities.
     */
    List<UniversityDTO> findAll();

    /**
     * Get the "id" university.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UniversityDTO> findOne(Long id);

    /**
     * Delete the "id" university.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
