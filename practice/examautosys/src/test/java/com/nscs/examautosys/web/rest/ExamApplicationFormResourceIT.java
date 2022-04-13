package com.nscs.examautosys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.nscs.examautosys.IntegrationTest;
import com.nscs.examautosys.domain.ExamApplicationForm;
import com.nscs.examautosys.domain.enumeration.BloodGroup;
import com.nscs.examautosys.domain.enumeration.Gender;
import com.nscs.examautosys.domain.enumeration.IdentityType;
import com.nscs.examautosys.repository.ExamApplicationFormRepository;
import com.nscs.examautosys.service.dto.ExamApplicationFormDTO;
import com.nscs.examautosys.service.mapper.ExamApplicationFormMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link ExamApplicationFormResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ExamApplicationFormResourceIT {

    private static final String DEFAULT_REG_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_REG_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MIDDLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MIDDLE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_DOB = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DOB = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_FATHER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FATHER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_NATIONALITY = "AAAAAAAAAA";
    private static final String UPDATED_NATIONALITY = "BBBBBBBBBB";

    private static final Gender DEFAULT_GENDER = Gender.MALE;
    private static final Gender UPDATED_GENDER = Gender.FEMALE;

    private static final String DEFAULT_RELIGION = "AAAAAAAAAA";
    private static final String UPDATED_RELIGION = "BBBBBBBBBB";

    private static final String DEFAULT_ADHAR_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ADHAR_NUMBER = "BBBBBBBBBB";

    private static final BloodGroup DEFAULT_BLOOD_GROUP = BloodGroup.O_POS;
    private static final BloodGroup UPDATED_BLOOD_GROUP = BloodGroup.O_NEG;

    private static final Boolean DEFAULT_IS_APPROVED = false;
    private static final Boolean UPDATED_IS_APPROVED = true;

    private static final String DEFAULT_CATAGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATAGORY = "BBBBBBBBBB";

    private static final IdentityType DEFAULT_IDENTITY_TYPE = IdentityType.ADHAR;
    private static final IdentityType UPDATED_IDENTITY_TYPE = IdentityType.PAN_CARD;

    private static final String DEFAULT_IDENTITY_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTITY_NUMBER = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_SIGNATURE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SIGNATURE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_SIGNATURE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SIGNATURE_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_ADHAR = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ADHAR = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ADHAR_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ADHAR_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_IMAGE_PATH = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_SIGN_PATH = "AAAAAAAAAA";
    private static final String UPDATED_SIGN_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_ADHAR_PATH = "AAAAAAAAAA";
    private static final String UPDATED_ADHAR_PATH = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/exam-application-forms";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ExamApplicationFormRepository examApplicationFormRepository;

    @Autowired
    private ExamApplicationFormMapper examApplicationFormMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restExamApplicationFormMockMvc;

    private ExamApplicationForm examApplicationForm;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExamApplicationForm createEntity(EntityManager em) {
        ExamApplicationForm examApplicationForm = new ExamApplicationForm()
            .regNumber(DEFAULT_REG_NUMBER)
            .firstName(DEFAULT_FIRST_NAME)
            .middleName(DEFAULT_MIDDLE_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .dob(DEFAULT_DOB)
            .fatherName(DEFAULT_FATHER_NAME)
            .email(DEFAULT_EMAIL)
            .mobileNumber(DEFAULT_MOBILE_NUMBER)
            .nationality(DEFAULT_NATIONALITY)
            .gender(DEFAULT_GENDER)
            .religion(DEFAULT_RELIGION)
            .adharNumber(DEFAULT_ADHAR_NUMBER)
            .bloodGroup(DEFAULT_BLOOD_GROUP)
            .isApproved(DEFAULT_IS_APPROVED)
            .catagory(DEFAULT_CATAGORY)
            .identityType(DEFAULT_IDENTITY_TYPE)
            .identityNumber(DEFAULT_IDENTITY_NUMBER)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .signature(DEFAULT_SIGNATURE)
            .signatureContentType(DEFAULT_SIGNATURE_CONTENT_TYPE)
            .adhar(DEFAULT_ADHAR)
            .adharContentType(DEFAULT_ADHAR_CONTENT_TYPE)
            .imagePath(DEFAULT_IMAGE_PATH)
            .signPath(DEFAULT_SIGN_PATH)
            .adharPath(DEFAULT_ADHAR_PATH);
        return examApplicationForm;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExamApplicationForm createUpdatedEntity(EntityManager em) {
        ExamApplicationForm examApplicationForm = new ExamApplicationForm()
            .regNumber(UPDATED_REG_NUMBER)
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            .dob(UPDATED_DOB)
            .fatherName(UPDATED_FATHER_NAME)
            .email(UPDATED_EMAIL)
            .mobileNumber(UPDATED_MOBILE_NUMBER)
            .nationality(UPDATED_NATIONALITY)
            .gender(UPDATED_GENDER)
            .religion(UPDATED_RELIGION)
            .adharNumber(UPDATED_ADHAR_NUMBER)
            .bloodGroup(UPDATED_BLOOD_GROUP)
            .isApproved(UPDATED_IS_APPROVED)
            .catagory(UPDATED_CATAGORY)
            .identityType(UPDATED_IDENTITY_TYPE)
            .identityNumber(UPDATED_IDENTITY_NUMBER)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .signature(UPDATED_SIGNATURE)
            .signatureContentType(UPDATED_SIGNATURE_CONTENT_TYPE)
            .adhar(UPDATED_ADHAR)
            .adharContentType(UPDATED_ADHAR_CONTENT_TYPE)
            .imagePath(UPDATED_IMAGE_PATH)
            .signPath(UPDATED_SIGN_PATH)
            .adharPath(UPDATED_ADHAR_PATH);
        return examApplicationForm;
    }

    @BeforeEach
    public void initTest() {
        examApplicationForm = createEntity(em);
    }

    @Test
    @Transactional
    void createExamApplicationForm() throws Exception {
        int databaseSizeBeforeCreate = examApplicationFormRepository.findAll().size();
        // Create the ExamApplicationForm
        ExamApplicationFormDTO examApplicationFormDTO = examApplicationFormMapper.toDto(examApplicationForm);
        restExamApplicationFormMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(examApplicationFormDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ExamApplicationForm in the database
        List<ExamApplicationForm> examApplicationFormList = examApplicationFormRepository.findAll();
        assertThat(examApplicationFormList).hasSize(databaseSizeBeforeCreate + 1);
        ExamApplicationForm testExamApplicationForm = examApplicationFormList.get(examApplicationFormList.size() - 1);
        assertThat(testExamApplicationForm.getRegNumber()).isEqualTo(DEFAULT_REG_NUMBER);
        assertThat(testExamApplicationForm.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testExamApplicationForm.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testExamApplicationForm.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testExamApplicationForm.getDob()).isEqualTo(DEFAULT_DOB);
        assertThat(testExamApplicationForm.getFatherName()).isEqualTo(DEFAULT_FATHER_NAME);
        assertThat(testExamApplicationForm.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testExamApplicationForm.getMobileNumber()).isEqualTo(DEFAULT_MOBILE_NUMBER);
        assertThat(testExamApplicationForm.getNationality()).isEqualTo(DEFAULT_NATIONALITY);
        assertThat(testExamApplicationForm.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testExamApplicationForm.getReligion()).isEqualTo(DEFAULT_RELIGION);
        assertThat(testExamApplicationForm.getAdharNumber()).isEqualTo(DEFAULT_ADHAR_NUMBER);
        assertThat(testExamApplicationForm.getBloodGroup()).isEqualTo(DEFAULT_BLOOD_GROUP);
        assertThat(testExamApplicationForm.getIsApproved()).isEqualTo(DEFAULT_IS_APPROVED);
        assertThat(testExamApplicationForm.getCatagory()).isEqualTo(DEFAULT_CATAGORY);
        assertThat(testExamApplicationForm.getIdentityType()).isEqualTo(DEFAULT_IDENTITY_TYPE);
        assertThat(testExamApplicationForm.getIdentityNumber()).isEqualTo(DEFAULT_IDENTITY_NUMBER);
        assertThat(testExamApplicationForm.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testExamApplicationForm.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testExamApplicationForm.getSignature()).isEqualTo(DEFAULT_SIGNATURE);
        assertThat(testExamApplicationForm.getSignatureContentType()).isEqualTo(DEFAULT_SIGNATURE_CONTENT_TYPE);
        assertThat(testExamApplicationForm.getAdhar()).isEqualTo(DEFAULT_ADHAR);
        assertThat(testExamApplicationForm.getAdharContentType()).isEqualTo(DEFAULT_ADHAR_CONTENT_TYPE);
        assertThat(testExamApplicationForm.getImagePath()).isEqualTo(DEFAULT_IMAGE_PATH);
        assertThat(testExamApplicationForm.getSignPath()).isEqualTo(DEFAULT_SIGN_PATH);
        assertThat(testExamApplicationForm.getAdharPath()).isEqualTo(DEFAULT_ADHAR_PATH);
    }

    @Test
    @Transactional
    void createExamApplicationFormWithExistingId() throws Exception {
        // Create the ExamApplicationForm with an existing ID
        examApplicationForm.setId(1L);
        ExamApplicationFormDTO examApplicationFormDTO = examApplicationFormMapper.toDto(examApplicationForm);

        int databaseSizeBeforeCreate = examApplicationFormRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restExamApplicationFormMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(examApplicationFormDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExamApplicationForm in the database
        List<ExamApplicationForm> examApplicationFormList = examApplicationFormRepository.findAll();
        assertThat(examApplicationFormList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = examApplicationFormRepository.findAll().size();
        // set the field null
        examApplicationForm.setFirstName(null);

        // Create the ExamApplicationForm, which fails.
        ExamApplicationFormDTO examApplicationFormDTO = examApplicationFormMapper.toDto(examApplicationForm);

        restExamApplicationFormMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(examApplicationFormDTO))
            )
            .andExpect(status().isBadRequest());

        List<ExamApplicationForm> examApplicationFormList = examApplicationFormRepository.findAll();
        assertThat(examApplicationFormList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = examApplicationFormRepository.findAll().size();
        // set the field null
        examApplicationForm.setLastName(null);

        // Create the ExamApplicationForm, which fails.
        ExamApplicationFormDTO examApplicationFormDTO = examApplicationFormMapper.toDto(examApplicationForm);

        restExamApplicationFormMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(examApplicationFormDTO))
            )
            .andExpect(status().isBadRequest());

        List<ExamApplicationForm> examApplicationFormList = examApplicationFormRepository.findAll();
        assertThat(examApplicationFormList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = examApplicationFormRepository.findAll().size();
        // set the field null
        examApplicationForm.setEmail(null);

        // Create the ExamApplicationForm, which fails.
        ExamApplicationFormDTO examApplicationFormDTO = examApplicationFormMapper.toDto(examApplicationForm);

        restExamApplicationFormMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(examApplicationFormDTO))
            )
            .andExpect(status().isBadRequest());

        List<ExamApplicationForm> examApplicationFormList = examApplicationFormRepository.findAll();
        assertThat(examApplicationFormList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMobileNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = examApplicationFormRepository.findAll().size();
        // set the field null
        examApplicationForm.setMobileNumber(null);

        // Create the ExamApplicationForm, which fails.
        ExamApplicationFormDTO examApplicationFormDTO = examApplicationFormMapper.toDto(examApplicationForm);

        restExamApplicationFormMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(examApplicationFormDTO))
            )
            .andExpect(status().isBadRequest());

        List<ExamApplicationForm> examApplicationFormList = examApplicationFormRepository.findAll();
        assertThat(examApplicationFormList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNationalityIsRequired() throws Exception {
        int databaseSizeBeforeTest = examApplicationFormRepository.findAll().size();
        // set the field null
        examApplicationForm.setNationality(null);

        // Create the ExamApplicationForm, which fails.
        ExamApplicationFormDTO examApplicationFormDTO = examApplicationFormMapper.toDto(examApplicationForm);

        restExamApplicationFormMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(examApplicationFormDTO))
            )
            .andExpect(status().isBadRequest());

        List<ExamApplicationForm> examApplicationFormList = examApplicationFormRepository.findAll();
        assertThat(examApplicationFormList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAdharNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = examApplicationFormRepository.findAll().size();
        // set the field null
        examApplicationForm.setAdharNumber(null);

        // Create the ExamApplicationForm, which fails.
        ExamApplicationFormDTO examApplicationFormDTO = examApplicationFormMapper.toDto(examApplicationForm);

        restExamApplicationFormMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(examApplicationFormDTO))
            )
            .andExpect(status().isBadRequest());

        List<ExamApplicationForm> examApplicationFormList = examApplicationFormRepository.findAll();
        assertThat(examApplicationFormList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIdentityTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = examApplicationFormRepository.findAll().size();
        // set the field null
        examApplicationForm.setIdentityType(null);

        // Create the ExamApplicationForm, which fails.
        ExamApplicationFormDTO examApplicationFormDTO = examApplicationFormMapper.toDto(examApplicationForm);

        restExamApplicationFormMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(examApplicationFormDTO))
            )
            .andExpect(status().isBadRequest());

        List<ExamApplicationForm> examApplicationFormList = examApplicationFormRepository.findAll();
        assertThat(examApplicationFormList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIdentityNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = examApplicationFormRepository.findAll().size();
        // set the field null
        examApplicationForm.setIdentityNumber(null);

        // Create the ExamApplicationForm, which fails.
        ExamApplicationFormDTO examApplicationFormDTO = examApplicationFormMapper.toDto(examApplicationForm);

        restExamApplicationFormMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(examApplicationFormDTO))
            )
            .andExpect(status().isBadRequest());

        List<ExamApplicationForm> examApplicationFormList = examApplicationFormRepository.findAll();
        assertThat(examApplicationFormList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllExamApplicationForms() throws Exception {
        // Initialize the database
        examApplicationFormRepository.saveAndFlush(examApplicationForm);

        // Get all the examApplicationFormList
        restExamApplicationFormMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(examApplicationForm.getId().intValue())))
            .andExpect(jsonPath("$.[*].regNumber").value(hasItem(DEFAULT_REG_NUMBER)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].dob").value(hasItem(DEFAULT_DOB.toString())))
            .andExpect(jsonPath("$.[*].fatherName").value(hasItem(DEFAULT_FATHER_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].mobileNumber").value(hasItem(DEFAULT_MOBILE_NUMBER)))
            .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].religion").value(hasItem(DEFAULT_RELIGION)))
            .andExpect(jsonPath("$.[*].adharNumber").value(hasItem(DEFAULT_ADHAR_NUMBER)))
            .andExpect(jsonPath("$.[*].bloodGroup").value(hasItem(DEFAULT_BLOOD_GROUP.toString())))
            .andExpect(jsonPath("$.[*].isApproved").value(hasItem(DEFAULT_IS_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].catagory").value(hasItem(DEFAULT_CATAGORY)))
            .andExpect(jsonPath("$.[*].identityType").value(hasItem(DEFAULT_IDENTITY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].identityNumber").value(hasItem(DEFAULT_IDENTITY_NUMBER)))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].signatureContentType").value(hasItem(DEFAULT_SIGNATURE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].signature").value(hasItem(Base64Utils.encodeToString(DEFAULT_SIGNATURE))))
            .andExpect(jsonPath("$.[*].adharContentType").value(hasItem(DEFAULT_ADHAR_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].adhar").value(hasItem(Base64Utils.encodeToString(DEFAULT_ADHAR))))
            .andExpect(jsonPath("$.[*].imagePath").value(hasItem(DEFAULT_IMAGE_PATH)))
            .andExpect(jsonPath("$.[*].signPath").value(hasItem(DEFAULT_SIGN_PATH)))
            .andExpect(jsonPath("$.[*].adharPath").value(hasItem(DEFAULT_ADHAR_PATH)));
    }

    @Test
    @Transactional
    void getExamApplicationForm() throws Exception {
        // Initialize the database
        examApplicationFormRepository.saveAndFlush(examApplicationForm);

        // Get the examApplicationForm
        restExamApplicationFormMockMvc
            .perform(get(ENTITY_API_URL_ID, examApplicationForm.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(examApplicationForm.getId().intValue()))
            .andExpect(jsonPath("$.regNumber").value(DEFAULT_REG_NUMBER))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.middleName").value(DEFAULT_MIDDLE_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.dob").value(DEFAULT_DOB.toString()))
            .andExpect(jsonPath("$.fatherName").value(DEFAULT_FATHER_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.mobileNumber").value(DEFAULT_MOBILE_NUMBER))
            .andExpect(jsonPath("$.nationality").value(DEFAULT_NATIONALITY))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.religion").value(DEFAULT_RELIGION))
            .andExpect(jsonPath("$.adharNumber").value(DEFAULT_ADHAR_NUMBER))
            .andExpect(jsonPath("$.bloodGroup").value(DEFAULT_BLOOD_GROUP.toString()))
            .andExpect(jsonPath("$.isApproved").value(DEFAULT_IS_APPROVED.booleanValue()))
            .andExpect(jsonPath("$.catagory").value(DEFAULT_CATAGORY))
            .andExpect(jsonPath("$.identityType").value(DEFAULT_IDENTITY_TYPE.toString()))
            .andExpect(jsonPath("$.identityNumber").value(DEFAULT_IDENTITY_NUMBER))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.signatureContentType").value(DEFAULT_SIGNATURE_CONTENT_TYPE))
            .andExpect(jsonPath("$.signature").value(Base64Utils.encodeToString(DEFAULT_SIGNATURE)))
            .andExpect(jsonPath("$.adharContentType").value(DEFAULT_ADHAR_CONTENT_TYPE))
            .andExpect(jsonPath("$.adhar").value(Base64Utils.encodeToString(DEFAULT_ADHAR)))
            .andExpect(jsonPath("$.imagePath").value(DEFAULT_IMAGE_PATH))
            .andExpect(jsonPath("$.signPath").value(DEFAULT_SIGN_PATH))
            .andExpect(jsonPath("$.adharPath").value(DEFAULT_ADHAR_PATH));
    }

    @Test
    @Transactional
    void getNonExistingExamApplicationForm() throws Exception {
        // Get the examApplicationForm
        restExamApplicationFormMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewExamApplicationForm() throws Exception {
        // Initialize the database
        examApplicationFormRepository.saveAndFlush(examApplicationForm);

        int databaseSizeBeforeUpdate = examApplicationFormRepository.findAll().size();

        // Update the examApplicationForm
        ExamApplicationForm updatedExamApplicationForm = examApplicationFormRepository.findById(examApplicationForm.getId()).get();
        // Disconnect from session so that the updates on updatedExamApplicationForm are not directly saved in db
        em.detach(updatedExamApplicationForm);
        updatedExamApplicationForm
            .regNumber(UPDATED_REG_NUMBER)
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            .dob(UPDATED_DOB)
            .fatherName(UPDATED_FATHER_NAME)
            .email(UPDATED_EMAIL)
            .mobileNumber(UPDATED_MOBILE_NUMBER)
            .nationality(UPDATED_NATIONALITY)
            .gender(UPDATED_GENDER)
            .religion(UPDATED_RELIGION)
            .adharNumber(UPDATED_ADHAR_NUMBER)
            .bloodGroup(UPDATED_BLOOD_GROUP)
            .isApproved(UPDATED_IS_APPROVED)
            .catagory(UPDATED_CATAGORY)
            .identityType(UPDATED_IDENTITY_TYPE)
            .identityNumber(UPDATED_IDENTITY_NUMBER)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .signature(UPDATED_SIGNATURE)
            .signatureContentType(UPDATED_SIGNATURE_CONTENT_TYPE)
            .adhar(UPDATED_ADHAR)
            .adharContentType(UPDATED_ADHAR_CONTENT_TYPE)
            .imagePath(UPDATED_IMAGE_PATH)
            .signPath(UPDATED_SIGN_PATH)
            .adharPath(UPDATED_ADHAR_PATH);
        ExamApplicationFormDTO examApplicationFormDTO = examApplicationFormMapper.toDto(updatedExamApplicationForm);

        restExamApplicationFormMockMvc
            .perform(
                put(ENTITY_API_URL_ID, examApplicationFormDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(examApplicationFormDTO))
            )
            .andExpect(status().isOk());

        // Validate the ExamApplicationForm in the database
        List<ExamApplicationForm> examApplicationFormList = examApplicationFormRepository.findAll();
        assertThat(examApplicationFormList).hasSize(databaseSizeBeforeUpdate);
        ExamApplicationForm testExamApplicationForm = examApplicationFormList.get(examApplicationFormList.size() - 1);
        assertThat(testExamApplicationForm.getRegNumber()).isEqualTo(UPDATED_REG_NUMBER);
        assertThat(testExamApplicationForm.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testExamApplicationForm.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testExamApplicationForm.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testExamApplicationForm.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testExamApplicationForm.getFatherName()).isEqualTo(UPDATED_FATHER_NAME);
        assertThat(testExamApplicationForm.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testExamApplicationForm.getMobileNumber()).isEqualTo(UPDATED_MOBILE_NUMBER);
        assertThat(testExamApplicationForm.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testExamApplicationForm.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testExamApplicationForm.getReligion()).isEqualTo(UPDATED_RELIGION);
        assertThat(testExamApplicationForm.getAdharNumber()).isEqualTo(UPDATED_ADHAR_NUMBER);
        assertThat(testExamApplicationForm.getBloodGroup()).isEqualTo(UPDATED_BLOOD_GROUP);
        assertThat(testExamApplicationForm.getIsApproved()).isEqualTo(UPDATED_IS_APPROVED);
        assertThat(testExamApplicationForm.getCatagory()).isEqualTo(UPDATED_CATAGORY);
        assertThat(testExamApplicationForm.getIdentityType()).isEqualTo(UPDATED_IDENTITY_TYPE);
        assertThat(testExamApplicationForm.getIdentityNumber()).isEqualTo(UPDATED_IDENTITY_NUMBER);
        assertThat(testExamApplicationForm.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testExamApplicationForm.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testExamApplicationForm.getSignature()).isEqualTo(UPDATED_SIGNATURE);
        assertThat(testExamApplicationForm.getSignatureContentType()).isEqualTo(UPDATED_SIGNATURE_CONTENT_TYPE);
        assertThat(testExamApplicationForm.getAdhar()).isEqualTo(UPDATED_ADHAR);
        assertThat(testExamApplicationForm.getAdharContentType()).isEqualTo(UPDATED_ADHAR_CONTENT_TYPE);
        assertThat(testExamApplicationForm.getImagePath()).isEqualTo(UPDATED_IMAGE_PATH);
        assertThat(testExamApplicationForm.getSignPath()).isEqualTo(UPDATED_SIGN_PATH);
        assertThat(testExamApplicationForm.getAdharPath()).isEqualTo(UPDATED_ADHAR_PATH);
    }

    @Test
    @Transactional
    void putNonExistingExamApplicationForm() throws Exception {
        int databaseSizeBeforeUpdate = examApplicationFormRepository.findAll().size();
        examApplicationForm.setId(count.incrementAndGet());

        // Create the ExamApplicationForm
        ExamApplicationFormDTO examApplicationFormDTO = examApplicationFormMapper.toDto(examApplicationForm);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExamApplicationFormMockMvc
            .perform(
                put(ENTITY_API_URL_ID, examApplicationFormDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(examApplicationFormDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExamApplicationForm in the database
        List<ExamApplicationForm> examApplicationFormList = examApplicationFormRepository.findAll();
        assertThat(examApplicationFormList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchExamApplicationForm() throws Exception {
        int databaseSizeBeforeUpdate = examApplicationFormRepository.findAll().size();
        examApplicationForm.setId(count.incrementAndGet());

        // Create the ExamApplicationForm
        ExamApplicationFormDTO examApplicationFormDTO = examApplicationFormMapper.toDto(examApplicationForm);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExamApplicationFormMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(examApplicationFormDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExamApplicationForm in the database
        List<ExamApplicationForm> examApplicationFormList = examApplicationFormRepository.findAll();
        assertThat(examApplicationFormList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamExamApplicationForm() throws Exception {
        int databaseSizeBeforeUpdate = examApplicationFormRepository.findAll().size();
        examApplicationForm.setId(count.incrementAndGet());

        // Create the ExamApplicationForm
        ExamApplicationFormDTO examApplicationFormDTO = examApplicationFormMapper.toDto(examApplicationForm);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExamApplicationFormMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(examApplicationFormDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ExamApplicationForm in the database
        List<ExamApplicationForm> examApplicationFormList = examApplicationFormRepository.findAll();
        assertThat(examApplicationFormList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateExamApplicationFormWithPatch() throws Exception {
        // Initialize the database
        examApplicationFormRepository.saveAndFlush(examApplicationForm);

        int databaseSizeBeforeUpdate = examApplicationFormRepository.findAll().size();

        // Update the examApplicationForm using partial update
        ExamApplicationForm partialUpdatedExamApplicationForm = new ExamApplicationForm();
        partialUpdatedExamApplicationForm.setId(examApplicationForm.getId());

        partialUpdatedExamApplicationForm
            .firstName(UPDATED_FIRST_NAME)
            .mobileNumber(UPDATED_MOBILE_NUMBER)
            .nationality(UPDATED_NATIONALITY)
            .gender(UPDATED_GENDER)
            .religion(UPDATED_RELIGION)
            .adharNumber(UPDATED_ADHAR_NUMBER)
            .bloodGroup(UPDATED_BLOOD_GROUP)
            .isApproved(UPDATED_IS_APPROVED)
            .catagory(UPDATED_CATAGORY)
            .identityType(UPDATED_IDENTITY_TYPE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .adhar(UPDATED_ADHAR)
            .adharContentType(UPDATED_ADHAR_CONTENT_TYPE)
            .signPath(UPDATED_SIGN_PATH)
            .adharPath(UPDATED_ADHAR_PATH);

        restExamApplicationFormMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExamApplicationForm.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedExamApplicationForm))
            )
            .andExpect(status().isOk());

        // Validate the ExamApplicationForm in the database
        List<ExamApplicationForm> examApplicationFormList = examApplicationFormRepository.findAll();
        assertThat(examApplicationFormList).hasSize(databaseSizeBeforeUpdate);
        ExamApplicationForm testExamApplicationForm = examApplicationFormList.get(examApplicationFormList.size() - 1);
        assertThat(testExamApplicationForm.getRegNumber()).isEqualTo(DEFAULT_REG_NUMBER);
        assertThat(testExamApplicationForm.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testExamApplicationForm.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testExamApplicationForm.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testExamApplicationForm.getDob()).isEqualTo(DEFAULT_DOB);
        assertThat(testExamApplicationForm.getFatherName()).isEqualTo(DEFAULT_FATHER_NAME);
        assertThat(testExamApplicationForm.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testExamApplicationForm.getMobileNumber()).isEqualTo(UPDATED_MOBILE_NUMBER);
        assertThat(testExamApplicationForm.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testExamApplicationForm.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testExamApplicationForm.getReligion()).isEqualTo(UPDATED_RELIGION);
        assertThat(testExamApplicationForm.getAdharNumber()).isEqualTo(UPDATED_ADHAR_NUMBER);
        assertThat(testExamApplicationForm.getBloodGroup()).isEqualTo(UPDATED_BLOOD_GROUP);
        assertThat(testExamApplicationForm.getIsApproved()).isEqualTo(UPDATED_IS_APPROVED);
        assertThat(testExamApplicationForm.getCatagory()).isEqualTo(UPDATED_CATAGORY);
        assertThat(testExamApplicationForm.getIdentityType()).isEqualTo(UPDATED_IDENTITY_TYPE);
        assertThat(testExamApplicationForm.getIdentityNumber()).isEqualTo(DEFAULT_IDENTITY_NUMBER);
        assertThat(testExamApplicationForm.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testExamApplicationForm.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testExamApplicationForm.getSignature()).isEqualTo(DEFAULT_SIGNATURE);
        assertThat(testExamApplicationForm.getSignatureContentType()).isEqualTo(DEFAULT_SIGNATURE_CONTENT_TYPE);
        assertThat(testExamApplicationForm.getAdhar()).isEqualTo(UPDATED_ADHAR);
        assertThat(testExamApplicationForm.getAdharContentType()).isEqualTo(UPDATED_ADHAR_CONTENT_TYPE);
        assertThat(testExamApplicationForm.getImagePath()).isEqualTo(DEFAULT_IMAGE_PATH);
        assertThat(testExamApplicationForm.getSignPath()).isEqualTo(UPDATED_SIGN_PATH);
        assertThat(testExamApplicationForm.getAdharPath()).isEqualTo(UPDATED_ADHAR_PATH);
    }

    @Test
    @Transactional
    void fullUpdateExamApplicationFormWithPatch() throws Exception {
        // Initialize the database
        examApplicationFormRepository.saveAndFlush(examApplicationForm);

        int databaseSizeBeforeUpdate = examApplicationFormRepository.findAll().size();

        // Update the examApplicationForm using partial update
        ExamApplicationForm partialUpdatedExamApplicationForm = new ExamApplicationForm();
        partialUpdatedExamApplicationForm.setId(examApplicationForm.getId());

        partialUpdatedExamApplicationForm
            .regNumber(UPDATED_REG_NUMBER)
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            .dob(UPDATED_DOB)
            .fatherName(UPDATED_FATHER_NAME)
            .email(UPDATED_EMAIL)
            .mobileNumber(UPDATED_MOBILE_NUMBER)
            .nationality(UPDATED_NATIONALITY)
            .gender(UPDATED_GENDER)
            .religion(UPDATED_RELIGION)
            .adharNumber(UPDATED_ADHAR_NUMBER)
            .bloodGroup(UPDATED_BLOOD_GROUP)
            .isApproved(UPDATED_IS_APPROVED)
            .catagory(UPDATED_CATAGORY)
            .identityType(UPDATED_IDENTITY_TYPE)
            .identityNumber(UPDATED_IDENTITY_NUMBER)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .signature(UPDATED_SIGNATURE)
            .signatureContentType(UPDATED_SIGNATURE_CONTENT_TYPE)
            .adhar(UPDATED_ADHAR)
            .adharContentType(UPDATED_ADHAR_CONTENT_TYPE)
            .imagePath(UPDATED_IMAGE_PATH)
            .signPath(UPDATED_SIGN_PATH)
            .adharPath(UPDATED_ADHAR_PATH);

        restExamApplicationFormMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExamApplicationForm.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedExamApplicationForm))
            )
            .andExpect(status().isOk());

        // Validate the ExamApplicationForm in the database
        List<ExamApplicationForm> examApplicationFormList = examApplicationFormRepository.findAll();
        assertThat(examApplicationFormList).hasSize(databaseSizeBeforeUpdate);
        ExamApplicationForm testExamApplicationForm = examApplicationFormList.get(examApplicationFormList.size() - 1);
        assertThat(testExamApplicationForm.getRegNumber()).isEqualTo(UPDATED_REG_NUMBER);
        assertThat(testExamApplicationForm.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testExamApplicationForm.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testExamApplicationForm.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testExamApplicationForm.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testExamApplicationForm.getFatherName()).isEqualTo(UPDATED_FATHER_NAME);
        assertThat(testExamApplicationForm.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testExamApplicationForm.getMobileNumber()).isEqualTo(UPDATED_MOBILE_NUMBER);
        assertThat(testExamApplicationForm.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testExamApplicationForm.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testExamApplicationForm.getReligion()).isEqualTo(UPDATED_RELIGION);
        assertThat(testExamApplicationForm.getAdharNumber()).isEqualTo(UPDATED_ADHAR_NUMBER);
        assertThat(testExamApplicationForm.getBloodGroup()).isEqualTo(UPDATED_BLOOD_GROUP);
        assertThat(testExamApplicationForm.getIsApproved()).isEqualTo(UPDATED_IS_APPROVED);
        assertThat(testExamApplicationForm.getCatagory()).isEqualTo(UPDATED_CATAGORY);
        assertThat(testExamApplicationForm.getIdentityType()).isEqualTo(UPDATED_IDENTITY_TYPE);
        assertThat(testExamApplicationForm.getIdentityNumber()).isEqualTo(UPDATED_IDENTITY_NUMBER);
        assertThat(testExamApplicationForm.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testExamApplicationForm.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testExamApplicationForm.getSignature()).isEqualTo(UPDATED_SIGNATURE);
        assertThat(testExamApplicationForm.getSignatureContentType()).isEqualTo(UPDATED_SIGNATURE_CONTENT_TYPE);
        assertThat(testExamApplicationForm.getAdhar()).isEqualTo(UPDATED_ADHAR);
        assertThat(testExamApplicationForm.getAdharContentType()).isEqualTo(UPDATED_ADHAR_CONTENT_TYPE);
        assertThat(testExamApplicationForm.getImagePath()).isEqualTo(UPDATED_IMAGE_PATH);
        assertThat(testExamApplicationForm.getSignPath()).isEqualTo(UPDATED_SIGN_PATH);
        assertThat(testExamApplicationForm.getAdharPath()).isEqualTo(UPDATED_ADHAR_PATH);
    }

    @Test
    @Transactional
    void patchNonExistingExamApplicationForm() throws Exception {
        int databaseSizeBeforeUpdate = examApplicationFormRepository.findAll().size();
        examApplicationForm.setId(count.incrementAndGet());

        // Create the ExamApplicationForm
        ExamApplicationFormDTO examApplicationFormDTO = examApplicationFormMapper.toDto(examApplicationForm);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExamApplicationFormMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, examApplicationFormDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(examApplicationFormDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExamApplicationForm in the database
        List<ExamApplicationForm> examApplicationFormList = examApplicationFormRepository.findAll();
        assertThat(examApplicationFormList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchExamApplicationForm() throws Exception {
        int databaseSizeBeforeUpdate = examApplicationFormRepository.findAll().size();
        examApplicationForm.setId(count.incrementAndGet());

        // Create the ExamApplicationForm
        ExamApplicationFormDTO examApplicationFormDTO = examApplicationFormMapper.toDto(examApplicationForm);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExamApplicationFormMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(examApplicationFormDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExamApplicationForm in the database
        List<ExamApplicationForm> examApplicationFormList = examApplicationFormRepository.findAll();
        assertThat(examApplicationFormList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamExamApplicationForm() throws Exception {
        int databaseSizeBeforeUpdate = examApplicationFormRepository.findAll().size();
        examApplicationForm.setId(count.incrementAndGet());

        // Create the ExamApplicationForm
        ExamApplicationFormDTO examApplicationFormDTO = examApplicationFormMapper.toDto(examApplicationForm);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExamApplicationFormMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(examApplicationFormDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ExamApplicationForm in the database
        List<ExamApplicationForm> examApplicationFormList = examApplicationFormRepository.findAll();
        assertThat(examApplicationFormList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteExamApplicationForm() throws Exception {
        // Initialize the database
        examApplicationFormRepository.saveAndFlush(examApplicationForm);

        int databaseSizeBeforeDelete = examApplicationFormRepository.findAll().size();

        // Delete the examApplicationForm
        restExamApplicationFormMockMvc
            .perform(delete(ENTITY_API_URL_ID, examApplicationForm.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ExamApplicationForm> examApplicationFormList = examApplicationFormRepository.findAll();
        assertThat(examApplicationFormList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
