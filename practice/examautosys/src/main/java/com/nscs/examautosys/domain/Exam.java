package com.nscs.examautosys.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nscs.examautosys.domain.enumeration.ExamType;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Exam.
 */
@Entity
@Table(name = "exam")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Exam implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ExamType type;

    @Column(name = "code")
    private String code;

    @Column(name = "date")
    private Instant date;

    @Column(name = "start_time")
    private LocalDate startTime;

    @Column(name = "end_time")
    private LocalDate endTime;

    @OneToOne
    @JoinColumn(unique = true)
    private Address correspondingAddress;

    @OneToOne
    @JoinColumn(unique = true)
    private Address permanentAddress;

    @OneToMany(mappedBy = "exam")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "correspondingAddress", "student", "exam", "examCenter" }, allowSetters = true)
    private Set<ExamApplicationForm> applicationForms = new HashSet<>();

    @OneToMany(mappedBy = "exam")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "address", "examApplicationForms", "exam" }, allowSetters = true)
    private Set<ExamCenter> examCenters = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "exams", "subjectPapers", "academicBatch" }, allowSetters = true)
    private Session session;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Exam id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Exam name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ExamType getType() {
        return this.type;
    }

    public Exam type(ExamType type) {
        this.setType(type);
        return this;
    }

    public void setType(ExamType type) {
        this.type = type;
    }

    public String getCode() {
        return this.code;
    }

    public Exam code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Instant getDate() {
        return this.date;
    }

    public Exam date(Instant date) {
        this.setDate(date);
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public LocalDate getStartTime() {
        return this.startTime;
    }

    public Exam startTime(LocalDate startTime) {
        this.setStartTime(startTime);
        return this;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }

    public LocalDate getEndTime() {
        return this.endTime;
    }

    public Exam endTime(LocalDate endTime) {
        this.setEndTime(endTime);
        return this;
    }

    public void setEndTime(LocalDate endTime) {
        this.endTime = endTime;
    }

    public Address getCorrespondingAddress() {
        return this.correspondingAddress;
    }

    public void setCorrespondingAddress(Address address) {
        this.correspondingAddress = address;
    }

    public Exam correspondingAddress(Address address) {
        this.setCorrespondingAddress(address);
        return this;
    }

    public Address getPermanentAddress() {
        return this.permanentAddress;
    }

    public void setPermanentAddress(Address address) {
        this.permanentAddress = address;
    }

    public Exam permanentAddress(Address address) {
        this.setPermanentAddress(address);
        return this;
    }

    public Set<ExamApplicationForm> getApplicationForms() {
        return this.applicationForms;
    }

    public void setApplicationForms(Set<ExamApplicationForm> examApplicationForms) {
        if (this.applicationForms != null) {
            this.applicationForms.forEach(i -> i.setExam(null));
        }
        if (examApplicationForms != null) {
            examApplicationForms.forEach(i -> i.setExam(this));
        }
        this.applicationForms = examApplicationForms;
    }

    public Exam applicationForms(Set<ExamApplicationForm> examApplicationForms) {
        this.setApplicationForms(examApplicationForms);
        return this;
    }

    public Exam addApplicationForm(ExamApplicationForm examApplicationForm) {
        this.applicationForms.add(examApplicationForm);
        examApplicationForm.setExam(this);
        return this;
    }

    public Exam removeApplicationForm(ExamApplicationForm examApplicationForm) {
        this.applicationForms.remove(examApplicationForm);
        examApplicationForm.setExam(null);
        return this;
    }

    public Set<ExamCenter> getExamCenters() {
        return this.examCenters;
    }

    public void setExamCenters(Set<ExamCenter> examCenters) {
        if (this.examCenters != null) {
            this.examCenters.forEach(i -> i.setExam(null));
        }
        if (examCenters != null) {
            examCenters.forEach(i -> i.setExam(this));
        }
        this.examCenters = examCenters;
    }

    public Exam examCenters(Set<ExamCenter> examCenters) {
        this.setExamCenters(examCenters);
        return this;
    }

    public Exam addExamCenter(ExamCenter examCenter) {
        this.examCenters.add(examCenter);
        examCenter.setExam(this);
        return this;
    }

    public Exam removeExamCenter(ExamCenter examCenter) {
        this.examCenters.remove(examCenter);
        examCenter.setExam(null);
        return this;
    }

    public Session getSession() {
        return this.session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Exam session(Session session) {
        this.setSession(session);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Exam)) {
            return false;
        }
        return id != null && id.equals(((Exam) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Exam{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", code='" + getCode() + "'" +
            ", date='" + getDate() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            "}";
    }
}
