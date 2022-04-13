package com.nscs.examautosys.service;

import com.nscs.examautosys.service.dto.SubjectPaperDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.nscs.examautosys.domain.SubjectPaper}.
 */
public interface SubjectPaperService {
    /**
     * Save a subjectPaper.
     *
     * @param subjectPaperDTO the entity to save.
     * @return the persisted entity.
     */
    SubjectPaperDTO save(SubjectPaperDTO subjectPaperDTO);

    /**
     * Updates a subjectPaper.
     *
     * @param subjectPaperDTO the entity to update.
     * @return the persisted entity.
     */
    SubjectPaperDTO update(SubjectPaperDTO subjectPaperDTO);

    /**
     * Partially updates a subjectPaper.
     *
     * @param subjectPaperDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SubjectPaperDTO> partialUpdate(SubjectPaperDTO subjectPaperDTO);

    /**
     * Get all the subjectPapers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SubjectPaperDTO> findAll(Pageable pageable);

    /**
     * Get the "id" subjectPaper.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SubjectPaperDTO> findOne(Long id);

    /**
     * Delete the "id" subjectPaper.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
