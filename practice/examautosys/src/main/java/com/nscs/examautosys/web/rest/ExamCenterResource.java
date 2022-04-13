package com.nscs.examautosys.web.rest;

import com.nscs.examautosys.repository.ExamCenterRepository;
import com.nscs.examautosys.service.ExamCenterService;
import com.nscs.examautosys.service.dto.ExamCenterDTO;
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
 * REST controller for managing {@link com.nscs.examautosys.domain.ExamCenter}.
 */
@RestController
@RequestMapping("/api")
public class ExamCenterResource {

    private final Logger log = LoggerFactory.getLogger(ExamCenterResource.class);

    private static final String ENTITY_NAME = "examCenter";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExamCenterService examCenterService;

    private final ExamCenterRepository examCenterRepository;

    public ExamCenterResource(ExamCenterService examCenterService, ExamCenterRepository examCenterRepository) {
        this.examCenterService = examCenterService;
        this.examCenterRepository = examCenterRepository;
    }

    /**
     * {@code POST  /exam-centers} : Create a new examCenter.
     *
     * @param examCenterDTO the examCenterDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new examCenterDTO, or with status {@code 400 (Bad Request)} if the examCenter has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/exam-centers")
    public ResponseEntity<ExamCenterDTO> createExamCenter(@Valid @RequestBody ExamCenterDTO examCenterDTO) throws URISyntaxException {
        log.debug("REST request to save ExamCenter : {}", examCenterDTO);
        if (examCenterDTO.getId() != null) {
            throw new BadRequestAlertException("A new examCenter cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExamCenterDTO result = examCenterService.save(examCenterDTO);
        return ResponseEntity
            .created(new URI("/api/exam-centers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /exam-centers/:id} : Updates an existing examCenter.
     *
     * @param id the id of the examCenterDTO to save.
     * @param examCenterDTO the examCenterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated examCenterDTO,
     * or with status {@code 400 (Bad Request)} if the examCenterDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the examCenterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/exam-centers/{id}")
    public ResponseEntity<ExamCenterDTO> updateExamCenter(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ExamCenterDTO examCenterDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ExamCenter : {}, {}", id, examCenterDTO);
        if (examCenterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, examCenterDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!examCenterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ExamCenterDTO result = examCenterService.update(examCenterDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, examCenterDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /exam-centers/:id} : Partial updates given fields of an existing examCenter, field will ignore if it is null
     *
     * @param id the id of the examCenterDTO to save.
     * @param examCenterDTO the examCenterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated examCenterDTO,
     * or with status {@code 400 (Bad Request)} if the examCenterDTO is not valid,
     * or with status {@code 404 (Not Found)} if the examCenterDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the examCenterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/exam-centers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ExamCenterDTO> partialUpdateExamCenter(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ExamCenterDTO examCenterDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ExamCenter partially : {}, {}", id, examCenterDTO);
        if (examCenterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, examCenterDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!examCenterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ExamCenterDTO> result = examCenterService.partialUpdate(examCenterDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, examCenterDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /exam-centers} : get all the examCenters.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of examCenters in body.
     */
    @GetMapping("/exam-centers")
    public List<ExamCenterDTO> getAllExamCenters() {
        log.debug("REST request to get all ExamCenters");
        return examCenterService.findAll();
    }

    /**
     * {@code GET  /exam-centers/:id} : get the "id" examCenter.
     *
     * @param id the id of the examCenterDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the examCenterDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/exam-centers/{id}")
    public ResponseEntity<ExamCenterDTO> getExamCenter(@PathVariable Long id) {
        log.debug("REST request to get ExamCenter : {}", id);
        Optional<ExamCenterDTO> examCenterDTO = examCenterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(examCenterDTO);
    }

    /**
     * {@code DELETE  /exam-centers/:id} : delete the "id" examCenter.
     *
     * @param id the id of the examCenterDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/exam-centers/{id}")
    public ResponseEntity<Void> deleteExamCenter(@PathVariable Long id) {
        log.debug("REST request to delete ExamCenter : {}", id);
        examCenterService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
