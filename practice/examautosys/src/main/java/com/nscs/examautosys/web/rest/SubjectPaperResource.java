package com.nscs.examautosys.web.rest;

import com.nscs.examautosys.repository.SubjectPaperRepository;
import com.nscs.examautosys.service.SubjectPaperService;
import com.nscs.examautosys.service.dto.SubjectPaperDTO;
import com.nscs.examautosys.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.nscs.examautosys.domain.SubjectPaper}.
 */
@RestController
@RequestMapping("/api")
public class SubjectPaperResource {

    private final Logger log = LoggerFactory.getLogger(SubjectPaperResource.class);

    private static final String ENTITY_NAME = "subjectPaper";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubjectPaperService subjectPaperService;

    private final SubjectPaperRepository subjectPaperRepository;

    public SubjectPaperResource(SubjectPaperService subjectPaperService, SubjectPaperRepository subjectPaperRepository) {
        this.subjectPaperService = subjectPaperService;
        this.subjectPaperRepository = subjectPaperRepository;
    }

    /**
     * {@code POST  /subject-papers} : Create a new subjectPaper.
     *
     * @param subjectPaperDTO the subjectPaperDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subjectPaperDTO, or with status {@code 400 (Bad Request)} if the subjectPaper has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/subject-papers")
    public ResponseEntity<SubjectPaperDTO> createSubjectPaper(@Valid @RequestBody SubjectPaperDTO subjectPaperDTO)
        throws URISyntaxException {
        log.debug("REST request to save SubjectPaper : {}", subjectPaperDTO);
        if (subjectPaperDTO.getId() != null) {
            throw new BadRequestAlertException("A new subjectPaper cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SubjectPaperDTO result = subjectPaperService.save(subjectPaperDTO);
        return ResponseEntity
            .created(new URI("/api/subject-papers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /subject-papers/:id} : Updates an existing subjectPaper.
     *
     * @param id the id of the subjectPaperDTO to save.
     * @param subjectPaperDTO the subjectPaperDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subjectPaperDTO,
     * or with status {@code 400 (Bad Request)} if the subjectPaperDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subjectPaperDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/subject-papers/{id}")
    public ResponseEntity<SubjectPaperDTO> updateSubjectPaper(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SubjectPaperDTO subjectPaperDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SubjectPaper : {}, {}", id, subjectPaperDTO);
        if (subjectPaperDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subjectPaperDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subjectPaperRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SubjectPaperDTO result = subjectPaperService.update(subjectPaperDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, subjectPaperDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /subject-papers/:id} : Partial updates given fields of an existing subjectPaper, field will ignore if it is null
     *
     * @param id the id of the subjectPaperDTO to save.
     * @param subjectPaperDTO the subjectPaperDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subjectPaperDTO,
     * or with status {@code 400 (Bad Request)} if the subjectPaperDTO is not valid,
     * or with status {@code 404 (Not Found)} if the subjectPaperDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the subjectPaperDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/subject-papers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SubjectPaperDTO> partialUpdateSubjectPaper(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SubjectPaperDTO subjectPaperDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SubjectPaper partially : {}, {}", id, subjectPaperDTO);
        if (subjectPaperDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subjectPaperDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subjectPaperRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SubjectPaperDTO> result = subjectPaperService.partialUpdate(subjectPaperDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, subjectPaperDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /subject-papers} : get all the subjectPapers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subjectPapers in body.
     */
    @GetMapping("/subject-papers")
    public ResponseEntity<List<SubjectPaperDTO>> getAllSubjectPapers(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of SubjectPapers");
        Page<SubjectPaperDTO> page = subjectPaperService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /subject-papers/:id} : get the "id" subjectPaper.
     *
     * @param id the id of the subjectPaperDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subjectPaperDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/subject-papers/{id}")
    public ResponseEntity<SubjectPaperDTO> getSubjectPaper(@PathVariable Long id) {
        log.debug("REST request to get SubjectPaper : {}", id);
        Optional<SubjectPaperDTO> subjectPaperDTO = subjectPaperService.findOne(id);
        return ResponseUtil.wrapOrNotFound(subjectPaperDTO);
    }

    /**
     * {@code DELETE  /subject-papers/:id} : delete the "id" subjectPaper.
     *
     * @param id the id of the subjectPaperDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/subject-papers/{id}")
    public ResponseEntity<Void> deleteSubjectPaper(@PathVariable Long id) {
        log.debug("REST request to delete SubjectPaper : {}", id);
        subjectPaperService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
