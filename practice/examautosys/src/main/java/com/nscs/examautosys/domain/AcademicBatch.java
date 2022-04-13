package com.nscs.examautosys.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AcademicBatch.
 */
@Entity
@Table(name = "academic_batch")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AcademicBatch implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "code")
    private String code;

    @NotNull
    @Column(name = "academic_start_year", nullable = false)
    private Integer academicStartYear;

    @NotNull
    @Column(name = "academic_end_year", nullable = false)
    private Integer academicEndYear;

    @OneToMany(mappedBy = "academicBatch")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "exams", "subjectPapers", "academicBatch" }, allowSetters = true)
    private Set<Session> sessions = new HashSet<>();

    @OneToMany(mappedBy = "academicBatch")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "examApplicationForms", "academicBatch" }, allowSetters = true)
    private Set<Student> students = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "academicBatches", "course" }, allowSetters = true)
    private Branch branch;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AcademicBatch id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public AcademicBatch name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public AcademicBatch code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getAcademicStartYear() {
        return this.academicStartYear;
    }

    public AcademicBatch academicStartYear(Integer academicStartYear) {
        this.setAcademicStartYear(academicStartYear);
        return this;
    }

    public void setAcademicStartYear(Integer academicStartYear) {
        this.academicStartYear = academicStartYear;
    }

    public Integer getAcademicEndYear() {
        return this.academicEndYear;
    }

    public AcademicBatch academicEndYear(Integer academicEndYear) {
        this.setAcademicEndYear(academicEndYear);
        return this;
    }

    public void setAcademicEndYear(Integer academicEndYear) {
        this.academicEndYear = academicEndYear;
    }

    public Set<Session> getSessions() {
        return this.sessions;
    }

    public void setSessions(Set<Session> sessions) {
        if (this.sessions != null) {
            this.sessions.forEach(i -> i.setAcademicBatch(null));
        }
        if (sessions != null) {
            sessions.forEach(i -> i.setAcademicBatch(this));
        }
        this.sessions = sessions;
    }

    public AcademicBatch sessions(Set<Session> sessions) {
        this.setSessions(sessions);
        return this;
    }

    public AcademicBatch addSession(Session session) {
        this.sessions.add(session);
        session.setAcademicBatch(this);
        return this;
    }

    public AcademicBatch removeSession(Session session) {
        this.sessions.remove(session);
        session.setAcademicBatch(null);
        return this;
    }

    public Set<Student> getStudents() {
        return this.students;
    }

    public void setStudents(Set<Student> students) {
        if (this.students != null) {
            this.students.forEach(i -> i.setAcademicBatch(null));
        }
        if (students != null) {
            students.forEach(i -> i.setAcademicBatch(this));
        }
        this.students = students;
    }

    public AcademicBatch students(Set<Student> students) {
        this.setStudents(students);
        return this;
    }

    public AcademicBatch addStudent(Student student) {
        this.students.add(student);
        student.setAcademicBatch(this);
        return this;
    }

    public AcademicBatch removeStudent(Student student) {
        this.students.remove(student);
        student.setAcademicBatch(null);
        return this;
    }

    public Branch getBranch() {
        return this.branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public AcademicBatch branch(Branch branch) {
        this.setBranch(branch);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AcademicBatch)) {
            return false;
        }
        return id != null && id.equals(((AcademicBatch) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AcademicBatch{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", academicStartYear=" + getAcademicStartYear() +
            ", academicEndYear=" + getAcademicEndYear() +
            "}";
    }
}
