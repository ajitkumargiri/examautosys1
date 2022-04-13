package com.nscs.examautosys.web.rest;

import com.nscs.examautosys.repository.AcademicBatchRepository;
import com.nscs.examautosys.service.AcademicBatchService;
import com.nscs.examautosys.service.dto.AcademicBatchDTO;
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
 * REST controller for managing {@link com.nscs.examautosys.domain.AcademicBatch}.
 */
@RestController
@RequestMapping("/api")
public class AcademicBatchResource {

    private final Logger log = LoggerFactory.getLogger(AcademicBatchResource.class);

    private static final String ENTITY_NAME = "academicBatch";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AcademicBatchService academicBatchService;

    private final AcademicBatchRepository academicBatchRepository;

    public AcademicBatchResource(AcademicBatchService academicBatchService, AcademicBatchRepository academicBatchRepository) {
        this.academicBatchService = academicBatchService;
        this.academicBatchRepository = academicBatchRepository;
    }

    /**
     * {@code POST  /academic-batches} : Create a new academicBatch.
     *
     * @param academicBatchDTO the academicBatchDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new academicBatchDTO, or with status {@code 400 (Bad Request)} if the academicBatch has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/academic-batches")
    public ResponseEntity<AcademicBatchDTO> createAcademicBatch(@Valid @RequestBody AcademicBatchDTO academicBatchDTO)
        throws URISyntaxException {
        log.debug("REST request to save AcademicBatch : {}", academicBatchDTO);
        if (academicBatchDTO.getId() != null) {
            throw new BadRequestAlertException("A new academicBatch cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AcademicBatchDTO result = academicBatchService.save(academicBatchDTO);
        return ResponseEntity
            .created(new URI("/api/academic-batches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /academic-batches/:id} : Updates an existing academicBatch.
     *
     * @param id the id of the academicBatchDTO to save.
     * @param academicBatchDTO the academicBatchDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated academicBatchDTO,
     * or with status {@code 400 (Bad Request)} if the academicBatchDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the academicBatchDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/academic-batches/{id}")
    public ResponseEntity<AcademicBatchDTO> updateAcademicBatch(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AcademicBatchDTO academicBatchDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AcademicBatch : {}, {}", id, academicBatchDTO);
        if (academicBatchDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, academicBatchDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!academicBatchRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AcademicBatchDTO result = academicBatchService.update(academicBatchDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, academicBatchDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /academic-batches/:id} : Partial updates given fields of an existing academicBatch, field will ignore if it is null
     *
     * @param id the id of the academicBatchDTO to save.
     * @param academicBatchDTO the academicBatchDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated academicBatchDTO,
     * or with status {@code 400 (Bad Request)} if the academicBatchDTO is not valid,
     * or with status {@code 404 (Not Found)} if the academicBatchDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the academicBatchDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/academic-batches/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AcademicBatchDTO> partialUpdateAcademicBatch(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AcademicBatchDTO academicBatchDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AcademicBatch partially : {}, {}", id, academicBatchDTO);
        if (academicBatchDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, academicBatchDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!academicBatchRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AcademicBatchDTO> result = academicBatchService.partialUpdate(academicBatchDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, academicBatchDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /academic-batches} : get all the academicBatches.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of academicBatches in body.
     */
    @GetMapping("/academic-batches")
    public ResponseEntity<List<AcademicBatchDTO>> getAllAcademicBatches(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of AcademicBatches");
        Page<AcademicBatchDTO> page = academicBatchService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /academic-batches/:id} : get the "id" academicBatch.
     *
     * @param id the id of the academicBatchDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the academicBatchDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/academic-batches/{id}")
    public ResponseEntity<AcademicBatchDTO> getAcademicBatch(@PathVariable Long id) {
        log.debug("REST request to get AcademicBatch : {}", id);
        Optional<AcademicBatchDTO> academicBatchDTO = academicBatchService.findOne(id);
        return ResponseUtil.wrapOrNotFound(academicBatchDTO);
    }

    /**
     * {@code DELETE  /academic-batches/:id} : delete the "id" academicBatch.
     *
     * @param id the id of the academicBatchDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/academic-batches/{id}")
    public ResponseEntity<Void> deleteAcademicBatch(@PathVariable Long id) {
        log.debug("REST request to delete AcademicBatch : {}", id);
        academicBatchService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
