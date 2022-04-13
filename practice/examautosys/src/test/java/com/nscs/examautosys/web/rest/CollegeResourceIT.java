package com.nscs.examautosys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.nscs.examautosys.IntegrationTest;
import com.nscs.examautosys.domain.College;
import com.nscs.examautosys.repository.CollegeRepository;
import com.nscs.examautosys.service.dto.CollegeDTO;
import com.nscs.examautosys.service.mapper.CollegeMapper;
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
 * Integration tests for the {@link CollegeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CollegeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/colleges";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CollegeRepository collegeRepository;

    @Autowired
    private CollegeMapper collegeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCollegeMockMvc;

    private College college;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static College createEntity(EntityManager em) {
        College college = new College().name(DEFAULT_NAME).code(DEFAULT_CODE);
        return college;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static College createUpdatedEntity(EntityManager em) {
        College college = new College().name(UPDATED_NAME).code(UPDATED_CODE);
        return college;
    }

    @BeforeEach
    public void initTest() {
        college = createEntity(em);
    }

    @Test
    @Transactional
    void createCollege() throws Exception {
        int databaseSizeBeforeCreate = collegeRepository.findAll().size();
        // Create the College
        CollegeDTO collegeDTO = collegeMapper.toDto(college);
        restCollegeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(collegeDTO)))
            .andExpect(status().isCreated());

        // Validate the College in the database
        List<College> collegeList = collegeRepository.findAll();
        assertThat(collegeList).hasSize(databaseSizeBeforeCreate + 1);
        College testCollege = collegeList.get(collegeList.size() - 1);
        assertThat(testCollege.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCollege.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    void createCollegeWithExistingId() throws Exception {
        // Create the College with an existing ID
        college.setId(1L);
        CollegeDTO collegeDTO = collegeMapper.toDto(college);

        int databaseSizeBeforeCreate = collegeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCollegeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(collegeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the College in the database
        List<College> collegeList = collegeRepository.findAll();
        assertThat(collegeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = collegeRepository.findAll().size();
        // set the field null
        college.setName(null);

        // Create the College, which fails.
        CollegeDTO collegeDTO = collegeMapper.toDto(college);

        restCollegeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(collegeDTO)))
            .andExpect(status().isBadRequest());

        List<College> collegeList = collegeRepository.findAll();
        assertThat(collegeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllColleges() throws Exception {
        // Initialize the database
        collegeRepository.saveAndFlush(college);

        // Get all the collegeList
        restCollegeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(college.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
    }

    @Test
    @Transactional
    void getCollege() throws Exception {
        // Initialize the database
        collegeRepository.saveAndFlush(college);

        // Get the college
        restCollegeMockMvc
            .perform(get(ENTITY_API_URL_ID, college.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(college.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE));
    }

    @Test
    @Transactional
    void getNonExistingCollege() throws Exception {
        // Get the college
        restCollegeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCollege() throws Exception {
        // Initialize the database
        collegeRepository.saveAndFlush(college);

        int databaseSizeBeforeUpdate = collegeRepository.findAll().size();

        // Update the college
        College updatedCollege = collegeRepository.findById(college.getId()).get();
        // Disconnect from session so that the updates on updatedCollege are not directly saved in db
        em.detach(updatedCollege);
        updatedCollege.name(UPDATED_NAME).code(UPDATED_CODE);
        CollegeDTO collegeDTO = collegeMapper.toDto(updatedCollege);

        restCollegeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, collegeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(collegeDTO))
            )
            .andExpect(status().isOk());

        // Validate the College in the database
        List<College> collegeList = collegeRepository.findAll();
        assertThat(collegeList).hasSize(databaseSizeBeforeUpdate);
        College testCollege = collegeList.get(collegeList.size() - 1);
        assertThat(testCollege.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCollege.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    void putNonExistingCollege() throws Exception {
        int databaseSizeBeforeUpdate = collegeRepository.findAll().size();
        college.setId(count.incrementAndGet());

        // Create the College
        CollegeDTO collegeDTO = collegeMapper.toDto(college);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCollegeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, collegeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(collegeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the College in the database
        List<College> collegeList = collegeRepository.findAll();
        assertThat(collegeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCollege() throws Exception {
        int databaseSizeBeforeUpdate = collegeRepository.findAll().size();
        college.setId(count.incrementAndGet());

        // Create the College
        CollegeDTO collegeDTO = collegeMapper.toDto(college);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCollegeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(collegeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the College in the database
        List<College> collegeList = collegeRepository.findAll();
        assertThat(collegeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCollege() throws Exception {
        int databaseSizeBeforeUpdate = collegeRepository.findAll().size();
        college.setId(count.incrementAndGet());

        // Create the College
        CollegeDTO collegeDTO = collegeMapper.toDto(college);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCollegeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(collegeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the College in the database
        List<College> collegeList = collegeRepository.findAll();
        assertThat(collegeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCollegeWithPatch() throws Exception {
        // Initialize the database
        collegeRepository.saveAndFlush(college);

        int databaseSizeBeforeUpdate = collegeRepository.findAll().size();

        // Update the college using partial update
        College partialUpdatedCollege = new College();
        partialUpdatedCollege.setId(college.getId());

        restCollegeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCollege.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCollege))
            )
            .andExpect(status().isOk());

        // Validate the College in the database
        List<College> collegeList = collegeRepository.findAll();
        assertThat(collegeList).hasSize(databaseSizeBeforeUpdate);
        College testCollege = collegeList.get(collegeList.size() - 1);
        assertThat(testCollege.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCollege.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    void fullUpdateCollegeWithPatch() throws Exception {
        // Initialize the database
        collegeRepository.saveAndFlush(college);

        int databaseSizeBeforeUpdate = collegeRepository.findAll().size();

        // Update the college using partial update
        College partialUpdatedCollege = new College();
        partialUpdatedCollege.setId(college.getId());

        partialUpdatedCollege.name(UPDATED_NAME).code(UPDATED_CODE);

        restCollegeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCollege.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCollege))
            )
            .andExpect(status().isOk());

        // Validate the College in the database
        List<College> collegeList = collegeRepository.findAll();
        assertThat(collegeList).hasSize(databaseSizeBeforeUpdate);
        College testCollege = collegeList.get(collegeList.size() - 1);
        assertThat(testCollege.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCollege.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    void patchNonExistingCollege() throws Exception {
        int databaseSizeBeforeUpdate = collegeRepository.findAll().size();
        college.setId(count.incrementAndGet());

        // Create the College
        CollegeDTO collegeDTO = collegeMapper.toDto(college);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCollegeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, collegeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(collegeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the College in the database
        List<College> collegeList = collegeRepository.findAll();
        assertThat(collegeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCollege() throws Exception {
        int databaseSizeBeforeUpdate = collegeRepository.findAll().size();
        college.setId(count.incrementAndGet());

        // Create the College
        CollegeDTO collegeDTO = collegeMapper.toDto(college);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCollegeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(collegeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the College in the database
        List<College> collegeList = collegeRepository.findAll();
        assertThat(collegeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCollege() throws Exception {
        int databaseSizeBeforeUpdate = collegeRepository.findAll().size();
        college.setId(count.incrementAndGet());

        // Create the College
        CollegeDTO collegeDTO = collegeMapper.toDto(college);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCollegeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(collegeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the College in the database
        List<College> collegeList = collegeRepository.findAll();
        assertThat(collegeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCollege() throws Exception {
        // Initialize the database
        collegeRepository.saveAndFlush(college);

        int databaseSizeBeforeDelete = collegeRepository.findAll().size();

        // Delete the college
        restCollegeMockMvc
            .perform(delete(ENTITY_API_URL_ID, college.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<College> collegeList = collegeRepository.findAll();
        assertThat(collegeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
