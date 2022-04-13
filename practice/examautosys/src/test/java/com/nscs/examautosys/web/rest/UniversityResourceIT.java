package com.nscs.examautosys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.nscs.examautosys.IntegrationTest;
import com.nscs.examautosys.domain.University;
import com.nscs.examautosys.repository.UniversityRepository;
import com.nscs.examautosys.service.dto.UniversityDTO;
import com.nscs.examautosys.service.mapper.UniversityMapper;
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
 * Integration tests for the {@link UniversityResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UniversityResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/universities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UniversityRepository universityRepository;

    @Autowired
    private UniversityMapper universityMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUniversityMockMvc;

    private University university;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static University createEntity(EntityManager em) {
        University university = new University().name(DEFAULT_NAME).code(DEFAULT_CODE);
        return university;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static University createUpdatedEntity(EntityManager em) {
        University university = new University().name(UPDATED_NAME).code(UPDATED_CODE);
        return university;
    }

    @BeforeEach
    public void initTest() {
        university = createEntity(em);
    }

    @Test
    @Transactional
    void createUniversity() throws Exception {
        int databaseSizeBeforeCreate = universityRepository.findAll().size();
        // Create the University
        UniversityDTO universityDTO = universityMapper.toDto(university);
        restUniversityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(universityDTO)))
            .andExpect(status().isCreated());

        // Validate the University in the database
        List<University> universityList = universityRepository.findAll();
        assertThat(universityList).hasSize(databaseSizeBeforeCreate + 1);
        University testUniversity = universityList.get(universityList.size() - 1);
        assertThat(testUniversity.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testUniversity.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    void createUniversityWithExistingId() throws Exception {
        // Create the University with an existing ID
        university.setId(1L);
        UniversityDTO universityDTO = universityMapper.toDto(university);

        int databaseSizeBeforeCreate = universityRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUniversityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(universityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the University in the database
        List<University> universityList = universityRepository.findAll();
        assertThat(universityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = universityRepository.findAll().size();
        // set the field null
        university.setName(null);

        // Create the University, which fails.
        UniversityDTO universityDTO = universityMapper.toDto(university);

        restUniversityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(universityDTO)))
            .andExpect(status().isBadRequest());

        List<University> universityList = universityRepository.findAll();
        assertThat(universityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllUniversities() throws Exception {
        // Initialize the database
        universityRepository.saveAndFlush(university);

        // Get all the universityList
        restUniversityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(university.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
    }

    @Test
    @Transactional
    void getUniversity() throws Exception {
        // Initialize the database
        universityRepository.saveAndFlush(university);

        // Get the university
        restUniversityMockMvc
            .perform(get(ENTITY_API_URL_ID, university.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(university.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE));
    }

    @Test
    @Transactional
    void getNonExistingUniversity() throws Exception {
        // Get the university
        restUniversityMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewUniversity() throws Exception {
        // Initialize the database
        universityRepository.saveAndFlush(university);

        int databaseSizeBeforeUpdate = universityRepository.findAll().size();

        // Update the university
        University updatedUniversity = universityRepository.findById(university.getId()).get();
        // Disconnect from session so that the updates on updatedUniversity are not directly saved in db
        em.detach(updatedUniversity);
        updatedUniversity.name(UPDATED_NAME).code(UPDATED_CODE);
        UniversityDTO universityDTO = universityMapper.toDto(updatedUniversity);

        restUniversityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, universityDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(universityDTO))
            )
            .andExpect(status().isOk());

        // Validate the University in the database
        List<University> universityList = universityRepository.findAll();
        assertThat(universityList).hasSize(databaseSizeBeforeUpdate);
        University testUniversity = universityList.get(universityList.size() - 1);
        assertThat(testUniversity.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUniversity.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    void putNonExistingUniversity() throws Exception {
        int databaseSizeBeforeUpdate = universityRepository.findAll().size();
        university.setId(count.incrementAndGet());

        // Create the University
        UniversityDTO universityDTO = universityMapper.toDto(university);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUniversityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, universityDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(universityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the University in the database
        List<University> universityList = universityRepository.findAll();
        assertThat(universityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUniversity() throws Exception {
        int databaseSizeBeforeUpdate = universityRepository.findAll().size();
        university.setId(count.incrementAndGet());

        // Create the University
        UniversityDTO universityDTO = universityMapper.toDto(university);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUniversityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(universityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the University in the database
        List<University> universityList = universityRepository.findAll();
        assertThat(universityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUniversity() throws Exception {
        int databaseSizeBeforeUpdate = universityRepository.findAll().size();
        university.setId(count.incrementAndGet());

        // Create the University
        UniversityDTO universityDTO = universityMapper.toDto(university);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUniversityMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(universityDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the University in the database
        List<University> universityList = universityRepository.findAll();
        assertThat(universityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUniversityWithPatch() throws Exception {
        // Initialize the database
        universityRepository.saveAndFlush(university);

        int databaseSizeBeforeUpdate = universityRepository.findAll().size();

        // Update the university using partial update
        University partialUpdatedUniversity = new University();
        partialUpdatedUniversity.setId(university.getId());

        partialUpdatedUniversity.code(UPDATED_CODE);

        restUniversityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUniversity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUniversity))
            )
            .andExpect(status().isOk());

        // Validate the University in the database
        List<University> universityList = universityRepository.findAll();
        assertThat(universityList).hasSize(databaseSizeBeforeUpdate);
        University testUniversity = universityList.get(universityList.size() - 1);
        assertThat(testUniversity.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testUniversity.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    void fullUpdateUniversityWithPatch() throws Exception {
        // Initialize the database
        universityRepository.saveAndFlush(university);

        int databaseSizeBeforeUpdate = universityRepository.findAll().size();

        // Update the university using partial update
        University partialUpdatedUniversity = new University();
        partialUpdatedUniversity.setId(university.getId());

        partialUpdatedUniversity.name(UPDATED_NAME).code(UPDATED_CODE);

        restUniversityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUniversity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUniversity))
            )
            .andExpect(status().isOk());

        // Validate the University in the database
        List<University> universityList = universityRepository.findAll();
        assertThat(universityList).hasSize(databaseSizeBeforeUpdate);
        University testUniversity = universityList.get(universityList.size() - 1);
        assertThat(testUniversity.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUniversity.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    void patchNonExistingUniversity() throws Exception {
        int databaseSizeBeforeUpdate = universityRepository.findAll().size();
        university.setId(count.incrementAndGet());

        // Create the University
        UniversityDTO universityDTO = universityMapper.toDto(university);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUniversityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, universityDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(universityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the University in the database
        List<University> universityList = universityRepository.findAll();
        assertThat(universityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUniversity() throws Exception {
        int databaseSizeBeforeUpdate = universityRepository.findAll().size();
        university.setId(count.incrementAndGet());

        // Create the University
        UniversityDTO universityDTO = universityMapper.toDto(university);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUniversityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(universityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the University in the database
        List<University> universityList = universityRepository.findAll();
        assertThat(universityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUniversity() throws Exception {
        int databaseSizeBeforeUpdate = universityRepository.findAll().size();
        university.setId(count.incrementAndGet());

        // Create the University
        UniversityDTO universityDTO = universityMapper.toDto(university);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUniversityMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(universityDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the University in the database
        List<University> universityList = universityRepository.findAll();
        assertThat(universityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUniversity() throws Exception {
        // Initialize the database
        universityRepository.saveAndFlush(university);

        int databaseSizeBeforeDelete = universityRepository.findAll().size();

        // Delete the university
        restUniversityMockMvc
            .perform(delete(ENTITY_API_URL_ID, university.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<University> universityList = universityRepository.findAll();
        assertThat(universityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
