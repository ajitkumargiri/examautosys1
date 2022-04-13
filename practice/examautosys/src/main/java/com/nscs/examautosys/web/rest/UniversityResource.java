package com.nscs.examautosys.web.rest;

import com.nscs.examautosys.repository.UniversityRepository;
import com.nscs.examautosys.service.UniversityService;
import com.nscs.examautosys.service.dto.UniversityDTO;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.nscs.examautosys.domain.University}.
 */
@RestController
@RequestMapping("/api")
public class UniversityResource {

    private final Logger log = LoggerFactory.getLogger(UniversityResource.class);

    private static final String ENTITY_NAME = "university";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UniversityService universityService;

    private final UniversityRepository universityRepository;

    public UniversityResource(UniversityService universityService, UniversityRepository universityRepository) {
        this.universityService = universityService;
        this.universityRepository = universityRepository;
    }

    /**
     * {@code POST  /universities} : Create a new university.
     *
     * @param universityDTO the universityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new universityDTO, or with status {@code 400 (Bad Request)} if the university has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/universities")
    public ResponseEntity<UniversityDTO> createUniversity(@Valid @RequestBody UniversityDTO universityDTO) throws URISyntaxException {
        log.debug("REST request to save University : {}", universityDTO);
        if (universityDTO.getId() != null) {
            throw new BadRequestAlertException("A new university cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UniversityDTO result = universityService.save(universityDTO);
        return ResponseEntity
            .created(new URI("/api/universities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /universities/:id} : Updates an existing university.
     *
     * @param id the id of the universityDTO to save.
     * @param universityDTO the universityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated universityDTO,
     * or with status {@code 400 (Bad Request)} if the universityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the universityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/universities/{id}")
    public ResponseEntity<UniversityDTO> updateUniversity(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody UniversityDTO universityDTO
    ) throws URISyntaxException {
        log.debug("REST request to update University : {}, {}", id, universityDTO);
        if (universityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, universityDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!universityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UniversityDTO result = universityService.update(universityDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, universityDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /universities/:id} : Partial updates given fields of an existing university, field will ignore if it is null
     *
     * @param id the id of the universityDTO to save.
     * @param universityDTO the universityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated universityDTO,
     * or with status {@code 400 (Bad Request)} if the universityDTO is not valid,
     * or with status {@code 404 (Not Found)} if the universityDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the universityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/universities/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UniversityDTO> partialUpdateUniversity(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody UniversityDTO universityDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update University partially : {}, {}", id, universityDTO);
        if (universityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, universityDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!universityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UniversityDTO> result = universityService.partialUpdate(universityDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, universityDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /universities} : get all the universities.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of universities in body.
     */
    @GetMapping("/universities")
    public List<UniversityDTO> getAllUniversities() {
        log.debug("REST request to get all Universities");
        return universityService.findAll();
    }

    /**
     * {@code GET  /universities/:id} : get the "id" university.
     *
     * @param id the id of the universityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the universityDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/universities/{id}")
    public ResponseEntity<UniversityDTO> getUniversity(@PathVariable Long id) {
        log.debug("REST request to get University : {}", id);
        Optional<UniversityDTO> universityDTO = universityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(universityDTO);
    }

    /**
     * {@code DELETE  /universities/:id} : delete the "id" university.
     *
     * @param id the id of the universityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/universities/{id}")
    public ResponseEntity<Void> deleteUniversity(@PathVariable Long id) {
        log.debug("REST request to delete University : {}", id);
        universityService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
