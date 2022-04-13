package com.nscs.examautosys.web.rest;

import com.nscs.examautosys.repository.CollegeRepository;
import com.nscs.examautosys.service.CollegeService;
import com.nscs.examautosys.service.dto.CollegeDTO;
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
 * REST controller for managing {@link com.nscs.examautosys.domain.College}.
 */
@RestController
@RequestMapping("/api")
public class CollegeResource {

    private final Logger log = LoggerFactory.getLogger(CollegeResource.class);

    private static final String ENTITY_NAME = "college";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CollegeService collegeService;

    private final CollegeRepository collegeRepository;

    public CollegeResource(CollegeService collegeService, CollegeRepository collegeRepository) {
        this.collegeService = collegeService;
        this.collegeRepository = collegeRepository;
    }

    /**
     * {@code POST  /colleges} : Create a new college.
     *
     * @param collegeDTO the collegeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new collegeDTO, or with status {@code 400 (Bad Request)} if the college has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/colleges")
    public ResponseEntity<CollegeDTO> createCollege(@Valid @RequestBody CollegeDTO collegeDTO) throws URISyntaxException {
        log.debug("REST request to save College : {}", collegeDTO);
        if (collegeDTO.getId() != null) {
            throw new BadRequestAlertException("A new college cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CollegeDTO result = collegeService.save(collegeDTO);
        return ResponseEntity
            .created(new URI("/api/colleges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /colleges/:id} : Updates an existing college.
     *
     * @param id the id of the collegeDTO to save.
     * @param collegeDTO the collegeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated collegeDTO,
     * or with status {@code 400 (Bad Request)} if the collegeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the collegeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/colleges/{id}")
    public ResponseEntity<CollegeDTO> updateCollege(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CollegeDTO collegeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update College : {}, {}", id, collegeDTO);
        if (collegeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, collegeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!collegeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CollegeDTO result = collegeService.update(collegeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, collegeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /colleges/:id} : Partial updates given fields of an existing college, field will ignore if it is null
     *
     * @param id the id of the collegeDTO to save.
     * @param collegeDTO the collegeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated collegeDTO,
     * or with status {@code 400 (Bad Request)} if the collegeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the collegeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the collegeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/colleges/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CollegeDTO> partialUpdateCollege(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CollegeDTO collegeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update College partially : {}, {}", id, collegeDTO);
        if (collegeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, collegeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!collegeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CollegeDTO> result = collegeService.partialUpdate(collegeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, collegeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /colleges} : get all the colleges.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of colleges in body.
     */
    @GetMapping("/colleges")
    public ResponseEntity<List<CollegeDTO>> getAllColleges(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Colleges");
        Page<CollegeDTO> page = collegeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /colleges/:id} : get the "id" college.
     *
     * @param id the id of the collegeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the collegeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/colleges/{id}")
    public ResponseEntity<CollegeDTO> getCollege(@PathVariable Long id) {
        log.debug("REST request to get College : {}", id);
        Optional<CollegeDTO> collegeDTO = collegeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(collegeDTO);
    }

    /**
     * {@code DELETE  /colleges/:id} : delete the "id" college.
     *
     * @param id the id of the collegeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/colleges/{id}")
    public ResponseEntity<Void> deleteCollege(@PathVariable Long id) {
        log.debug("REST request to delete College : {}", id);
        collegeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
