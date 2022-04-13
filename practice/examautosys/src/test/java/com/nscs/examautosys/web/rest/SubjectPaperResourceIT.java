package com.nscs.examautosys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.nscs.examautosys.IntegrationTest;
import com.nscs.examautosys.domain.SubjectPaper;
import com.nscs.examautosys.domain.enumeration.SubjectPaperType;
import com.nscs.examautosys.repository.SubjectPaperRepository;
import com.nscs.examautosys.service.dto.SubjectPaperDTO;
import com.nscs.examautosys.service.mapper.SubjectPaperMapper;
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
 * Integration tests for the {@link SubjectPaperResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SubjectPaperResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final SubjectPaperType DEFAULT_TYPE = SubjectPaperType.PRACTICAL;
    private static final SubjectPaperType UPDATED_TYPE = SubjectPaperType.THEORY;

    private static final Integer DEFAULT_FULL_MARK = 1;
    private static final Integer UPDATED_FULL_MARK = 2;

    private static final Integer DEFAULT_PASS_MARK = 1;
    private static final Integer UPDATED_PASS_MARK = 2;

    private static final String ENTITY_API_URL = "/api/subject-papers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SubjectPaperRepository subjectPaperRepository;

    @Autowired
    private SubjectPaperMapper subjectPaperMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSubjectPaperMockMvc;

    private SubjectPaper subjectPaper;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubjectPaper createEntity(EntityManager em) {
        SubjectPaper subjectPaper = new SubjectPaper()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .type(DEFAULT_TYPE)
            .fullMark(DEFAULT_FULL_MARK)
            .passMark(DEFAULT_PASS_MARK);
        return subjectPaper;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubjectPaper createUpdatedEntity(EntityManager em) {
        SubjectPaper subjectPaper = new SubjectPaper()
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .type(UPDATED_TYPE)
            .fullMark(UPDATED_FULL_MARK)
            .passMark(UPDATED_PASS_MARK);
        return subjectPaper;
    }

    @BeforeEach
    public void initTest() {
        subjectPaper = createEntity(em);
    }

    @Test
    @Transactional
    void createSubjectPaper() throws Exception {
        int databaseSizeBeforeCreate = subjectPaperRepository.findAll().size();
        // Create the SubjectPaper
        SubjectPaperDTO subjectPaperDTO = subjectPaperMapper.toDto(subjectPaper);
        restSubjectPaperMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subjectPaperDTO))
            )
            .andExpect(status().isCreated());

        // Validate the SubjectPaper in the database
        List<SubjectPaper> subjectPaperList = subjectPaperRepository.findAll();
        assertThat(subjectPaperList).hasSize(databaseSizeBeforeCreate + 1);
        SubjectPaper testSubjectPaper = subjectPaperList.get(subjectPaperList.size() - 1);
        assertThat(testSubjectPaper.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSubjectPaper.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSubjectPaper.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testSubjectPaper.getFullMark()).isEqualTo(DEFAULT_FULL_MARK);
        assertThat(testSubjectPaper.getPassMark()).isEqualTo(DEFAULT_PASS_MARK);
    }

    @Test
    @Transactional
    void createSubjectPaperWithExistingId() throws Exception {
        // Create the SubjectPaper with an existing ID
        subjectPaper.setId(1L);
        SubjectPaperDTO subjectPaperDTO = subjectPaperMapper.toDto(subjectPaper);

        int databaseSizeBeforeCreate = subjectPaperRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubjectPaperMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subjectPaperDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubjectPaper in the database
        List<SubjectPaper> subjectPaperList = subjectPaperRepository.findAll();
        assertThat(subjectPaperList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = subjectPaperRepository.findAll().size();
        // set the field null
        subjectPaper.setName(null);

        // Create the SubjectPaper, which fails.
        SubjectPaperDTO subjectPaperDTO = subjectPaperMapper.toDto(subjectPaper);

        restSubjectPaperMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subjectPaperDTO))
            )
            .andExpect(status().isBadRequest());

        List<SubjectPaper> subjectPaperList = subjectPaperRepository.findAll();
        assertThat(subjectPaperList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSubjectPapers() throws Exception {
        // Initialize the database
        subjectPaperRepository.saveAndFlush(subjectPaper);

        // Get all the subjectPaperList
        restSubjectPaperMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subjectPaper.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].fullMark").value(hasItem(DEFAULT_FULL_MARK)))
            .andExpect(jsonPath("$.[*].passMark").value(hasItem(DEFAULT_PASS_MARK)));
    }

    @Test
    @Transactional
    void getSubjectPaper() throws Exception {
        // Initialize the database
        subjectPaperRepository.saveAndFlush(subjectPaper);

        // Get the subjectPaper
        restSubjectPaperMockMvc
            .perform(get(ENTITY_API_URL_ID, subjectPaper.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(subjectPaper.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.fullMark").value(DEFAULT_FULL_MARK))
            .andExpect(jsonPath("$.passMark").value(DEFAULT_PASS_MARK));
    }

    @Test
    @Transactional
    void getNonExistingSubjectPaper() throws Exception {
        // Get the subjectPaper
        restSubjectPaperMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSubjectPaper() throws Exception {
        // Initialize the database
        subjectPaperRepository.saveAndFlush(subjectPaper);

        int databaseSizeBeforeUpdate = subjectPaperRepository.findAll().size();

        // Update the subjectPaper
        SubjectPaper updatedSubjectPaper = subjectPaperRepository.findById(subjectPaper.getId()).get();
        // Disconnect from session so that the updates on updatedSubjectPaper are not directly saved in db
        em.detach(updatedSubjectPaper);
        updatedSubjectPaper
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .type(UPDATED_TYPE)
            .fullMark(UPDATED_FULL_MARK)
            .passMark(UPDATED_PASS_MARK);
        SubjectPaperDTO subjectPaperDTO = subjectPaperMapper.toDto(updatedSubjectPaper);

        restSubjectPaperMockMvc
            .perform(
                put(ENTITY_API_URL_ID, subjectPaperDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subjectPaperDTO))
            )
            .andExpect(status().isOk());

        // Validate the SubjectPaper in the database
        List<SubjectPaper> subjectPaperList = subjectPaperRepository.findAll();
        assertThat(subjectPaperList).hasSize(databaseSizeBeforeUpdate);
        SubjectPaper testSubjectPaper = subjectPaperList.get(subjectPaperList.size() - 1);
        assertThat(testSubjectPaper.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSubjectPaper.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSubjectPaper.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testSubjectPaper.getFullMark()).isEqualTo(UPDATED_FULL_MARK);
        assertThat(testSubjectPaper.getPassMark()).isEqualTo(UPDATED_PASS_MARK);
    }

    @Test
    @Transactional
    void putNonExistingSubjectPaper() throws Exception {
        int databaseSizeBeforeUpdate = subjectPaperRepository.findAll().size();
        subjectPaper.setId(count.incrementAndGet());

        // Create the SubjectPaper
        SubjectPaperDTO subjectPaperDTO = subjectPaperMapper.toDto(subjectPaper);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubjectPaperMockMvc
            .perform(
                put(ENTITY_API_URL_ID, subjectPaperDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subjectPaperDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubjectPaper in the database
        List<SubjectPaper> subjectPaperList = subjectPaperRepository.findAll();
        assertThat(subjectPaperList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSubjectPaper() throws Exception {
        int databaseSizeBeforeUpdate = subjectPaperRepository.findAll().size();
        subjectPaper.setId(count.incrementAndGet());

        // Create the SubjectPaper
        SubjectPaperDTO subjectPaperDTO = subjectPaperMapper.toDto(subjectPaper);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubjectPaperMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subjectPaperDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubjectPaper in the database
        List<SubjectPaper> subjectPaperList = subjectPaperRepository.findAll();
        assertThat(subjectPaperList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSubjectPaper() throws Exception {
        int databaseSizeBeforeUpdate = subjectPaperRepository.findAll().size();
        subjectPaper.setId(count.incrementAndGet());

        // Create the SubjectPaper
        SubjectPaperDTO subjectPaperDTO = subjectPaperMapper.toDto(subjectPaper);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubjectPaperMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subjectPaperDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SubjectPaper in the database
        List<SubjectPaper> subjectPaperList = subjectPaperRepository.findAll();
        assertThat(subjectPaperList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSubjectPaperWithPatch() throws Exception {
        // Initialize the database
        subjectPaperRepository.saveAndFlush(subjectPaper);

        int databaseSizeBeforeUpdate = subjectPaperRepository.findAll().size();

        // Update the subjectPaper using partial update
        SubjectPaper partialUpdatedSubjectPaper = new SubjectPaper();
        partialUpdatedSubjectPaper.setId(subjectPaper.getId());

        partialUpdatedSubjectPaper.name(UPDATED_NAME).fullMark(UPDATED_FULL_MARK).passMark(UPDATED_PASS_MARK);

        restSubjectPaperMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubjectPaper.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSubjectPaper))
            )
            .andExpect(status().isOk());

        // Validate the SubjectPaper in the database
        List<SubjectPaper> subjectPaperList = subjectPaperRepository.findAll();
        assertThat(subjectPaperList).hasSize(databaseSizeBeforeUpdate);
        SubjectPaper testSubjectPaper = subjectPaperList.get(subjectPaperList.size() - 1);
        assertThat(testSubjectPaper.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSubjectPaper.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSubjectPaper.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testSubjectPaper.getFullMark()).isEqualTo(UPDATED_FULL_MARK);
        assertThat(testSubjectPaper.getPassMark()).isEqualTo(UPDATED_PASS_MARK);
    }

    @Test
    @Transactional
    void fullUpdateSubjectPaperWithPatch() throws Exception {
        // Initialize the database
        subjectPaperRepository.saveAndFlush(subjectPaper);

        int databaseSizeBeforeUpdate = subjectPaperRepository.findAll().size();

        // Update the subjectPaper using partial update
        SubjectPaper partialUpdatedSubjectPaper = new SubjectPaper();
        partialUpdatedSubjectPaper.setId(subjectPaper.getId());

        partialUpdatedSubjectPaper
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .type(UPDATED_TYPE)
            .fullMark(UPDATED_FULL_MARK)
            .passMark(UPDATED_PASS_MARK);

        restSubjectPaperMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubjectPaper.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSubjectPaper))
            )
            .andExpect(status().isOk());

        // Validate the SubjectPaper in the database
        List<SubjectPaper> subjectPaperList = subjectPaperRepository.findAll();
        assertThat(subjectPaperList).hasSize(databaseSizeBeforeUpdate);
        SubjectPaper testSubjectPaper = subjectPaperList.get(subjectPaperList.size() - 1);
        assertThat(testSubjectPaper.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSubjectPaper.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSubjectPaper.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testSubjectPaper.getFullMark()).isEqualTo(UPDATED_FULL_MARK);
        assertThat(testSubjectPaper.getPassMark()).isEqualTo(UPDATED_PASS_MARK);
    }

    @Test
    @Transactional
    void patchNonExistingSubjectPaper() throws Exception {
        int databaseSizeBeforeUpdate = subjectPaperRepository.findAll().size();
        subjectPaper.setId(count.incrementAndGet());

        // Create the SubjectPaper
        SubjectPaperDTO subjectPaperDTO = subjectPaperMapper.toDto(subjectPaper);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubjectPaperMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, subjectPaperDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(subjectPaperDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubjectPaper in the database
        List<SubjectPaper> subjectPaperList = subjectPaperRepository.findAll();
        assertThat(subjectPaperList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSubjectPaper() throws Exception {
        int databaseSizeBeforeUpdate = subjectPaperRepository.findAll().size();
        subjectPaper.setId(count.incrementAndGet());

        // Create the SubjectPaper
        SubjectPaperDTO subjectPaperDTO = subjectPaperMapper.toDto(subjectPaper);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubjectPaperMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(subjectPaperDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubjectPaper in the database
        List<SubjectPaper> subjectPaperList = subjectPaperRepository.findAll();
        assertThat(subjectPaperList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSubjectPaper() throws Exception {
        int databaseSizeBeforeUpdate = subjectPaperRepository.findAll().size();
        subjectPaper.setId(count.incrementAndGet());

        // Create the SubjectPaper
        SubjectPaperDTO subjectPaperDTO = subjectPaperMapper.toDto(subjectPaper);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubjectPaperMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(subjectPaperDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SubjectPaper in the database
        List<SubjectPaper> subjectPaperList = subjectPaperRepository.findAll();
        assertThat(subjectPaperList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSubjectPaper() throws Exception {
        // Initialize the database
        subjectPaperRepository.saveAndFlush(subjectPaper);

        int databaseSizeBeforeDelete = subjectPaperRepository.findAll().size();

        // Delete the subjectPaper
        restSubjectPaperMockMvc
            .perform(delete(ENTITY_API_URL_ID, subjectPaper.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SubjectPaper> subjectPaperList = subjectPaperRepository.findAll();
        assertThat(subjectPaperList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
