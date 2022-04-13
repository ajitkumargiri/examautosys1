package com.nscs.examautosys.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Session.
 */
@Entity
@Table(name = "session")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Session implements Serializable {

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

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @OneToMany(mappedBy = "session")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "correspondingAddress", "permanentAddress", "applicationForms", "examCenters", "session" },
        allowSetters = true
    )
    private Set<Exam> exams = new HashSet<>();

    @OneToMany(mappedBy = "session")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "session" }, allowSetters = true)
    private Set<SubjectPaper> subjectPapers = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "sessions", "students", "branch" }, allowSetters = true)
    private AcademicBatch academicBatch;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Session id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Session name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public Session code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public Session startDate(LocalDate startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public Session endDate(LocalDate endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Set<Exam> getExams() {
        return this.exams;
    }

    public void setExams(Set<Exam> exams) {
        if (this.exams != null) {
            this.exams.forEach(i -> i.setSession(null));
        }
        if (exams != null) {
            exams.forEach(i -> i.setSession(this));
        }
        this.exams = exams;
    }

    public Session exams(Set<Exam> exams) {
        this.setExams(exams);
        return this;
    }

    public Session addExam(Exam exam) {
        this.exams.add(exam);
        exam.setSession(this);
        return this;
    }

    public Session removeExam(Exam exam) {
        this.exams.remove(exam);
        exam.setSession(null);
        return this;
    }

    public Set<SubjectPaper> getSubjectPapers() {
        return this.subjectPapers;
    }

    public void setSubjectPapers(Set<SubjectPaper> subjectPapers) {
        if (this.subjectPapers != null) {
            this.subjectPapers.forEach(i -> i.setSession(null));
        }
        if (subjectPapers != null) {
            subjectPapers.forEach(i -> i.setSession(this));
        }
        this.subjectPapers = subjectPapers;
    }

    public Session subjectPapers(Set<SubjectPaper> subjectPapers) {
        this.setSubjectPapers(subjectPapers);
        return this;
    }

    public Session addSubjectPaper(SubjectPaper subjectPaper) {
        this.subjectPapers.add(subjectPaper);
        subjectPaper.setSession(this);
        return this;
    }

    public Session removeSubjectPaper(SubjectPaper subjectPaper) {
        this.subjectPapers.remove(subjectPaper);
        subjectPaper.setSession(null);
        return this;
    }

    public AcademicBatch getAcademicBatch() {
        return this.academicBatch;
    }

    public void setAcademicBatch(AcademicBatch academicBatch) {
        this.academicBatch = academicBatch;
    }

    public Session academicBatch(AcademicBatch academicBatch) {
        this.setAcademicBatch(academicBatch);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Session)) {
            return false;
        }
        return id != null && id.equals(((Session) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Session{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            "}";
    }
}
