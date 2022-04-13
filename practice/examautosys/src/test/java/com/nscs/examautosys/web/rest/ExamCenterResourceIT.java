package com.nscs.examautosys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.nscs.examautosys.IntegrationTest;
import com.nscs.examautosys.domain.ExamCenter;
import com.nscs.examautosys.repository.ExamCenterRepository;
import com.nscs.examautosys.service.dto.ExamCenterDTO;
import com.nscs.examautosys.service.mapper.ExamCenterMapper;
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
 * Integration tests for the {@link ExamCenterResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ExamCenterResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/exam-centers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ExamCenterRepository examCenterRepository;

    @Autowired
    private ExamCenterMapper examCenterMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restExamCenterMockMvc;

    private ExamCenter examCenter;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExamCenter createEntity(EntityManager em) {
        ExamCenter examCenter = new ExamCenter().name(DEFAULT_NAME).code(DEFAULT_CODE);
        return examCenter;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExamCenter createUpdatedEntity(EntityManager em) {
        ExamCenter examCenter = new ExamCenter().name(UPDATED_NAME).code(UPDATED_CODE);
        return examCenter;
    }

    @BeforeEach
    public void initTest() {
        examCenter = createEntity(em);
    }

    @Test
    @Transactional
    void createExamCenter() throws Exception {
        int databaseSizeBeforeCreate = examCenterRepository.findAll().size();
        // Create the ExamCenter
        ExamCenterDTO examCenterDTO = examCenterMapper.toDto(examCenter);
        restExamCenterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(examCenterDTO)))
            .andExpect(status().isCreated());

        // Validate the ExamCenter in the database
        List<ExamCenter> examCenterList = examCenterRepository.findAll();
        assertThat(examCenterList).hasSize(databaseSizeBeforeCreate + 1);
        ExamCenter testExamCenter = examCenterList.get(examCenterList.size() - 1);
        assertThat(testExamCenter.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testExamCenter.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    void createExamCenterWithExistingId() throws Exception {
        // Create the ExamCenter with an existing ID
        examCenter.setId(1L);
        ExamCenterDTO examCenterDTO = examCenterMapper.toDto(examCenter);

        int databaseSizeBeforeCreate = examCenterRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restExamCenterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(examCenterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ExamCenter in the database
        List<ExamCenter> examCenterList = examCenterRepository.findAll();
        assertThat(examCenterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = examCenterRepository.findAll().size();
        // set the field null
        examCenter.setName(null);

        // Create the ExamCenter, which fails.
        ExamCenterDTO examCenterDTO = examCenterMapper.toDto(examCenter);

        restExamCenterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(examCenterDTO)))
            .andExpect(status().isBadRequest());

        List<ExamCenter> examCenterList = examCenterRepository.findAll();
        assertThat(examCenterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllExamCenters() throws Exception {
        // Initialize the database
        examCenterRepository.saveAndFlush(examCenter);

        // Get all the examCenterList
        restExamCenterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(examCenter.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
    }

    @Test
    @Transactional
    void getExamCenter() throws Exception {
        // Initialize the database
        examCenterRepository.saveAndFlush(examCenter);

        // Get the examCenter
        restExamCenterMockMvc
            .perform(get(ENTITY_API_URL_ID, examCenter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(examCenter.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE));
    }

    @Test
    @Transactional
    void getNonExistingExamCenter() throws Exception {
        // Get the examCenter
        restExamCenterMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewExamCenter() throws Exception {
        // Initialize the database
        examCenterRepository.saveAndFlush(examCenter);

        int databaseSizeBeforeUpdate = examCenterRepository.findAll().size();

        // Update the examCenter
        ExamCenter updatedExamCenter = examCenterRepository.findById(examCenter.getId()).get();
        // Disconnect from session so that the updates on updatedExamCenter are not directly saved in db
        em.detach(updatedExamCenter);
        updatedExamCenter.name(UPDATED_NAME).code(UPDATED_CODE);
        ExamCenterDTO examCenterDTO = examCenterMapper.toDto(updatedExamCenter);

        restExamCenterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, examCenterDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(examCenterDTO))
            )
            .andExpect(status().isOk());

        // Validate the ExamCenter in the database
        List<ExamCenter> examCenterList = examCenterRepository.findAll();
        assertThat(examCenterList).hasSize(databaseSizeBeforeUpdate);
        ExamCenter testExamCenter = examCenterList.get(examCenterList.size() - 1);
        assertThat(testExamCenter.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testExamCenter.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    void putNonExistingExamCenter() throws Exception {
        int databaseSizeBeforeUpdate = examCenterRepository.findAll().size();
        examCenter.setId(count.incrementAndGet());

        // Create the ExamCenter
        ExamCenterDTO examCenterDTO = examCenterMapper.toDto(examCenter);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExamCenterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, examCenterDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(examCenterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExamCenter in the database
        List<ExamCenter> examCenterList = examCenterRepository.findAll();
        assertThat(examCenterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchExamCenter() throws Exception {
        int databaseSizeBeforeUpdate = examCenterRepository.findAll().size();
        examCenter.setId(count.incrementAndGet());

        // Create the ExamCenter
        ExamCenterDTO examCenterDTO = examCenterMapper.toDto(examCenter);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExamCenterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(examCenterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExamCenter in the database
        List<ExamCenter> examCenterList = examCenterRepository.findAll();
        assertThat(examCenterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamExamCenter() throws Exception {
        int databaseSizeBeforeUpdate = examCenterRepository.findAll().size();
        examCenter.setId(count.incrementAndGet());

        // Create the ExamCenter
        ExamCenterDTO examCenterDTO = examCenterMapper.toDto(examCenter);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExamCenterMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(examCenterDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ExamCenter in the database
        List<ExamCenter> examCenterList = examCenterRepository.findAll();
        assertThat(examCenterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateExamCenterWithPatch() throws Exception {
        // Initialize the database
        examCenterRepository.saveAndFlush(examCenter);

        int databaseSizeBeforeUpdate = examCenterRepository.findAll().size();

        // Update the examCenter using partial update
        ExamCenter partialUpdatedExamCenter = new ExamCenter();
        partialUpdatedExamCenter.setId(examCenter.getId());

        partialUpdatedExamCenter.name(UPDATED_NAME);

        restExamCenterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExamCenter.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedExamCenter))
            )
            .andExpect(status().isOk());

        // Validate the ExamCenter in the database
        List<ExamCenter> examCenterList = examCenterRepository.findAll();
        assertThat(examCenterList).hasSize(databaseSizeBeforeUpdate);
        ExamCenter testExamCenter = examCenterList.get(examCenterList.size() - 1);
        assertThat(testExamCenter.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testExamCenter.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    void fullUpdateExamCenterWithPatch() throws Exception {
        // Initialize the database
        examCenterRepository.saveAndFlush(examCenter);

        int databaseSizeBeforeUpdate = examCenterRepository.findAll().size();

        // Update the examCenter using partial update
        ExamCenter partialUpdatedExamCenter = new ExamCenter();
        partialUpdatedExamCenter.setId(examCenter.getId());

        partialUpdatedExamCenter.name(UPDATED_NAME).code(UPDATED_CODE);

        restExamCenterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExamCenter.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedExamCenter))
            )
            .andExpect(status().isOk());

        // Validate the ExamCenter in the database
        List<ExamCenter> examCenterList = examCenterRepository.findAll();
        assertThat(examCenterList).hasSize(databaseSizeBeforeUpdate);
        ExamCenter testExamCenter = examCenterList.get(examCenterList.size() - 1);
        assertThat(testExamCenter.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testExamCenter.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    void patchNonExistingExamCenter() throws Exception {
        int databaseSizeBeforeUpdate = examCenterRepository.findAll().size();
        examCenter.setId(count.incrementAndGet());

        // Create the ExamCenter
        ExamCenterDTO examCenterDTO = examCenterMapper.toDto(examCenter);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExamCenterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, examCenterDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(examCenterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExamCenter in the database
        List<ExamCenter> examCenterList = examCenterRepository.findAll();
        assertThat(examCenterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchExamCenter() throws Exception {
        int databaseSizeBeforeUpdate = examCenterRepository.findAll().size();
        examCenter.setId(count.incrementAndGet());

        // Create the ExamCenter
        ExamCenterDTO examCenterDTO = examCenterMapper.toDto(examCenter);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExamCenterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(examCenterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExamCenter in the database
        List<ExamCenter> examCenterList = examCenterRepository.findAll();
        assertThat(examCenterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamExamCenter() throws Exception {
        int databaseSizeBeforeUpdate = examCenterRepository.findAll().size();
        examCenter.setId(count.incrementAndGet());

        // Create the ExamCenter
        ExamCenterDTO examCenterDTO = examCenterMapper.toDto(examCenter);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExamCenterMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(examCenterDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ExamCenter in the database
        List<ExamCenter> examCenterList = examCenterRepository.findAll();
        assertThat(examCenterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteExamCenter() throws Exception {
        // Initialize the database
        examCenterRepository.saveAndFlush(examCenter);

        int databaseSizeBeforeDelete = examCenterRepository.findAll().size();

        // Delete the examCenter
        restExamCenterMockMvc
            .perform(delete(ENTITY_API_URL_ID, examCenter.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ExamCenter> examCenterList = examCenterRepository.findAll();
        assertThat(examCenterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
