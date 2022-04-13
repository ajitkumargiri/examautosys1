package com.nscs.examautosys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.nscs.examautosys.IntegrationTest;
import com.nscs.examautosys.domain.Student;
import com.nscs.examautosys.domain.enumeration.BloodGroup;
import com.nscs.examautosys.domain.enumeration.Gender;
import com.nscs.examautosys.domain.enumeration.MaritialStatus;
import com.nscs.examautosys.repository.StudentRepository;
import com.nscs.examautosys.service.dto.StudentDTO;
import com.nscs.examautosys.service.mapper.StudentMapper;
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
 * Integration tests for the {@link StudentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StudentResourceIT {

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

    private static final String DEFAULT_MOTHER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MOTHER_NAME = "BBBBBBBBBB";

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

    private static final String DEFAULT_CATAGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATAGORY = "BBBBBBBBBB";

    private static final MaritialStatus DEFAULT_MARITIAL_STATUS = MaritialStatus.MARRIED;
    private static final MaritialStatus UPDATED_MARITIAL_STATUS = MaritialStatus.UNMARRIED;

    private static final String DEFAULT_ADHAR_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ADHAR_NUMBER = "BBBBBBBBBB";

    private static final BloodGroup DEFAULT_BLOOD_GROUP = BloodGroup.O_POS;
    private static final BloodGroup UPDATED_BLOOD_GROUP = BloodGroup.O_NEG;

    private static final String DEFAULT_FATHER_MOBILE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_FATHER_MOBILE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_FATHER_EMAIL_ID = "AAAAAAAAAA";
    private static final String UPDATED_FATHER_EMAIL_ID = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/students";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStudentMockMvc;

    private Student student;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Student createEntity(EntityManager em) {
        Student student = new Student()
            .regNumber(DEFAULT_REG_NUMBER)
            .firstName(DEFAULT_FIRST_NAME)
            .middleName(DEFAULT_MIDDLE_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .dob(DEFAULT_DOB)
            .fatherName(DEFAULT_FATHER_NAME)
            .motherName(DEFAULT_MOTHER_NAME)
            .email(DEFAULT_EMAIL)
            .mobileNumber(DEFAULT_MOBILE_NUMBER)
            .nationality(DEFAULT_NATIONALITY)
            .gender(DEFAULT_GENDER)
            .religion(DEFAULT_RELIGION)
            .catagory(DEFAULT_CATAGORY)
            .maritialStatus(DEFAULT_MARITIAL_STATUS)
            .adharNumber(DEFAULT_ADHAR_NUMBER)
            .bloodGroup(DEFAULT_BLOOD_GROUP)
            .fatherMobileNumber(DEFAULT_FATHER_MOBILE_NUMBER)
            .fatherEmailId(DEFAULT_FATHER_EMAIL_ID)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .signature(DEFAULT_SIGNATURE)
            .signatureContentType(DEFAULT_SIGNATURE_CONTENT_TYPE)
            .adhar(DEFAULT_ADHAR)
            .adharContentType(DEFAULT_ADHAR_CONTENT_TYPE);
        return student;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Student createUpdatedEntity(EntityManager em) {
        Student student = new Student()
            .regNumber(UPDATED_REG_NUMBER)
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            .dob(UPDATED_DOB)
            .fatherName(UPDATED_FATHER_NAME)
            .motherName(UPDATED_MOTHER_NAME)
            .email(UPDATED_EMAIL)
            .mobileNumber(UPDATED_MOBILE_NUMBER)
            .nationality(UPDATED_NATIONALITY)
            .gender(UPDATED_GENDER)
            .religion(UPDATED_RELIGION)
            .catagory(UPDATED_CATAGORY)
            .maritialStatus(UPDATED_MARITIAL_STATUS)
            .adharNumber(UPDATED_ADHAR_NUMBER)
            .bloodGroup(UPDATED_BLOOD_GROUP)
            .fatherMobileNumber(UPDATED_FATHER_MOBILE_NUMBER)
            .fatherEmailId(UPDATED_FATHER_EMAIL_ID)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .signature(UPDATED_SIGNATURE)
            .signatureContentType(UPDATED_SIGNATURE_CONTENT_TYPE)
            .adhar(UPDATED_ADHAR)
            .adharContentType(UPDATED_ADHAR_CONTENT_TYPE);
        return student;
    }

    @BeforeEach
    public void initTest() {
        student = createEntity(em);
    }

    @Test
    @Transactional
    void createStudent() throws Exception {
        int databaseSizeBeforeCreate = studentRepository.findAll().size();
        // Create the Student
        StudentDTO studentDTO = studentMapper.toDto(student);
        restStudentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(studentDTO)))
            .andExpect(status().isCreated());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeCreate + 1);
        Student testStudent = studentList.get(studentList.size() - 1);
        assertThat(testStudent.getRegNumber()).isEqualTo(DEFAULT_REG_NUMBER);
        assertThat(testStudent.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testStudent.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testStudent.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testStudent.getDob()).isEqualTo(DEFAULT_DOB);
        assertThat(testStudent.getFatherName()).isEqualTo(DEFAULT_FATHER_NAME);
        assertThat(testStudent.getMotherName()).isEqualTo(DEFAULT_MOTHER_NAME);
        assertThat(testStudent.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testStudent.getMobileNumber()).isEqualTo(DEFAULT_MOBILE_NUMBER);
        assertThat(testStudent.getNationality()).isEqualTo(DEFAULT_NATIONALITY);
        assertThat(testStudent.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testStudent.getReligion()).isEqualTo(DEFAULT_RELIGION);
        assertThat(testStudent.getCatagory()).isEqualTo(DEFAULT_CATAGORY);
        assertThat(testStudent.getMaritialStatus()).isEqualTo(DEFAULT_MARITIAL_STATUS);
        assertThat(testStudent.getAdharNumber()).isEqualTo(DEFAULT_ADHAR_NUMBER);
        assertThat(testStudent.getBloodGroup()).isEqualTo(DEFAULT_BLOOD_GROUP);
        assertThat(testStudent.getFatherMobileNumber()).isEqualTo(DEFAULT_FATHER_MOBILE_NUMBER);
        assertThat(testStudent.getFatherEmailId()).isEqualTo(DEFAULT_FATHER_EMAIL_ID);
        assertThat(testStudent.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testStudent.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testStudent.getSignature()).isEqualTo(DEFAULT_SIGNATURE);
        assertThat(testStudent.getSignatureContentType()).isEqualTo(DEFAULT_SIGNATURE_CONTENT_TYPE);
        assertThat(testStudent.getAdhar()).isEqualTo(DEFAULT_ADHAR);
        assertThat(testStudent.getAdharContentType()).isEqualTo(DEFAULT_ADHAR_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createStudentWithExistingId() throws Exception {
        // Create the Student with an existing ID
        student.setId(1L);
        StudentDTO studentDTO = studentMapper.toDto(student);

        int databaseSizeBeforeCreate = studentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(studentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentRepository.findAll().size();
        // set the field null
        student.setFirstName(null);

        // Create the Student, which fails.
        StudentDTO studentDTO = studentMapper.toDto(student);

        restStudentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(studentDTO)))
            .andExpect(status().isBadRequest());

        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentRepository.findAll().size();
        // set the field null
        student.setLastName(null);

        // Create the Student, which fails.
        StudentDTO studentDTO = studentMapper.toDto(student);

        restStudentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(studentDTO)))
            .andExpect(status().isBadRequest());

        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentRepository.findAll().size();
        // set the field null
        student.setEmail(null);

        // Create the Student, which fails.
        StudentDTO studentDTO = studentMapper.toDto(student);

        restStudentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(studentDTO)))
            .andExpect(status().isBadRequest());

        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMobileNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentRepository.findAll().size();
        // set the field null
        student.setMobileNumber(null);

        // Create the Student, which fails.
        StudentDTO studentDTO = studentMapper.toDto(student);

        restStudentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(studentDTO)))
            .andExpect(status().isBadRequest());

        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNationalityIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentRepository.findAll().size();
        // set the field null
        student.setNationality(null);

        // Create the Student, which fails.
        StudentDTO studentDTO = studentMapper.toDto(student);

        restStudentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(studentDTO)))
            .andExpect(status().isBadRequest());

        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAdharNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentRepository.findAll().size();
        // set the field null
        student.setAdharNumber(null);

        // Create the Student, which fails.
        StudentDTO studentDTO = studentMapper.toDto(student);

        restStudentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(studentDTO)))
            .andExpect(status().isBadRequest());

        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllStudents() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList
        restStudentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(student.getId().intValue())))
            .andExpect(jsonPath("$.[*].regNumber").value(hasItem(DEFAULT_REG_NUMBER)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].dob").value(hasItem(DEFAULT_DOB.toString())))
            .andExpect(jsonPath("$.[*].fatherName").value(hasItem(DEFAULT_FATHER_NAME)))
            .andExpect(jsonPath("$.[*].motherName").value(hasItem(DEFAULT_MOTHER_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].mobileNumber").value(hasItem(DEFAULT_MOBILE_NUMBER)))
            .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].religion").value(hasItem(DEFAULT_RELIGION)))
            .andExpect(jsonPath("$.[*].catagory").value(hasItem(DEFAULT_CATAGORY)))
            .andExpect(jsonPath("$.[*].maritialStatus").value(hasItem(DEFAULT_MARITIAL_STATUS.toString())))
            .andExpect(jsonPath("$.[*].adharNumber").value(hasItem(DEFAULT_ADHAR_NUMBER)))
            .andExpect(jsonPath("$.[*].bloodGroup").value(hasItem(DEFAULT_BLOOD_GROUP.toString())))
            .andExpect(jsonPath("$.[*].fatherMobileNumber").value(hasItem(DEFAULT_FATHER_MOBILE_NUMBER)))
            .andExpect(jsonPath("$.[*].fatherEmailId").value(hasItem(DEFAULT_FATHER_EMAIL_ID)))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].signatureContentType").value(hasItem(DEFAULT_SIGNATURE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].signature").value(hasItem(Base64Utils.encodeToString(DEFAULT_SIGNATURE))))
            .andExpect(jsonPath("$.[*].adharContentType").value(hasItem(DEFAULT_ADHAR_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].adhar").value(hasItem(Base64Utils.encodeToString(DEFAULT_ADHAR))));
    }

    @Test
    @Transactional
    void getStudent() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get the student
        restStudentMockMvc
            .perform(get(ENTITY_API_URL_ID, student.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(student.getId().intValue()))
            .andExpect(jsonPath("$.regNumber").value(DEFAULT_REG_NUMBER))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.middleName").value(DEFAULT_MIDDLE_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.dob").value(DEFAULT_DOB.toString()))
            .andExpect(jsonPath("$.fatherName").value(DEFAULT_FATHER_NAME))
            .andExpect(jsonPath("$.motherName").value(DEFAULT_MOTHER_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.mobileNumber").value(DEFAULT_MOBILE_NUMBER))
            .andExpect(jsonPath("$.nationality").value(DEFAULT_NATIONALITY))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.religion").value(DEFAULT_RELIGION))
            .andExpect(jsonPath("$.catagory").value(DEFAULT_CATAGORY))
            .andExpect(jsonPath("$.maritialStatus").value(DEFAULT_MARITIAL_STATUS.toString()))
            .andExpect(jsonPath("$.adharNumber").value(DEFAULT_ADHAR_NUMBER))
            .andExpect(jsonPath("$.bloodGroup").value(DEFAULT_BLOOD_GROUP.toString()))
            .andExpect(jsonPath("$.fatherMobileNumber").value(DEFAULT_FATHER_MOBILE_NUMBER))
            .andExpect(jsonPath("$.fatherEmailId").value(DEFAULT_FATHER_EMAIL_ID))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.signatureContentType").value(DEFAULT_SIGNATURE_CONTENT_TYPE))
            .andExpect(jsonPath("$.signature").value(Base64Utils.encodeToString(DEFAULT_SIGNATURE)))
            .andExpect(jsonPath("$.adharContentType").value(DEFAULT_ADHAR_CONTENT_TYPE))
            .andExpect(jsonPath("$.adhar").value(Base64Utils.encodeToString(DEFAULT_ADHAR)));
    }

    @Test
    @Transactional
    void getNonExistingStudent() throws Exception {
        // Get the student
        restStudentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewStudent() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        int databaseSizeBeforeUpdate = studentRepository.findAll().size();

        // Update the student
        Student updatedStudent = studentRepository.findById(student.getId()).get();
        // Disconnect from session so that the updates on updatedStudent are not directly saved in db
        em.detach(updatedStudent);
        updatedStudent
            .regNumber(UPDATED_REG_NUMBER)
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            .dob(UPDATED_DOB)
            .fatherName(UPDATED_FATHER_NAME)
            .motherName(UPDATED_MOTHER_NAME)
            .email(UPDATED_EMAIL)
            .mobileNumber(UPDATED_MOBILE_NUMBER)
            .nationality(UPDATED_NATIONALITY)
            .gender(UPDATED_GENDER)
            .religion(UPDATED_RELIGION)
            .catagory(UPDATED_CATAGORY)
            .maritialStatus(UPDATED_MARITIAL_STATUS)
            .adharNumber(UPDATED_ADHAR_NUMBER)
            .bloodGroup(UPDATED_BLOOD_GROUP)
            .fatherMobileNumber(UPDATED_FATHER_MOBILE_NUMBER)
            .fatherEmailId(UPDATED_FATHER_EMAIL_ID)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .signature(UPDATED_SIGNATURE)
            .signatureContentType(UPDATED_SIGNATURE_CONTENT_TYPE)
            .adhar(UPDATED_ADHAR)
            .adharContentType(UPDATED_ADHAR_CONTENT_TYPE);
        StudentDTO studentDTO = studentMapper.toDto(updatedStudent);

        restStudentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, studentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(studentDTO))
            )
            .andExpect(status().isOk());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeUpdate);
        Student testStudent = studentList.get(studentList.size() - 1);
        assertThat(testStudent.getRegNumber()).isEqualTo(UPDATED_REG_NUMBER);
        assertThat(testStudent.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testStudent.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testStudent.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testStudent.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testStudent.getFatherName()).isEqualTo(UPDATED_FATHER_NAME);
        assertThat(testStudent.getMotherName()).isEqualTo(UPDATED_MOTHER_NAME);
        assertThat(testStudent.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testStudent.getMobileNumber()).isEqualTo(UPDATED_MOBILE_NUMBER);
        assertThat(testStudent.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testStudent.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testStudent.getReligion()).isEqualTo(UPDATED_RELIGION);
        assertThat(testStudent.getCatagory()).isEqualTo(UPDATED_CATAGORY);
        assertThat(testStudent.getMaritialStatus()).isEqualTo(UPDATED_MARITIAL_STATUS);
        assertThat(testStudent.getAdharNumber()).isEqualTo(UPDATED_ADHAR_NUMBER);
        assertThat(testStudent.getBloodGroup()).isEqualTo(UPDATED_BLOOD_GROUP);
        assertThat(testStudent.getFatherMobileNumber()).isEqualTo(UPDATED_FATHER_MOBILE_NUMBER);
        assertThat(testStudent.getFatherEmailId()).isEqualTo(UPDATED_FATHER_EMAIL_ID);
        assertThat(testStudent.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testStudent.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testStudent.getSignature()).isEqualTo(UPDATED_SIGNATURE);
        assertThat(testStudent.getSignatureContentType()).isEqualTo(UPDATED_SIGNATURE_CONTENT_TYPE);
        assertThat(testStudent.getAdhar()).isEqualTo(UPDATED_ADHAR);
        assertThat(testStudent.getAdharContentType()).isEqualTo(UPDATED_ADHAR_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingStudent() throws Exception {
        int databaseSizeBeforeUpdate = studentRepository.findAll().size();
        student.setId(count.incrementAndGet());

        // Create the Student
        StudentDTO studentDTO = studentMapper.toDto(student);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, studentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(studentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStudent() throws Exception {
        int databaseSizeBeforeUpdate = studentRepository.findAll().size();
        student.setId(count.incrementAndGet());

        // Create the Student
        StudentDTO studentDTO = studentMapper.toDto(student);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStudentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(studentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStudent() throws Exception {
        int databaseSizeBeforeUpdate = studentRepository.findAll().size();
        student.setId(count.incrementAndGet());

        // Create the Student
        StudentDTO studentDTO = studentMapper.toDto(student);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStudentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(studentDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStudentWithPatch() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        int databaseSizeBeforeUpdate = studentRepository.findAll().size();

        // Update the student using partial update
        Student partialUpdatedStudent = new Student();
        partialUpdatedStudent.setId(student.getId());

        partialUpdatedStudent
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            .dob(UPDATED_DOB)
            .mobileNumber(UPDATED_MOBILE_NUMBER)
            .religion(UPDATED_RELIGION)
            .catagory(UPDATED_CATAGORY)
            .bloodGroup(UPDATED_BLOOD_GROUP)
            .fatherMobileNumber(UPDATED_FATHER_MOBILE_NUMBER)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .signature(UPDATED_SIGNATURE)
            .signatureContentType(UPDATED_SIGNATURE_CONTENT_TYPE)
            .adhar(UPDATED_ADHAR)
            .adharContentType(UPDATED_ADHAR_CONTENT_TYPE);

        restStudentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStudent.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStudent))
            )
            .andExpect(status().isOk());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeUpdate);
        Student testStudent = studentList.get(studentList.size() - 1);
        assertThat(testStudent.getRegNumber()).isEqualTo(DEFAULT_REG_NUMBER);
        assertThat(testStudent.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testStudent.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testStudent.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testStudent.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testStudent.getFatherName()).isEqualTo(DEFAULT_FATHER_NAME);
        assertThat(testStudent.getMotherName()).isEqualTo(DEFAULT_MOTHER_NAME);
        assertThat(testStudent.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testStudent.getMobileNumber()).isEqualTo(UPDATED_MOBILE_NUMBER);
        assertThat(testStudent.getNationality()).isEqualTo(DEFAULT_NATIONALITY);
        assertThat(testStudent.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testStudent.getReligion()).isEqualTo(UPDATED_RELIGION);
        assertThat(testStudent.getCatagory()).isEqualTo(UPDATED_CATAGORY);
        assertThat(testStudent.getMaritialStatus()).isEqualTo(DEFAULT_MARITIAL_STATUS);
        assertThat(testStudent.getAdharNumber()).isEqualTo(DEFAULT_ADHAR_NUMBER);
        assertThat(testStudent.getBloodGroup()).isEqualTo(UPDATED_BLOOD_GROUP);
        assertThat(testStudent.getFatherMobileNumber()).isEqualTo(UPDATED_FATHER_MOBILE_NUMBER);
        assertThat(testStudent.getFatherEmailId()).isEqualTo(DEFAULT_FATHER_EMAIL_ID);
        assertThat(testStudent.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testStudent.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testStudent.getSignature()).isEqualTo(UPDATED_SIGNATURE);
        assertThat(testStudent.getSignatureContentType()).isEqualTo(UPDATED_SIGNATURE_CONTENT_TYPE);
        assertThat(testStudent.getAdhar()).isEqualTo(UPDATED_ADHAR);
        assertThat(testStudent.getAdharContentType()).isEqualTo(UPDATED_ADHAR_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateStudentWithPatch() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        int databaseSizeBeforeUpdate = studentRepository.findAll().size();

        // Update the student using partial update
        Student partialUpdatedStudent = new Student();
        partialUpdatedStudent.setId(student.getId());

        partialUpdatedStudent
            .regNumber(UPDATED_REG_NUMBER)
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            .dob(UPDATED_DOB)
            .fatherName(UPDATED_FATHER_NAME)
            .motherName(UPDATED_MOTHER_NAME)
            .email(UPDATED_EMAIL)
            .mobileNumber(UPDATED_MOBILE_NUMBER)
            .nationality(UPDATED_NATIONALITY)
            .gender(UPDATED_GENDER)
            .religion(UPDATED_RELIGION)
            .catagory(UPDATED_CATAGORY)
            .maritialStatus(UPDATED_MARITIAL_STATUS)
            .adharNumber(UPDATED_ADHAR_NUMBER)
            .bloodGroup(UPDATED_BLOOD_GROUP)
            .fatherMobileNumber(UPDATED_FATHER_MOBILE_NUMBER)
            .fatherEmailId(UPDATED_FATHER_EMAIL_ID)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .signature(UPDATED_SIGNATURE)
            .signatureContentType(UPDATED_SIGNATURE_CONTENT_TYPE)
            .adhar(UPDATED_ADHAR)
            .adharContentType(UPDATED_ADHAR_CONTENT_TYPE);

        restStudentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStudent.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStudent))
            )
            .andExpect(status().isOk());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeUpdate);
        Student testStudent = studentList.get(studentList.size() - 1);
        assertThat(testStudent.getRegNumber()).isEqualTo(UPDATED_REG_NUMBER);
        assertThat(testStudent.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testStudent.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testStudent.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testStudent.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testStudent.getFatherName()).isEqualTo(UPDATED_FATHER_NAME);
        assertThat(testStudent.getMotherName()).isEqualTo(UPDATED_MOTHER_NAME);
        assertThat(testStudent.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testStudent.getMobileNumber()).isEqualTo(UPDATED_MOBILE_NUMBER);
        assertThat(testStudent.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testStudent.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testStudent.getReligion()).isEqualTo(UPDATED_RELIGION);
        assertThat(testStudent.getCatagory()).isEqualTo(UPDATED_CATAGORY);
        assertThat(testStudent.getMaritialStatus()).isEqualTo(UPDATED_MARITIAL_STATUS);
        assertThat(testStudent.getAdharNumber()).isEqualTo(UPDATED_ADHAR_NUMBER);
        assertThat(testStudent.getBloodGroup()).isEqualTo(UPDATED_BLOOD_GROUP);
        assertThat(testStudent.getFatherMobileNumber()).isEqualTo(UPDATED_FATHER_MOBILE_NUMBER);
        assertThat(testStudent.getFatherEmailId()).isEqualTo(UPDATED_FATHER_EMAIL_ID);
        assertThat(testStudent.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testStudent.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testStudent.getSignature()).isEqualTo(UPDATED_SIGNATURE);
        assertThat(testStudent.getSignatureContentType()).isEqualTo(UPDATED_SIGNATURE_CONTENT_TYPE);
        assertThat(testStudent.getAdhar()).isEqualTo(UPDATED_ADHAR);
        assertThat(testStudent.getAdharContentType()).isEqualTo(UPDATED_ADHAR_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingStudent() throws Exception {
        int databaseSizeBeforeUpdate = studentRepository.findAll().size();
        student.setId(count.incrementAndGet());

        // Create the Student
        StudentDTO studentDTO = studentMapper.toDto(student);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, studentDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(studentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStudent() throws Exception {
        int databaseSizeBeforeUpdate = studentRepository.findAll().size();
        student.setId(count.incrementAndGet());

        // Create the Student
        StudentDTO studentDTO = studentMapper.toDto(student);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStudentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(studentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStudent() throws Exception {
        int databaseSizeBeforeUpdate = studentRepository.findAll().size();
        student.setId(count.incrementAndGet());

        // Create the Student
        StudentDTO studentDTO = studentMapper.toDto(student);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStudentMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(studentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStudent() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        int databaseSizeBeforeDelete = studentRepository.findAll().size();

        // Delete the student
        restStudentMockMvc
            .perform(delete(ENTITY_API_URL_ID, student.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
