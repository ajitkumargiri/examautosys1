package com.nscs.examautosys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.nscs.examautosys.IntegrationTest;
import com.nscs.examautosys.domain.AcademicBatch;
import com.nscs.examautosys.repository.AcademicBatchRepository;
import com.nscs.examautosys.service.dto.AcademicBatchDTO;
import com.nscs.examautosys.service.mapper.AcademicBatchMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AcademicBatchResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AcademicBatchResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_ACADEMIC_START_YEAR = 1;
    private static final Integer UPDATED_ACADEMIC_START_YEAR = 2;

    private static final Integer DEFAULT_ACADEMIC_END_YEAR = 1;
    private static final Integer UPDATED_ACADEMIC_END_YEAR = 2;

    private static final String ENTITY_API_URL = "/api/academic-batches";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AcademicBatchRepository academicBatchRepository;

    @Autowired
    private AcademicBatchMapper academicBatchMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAcademicBatchMockMvc;

    private AcademicBatch academicBatch;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AcademicBatch createEntity(EntityManager em) {
        AcademicBatch academicBatch = new AcademicBatch()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .academicStartYear(DEFAULT_ACADEMIC_START_YEAR)
            .academicEndYear(DEFAULT_ACADEMIC_END_YEAR);
        return academicBatch;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AcademicBatch createUpdatedEntity(EntityManager em) {
        AcademicBatch academicBatch = new AcademicBatch()
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .academicStartYear(UPDATED_ACADEMIC_START_YEAR)
            .academicEndYear(UPDATED_ACADEMIC_END_YEAR);
        return academicBatch;
    }

    @BeforeEach
    public void initTest() {
        academicBatch = createEntity(em);
    }

    @Test
    @Transactional
    void createAcademicBatch() throws Exception {
        int databaseSizeBeforeCreate = academicBatchRepository.findAll().size();
        // Create the AcademicBatch
        AcademicBatchDTO academicBatchDTO = academicBatchMapper.toDto(academicBatch);
        restAcademicBatchMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(academicBatchDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AcademicBatch in the database
        List<AcademicBatch> academicBatchList = academicBatchRepository.findAll();
        assertThat(academicBatchList).hasSize(databaseSizeBeforeCreate + 1);
        AcademicBatch testAcademicBatch = academicBatchList.get(academicBatchList.size() - 1);
        assertThat(testAcademicBatch.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAcademicBatch.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAcademicBatch.getAcademicStartYear()).isEqualTo(DEFAULT_ACADEMIC_START_YEAR);
        assertThat(testAcademicBatch.getAcademicEndYear()).isEqualTo(DEFAULT_ACADEMIC_END_YEAR);
    }

    @Test
    @Transactional
    void createAcademicBatchWithExistingId() throws Exception {
        // Create the AcademicBatch with an existing ID
        academicBatch.setId(1L);
        AcademicBatchDTO academicBatchDTO = academicBatchMapper.toDto(academicBatch);

        int databaseSizeBeforeCreate = academicBatchRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAcademicBatchMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(academicBatchDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AcademicBatch in the database
        List<AcademicBatch> academicBatchList = academicBatchRepository.findAll();
        assertThat(academicBatchList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = academicBatchRepository.findAll().size();
        // set the field null
        academicBatch.setName(null);

        // Create the AcademicBatch, which fails.
        AcademicBatchDTO academicBatchDTO = academicBatchMapper.toDto(academicBatch);

        restAcademicBatchMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(academicBatchDTO))
            )
            .andExpect(status().isBadRequest());

        List<AcademicBatch> academicBatchList = academicBatchRepository.findAll();
        assertThat(academicBatchList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAcademicStartYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = academicBatchRepository.findAll().size();
        // set the field null
        academicBatch.setAcademicStartYear(null);

        // Create the AcademicBatch, which fails.
        AcademicBatchDTO academicBatchDTO = academicBatchMapper.toDto(academicBatch);

        restAcademicBatchMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(academicBatchDTO))
            )
            .andExpect(status().isBadRequest());

        List<AcademicBatch> academicBatchList = academicBatchRepository.findAll();
        assertThat(academicBatchList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAcademicEndYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = academicBatchRepository.findAll().size();
        // set the field null
        academicBatch.setAcademicEndYear(null);

        // Create the AcademicBatch, which fails.
        AcademicBatchDTO academicBatchDTO = academicBatchMapper.toDto(academicBatch);

        restAcademicBatchMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(academicBatchDTO))
            )
            .andExpect(status().isBadRequest());

        List<AcademicBatch> academicBatchList = academicBatchRepository.findAll();
        assertThat(academicBatchList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAcademicBatches() throws Exception {
        // Initialize the database
        academicBatchRepository.saveAndFlush(academicBatch);

        // Get all the academicBatchList
        restAcademicBatchMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(academicBatch.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].academicStartYear").value(hasItem(DEFAULT_ACADEMIC_START_YEAR)))
            .andExpect(jsonPath("$.[*].academicEndYear").value(hasItem(DEFAULT_ACADEMIC_END_YEAR)));
    }

    @Test
    @Transactional
    void getAcademicBatch() throws Exception {
        // Initialize the database
        academicBatchRepository.saveAndFlush(academicBatch);

        // Get the academicBatch
        restAcademicBatchMockMvc
            .perform(get(ENTITY_API_URL_ID, academicBatch.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(academicBatch.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.academicStartYear").value(DEFAULT_ACADEMIC_START_YEAR))
            .andExpect(jsonPath("$.academicEndYear").value(DEFAULT_ACADEMIC_END_YEAR));
    }

    @Test
    @Transactional
    void getNonExistingAcademicBatch() throws Exception {
        // Get the academicBatch
        restAcademicBatchMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAcademicBatch() throws Exception {
        // Initialize the database
        academicBatchRepository.saveAndFlush(academicBatch);

        int databaseSizeBeforeUpdate = academicBatchRepository.findAll().size();

        // Update the academicBatch
        AcademicBatch updatedAcademicBatch = academicBatchRepository.findById(academicBatch.getId()).get();
        // Disconnect from session so that the updates on updatedAcademicBatch are not directly saved in db
        em.detach(updatedAcademicBatch);
        updatedAcademicBatch
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .academicStartYear(UPDATED_ACADEMIC_START_YEAR)
            .academicEndYear(UPDATED_ACADEMIC_END_YEAR);
        AcademicBatchDTO academicBatchDTO = academicBatchMapper.toDto(updatedAcademicBatch);

        restAcademicBatchMockMvc
            .perform(
                put(ENTITY_API_URL_ID, academicBatchDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(academicBatchDTO))
            )
            .andExpect(status().isOk());

        // Validate the AcademicBatch in the database
        List<AcademicBatch> academicBatchList = academicBatchRepository.findAll();
        assertThat(academicBatchList).hasSize(databaseSizeBeforeUpdate);
        AcademicBatch testAcademicBatch = academicBatchList.get(academicBatchList.size() - 1);
        assertThat(testAcademicBatch.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAcademicBatch.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAcademicBatch.getAcademicStartYear()).isEqualTo(UPDATED_ACADEMIC_START_YEAR);
        assertThat(testAcademicBatch.getAcademicEndYear()).isEqualTo(UPDATED_ACADEMIC_END_YEAR);
    }

    @Test
    @Transactional
    void putNonExistingAcademicBatch() throws Exception {
        int databaseSizeBeforeUpdate = academicBatchRepository.findAll().size();
        academicBatch.setId(count.incrementAndGet());

        // Create the AcademicBatch
        AcademicBatchDTO academicBatchDTO = academicBatchMapper.toDto(academicBatch);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAcademicBatchMockMvc
            .perform(
                put(ENTITY_API_URL_ID, academicBatchDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(academicBatchDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AcademicBatch in the database
        List<AcademicBatch> academicBatchList = academicBatchRepository.findAll();
        assertThat(academicBatchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAcademicBatch() throws Exception {
        int databaseSizeBeforeUpdate = academicBatchRepository.findAll().size();
        academicBatch.setId(count.incrementAndGet());

        // Create the AcademicBatch
        AcademicBatchDTO academicBatchDTO = academicBatchMapper.toDto(academicBatch);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAcademicBatchMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(academicBatchDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AcademicBatch in the database
        List<AcademicBatch> academicBatchList = academicBatchRepository.findAll();
        assertThat(academicBatchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAcademicBatch() throws Exception {
        int databaseSizeBeforeUpdate = academicBatchRepository.findAll().size();
        academicBatch.setId(count.incrementAndGet());

        // Create the AcademicBatch
        AcademicBatchDTO academicBatchDTO = academicBatchMapper.toDto(academicBatch);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAcademicBatchMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(academicBatchDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AcademicBatch in the database
        List<AcademicBatch> academicBatchList = academicBatchRepository.findAll();
        assertThat(academicBatchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAcademicBatchWithPatch() throws Exception {
        // Initialize the database
        academicBatchRepository.saveAndFlush(academicBatch);

        int databaseSizeBeforeUpdate = academicBatchRepository.findAll().size();

        // Update the academicBatch using partial update
        AcademicBatch partialUpdatedAcademicBatch = new AcademicBatch();
        partialUpdatedAcademicBatch.setId(academicBatch.getId());

        partialUpdatedAcademicBatch
            .name(UPDATED_NAME)
            .academicStartYear(UPDATED_ACADEMIC_START_YEAR)
            .academicEndYear(UPDATED_ACADEMIC_END_YEAR);

        restAcademicBatchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAcademicBatch.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAcademicBatch))
            )
            .andExpect(status().isOk());

        // Validate the AcademicBatch in the database
        List<AcademicBatch> academicBatchList = academicBatchRepository.findAll();
        assertThat(academicBatchList).hasSize(databaseSizeBeforeUpdate);
        AcademicBatch testAcademicBatch = academicBatchList.get(academicBatchList.size() - 1);
        assertThat(testAcademicBatch.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAcademicBatch.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAcademicBatch.getAcademicStartYear()).isEqualTo(UPDATED_ACADEMIC_START_YEAR);
        assertThat(testAcademicBatch.getAcademicEndYear()).isEqualTo(UPDATED_ACADEMIC_END_YEAR);
    }

    @Test
    @Transactional
    void fullUpdateAcademicBatchWithPatch() throws Exception {
        // Initialize the database
        academicBatchRepository.saveAndFlush(academicBatch);

        int databaseSizeBeforeUpdate = academicBatchRepository.findAll().size();

        // Update the academicBatch using partial update
        AcademicBatch partialUpdatedAcademicBatch = new AcademicBatch();
        partialUpdatedAcademicBatch.setId(academicBatch.getId());

        partialUpdatedAcademicBatch
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .academicStartYear(UPDATED_ACADEMIC_START_YEAR)
            .academicEndYear(UPDATED_ACADEMIC_END_YEAR);

        restAcademicBatchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAcademicBatch.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAcademicBatch))
            )
            .andExpect(status().isOk());

        // Validate the AcademicBatch in the database
        List<AcademicBatch> academicBatchList = academicBatchRepository.findAll();
        assertThat(academicBatchList).hasSize(databaseSizeBeforeUpdate);
        AcademicBatch testAcademicBatch = academicBatchList.get(academicBatchList.size() - 1);
        assertThat(testAcademicBatch.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAcademicBatch.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAcademicBatch.getAcademicStartYear()).isEqualTo(UPDATED_ACADEMIC_START_YEAR);
        assertThat(testAcademicBatch.getAcademicEndYear()).isEqualTo(UPDATED_ACADEMIC_END_YEAR);
    }

    @Test
    @Transactional
    void patchNonExistingAcademicBatch() throws Exception {
        int databaseSizeBeforeUpdate = academicBatchRepository.findAll().size();
        academicBatch.setId(count.incrementAndGet());

        // Create the AcademicBatch
        AcademicBatchDTO academicBatchDTO = academicBatchMapper.toDto(academicBatch);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAcademicBatchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, academicBatchDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(academicBatchDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AcademicBatch in the database
        List<AcademicBatch> academicBatchList = academicBatchRepository.findAll();
        assertThat(academicBatchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAcademicBatch() throws Exception {
        int databaseSizeBeforeUpdate = academicBatchRepository.findAll().size();
        academicBatch.setId(count.incrementAndGet());

        // Create the AcademicBatch
        AcademicBatchDTO academicBatchDTO = academicBatchMapper.toDto(academicBatch);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAcademicBatchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(academicBatchDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AcademicBatch in the database
        List<AcademicBatch> academicBatchList = academicBatchRepository.findAll();
        assertThat(academicBatchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAcademicBatch() throws Exception {
        int databaseSizeBeforeUpdate = academicBatchRepository.findAll().size();
        academicBatch.setId(count.incrementAndGet());

        // Create the AcademicBatch
        AcademicBatchDTO academicBatchDTO = academicBatchMapper.toDto(academicBatch);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAcademicBatchMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(academicBatchDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AcademicBatch in the database
        List<AcademicBatch> academicBatchList = academicBatchRepository.findAll();
        assertThat(academicBatchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAcademicBatch() throws Exception {
        // Initialize the database
        academicBatchRepository.saveAndFlush(academicBatch);

        int databaseSizeBeforeDelete = academicBatchRepository.findAll().size();

        // Delete the academicBatch
        restAcademicBatchMockMvc
            .perform(delete(ENTITY_API_URL_ID, academicBatch.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AcademicBatch> academicBatchList = academicBatchRepository.findAll();
        assertThat(academicBatchList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
